package com.example.hardwaresensors

import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.getSystemService
import kotlinx.android.synthetic.main.activity_main3.*
import kotlin.math.roundToInt

/*
Working with Accelerometer Sensor
 */

class MainActivity3 : AppCompatActivity(), SensorEventListener {

    lateinit var sensorManager: SensorManager
    lateinit var accelSensor: Sensor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        sensorManager = getSystemService<SensorManager>()!!
        accelSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) //there's also linear accelerometer gives value of accelerometer - g
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        //Reading accelerometer values
//        Log.d("HWSENS", """
//            ----
//            ax = ${p0!!.values[0]}
//            ay = ${p0!!.values[1]}
//            az = ${p0!!.values[2]}
//            ----
//        """.trimIndent())

        //Accelerometer disco lights app
        val bgColor = Color.rgb(
            accelToColor(p0!!.values[0]),
            accelToColor(p0.values[1]),
            accelToColor(p0.values[2])
        )
        AIndicator.setBackgroundColor(bgColor)
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(
            this, accelSensor, 1000 * 1000
        )
    }

    override fun onPause() {
        sensorManager.unregisterListener(this)
        super.onPause()
    }

    //Accelerometer disco lights app
    private fun accelToColor(accel : Float) : Int {
        //range is -12 to +12
        //accel + 12 changes range to from 0 to 24
        return (((accel+12) / 24) * 255).roundToInt()
    }
}