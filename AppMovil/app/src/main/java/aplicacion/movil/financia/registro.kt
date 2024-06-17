package aplicacion.movil.financia

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import aplicacion.movil.financia.Modelo.Conexion
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class registro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val txtNombreDeUsuario = findViewById<EditText>(R.id.txtNombreDeUsuario)
        val txtContraseña = findViewById<EditText>(R.id.txtContraseña)
        val txtCorreoElectronico = findViewById<EditText>(R.id.txtCorreoElectronico)
        val btnIngresarLogin = findViewById<Button>(R.id.btnIngresarLogin)
        val btnregistrarse = findViewById<Button>(R.id.btnregistrarse)


        btnIngresarLogin.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                val objConexion = Conexion().cadenaConexion()

                val crearUsuario =
                    objConexion?.prepareStatement("INSERT INTO tbUsuarios(UUID_usuario, correoElectronico, contrasena) VALUES (?, ?, ?)")!!
                crearUsuario.setString(1, UUID.randomUUID().toString())
                crearUsuario.setString(2, txtNombreDeUsuario.text.toString())
                crearUsuario.setString(3, txtContraseña.text.toString())
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