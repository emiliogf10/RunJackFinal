package com.example.runjack;

import android.graphics.Bitmap;
import android.graphics.PointF;

import java.util.Random;

public class Cohete {
    public PointF pos;
    public Bitmap imagen;
    private Random g;

    public Cohete(Bitmap imagen,float x,float y){
        this.imagen = imagen;
        this.pos = new PointF(x,y);

    }

    public void movimiento(int alto,int ancho,int vel){
        pos.x -= vel;


    }
}
