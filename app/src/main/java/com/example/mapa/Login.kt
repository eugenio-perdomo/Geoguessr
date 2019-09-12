package com.example.mapa

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class Login : AppCompatActivity() {

    private lateinit var usuario: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun Jugar(v:View) {
        usuario = findViewById(R.id.username)
        val user = usuario.text.toString()
        val i = Intent(this, VistaCalle::class.java)
        i.putExtra("Usuario", user)
        i.putExtra("Contador", 0)
        startActivity(i)
    }

}
