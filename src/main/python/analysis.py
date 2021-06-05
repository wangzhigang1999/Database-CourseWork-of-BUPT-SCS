# %%
import json
import pandas as pd

from pylouvain import PyLouvain
from query import connect

# %%
con = connect()
sql = f'''
    select ServingSector, InterferingSector, mean
    from tbC2Inew
'''
# sql = f'''
#     select SCELL, NCELL, C2I_Mean
#     from tbC2I
# '''

tbC2I = pd.read_sql(sql, con)
# tbC2I.to_csv('tbC2I.csv', sep=' ', header=False, index=False)

# %%
# 获取社区划分
pyl, node_dict = PyLouvain.from_df(tbC2I)
reverse_node_dict = dict(zip(node_dict.values(), node_dict.keys()))  # key是编号的形式，value是253916-2的形式
partition, q = pyl.apply_method()

# %%


def createOffset(group):
    group['offset'] = range(len(group))
    return group


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

# %%
nodes = []
categories = []
community_dict = {}
for i, p in enumerate(partition):
    categories.append(dict(name=f'社区{i}(节点数{len(partition[i])})', symbol='pin'))
    for node_id in p:
        name = reverse_node_dict[node_id]
        nodes.append(dict(name=name, x=x_dict[name], y=y_dict[name], category=i, symbolOffset=[5*offset_dict[name], 0]))
        community_dict[name] = i

links = []
for _, row in tbC2I.iterrows():
    cross = community_dict[row[0]] != community_dict[row[1]]
    links.append(dict(source=row[0], target=row[1], value=round(row[2], 3), cross=cross))

with open('static/community.json', 'w') as f:
    json.dump(dict(nodes=nodes, links=links, categories=categories, q=round(q, 3)), f)
