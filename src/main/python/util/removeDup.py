import pandas as pd

from query import connect
con = connect()
table_name = "tbC2I"
sql = "select * from {}".format(table_name)
pair = pd.read_sql(sql, con)
print("read done")
pair = pair.drop_duplicates()

pair.to_sql(table_name, con, if_exists='replace', index=False)
