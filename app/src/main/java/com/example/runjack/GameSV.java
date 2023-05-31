package com.example.runjack;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.util.Locale;

/**
 * Clase principal de RunJack!
 *
 * @author Emilio
 * @version 1
 */

public class GameSV extends SurfaceView implements SurfaceHolder.Callback {

    /**
     * Contexto de la aplicación.
     */
    Context context;

    /**
     * El objeto SurfaceHolder que maneja el surface surface.
     */
    SurfaceHolder surfaceHolder;

    /**
     * Objeto MediaPlayer que maneja la música de fondo.
     */
    public static MediaPlayer musica_fondo;

    /**
     *  Booleana que indica si se está jugando al juego.
     */
    boolean funcionando;


    /**
     * Hilo del juego que corre su lógica.
     */
    Hilo hilo;

    /**
     * Ancho y alto de la pantalla.
     */
    int anchoPantalla, altoPantalla;

    /**
     * Escena actual que se está mostrando en el juego.
     */
    Escena escenaActual;
    /**
     * Escena a la que se va a cambiar.
     */
    int nuevaEscena;

    /**
     * Instancia de un objeto Cohete.
     */
    Cohete cohete;

    /**
     * Bitmaps de botón de sonido, botón de música y botón de idioma.
     */
    public static Bitmap btnSonido, btnMusica, btnIdioma;

    /**
     * Puntuación actual del juego.
     */

    public static int puntuacion = 0;

    /**
     * Se utilizará para guardar datos de los ajustes del juego.
     */

    public static SharedPreferences sp;

    /**
     * Booleana que indica cuando la musica está encendida.
     */
    boolean musica_on;

    /**
     * Booleana que indica cuando el los efectos de sonido están encendidos.
     */

    public static boolean sonido_on = true;

    /**
     * Idioma seleccionado para el juego.
     */
    String idioma;



    /**
     * Crea un nuevo objeto GameSV con el contexto especificado.
     *
     * @param context   Contexto de la aplicacion.
     */
    public GameSV(Context context) {
        super(context);
        this.surfaceHolder = getHolder();
        this.context = context;
        this.surfaceHolder.addCallback(this); // y se indica donde van las funciones callback
        this.context = context; // Obtenemos el contexto
        hilo = new Hilo();
        this.funcionando = true;

        sp = context.getApplicationContext().getSharedPreferences("SettingsValues", Context.MODE_PRIVATE);
        sonido_on = sp.getBoolean("sonido_on",true);
        musica_on = sp.getBoolean("musica_on",true);
        this.idioma = sp.getString("idioma","es");

        musica_fondo = MediaPlayer.create(this.getContext(),R.raw.musica_fondo);
        musica_fondo.setLooping(true);
        musica();

        btnSonido = BitmapFactory.decodeResource(context.getResources(), R.drawable.altavoz);
        btnMusica = BitmapFactory.decodeResource(context.getResources(), R.drawable.musica);
        btnIdioma = BitmapFactory.decodeResource(context.getResources(), R.drawable.bandera_espana);


        //Cambiar idioma

        CambiarIdioma(idioma);
    }

    /**
     * Función que controla la música de fondo.
     * La música se reproducirá si la booleana musica_on está a true y la musica no esté sonando, si no se pausa.
     */
    public void musica(){
        if(musica_on){
            if(!musica_fondo.isPlaying()){
                musica_fondo.start();
            }
        }else{
            musica_fondo.pause();
        }
    }

    /**
     *  Función que con la booleana establece el estado de la música, y llama a la función musica().
     *
     * @param musica
     */
    public void setMusica(boolean musica){
        musica_on = musica;
        musica();
    }

    /**
     *  Activa o desactiva el sonido.
     *
     * @param sonido    Booleana que indica cuando la música tiene que activarse.
     */
    public void setSonido(boolean sonido){
        sonido_on = sonido;
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {

    }

    @Override
        public void surfaceChanged (SurfaceHolder holder,int format, int width, int height){

            this.anchoPantalla = width;
            this.altoPantalla = height;
            escenaActual = new Menu(context, 1, anchoPantalla, altoPantalla);

            if (hilo.getState() == Thread.State.NEW){
                hilo.start();
            }
            if (hilo.getState() == Thread.State.TERMINATED) {
                hilo = new Hilo();
                hilo.start();
            }

        }

        @Override
        public void surfaceDestroyed (SurfaceHolder holder){
            this.funcionando = false;
            this.musica_fondo.stop();
            try {
                hilo.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public boolean onTouchEvent (MotionEvent event){
            int pointerIndex = event.getActionIndex();
            int pointerID = event.getPointerId(pointerIndex);
            int accion = event.getActionMasked();
            int x = (int) event.getX();
            int y = (int) event.getY();

            switch (accion) {
                case MotionEvent.ACTION_DOWN:
                    nuevaEscena = escenaActual.onTouchEvent(event);
                    cambiaEscena(nuevaEscena);
                    return true;
            }
            return super.onTouchEvent(event);
        }


    /**
     *Cambia la escena actual a una nueva escena según el número de escena especificado.
     *
     * @param cambiaEscena  Numero de la escena nueva a la que se desea cambiar.
     */
    public void cambiaEscena ( int cambiaEscena){
            if (escenaActual.numEscena != nuevaEscena) {
                switch (nuevaEscena) {
                    case 1:
                        escenaActual = new Menu(context, 1, anchoPantalla, altoPantalla);
                        break;
                    case 2:
                        escenaActual = new Juego(context, 2, anchoPantalla, altoPantalla);
                        break;
                    case 4:
                        escenaActual = new Creditos(context, 4, anchoPantalla, altoPantalla);
                        break;
                    case 5:
                        escenaActual = new Ajustes(context, 5, anchoPantalla, altoPantalla,this);
                        break;
                    case 6:
                        escenaActual = new Records(context, 6, anchoPantalla, altoPantalla);
                        break;
                    case 7:
                        escenaActual = new Informacion(context,7,anchoPantalla,altoPantalla);
                        break;

                }
            }
        }


    /**
     * Cmbia el idioma del juego.
     *
     * @param idioma    Codigo del nuevo idioma.
     */
    public void CambiarIdioma(String idioma){
        Log.i("TAG","Cambiamos el idioma");
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.locale = new Locale(idioma.toLowerCase());
        res.updateConfiguration(conf,dm);
    }


    /**
     * Clase que representa un hilo de ejecución para la actualización y dibujado de la escena en un canvas.
     * Extiende de la clase Thread.
     */
    public class Hilo extends Thread {
            @Override
            public void run() {
                super.run();
                Canvas c = null;
                while (funcionando) {
                    c = null;
                    try {
                        if (!surfaceHolder.getSurface().isValid()) continue;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            c = surfaceHolder.lockHardwareCanvas();
                        }
                        if (c == null) c = surfaceHolder.lockCanvas();

                        synchronized (surfaceHolder) {
                            escenaActual.actualizaFisica();
                            escenaActual.dibuja(c);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            surfaceHolder.unlockCanvasAndPost(c);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }


    }


