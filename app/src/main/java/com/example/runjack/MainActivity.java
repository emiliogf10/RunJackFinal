package com.example.runjack;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

/**
 * Main Activity de RunJack!
 *
 * @author Emilio
 * @version 1
 */
public class MainActivity extends AppCompatActivity {

    /**
     *  Instancia de SensorManager para acceder a los sensores del dispositivo.
     */
    SensorManager sm;

    /**
     * Acelerometro del juego.
     */
    Sensor acelerometro;

    /**
     * Base de datos del juego.
     */
    public static BaseDeDatos base_Datos;

    /**
     * Instancia de SQLiteDatabse que interactua con la base de datos.
     */
    SQLiteDatabase bd;

    /**
     * Vibrador del juego.
     */
    Vibrator vibrator;

    /**
     * Función que se llama cuando se crea la actividad.
     *
     * @param savedInstanceState    El estado guardado de la instancia anterior de la actividad.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GameSV gamesv=new GameSV(this);

        //PANTALLA HORIZONTAL
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


        //Base de datos
        base_Datos = new BaseDeDatos(this);
        this.bd = base_Datos.getReadableDatabase();

        if(this.bd != null){
            base_Datos.onCreate(this.bd);
        }

        //Vibrador
        this.vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        //Acelerómetro
        this.sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        this.acelerometro = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


        if (Build.VERSION.SDK_INT < 16) { // versiones anteriores a Jelly Bean
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }else { // versiones iguales o superiores a Jelly Bean
            final int flags= View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION // Oculta la barra de navegación
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // Oculta la barra de navegación
                    | View.SYSTEM_UI_FLAG_IMMERSIVE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            final View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(flags);
            decorView.setOnSystemUiVisibilityChangeListener(visibility -> {
                if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                    decorView.setSystemUiVisibility(flags);
                }
            });
        }
        getSupportActionBar().hide(); // se oculta la barra de ActionBa
        gamesv.setKeepScreenOn(true); //Pantalla siempre encendida
        setContentView(gamesv);

    }

    /**
     * Función que se llama al destruirse la aplicación.
     * Se libera la musica.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        GameSV.musica_fondo.release();
    }
}