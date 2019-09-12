package com.example.mapa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import kotlin.random.Random

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class VistaCalle : AppCompatActivity(), OnStreetViewPanoramaReadyCallback {
    private var lat = Random.nextDouble(-90.0,90.0)
    private var lon = Random.nextDouble(-180.0,180.0)
    private var usuario :String = ""
    private var contador: Int = 0
    override fun onStreetViewPanoramaReady(p0: StreetViewPanorama?) {
        p0?.setPosition(LatLng(lat,lon), 9000000, StreetViewSource.OUTDOOR)
        p0?.setOnStreetViewPanoramaChangeListener {
            val camera = StreetViewPanoramaCamera.Builder().bearing(
                p0.panoramaCamera.bearing - 180).build()
            p0.animateTo(camera,5000)
            lat = p0.location.position.latitude
            lon = p0.location.position.longitude
            Log.v("locatei", p0.location.toString())
        }
        p0?.setOnStreetViewPanoramaClickListener {
            val camera = StreetViewPanoramaCamera.Builder().bearing(
                p0.panoramaCamera.bearing + 180).build()
            p0.animateTo(camera,5000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vista_calle)
        val instancia = SupportStreetViewPanoramaFragment.newInstance()
        supportFragmentManager.beginTransaction().add(R.id.vistacallefragment,instancia).commit()
        instancia.getStreetViewPanoramaAsync(this)
        usuario = intent.getStringExtra("Usuario")
    }

    fun Guess (v: View){
        contador= intent.getIntExtra("Contador", 0)
        val i = Intent(this, MapsActivity::class.java)
        i.putExtra("latitud",lat)
        i.putExtra("longitud", lon)
        i.putExtra("Usuario", usuario)
        i.putExtra("Contador", contador)
        startActivity(i)
    }
}