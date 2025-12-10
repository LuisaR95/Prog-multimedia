import kotlin.math.abs

// ----------------------
// Definición de Clases
// ----------------------
data class Temperatura(val valor: Double, val escala: String)

sealed class TemperaturaException(message: String) : Exception(message) {
    class TemperaturaImposibleException(valor: Double, escala: String) :
        TemperaturaException("Error: $valor $escala es físicamente imposible. La temperatura más baja posible es el cero absoluto.")

    class EntradaInvalidaException(entrada: String) :
        TemperaturaException("Error: '$entrada' no es un número válido.")
}

// ----------------------
// Constantes
// ----------------------
const val CERO_ABSOLUTO_CELSIUS = -273.15
const val CERO_ABSOLUTO_KELVIN = 0.0

// ----------------------
// Funciones de conversión
// ----------------------
fun celsiusAFahrenheit(celsius: Double) = celsius * 9.0 / 5.0 + 32.0
fun kelvinACelsius(k: Double) = k - 273.15
fun fahrenheitACelsius(f: Double) = (f - 32) * 5.0 / 9.0

// ----------------------
// Validación
// ----------------------
fun validarTemperatura(celsius: Double): Result<Double> =
    if (celsius < CERO_ABSOLUTO_CELSIUS)
        Result.failure(TemperaturaException.TemperaturaImposibleException(celsius, "°C"))
    else
        Result.success(celsius)

// ----------------------
// Función convertir()
// ----------------------
fun convertir(valor: String, opcion: Int): Result<Temperatura> {
    val temp = valor.toDoubleOrNull()
        ?: return Result.failure(TemperaturaException.EntradaInvalidaException(valor))

    return when (opcion) {
        1 -> { // C -> F
            validarTemperatura(temp)
                .map { celsiusAFahrenheit(it) }
                .map { Temperatura(it, "°F") }
        }

        2 -> { // K -> C
            if (temp < CERO_ABSOLUTO_KELVIN)
                return Result.failure(TemperaturaException.TemperaturaImposibleException(temp, "K"))

            Result.success(Temperatura(kelvinACelsius(temp), "°C"))
        }

        3 -> { // F -> C
            val c = fahrenheitACelsius(temp)
            validarTemperatura(c)
                .map { Temperatura(it, "°C") }
        }

        else -> Result.failure(Exception("Opción no válida"))
    }
}

// ----------------------
// Auxiliares
// ----------------------
fun Double.format(d: Int) = String.format("%.${d}f", this)

fun manejarResultadoPrueba(r: Result<Temperatura>) {
    if (r.isSuccess) {
        val t = r.getOrThrow()
        println("PRUEBA ÉXITO: ${t.valor.format(2)} ${t.escala}")
    } else {
        println("PRUEBA FALLO: ${r.exceptionOrNull()?.message}")
    }
}

// ----------------------
// Menú
// ----------------------
fun menuInteractivo() {
    val historial = mutableListOf<String>()
    var seguir = true

    while (seguir) {
        println("\n=== Conversor de Temperaturas ===")
        println("1. Celsius → Fahrenheit")
        println("2. Kelvin → Celsius")
        println("3. Fahrenheit → Celsius")
        println("4. Ver historial")
        println("0. Salir")
        print("Opción: ")

        val opcion = readLine()?.toIntOrNull()

        when (opcion) {
            1, 2, 3 -> {
                print("Introduce la temperatura: ")
                val entrada = readLine() ?: ""
                val res = convertir(entrada, opcion)

                if (res.isSuccess) {
                    val t = res.getOrThrow()
                    val msg = "OK: $entrada → ${t.valor.format(2)} ${t.escala}"
                    println(msg)
                    historial.add(msg)
                } else {
                    val msg = res.exceptionOrNull()?.message ?: "Error"
                    println(msg)
                    historial.add("FALLO: $msg")
                }
            }

            4 -> {
                println("\n=== Historial ===")
                if (historial.isEmpty()) println("Vacío.")
                else historial.forEachIndexed { i, s -> println("${i + 1}. $s") }
            }

            0 -> {
                println("Adiós!")
                seguir = false
            }

            else -> println("Opción inválida.")
        }
    }
}

// ----------------------
// FUNCIÓN PRINCIPAL REAL
// ----------------------
fun main() {
    println("--- Pruebas Iniciales ---")
    manejarResultadoPrueba(convertir("-273.16", 1))
    manejarResultadoPrueba(convertir("-273.15", 1))
    manejarResultadoPrueba(convertir("-1", 2))

    menuInteractivo()
}
