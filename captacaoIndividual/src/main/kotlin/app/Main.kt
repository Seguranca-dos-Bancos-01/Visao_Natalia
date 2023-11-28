package app

import Repositorio


open class Main {
    companion object {
        @JvmStatic fun main(args: Array<String>) {

            // Colocando a conexão por meio do mysql local
            Conexao.jdbcTemplate

            // chamando as funções do mysql local
            val repositorio = Repositorio()
            repositorio.iniciar()


            codigoPython.captcao()

        }
    }
}