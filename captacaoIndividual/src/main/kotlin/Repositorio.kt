import Conexao.jdbcTemplate
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.PreparedStatementCallback
import java.util.*

class Repositorio {

    lateinit var jdbcTemplate: JdbcTemplate

    var banco = 0;
    var servidor = 0;
    var especificacao = 0;
    var componenteCPU = 0;
    var componenteRAM = 0;
    var componenteDISCO = 0;
    var metricaCPU = 0;
    var metricaRAM = 0;
    var metricaDISCO = 0;
    var locacao = 0;
    var particao = 0;

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

              val selectComponenteCPU = "SELECT componente.idComponentes FROM componente JOIN servidor ON componente.servidor_idServidor = servidor.idServidor WHERE modelo = 'cpu' AND servidor.idServidor = ?;"
              componenteCPU = jdbcTemplate.execute(selectComponenteCPU, PreparedStatementCallback { preparedStatement ->
                  preparedStatement.setInt(1, servidor)
                  val resultSet = preparedStatement.executeQuery()
                  resultSet.next()
                  resultSet.getInt("idComponentes")
              })

              val selectComponenteRAM = "select componente.idComponentes from componente where servidor_idServidor = ? and modelo = \"ram\";"
              componenteRAM = jdbcTemplate.execute(selectComponenteRAM, PreparedStatementCallback { preparedStatement ->
                  preparedStatement.setInt(1, servidor)
                  val resultSet = preparedStatement.executeQuery()
                  resultSet.next()
                  resultSet.getInt("idComponentes")
              })

              val selectComponenteDISCO = "select componente.idComponentes from componente where servidor_idServidor = ? and modelo = \"disco\";"
              componenteDISCO = jdbcTemplate.execute(selectComponenteDISCO, PreparedStatementCallback { preparedStatement ->
                  preparedStatement.setInt(1, servidor)
                  val resultSet = preparedStatement.executeQuery()
                  resultSet.next()
                  resultSet.getInt("idComponentes")
              })

              val selectMetricaCPU = "select componente.fkMetrica from componente where servidor_idServidor = ? and modelo = \"cpu\";"
              metricaCPU = jdbcTemplate.execute(selectMetricaCPU, PreparedStatementCallback { preparedStatement ->
                  preparedStatement.setInt(1, servidor)
                  val resultSet = preparedStatement.executeQuery()
                  resultSet.next()
                  resultSet.getInt("fkMetrica")
              })

              val selectMetricaRAM = "select componente.fkMetrica from componente where servidor_idServidor = ? and modelo = \"ram\";"
              metricaRAM = jdbcTemplate.execute(selectMetricaRAM, PreparedStatementCallback { preparedStatement ->
                  preparedStatement.setInt(1, servidor)
                  val resultSet = preparedStatement.executeQuery()
                  resultSet.next()
                  resultSet.getInt("fkMetrica")
              })

              val selectMetricaDISCO = "select componente.fkMetrica from componente where servidor_idServidor = ? and modelo = \"disco\";"
              metricaDISCO = jdbcTemplate.execute(selectMetricaDISCO, PreparedStatementCallback { preparedStatement ->
                  preparedStatement.setInt(1, servidor)
                  val resultSet = preparedStatement.executeQuery()
                  resultSet.next()
                  resultSet.getInt("fkMetrica")
              })

              locacao = 1
              particao = 1

              println("Captura foi inicializada! Verifique sua dashboard!")

          } else {
              println("Não encontramos os dados inseridos. Verifique os os dados e tente novamente.")
          }
        }
    }
}