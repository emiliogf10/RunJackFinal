package com.example.runjack;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase encargada de crear,actualizar y borrar la base de datos del juego.
 * Como es evidente, también tenemos una función que nos devuelve las 3 puntuaciones más altas.
 * Hereda de SQLiteOpenHelper.
 *
 * @author Emilio
 * @version 1
 */
public class BaseDeDatos extends SQLiteOpenHelper {

    /**
     * Contexto de la aplicación
     */
    Context context;

    /**
     * Nombre de la base de datos.
     */
    private static final String NOMBRE = "run_jack:db.db";

    /**
     * Nombre de la tabla.
     */

    public static final String TABLA = "puntos_mas_altos";

    /**
     * Version de la bd.
     */
    private static final int DB_VERSION = 1;

    /**
     * Crea un anueva instancia de BaseDeDatos.
     *
     * @param context   El contexto de la aplicación.
     */
    public BaseDeDatos(@Nullable Context context) {
        super(context, NOMBRE, null, DB_VERSION);
        this.context = context;
    }

    /**
     * Crea la base de datos si esta no existe.
     *
     * @param db El objeto SQLiteDatabase.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLA + "(" + "id INTEGER PRIMARY KEY AUTOINCREMENT," + "puntuacion INTEGER NOT NULL)");
    }

    /**
     * Elimina la tabla reciente y crea una nueva.
     *
     * @param db    Objeto SQLiteDatabase.
     * @param vieja Version antigua de la base de datos.
     * @param nueva Version reciente de la base de datos.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int vieja, int nueva) {
        db.execSQL("DROP TABLE " + TABLA);
        onCreate(db);
    }

    /**
     * Inserta una puntuacion dentro de la tabla de puntos_mas_altos.
     *
     * @param puntos    La puntuación que se va a insertar.
     */
    public void añadirPuntos(int puntos){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues valores = new ContentValues();

            valores.put("puntuacion",puntos);
            db.insert(TABLA,null,valores);
            db.close();

        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Devuelve las dos puntuaciones más altas.
     *
     * @return Una lista de las puntuaciones más altas.
     */
    public List<Integer> obtenerPuntos(){
        List<Integer> puntos_maximos = new ArrayList<>();

        try{
            SQLiteDatabase db = this.getWritableDatabase();
            String query = "SELECT DISTINCT puntuacion FROM " + TABLA + " ORDER BY puntuacion DESC LIMIT 3";
            Cursor cursor = db.rawQuery(query,null);

            while (cursor.moveToNext()){
                int punt = cursor.getInt(cursor.getColumnIndexOrThrow("puntuacion"));
                puntos_maximos.add(punt);
            }
            cursor.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        return puntos_maximos;
    }

    /**
     * Elimina todos los datos de la tabla puntos_mas_altos.
     */
    public void eliminarPuntos(){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLA,null,null);

        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}
