package com.example.runjack;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BaseDeDatos extends SQLiteOpenHelper {

    Context context;

    private static final String NOMBRE = "run_jack:db.db";

    public static final String TABLA = "puntos_mas_altos";

    private static final int DB_VERSION = 1;


    public BaseDeDatos(@Nullable Context context) {
        super(context, NOMBRE, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLA + "(" + "id INTEGER PRIMARY KEY AUTOINCREMENT," + "puntuacion INTEGER NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int vieja, int nueva) {
        db.execSQL("DROP TABLE " + TABLA);
        onCreate(db);
    }

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

    public List<Integer> obtenerPuntos(){
        List<Integer> puntos_maximos = new ArrayList<>();

        try{
            SQLiteDatabase db = this.getWritableDatabase();
            String query = "SELECT DISTINCT puntos FROM " + TABLA + " ORDER BY puntos DESC LIMIT 2";
            Cursor cursor = db.rawQuery(query,null);

            while (cursor.moveToNext()){
                int puntuacion = cursor.getInt(cursor.getColumnIndexOrThrow("puntuacion"));
                puntos_maximos.add(puntuacion);
            }
            cursor.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        return puntos_maximos;
    }

    public void eliminarPuntos(){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLA,null,null);

        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}
