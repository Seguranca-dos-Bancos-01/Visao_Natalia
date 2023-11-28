import Conexao.jdbcTemplate
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.PreparedStatementCallback
import java.util.*

class Repositorio {

    lateinit var jdbcTemplate: JdbcTemplate

    var banco :Int = 0;
    var servidor :Int = 0;
    var especificacao :Int = 0;
    var componenteCPU :Int = 0;
    var componenteRAM :Int = 0;
    var componenteDISCO :Int = 0;
    var metricaCPU :Int = 0;
    var metricaRAM :Int = 0;
    var metricaDISCO :Int = 0;
    var plano :Int = 0;
    var particao :Int = 0;

    fun iniciar() {
        jdbcTemplate = Conexao.jdbcTemplate!!

    }
    fun verificarUsuario() {
        val scanner = Scanner(System.`in`)

        println("Bem-Vindo(a) a API SecurityBank")
        println("Iniciando validação das suas credenciais...")
        println("Insira o email de acesso: ")
        val email = scanner.nextLine()
        println("Insira o senha de acesso: ")
        val senha = scanner.nextLine()

        println("Buscando os dados... ")

        val query = "SELECT COUNT(*) FROM funcionarios WHERE email = ? AND senha = ?"

        val opcao = jdbcTemplate.execute(query, PreparedStatementCallback { preparedStatement ->
            preparedStatement.setString(1, email)
            preparedStatement.setString(2, senha)
            val resultSet = preparedStatement.executeQuery()
            resultSet.next()
            resultSet.getInt(1)
        })

        if(opcao != null) {

          if(opcao > 0) {
              val selectNome = "SELECT nome FROM funcionarios WHERE email = ? AND senha = ?"
              val nome = jdbcTemplate.execute(selectNome, PreparedStatementCallback { preparedStatement ->
                  preparedStatement.setString(1, email)
                  preparedStatement.setString(2, senha)
                  val nomeResultSet = preparedStatement.executeQuery()
                  nomeResultSet.next()
                  nomeResultSet.getString("nome")
              })

              println("Olá $nome, vamos iniciar as operações!")

              val selectBanco = "SELECT fkBanco FROM funcionarios WHERE email = ? AND senha = ?"
              banco = jdbcTemplate.execute(selectBanco, PreparedStatementCallback { preparedStatement ->
                  preparedStatement.setString(1, email)
                  preparedStatement.setString(2, senha)
                  val resultSet = preparedStatement.executeQuery()
                  resultSet.next()
                  resultSet.getInt("fkBanco")
              })

              val selectServidoresCadastrados = "select servidor.idServidor, servidor.apelido from servidor join banco on fkBanco = idBanco where fkBanco = ${banco};"

              println("Servidores cadastrados na sua empresa:")

              val servidoresCadastrados = jdbcTemplate.query(selectServidoresCadastrados) { resultSet, _ ->
                  val idServidor = resultSet.getInt("idServidor")
                  val apelidoServidor = resultSet.getString("apelido")

                  println("Servidor: $apelidoServidor com código $idServidor")
              }


              println("Insira o código do servidor selecionado para captar dados:")
              servidor = scanner.nextInt()

              println("Servidor $servidor selecionado com sucesso!")

              val selectEspecificacoes = "select servidor.fkEspecificacoes from servidor where idServidor = ?;"
              especificacao = jdbcTemplate.execute(selectEspecificacoes, PreparedStatementCallback { preparedStatement ->
                  preparedStatement.setInt(1, servidor)
                  val resultSet = preparedStatement.executeQuery()
                  resultSet.next()
                  resultSet.getInt("fkEspecificacoes")
              })

              val selectComponenteCPU = "SELECT componentes.idComponentes FROM componentes JOIN servidor ON componentes.fkServidor = servidor.idServidor WHERE modelo = 'cpu' AND servidor.idServidor = ?;"
              componenteCPU = jdbcTemplate.execute(selectComponenteCPU, PreparedStatementCallback { preparedStatement ->
                  preparedStatement.setInt(1, servidor)
                  val resultSet = preparedStatement.executeQuery()
                  resultSet.next()
                  resultSet.getInt("idComponentes")
              })

              val selectComponenteRAM = "select componentes.idComponentes from componentes where fkServidor = ? and modelo = 'ram';"
              componenteRAM = jdbcTemplate.execute(selectComponenteRAM, PreparedStatementCallback { preparedStatement ->
                  preparedStatement.setInt(1, servidor)
                  val resultSet = preparedStatement.executeQuery()
                  resultSet.next()
                  resultSet.getInt("idComponentes")
              })

              val selectComponenteDISCO = "select componentes.idComponentes from componentes where fkServidor = ? and modelo = 'disco';"
              componenteDISCO = jdbcTemplate.execute(selectComponenteDISCO, PreparedStatementCallback { preparedStatement ->
                  preparedStatement.setInt(1, servidor)
                  val resultSet = preparedStatement.executeQuery()
                  resultSet.next()
                  resultSet.getInt("idComponentes")
              })

              val selectMetricaCPU = "select componentes.fkMetrica from componentes where fkServidor = ? and modelo = 'cpu';"
              metricaCPU = jdbcTemplate.execute(selectMetricaCPU, PreparedStatementCallback { preparedStatement ->
                  preparedStatement.setInt(1, servidor)
                  val resultSet = preparedStatement.executeQuery()
                  resultSet.next()
                  resultSet.getInt("fkMetrica")
              })

              val selectMetricaRAM = "select componentes.fkMetrica from componentes where fkServidor = ? and modelo = 'ram';"
              metricaRAM = jdbcTemplate.execute(selectMetricaRAM, PreparedStatementCallback { preparedStatement ->
                  preparedStatement.setInt(1, servidor)
                  val resultSet = preparedStatement.executeQuery()
                  resultSet.next()
                  resultSet.getInt("fkMetrica")
              })

              val selectMetricaDISCO = "select componentes.fkMetrica from componentes where fkServidor = ? and modelo = 'disco';"
              metricaDISCO = jdbcTemplate.execute(selectMetricaDISCO, PreparedStatementCallback { preparedStatement ->
                  preparedStatement.setInt(1, servidor)
                  val resultSet = preparedStatement.executeQuery()
                  resultSet.next()
                  resultSet.getInt("fkMetrica")
              })

              val selectPlano = "select fkPlano from servidor where idServidor = ?;"
              plano = jdbcTemplate.execute(selectPlano, PreparedStatementCallback { preparedStatement ->
                  preparedStatement.setInt(1, servidor)
                  val resultSet = preparedStatement.executeQuery()
                  resultSet.next()
                  resultSet.getInt("fkPlano")
              })

              particao = 2

              println("Captura foi inicializada! Verifique sua dashboard!")

          } else {
              println("Não encontramos os dados inseridos. Verifique os os dados e tente novamente.")
          }
        }
    }
}