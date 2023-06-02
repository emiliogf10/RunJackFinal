package com.example.runjack.Limites;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.example.runjack.R;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

/**
 * Representa el techo de RunJack!
 *
 * @author Emilio
 * @version 1
 */
public class Techo {

    /**
     * Contexto de la aplicación.
     */
    Context context;

    /**
     * BodyDef del Suelo.
     */
    BodyDef bd;

    /**
     * Body del Suelo.
     */
    Body bSuelo;

    /**
     * Rectángulo del suelo.
     */
    public RectF hitbox;

    /**
     * Mundo Jbox2D.
     */
    World world;

    /**
     * Color del suelo.
     */
    Paint color;

    /**
     * Ancho y alto de pantalla.
     */
    float anchoP,altoP;

    /**
     * Crea un nuevo objeto Techo con los parámetros especificados.
     * @param context   Contexto de la aplicación.
     * @param world Mundo físico donde se crea el techo.
     * @param density   Densidad del techo.
     * @param friction  Fricción del techo.
     * @param anchoP    Ancho de la pantalla.
     * @param altoP Alto de la pantalla.
     */
    public Techo(Context context,World world, float density, float friction,float anchoP,float altoP){

        this.context = context;
        this.anchoP = anchoP;
        this.altoP = altoP;

        color=new Paint();
        color.setColor(context.getResources().getColor(R.color.color_suelo));

        this.hitbox = new RectF(0, 0, anchoP, altoP / 14);
        this.world=world;

        PolygonShape ps = new PolygonShape();
        ps.setAsBox(((hitbox.right - hitbox.left) / 2) / 10, ((hitbox.bottom - hitbox.top) / 2) / 10);

        FixtureDef fd = new FixtureDef();
        fd.shape = ps;
        fd.density = density;
        fd.friction = friction;

        this.bd = new BodyDef();

        bd.type = BodyType.STATIC;

        bSuelo = world.createBody(bd);
        bSuelo.createFixture(fd);
    }

    /**
     * Dibuja el objeto Techo en el Canvas especificado.
     *
     * @param c El objeto Canvas en el que se dibujará Suelo.
     */
    public void dibuja(Canvas c) {
        c.drawRect(hitbox, color);
    }

    /**
     *  Destruye el cuerpo del objeto Techo en el mundo físico.
     */
    public void destroy(){
        world.destroyBody(bSuelo);
    }

    /**
     *  Obtiene la posición del objeto Techo en el mundo físico.
     *
     * @return  La posición del objeto Techo como un objeto Vec2.
     */
    public Vec2 getPosicion() {
        return bSuelo.getPosition();
    }

    /**
     *  Obtiene el rectángulo de colisión (hitbox) del objeto Techo.
     *
     * @return  El rectángulo de colisión del objeto Techo.
     */
    public RectF getHitBox() {
        return hitbox;
    }
}
