package com.example.runjack;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Timer;

public class Juego extends Escena {
    int numEscena=2;

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
    Suelo suelo;
    Explosion explosion;

    Hardware hw;

    //Booleana que si está a true, lanza el GameOver
    boolean fin_juego = false;

    //Lista de explosiones
    Explosion[] explosionesVarias = new Explosion[4];

    //Sonido de la explosion
    MediaPlayer sonido_explosion;


    /**
     *Método que construye una instancia de Juego (constructor)
     *
     * @param context   Contexto de la aplicacion
     * @param numEscena Numero que identifica la escena
     * @param anp       Ancho de la pantalla
     * @param alp       Alto de la pantalla
     */
    public Juego(Context context, int numEscena, int anp, int alp) {
        super(context,  anp, alp, numEscena);
        this.numEscena=numEscena;

        this.hw = new Hardware(context);

        //Sonido de explosion
        sonido_explosion = MediaPlayer.create(context.getApplicationContext(),R.raw.explosion);

        this.listaCohetes = new ArrayList<>();
        explosion = new Explosion(context);

        //Timer cohete
        this.timerCohete = new Timer();
        this.timerCohete.schedule(new CohetesVarios(),3000,t);

        //Se inicializa el array con 4 explosiones
        this.explosionesVarias[0] = new Explosion(context);
        this.explosionesVarias[1] = new Explosion(context);
        this.explosionesVarias[2] = new Explosion(context);
        this.explosionesVarias[3] = new Explosion(context);

        //Creacion del mundo
        this.gravity = new Vec2(2.0f, -10.0f);
        this.world = new World(gravity);
        /*world.setSleepingAllowed(doSleep);*/

        //Bitmap del cohete con su correspondiente escalado
        this.bitmapCohete = BitmapFactory.decodeResource(context.getResources(),R.drawable.cohete);
        this.cohete_escalado = Bitmap.createScaledBitmap(bitmapCohete,anchoPantalla/5,altoPantalla/5,false);

        //Boton pausa
        this.pausa = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.pausa);
        this.btnPausa = new Rect(anchoPantalla / 15 * 13, altoPantalla / 10, anchoPantalla / 15 * 14
                , altoPantalla / 10 + 50);

        //Botones de los menus de pausa y GameOver
        btnCasa = new RectF((float)anchoPantalla/9*2, (float)altoPantalla/7*4, (float)anchoPantalla/9*4, (float)altoPantalla/7*5);
        btnResume = new RectF(anchoPantalla/9*5, altoPantalla/7*4, anchoPantalla/9*7, altoPantalla/7*5);

        //Creacion de Jack y del suelo
        jack = new Jack(context,world,4,1,anchoPantalla,altoPantalla);
        suelo = new Suelo(context,world, 0.9f, 0.1f,anchoPantalla,altoPantalla);


    }

    /**
     * Metodo en el que se dibuja la escena en el canvas
     *
     * @param c El lienzo sobre el que debe dibujarse la escena.
     */
    public void dibuja(Canvas c){
        try{
            c.drawColor(Color.WHITE);
            super.dibuja(c);

            jack.dibuja(c);
            /*suelo.dibuja(c);*/

            //Si las booleanas estan a false se siguen creando cohetes
            if(!enPausa && !fin_juego){

                for(Cohete cohete : listaCohetes){
                    c.drawBitmap(cohete.imagen,cohete.pos.x,cohete.pos.y,null);
                    cohete.movimiento(altoPantalla,anchoPantalla,6);
                }
            }else{
                pantallaPausa(c);
            }

            //Se dibuja el boton pausa
            c.drawBitmap(pausa,null,btnPausa,null);



        }catch (Exception e){
            e.printStackTrace();
        }

        if(fin_juego){
            for(Explosion explosion : explosionesVarias){
                //CAMBIAR POSICIONES DE LAS EXPLOSIONES
                explosion.dibujaExplosion(c,this.jack.posicion.x,this.jack.posicion.y);
            }
            pantallaGameOver(c);



        }

    }

//Se detectan colisiones; si hay una colision, se elimina el cohete y se pone booleana a true.
    @Override
    public void actualizaFisica(){
        if(!fin_juego){
            for(int i=listaCohetes.size() -1;i >= 0;i--){
                if(listaCohetes.get(i).detectarColision(jack)){
                    fin_juego = true;
                    listaCohetes.remove(listaCohetes.get(i));
                    this.hw.vibra();
                    sonido_explosion.start();
                    Log.i("COLISION","SI Colision");
                }
            }
        }


        this.jack.actualizaHit();

    }

    @Override
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

    /**
     * Funcion que crea un nuevo cohete y lo añade a la lista.
     *
     * @param c Canvas en donde se dibujará el cohete
     */
    //Funcion para añadir un nuevo cohete y añadirlo a la lista
    private void AñadirCohete(Canvas c){
        if(!enPausa && !fin_juego){
            Cohete cohete = new Cohete(cohete_escalado,anchoPantalla,new Random().nextFloat()*(altoPantalla-cohete_escalado.getHeight()));
            listaCohetes.add(cohete);
        }

    }

    /**
     * Funcion que es llamada por el timer de cohete, y que a su vez llama a AñadirCohete(c).
     */
    //Funcion que se llama desde el timer
    private class CohetesVarios extends java.util.TimerTask {
        @Override
        public void run(){
            AñadirCohete(c);
        }
    }



}
