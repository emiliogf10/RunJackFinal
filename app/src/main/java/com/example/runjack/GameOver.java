package com.example.runjack;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;

public class GameOver extends Escena {
    int numEscena=3;

    public GameOver(Context context, int numEscena, int anp, int alp) {
        super(context,  anp, alp, numEscena);
        this.numEscena=numEscena;
    }

    public void dibuja(Canvas c){
        c.drawColor(Color.CYAN);
        super.dibuja(c);
        c.drawText("GameOver",anchoPantalla/2, altoPantalla/10,p);

    }

    public void actualizaFisica(){

    }

    int onTouchEvent(MotionEvent event){


        int aux=super.onTouchEvent(event);
        if (aux!=this.numEscena && aux!=-1) return aux;
        return this.numEscena;

    }

}
