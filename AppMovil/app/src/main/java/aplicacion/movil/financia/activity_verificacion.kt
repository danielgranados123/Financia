package aplicacion.movil.financia

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.sql.PreparedStatement
import java.sql.SQLException

class activity_verificacion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_verificacion)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val txtCodigo = findViewById<EditText>(R.id.txtCodigoVerificar)
        val btnVerificar = findViewById<Button>(R.id.btnVerificar)

        btnVerificar.setOnClickListener{

            val usuarioIngresado = intent.extras?.getString("usuarioIngresado").orEmpty()

            //Verifica si el codigo ingresado es el mismo al que enviamos y esta en la bd

            //Realizamos un select a la bd para ir a atraer el correo del usuario ingresado
            try{
                val traerCorreo: PreparedStatement  = conncetSql.dbConn()?.prepareStatement("select codigoRecuperacion  from tbUsuarios where usuario = ?")!!
                traerCorreo.setString(1, usuarioIngresado)
                val rs = traerCorreo.executeQuery()

                while (rs.next()){
                    CodigoBD = rs.getString("codigoRecuperacion")
                }
            }catch (ex: SQLException){
                Toast.makeText(this, "error ${ex.toString()}",Toast.LENGTH_SHORT).show()
            }
            //Comparamos lo que el usuario escribio con lo que esta en la bd
            if(txtCodigo.text.toString() == CodigoBD ){
            }else{
                Toast.makeText(this, "El codigo ingresado no coincide",Toast.LENGTH_SHORT).show()
            }
        }
    }
}