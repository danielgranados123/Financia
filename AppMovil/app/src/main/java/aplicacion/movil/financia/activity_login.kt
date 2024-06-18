package aplicacion.movil.financia

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.security.MessageDigest
import modelo.ClaseConexion


class activity_login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.txtIngresarRegistrarse)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Se manda a llamar a los elementos de la activity
        val btnIngresar = findViewById<Button>(R.id.btnIngresarLogin)
        val btnRegistrar = findViewById<TextView>(R.id.txtRegistrarLogin)
        val txtUsuario = findViewById<TextView>(R.id.txtUsuarioLogin)
        val txtClave = findViewById<TextView>(R.id.txtClaveLogin)

        //Si el usuario no tiene cuenta, puede registrarse
        btnRegistrar.setOnClickListener {
            val intent = Intent(this, activity_registrarse::class.java)
            startActivity(intent)
        }

        //Creo la función para encriptar la contraseña
        fun hashSHA256(input: String): String {
            val bytes = MessageDigest.getInstance("SHA-256").digest(input.toByteArray())
            return bytes.joinToString("") { "%02x".format(it) }
        }

        btnIngresar.setOnClickListener {

            val pantallaPrincipal = Intent(this, MainActivity::class.java)

            GlobalScope.launch(Dispatchers.IO) {
                //1-Creo un objeto de la clase conexion
                val objConexion = ClaseConexion().cadenaConexion()

                //Encripto la contraseña usando la función de arriba
                val contraseniaEncriptada = hashSHA256(txtClave.text.toString())

                //Se comprueba aque el usuario existe
                val comprobarUsuario = objConexion?.prepareStatement("SELECT * FROM tbUsuarios WHERE nombreUsuario = ? AND contrasenaUsuario = ?")!!
                comprobarUsuario.setString(1, txtClave.text.toString())
                comprobarUsuario.setString(2, contraseniaEncriptada)
                val resultado = comprobarUsuario.executeQuery()

                if (resultado.next()) {
                    startActivity(pantallaPrincipal)
                } else {
                    withContext(Dispatchers.Main){
                        Toast.makeText(this@activity_login, "Usuario o contraseña incorrectos.", Toast.LENGTH_SHORT).show()
                        println("contraseña $contraseniaEncriptada")
                    }
                }
    }
        }
    }
}
