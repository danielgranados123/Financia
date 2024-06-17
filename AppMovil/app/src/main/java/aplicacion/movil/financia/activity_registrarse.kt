package aplicacion.movil.financia

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import java.util.UUID

class activity_registrarse : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registrarse)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Se llaman todos los elementos
        val txtNombreDeUsuario = findViewById<EditText>(R.id.txtUsuarioLogin)
        val txtContraseña = findViewById<EditText>(R.id.txtContraseña)
        val txtCorreoElectronico = findViewById<EditText>(R.id.txtClaveLogin)

        val btnIngresarLogin = findViewById<Button>(R.id.btnIngresarLogin)
        val btnregistrarse = findViewById<Button>(R.id.btnregistrarse)

        //Se programa el boton para registrarse como usuario. Por defecto el rol es el de Usuario Normal
        btnIngresarLogin.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                val objConexion = ClaseConexion().cadenaConexion()

                val crearUsuario =
                    objConexion?.prepareStatement("INSERT INTO tbUsuarios(UUID_Usuario, UUID_TipoUsuario, nombreUsuario, correoUsuario, contrasenaUsuario, huellaUsuario) VALUES (?, ?, ?, ?, ?)")!!
                crearUsuario.setString(1, UUID.randomUUID().toString())
                crearUsuario.setString(2, txtNombreDeUsuario.text.toString())
                crearUsuario.setString(3, txtNombreDeUsuario.text.toString())
                crearUsuario.setString(4, txtCorreoElectronico.text.toString())
                crearUsuario.setString(5, txtContraseña.text.toString())
                crearUsuario.setString(6, txtContraseña.text.toString())
                crearUsuario.executeUpdate()

                withContext(Dispatchers.Main) {
                    txtNombreDeUsuario.setText("")
                    txtCorreoElectronico.setText("")
                    txtContraseña.setText("")
                }
            }
        }
    }
}
