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

    /**
     * Instancia de Sensor Manager.
     */
    SensorManager sm;

    /**
     * Instancia de un sensor; en este caso el acelerómetro.
     */

    public Sensor acelerometro;

    /**
     *  Array que guarda la gravedad del acelerómetro.
     */
    float[] gravedad;

    /**
     *  Array de los componentes de la linea de aceleración.
     */
    float[] linear_acceleration;

    /**
     * Instancia de la clase juego.
     */
    Juego juego;

    /**
     * Crea un nuevo objeto Hardware con el contexto especificado.
     *
     * @param context   Contexto de la aplicación.
     * @param juego     Clase del juego.
     */
    public Hardware(Context context,Juego juego){

        this.juego = juego;

        this.vibrador = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

        this.sm = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        this.acelerometro = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        continuar();

        this.gravedad = new float[3];
        this.linear_acceleration = new float[3];

    }


    /**
     *  Función que se ejecuta cuando el valor del sensor cambia.
     *
     * @param event Evento del sensor.
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        final float alpha = (float) 0.8;
        // Isolate the force of gravity with the low-pass filter.
        gravedad[0] = alpha * gravedad[0] + (1 - alpha) * event.values[0];
        gravedad[1] = alpha * gravedad[1] + (1 - alpha) * event.values[1];
        gravedad[2] = alpha * gravedad[2] + (1 - alpha) * event.values[2];

        // Remove the gravity contribution with the high-pass filter.
        linear_acceleration[0] = event.values[0] - gravedad[0];
        linear_acceleration[1] = event.values[1] - gravedad[1];
        linear_acceleration[2] = event.values[2] - gravedad[2];

        double magnitude = Math.sqrt(
                linear_acceleration[0] * linear_acceleration[0] +
                        linear_acceleration[1] * linear_acceleration[1] +
                        linear_acceleration[2] * linear_acceleration[2]);

        double threshold = 7.0;
        if (magnitude >= threshold) {
            if(!juego.enPausa){
                juego.eliminarCohetes();
            }

        }

    }


    /**
     *  Esta función se llama cuando el sensor ha sufrido un cambio de precisión.
     *
     * @param sensor    Sensor el cual ha sufrido un cambio de precisión.
     * @param i El nuevo valor de precisión.
     */
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


    /**
     *  Método que se llama cuando se quiere desactivar el sensor.
     */
    public void pausa(){
        sm.unregisterListener(this,acelerometro);
    }

    /**
     *  Método que se llama cuando se quiere volver a activar el sensor.
     */
    public void continuar(){
        sm.registerListener(this,acelerometro,SensorManager.SENSOR_DELAY_NORMAL);
    }
}
