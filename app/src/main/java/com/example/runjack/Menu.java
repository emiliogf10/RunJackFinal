package com.example.runjack;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

public class Menu extends Escena {

    /**
     * Número identificativo de la escena.
     */
    int numEscena=1;

    /**
     * Rectángulos de las ecenas y de la imagen del menú.
     */
    Rect btnEscena3,btnEscena4, btnEscena5, btnEscena6,btnEscena7,logo_menu;


    /**
     * Bitmaps de los botones del menú y el del logo.
     */
    Bitmap btnPlay,btnAjustes,btnRecords,btnInformacion,btnCreditos,logo;

    /**
     * Bitmaps de los botones del menú y el del logo, escalados.
     */
    Bitmap play_escalado,ajustes_escalado,trofeo_escalado,informacion_escalado,creditos_escalado,logo_escalado;

    /**
     * Crea un objeto Menu con los parámetros especificados.
     *
     * @param context   Contexto de la aplicación.
     * @param numEscena Numero identificativo de la escena.
     * @param anp   Ancho de la pantalla.
     * @param alp   Alto de la pantalla.
     */
    public Menu(Context context, int numEscena, int anp, int alp) {
        super(context,  anp, alp, numEscena);
        this.numEscena=numEscena;

        this.logo = BitmapFactory.decodeResource(context.getResources(),R.drawable.jetpack2);

        btnPlay = BitmapFactory.decodeResource(context.getResources(), R.drawable.play);
        btnAjustes = BitmapFactory.decodeResource(context.getResources(), R.drawable.ajustes);
        btnRecords = BitmapFactory.decodeResource(context.getResources(), R.drawable.trofeo);
        btnInformacion = BitmapFactory.decodeResource(context.getResources(),R.drawable.informacion);
        btnCreditos = BitmapFactory.decodeResource(context.getResources(),R.drawable.creditos);

        this.play_escalado = Bitmap.createScaledBitmap(this.btnPlay,
                anchoPantalla/8, altoPantalla/8, true);
        this.ajustes_escalado = Bitmap.createScaledBitmap(this.btnAjustes,
                anchoPantalla/8, altoPantalla/8, true);
        this.trofeo_escalado = Bitmap.createScaledBitmap(this.btnRecords,
                anchoPantalla/8, altoPantalla/8, true);
        this.informacion_escalado = Bitmap.createScaledBitmap(this.btnInformacion,
                anchoPantalla/8, altoPantalla/8, true);
        this.creditos_escalado = Bitmap.createScaledBitmap(this.btnCreditos,
                anchoPantalla/8, altoPantalla/8, true);
        this.logo_escalado = Bitmap.createScaledBitmap(this.logo,anchoPantalla/6,altoPantalla/6,true);

        /*btnEscena3 =new Rect(anchoPantalla/5,altoPantalla/8*2,anchoPantalla/5*2,altoPantalla/8*3);
        btnEscena5 =new Rect(anchoPantalla/5,altoPantalla/8*4,anchoPantalla/5*2,altoPantalla/8*5);
        btnEscena6 =new Rect(anchoPantalla/5,altoPantalla/8*6,anchoPantalla/5*2,altoPantalla/8*7);
        btnEscena7 = new Rect(anchoPantalla/5,altoPantalla/8*8,anchoPantalla/5*2,altoPantalla/8*9);*/

        btnEscena3 =new Rect(anchoPantalla/13*2, altoPantalla/6*4, anchoPantalla/13*3,
                altoPantalla/6*5);
        btnEscena4 = new Rect(anchoPantalla/13*10, altoPantalla/6*4, anchoPantalla/13*11,
                altoPantalla/6*5);
        btnEscena5 =new Rect(anchoPantalla/13*4, altoPantalla/6*4, anchoPantalla/13*5,
                altoPantalla/6*5);
        btnEscena6 =new Rect(anchoPantalla/13*6, altoPantalla/6*4, anchoPantalla/13*7,
                altoPantalla/6*5);
        btnEscena7 = new Rect(anchoPantalla/13*8, altoPantalla/6*4, anchoPantalla/13*9,
                altoPantalla/6*5);

        this.logo_menu = new Rect(anchoPantalla/8, altoPantalla/7, anchoPantalla/8*3,
                altoPantalla/7*4);
    }

    /**
     * Dibuja el objeto Menu en el Canvas especificado.
     *
     * @param c El objeto Canvas en el que se dibujará la escena de créditos.
     */
    public void dibuja(Canvas c){
        c.drawColor(Color.RED);

        super.dibuja(c);
        c.drawBitmap(logo_escalado,null,logo_menu,null);
        c.drawBitmap(play_escalado,null,btnEscena3,null);
        c.drawBitmap(ajustes_escalado,null,btnEscena5,null);
        c.drawBitmap(trofeo_escalado,null,btnEscena6,null);
        c.drawBitmap(informacion_escalado,null,btnEscena7,null);
        c.drawBitmap(creditos_escalado,null,btnEscena4,null);

        p.setTextSize(anchoPantalla / 10);

        c.drawText("RUN",anchoPantalla/2, altoPantalla/4,p);
        c.drawText("JACK!",anchoPantalla/3*2, altoPantalla/2,p);
    }


    /**
     * Maneja los eventos táctiles en la pantalla.
     *
     * @param event Representa el evento.
     * @return  Devuelve el numero de escena al que se debe cambiar.
     */
    int onTouchEvent(MotionEvent event){
        int x=(int)event.getX();
        int y=(int)event.getY();
        int aux=super.onTouchEvent(event);

        if (aux!=this.numEscena && aux!=-1) return aux;
        if (btnEscena3.contains(x,y))return 2;
        else if(btnEscena4.contains(x,y))return 4;
        else if (btnEscena5.contains(x,y))return 5;
        else if (btnEscena6.contains(x,y))return 6;
        else if(btnEscena7.contains(x,y))return 7;


        return this.numEscena;
    }

}
