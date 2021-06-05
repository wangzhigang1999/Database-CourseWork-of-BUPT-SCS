import json
from itertools import combinations

import pandas as pd
import sqlalchemy
from scipy import stats
from pylouvain import PyLouvain


def connect():
    connect_args = dict(
        host='localhost',
        port=3306,
        user='root',
        password='root',
        db='bupt')
    con = sqlalchemy.create_engine('mysql+pymysql://', connect_args=connect_args)
    return con


def create_tbPRBnew():
    con = connect()
    prbs = list(map(lambda x: 'idx_' + str(x), range(0, 100)))
    sql = f'''
        select `起始时间` as StartTime, `网元_基站名称` as ENODEB_NAME, {', '.join(prbs)}
        from prb
    '''
    tbPRB = pd.read_sql(sql, con)
    tbPRB['StartHour'] = tbPRB['StartTime'].apply(lambda x: x[:-6])
    tbPRB['PRB'] = tbPRB[prbs].mean(axis=1)

    tbPRBnew = tbPRB.groupby(['StartHour', 'ENODEB_NAME'], as_index=False)['PRB'].mean()
    tbPRBnew.to_sql('tbPRBnew', con, if_exists='replace', index=False)


def create_tbC2Inew(threshold):
    threshold = int(threshold)
    con = connect()
    sql = '''
        select ServingSector, InterferingSector, LteScRSRP-LteNcRSRP as diff
        from mro_data
    '''
    tbC2I = pd.read_sql(sql, con)
    tbC2I = tbC2I.groupby(['ServingSector', 'InterferingSector'])['diff'].aggregate(['count', 'mean', 'std'])
    tbC2I = tbC2I[tbC2I['count'] >= threshold]
    tbC2I = tbC2I.drop('count', axis=1)
    tbC2I['PrbC2I9'] = stats.norm.cdf(9, tbC2I['mean'], tbC2I['std'])
    tbC2I['PrbABS6'] = stats.norm.cdf(6, tbC2I['mean'], tbC2I['std']) - stats.norm.cdf(-6, tbC2I['mean'], tbC2I['std'])
    tbC2Inew = tbC2I.reset_index()
    tbC2Inew.to_sql('tbC2Inew', con, if_exists='replace', index=False)
    analyze_interference()
    return json.dumps({"msg": "success"})


def create_tbC2I3(x):
    con = connect()
    x = int(x)
    sql = f'''
        select ServingSector as Sector1, InterferingSector as Sector2
        from tbC2Inew
        where PrbABS6 > {x / 100}
    '''
    pair = pd.read_sql(sql, con)
    pair = pair.append([pair['Sector2'], pair['Sector1']])
    pair = pair.drop_duplicates()
    tbC2I3 = pd.DataFrame(columns=['Sector1', 'Sector2', 'Sector3'])
    i = 0
    for a, df in pair.groupby('Sector1'):
        if df['Sector2'].size > 1:
            for c in combinations(df['Sector2'].values, 2):
                tbC2I3.loc[i] = sorted((a,) + c)
                i += 1

    tbC2I3 = tbC2I3.drop_duplicates()
    tbC2I3 = tbC2I3.sort_values(['Sector1', 'Sector2', 'Sector3'])
    tbC2I3.to_sql('tbC2I3', con, if_exists='replace', index=False)
    return json.dumps({"msg": "success"})


def analyze_interference():
    con = connect()
    sql = f'''
        select ServingSector, InterferingSector, mean
        from tbC2Inew
    '''
    tbC2I = pd.read_sql(sql, con)
    pyl, node_dict = PyLouvain.from_df(tbC2I)
    reverse_node_dict = dict(zip(node_dict.values(), node_dict.keys()))
    partition, q = pyl.apply_method()
    sql = f'''
        select SECTOR_ID, LONGITUDE, LATITUDE
        from cell
    '''
    tbCell = pd.read_sql(sql, con)
    tbCell = tbCell.drop_duplicates()
    tbCell = tbCell[tbCell['SECTOR_ID'].apply(lambda x: x in node_dict)]
    tbCell[['LONGITUDE', 'LATITUDE']] = tbCell[['LONGITUDE', 'LATITUDE']].astype(float)
    tbCell = tbCell.groupby(['LONGITUDE', 'LATITUDE']).apply(createOffset)

    x_dict = dict(zip(tbCell['SECTOR_ID'], tbCell['LONGITUDE']))
    y_dict = dict(zip(tbCell['SECTOR_ID'], tbCell['LATITUDE']))
    offset_dict = dict(zip(tbCell['SECTOR_ID'], tbCell['offset']))
    nodes = []
    categories = []
    community_dict = {}
    for i, p in enumerate(partition):
        categories.append(dict(name=f'社区{i}(节点数{len(partition[i])})', symbol='pin'))
        for node_id in p:
            name = reverse_node_dict[node_id]
            nodes.append(dict(name=name, x=x_dict[name], y=y_dict[name], category=i, symbolOffset=[5 * offset_dict[name], 0]))
            community_dict[name] = i

    links = []
    for _, row in tbC2I.iterrows():
        cross = community_dict[row[0]] != community_dict[row[1]]
        links.append(dict(source=row[0], target=row[1], value=round(row[2], 3), cross=cross))

    with open('static/community.json', 'w') as f:
        json.dump(dict(nodes=nodes, links=links, categories=categories, q=round(q, 3)), f)

    return json.dumps({"msg": "success"})


def createOffset(group):
    group['offset'] = range(len(group))
    return group


if __name__ == '__main__':
    create_tbPRBnew()
    create_tbC2Inew(100)
    create_tbC2I3(70)
    analyze_interference()
