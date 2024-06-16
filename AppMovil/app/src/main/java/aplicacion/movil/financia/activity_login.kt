package aplicacion.movil.financia

import android.content.Intent
import android.os.Bundle
<<<<<<< HEAD
import android.widget.Toast
=======
import android.widget.Button
import android.widget.EditText
>>>>>>> e8a388b672f1665f9b39a255be4597736f87b7b6
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
<<<<<<< HEAD
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.security.MessageDigest
import java.util.UUID
=======
import aplicacion.movil.financia.data.model.Conexion
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
>>>>>>> e8a388b672f1665f9b39a255be4597736f87b7b6

class activity_login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
<<<<<<< HEAD

        //Creo la función para encriptar la contraseña
        fun hashSHA256(input: String): String {
            val bytes = MessageDigest.getInstance("SHA-256").digest(input.toByteArray())
            return bytes.joinToString("") { "%02x".format(it) }
        }
        btnIngresar.setOnClickListener {
            //preparo el intent para cambiar a la pantalla de bienvenida
            val pantallaPrincipal = Intent(this, MainActivity::class.java)
            //Dentro de una corrutina hago un select en la base de datos
            GlobalScope.launch(Dispatchers.IO) {
                //1-Creo un objeto de la clase conexion
                val objConexion = ClaseConexion().cadenaConexion()

                //Encripto la contraseña usando la función de arriba
                val contraseniaEncriptada = hashSHA256(txtContrasenia.text.toString())


                //2- Creo una variable que contenga un PrepareStatement
                //MUCHA ATENCION! hace un select where el correo y la contraseña sean iguales a
                //los que el usuario escribe
                //Si el select encuentra un resultado es por que el usuario y contraseña si están
                //en la base de datos, si se equivoca al escribir algo, no encontrará nada el select
                val comprobarUsuario = objConexion?.prepareStatement("SELECT * FROM tbUsuarios WHERE correoUsuario = ? AND contrasenaUsuario = ?")!!
                comprobarUsuario.setString(1, txtCorreo.text.toString())
                comprobarUsuario.setString(2, contraseniaEncriptada)
                val resultado = comprobarUsuario.executeQuery()
                //Si encuentra un resultado
                if (resultado.next()) {
                    startActivity(pantallaPrincipal)
                } else {
                    withContext(Dispatchers.Main){
                        Toast.makeText(this@Login, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                        println("contraseña $contraseniaEncriptada")
                    }

=======
        val txtNombredeUsuario = findViewById<EditText>(R.id.txtNombreDeUsuario)
        val  txtCorreoElectronico = findViewById<EditText>(R.id.txtCorreoElectronico)
        val btnIngresarLogin = findViewById<Button>(R.id.btnIngresarLogin)

        btnIngresarLogin.setOnClickListener {
            val pantallaPrincipal = Intent(this, registro::class.java)
            GlobalScope.launch(Dispatchers.IO) {
                val objConexion = Conexion().cadenaConexion()
                val comprobarUsuario = objConexion?.prepareStatement("SELECT * FROM tbUsuarios WHERE corrreoUsuario = ? AND clave = ?")!!
                comprobarUsuario.setString(1, txtCorreoElectronico.text.toString())
                comprobarUsuario.setString(2, txtNombredeUsuario.text.toString())
                val resultado = comprobarUsuario.executeQuery()
                if (resultado.next()) {
                    startActivity(pantallaPrincipal)
                } else {
                    println("Usuario no encontrado, verifique las credenciales")
>>>>>>> e8a388b672f1665f9b39a255be4597736f87b7b6
                }
            }
        }

<<<<<<< HEAD
=======
        btnIngresarLogin.setOnClickListener {
            //Cambio de pantalla
            val pantallaRegistrarme = Intent(this, registro::class.java)
            startActivity(pantallaRegistrarme)
        }
>>>>>>> e8a388b672f1665f9b39a255be4597736f87b7b6

    }
}