package com.example.runjack;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.MotionEvent;

import java.util.Timer;

/**
 * Clase padre para todas las subclases que crearemos; Creditos,Informacion,Records,Ajustes,Pausa,
 * Game Over y Menu.
 * @author Emilio
 * @version 1
 */
public class Escena {
    int numEscena=-1;
    int altoPantalla;
    int anchoPantalla;
    Context context;
    Paint p;
    Rect btnMenu;
    Bitmap menu;
    Bitmap menuEscalado;

    Typeface tf;

    /**
     * Constructs an instance of the Scene class.
     *
     * @param context Contexto de la aplicacion
     * @param altoPantalla Alto de la pantalla
     * @param anchoPantalla Alto de la pantalla
     * @param numEscena Numero de identificacion de la escena
     */

    public Escena( Context context, int anchoPantalla, int altoPantalla, int numEscena) {
        this.altoPantalla = altoPantalla;
        this.anchoPantalla = anchoPantalla;
        this.context = context;
        p=new Paint();
        p.setTextSize(altoPantalla/10);
        p.setColor(Color.WHITE);
        p.setTextAlign(Paint.Align.CENTER);
        menu = BitmapFactory.decodeResource(context.getResources(), R.drawable.casa);
        menuEscalado = Bitmap.createScaledBitmap(menu,menu.getWidth()/4,menu.getHeight()/4,true);

        tf = Typeface.createFromAsset(context.getAssets(), "SUPERBOLT.ttf");
        p.setTypeface(tf);

        this.numEscena=numEscena;
        this.btnMenu = new Rect(anchoPantalla/12, altoPantalla/12, anchoPantalla/12*2, altoPantalla/12*2);
    }

    /**
     * Dibuja la escena en canvas
     *
     * @param c The canvas on which the scene should be drawn.
     */
    public void dibuja(Canvas c){
        c.drawColor(Color.parseColor("#e2e2e2"));
        if (numEscena!=1){
            c.drawBitmap(menuEscalado,null,btnMenu,null);
        }
    }

    /**
     * Actualiza la fisica de cada escena
     *
     */
    public void actualizaFisica(){

    }

    /**
     * Handles the touch events on the scene.
     *
     * @param event representa el evento
     * @return Devuelve El resultado del evento click (1 si el botón se tocó en escenas diferentes de 1 y 3, si no, devuelve -1)
     *
     */
    int onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        if (numEscena != 1) if (btnMenu.contains(x, y)) return 1;

        return -1;
    }

}
