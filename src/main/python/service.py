import json
import os
import shutil
import uuid

import pandas as  pd

from db_init import cnx

static_dir = r"E:\basssssss\DatabaseCourseDesign\export_file"


def communityKPIIndicatorInformationQuery(startTimeStamp, endTimeStamp, field, communityName):
    sql = "select 起始时间,`{}` from kpi where (小区名称='{}' and 起始时间>='{}' and 起始时间<='{}' )".format(field, communityName, startTimeStamp,
                                                                                              endTimeStamp)
    mycursor = cnx.cursor()

    mycursor.execute(sql)
    res = []
    for i in mycursor:
        res.append({"time": i[0], field: i[1]})
    return json.dumps({"msg": res})


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

    shutil.move("{}.xlsx".format(file_name),os.path.join(static_dir,file_name+".xlsx"))
    # os.system("mv {} {}".format("{}.xlsx".format(file_name), static_dir))
    return "/database/{}.xlsx".format(file_name)


def convertJsonToExcel(jsonData):
    js = pd.read_json(jsonData)
    file_name = str(uuid.uuid4())
    js.to_excel("{}.xlsx".format(file_name), index=False)

    shutil.move("{}.xlsx".format(file_name),os.path.join(static_dir,file_name+".xlsx"))
    return "/database/{}.xlsx".format(file_name)

if __name__ == '__main__':
    print(communityKPIIndicatorInformationQuery("07/17/2020 00:00:00", "07/18/2020 00:00:00", "无线接通率ay", "H霍义马高速东-HLHF-1"))
    # print(exportTable("kpi"))
    pass
