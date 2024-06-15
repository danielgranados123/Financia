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
        //1- Mandamos a traer a todos los elementos de la vista
        val txtNombredeUsuario = findViewById<EditText>(R.id.txtNombreDeUsuario)
        val  txtCorreoElectronico = findViewById<EditText>(R.id.txtCorreoElectronico)
        val btnIngresarLogin = findViewById<Button>(R.id.btnIngresarLogin)

        //2- Programo los botones
        btnIngresarLogin.setOnClickListener {
            //preparo el intent para cambiar a la pantalla de bienvenida
            val pantallaPrincipal = Intent(this, registro::class.java)
            //Dentro de una corrutina hago un select en la base de datos
            GlobalScope.launch(Dispatchers.IO) {
                //1-Creo un objeto de la clase conexion
                val objConexion = Conexion().cadenaConexion()
                //2- Creo una variable que contenga un PrepareStatement
                //MUCHA ATENCION! hace un select where el correo y la contraseña sean iguales a
                //los que el usuario escribe
                //Si el select encuentra un resultado es por que el usuario y contraseña si están
                //en la base de datos, si se equivoca al escribir algo, no encontrará nada el select
                val comprobarUsuario = objConexion?.prepareStatement("SELECT * FROM tbUsuarios WHERE correoElectronico = ? AND clave = ?")!!
                comprobarUsuario.setString(1, txtCorreoElectronico.text.toString())
                comprobarUsuario.setString(2, txtNombredeUsuario.text.toString())
                val resultado = comprobarUsuario.executeQuery()
                //Si encuentra un resultado
                if (resultado.next()) {
                    startActivity(pantallaPrincipal)
                } else {
                    println("Usuario no encontrado, verifique las credenciales")
                }
            }
        }

        btnIngresarLogin.setOnClickListener {
            //Cambio de pantalla
            val pantallaRegistrarme = Intent(this, registrarse::class.java)
            startActivity(pantallaRegistrarme)
        }

    }
}