package com.example.runjack;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;

import java.util.Random;

public class Juego extends Escena {
    int numEscena=2;
    Cohete cohete;
    Bitmap bitmapCohete,bitmapFondo;
    GameSV game;



    public Juego(Context context, int numEscena, int anp, int alp) {
        super(context,  anp, alp, numEscena);
        this.numEscena=numEscena;

        bitmapCohete = BitmapFactory.decodeResource(context.getResources(),R.drawable.cohete);
        cohete = new Cohete(bitmapCohete,new Random().nextFloat()*(anchoPantalla-bitmapCohete.getWidth()),0);

    }
    public void dibuja(Canvas c){
        c.drawColor(Color.WHITE);
        super.dibuja(c);
        c.drawText("Juego",anchoPantalla/2, altoPantalla/10,p);
        /*game.dibujar(c);*/
        c.drawBitmap(cohete.imagen, cohete.pos.x, cohete.pos.y,null);
        cohete.movimiento(altoPantalla,anchoPantalla,2);

    }




    public void actualizaFisica(){

    }

    int onTouchEvent(MotionEvent event){

        int aux=super.onTouchEvent(event);
        if (aux!=this.numEscena && aux!=-1) return aux;
        return this.numEscena;
    }



}
