package com.example.runjack;

import android.content.Context;
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

import java.util.Locale;


public class GameSV extends SurfaceView implements SurfaceHolder.Callback {

    SurfaceHolder surfaceHolder;
    Context context;

    public static MediaPlayer musica_fondo;
    Bitmap bitmapFondo;
    boolean funcionando = true;
    boolean esTitulo=true;
    Hilo hilo;

    boolean finJuego = false;
    int anchoPantalla, altoPantalla;
    Escena escenaActual;
    int nuevaEscena;
    Cohete cohete;

    public static Bitmap btnSonido, btnMusica, btnIdioma;
    public static Configuration configuration;

    public static String idioma;

    public static MediaPlayer musica;

    public static int puntuacion = 0;


    /**
     * Crea un nuevo objeto GameSV con el contexto especificado.
     *
     * @param context   Contexto de la aplicacion.
     */
    public GameSV(Context context) {
        super(context);
        this.context = context;

        musica_fondo = MediaPlayer.create(this.getContext(),R.raw.musica_fondo);
        musica_fondo.setLooping(true);
        musica_fondo.start();

        btnSonido = BitmapFactory.decodeResource(context.getResources(), R.drawable.altavoz);
        btnMusica = BitmapFactory.decodeResource(context.getResources(), R.drawable.musica);
        btnIdioma = BitmapFactory.decodeResource(context.getResources(), R.drawable.bandera_espana);

        this.surfaceHolder = getHolder();
        this.context = context;
        this.surfaceHolder.addCallback(this); // y se indica donde van las funciones callback
        this.context = context; // Obtenemos el contexto
        hilo = new Hilo();

        bitmapFondo = BitmapFactory.decodeResource(context.getResources(), R.drawable.fondo);

        //Cambiar idioma

        CambiarIdioma("en");
        configuration = getResources().getConfiguration();
        idioma = configuration.locale.getLanguage();
        btnIdioma = BitmapFactory.decodeResource(context.getResources(),R.drawable.bandera_inglesa);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    /**
     * Actualiza la física del cohete en la escena.
     * Mueve el cohete según los límites de la pantalla y una velocidad dada.
     */
    public void actualizarFisica(){
        cohete.movimiento(altoPantalla,anchoPantalla,10);
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
                        escenaActual = new Ajustes(context, 5, anchoPantalla, altoPantalla);
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
        conf.setLocale(new Locale(idioma.toLowerCase()));
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


