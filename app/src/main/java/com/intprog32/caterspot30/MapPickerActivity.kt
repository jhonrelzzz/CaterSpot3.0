/*
package com.intprog32.caterspot30

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapPickerActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private var selectedMarker: Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_picker)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val defaultLocation = LatLng(14.5995, 120.9842) // Manila
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 15f))

        selectedMarker = mMap.addMarker(
            MarkerOptions().position(defaultLocation).title("Selected Location").draggable(true)
        )

        mMap.setOnMarkerDragListener(object : GoogleMap.OnMarkerDragListener {
            override fun onMarkerDragStart(marker: Marker) {}

            override fun onMarkerDrag(marker: Marker) {}

            override fun onMarkerDragEnd(marker: Marker) {
                val pos = marker.position
                selectedMarker?.position = pos
                Toast.makeText(this@MapPickerActivity, "Selected: ${pos.latitude}, ${pos.longitude}", Toast.LENGTH_SHORT).show()

                // Return result to BookingActivity
                val resultIntent = Intent()
                resultIntent.putExtra("latitude", pos.latitude)
                resultIntent.putExtra("longitude", pos.longitude)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        })
    }
}
*/
