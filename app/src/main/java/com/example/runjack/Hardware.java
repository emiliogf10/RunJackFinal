package com.example.runjack;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Clase que implementa el hardware; en mi caso el vibrador y el giroscopio.
 * @author Emilio
 * @version 1
 * @since 23/05/2023
 */
public class Hardware extends AppCompatActivity implements SensorEventListener {

    Vibrator vibrador;

    /**
     * Crea un nuevo objeto Hardware con el contexto especificado.
     *
     * @param context   Contexto de la aplicacion.
     */
    public Hardware(Context context){
        this.vibrador = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

    }
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    /**
     * Activa la vibracion del dispositivo
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
