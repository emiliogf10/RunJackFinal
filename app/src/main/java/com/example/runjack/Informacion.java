package com.example.runjack;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

public class Informacion extends Escena {

    /**
     * Número identificativo de la escena.
     */
    int numEscena = 7;

    /**
     * Bitmaps de información en español y en inglés.
     */
    Bitmap inf_esp,inf_ing;

    /**
     * Bitmaps de información en español y en inglés, escalados.
     */
    Bitmap esp_escalado,ing_escalado;


    /**
     * Crea un nuevo objeto Informacion con los parámetros especificados.
     *
     * @param context   Contexto de la aplicación.
     * @param numEscena Número identificativo de la escena.
     * @param anchoPantalla Ancho de la pantalla.
     * @param altoPantalla  Alto de la pantalla.
     */
    public Informacion(Context context,int numEscena, int anchoPantalla, int altoPantalla) {
        super(context, anchoPantalla, altoPantalla, numEscena);
        this.numEscena = numEscena;


        this.inf_esp = BitmapFactory.decodeResource(context.getResources(),R.drawable.cj_esp);
        this.inf_ing = BitmapFactory.decodeResource(context.getResources(),R.drawable.cj_ing);

        this.esp_escalado = Bitmap.createScaledBitmap(this.inf_esp,anchoPantalla + 10,altoPantalla + 10,true);
        this.ing_escalado = Bitmap.createScaledBitmap(this.inf_ing,anchoPantalla + 10,altoPantalla + 10,true);


    }

    /**
     * Dibuja la escena en el Canvas.
     *
     * @param c El objeto Canvas en el que se dibujará la escena de créditos.
     */
    public void dibuja(Canvas c){
        super.dibuja(c);
        c.drawText(context.getString(R.string.information_title),anchoPantalla/2, altoPantalla/10,p);

        if(GameSV.idioma.equals("en")){
            c.drawBitmap(this.ing_escalado,null,new Rect(c.getWidth() / 10,c.getHeight()/5,c.getWidth() - c.getWidth() / 10,c.getHeight()),null);
        }else{
            c.drawBitmap(this.esp_escalado,null,new Rect(c.getWidth() / 10,c.getHeight()/5,c.getWidth() - c.getWidth() / 10,c.getHeight()),null);
        }
    }

    /**
     * Maneja los eventos táctiles en la pantalla.
     *
     * @param event Representa el evento.
     * @return  Devuelve el numero de escena al que se debe cambiar.
     */
    @Override
    int onTouchEvent(MotionEvent event){

        int aux=super.onTouchEvent(event);
        if (aux!=this.numEscena && aux!=-1) return aux;

        return this.numEscena;
    }
}
