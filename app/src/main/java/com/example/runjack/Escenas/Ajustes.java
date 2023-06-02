package com.example.runjack.Escenas;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.example.runjack.GameSV;
import com.example.runjack.R;

/**
 *  Escena que representa los ajustes de RunJack!
 *  En ella puedes cambiar el idioma y apagar y encender la música y efectos sonoros.
 *  Hereda de Escena.
 *
 * @author Emilio
 * @version 1
 */
public class Ajustes extends Escena {
    /**
     * Número identificativo de las escena.
     */
    int numEscena=5;

    /**
     * Objeto GameSV con el contexto especificado.
     */
    GameSV gsv;

    /**
     *  Rectángulos de los botones de idioma,música y volumen.
     */
    Rect btnIdioma,btnVolumen,btnMusica;

    /**
     * Idioma del juego.
     */
    String idioma;

    /**
     * Se guarda la musica de las shared preferences.
     */
    boolean musica;

    /**
     * Se guarda el sonido de las shared preferences.
     */
    boolean sonido;

    /**
     * Bitmaps auxiliares de los botones de idioma, música y volumen.
     */

    Bitmap silencio_on,silencio_off, ingles,español, musica_off,musica_on;

    /**
     * Ancho y alto de la pantalla.
     */
    int anchoP,altoP;


    /**
     * Crea un objeto Ajustes con los parámetros especificados.
     *
     * @param context   Contexto de la aplicacion.
     * @param numEscena Numero identificativo de la escena.
     * @param anp   Ancho de la pantalla.
     * @param alp   Alto de la pantalla.
     * @param gsv   Con esta clase gestionaremos la música
     */
    public Ajustes(Context context, int numEscena, int anp, int alp,GameSV gsv) {
        super(context, anp, alp, numEscena);
        this.gsv = gsv;
        this.numEscena = numEscena;
        this.altoP = alp;
        this.anchoP = anp;



        this.silencio_on = BitmapFactory.decodeResource(context.getResources(), R.drawable.silencio);
        this.ingles = BitmapFactory.decodeResource(context.getResources(),R.drawable.bandera_inglesa);
        this.musica_off = BitmapFactory.decodeResource(context.getResources(),R.drawable.sin_musica);

        this.silencio_off = BitmapFactory.decodeResource(context.getResources(),R.drawable.altavoz);
        this.español = BitmapFactory.decodeResource(context.getResources(),R.drawable.bandera_espana);
        this.musica_on = BitmapFactory.decodeResource(context.getResources(),R.drawable.musica);

        this.btnIdioma = new Rect(anchoP/10*2, altoP/3, anchoP/10*3,
                altoP/3*2);
        this.btnVolumen = new Rect(anchoP/10*4, altoP/3, anchoP/10*5,
                altoP/3*2);
        this.btnMusica = new Rect(anchoP/10*6, altoP/3, anchoP/10*7,
                altoP/3*2);

        //Shared Preferences
        this.sonido = GameSV.sp.getBoolean("sonido_on",true);
        this.musica = GameSV.sp.getBoolean("musica_on",true);
        this.idioma = GameSV.sp.getString("idioma","es");

    }

    /**
     * Dibuja la escena Ajustes en el Canvas especificado.
     *
     * @param c El Canvas en donde será dibujada la escena.
     */
    public void dibuja(Canvas c){
        c.drawColor(Color.GREEN);
        super.dibuja(c);
        c.drawText((String) context.getString(R.string.titulo_ajustes),anchoPantalla/2, altoPantalla/10,p);

        //Dependiendo de las booleanas, se pone un icono u otro.
        if(this.musica){
            c.drawBitmap(this.musica_on,null,btnMusica,null);
        }else{
            c.drawBitmap(this.musica_off,null,btnMusica,null);
        }

        if (this.sonido){
            c.drawBitmap(this.silencio_off,null,btnVolumen,null);
        }else{
            c.drawBitmap(this.silencio_on,null,btnVolumen,null);
        }

        if(idioma.equals("es")){
            c.drawBitmap(this.español,null,btnIdioma,null);
        }else{
            c.drawBitmap(this.ingles,null,btnIdioma,null);
        }


    }

    /**
     * Maneja los eventos táctiles en la pantalla.
     *
     * @param event Representa el evento.
     * @return Devuelve el numero de escena al que se debe cambiar.
     */
    public  int onTouchEvent(MotionEvent event){

        int aux=super.onTouchEvent(event);


        int x=(int)event.getX();
        int y=(int)event.getY();

        if (aux!=this.numEscena && aux!=-1){
            return aux;
        }

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            SharedPreferences.Editor ed = GameSV.sp.edit();

            if (this.btnVolumen.contains(x, y)) {

                this.sonido = !this.sonido;
                ed.putBoolean("sonido_on",this.sonido);
                gsv.setSonido(this.sonido);

            }
            if (btnMusica.contains(x, y)) {

                this.musica = !this.musica;
                ed.putBoolean("musica_on",this.musica);
                gsv.setMusica(this.musica);

            }
            if (this.btnIdioma.contains(x, y)) {
                if(idioma.equals("es")){
                   this.idioma = "en";
                   gsv.CambiarIdioma("en");
                } else {
                    this.idioma = "es";
                    gsv.CambiarIdioma("es");
                }
                ed.putString("idioma",idioma);
            }
            ed.apply();
        }

        return this.numEscena;
    }

}
