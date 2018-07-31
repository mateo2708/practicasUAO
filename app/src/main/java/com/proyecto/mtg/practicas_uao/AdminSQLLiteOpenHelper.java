package com.proyecto.mtg.practicas_uao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mateo on 3/7/2018.
 */

public class AdminSQLLiteOpenHelper extends SQLiteOpenHelper {

    public AdminSQLLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE PRACTICAS(codigo VARCHAR(50) PRIMARY KEY, nom_empres VARCHAR(50), fecha_inicio VARCHAR(20), fecha_fin VARCHAR(20)," +
                "  programa VARCHAR(50), requisitos VARCHAR(50), fotografia VARCHAR(50), sede VARCHAR(50));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS PRACTICAS");
        db.execSQL("CREATE TABLE PRACTICAS(codigo VARCHAR(50) PRIMARY KEY, nom_empres VARCHAR(50), fecha_inicio VARCHAR(20), fecha_fin VARCHAR(20)," +
                " programa VARCHAR(50), requisitos VARCHAR(50), fotografia VARCHAR(50), sede VARCHAR(50));");    }
}
