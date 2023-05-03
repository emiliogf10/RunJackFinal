package com.example.runjack;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class GameSV extends SurfaceView implements SurfaceHolder.Callback {
    SurfaceHolder surfaceHolder;
    Context context;
    boolean funcionando=true;
    Hilo hilo;
    boolean finJuego=false;
    int anchoPantalla, altoPantalla;
    Escena escenaActual;
int  nuevaEscena;
    public GameSV(Context context) {
        super(context);
        this.surfaceHolder=getHolder();
        this.context=context;
        this.surfaceHolder.addCallback(this); // y se indica donde van las funciones callback
        this.context = context; // Obtenemos el contexto
        hilo = new Hilo();
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        this.anchoPantalla=width;
        this.altoPantalla=height;
        escenaActual=new Menu(context, 1 , anchoPantalla, altoPantalla);

        if (hilo.getState() == Thread.State.NEW) hilo.start();
        if (hilo.getState() == Thread.State.TERMINATED) {
            hilo=new Hilo();
            hilo.start();
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        this.funcionando=false;
        try {
            hilo.join();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int pointerIndex = event.getActionIndex();
        int pointerID = event.getPointerId(pointerIndex);
        int accion = event.getActionMasked();
        int x=(int)event.getX();
        int y=(int)event.getY();

        switch (accion){
            case MotionEvent.ACTION_DOWN:
                     nuevaEscena=escenaActual.onTouchEvent(event);
                Log.i("escena", "onTouchEvent: "+nuevaEscena+" "+escenaActual.numEscena);
                cambiaEscena(nuevaEscena);
                return true;
        }
        return super.onTouchEvent(event);
    }


    public void cambiaEscena(int cambiaEscena){
        if (escenaActual.numEscena!=nuevaEscena){
            switch (nuevaEscena){
                case 1: escenaActual=new Menu(context, 1, anchoPantalla, altoPantalla); break;
                case 2: escenaActual=new Juego(context, 2, anchoPantalla, altoPantalla); break;
                case 3: escenaActual=new GameOver(context, 3, anchoPantalla, altoPantalla); break;
                case 4: escenaActual=new Creditos(context, 4, anchoPantalla, altoPantalla); break;
                case 5: escenaActual=new Ajustes(context, 5, anchoPantalla, altoPantalla); break;
                case 6: escenaActual=new Records(context, 6, anchoPantalla, altoPantalla); break;

            }
        }
    }


    public class Hilo extends Thread{
        @Override
        public void run() {
            super.run();
            Canvas c=null;
            while (funcionando){
                c=null;
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
                }catch (Exception e){
                    e.printStackTrace();
                }
                finally {
                    try {
                        surfaceHolder.unlockCanvasAndPost(c);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }


}

