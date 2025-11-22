package com.example.prueba_retrofit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "departamentos.db";
    // Incrementamos la versión para borrar la tabla antigua y empezar de cero
    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_NAME = "departamentos";
    private static final String COL_ID = "departmentId";
    private static final String COL_NAME = "displayName";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY, " +
                COL_NAME + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Al subir de versión, eliminamos la tabla anterior (limpiando los datos viejos)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // Y creamos una nueva tabla vacía
        onCreate(db);
    }

    // Método para insertar o actualizar un departamento editado
    public void insertOrUpdate(Departamento departamento) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_ID, departamento.getDepartmentId());
        values.put(COL_NAME, departamento.getDisplayName());
        
        // Si existe lo actualiza, si no existe lo inserta
        db.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    // Obtener todos los departamentos (solo los que hemos editado/guardado)
    public List<Departamento> getAll() {
        List<Departamento> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME));
                list.add(new Departamento(id, name));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    // Obtener un departamento por ID
    public Departamento getDepartmentById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, COL_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
        
        Departamento d = null;
        if (cursor != null && cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME));
            d = new Departamento(id, name);
            cursor.close();
        }
        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return d;
    }
}