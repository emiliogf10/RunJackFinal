package com.example.runjack;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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

/**
 *  Clase del juego en RunJack!
 *  En ella nos podemos enconntrar las colisiones,movimiento del personaje y los cohetes y la actualizión de  estos
 *
 *  @author Emilio
 *  @version 1
 */
public class Juego extends Escena {

    /**
     * Numero identificativo de la escena.
     */
    int numEscena = 2;

    /**
     * Instanciación de un mundo.
     */
    World world;

    /**
     * Instanciación de la clase Jack.
     */
    Jack jack;
    /**
     * Instanciación de la clase Suelo.
     */
    Suelo suelo;
    /**
     * Instanciación de la clase Explosion.
     */
    Explosion explosion;

    /**
     * Instanciación de la clase Hardware.
     */
    Hardware hw;

    /**
     * Rectangulo del botón pausa.
     */
    Rect btnPausa;

    /**
     * Bitmaps del cohete y del escalado del cohete.
     */

    Bitmap pausa,cohete_escalado,bitmapCohete;

    /**
     * Lienzo sobre el que se va a dibujar.
     */
    Canvas c;

    /**
     * Timer del cohete.
     */
    Timer timer;

    /**
     * Intervalo de cada cuanto se crea un cohete.
     */
    int t = 2500;

    /**
     * Velocidad del cohete.
     */
    int velocidad_cohete = 6;

    /**
     * Intervalo de cada cuanto se cambia cada animación de Jack.
     */
    int tJack = 30;

    /**
     * Lista de cohetes.
     */
    ArrayList<Cohete> listaCohetes;

    /**
     * Gravedad con la que se comporta nuestro mundo
     */
    Vec2 gravity;

    /**
     * Tiempo entre cada ejecución del mundo.
     */
    float timeStep = 1.0f / 10.0f;

    /**
     * Número de iteraciones en la fase de velocidad del mundo.
     */
    int velocidadIteracion = 6;

    /**
     * Número de iteraciones en la fase de posición del mundo.
     */
    int iteracionPosicion = 2;

    /**
     * Booleana que si está a true indica que el juego está en pausa.
     */
    boolean enPausa = false;

    /**
     * Booleana que si está a true indica el final del juego.
     */
    boolean fin_juego = false;

    /**
     * Booleana que si está a true, indica que se alcanzó el nivel máximo.
     * A partir de aqui, los cohetes saldrán a una velocidad fija y cada 500 ms.
     */
    boolean nMaximo = false;

    /**
     * Array de explosiones(muestra varias explosiones en pantalla cuando un cohete impacta con Jack).
     */
    Explosion[] explosionesVarias = new Explosion[4];

    /**
     * Sonido de la explosión.
     */
    public static MediaPlayer sonido_explosion;

    /**
     * Booleana que si está a true, indica que se le aplica fuerza a Jack(salta).
     */
    boolean aplicoFuerza = false;

    /**
     * Cantidad de cohetes que se van creando.
     */
    int cont_cohetes = 0;


    /**
     * Numero de cohetes que salieron fuera de la pantalla.
     */
    int contCohetesSalieron = 0;

    /**
     * Niveles del juego; en donde consigues diferentes puntos por esquivar cohetes dependiendo
     * en el nivel en el que estés.
     */

    int nivel = 1;

    /**
     * Pintura para la puntuacion
     */
    Paint paint_puntuacion;

    /**
     * Instancia de la clase Techo.
     */
    Techo techo;


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

        this.hw = new Hardware(context,this);

        this.paint_puntuacion = new Paint();
        this.paint_puntuacion.setTextSize(altoPantalla/10);
        this.paint_puntuacion.setColor(Color.WHITE);
        this.paint_puntuacion.setTextAlign(Paint.Align.CENTER);
        this.paint_puntuacion.setTypeface(tf);

        //Sonido de explosion
        sonido_explosion = MediaPlayer.create(context.getApplicationContext(),R.raw.explosion);

        this.listaCohetes = new ArrayList<>();

        //Timer cohete
        this.timer = new Timer();
        this.timer.schedule(new CohetesVarios(),2000,t);
        this.timer.schedule(new CohetesVarios(),3000,t);
        this.timer.schedule(new ImagenesVarias(),500,tJack);
        this.timer.schedule(new ControlaGiro(),10000,15000);

        //Se inicializa el array con 4 explosiones
        this.explosionesVarias[0] = new Explosion(context);
        this.explosionesVarias[1] = new Explosion(context);
        this.explosionesVarias[2] = new Explosion(context);
        this.explosionesVarias[3] = new Explosion(context);

        //Creación del mundo
        this.gravity = new Vec2(2.0f, -8.0f);
        this.world = new World(gravity);



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

        techo = new Techo(context,world, 0.9f, 0.1f,anchoPantalla,altoPantalla);


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
            techo.dibuja(c);

            jack.dibuja(c);


            c.drawText(GameSV.puntuacion + "",(float) anchoPantalla / 10, (float) altoPantalla / 10 + 50, paint_puntuacion);

            //Si las booleanas estan a false se siguen creando cohetes
            if(!enPausa && !fin_juego){

                for(Cohete cohete : listaCohetes){

                        cohete.dibuja(c);


                    cohete.movimiento(altoPantalla,anchoPantalla,velocidad_cohete);

                    //Si el cohete sale de la pantalla, se elimina de la lista.
                    if(cohete.pos.x < 0 || cohete.pos.y > altoPantalla || cohete.pos.y < suelo.getHitBox().height()){
                        listaCohetes.remove(cohete);
                        contCohetesSalieron++;

                        switch (nivel){
                            case 1:
                                GameSV.puntuacion += 5;
                                break;
                            case 2:
                                GameSV.puntuacion += 8;
                                break;
                            case 3:
                                GameSV.puntuacion += 10;
                                break;
                            case 4:
                                GameSV.puntuacion += 12;
                                break;
                            case 5:
                                GameSV.puntuacion += 14;
                                break;
                        }



                        Log.i("salieron","salieron: " + contCohetesSalieron + "\tCohete: " + cohete + "\tPuntuación total: " + GameSV.puntuacion + "\tNivel: " + nivel);
                    }
                }

                //Mientras el nivel maximo está a false, se sigue aumentando la velocidad.
                if(!nMaximo){
                    if(cont_cohetes == 10){
                        t = t - 500;
                        nivel += 1;
                        velocidad_cohete = velocidad_cohete + 1;
                        cont_cohetes = 0;
                    }

                    if(t <= 500){
                        nMaximo = true;
                    }
                }else{
                    t = 1000;
                    nivel = 5;
                    velocidad_cohete = 12;

                }



            }else if(enPausa && !fin_juego){
                pantallaPausa(c);
                aplicoFuerza = false;

            }

            //Se dibuja el boton pausa
            c.drawBitmap(pausa,null,btnPausa,null);



        }catch (Exception e){
            e.printStackTrace();
        }

        if(fin_juego){

            aplicoFuerza = false;
            this.explosion.dibujaExplosion(c,this.jack.posicion.x,this.jack.posicion.y);
            if(explosion.frame == explosion.explosiones.length - 1){
                this.paint_puntuacion.setColor(Color.WHITE);
                pantallaGameOver(c);
                liberarRecursos();


            }


        }



    }

    //Se detectan colisiones; si hay una colision, se elimina el cohete y se pone booleana a true.
    @Override
    public void actualizaFisica(){
        world.step(timeStep,velocidadIteracion,iteracionPosicion);

        if(aplicoFuerza){
            jack.aplicarFuerza(Jack.getFuerza(120f, 160f),  7);

        }

        //Se hace que Jack caiga hacia abajo con la propia gravedad
        if(!jack.hitbox.intersect(suelo.hitbox) && !jack.isEnSuelo() && !aplicoFuerza){
            if(!enPausa){
                float y = jack.posicion.y - gravity.y;
                jack.posicion.y = y;
                this.jack.actualizaHit();
            }

        }else{
            if(!jack.isEnSuelo() && !aplicoFuerza){
                jack.setEnSuelo(true);
                jack.posicion.y = suelo.hitbox.top - jack.getImagenesJack()[0].getHeight();
            }
        }
        Log.i("GRAVI","x: "+jack.getX() + " y: " + jack.getY());

        if(!fin_juego){
            for(int i=listaCohetes.size() -1;i >= 0;i--){
                if(listaCohetes.get(i).detectarColision(jack.getHitBox())){
                    listaCohetes.remove(listaCohetes.get(i));
                    explosion = new Explosion(context);
                    fin_juego = true;
                    //Se comprueba que la puntuación es mayor que 0; si lo es, se inserta en la base de datos.
                    if(GameSV.puntuacion > 0){
                        MainActivity.base_Datos.añadirPuntos(GameSV.puntuacion);
                    }
                    cont_cohetes = 0;
                    this.nivel = 1;


                    if(GameSV.sonido_on){
                        this.hw.vibra();
                        sonido_explosion.start();
                    }

                    Log.i("COLISION","SI Colision");
                }


                listaCohetes.get(i).actualizaHit();
            }

            //Se controla que Jack no salga de la pantalla mediante el techo.
            if(jack.hitbox.intersect(techo.hitbox) && !jack.isEnSuelo() && aplicoFuerza){
                jack.fuerza.y = 0;
                float y = jack.posicion.y - gravity.y;
                jack.posicion.y = y;
                this.jack.actualizaHit();
            }

            Log.i("fuerza","AplicoFuerza: " + aplicoFuerza + "\tPosY: " + jack.posicion.y );
            if(aplicoFuerza && jack.isEnSuelo()){

                jack.aplicarFuerza();
                jack.setEnSuelo(false);
                this.jack.actualizaHit();

            }else{
                if(aplicoFuerza){
                    jack.aplicarFuerza();
                    jack.actualizaHit();
                }
            }

            if(enPausa){
                hw.pausa();
            }
        }

        this.jack.actualizaHit();

    }

    @Override
    public int onTouchEvent(MotionEvent event){
        //Recogemos la pulsacion
        int accion = event.getAction();

        switch (accion){
            case MotionEvent.ACTION_DOWN:

                if(!enPausa && !fin_juego){

                    aplicoFuerza = true;
                }

                Log.i("TOUCH","tocaste la pantalla");

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
                hw.continuar();
            }

            if(btnCasa.contains(x,y)){
                GameSV.puntuacion = 0;
                liberarRecursos();
                return 1;

            }
        }


        return this.numEscena;

    }

    /**
     * Funcion que crea un nuevo cohete y lo añade a la lista.
     *
     * @param c Canvas en donde se dibujará el cohete.
     */
    //Funcion para añadir un nuevo cohete y añadirlo a la lista
    private void AñadirCohete(Canvas c){
        if(!enPausa && !fin_juego){

            Cohete cohete = new Cohete(context,anchoPantalla,new Random().nextFloat()*(altoPantalla-cohete_escalado.getHeight()),anchoPantalla,altoPantalla);
            listaCohetes.add(cohete);
           /* Cohete cohete = new Cohete(context,anchoPantalla,300,anchoPantalla,altoPantalla);
            listaCohetes.add(cohete);*/
            cont_cohetes++;
            Log.i("cohete","añade cohete" + "\tCont: " + cont_cohetes + "\tIntervalo: " + t + "\tVelocidad: " + velocidad_cohete);

        }

    }


    /**
     * Función que es llamada por el timer y que a su vez llama a jack.dibujaAnimaciones() y dibuja la animacion de Jack.
     */
    public class ImagenesVarias extends java.util.TimerTask {

        /**
         * Actualiza a Jack, a los cohetes y cuando se acaba el juego, a la explosión.
         */
        @Override
        public void run(){

            if(!enPausa && !fin_juego){
                jack.dibujaAnimaciones();
            }

            for(Cohete cohete : listaCohetes){
                cohete.actualizaHit();
            }

            if(fin_juego){
                explosion.actualizaExplosion();
            }

            jack.actualizaHit();
        }
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

    private class ControlaGiro extends java.util.TimerTask {
        @Override
        public void run(){
            controlarGiro();
        }
    }

    private void controlarGiro() {
        if(!enPausa){
            this.paint_puntuacion.setColor(Color.RED);
            this.hw.continuar();
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

        //Pausa el giroscopio
        this.hw.pausa();
    }

    /**
     *  Función que llamaremos con el giroscopio para eliminar los cohetes.
     */
    public void eliminarCohetes(){
        GameSV.puntuacion += listaCohetes.size() * 5;
        this.listaCohetes.clear();
        this.paint_puntuacion.setColor(Color.WHITE);
        this.hw.pausa();
    }




}
