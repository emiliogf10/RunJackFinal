package com.example.runjack;

import android.graphics.Bitmap;
import android.graphics.PointF;

public class Jack {

    public PointF pos;
    public Bitmap imagen;

    public Jack(Bitmap imagen,float x,float y){
        this.imagen = imagen;
        this.pos = new PointF(x,y);
    }
    public void movimiento(int alto,int ancho, int vel){
        pos.x += vel;
        if(pos.x > ancho){
            pos.x = 0;
        }
    }
}
