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
    RectF hitbox,borde;             // Rectangulo con el hitbox.
    World world;              // Mundo JBox2D
    Paint p,color;

    Bitmap imagenJack;

    boolean activo=true;

    float anchoP,altoP;

    PointF posicion;

    Context context;

    Bitmap[] imagenesJack = new Bitmap[15];

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
     * @param world     El objeto World al que pertenece Jack.
     * @param density   La densidad del cuerpo de Jack.
     * @param friction  La fricción del cuerpo de Jack.
     * @param anchoP    El ancho del área de juego.
     * @param altoP     El alto del área de juego.
     */
    public Jack(Context context,World world,float density,float friction,float anchoP,float altoP){


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

        this.frame = 0;

        //Definicion del cuerpo (Jack)
        this.hitbox = new RectF(100,200,50,100);

        this.anchoP = anchoP;
        this.altoP = altoP;
        imagenJack = BitmapFactory.decodeResource(context.getResources(),R.drawable.jack1);

        posicion = new PointF(anchoP / 4 - imagenJack.getWidth(),altoP / 2 - imagenJack.getHeight());


        this.world = world;
        this.context = context;

        p = new Paint();
        color = new Paint(Color.RED);
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

        punto = bJack.getWorldPoint(bJack.getWorldCenter());



    }

    /**
     *Dibuja el objeto Jack en el canvas especificado.
     *
     * @param c El objeto Canvas en el que se dibujará Jack.
     */
    public void dibuja(Canvas c) {
        this.c = c;

        c.drawBitmap(imagenesJack[frame], posicion.x,posicion.y, null);
        c.drawRect(this.hitbox,color);
        actualizaHit();
    }

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
        this.hitbox= new RectF(this.posicion.x,this.posicion.y,this.posicion.x + this.imagenJack.getWidth(),this.posicion.y + this.imagenJack.getHeight());

    }

    /**
     * Establece la posición del objeto Jack en el mundo físico.
     *
     * @param x La coordenada x de la posición.
     * @param y La coordenada y de la posición.
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

    public float getX() {
        return bJack.getPosition().x * div;
    }

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

    public boolean collision(RectF hide_box){
        return this.hitbox.intersect(hide_box);
    }

    public void aplicarFuerza(float fuerzaX,float fuerzaY){
        this.fuerza = new Vec2(fuerzaX,fuerzaY);
        bJack.applyLinearImpulse(fuerza,punto);
    }

    public void aplicarFuerza(){
        if(this.fuerza != null){
            bJack.applyForce(fuerza,punto);
        }
    }



}
