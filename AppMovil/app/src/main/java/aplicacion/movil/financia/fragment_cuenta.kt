package aplicacion.movil.financia

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import modelo.dataClassUsuarios
import java.util.UUID


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [fragment_cuenta.newInstance] factory method to
 * create an instance of this fragment.
 */
class fragment_cuenta : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_cuenta, container, false)

        val root = inflater.inflate(R.layout.fragment_cuenta, container, false)

        //Se manda a llamar a los elementos
        val regresarAjustes = root.findViewById<ImageView>(R.id.imgRegresar)
        val imgNombreUsuario = root.findViewById<ImageView>(R.id.imgEditarUsernameCuenta)
        val imgCorreoUsuario = root.findViewById<ImageView>(R.id.imgEditarEmailCuenta)
        val imgClaveUsuario = root.findViewById<ImageView>(R.id.imgEditarPasswordCuenta)
        val imgHuellaUsuario = root.findViewById<ImageView>(R.id.imgEditarHuellaCuenta)


        regresarAjustes.setOnClickListener{
            val intent = Intent(requireContext(), fragment_ajustes::class.java)
            startActivity(intent)
        }

        imgNombreUsuario.setOnClickListener {
            val context = requireContext()

            // Crear la alerta
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Editar nombre")

            // Agregar un cuadro de texto para que el usuario pueda escribir un nuevo nombre
            val nuevoNombre = EditText(context)
            nuevoNombre.hint = "Nuevo nombre"
            builder.setView(nuevoNombre)

            builder.setPositiveButton("Actualizar") { dialog, which ->
                actualizarNombreUsuario(nuevoNombre.text.toString(), "UUID_DEL_PRODUCTO")
            }

            builder.setNegativeButton("Cancelar") { dialog, which ->
                dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()
        }

        imgCorreoUsuario.setOnClickListener {
            val context = requireContext()

            // Crear la alerta
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Editar correo")

            // Agregar un cuadro de texto para que el usuario pueda escribir un nuevo nombre
            val nuevaClave = EditText(context)
            nuevaClave.hint = "Nuevo correo"
            builder.setView(nuevaClave)

            builder.setPositiveButton("Actualizar") { dialog, which ->
                actualizarCorreoUsuario(nuevaClave.text.toString(), "UUID_DEL_PRODUCTO")
            }

            builder.setNegativeButton("Cancelar") { dialog, which ->
                dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()
        }

        imgClaveUsuario.setOnClickListener {
            val context = requireContext()

            // Crear la alerta
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Editar contrasena")

            // Agregar un cuadro de texto para que el usuario pueda escribir un nuevo nombre
            val nuevaClave = EditText(context)
            nuevaClave.hint = "Nueva contrasena"
            builder.setView(nuevaClave)

            builder.setPositiveButton("Actualizar") { dialog, which ->
                actualizarClaveUsuario(nuevaClave.text.toString(), "UUID_DEL_PRODUCTO")
            }

            builder.setNegativeButton("Cancelar") { dialog, which ->
                dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()
        }

        imgHuellaUsuario.setOnClickListener {
            val context = requireContext()

            // Crear la alerta
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Editar huella")

            // Agregar un cuadro de texto para que el usuario pueda escribir un nuevo nombre
            val nuevaHuella = EditText(context)
            nuevaHuella.hint = "Nueva huella"
            builder.setView(nuevaHuella)

            builder.setPositiveButton("Actualizar") { dialog, which ->
                actualizarHuellaUsuario(nuevaHuella.text.toString(), "UUID_DEL_PRODUCTO")
            }

            builder.setNegativeButton("Cancelar") { dialog, which ->
                dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()
        }



        return root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment fragment_cuenta.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            fragment_cuenta().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


    fun actualizarNombreUsuario(UUID_Usuario:String,nombreUsuario: String){
        //crear una corrutina
        GlobalScope.launch(Dispatchers.IO) {
            //creo un objeto de la clase conexion
            val objConexion = ClaseConexion().cadenaConexion()
            //variable que contenga prepared sttement
            val updateNombreUsuario =
                objConexion?.prepareStatement("update tbUsuarios set nombreUsuario = ? where UUID_Usuario = ?")!!

            updateNombreUsuario.setString(1, UUID_Usuario)
            updateNombreUsuario.setString(2, nombreUsuario)
            updateNombreUsuario.executeUpdate()

            val commit = objConexion.prepareStatement("commit")
            commit.executeUpdate()

        }
    }

    fun actualizarCorreoUsuario(UUID_Usuario:String,correoUsuario: String){
        //crear una corrutina
        GlobalScope.launch(Dispatchers.IO) {
            //creo un objeto de la clase conexion
            val objConexion = ClaseConexion().cadenaConexion()
            //variable que contenga prepared sttement
            val updateCorreoUsuario =
                objConexion?.prepareStatement("update tbUsuarios set correoUsuario = ? where UUID_Usuario = ?")!!

            updateCorreoUsuario.setString(1, UUID_Usuario)
            updateCorreoUsuario.setString(2, correoUsuario)
            updateCorreoUsuario.executeUpdate()

            val commit = objConexion.prepareStatement("commit")
            commit.executeUpdate()

        }
    }

    fun actualizarClaveUsuario(UUID_Usuario:String,contrasenaUsuario: String){
        //crear una corrutina
        GlobalScope.launch(Dispatchers.IO) {
            //creo un objeto de la clase conexion
            val objConexion = ClaseConexion().cadenaConexion()
            //variable que contenga prepared sttement
            val updateClaveUsuario =
                objConexion?.prepareStatement("update tbUsuarios set contrasenaUsuario = ? where UUID_Usuario = ?")!!

            updateClaveUsuario.setString(1, UUID_Usuario)
            updateClaveUsuario.setString(2, contrasenaUsuario)
            updateClaveUsuario.executeUpdate()

            val commit = objConexion.prepareStatement("commit")
            commit.executeUpdate()

        }
    }

    fun actualizarHuellaUsuario(UUID_Usuario:String,huellaUsuario: String){
        //crear una corrutina
        GlobalScope.launch(Dispatchers.IO) {
            //creo un objeto de la clase conexion
            val objConexion = ClaseConexion().cadenaConexion()
            //variable que contenga prepared sttement
            val updateHuellaUsuario =
                objConexion?.prepareStatement("update tbUsuarios set huellaUsuario = ? where UUID_Usuario = ?")!!

            updateHuellaUsuario.setString(1, UUID_Usuario)
            updateHuellaUsuario.setString(2, huellaUsuario)
            updateHuellaUsuario.executeUpdate()

            val commit = objConexion.prepareStatement("commit")
            commit.executeUpdate()

        }
    }
}


