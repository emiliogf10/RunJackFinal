package com.example.runjack;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import java.util.Timer;

public class Jack {

    Integer div = 10;
    BodyDef bdJack;          // Definición del cuerpo
    Body bJack;                // Cuerpo del objeto a dibujar
    RectF hitbox;             // Rectangulo con el hitbox.
    World world;              // Mundo JBox2D
    Paint p,color;

    //Bitmap imagenJack;

    boolean activo=true;

    float anchoP,altoP;

    PointF posicion;

    Context context;

    Bitmap[] imagenesJack = new Bitmap[27];

    public int frame;

    Canvas c;

    //Fuerza de impulso que se le aplicará a Jack
    Vec2 fuerza;

    //Punto donde se aplica la fuerza(centro de Jack)
    Vec2 punto;

    /**
     *Crea un nuevo objeto Jack con los parámetros especificados.
     *
     * @param context   El contexto de la aplicación.
     * @param world El objeto World al que pertenece Jack.
     * @param density   La densidad del cuerpo de Jack.
     * @param friction  La fricción del cuerpo de Jack.
     * @param anchoP    El ancho del área de juego.
     * @param altoP El alto del área de juego.
     * @param x Posicion X de Jack.
     * @param y Posicion Y de Jack.
     */
    public Jack(Context context,World world,float density,float friction,float anchoP,float altoP,float x,float y){


        imagenesJack[0] = BitmapFactory.decodeResource(context.getResources(),R.drawable.jack1);
        imagenesJack[1] = BitmapFactory.decodeResource(context.getResources(),R.drawable.jack2);
        imagenesJack[2] = BitmapFactory.decodeResource(context.getResources(),R.drawable.jack3);
        imagenesJack[3] = BitmapFactory.decodeResource(context.getResources(),R.drawable.jack4);
        imagenesJack[4] = BitmapFactory.decodeResource(context.getResources(),R.drawable.jack5);
        imagenesJack[5] = BitmapFactory.decodeResource(context.getResources(),R.drawable.jack6);
        imagenesJack[6] = BitmapFactory.decodeResource(context.getResources(),R.drawable.jack7);
        imagenesJack[7] = BitmapFactory.decodeResource(context.getResources(),R.drawable.jack8);
        imagenesJack[8] = BitmapFactory.decodeResource(context.getResources(),R.drawable.jack9);
        imagenesJack[9] = BitmapFactory.decodeResource(context.getResources(),R.drawable.jack10);
        imagenesJack[10] = BitmapFactory.decodeResource(context.getResources(),R.drawable.jack11);
        imagenesJack[11] = BitmapFactory.decodeResource(context.getResources(),R.drawable.jack12);
        imagenesJack[12] = BitmapFactory.decodeResource(context.getResources(),R.drawable.jack13);
        imagenesJack[13] = BitmapFactory.decodeResource(context.getResources(),R.drawable.jack14);
        imagenesJack[14] = BitmapFactory.decodeResource(context.getResources(),R.drawable.jack15);
        imagenesJack[15] = BitmapFactory.decodeResource(context.getResources(),R.drawable.jack16);
        imagenesJack[16] = BitmapFactory.decodeResource(context.getResources(),R.drawable.jack17);
        imagenesJack[17] = BitmapFactory.decodeResource(context.getResources(),R.drawable.jack18);
        imagenesJack[18] = BitmapFactory.decodeResource(context.getResources(),R.drawable.jack19);
        imagenesJack[19] = BitmapFactory.decodeResource(context.getResources(),R.drawable.jack20);
        imagenesJack[20] = BitmapFactory.decodeResource(context.getResources(),R.drawable.jack21);
        imagenesJack[21] = BitmapFactory.decodeResource(context.getResources(),R.drawable.jack22);
        imagenesJack[22] = BitmapFactory.decodeResource(context.getResources(),R.drawable.jack23);
        imagenesJack[23] = BitmapFactory.decodeResource(context.getResources(),R.drawable.jack24);
        imagenesJack[24] = BitmapFactory.decodeResource(context.getResources(),R.drawable.jack25);
        imagenesJack[25] = BitmapFactory.decodeResource(context.getResources(),R.drawable.jack26);
        imagenesJack[26] = BitmapFactory.decodeResource(context.getResources(),R.drawable.jack27);

       for(int i = 0;i < imagenesJack.length;i++){
           imagenesJack[i] = Bitmap.createScaledBitmap(imagenesJack[i],300,300,false);
        }

        this.frame = 0;

        this.anchoP = anchoP;
        this.altoP = altoP;

        this.posicion = new PointF(x,y);

        this.world = world;
        this.context = context;

        p = new Paint();
        color = new Paint(Color.RED);
        color.setColor(Color.RED);
        this.color.setStyle(Paint.Style.STROKE);
        this.color.setStrokeWidth(5);

        PolygonShape psJack = new PolygonShape();
        psJack.setAsBox(6,5);

        FixtureDef fdJack = new FixtureDef();
        fdJack.shape = psJack;
        fdJack.density = density;
        fdJack.friction = friction;

        this.bdJack = new BodyDef();
        bdJack.position.set(posicion.x,posicion.y);
        bdJack.type = BodyType.DYNAMIC;

        bJack = world.createBody(bdJack);
        bJack.createFixture(fdJack);

        //AÑADIRLE UN VALOR A FUERZA.
        punto = bJack.getWorldPoint(bJack.getWorldCenter());

        this.hitbox= new RectF(this.posicion.x,this.posicion.y,this.posicion.x + this.imagenesJack[0].getWidth(),this.posicion.y + this.imagenesJack[0].getHeight());

        actualizaHit();



    }

    /**
     *Dibuja el objeto Jack en el Canvas especificado.
     *
     * @param c El objeto Canvas en el que se dibujará Jack.
     */
    public void dibuja(Canvas c) {
        this.c = c;

        c.drawBitmap(imagenesJack[frame], posicion.x,posicion.y, null);
        c.drawRect(this.hitbox,color);
        actualizaHit();
    }

    /**
     * Método que dibuja las animaciones en un ciclo continuo.
     */
    public void dibujaAnimaciones(){
        if(this.frame == this.imagenesJack.length - 1){
            this.frame = 0;
        }
        this.frame++;
    }

    /**
     * Destruye el cuerpo del objeto Jack en el mundo físico.
     */
    public void destroy(){
        world.destroyBody(bJack);
    }

    /**
     * Actualiza el rectángulo de colisión (hitbox) del objeto Jack.
     */
    public void actualizaHit(){



        this.hitbox= new RectF(this.posicion.x,this.posicion.y,this.posicion.x + this.imagenesJack[0].getWidth(),this.posicion.y + this.imagenesJack[0].getHeight());
        RectF jack = this.hitbox;
        Log.i("POS","JACKK2 -->Iz:" + this.hitbox.left+ " \tArriba: " + jack.top + " \tDerecha: " + jack.right + " Abajo: " + jack.bottom );

    }

    /**
     * Establece la posición del objeto Jack en el mundo físico.
     *
     * @param x La coordenada X de la posición.
     * @param y La coordenada Y de la posición.
     */
    public void setPosition(float x, float y) {
        bJack.setTransform(new Vec2(x / div, y / div), bJack.getAngle());
        bJack.setActive(true);
        bJack.setAwake(true);

    }

    /**
     * Establece el color de pintura del objeto Jack.
     *
     * @param color El color a establecer.
     */
    public void setColor(int color) {
        this.p.setColor(color);
    }

    /**
     * Obtiene la posición del objeto Jack en el mundo físico.
     *
     * @return  La posición del objeto Jack como un objeto Vec2.
     */
    public Vec2 getPosicion() {
        return bJack.getPosition();
    }

    /**
     * Devuelve la posición X del objeto bJack multiplicada por el factor de escala div.
     *
     * @return  La posición X del objeto bJack multiplicado por div.
     */
    public float getX() {
        return bJack.getPosition().x * div;
    }

    /**
     * Devuelve la posición Y del objeto bJack multiplicada por el factor de escala div.
     *
     * @return  La posición Y del objeto bJack multiplicado por div.
     */
    public float getY() {
        return bJack.getPosition().y * div;
    }

    /**
     * Obtiene el rectángulo de colisión (hitbox) del objeto Jack.
     *
     * @return  El rectángulo de colisión del objeto Jack.
     */
    public RectF getHitBox() {
        return hitbox;
    }

    /**
     * Comprueba si hay colisión entre el objeto actual y el rectángulo especificado.
     *
     * @param hide_box  Rectángulo con el cual se va a comprobar la colisión.
     * @return  True si hay colisión, false si no.
     */
    public boolean collision(RectF hide_box){
        return this.hitbox.intersect(hide_box);
    }

    /**
     * Aplica una fuerza especificada en los ejes X e Y al objeto Jack.
     *
     * @param fuerzaX   Componente de fuerza en el eje X.
     * @param fuerzaY   Componente de fuerza en el eje Y.
     */
    public void aplicarFuerza(float fuerzaX,float fuerzaY){
        this.fuerza = new Vec2(fuerzaX,fuerzaY);
        bJack.applyLinearImpulse(fuerza,punto);
    }

    /**
     * Aplica la fuerza almacenada en el objeto Jack.
     * Si no se ha establecido una fuerza previamente, no se aplicará nunguna fuerza.
     */
    public void aplicarFuerza(){
        if(this.fuerza != null){
            bJack.applyLinearImpulse(fuerza,punto);
            Log.i("TOUCH","Hay fuerza");
        }else{
            Log.i("TOUCH","No hay fuerza");
        }
    }

    public static float getFuerza(float limiteInferior, float limiteSuperior){
        double fuerza=(Math.random() * (limiteSuperior - limiteInferior)) + limiteInferior;
        int limite = (int) Math.random() * (3 - 1) + 1;
        return (float) (limite==1?fuerza:fuerza*-1);
    }




}
