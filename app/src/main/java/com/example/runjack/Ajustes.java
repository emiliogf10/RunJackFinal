package com.example.runjack;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.MotionEvent;

public class Ajustes extends Escena {
    int numEscena=5;
    Rect btnIdioma,btnVolumen,btnMusica;
    Bitmap bIdioma,bVolumen,bMusica;
    int anchoP,altoP;

    public Ajustes(Context context, int numEscena, int anp, int alp) {
        super(context, anp, alp, numEscena);
        this.numEscena = numEscena;
        this.altoP = alp;
        this.anchoP = anp;

        this.bIdioma = BitmapFactory.decodeResource(context.getResources(),R.drawable.bandera_espana);
        this.bMusica = BitmapFactory.decodeResource(context.getResources(),R.drawable.musica);
        this.bVolumen = BitmapFactory.decodeResource(context.getResources(),R.drawable.altavoz);

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
        c.drawBitmap(bIdioma,null,btnIdioma,null);

    }

    public void actualizaFisica(){

    }

    public  int onTouchEvent(MotionEvent event){

        int aux=super.onTouchEvent(event);
        if (aux!=this.numEscena && aux!=-1) return aux;
        int x=(int)event.getX();
        int y=(int)event.getY();

        if (btnVolumen.contains(x, y)) {
            bVolumen = (bVolumen.equals(BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.altavoz))) ? BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.altavoz) : BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.silencio);

        } else if (btnMusica.contains(x, y)) {
            bMusica = (bMusica.equals(BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.musica))) ? BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.musica) : BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.sin_musica);

        } else if (btnIdioma.contains(x, y)) {
            bIdioma =
                    (bIdioma.equals(BitmapFactory.decodeResource(context.getResources(),
                            R.drawable.bandera_espana))) ? BitmapFactory.decodeResource(context.getResources(),
                            R.drawable.bandera_espana) : BitmapFactory.decodeResource(context.getResources(),
                            R.drawable.bandera_inglesa);
        }


        return this.numEscena;
    }

}
