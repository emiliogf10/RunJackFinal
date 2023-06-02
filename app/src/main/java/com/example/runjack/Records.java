package com.example.runjack;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.view.MotionEvent;

import androidx.core.content.ContextCompat;

import java.util.List;

/**
 * Escena de Puntuaciones más altas de RunJack!
 * En ella se pueden ver las puntuaciones más altas.
 * Hereda de Escena.
 *
 * @author Emilio
 * @version 1
 */
public class Records extends Escena {

    /**
     * Objeto RectF que representa el botón de eliminar records.
     */
    RectF btn_eliminar;

    /**
     * Número identificativo de la escena.
     */
    int numEscena=6;

    /**
     * Pintura que se usará para dibujar las puntuaciones.
     */
    Paint pun;

    /**
     * Pintura que se usará para el botón.
     */

    Paint btn;

    /**
     * Lista de las puntuaciones más altas.
     */
    List<Integer> puntuaciones_mas_altas;

    /**
     *
     * @param context   Contexto de la aplicación.
     * @param numEscena Numero identificativo de la escena.
     * @param anp   Ancho de la pantalla.
     * @param alp   Alto de la pantalla.
     */
    public Records(Context context, int numEscena, int anp, int alp) {
        super(context,  anp, alp, numEscena);
        this.numEscena=numEscena;
        this.puntuaciones_mas_altas = MainActivity.base_Datos.obtenerPuntos();

        this.pun = new Paint();
        this.pun.setColor(Color.WHITE);
        this.pun.setTypeface(Typeface.createFromAsset(context.getAssets(),"SUPERBOLT.ttf"));
        pun.setTextSize((float)altoPantalla/16);

        this.btn = new Paint();
        this.btn.setStyle(Paint.Style.FILL);
        this.btn.setTextAlign(Paint.Align.CENTER);
        this.btn.setColor(ContextCompat.getColor(context,R.color.gris));

        this.btn_eliminar = new RectF((float)anchoPantalla / 4, (float)altoPantalla / 7 * 6,
                (float)anchoPantalla/ 4 * 3, (float)altoPantalla / 7 * 6.8f);


    }

    /**
     * Dibuja la escena Menu en el Canvas especificado.
     *
     * @param c El objeto Canvas en el que se dibujará la escena de créditos.
     */
    public void dibuja(Canvas c){
        super.dibuja(c);
        c.drawText((String) context.getString(R.string.titulo_records),anchoPantalla/2, altoPantalla/10,p);

        //Si la base de datos no tiene puntuaciones, imprime mensaje.
        if(puntuaciones_mas_altas.size() == 0){
            c.drawText((String) context.getString(R.string.noRegistros),(float)this.anchoPantalla/2 - this.pun.measureText((String)context.getString(R.string.noRegistros))/2,
                    (float)altoPantalla / 2, this.pun);
        }else {
            c.drawRoundRect(this.btn_eliminar,15f,15f,btn);
            c.drawText((String) context.getString(R.string.eliminar_records),
                    (float)anchoPantalla/2 - this.pun.measureText((String) context.getString(R.string.eliminar_records)) / 2,
                    (float)altoPantalla/5 *4.6f, this.pun);

            c.drawText("1 - " + this.puntuaciones_mas_altas.get(0),
                    (float) this.anchoPantalla / 2 - this.pun.measureText((String) "1 - " + this.puntuaciones_mas_altas.get(0)) / 2,
                    (float) this.altoPantalla / 2 - (float) this.altoPantalla / 10,
                    pun);
            if (puntuaciones_mas_altas.size() > 1) {
                c.drawText("2 - " + this.puntuaciones_mas_altas.get(1),
                        (float) this.anchoPantalla / 2 - this.pun.measureText((String) "2 - " + this.puntuaciones_mas_altas.get(1)) / 2,
                        (float) this.altoPantalla / 2,
                        pun);
                if (puntuaciones_mas_altas.size() > 2) {
                    c.drawText("3 - " + this.puntuaciones_mas_altas.get(2),
                            (float) this.anchoPantalla / 2 - this.pun.measureText((String) "3 - " + this.puntuaciones_mas_altas.get(2)) / 2,
                            (float) this.altoPantalla / 2 + (float) this.altoPantalla / 10,
                            pun);
                }
            }
        }
    }

    /**
     * Maneja los eventos táctiles en la pantalla.
     *
     * @param event Representa el evento.
     * @return Devuelve el numero de escena al que se debe cambiar.
     */
    int onTouchEvent(MotionEvent event){
        int x = (int)event.getX();
        int y = (int)event.getY();

        int aux=super.onTouchEvent(event);
        if (aux!=this.numEscena && aux!=-1) return aux;

        if(this.btn_eliminar.contains(x, y)){
            MainActivity.base_Datos.eliminarPuntos();
            this.puntuaciones_mas_altas = MainActivity.base_Datos.obtenerPuntos();
        }
        return this.numEscena;
    }

}
