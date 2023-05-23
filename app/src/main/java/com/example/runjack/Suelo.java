package com.example.runjack;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

public class Suelo {

    BodyDef bd;
    Body bSuelo;
    RectF hitbox;
    World world;
    Paint color;

    float anchoP,altoP;

    Context context;

    public Suelo(Context context,World world, float density, float friction,float anchoP,float altoP){
        this.context = context;
        this.anchoP = anchoP;
        this.altoP = altoP;

        this.hitbox = new RectF(anchoP / 10 * 2, altoP / 10 * 5, anchoP / 10 * 2.5f, altoP / 10 * 6.5f);
        this.world=world;

        PolygonShape ps = new PolygonShape();
        ps.setAsBox(((hitbox.right - hitbox.left) / 2) / 10, ((hitbox.bottom - hitbox.top) / 2) / 10);

        FixtureDef fd = new FixtureDef();
        fd.shape = ps;
        fd.density = density;
        fd.friction = friction;

        this.bd = new BodyDef();

        bd.position.set(hitbox.centerX() / 10, hitbox.centerY() / 10);
        bd.type = BodyType.STATIC;

        bSuelo = world.createBody(bd);
        bSuelo.createFixture(fd);
    }

    public void dibuja(Canvas c) {
        c.drawRect(hitbox, color);
    }

    public void destroy(){
        world.destroyBody(bSuelo);
    }

    public Vec2 getPosicion() {
        return bSuelo.getPosition();
    }

    public float getX() {
        return bSuelo.getPosition().x * 10;
    }

    public float getY() {
        return bSuelo.getPosition().y * 10;
    }

    public RectF getHitBox() {
        return hitbox;
    }



}
