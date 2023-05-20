package com.example.runjack;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;

public class Informacion extends Escena {

    int numEscena = 7;
    ScrollView scrollView;
    LinearLayout linearLayout;

    ImageView iv1;
    ImageView iv2;

    public Informacion(Context context,int numEscena, int anchoPantalla, int altoPantalla) {
        super(context, anchoPantalla, altoPantalla, numEscena);
        this.numEscena = numEscena;


    }

    public void dibuja(Canvas c){
        super.dibuja(c);
        c.drawText(context.getString(R.string.information_title),anchoPantalla/2, altoPantalla/10,p);
    }

    @Override
    int onTouchEvent(MotionEvent event){

        int aux=super.onTouchEvent(event);
        if (aux!=this.numEscena && aux!=-1) return aux;
        return this.numEscena;
    }
}
