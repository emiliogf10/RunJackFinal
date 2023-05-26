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

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;

public class Juego extends Escena {
    int numEscena = 2;

    Rect btnPausa;

    Bitmap bitmapFondo,pausa,cohete_escalado,bitmapCohete;
    Canvas c;
    //Timer del cohete
    Timer timer;
    //Intervalo de cada cuanto se ejecuta el timer
    int t = 1500;

    int velocidad_cohete = 6;
    int tJack = 50;
    ArrayList<Cohete> listaCohetes;
    Vec2 gravity;
    World world;
    //Tiempo entre cada ejecucion
    float timeStep = 1.0f / 10.0f;

    //Numero de iteraciones en la fase de velocidad
    int velocidadIteracion = 6;

    //Numero de iteraciones en la fase de posicion
    int iteracionPosicion = 2;

    boolean enPausa = false;

    Jack jack;
    Suelo suelo;
    Explosion explosion;

    Hardware hw;

    //Booleana que si está a true, lanza el GameOver
    boolean fin_juego = false;

    //Se lanza cuando se saca el nivel maximo
    boolean nMaximo = false;

    //Lista de explosiones
    Explosion[] explosionesVarias = new Explosion[4];

    //Sonido de la explosion
    MediaPlayer sonido_explosion;

    boolean aplicoFuerza = false;

    //Contador
    int cont_cohetes = 0;

    //Cuenta los cohetes que se han supera
    int contGeneralCohetes = 0;


    /**
     * Constructor de la clase Juego.
     *
     * @param context   Contexto de la aplicación
     * @param numEscena Numero que identifica la escena
     * @param anp   Ancho de la pantalla
     * @param alp   Alto de la pantalla
     */
    public Juego(Context context, int numEscena, int anp, int alp) {
        super(context,  anp, alp, numEscena);
        this.numEscena=numEscena;

        this.bitmapCohete = BitmapFactory.decodeResource(context.getResources(),R.drawable.cohete);
        this.cohete_escalado = Bitmap.createScaledBitmap(bitmapCohete, (int) (anchoPantalla/5), (int) (altoPantalla/5),false);

        this.hw = new Hardware(context);

        //Sonido de explosion
        sonido_explosion = MediaPlayer.create(context.getApplicationContext(),R.raw.explosion);

        this.listaCohetes = new ArrayList<>();
        explosion = new Explosion(context);

        //Timer cohete
        this.timer = new Timer();
        this.timer.schedule(new CohetesVarios(),2000,t);
        this.timer.schedule(new ImagenesVarias(),500,tJack);

        //Se inicializa el array con 4 explosiones
        this.explosionesVarias[0] = new Explosion(context);
        this.explosionesVarias[1] = new Explosion(context);
        this.explosionesVarias[2] = new Explosion(context);
        this.explosionesVarias[3] = new Explosion(context);

        //Creacion del mundo
        this.gravity = new Vec2(2.0f, -10.0f);
        this.world = new World(gravity);
        /*world.setSleepingAllowed(doSleep);*/



        //Boton pausa
        this.pausa = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.pausa);
        this.btnPausa = new Rect(anchoPantalla / 15 * 13, altoPantalla / 10, anchoPantalla / 15 * 14
                , altoPantalla / 10 + 50);

        //Botones de los menus de pausa y GameOver
        btnCasa = new RectF((float)anchoPantalla/9*2, (float)altoPantalla/7*4, (float)anchoPantalla/9*4, (float)altoPantalla/7*5);
        btnResume = new RectF(anchoPantalla/9*5, altoPantalla/7*4, anchoPantalla/9*7, altoPantalla/7*5);

        //Creacion de Jack y del suelo
        jack = new Jack(context,world,4,1,anchoPantalla,altoPantalla,anchoPantalla / 6,altoPantalla/3);
        suelo = new Suelo(context,world, 0.9f, 0.1f,anchoPantalla,altoPantalla);


    }

    /**
     * Metodo en el que se dibuja la escena en el Canvas especificado.
     *
     * @param c El Canvas sobre el que debe dibujarse la escena.
     */
    public void dibuja(Canvas c){
        try{
            c.drawColor(Color.WHITE);
            super.dibuja(c);

            suelo.dibuja(c);
            jack.dibuja(c);

            //Si las booleanas estan a false se siguen creando cohetes
            if(!enPausa && !fin_juego){

                for(Cohete cohete : listaCohetes){
                    cohete.dibuja(c);
                    cohete.movimiento(altoPantalla,anchoPantalla,velocidad_cohete);
                }

                if(!nMaximo){
                    if(cont_cohetes == 5){
                        t = t - 500;
                        velocidad_cohete = velocidad_cohete + 1;
                        cont_cohetes = 0;
                    }

                    if(t <= 500){
                        nMaximo = true;
                    }
                }else{
                    t = 250;
                    velocidad_cohete = 12;

                }



            }else if(enPausa && !fin_juego){
                pantallaPausa(c);

            }else{
                pantallaGameOver(c);
                this.timer.cancel();
                this.listaCohetes.clear();

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
                    if(explosion.frame == 19){
                        pantallaGameOver(c);
                        liberarRecursos();

                    }
            }

        }



    }

    //Se detectan colisiones; si hay una colision, se elimina el cohete y se pone booleana a true.
    @Override
    public void actualizaFisica(){
        world.step(timeStep,velocidadIteracion,iteracionPosicion);

        //Se hace que Jack caiga hacia abajo
        if(!jack.hitbox.intersect(suelo.hitbox)){
            float y = jack.posicion.y - gravity.y;
            jack.posicion.y = y;
            this.jack.actualizaHit();
        }
        Log.i("GRAVI","x: "+jack.getX() + " y: " + jack.getY());

        if(!fin_juego){
            for(int i=listaCohetes.size() -1;i >= 0;i--){
                if(listaCohetes.get(i).detectarColision(jack.getHitBox())){
                    fin_juego = true;
                    cont_cohetes = 0;
                    listaCohetes.remove(listaCohetes.get(i));
                    this.hw.vibra();
                    sonido_explosion.start();
                    Log.i("COLISION","SI Colision");
                }

                //SE ELIMINAN LOS COHETES CUANDO SALEN DE LA PANTALLA
                /*if(listaCohetes.get(i).pos.y > anchoPantalla){
                    listaCohetes.remove(listaCohetes.get(i));
                    Log.i("ELIMINAR","cohete eliminado");
                }*/

                listaCohetes.get(i).actualizaHit();
            }
        }


        if(aplicoFuerza){
            jack.aplicarFuerza();
        }


        this.jack.actualizaHit();


    }

    @Override
    public int onTouchEvent(MotionEvent event){
        //Recogemos la pulsacion
        int accion = event.getAction();

        switch (accion){
            case MotionEvent.ACTION_DOWN:
                aplicoFuerza = true;
                Log.i("TOUCH","tocaste la pantalla");
                jack.aplicarFuerza(Jack.getFuerza(120f, 160f),  -10);
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                aplicoFuerza = false;
                break;
            case MotionEvent.ACTION_UP:
                aplicoFuerza = false;
                break;

        }

        int x=(int)event.getX();
        int y=(int)event.getY();

        int aux=super.onTouchEvent(event);
        if (aux!=this.numEscena && aux!=-1) return aux;
        if (btnPausa.contains(x,y)){
            this.enPausa = true;
        }
        if(this.enPausa || this.fin_juego){
            if(btnResume.contains(x,y)){
                this.enPausa = false;
            }

            if(btnCasa.contains(x,y)){
                liberarRecursos();
                return 1;

            }
        }


        return this.numEscena;




    }

    /**
     * Función que es llamada por el timer de cohete, y que a su vez llama a AñadirCohete(c).
     */
    //Funcion que se llama desde el timer
    private class CohetesVarios extends java.util.TimerTask {
        @Override
        public void run(){
            AñadirCohete(c);
        }
    }

    /**
     * Funcion que crea un nuevo cohete y lo añade a la lista.
     *
     * @param c Canvas en donde se dibujará el cohete.
     */
    //Funcion para añadir un nuevo cohete y añadirlo a la lista
    private void AñadirCohete(Canvas c){
        if(!enPausa && !fin_juego){
            /*Random r = new Random();
            float aleatorio = r.nextFloat() * (altoPantalla - suelo.getHitBox().top)+ suelo.getHitBox().top;*/
            /*Cohete cohete = new Cohete(context,anchoPantalla,new Random().nextFloat()*(altoPantalla-cohete_escalado.getHeight()),anchoPantalla,altoPantalla);
            listaCohetes.add(cohete);*/
            Cohete cohete = new Cohete(context,anchoPantalla,300,anchoPantalla,altoPantalla);
            listaCohetes.add(cohete);
            cont_cohetes++;
            Log.i("cohete","añade cohete" + "\tCont: " + cont_cohetes + "\tIntervalo: " + t + "\tVelocidad: " + velocidad_cohete);

        }

    }


    /**
     * Función que es llamada por el timer y que a su vez llama a jack.dibujaAnimaciones() y dibuja la animacion de Jack.
     */
    public class ImagenesVarias extends java.util.TimerTask {
        @Override
        public void run(){

            if(!enPausa && !fin_juego){
                jack.dibujaAnimaciones();
            }
        }
    }

    /**
     * Función que libera recursos.
     */
    public void liberarRecursos(){
        //Libera recursos del timer
        this.timer.cancel();

        //Limpia la lista de cohetes.
        this.listaCohetes.clear();
    }




}
