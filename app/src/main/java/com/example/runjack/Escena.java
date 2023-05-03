package com.example.runjack;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

public class Escena {
    int numEscena=-1;
    int altoPantalla;
    int anchoPantalla;
    Context context;
    Paint p;
    Rect btnMenu;
    Bitmap menu;

    public Escena( Context context, int anchoPantalla, int altoPantalla, int numEscena) {
        this.altoPantalla = altoPantalla;
        this.anchoPantalla = anchoPantalla;
        this.context = context;
        p=new Paint();
        p.setTextSize(altoPantalla/10);
        p.setColor(Color.WHITE);
        p.setTextAlign(Paint.Align.CENTER);
        menu = BitmapFactory.decodeResource(context.getResources(), R.drawable.casa);
        this.numEscena=numEscena;
        this.btnMenu = new Rect(100, 100, 500, 400);
    }

    public void dibuja(Canvas c){

        if (numEscena!=1) c.drawBitmap(menu,null,btnMenu,null);
    }

    public void actualizaFisica(){

    }

    int onTouchEvent(MotionEvent event){
        int x=(int)event.getX();
        int y=(int)event.getY();

     if (numEscena!=1)   if (btnMenu.contains(x,y)) return 1;

        return -1;
    }

}
