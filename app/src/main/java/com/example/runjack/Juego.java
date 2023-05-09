package com.example.runjack;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;

import java.util.List;
import java.util.Random;

public class Juego extends Escena {
    int numEscena=2;
    Cohete cohete;
    Bitmap bitmapCohete,bitmapFondo,cohete_escalado;
    GameSV game;
    Canvas c;
    List<Cohete> listaCohetes;
    Bitmap[] imagenesJack;



    public Juego(Context context, int numEscena, int anp, int alp) {
        super(context,  anp, alp, numEscena);
        this.numEscena=numEscena;

        bitmapCohete = BitmapFactory.decodeResource(context.getResources(),R.drawable.cohete);
        cohete_escalado = Bitmap.createScaledBitmap(bitmapCohete,anchoPantalla/5,altoPantalla/5,false);
        cohete = new Cohete(cohete_escalado,anchoPantalla,new Random().nextFloat()*(altoPantalla-cohete_escalado.getHeight()));

        /*int[] recursosJack = {
                R.drawable.jack1, R.drawable.jack2, R.drawable.jack3, R.drawable.jack4, R.drawable.jack5,
                R.drawable.jack6, R.drawable.jack7, R.drawable.jack8, R.drawable.jack9, R.drawable.jack10,
                R.drawable.jack11, R.drawable.jack12, R.drawable.jack13, R.drawable.jack14, R.drawable.jack15
        };
        for (int i = 0; i < imagenesJack.length; i++) {
            imagenesJack[i] = BitmapFactory.decodeResource(context.getResources(), recursosJack[i]);
        }*/


    }
    public void dibuja(Canvas c){
        c.drawColor(Color.WHITE);
        super.dibuja(c);
        /*c.drawText("Juego",anchoPantalla/2, altoPantalla/10,p);*/
        /*game.dibujar(c);*/

        crearCohete(6,c);

    }




    public void actualizaFisica(){

    }

    int onTouchEvent(MotionEvent event){

        int aux=super.onTouchEvent(event);
        if (aux!=this.numEscena && aux!=-1) return aux;
        return this.numEscena;
    }

    //Funcion para crear un cohete
    public void crearCohete(int velocidad,Canvas c){
        c.drawBitmap(cohete.imagen, cohete.pos.x, cohete.pos.y,null);
        cohete.movimiento(altoPantalla,anchoPantalla,velocidad);
        listaCohetes.add(cohete);

    }


    public void crearJack(){

    }

}
