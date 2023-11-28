import java.io.File

object codigoPython {

    fun captcao() {

        val fks = Repositorio()

        fks.iniciar()
        fks.verificarUsuario()

        val banco = fks.banco
        val servidor = fks.servidor
        val especificacao = fks.especificacao
        val componenteCPU = fks.componenteCPU
        val componenteRAM = fks.componenteRAM
        val componenteDISCO = fks.componenteDISCO
        val metricaCPU = fks.metricaCPU
        val metricaRAM = fks.metricaRAM
        val metricaDISCO = fks.metricaDISCO
        val plano = fks.plano
        val particao = fks.particao


        println(banco)
        println(servidor)
        println(especificacao)
        println(componenteCPU)
        println(componenteRAM)
        println(componenteDISCO)
        println(metricaCPU)
        println(metricaRAM)
        println(metricaDISCO)
        println(plano)
        println(particao)

        val codigo = """
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

connection = mysql_connection('localhost', 'root', 'Juronaty@23', 'SecurityBank')

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
        data, cpu, ${servidor}, ${banco}, ${especificacao}, ${componenteCPU}, ${metricaCPU}, ${plano}, ${particao},
        data, ram, ${servidor}, ${banco}, ${especificacao}, ${componenteRAM}, ${metricaRAM}, ${plano}, ${particao},
        data, disco, ${servidor}, ${banco}, ${especificacao}, ${componenteDISCO}, ${metricaDISCO}, ${plano}, ${particao},
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
        data, cpu, ${servidor}, ${banco}, ${especificacao}, ${componenteCPU}, ${metricaCPU}, ${plano}, ${particao},
        data, ram, ${servidor}, ${banco}, ${especificacao}, ${componenteRAM}, ${metricaRAM}, ${plano}, ${particao},
        data, disco, ${servidor}, ${banco}, ${especificacao}, ${componenteDISCO}, ${metricaDISCO}, ${plano}, ${particao},
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
"""

        val nomeArquivo = "CaptacaoNatalia.py"

        File(nomeArquivo).writeText(codigo)

        Runtime.getRuntime().exec("py $nomeArquivo")
}


}