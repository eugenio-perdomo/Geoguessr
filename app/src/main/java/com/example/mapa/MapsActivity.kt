package com.example.mapa

import android.content.Intent
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.firebase.firestore.FirebaseFirestore

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private var distancia: Float = 0.0f
    private var usuario :String = ""
    var bd: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val posicion = 1
    private var puntos: Double = 0.0
    var flag: Boolean = true
    private var contador: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        usuario = intent.getStringExtra("Usuario")
    }

    override fun onMapReady(googleMap: GoogleMap) {

        mMap = googleMap
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(50.0, 50.0), 3f))
        val lat  = intent.getDoubleExtra("latitud",0.0)
        val lon  = intent.getDoubleExtra("longitud",0.0)
        val mapita = LatLng(lat, lon)

        mMap.setOnMapLongClickListener {
            //No permite seguir clickeando
            if(flag){
                mMap.addMarker(MarkerOptions().position(it).title("Apretaste Aca"))
                flag = false
                val loc1 = Location("A")
                loc1.latitude = lat
                loc1.longitude = lon
                val loc2 = Location("B")
                loc2.latitude = it.latitude
                loc2.longitude = it.longitude
                distancia = loc1.distanceTo(loc2)
                distancia /= 1000
                puntos = distancia.toDouble()/100

                //Añade marcador objetivo
                mMap.addMarker(MarkerOptions().position(mapita).title("Distancia: " + distancia.toShort() + "Km, Puntaje: " + puntos.toInt()))
                val dataJugador = hashMapOf(
                    "puntos" to puntos.toInt()
                )
                //Escribe los puntos
                bd.collection("usuario").add(dataJugador as Map<String, Any>).addOnSuccessListener {
                        Log.d("Existe", "DocumentSnapshot successfully written!")
                    }
                    .addOnFailureListener { e -> Log.w("No EXiste", "Error writing document", e) }

                mMap.addPolyline(
                    PolylineOptions().clickable(false).add(LatLng(lat,lon),
                        LatLng(it.latitude,it.longitude))
                )
            }
        }
    }
    fun irRanking(v: View){
        contador= intent.getIntExtra("Contador", 0)
        val i = Intent(this, Ranking::class.java)
        i.putExtra("Usuario", usuario)
        i.putExtra("Puntos", puntos)
        i.putExtra("Posicion", posicion)
        i.putExtra("distancia", distancia)
        i.putExtra("Contador", contador)
        startActivity(i)
    }
}
