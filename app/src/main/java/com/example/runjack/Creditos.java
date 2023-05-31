package com.example.runjack;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.MotionEvent;

/**
 * Escena créditos de RunJack!
 * En ella se muestran de donde se sacan todos los recursos del juego.
 * Hereda de Escena.
 *
 * @author Emilio
 * @version 1
 */
public class Creditos extends Escena {

    /**
     * Número identificativo de la escena.
     */
    int numEscena=4;

    /**
     * Imagenes de los créditos en español y los créditos en inglés.
     */

    Bitmap creditos_esp,creditos_ing;

    /**
     * Imagenes de los créditos en español y en inglés, escalados.
     */

    Bitmap esp_escalado,ing_escalado;


    /**
     * Crea un nuevo objeto Creditos con los parámetros especificados.
     *
     * @param context   Contexto de la aplicacion.
     * @param numEscena Numero identificativo de la escena.
     * @param anp   Ancho de la pantalla.
     * @param alp   Alto de la pantalla.
     */
    public Creditos(Context context, int numEscena, int anp, int alp) {
        super(context,  anp, alp, numEscena);
        this.numEscena=numEscena;

        this.creditos_esp = BitmapFactory.decodeResource(context.getResources(),R.drawable.creditos_esp);
        this.creditos_ing = BitmapFactory.decodeResource(context.getResources(),R.drawable.creditos_ing);

        this.esp_escalado = Bitmap.createScaledBitmap(this.creditos_esp,anchoPantalla + 10,altoPantalla + 10,true);
        this.ing_escalado = Bitmap.createScaledBitmap(this.creditos_ing,anchoPantalla + 10,altoPantalla + 10,true);


    }


    /**
     * Dibuja el objeto Creditos en el canvas especificado.
     *
     * @param c El objeto Canvas en el que se dibujará la escena de créditos.
     */
    public void dibuja(Canvas c){
        c.drawColor(Color.DKGRAY);
        super.dibuja(c);
        c.drawText(context.getString(R.string.titulo_records),
                (float)anchoPantalla/2 - p.measureText((String)context.getString(R.string.titulo_records))/2,
                (float)altoPantalla/6, p);

        if(idioma.equals("en")){
            c.drawBitmap(this.ing_escalado,null,new Rect(c.getWidth() / 10,c.getHeight()/5,c.getWidth() - c.getWidth() / 10,c.getHeight()),null);
        }else{
            c.drawBitmap(this.esp_escalado,null,new Rect(c.getWidth() / 10,c.getHeight()/5,c.getWidth() - c.getWidth() / 10,c.getHeight()),null);
        }
    }

    /**
     * Maneja los eventos táctiles en la pantalla.
     *
     * @param event Representa el evento.
     * @return Devuelve el numero de escena al que se debe cambiar.
     */
    int onTouchEvent(MotionEvent event){

        int aux=super.onTouchEvent(event);
        if (aux!=this.numEscena && aux!=-1) return aux;
        return this.numEscena;
    }

}
