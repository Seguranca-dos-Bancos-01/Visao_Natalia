import org.apache.commons.dbcp2.BasicDataSource
import org.springframework.jdbc.core.JdbcTemplate

object ConexaoSQL {
    var jdbcTemplate: JdbcTemplate? = null
        get() {
            if (field == null) {
                val dataSource = BasicDataSource()
                dataSource.driverClassName = "com.microsoft.sqlserver.jdbc.SQLServerDriver"
                val serverName = "34.206.192.7"
                val dataBase = "SecurityBank"
                dataSource.url = "jdbc:sqlserver://$serverName;database=$dataBase;encrypt=true;trustServerCertificate=true"
                dataSource.username = "sa"
                dataSource.password = "UrubuDoGit123"
                val novoJdbcTemplate = JdbcTemplate(dataSource)
                field = novoJdbcTemplate
            }
            return field
        }

}
