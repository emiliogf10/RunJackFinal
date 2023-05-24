package com.example.runjack;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import java.util.Random;

public class Cohete {
    public PointF pos;
    public Bitmap imagen;

    private Random g;

    RectF coheteRect;

    Paint color;

    /**
     *Crea un nuevo objeto Cohete con la imagen especificada y la posición inicial.
     *
     * @param imagen    Imagen del cohete.
     * @param x         Coordenada x de la posicion inicial del cohete.
     * @param y         Coordenada y de la posicion inicial del cohete.
     */
    public Cohete(Bitmap imagen,float x,float y){
        color = new Paint(Color.RED);
        this.color.setStyle(Paint.Style.STROKE);
        this.color.setStrokeWidth(5);

        this.imagen = imagen;
        this.pos = new PointF(x,y);
        coheteRect = new RectF(this.pos.x, this.pos.y, this.pos.x + imagen.getWidth(), this.pos.y + imagen.getHeight());
    }

    public void dibuja(Canvas c){
        c.drawBitmap(imagen, pos.x,pos.y, null);
        c.drawRect(this.coheteRect,color);
        actualizaHit();
    }

    /**
     *Realiza el movimiento del objeto en el eje x con una velocidad especificada.
     *
     * @param alto  Alto de la zona de movimiento.
     * @param ancho Ancho de la zona de movimiento.
     * @param vel   Velocidad de movimiento.
     */
    public void movimiento(int alto,int ancho,int vel){
        pos.x -= vel;
    }

    /**
     *Método que detecta si hay colision entre un objeto Jack y un cohete.
     *
     * @param jack  Objeto Jack que se le pasa para comprobar si colisiona con el cohete.
     * @return      Devuelve true si hay colision y false si no.
     */
    public boolean detectarColision(RectF jack) {
        return coheteRect.intersect(jack);
    }

    public void actualizaHit(){
        this.coheteRect= new RectF(this.pos.x,this.pos.y,this.pos.x + this.imagen.getWidth(),this.pos.y + this.imagen.getHeight());

    }

}
