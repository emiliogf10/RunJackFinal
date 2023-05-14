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
    int numEscena=1;
    Rect btnEscena3, btnEscena5, btnEscena6,btnEscena7,logo_menu;
    Paint boton;
    Bitmap btnPlay,btnAjustes,btnRecords,btnInformacion,logo;

    Bitmap play_escalado,ajustes_escalado,trofeo_escalado,informacion_escalado,logo_escalado;

    public Menu(Context context, int numEscena, int anp, int alp) {
        super(context,  anp, alp, numEscena);
        this.numEscena=numEscena;
        boton=new Paint();

        this.logo = BitmapFactory.decodeResource(context.getResources(),R.drawable.logo_menu);

        btnPlay = BitmapFactory.decodeResource(context.getResources(), R.drawable.play);
        btnAjustes = BitmapFactory.decodeResource(context.getResources(), R.drawable.ajustes);
        btnRecords = BitmapFactory.decodeResource(context.getResources(), R.drawable.trofeo);
        btnInformacion = BitmapFactory.decodeResource(context.getResources(),R.drawable.informacion);

        this.play_escalado = Bitmap.createScaledBitmap(this.btnPlay,
                anchoPantalla/8, altoPantalla/8, true);
        this.ajustes_escalado = Bitmap.createScaledBitmap(this.btnAjustes,
                anchoPantalla/8, altoPantalla/8, true);
        this.trofeo_escalado = Bitmap.createScaledBitmap(this.btnRecords,
                anchoPantalla/8, altoPantalla/8, true);
        this.informacion_escalado = Bitmap.createScaledBitmap(this.btnInformacion,
                anchoPantalla/8, altoPantalla/8, true);
        this.logo_escalado = Bitmap.createScaledBitmap(this.logo,anchoPantalla/6,altoPantalla/6,true);

        boton.setColor(Color.GREEN);
        /*btnEscena3 =new Rect(anchoPantalla/5,altoPantalla/8*2,anchoPantalla/5*2,altoPantalla/8*3);
        btnEscena5 =new Rect(anchoPantalla/5,altoPantalla/8*4,anchoPantalla/5*2,altoPantalla/8*5);
        btnEscena6 =new Rect(anchoPantalla/5,altoPantalla/8*6,anchoPantalla/5*2,altoPantalla/8*7);
        btnEscena7 = new Rect(anchoPantalla/5,altoPantalla/8*8,anchoPantalla/5*2,altoPantalla/8*9);*/

        btnEscena3 =new Rect(anchoPantalla/13*2, altoPantalla/6*4, anchoPantalla/13*3,
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
    public void dibuja(Canvas c){
        c.drawColor(Color.RED);

        super.dibuja(c);
        c.drawBitmap(logo_escalado,null,logo_menu,null);
        c.drawBitmap(play_escalado,null,btnEscena3,null);
        c.drawBitmap(ajustes_escalado,null,btnEscena5,null);
        c.drawBitmap(trofeo_escalado,null,btnEscena6,null);
        c.drawBitmap(informacion_escalado,null,btnEscena7,null);
        c.drawText("RUN JACK!",anchoPantalla/2, altoPantalla/10,p);
    }

    int onTouchEvent(MotionEvent event){
        int x=(int)event.getX();
        int y=(int)event.getY();
        int aux=super.onTouchEvent(event);
        Log.i("boton", "onTouchEvent: "+(btnEscena3.contains(x,y))+" "+aux+" "+this.numEscena);

        if (aux!=this.numEscena && aux!=-1) return aux;
        if (btnEscena3.contains(x,y))return 2;
        else if (btnEscena5.contains(x,y))return 5;
        else if (btnEscena6.contains(x,y))return 6;
        else if(btnEscena7.contains(x,y))return 7;


        return this.numEscena;
    }

}
