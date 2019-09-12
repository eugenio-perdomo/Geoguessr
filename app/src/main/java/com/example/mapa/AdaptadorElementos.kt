package com.example.mapa

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kotlinx.android.synthetic.main.mostrar_elemento.view.*

class AdaptadorElementos (private val context: Context, private val lista:List<String>)
        : RecyclerView.Adapter<ViewHolderElemento> () {
    var cont:Int = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderElemento {

        return ViewHolderElemento(LayoutInflater.from(context)
            .inflate(R.layout.mostrar_elemento,parent,false))
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: ViewHolderElemento, position: Int) {
        cont++
        Log.v("size", "ahora$cont")
        holder.elem.text = lista[position]
    }
}

class ViewHolderElemento(view: View) : ViewHolder(view),View.OnClickListener {
    init {
        view.setOnClickListener(this)
    }
    override fun onClick(p0: View?) {
        println( p0?.tv_elemento?.text)
    }
    val elem = view.tv_elemento
}
