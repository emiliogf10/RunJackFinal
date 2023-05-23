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

    /**
     *Crea un nuevo objeto Cohete con la imagen especificada y la posición inicial.
     *
     * @param imagen    Imagen del cohete.
     * @param x         Coordenada x de la posicion inicial del cohete.
     * @param y         Coordenada y de la posicion inicial del cohete.
     */
    public Cohete(Bitmap imagen,float x,float y){
        this.imagen = imagen;
        this.pos = new PointF(x,y);

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
    public boolean detectarColision(Jack jack) {
        RectF coheteRect = new RectF(pos.x, pos.y, pos.x + imagen.getWidth(), pos.y + imagen.getHeight());
        return coheteRect.intersect(jack.getHitBox());
    }

}
