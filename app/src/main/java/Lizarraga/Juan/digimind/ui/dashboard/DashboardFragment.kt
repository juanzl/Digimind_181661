package Lizarraga.Juan.digimind.ui.dashboard

import Lizarraga.Juan.digimind.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import Lizarraga.Juan.digimind.databinding.FragmentDashboardBinding
import Lizarraga.Juan.digimind.ui.Task
import Lizarraga.Juan.digimind.ui.home.HomeFragment
import android.app.TimePickerDialog
import android.content.Context
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*
import com.google.gson.Gson

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.btnTime.setOnClickListener{
            set_time()
        }
        binding.btnSave.setOnClickListener {
            guardar()
        }

        return root
    }
        fun guardar(){
            var titulo: String = binding.etTask.text.toString()
            var tiempo: String = binding.btnTime.text.toString()

            var dia: String = ""

            if(binding.rbDay1.isChecked) dia = getString(R.string.day1)
            if(binding.rbDay2.isChecked) dia = getString(R.string.day2)
            if(binding.rbDay3.isChecked) dia = getString(R.string.day3)
            if(binding.rbDay4.isChecked) dia = getString(R.string.day4)
            if(binding.rbDay5.isChecked) dia = getString(R.string.day5)
            if(binding.rbDay6.isChecked) dia = getString(R.string.day6)
            if(binding.rbDay7.isChecked) dia = getString(R.string.day7)

            var tarea = Task(titulo, dia, tiempo)

            HomeFragment.tasks.add(tarea)
            Toast.makeText(context, "Se agrego la tarea", Toast.LENGTH_SHORT).show()

        }
    fun guardar_json(){

        val preferencias = context?.getSharedPreferences("preferencias", Context.MODE_PRIVATE)
        val editor = preferencias?.edit()

        val gson: Gson = Gson()

        var json = gson.toJson(HomeFragment.tasks)

        editor?.putString("tareas", json)
        editor?.apply()


    }

        fun set_time(){
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                binding.btnTime.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
        TimePickerDialog(context, timeSetListener, cal.get(Calendar.HOUR_OF_DAY),
        cal.get(Calendar.MINUTE), true).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}





