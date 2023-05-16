package com.example.runjack;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.MotionEvent;

import java.io.Console;

public class Ajustes extends Escena {
    int numEscena=5;

    GameSV gsv;
    Rect btnIdioma,btnVolumen,btnMusica;
    Bitmap bEsp = BitmapFactory.decodeResource(context.getResources(),R.drawable.bandera_espana);
    Bitmap bMusica = BitmapFactory.decodeResource(context.getResources(),R.drawable.musica);
    Bitmap bVolumen = BitmapFactory.decodeResource(context.getResources(),R.drawable.altavoz);
    GameSV cIdioma;

    Bitmap silencio_on,silencio_off, ingles,español, musica_off,musica_on;
    int anchoP,altoP;

    public Ajustes(Context context, int numEscena, int anp, int alp) {
        super(context, anp, alp, numEscena);
        this.numEscena = numEscena;
        this.altoP = alp;
        this.anchoP = anp;
        cIdioma = new GameSV(context);

        gsv = new GameSV(context);

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

    public void dibuja(Canvas c){
        c.drawColor(Color.GREEN);
        super.dibuja(c);
        c.drawText("Ajustes",anchoPantalla/2, altoPantalla/10,p);
        c.drawBitmap(bMusica,null,btnMusica,null);
        c.drawBitmap(bVolumen,null,btnVolumen,null);
        c.drawBitmap(bEsp,null,btnIdioma,null);

    }

    public void actualizaFisica(){

    }

    public  int onTouchEvent(MotionEvent event){

        int aux=super.onTouchEvent(event);

        int x=(int)event.getX();
        int y=(int)event.getY();

        if (aux!=this.numEscena && aux!=-1){
            return aux;
        }

        if (btnVolumen.contains(x, y)) {

            bVolumen = (bVolumen == silencio_on) ? silencio_off : silencio_on;

        }
        if (btnMusica.contains(x, y)) {

            if(gsv.musica.equals(musica_on)){
                gsv.musica = musica_off;
                gsv.musica_fondo.pause();
            } else {
                gsv.musica = musica_on;
                gsv.musica_fondo.start();
            }

        }
        if (btnIdioma.contains(x, y)) {
            if(gsv.idioma.equals(español)){
                gsv.idioma = ingles;
                gsv.CambiarIdioma("es");
            } else {
                gsv.idioma = español;
                gsv.CambiarIdioma("en");
            }
        }


        return this.numEscena;
    }

}
