package com.example.runjack;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

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
    public Jack(Context context,World world,float density,float friction,float anchoP,float altoP){
        //Definicion del cuerpo (Jack)
        this.hitbox = new RectF(100,200,50,100);

        this.anchoP = anchoP;
        this.altoP = altoP;

        posicion = new PointF(anchoP / 2,altoP / 2);

        imagenJack = BitmapFactory.decodeResource(context.getResources(),R.drawable.jack1);

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
        actualizaHit();

    }

    public void dibuja(Canvas c) {



        c.drawBitmap(imagenJack, posicion.x,posicion.y, null);
        c.drawRect(this.hitbox,color);
        /*Log.i("JACK3","x:"+ x + " y:" + y);*/
        actualizaHit();
    }
    public void destroy(){
        world.destroyBody(bJack);
    }

    public void actualizaHit(){
        this.hitbox= new RectF(this.bdJack.position.x,this.bdJack.position.y,this.bdJack.position.x + this.imagenJack.getWidth(),this.bdJack.position.y + this.imagenJack.getHeight());

    }

    public void setPosition(float x, float y) {
        bJack.setTransform(new Vec2(x / div, y / div), bJack.getAngle());
        bJack.setActive(true);
        bJack.setAwake(true);
    }


    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
        bJack.setActive(activo);
    }

    public void setColor(int color) {
        this.p.setColor(color);
    }

    public Vec2 getPosicion() {
        return bJack.getPosition();
    }

    public float getX() {
        return bJack.getPosition().x * div;
    }

    public float getY() {
        return bJack.getPosition().y * div;
    }

    public float getXMundo() {
        return bJack.getPosition().x;
    }

    public float getYMundo() {
        return bJack.getPosition().y;
    }

    public float getAngle() {
        return bJack.getAngle();
    }

    public RectF getHitBox() {
        return hitbox;
    }



}
