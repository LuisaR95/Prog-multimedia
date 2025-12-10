package Apuntes

// Creación del objeto usuario
data class Usuario(val nombre: String, val correo: String, val edad: Int)

// Función que válida el Apuntes.nombre del usuario
fun validarNombre (nombre: String): Result<String> {
    return if (nombre.length <= 3) {
        Result.failure(Exception("El Apuntes.nombre debe tener al menos 3 caracteres"))
    } else {
        Result.success( nombre)

    }
}

// Función que valida el
fun validarEmail(email: String): Result<String> {
    return if (email.contains("@") && email.contains(".")) {
        Result.success(email)
    } else {
        Result.failure(Exception("El email no valido"))

    }
}

// Función que valida la Apuntes.edad
fun validarEdad(edad: Int): Result<Int> {
    return if (edad in 18..120) {
        Result.success(edad)
    } else {
        Result.failure(Exception("La Apuntes.edad de de estar entre 18 y 120"))

    }
}

// Función Apuntes.Entrega2.main - ejecucucion del codigo
fun main(){
    // Peticion al usuario para que se registre
    // Su Apuntes.nombre
    print("Nombre: ")
    val nombre = readLine() ?: " "

    // Su email
    print("Email: ")
    val Email = readLine() ?: " "

    // Su Apuntes.edad
    print("Edad: ")
    val edad = readLine()?.toIntOrNull() ?: -1

    val resultadoNombre = validarNombre(nombre)
    val resultadoEmail = validarEmail(Email)
    val resultadoEdad = validarEdad(edad)

    // Devoucion del Apuntes.resultado y errores
    when {
        resultadoNombre.isFailure -> println("Error en Apuntes.nombre: ${resultadoNombre.exceptionOrNull()?.message}")
        resultadoEmail.isFailure -> println("Error en email: ${resultadoEmail.exceptionOrNull()?.message}")
        resultadoEdad.isFailure -> println("Error en Apuntes.edad: ${resultadoEdad.exceptionOrNull()?.message}")
        else -> {
            // Inicializamos el objeto usuraio tras verificar que son correctos los campos
            val usuario = Usuario(nombre, Email, edad)
            println("Apuntes.Usuario creado: $usuario")
        }
    }
}