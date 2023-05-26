package com.example.runjack;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;

public class Records extends Escena {
    int numEscena=6;

    /**
     *
     * @param context   Contexto de la aplicación.
     * @param numEscena Numero identificativo de la escena.
     * @param anp   Ancho de la pantalla.
     * @param alp   Alto de la pantalla.
     */
    public Records(Context context, int numEscena, int anp, int alp) {
        super(context,  anp, alp, numEscena);
        this.numEscena=numEscena;
    }

    /**
     * Dibuja la escena Menu en el Canvas especificado.
     *
     * @param c El objeto Canvas en el que se dibujará la escena de créditos.
     */
    public void dibuja(Canvas c){
        super.dibuja(c);
        c.drawText(context.getString(R.string.records_title),anchoPantalla/2, altoPantalla/10,p);
    }

    /**
     * Actualiza la fisica de los elementos de la escena.
     */
    public void actualizaFisica(){

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
