package com.example.runjack.Escenas;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.view.MotionEvent;

import androidx.core.content.ContextCompat;

import com.example.runjack.GameSV;
import com.example.runjack.R;

/**
 * Clase padre para todas las subclases que crearemos; Créditos,Informacion,Records,Ajustes y Menu.
 *
 * @author Emilio
 * @version 1
 */
public class Escena {
    /**
     * Contexto de la aplicación
     */
    public Context context;

    /**
     * Número identificativo de la escena.
     */
    public int numEscena = -1;

    /**
     * Alto de la pantalla.
     */
    public int altoPantalla;

    /**
     * Ancho de la pantalla.
     */
    public int anchoPantalla;

    /**
     *  Representa la pintura en las ventanas de gameOver y pausa.
     */
    public Paint p,ventana,titulo;

    /**
     * Rectángulo que representa el botón de la casita.
     */
    public Rect btnMenu;

    /**
     *  Rectángulos que representan a los botones de las ventanas de pausa y gameOver.
     */
    public RectF base,btnCasa,btnResume;

    /**
     * Bitmaps de la imagen del menú.
     */
    public Bitmap menu,menuEscalado;

    /**
     * Objeto typeface que representa el tipo de letra del juego.
     */
    public Typeface tf;

    /**
     * Objeto configuration para controlar los ajustes del juego.
     */

    public Configuration config;

    /**
     * Idioma del juego.
     */

    public String idioma;

    /**
     * Color de fondo que le daremos a las escenas.
     */
    public int color_fondo;


    /**
     * Crea un nuevo objeto Escena con los parámetros especificados.
     *
     * @param context Contexto de la aplicacion.
     * @param altoPantalla Alto de la pantalla.
     * @param anchoPantalla Alto de la pantalla.
     * @param numEscena Numero de identificación de la escena.
     */

    public Escena( Context context, int anchoPantalla, int altoPantalla, int numEscena) {
        this.altoPantalla = altoPantalla;
        this.anchoPantalla = anchoPantalla;
        this.context = context;
        p=new Paint();
        p.setTextSize(altoPantalla/10);
        p.setColor(Color.WHITE);
        p.setTextAlign(Paint.Align.CENTER);

        tf = Typeface.createFromAsset(context.getAssets(), "SUPERBOLT.ttf");
        p.setTypeface(tf);

        this.titulo = new Paint();
        this.titulo.setAlpha(200);
        this.titulo.setTypeface(tf);
        this.titulo.setAntiAlias(true);
        this.titulo.setColor(Color.WHITE);
        this.titulo.setTextSize((float) altoPantalla / 10);
        this.titulo.setTextAlign(Paint.Align.CENTER);

        this.ventana = new Paint();
        base = new RectF((float) anchoPantalla / 5,(float) altoPantalla / 5,(float) anchoPantalla / 5 * 4,(float) altoPantalla / 5 * 4);


        menu = BitmapFactory.decodeResource(context.getResources(), R.drawable.casa);
        menuEscalado = Bitmap.createScaledBitmap(menu,menu.getWidth()/4,menu.getHeight()/4,true);



        this.numEscena=numEscena;
        this.btnMenu = new Rect(anchoPantalla/14, altoPantalla/14, anchoPantalla/12*2, altoPantalla/12*2);

        config = context.getResources().getConfiguration();
        idioma = config.locale.getLanguage();

        color_fondo = context.getResources().getColor(R.color.color_fondo);
    }

    /**
     * Dibuja la escena en el Canvas especificado.
     *
     * @param c El objeto Canvas en el que se dibujará la escena de créditos.
     */
    public void dibuja(Canvas c){
        c.drawColor(color_fondo);
        //Se dibuja el icono de la casa en todas las escenas menos en el menú y en el juego.
        if (numEscena != 2 && numEscena != 1){
            c.drawBitmap(menuEscalado,null,btnMenu,null);
        }
    }

    /**
     * Actualiza la fisica de cada escena.
     *
     */
    public void actualizaFisica(){

    }

    /**
     * Maneja los eventos táctiles en la pantalla.
     *
     * @param event Representa el evento.
     * @return Devuelve el resultado del evento click.
     * Devuelve 1 si el botón se tocó en escenas diferentes de 1 y 3, si no, devuelve -1.
     *
     */
    public int onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        if (numEscena != 1 && numEscena != 2) if (btnMenu.contains(x, y)) return 1;

        return -1;
    }


    /**
     * Dibuja un menú de pausa en el Canvas.
     *
     * @param c Canvas en donde se va a dibujar el menú de pausa.
     */
    public void pantallaPausa(Canvas c){

        this.ventana.setStyle(Paint.Style.FILL);
        this.titulo.setTextAlign(Paint.Align.CENTER);
        this.ventana.setColor(ContextCompat.getColor(context,R.color.gris));

        c.drawRoundRect(base, 15f, 15f, ventana);

        this.ventana.setColor(ContextCompat.getColor(context, R.color.color_letra));
        this.ventana.setTypeface(tf);
        this.ventana.setTextSize((float) altoPantalla / 8);

        c.drawText((String) context.getText(R.string.pausa), (float) anchoPantalla / 5 * 2,
                    (float) altoPantalla / 7 * 3, this.ventana);


        ventana.setColor(ContextCompat.getColor(context, R.color.black));
        c.drawRoundRect(this.btnCasa, 20f, 20f, ventana);
        c.drawRoundRect(this.btnResume, 20f, 20f, ventana);


        ventana.setColor(ContextCompat.getColor(context, R.color.color_letra));
        ventana.setTextSize((float) altoPantalla / 15);


        c.drawText((String) context.getString(R.string.menu), (float) anchoPantalla / 7 * 2, (float) altoPantalla / 12 * 8, ventana);
        c.drawText((String) context.getString(R.string.reanudar), (float) anchoPantalla / 7 * 4, (float) altoPantalla / 12 * 8, ventana);


    }

    /**
     * Dibuja un menú de Game Over en el Canvas.
     *
     * @param c Canvas en donde se va a dibujar el menú de Game Over.
     */
    public void pantallaGameOver(Canvas c){

        this.ventana.setStyle(Paint.Style.FILL);
        this.titulo.setTextAlign(Paint.Align.CENTER);
        this.ventana.setColor(ContextCompat.getColor(context,R.color.gris));

        c.drawRoundRect(base, 25f, 25f, ventana);

        this.ventana.setColor(ContextCompat.getColor(context, R.color.color_letra));
        this.ventana.setTypeface(tf);
        this.ventana.setTextSize((float) altoPantalla / 8);

        c.drawText((String) context.getText(R.string.titulo_fin_juego),
                (float) anchoPantalla / 5 * 1.5f,
                (float) altoPantalla / 7 * 2.5f, this.ventana);

        this.ventana.setColor(ContextCompat.getColor(context, R.color.color_letra));
        this.ventana.setTypeface(tf);
        this.ventana.setTextSize((float) altoPantalla / 15);

        c.drawText((String) context.getText(R.string.puntos) + ": " + String.valueOf(GameSV.puntuacion),
                (float) anchoPantalla / 7 * 3.5f,
                (float) altoPantalla /12 * 8, this.ventana);

        ventana.setColor(ContextCompat.getColor(context, R.color.black));
        c.drawRoundRect(this.btnCasa, 20f, 20f, ventana);




        ventana.setColor(ContextCompat.getColor(context, R.color.color_letra));
        ventana.setTextSize((float) altoPantalla / 15);

        c.drawText((String) context.getString(R.string.menu),
                (float) anchoPantalla / 7 * 2, (float) altoPantalla / 12 * 8, ventana);
        
    }

}
