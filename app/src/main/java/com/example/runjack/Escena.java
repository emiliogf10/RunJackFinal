package com.example.runjack;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.view.MotionEvent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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
    Paint p,ventana,titulo;
    Rect btnMenu,btnAtras;

    RectF base,btnCasa,btnResume;
    Bitmap menu;
    Bitmap menuEscalado;

    boolean acabado = false;
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

        tf = Typeface.createFromAsset(context.getAssets(), "SUPERBOLT.ttf");
        p.setTypeface(tf);

        this.titulo = new Paint();
        this.titulo.setAlpha(200);
        this.titulo.setTypeface(tf);
        this.titulo.setAntiAlias(true);
        this.titulo.setColor(Color.WHITE);
        this.titulo.setTextSize((float) altoPantalla / 10);
        this.titulo.setTextAlign(Paint.Align.CENTER);

        this.ventana = new Paint();
        base = new RectF((float) anchoPantalla / 5,(float) altoPantalla / 5,(float) anchoPantalla / 5 * 4,(float) altoPantalla / 5 * 4);

        this.btnAtras = new Rect(anchoPantalla / 20,altoPantalla / 12,anchoPantalla / 20 + 50,altoPantalla / 12 + 50);
        menu = BitmapFactory.decodeResource(context.getResources(), R.drawable.casa);
        menuEscalado = Bitmap.createScaledBitmap(menu,menu.getWidth()/4,menu.getHeight()/4,true);



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

    public void pantallaPausa(Canvas c,boolean acabado){
        this.acabado = acabado;

        this.ventana.setStyle(Paint.Style.FILL);
        this.titulo.setTextAlign(Paint.Align.CENTER);
        this.ventana.setColor(ContextCompat.getColor(context,R.color.gris));

        c.drawRoundRect(base, 20f, 20f, ventana);

        this.ventana.setColor(ContextCompat.getColor(context, R.color.color_letra));
        this.ventana.setTypeface(tf);
        this.ventana.setTextSize((float) altoPantalla / 8);

        if (acabado) {
            c.drawText((String) context.getText(R.string.gameOver_title),
                    (float) anchoPantalla / 5 * 2,
                    (float) altoPantalla / 7 * 2, this.ventana);

        } else {
            c.drawText((String) context.getText(R.string.Pause), (float) anchoPantalla / 5 * 2,
                    (float) altoPantalla / 7 * 3, this.ventana);
        }

        ventana.setColor(ContextCompat.getColor(context, R.color.black));
        c.drawRoundRect(this.btnCasa, 20f, 20f, ventana);
        c.drawRoundRect(this.btnResume, 20f, 20f, ventana);


        ventana.setColor(ContextCompat.getColor(context, R.color.color_letra));
        ventana.setTextSize((float) altoPantalla / 15);
        if (acabado) {
            c.drawText((String) context.getString(R.string.home),
                    (float) anchoPantalla / 7 * 4, (float) altoPantalla / 12 * 8, ventana);
        } else {
            c.drawText((String) context.getString(R.string.home), (float) anchoPantalla / 7 * 2, (float) altoPantalla / 12 * 8, ventana);
            c.drawText((String) context.getString(R.string.resume), (float) anchoPantalla / 7 * 4, (float) altoPantalla / 12 * 8, ventana);
        }

    }

}
