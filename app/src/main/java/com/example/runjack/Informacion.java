package com.example.runjack;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;

public class Informacion extends Escena {

    int numEscena = 7;
    public Informacion(Context context,int numEscena, int anchoPantalla, int altoPantalla) {
        super(context, anchoPantalla, altoPantalla, numEscena);
        this.numEscena = numEscena;
    }

    public void dibuja(Canvas c){
        super.dibuja(c);
        c.drawText(context.getString(R.string.information_title),anchoPantalla/2, altoPantalla/10,p);
    }

    int onTouchEvent(MotionEvent event){

        int aux=super.onTouchEvent(event);
        if (aux!=this.numEscena && aux!=-1) return aux;
        return this.numEscena;
    }
}
