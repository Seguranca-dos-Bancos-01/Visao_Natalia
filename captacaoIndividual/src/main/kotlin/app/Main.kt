package app

import RepositorioSQL


open class Main {
    companion object {
        @JvmStatic fun main(args: Array<String>) {

            Conexao.jdbcTemplate

            // chamando as funções do mysql local
            val repositorio = RepositorioSQL()
            repositorio.iniciar()


            codigoPython.captcao()

        }
    }
}