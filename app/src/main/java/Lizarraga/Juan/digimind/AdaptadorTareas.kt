package Lizarraga.Juan.digimind

import Lizarraga.Juan.digimind.ui.Task
import Lizarraga.Juan.digimind.ui.home.HomeFragment
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import java.util.ArrayList

class AdaptadorTareas: BaseAdapter {
    lateinit var context: Context
    var tasks: ArrayList<Task> = ArrayList<Task>()

    constructor(contexto: Context, tasks: ArrayList<Task>){
        this.context = contexto
        this.tasks = tasks

    }

    override fun getCount(): Int {
        return tasks.size
    }

    override fun getItem(p0: Int): Any {
        return tasks[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View? {
        var inflador = LayoutInflater.from(context)
        var vista = inflador.inflate(R.layout.task_view, null)

        var task = tasks[p0]

        val tv_titulo: TextView = vista.findViewById(R.id.tv_title)
        val tv_time: TextView = vista.findViewById(R.id.tv_time)
        val tv_dia: TextView = vista.findViewById(R.id.tv_days)

        tv_titulo.setText(task.title)
        tv_time.setText(task.time)
        tv_dia.setText(task.day)

        vista.setOnClickListener{
        eliminar(task)
    }
        return vista
    }
    fun eliminar (task: Task) {

        val alertDialog: AlertDialog? = context?.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setPositiveButton(R.string.ok_button,
                    DialogInterface.OnClickListener { dialog, id ->
                        // User clicked OK button
                        HomeFragment.tasks.remove(task)
                        guardar_json()
                        HomeFragment.adaptador.notifyDataSetChanged()
                        Toast.makeText(context, R.string.msg_deleted, Toast.LENGTH_SHORT).show()
                    })
                setNegativeButton(R.string.cancel_button,
                    DialogInterface.OnClickListener { dialog, id ->
                        // User cancelled the dialog
                    })
            }
            // Set other dialog properties
            builder?.setMessage(R.string.msg)
                .setTitle(R.string.title)

            // Create the AlertDialog
            builder.create()
        }
        alertDialog?.show()
    }
    fun guardar_json(){

        val preferencias = context?.getSharedPreferences("preferencias", Context.MODE_PRIVATE)
        val editor = preferencias?.edit()

        val gson: Gson = Gson()

        var json = gson.toJson(HomeFragment.tasks)

        editor?.putString("tareas", json)
        editor?.apply()


    }

}