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
    Rect btnEscena3, btnEscena5, btnEscena6;
    Paint boton;
    Bitmap btnPlay,btnAjustes,btnRecords;

    public Menu(Context context, int numEscena, int anp, int alp) {
        super(context,  anp, alp, numEscena);
        this.numEscena=numEscena;
        boton=new Paint();
        btnPlay = BitmapFactory.decodeResource(context.getResources(), R.drawable.play);
        btnAjustes = BitmapFactory.decodeResource(context.getResources(), R.drawable.ajustes);
        btnRecords = BitmapFactory.decodeResource(context.getResources(), R.drawable.trofeo);
        boton.setColor(Color.GREEN);
        btnEscena3 =new Rect(anchoPantalla/5,altoPantalla/8*2,anchoPantalla/5*2,altoPantalla/8*3);
        btnEscena5 =new Rect(anchoPantalla/5,altoPantalla/8*4,anchoPantalla/5*2,altoPantalla/8*5);
        btnEscena6 =new Rect(anchoPantalla/5,altoPantalla/8*6,anchoPantalla/5*2,altoPantalla/8*7);
    }
    public void dibuja(Canvas c){
        c.drawColor(Color.RED);

        super.dibuja(c);
        c.drawBitmap(btnPlay,null,btnEscena3,null);
        c.drawBitmap(btnAjustes,null,btnEscena5,null);
        c.drawBitmap(btnRecords,null,btnEscena6,null);
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


        return this.numEscena;
    }

}
