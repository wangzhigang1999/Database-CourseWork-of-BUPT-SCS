import json
import os
import shutil
import uuid

import pandas as  pd

from db_init import cnx

static_dir = r"E:\basssssss\DatabaseCourseDesign\export_file"


def communityKPIIndicatorInformationQuery(startTimeStamp, endTimeStamp, field, communityName):
    sql = "select 起始时间,`{}` from kpi where (小区名称='{}' and 起始时间>='{}' and 起始时间<='{}' )".format(field, communityName,
                                                                                              startTimeStamp,                                                                               endTimeStamp)
    mycursor = cnx.cursor()
    mycursor.execute(sql)
    time = []
    value = []
    for i in mycursor:
        time.append(i[0])
        value.append(i[1])
    mycursor.close()
    return json.dumps({"time": time, "value": value})


def prb_hour(startTime, endTime, id, node):
    sql = "SELECT StartHour,idx_{} from tbPRBnew WHERE (StartHour > '{}' and StartHour < '{}' and ENODEB_NAME ='{}' )" \
        .format(id, startTime, endTime, node)
    mycursor = cnx.cursor()

    mycursor.execute(sql)
    time = []
    value = []
    for i in mycursor:
        time.append(i[0])
        value.append(i[1])
    json_data = json.dumps({"time": time, "value": value})
    path = convertJsonToExcel(json_data)
    mycursor.close()

    return json.dumps({"time": time, "value": value, "download_url": path})


def prb_min(startTime, endTime, id, node):
    sql = "SELECT StartTime,idx_{} from prb WHERE (StartTime > '{}' and StartTime < '{}' and ENODEB_NAME ='{}' )" \
        .format(id, startTime, endTime, node)
    mycursor = cnx.cursor()

    mycursor.execute(sql)
    time = []
    value = []
    for i in mycursor:
        time.append(i[0])
        value.append(i[1])
    json_data = json.dumps({"time": time, "value": value})
    path = convertJsonToExcel(json_data)
    mycursor.close()
    return json.dumps({"time": time, "value": value, "download_url": path})


def exportTable(tableName):
    print(tableName)
    sqlcmd = "select * from {}".format(tableName)
    chunks = pd.read_sql(sqlcmd, cnx, chunksize=50)

    file_name = tableName
    tmp_path = "{}.csv".format(file_name)
    for df in chunks:
        df.to_csv(tmp_path, mode='a', index=False)
    df = pd.read_csv(tmp_path)
    df.to_excel("{}.xlsx".format(file_name), index=False)
    os.remove(tmp_path)

    shutil.move("{}.xlsx".format(file_name), os.path.join(static_dir, file_name + ".xlsx"))
    # os.system("mv {} {}".format("{}.xlsx".format(file_name), static_dir))
    return "/database/{}.xlsx".format(file_name)


def convertJsonToExcel(jsonData):
    js = pd.read_json(jsonData)
    file_name = str(uuid.uuid4())
    js.to_excel("{}.xlsx".format(file_name), index=False)

    shutil.move("{}.xlsx".format(file_name), os.path.join(static_dir, file_name + ".xlsx"))
    return "/database/{}.xlsx".format(file_name)


def getInfo(type):
    if os.path.exists("{}.json".format(type)):
        with open("{}.json".format(type)) as f:
            res = json.load(f)
            return res
    res = {}
    cursor = cnx.cursor(buffered=True)
    cursor.execute("select * from %s" % type)
    col_name_list = [tuple[0] for tuple in cursor.description]
    community_name = []
    cursor.close()
    if type == "kpi":
        col_name_list.remove('起始时间')
        col_name_list.remove('网元基站名称')
        col_name_list.remove('小区')
        col_name_list.remove('小区名称')

        sql = "select DISTINCT 小区名称 FROM kpi; "
        mycursor = cnx.cursor()

        mycursor.execute(sql)
        for i in mycursor:
            community_name.append(i[0])
        mycursor.close()
    else:
        col_name_list.remove("StartTime")
        col_name_list.remove("ENODEB_NAME")

        sql = "select DISTINCT ENODEB_NAME FROM prb; "
        mycursor = cnx.cursor()

        mycursor.execute(sql)
        for i in mycursor:
            community_name.append(i[0])
        mycursor.close()
    res["available_fields"] = col_name_list
    res["community_name"] = community_name

    with open("{}.json".format(type), "w+") as f:
        json.dump(res, f)

    return json.dumps(res)


if __name__ == '__main__':
    # print(communityKPIIndicatorInformationQuery("07/17/2020 00:00:00", "07/18/2020 00:00:00", "无线接通率ay",
    #                                             "H霍义马高速东-HLHF-1"))
    # print(exportTable("kpi"))
    # print(prb_min('07/17/2020 00', '07/17/2020 12', 10, 'B马310国道煤场-HLHF'))
    # pass
    print(getInfo("prb"))
