package aplicacion.movil.financia.data.model

import java.sql.Connection
import java.sql.DriverManager

class Conexion {
    fun cadenaConexion(): Connection?{
        try {
            val ip = "jdbc:oracle:thin:@192.168.1.18:1521:xe"
            val usuario = "system"
            val contrasena = "itr2023"

            val conexion = DriverManager.getConnection(ip, usuario, contrasena)
            return conexion
        }catch (e: Exception){
            println("Este es el error: $e")
            return null
        }
    }

}