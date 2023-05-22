package com.example.runjack;

import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

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

    public boolean detectarColision(Jack jack) {
        RectF coheteRect = new RectF(pos.x, pos.y, pos.x + imagen.getWidth(), pos.y + imagen.getHeight());
        return coheteRect.intersect(jack.getHitBox());
    }

}
