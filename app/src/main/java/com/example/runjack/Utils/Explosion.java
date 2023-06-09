package com.example.runjack.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.runjack.R;

/**
 * Clase que representa las explosiones de RunJack!
 *
 * @author Emilio
 * @version 1
 */
public class Explosion {

    /**
     * Contexto de la aplicación.
     */
    Context context;

    /**
     * Array de bitmaps de las explosiones.
     */
    public Bitmap[] explosiones;

    /**
     * Contador que servirá para ir mostrando todos los bitmaps de las explosiones.
     */
    public int frame;


    /**
     * Crea un nuevo objeto Explosion con el contexto especificado.
     *
     * @param context   Contexto de la aplicacion.
     */
    public Explosion(Context context){
        this.context = context;

        this.explosiones = new Bitmap[19];


                this.explosiones[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion1);
                this.explosiones[1] = BitmapFactory.decodeResource(context.getResources(),R.drawable.explosion2);
                this.explosiones[2] = BitmapFactory.decodeResource(context.getResources(),R.drawable.explosion3);
                this.explosiones[3] = BitmapFactory.decodeResource(context.getResources(),R.drawable.explosion4);
                this.explosiones[4] = BitmapFactory.decodeResource(context.getResources(),R.drawable.explosion5);
                this.explosiones[5] = BitmapFactory.decodeResource(context.getResources(),R.drawable.explosion6);
                this.explosiones[6] = BitmapFactory.decodeResource(context.getResources(),R.drawable.explosion7);
                this.explosiones[7] = BitmapFactory.decodeResource(context.getResources(),R.drawable.explosion8);
                this.explosiones[8] = BitmapFactory.decodeResource(context.getResources(),R.drawable.explosion9);
                this.explosiones[9] = BitmapFactory.decodeResource(context.getResources(),R.drawable.explosion10);
                this.explosiones[10] = BitmapFactory.decodeResource(context.getResources(),R.drawable.explosion11);
                this.explosiones[11] = BitmapFactory.decodeResource(context.getResources(),R.drawable.explosion12);
                this.explosiones[12] = BitmapFactory.decodeResource(context.getResources(),R.drawable.explosion13);
                this.explosiones[13] = BitmapFactory.decodeResource(context.getResources(),R.drawable.explosion14);
                this.explosiones[14] = BitmapFactory.decodeResource(context.getResources(),R.drawable.explosion15);
                this.explosiones[15] = BitmapFactory.decodeResource(context.getResources(),R.drawable.explosion16);
                this.explosiones[16] = BitmapFactory.decodeResource(context.getResources(),R.drawable.explosion17);
                this.explosiones[17] = BitmapFactory.decodeResource(context.getResources(),R.drawable.explosion18);
                this.explosiones[18] = BitmapFactory.decodeResource(context.getResources(),R.drawable.explosion19);




            this.frame = 0;



    }

    /**
     * Dibuja la explosión en el canvas en las coordenadas especificadas.
     *
     * @param c Canvas en el que se dibujará la explosión.
     * @param x La coordenada X de la posición de la explosión.
     * @param y La coordenada Y de la posición de la explosión.
     */
    public void dibujaExplosion(Canvas c, float x, float y){
        c.drawBitmap(this.explosiones[frame],x,y,null);
        if(this.frame < explosiones.length - 1){
            this.frame++;
        }
    }

    /**
     * Se comprueba que el frame actual es menor que la longitud del array de bitmaps; si es asi se suma 1;
     */
    public void actualizaExplosion(){
        if(this.frame < explosiones.length - 1){
            this.frame++;
        }
    }
}
