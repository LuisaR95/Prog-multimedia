package Apuntes

import java.time.LocalDateTime

fun main(){
    println("¿Cual es tu Apuntes.nombre?")
    // Esperamos entrada por parte del usuario
    val nombre = readLine() ?: "Visitante"

    //Cogemos la libreria de java.time la hora Apuntes.a partir de la funcion
    //now del objeto LocalDateTime
    val horaActual = LocalDateTime.now().hour

    // Creación de una función anonima
    val saludo = when {
        horaActual < 18 -> "Buenas tardes"
        horaActual < 12 -> "Buenos días"
        else -> "Buenas noches"
       }
    // Devolver resultados al ususario
    println("$saludo, $nombre")



    }
