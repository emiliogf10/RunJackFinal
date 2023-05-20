package com.example.runjack;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.MotionEvent;

import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;

public class Juego extends Escena {
    int numEscena=2;
    Cohete cohete;

    Rect btnPausa;
    Bitmap bitmapCohete,bitmapFondo,cohete_escalado,pausa;

    GameSV game;
    Canvas c;
    Timer timerCohete;
    int t = 8000;
    ArrayList<Cohete> listaCohetes;

    Bitmap[] imagenesJack;
    Vec2 gravity;
    World world;
    boolean doSleep = true,enPausa = false;

    Jack jack;

    /*Texture textureJack;

    Sprite sprite;

    SpriteBatch batch;*/




    public Juego(Context context, int numEscena, int anp, int alp) {
        super(context,  anp, alp, numEscena);
        this.numEscena=numEscena;
        this.listaCohetes = new ArrayList<>();
        this.timerCohete = new Timer();
        this.timerCohete.schedule(new CohetesVarios(),3000,t);
        gravity = new Vec2(0.0f, -10.0f);
        world = new World(gravity);
        world.setSleepingAllowed(doSleep);
        bitmapCohete = BitmapFactory.decodeResource(context.getResources(),R.drawable.cohete);
        cohete_escalado = Bitmap.createScaledBitmap(bitmapCohete,anchoPantalla/5,altoPantalla/5,false);
        cohete = new Cohete(cohete_escalado,anchoPantalla,new Random().nextFloat()*(altoPantalla-cohete_escalado.getHeight()));
        //Boton pausa
        this.pausa = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.pausa);
        this.btnPausa = new Rect(anchoPantalla / 15 * 13, altoPantalla / 10, anchoPantalla / 15 * 14
                , altoPantalla / 10 + 50);

        if(acabado){
            btnCasa = new RectF((float)anchoPantalla/9*4, (float)altoPantalla/7*4,
                    (float)anchoPantalla/9*6, (float)altoPantalla/7*5);
        } else {
            btnCasa = new RectF((float)anchoPantalla/9*2, (float)altoPantalla/7*4, (float)anchoPantalla/9*4, (float)altoPantalla/7*5);
            btnResume = new RectF((float)anchoPantalla/9*5, (float)altoPantalla/7*4, (float)anchoPantalla/9*7, (float)altoPantalla/7*5);
        }

        //AQUI
        jack = new Jack(world,new RectF(100,200,50,100),4,1);



        /*int[] recursosJack = {
                R.drawable.jack1, R.drawable.jack2, R.drawable.jack3, R.drawable.jack4, R.drawable.jack5,
                R.drawable.jack6, R.drawable.jack7, R.drawable.jack8, R.drawable.jack9, R.drawable.jack10,
                R.drawable.jack11, R.drawable.jack12, R.drawable.jack13, R.drawable.jack14, R.drawable.jack15
        };
        for (int i = 0; i < imagenesJack.length; i++) {
            imagenesJack[i] = BitmapFactory.decodeResource(context.getResources(), recursosJack[i]);
        }*/


    }
    public void dibuja(Canvas c){
        c.drawColor(Color.WHITE);
        super.dibuja(c);
        /*c.drawText("Juego",anchoPantalla/2, altoPantalla/10,p);*/
        /*game.dibujar(c);*/

        if(!enPausa){
            for(Cohete cohete : listaCohetes){
                c.drawBitmap(cohete.imagen,cohete.pos.x,cohete.pos.y,null);
                cohete.movimiento(altoPantalla,anchoPantalla,6);
            }
        }else{
            pantallaPausa(c,false);
        }


        c.drawBitmap(pausa,null,btnPausa,null);

        jack.dibuja(c);


    }




    public void actualizaFisica(){

    }

    int onTouchEvent(MotionEvent event){
        int x=(int)event.getX();
        int y=(int)event.getY();

        int aux=super.onTouchEvent(event);
        if (aux!=this.numEscena && aux!=-1) return aux;
        if (btnPausa.contains(x,y)){
            this.enPausa = true;
        }

        if(btnResume.contains(x,y)){
            this.enPausa = false;
        }

        if(btnCasa.contains(x,y)){
            return 1;
        }
        return this.numEscena;
    }

    //Funcion para crear un cohete
    private void crearCohete(int velocidad,Canvas c){
        c.drawBitmap(cohete.imagen, cohete.pos.x, cohete.pos.y,null);
        cohete.movimiento(altoPantalla,anchoPantalla,velocidad);
        listaCohetes.add(cohete);

    }

    //Funcion para añadir cohetes a la lista
    private void AñadirCohete(Canvas c){
        Cohete cohete = new Cohete(cohete_escalado,anchoPantalla,new Random().nextFloat()*(altoPantalla-cohete_escalado.getHeight()));
        listaCohetes.add(cohete);
    }


    private void crearJack(){

    }

    private class CohetesVarios extends java.util.TimerTask {
        @Override
        public void run(){
            AñadirCohete(c);
        }
    }



}
