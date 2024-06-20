package aplicacion.movil.financia.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import aplicacion.movil.financia.R
import aplicacion.movil.financia.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        //Se manda a llamar a los elementos de la activity
        val btnNuevoHome = root.findViewById<ImageView>(R.id.btnNuevoHome)
        val btnNuevoIngresoHome = root.findViewById<Button>(R.id.btnNuevoIngresoHome)
        val btnNuevoGastoHome = root.findViewById<Button>(R.id.btnNuevoGastoHome)


        //Se configura el botón para mostrar/ocultar los botones
        btnNuevoHome.setOnClickListener {
            if (btnNuevoIngresoHome.visibility == View.INVISIBLE) {
                btnNuevoIngresoHome.visibility = View.VISIBLE
                btnNuevoGastoHome.visibility = View.VISIBLE
            } else {
                btnNuevoIngresoHome.visibility = View.INVISIBLE
                btnNuevoGastoHome.visibility = View.INVISIBLE
            }
        }

        //Si no se presiona nada, los botones deben estar invisibles
        btnNuevoIngresoHome.visibility = View.INVISIBLE
        btnNuevoGastoHome.visibility = View.INVISIBLE

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

