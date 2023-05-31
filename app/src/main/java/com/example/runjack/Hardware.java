package com.example.runjack;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Clase que implementa el hardware; en mi caso el vibrador y el acelerómetro.
 *
 * @author Emilio
 * @version 1
 */
public class Hardware extends AppCompatActivity implements SensorEventListener {

    /**
     * Instancia del vibrador del juego.
     */
    Vibrator vibrador;

    SensorManager sm;

    public Sensor acelerometro;

    /**
     * Crea un nuevo objeto Hardware con el contexto especificado.
     *
     * @param context   Contexto de la aplicación.
     */
    public Hardware(Context context){
        this.vibrador = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

        this.sm = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        this.acelerometro = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

    }
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    /**
     * Activa la vibración del dispositivo
     * Si el dispositivo tiene soporte para vibración y el vibrador está disponible, se activará una vibración corta.
     * La duración de la vibración es de 400 milisegundos.
     */
    public void vibra(){
        if(vibrador != null){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                this.vibrador.vibrate(VibrationEffect.createOneShot(400,VibrationEffect.DEFAULT_AMPLITUDE));
            }else{
                this.vibrador.vibrate(400);
            }
        }
    }
}
