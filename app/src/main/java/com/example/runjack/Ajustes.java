package com.example.runjack;

import static com.example.runjack.Juego.sonido_explosion;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.MotionEvent;

public class Ajustes extends Escena {
    /**
     * Número identificativo de las escena.
     */
    int numEscena=5;

    /**
     * Objeto GameSV con el contexto especificado.
     */
    GameSV gsv = new GameSV(context);

    /**
     *  Rectángulos de los botones de idioma,música y volumen.
     */
    Rect btnIdioma,btnVolumen,btnMusica;

    /**
     * Imagen del botón de Idioma.
     */
    Bitmap bEsp = BitmapFactory.decodeResource(context.getResources(),R.drawable.bandera_espana);

    /**
     * Imagen del botón de música.
     */
    Bitmap bMusica = BitmapFactory.decodeResource(context.getResources(),R.drawable.musica);

    /**
     * Imagen del botón de volumen.
     */
    Bitmap bVolumen = BitmapFactory.decodeResource(context.getResources(),R.drawable.altavoz);

    /**
     * Idioma del juego.
     */
    GameSV cIdioma;

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
     */
    public Ajustes(Context context, int numEscena, int anp, int alp) {
        super(context, anp, alp, numEscena);
        this.numEscena = numEscena;
        this.altoP = alp;
        this.anchoP = anp;



        this.silencio_on = BitmapFactory.decodeResource(context.getResources(),R.drawable.silencio);
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

    }

    /**
     * Dibuja la escena Ajustes en el Canvas especificado.
     *
     * @param c El Canvas en donde será dibujada la escena.
     */
    public void dibuja(Canvas c){
        c.drawColor(Color.GREEN);
        super.dibuja(c);
        c.drawText("Ajustes",anchoPantalla/2, altoPantalla/10,p);
        c.drawBitmap(bMusica,null,btnMusica,null);
        c.drawBitmap(bVolumen,null,btnVolumen,null);
        c.drawBitmap(bEsp,null,btnIdioma,null);

    }

    /**
     * Actualiza la fisica de los elementos de la escena.
     */
    public void actualizaFisica(){

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
            if (btnVolumen.contains(x, y)) {

                bVolumen = (bVolumen == silencio_on) ? silencio_off : silencio_on;

                if(bVolumen == silencio_on){
                    bVolumen = silencio_off;
                    /*sonido_explosion.start();*/
                    gsv.btnSonido = silencio_off;
                }else{
                    bVolumen = silencio_on;
                    /*sonido_explosion.stop();*/
                    gsv.btnSonido = silencio_on;
                }

            }
            if (btnMusica.contains(x, y)) {


                if(bMusica == musica_on && gsv.btnMusica.equals(musica_on)){
                    bMusica = musica_off;
                    gsv.btnMusica = musica_off;
                    gsv.musica_fondo.pause();

                }else{
                    bMusica = musica_on;

                    gsv.musica_fondo.start();
                }

            }
            if (btnIdioma.contains(x, y)) {
                if(bEsp == español && gsv.btnIdioma.equals(español)){
                    bEsp = ingles;
                    gsv.btnIdioma = ingles;
                    gsv.CambiarIdioma("es");
                } else {
                    bEsp = español;
                    gsv.btnIdioma = español;
                    gsv.CambiarIdioma("en");
                }
            }
        }




        return this.numEscena;
    }

}
