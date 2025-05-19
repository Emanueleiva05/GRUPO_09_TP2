package com.example.proyecto;

import android.content.ContentValues
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.compose.animation.core.rememberTransition
import androidx.core.content.contentValuesOf

class DBHelper(context:Context):SQLiteOpenHelper(context, "ciudades.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase){
        db.execSQL("CREATE TABLE pais (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "nombre TEXT)")

        db.execSQL("CREATE TABLE ciudad (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nombre TEXT, " +
                    "poblacion INTEGER, " +
                    "pais_id INTEGER, " +
                    "FOREIGN KEY (pais_id) REFERENCES pais(id))")
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS ciudad")
        db.execSQL("DROP TABLE IF EXISTS pais")
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

//    Funcion para borrar todas las ciudades que coincidan con el nombre recibido por parametro
    fun borrarCiudadPorNombre(nombre: String): Boolean {
        val db = writableDatabase
        val filasAfectadas = db.delete("ciudad","nombre = ?", arrayOf(nombre))
        db.close()
        return filasAfectadas > 0
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

    fun insertPais (nombre: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("nombre", nombre)
        }
        val resultado = db.insert("pais", null, values)
        db.close()
        return resultado != -1L
    }

    data class CiudadDetalle(
        val nombre: String,
        val poblacion: Int,
        val paisNombre: String
    )

    fun obtenerCiudadPorNombre(nombre: String): CiudadDetalle? {
        val db = readableDatabase

        val cursor = db.rawQuery(
            "SELECT ciudad.nombre, ciudad.poblacion, pais.nombre" +
            "FROM ciudad " +
            "JOIN pais ON ciudad.pais_id = pais.id " +
            "WHERE ciudad.nombre = ?",
            arrayOf(nombre)
        )
        val ciudad = if (cursor.moveToFirst()) {
            val nombreCiudad = cursor.getString(0)
            val poblacion = cursor.getInt(1)
            val nombrePais = cursor.getString(2)

            CiudadDetalle(nombreCiudad, poblacion, nombrePais)
        } else {
            null
        }
        cursor.close()
        db.close()
        return ciudad
    }

    fun obtenerPaises(): List<String> {
        val paises = mutableListOf<String>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT nombre FROM pais", null)

        if (cursor.moveToFirst()) {
            do {
                val nombrePais = cursor.getString(0)
                paises.add(nombrePais)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return paises
    }
}
