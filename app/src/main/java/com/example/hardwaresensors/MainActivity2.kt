package com.example.hardwaresensors

import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.getSystemService
import kotlinx.android.synthetic.main.activity_main2.*
import kotlin.random.Random

/*
Working with Proximity Sensor - Swipe to change color
 */

class MainActivity2 : AppCompatActivity() , SensorEventListener{

    lateinit var sensorManager: SensorManager
    lateinit var proxSensor: Sensor   //proximity sensor

    //random array of colors
    val colors = arrayOf(Color.RED, Color.GREEN, Color.BLUE, Color.CYAN, Color.MAGENTA, Color.YELLOW)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        sensorManager = getSystemService<SensorManager>()!!
        proxSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)

    }

    override fun onSensorChanged(p0: SensorEvent?) {
        if (p0!!.values[0] > 0) {    //in proximity sensor we get only one value while in accelerometer we get 3 values
            ProxIndicator.setBackgroundColor(colors[Random.nextInt(6)])
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(
            this, proxSensor, 1000 * 1000
        )
    }

    override fun onPause() {
        sensorManager.unregisterListener(this)
        super.onPause()
    }
}