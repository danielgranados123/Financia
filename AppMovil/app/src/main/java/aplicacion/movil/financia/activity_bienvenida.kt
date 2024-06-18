package aplicacion.movil.financia

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class activity_bienvenida : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_bienvenidaa)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.txtIngresarRegistrarse)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Se llaman los elementos de la activity
        val btnIngresar = findViewById<Button>(R.id.btnIngresarBienvenida)
        val btnRegistrarse = findViewById<Button>(R.id.btbtnRegistrarBienvenida)

        //Se programa el boton para que mande a la interfaz de inicio de sesion
        btnIngresar.setOnClickListener{
            val intent = Intent(this, activity_login::class.java)
            startActivity(intent)
        }

        //Se programa el boton para que mande a la interfaz de registro
        btnRegistrarse.setOnClickListener{
            val intent = Intent(this, activity_registrarse::class.java)
            startActivity(intent)
        }

    }
}