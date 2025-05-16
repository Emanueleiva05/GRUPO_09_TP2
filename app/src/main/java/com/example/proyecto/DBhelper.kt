package com.example.proyecto;

import android.content.ContentValues
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.core.content.contentValuesOf

class DBHelper(context:Context):SQLiteOpenHelper(context, "ciudades.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase){
        db.execSQL("CREATE TABLE ciudad (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nombre TEXT, " +
                    "poblacion INTEGER, " +
                    "pais TEXT)")
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS ciudad")
        onCreate(db)
    }

    fun insertCiudad(nombre: String, poblacion: Int, pais: String){
        val db = writableDatabase
        val values = ContentValues()
        values.put("nombre",nombre)
        values.put("poblacion",poblacion)
        values.put("pais",pais)
        db.insert("ciudad",null,values)
        db.close()
    }

    fun modificarPoblacion(nombre: String, poblacion: Int){
        val db = writableDatabase
        val values = ContentValues().apply {
            put("poblacion", poblacion)
        }
        db.update(
            "ciudad", //Nombre de table
            values,   //Nuevos valores
            "nombre = ?",
            arrayOf(nombre)
        )
        db.close()
    }

    fun borrarCiudadesDeUnPais(pais: String){ //Cuando cambies esto por una id de pais en cidudad avisame y modifico esto
        val db = writableDatabase
        db.delete("ciudad","pais = ?",arrayOf(pais))  //El metodo delete esta modelado para que se pase un array por eso el arrayOf
        db.close()
    }

    fun obtenerCiudades(): List<String> {
        val ciudades = mutableListOf<String>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT nombre, pais, poblacion FROM ciudad", null)

        if (cursor.moveToFirst()) {
            do {
                val nombre = cursor.getString(0)
                val pais = cursor.getString(1)
                val poblacion = cursor.getInt(2)

                ciudades.add("$nombre, $pais - $poblacion habitantes")
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return ciudades
    }

    fun obtenerPaises(): List<String> {
        return emptyList()
    }
}
