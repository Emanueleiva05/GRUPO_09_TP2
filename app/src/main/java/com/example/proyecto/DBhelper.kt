package com.example.proyecto;

import android.content.ContentValues
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.compose.animation.core.rememberTransition
import androidx.core.content.contentValuesOf

class DBHelper(context:Context):SQLiteOpenHelper(context, "ciudades.db", null, 2) {
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

    fun insertCiudad(nombre: String, poblacion: Int, paisId: Int) {
        val db = writableDatabase
        val values = ContentValues()
        values.put("nombre", nombre)
        values.put("poblacion", poblacion)
        values.put("pais_id", paisId)
        db.insert("ciudad", null, values)
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

    fun borrarCiudadesDeUnPais(nombrePais: String): Boolean {
        val db = writableDatabase
        var totalFilasBorradas = 0
        val cursor = db.rawQuery("SELECT id FROM pais WHERE nombre = ?", arrayOf(nombrePais))

        if (cursor.moveToFirst()) {
            do {
                val paisId = cursor.getInt(0)
                val filasBorradas = db.delete("ciudad", "pais_id = ?", arrayOf(paisId.toString()))
                totalFilasBorradas += filasBorradas
            } while (cursor.moveToNext())
        } else {
            cursor.close()
            // No se encontró ningún país con ese nombre
            return false
        }
        cursor.close()
        // No cerramos db aquí para evitar errores de concurrencia
        return totalFilasBorradas > 0
    }


    //    Funcion para borrar todas las ciudades que coincidan con el nombre recibido por parametro
    fun borrarCiudadPorNombre(nombre: String): Boolean {
        val db = writableDatabase
        val filasAfectadas = db.delete("ciudad","nombre = ?", arrayOf(nombre))
        db.close()
        return filasAfectadas > 0
    }

    data class CiudadDetalle(
        val nombre: String,
        val poblacion: Int,
        val paisNombre: String
    )

    fun obtenerCiudades(): List<CiudadDetalle> {
        val ciudades = mutableListOf<CiudadDetalle>()
        val db = readableDatabase

        val cursor = db.rawQuery(
            "SELECT ciudad.nombre, ciudad.poblacion, pais.nombre " +
                    "FROM ciudad JOIN pais ON ciudad.pais_id = pais.id",
            null
        )

        if (cursor.moveToFirst()) {
            do {
                val nombre = cursor.getString(0)
                val poblacion = cursor.getInt(1)
                val paisNombre = cursor.getString(2)

                ciudades.add(CiudadDetalle(nombre, poblacion, paisNombre))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return ciudades
    }

    fun obtenerIdPaisPorNombre(nombrePais: String): Int? {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT id FROM pais WHERE nombre = ?", arrayOf(nombrePais))
        val id = if (cursor.moveToFirst()) cursor.getInt(0) else null
        cursor.close()
        db.close()
        return id
    }

    fun existePais(nombrePais: String): Boolean {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT 1 FROM pais WHERE nombre = ? LIMIT 1", arrayOf(nombrePais))
        val existe = cursor.moveToFirst()
        cursor.close()
        // No cerramos db aquí para evitar problemas si se usa varias veces en cascada
        return existe
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
