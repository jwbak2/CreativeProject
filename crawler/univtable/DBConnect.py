

import cx_Oracle


# db init

def dbConnect() :
    dsn = cx_Oracle.makedsn("54.160.178.185",1521,"xe")
    return cx_Oracle.connect("crtvp","ganggoo",dsn)

db = dbConnect()
cursor = db.cursor()

##


cursor.execute("select * from test_table")





# db close

cursor.close()
db.close()

##