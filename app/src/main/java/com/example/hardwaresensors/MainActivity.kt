package com.example.hardwaresensors

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.getSystemService

class MainActivity : AppCompatActivity() {

    lateinit var sensorEventListener: SensorEventListener
    lateinit var sensorManager: SensorManager
    lateinit var proxSensor: Sensor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
        https://github.com/coding-blocks-archives/Android_Hardware_Sensors
         */

        //check whether sensors are working or not
        sensorManager = getSystemService<SensorManager>()!!
        if(sensorManager == null){
            Toast.makeText(this,"Could not get sensors!",Toast.LENGTH_LONG).show()
            finish()
        } else{
            val sensors = sensorManager.getSensorList(Sensor.TYPE_ALL)  //this will give all sensors on that device
            sensors.forEach {
                Log.d("SENS" , """
                    ${it.name} | ${it.stringType} | ${it.vendor}
                    """.trimIndent()
                )
            }
        }

        //to get proximity sensor - gives 0.0 when phone is covered with hand otherwise 5.000305
        proxSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_PROXIMITY)
        sensorEventListener = object : SensorEventListener{
            override fun onSensorChanged(p0: SensorEvent?) {
                Log.d("HWSENS", """
            onSensorChanged: ${p0!!.values[0]}
           
        """.trimIndent())
            }

            override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
                //nothing
            }

        }
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(
                sensorEventListener,proxSensor,1000 * 1000
        )
    }

    //stop detecting in sleep mode
    override fun onPause() {
        sensorManager.unregisterListener(sensorEventListener)
        super.onPause()
    }
}