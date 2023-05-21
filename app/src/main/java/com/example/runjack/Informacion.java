package com.example.runjack;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;

public class Informacion extends Escena {

    int numEscena = 7;

    Bitmap inf_esp,inf_ing;
    Bitmap esc_esp,esc_ing;

    public Informacion(Context context,int numEscena, int anchoPantalla, int altoPantalla) {
        super(context, anchoPantalla, altoPantalla, numEscena);
        this.numEscena = numEscena;

        this.inf_esp = BitmapFactory.decodeResource(context.getResources(),R.drawable.fondo);

        this.esc_esp = Bitmap.createScaledBitmap(this.inf_esp,anchoPantalla+10,altoPantalla+10,true);


    }

    public void dibuja(Canvas c){
        super.dibuja(c);
        c.drawText(context.getString(R.string.information_title),anchoPantalla/2, altoPantalla/10,p);

        if(GameSV.idioma.equals("es")){
            
        }else{

        }
    }

    @Override
    int onTouchEvent(MotionEvent event){

        int aux=super.onTouchEvent(event);
        if (aux!=this.numEscena && aux!=-1) return aux;
        return this.numEscena;
    }
}
