package aplicacion.movil.financia

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import aplicacion.movil.financia.data.model.Conexion
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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
        val txtNombredeUsuario = findViewById<EditText>(R.id.txtNombreDeUsuario)
        val  txtCorreoElectronico = findViewById<EditText>(R.id.txtCorreoElectronico)
        val btnIngresarLogin = findViewById<Button>(R.id.btnIngresarLogin)

        btnIngresarLogin.setOnClickListener {
            val pantallaPrincipal = Intent(this, registro::class.java)
            GlobalScope.launch(Dispatchers.IO) {
                val objConexion = Conexion().cadenaConexion()
                val comprobarUsuario = objConexion?.prepareStatement("SELECT * FROM tbUsuarios WHERE correoElectronico = ? AND clave = ?")!!
                comprobarUsuario.setString(1, txtCorreoElectronico.text.toString())
                comprobarUsuario.setString(2, txtNombredeUsuario.text.toString())
                val resultado = comprobarUsuario.executeQuery()
                if (resultado.next()) {
                    startActivity(pantallaPrincipal)
                } else {
                    println("Usuario no encontrado, verifique las credenciales")
                }
            }
        }

        btnIngresarLogin.setOnClickListener {
            //Cambio de pantalla
            val pantallaRegistrarme = Intent(this, registro::class.java)
            startActivity(pantallaRegistrarme)
        }

    }
}