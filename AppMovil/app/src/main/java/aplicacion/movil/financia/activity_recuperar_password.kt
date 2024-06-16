package aplicacion.movil.financia

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException
import kotlin.random.Random

class activity_recuperar_password : AppCompatActivity() {

    //Creamos una estancia de la clase conexion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recuperar_password)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val btnRecuperar = findViewById<Button>(R.id.btnRecuperarContrasena)
        val txtUsuarioRecuperar = findViewById<EditText>(R.id.txtUsuarioRecuperar)
        val txtContraRecuperar = findViewById<EditText>(R.id.txtPasswordRecuperar)

        btnRecuperar.setOnClickListener{
            //Creacion de un codigo Aleatorio
            val codigoAleatorio = Random.nextInt(0,10000).toString()
            //Realizamos un select a la bd Financia para traer el correo del usuario ingresado
            try{
                val traerCorreo: PreparedStatement = connectSQL.dbConn()?.prepareStatement("select correo from tbUsuarios = ?")!!
                traerCorreo.setString(1,txtUsuarioRecuperar.text.toString())
                val rs = traerCorreo.executeQuery()

                while (rs.next()){
                    //Correo del usuario ingresado
                    correo = rs.getString("correoUsuario")
                }
            }catch (ex: SQLException){
                Toast.makeText(this,"eror ${ex.toString()}", Toast.LENGTH_SHORT).show()
            }
            //Traemos el correo con el numero aleatorio
            val EnviarCorreo = EnviarCorreo(correo, "Codigo de recuperacion", codigoAleatorio)
            EnviarCorreo.execute()

            try{
                //Ingresa el numero aleatorio a la tabla
                val addCodigo: PreparedStatement = connectSql.dbConn()?.prepareStatement("update tbUsuarios set codigoRecuperacion = ? where usuario = ?")!!
                addCodigo.setString(1, codigoAleatorio)
                addCodigo.setString(2, txtUsuarioRecuperar.text.toString())
                addCodigo.executeUpdate()
            }catch (ex: SQLException){
                Toast.makeText(this, "Error ${ex.toString()}", Toast.LENGTH_SHORT).show()
            }
            //Cambiamos de pantalla
            val intent = Intent()
         }
    }
}