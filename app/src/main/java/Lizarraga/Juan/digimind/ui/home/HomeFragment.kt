package Lizarraga.Juan.digimind.ui.home

import Lizarraga.Juan.digimind.AdaptadorTareas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import Lizarraga.Juan.digimind.databinding.FragmentHomeBinding
import Lizarraga.Juan.digimind.ui.Task
import android.content.Context
import android.widget.GridView

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.ArrayList

class HomeFragment : Fragment() {


    private var _binding: FragmentHomeBinding? = null

    companion object {
        var tasks: ArrayList<Task> = ArrayList<Task>()
        var first = true
        lateinit var adaptador: AdaptadorTareas
    }
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

        val gridView: GridView = binding.gridview

//        if(first){
//            fill_tasks()
//            first = false
//
//        }

        cargar_tareas()
        fill_tasks()

        adaptador = AdaptadorTareas(root.context, tasks)

        gridView.adapter = adaptador

        return root
    }
    fun fill_tasks(){

        tasks.add(Task("tarea 1", "Lunes","15.00"))
        tasks.add(Task("tarea 2", "Lunes","15.00"))
        tasks.add(Task("tarea 3", "Lunes","15.00"))
        tasks.add(Task("tarea 4", "Lunes","15.00"))
        tasks.add(Task("tarea 5", "Lunes","15.00"))
    }

    fun cargar_tareas(){

        val preferencias = context?.getSharedPreferences("preferencias", Context.MODE_PRIVATE)
        val gson: Gson = Gson()

        var json = preferencias?.getString("tareas", null)

        val type = object : TypeToken<ArrayList<Task?>?>() {}.type

        if(json == null) {
            tasks = ArrayList<Task>()
        }else{
            tasks = gson.fromJson(json, type)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}