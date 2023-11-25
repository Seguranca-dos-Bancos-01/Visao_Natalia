
import psutil
import time
import datetime
from mysql.connector import connect

def mysql_connection(host, user, passwd, database=None):
        connection = connect(
            host=host,
            user=user,
            passwd=passwd,
            database=database
        )
        return connection

connection = mysql_connection('localhost', 'root', 'Juronaty@23', 'SecurityBank')

while True :
    cpu = round(psutil.cpu_percent(interval = 1), 2)
    ram = round(psutil.virtual_memory().percent, 2)
    disco = round(psutil.disk_usage('/').percent, 2)
    data = datetime.datetime.now()

    query = '''
            INSERT INTO registro (dataHorario, dadoCaptado, fkServidor, fkBanco, fkEspecificacoes, fkComponentes, fkMetrica, fkLocacao, fkParticao) VALUES
            (%s, %s, %s, %s, %s, %s, %s, %s, %s),
            (%s, %s, %s, %s, %s, %s, %s, %s, %s),
            (%s, %s, %s, %s, %s, %s, %s, %s, %s);
    '''

    insert = [
        data, cpu, 1, 1, 1, 1, 1, 1, 1,
        data, ram, 1, 1, 1, 2, 1, 1, 1,
        data, disco, 1, 1, 1, 3, 1, 1, 1,
    ]

    cursor = connection.cursor()
    cursor.execute(query, insert)
    connection.commit()

    print(f'{cpu}')
    print(f'{ram}')
    print(f'{disco}')

    time.sleep(30)
    
cursor.close()
