package com.example.runjack;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;

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
    RectF hitbox;             // Rectangulo con el hitbox.
    World world;              // Mundo JBox2D

    Paint p;

    boolean activo=true;

    public Jack(World world,RectF hitbox,float density,float friction){
        //Definicion del cuerpo (Jack)
        this.hitbox = hitbox;
        this.world = world;

        p = new Paint();

        PolygonShape psJack = new PolygonShape();
        /*psJack.setAsBox(anchoPantalla / 2,altoPantalla / 2);*/

        FixtureDef fdJack = new FixtureDef();
        fdJack.shape = psJack;
        fdJack.density = density;
        fdJack.friction = friction;

        this.bdJack = new BodyDef();
        bdJack.position.set(hitbox.centerX() / 10, hitbox.centerY() / 10);
        bdJack.type = BodyType.DYNAMIC;

        bJack = world.createBody(bdJack);
        bJack.createFixture(fdJack);
    }

    public void dibuja(Canvas c) {
        c.drawRect(hitbox,p);
    }
    public void destroy(){
        world.destroyBody(bJack);
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
