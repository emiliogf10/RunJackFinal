package com.example.runjack;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import java.util.Random;

public class Cohete {
    Context context;
    Bitmap bitmapCohete,cohete_escalado;
    public PointF pos;

    private Random g;

    RectF coheteRect;

    Paint color;

    /**
     * Crea un nuevo objeto Cohete con los parámetros especificados.
     *
     * @param context   Contexto de la aplicacion.
     * @param x Posicion X del cohete.
     * @param y Posicion Y del cohete.
     * @param anchoPantalla Ancho de la pantalla.
     * @param altoPantalla  Alto de la pantalla.
     */
    public Cohete(Context context,float x,float y,float anchoPantalla,float altoPantalla){
        this.context = context;

        color = new Paint(Color.RED);
        this.color.setStyle(Paint.Style.STROKE);
        this.color.setStrokeWidth(5);

        //Bitmap del cohete con su correspondiente escalado
        this.bitmapCohete = BitmapFactory.decodeResource(context.getResources(),R.drawable.cohete);
        this.cohete_escalado = Bitmap.createScaledBitmap(bitmapCohete, (int) (anchoPantalla/6), (int) (altoPantalla/6),false);

        this.pos = new PointF(x,y);
        coheteRect = new RectF(this.pos.x, this.pos.y, this.pos.x + cohete_escalado.getWidth(), this.pos.y + cohete_escalado.getHeight());
    }

    /**
     * Dibuja el objeto cohete en el canvas especificado.
     *
     * @param c Lienzo en donde se dibujará el cohete.
     */
    public void dibuja(Canvas c){
        c.drawBitmap(cohete_escalado, this.pos.x,this.pos.y, null);
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
     *Método que detecta si hay colision entre un objeto Jack y un Cohete.
     *
     * @param jack  Objeto Jack que se le pasa para comprobar si colisiona con el cohete.
     * @return      Devuelve true si hay colision y false si no.
     */
    public boolean detectarColision(RectF jack) {
        Log.i("POS","JACKK -->Iz:" + jack.left+ " \tArriba: " + jack.top + " \tDerecha: " + jack.right + " Abajo: " + jack.bottom + "\nCOHE -->Iz:" + coheteRect.left + " \tArriba: " + coheteRect.top + " \tDerecha: " + coheteRect.right + " \tAbajo: " + coheteRect.bottom);
       RectF cohetaux= new RectF(this.pos.x,this.pos.y,this.pos.x + this.cohete_escalado.getWidth(),this.pos.y + this.cohete_escalado.getHeight());
       RectF jackaux = new RectF(jack.left,jack.top,jack.right,jack.bottom);
        return cohetaux.intersect(jackaux);
    }

    /**
     *Actualiza el rectángulo de colisión (hitbox) del objeto Cohete en función de su posición actualizada.
     */
    public void actualizaHit(){
        this.coheteRect= new RectF(this.pos.x,this.pos.y,this.pos.x + this.cohete_escalado.getWidth(),this.pos.y + this.cohete_escalado.getHeight());

    }

    /**
     *Obtiene el rectángulo de colisión (hitbox) del objeto Cohete.
     *
     * @return  El rectángulo de colisión del objeto Cohete.
     */
    public RectF getHitBox() {
        return coheteRect;
    }

}
