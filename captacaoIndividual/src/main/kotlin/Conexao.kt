import org.apache.commons.dbcp2.BasicDataSource
import org.springframework.jdbc.core.JdbcTemplate

object Conexao {

        var jdbcTemplate: JdbcTemplate? = null
            get() {
                if (field == null) {
                    val dataSource = BasicDataSource()
                    dataSource.driverClassName = "com.mysql.cj.jdbc.Driver"
                    dataSource.url = "jdbc:mysql://localhost/SecurityBank"
                    dataSource.username = "root"
                    dataSource.password = "urubu100"
                    val novoJdbcTemplate = JdbcTemplate(dataSource)
                    field = novoJdbcTemplate
                }
                return field
            }

        }