package com.example.runjack;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.MotionEvent;

public class Ajustes extends Escena {
    int numEscena=5;

    public Ajustes(Context context, int numEscena, int anp, int alp) {
        super(context, anp, alp, numEscena);
        this.numEscena = numEscena;

    }

    public void dibuja(Canvas c){
        c.drawColor(Color.GREEN);
        super.dibuja(c);
        c.drawText("Ajustes",anchoPantalla/2, altoPantalla/10,p);

    }

    public void actualizaFisica(){

    }

    int onTouchEvent(MotionEvent event){

        int aux=super.onTouchEvent(event);
        if (aux!=this.numEscena && aux!=-1) return aux;
        int x=(int)event.getX();
        int y=(int)event.getY();


        return this.numEscena;
    }

}
