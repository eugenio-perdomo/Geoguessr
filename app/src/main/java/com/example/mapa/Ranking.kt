package com.example.mapa

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class Ranking : AppCompatActivity() {
    private var bd: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var puntos : Int = 0
    private var usuario :String = ""
    private var contador: Int = 0
    private var elementoLista: String = ""
    private var myList: MutableList<String> = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ranking)
        usuario = intent.getStringExtra("Usuario")
        findViewById<TextView>(R.id.tv_ranking).text = usuario
        puntos =intent.getDoubleExtra("Puntos", 0.0).toInt()
        contador= intent.getIntExtra("Contador", 0)

        bd.collection("usuario").orderBy("puntos", Query.Direction.DESCENDING).limit(5).get().addOnSuccessListener { documents ->
            for (document in documents) {
                Log.v("LLegaron los puntos", "${document.id} => ${document.data}")
                elementoLista = document.getString("nombre")+ " " + document.getLong("puntos").toString()
                myList.add(elementoLista)
            }
            val rView = findViewById<RecyclerView>(R.id.rv_lista)
            val l:RecyclerView.LayoutManager = LinearLayoutManager(this.baseContext)
            val ada = AdaptadorElementos(this.baseContext,myList)
            rView.adapter = ada
            rView.layoutManager = l
        }.addOnFailureListener { exception ->
            Log.w("No llegaron", "Error getting documents: ", exception)
        }
        contador++
        if(contador == 5) { // Esto se va enseguida, en vez de mostrar el ranking nro 5
            volverAlInicio()
        }
    }

    fun Jugar(v: View) {
        val i = Intent(this, VistaCalle::class.java)
        i.putExtra("Usuario", usuario)
        i.putExtra("Contador", contador)
        startActivity(i)
    }
    fun volverAlInicio() {
        val i = Intent(this, Login::class.java)
        startActivity(i)
    }
}
