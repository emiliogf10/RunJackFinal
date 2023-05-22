package com.example.runjack;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import org.jbox2d.collision.shapes.PolygonShape;
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

    Context context;

    public Suelo(Context context,World world, RectF hitbox, float density, float friction, float restitution){
        this.context = context;

        this.hitbox = hitbox;
        this.world=world;

        PolygonShape ps = new PolygonShape();
        ps.setAsBox(((hitbox.right - hitbox.left) / 2) / 10, ((hitbox.bottom - hitbox.top) / 2) / 10);

        FixtureDef fd = new FixtureDef();
        fd.shape = ps;
        fd.density = density;
        fd.friction = friction;
        fd.restitution = restitution;

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



}
