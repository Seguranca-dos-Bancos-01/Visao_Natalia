
import psutil
import time
import datetime
from mysql.connector import connect
import pyodbc

# Conectando no Local 

def mysql_connection(host, user, passwd, database=None):
        connection = connect(
            host=host,
            user=user,
            passwd=passwd,
            database=database
        )
        return connection

connection = mysql_connection('localhost', 'root', 'urubu100', 'SecurityBank')

def sql_server_connection(server, database, username, password):
    conn_str = f'DRIVER={{SQL Server}};SERVER={server};DATABASE={database};UID={username};PWD={password}'
    connectionSQL = pyodbc.connect(conn_str)
    
    return connectionSQL

server_connection = sql_server_connection('34.206.192.7', 'SecurityBank', 'sa', 'UrubuDoGit123')


while True :
    cpu = round(psutil.cpu_percent(interval = 1), 2)
    ram = round(psutil.virtual_memory().percent, 2)
    disco = round(psutil.disk_usage('/').percent, 2)
    data = datetime.datetime.now()

    query = '''
            INSERT INTO registros (dataHorario, dadoCaptado, fkServidorReg, fkBanco, fkEspecificacoes, fkComponentesReg, fkMetrica, fkPlano, fkParticao) VALUES
            (%s, %s, %s, %s, %s, %s, %s, %s, %s),
            (%s, %s, %s, %s, %s, %s, %s, %s, %s),
            (%s, %s, %s, %s, %s, %s, %s, %s, %s);
    '''

    insert = [
        data, cpu, 1, 1, 1, 1, 1, 1, 2,
        data, ram, 1, 1, 1, 2, 1, 1, 2,
        data, disco, 1, 1, 1, 3, 1, 1, 2,
    ]
    
    cursor = connection.cursor()
    cursor.execute(query, insert)
    connection.commit() 
    
    querySQLSERVER = '''
            INSERT INTO registros (dataHorario, dadoCaptado, fkServidorReg, fkBanco, fkEspecificacoes, fkComponentesReg, fkMetrica, fkPlano, fkParticao) VALUES
            (?, ?, ?, ?, ?, ?, ?, ?, ?),
            (?, ?, ?, ?, ?, ?, ?, ?, ?),
            (?, ?, ?, ?, ?, ?, ?, ?, ?);
    '''

    insertSQLSERVER = [
        data, cpu, 1, 1, 1, 1, 1, 1, 2,
        data, ram, 1, 1, 1, 2, 1, 1, 2,
        data, disco, 1, 1, 1, 3, 1, 1, 2,
    ]
    
    cursorSQL = server_connection.cursor()
    cursorSQL.execute(querySQLSERVER, insertSQLSERVER)
    server_connection.commit()

   

print(f'{cpu}')
print(f'{ram}')
print(f'{disco}')

time.sleep(30)
    
cursor.close()
cursorSQL.close()
