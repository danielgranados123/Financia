package aplicacion.movil.financia

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import java.security.MessageDigest
import java.util.UUID

class activity_registrarse : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registrarse)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.txtIngresarRegistrarse)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Se llaman todos los elementos
        val txtNombreDeUsuario = findViewById<EditText>(R.id.txtUsuarioLogin)
        val txtContrasena = findViewById<EditText>(R.id.txtContraseña)
        val txtCorreoElectronico = findViewById<EditText>(R.id.txtClaveLogin)

        val txtIngresar = findViewById<TextView>(R.id.txtIngresarRegistrarse)
        val btnregistrarse = findViewById<Button>(R.id.btnregistrarseRegistrarse)

        txtIngresar.setOnClickListener {
                val intent = Intent(this, activity_login::class.java)
                startActivity(intent)
        }

        //Funcion para encriptar la contraseña
        fun hashSHA256(input: String): String {
            val bytes = MessageDigest.getInstance("SHA-256").digest(input.toByteArray())
            return bytes.joinToString("") { "%02x".format(it) }
        }


        //Se programa el boton para registrarse como usuario. Por defecto el rol es el de Usuario Normal
        btnregistrarse.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                val objConexion = ClaseConexion().cadenaConexion()

                val contraseniaEncriptada = hashSHA256(txtContrasena.text.toString())

                val crearUsuario =
                    objConexion?.prepareStatement("INSERT INTO tbUsuarios(UUID_Usuario, UUID_TipoUsuario, nombreUsuario, correoUsuario, contrasenaUsuario, huellaUsuario) VALUES (?, ?, ?, ?, ?)")!!
                crearUsuario.setString(1, UUID.randomUUID().toString())
                crearUsuario.setString(2, txtNombreDeUsuario.text.toString())
                crearUsuario.setString(3, txtNombreDeUsuario.text.toString())
                crearUsuario.setString(4, txtCorreoElectronico.text.toString())
                crearUsuario.setString(5, contraseniaEncriptada)
                crearUsuario.setString(6, txtNombreDeUsuario.text.toString())
                crearUsuario.executeUpdate()

                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@activity_registrarse,
                        "Usuario creado con éxito",
                        Toast.LENGTH_SHORT
                    ).show()

                    txtNombreDeUsuario.setText("")
                    txtCorreoElectronico.setText("")
                    txtContrasena.setText("")
                }

            }
        }
    }
}
