package Apuntes

fun main() {
    println("== Calculadora ==")

    // Pedimos primer numer
    println("Primer numero: ")
    val a = readLine()?.toDoubleOrNull() ?: return

    // pedimos la operac
    println("Operacion: +, - /, *")
    val operacion = readLine()?: return

    // Pedimos segundo numer
    println("Segundo numero: ")
    val b = readLine()?.toDoubleOrNull() ?: return

    // Calcular el Apuntes.resultado de la op

    val resultado = when (operacion) {
        "+" -> a + b
        "-" -> a - b
        "*" -> a * b
        "/" -> if(b != 0.0) a / b else { println("Error división por cero"); return }
        else -> { print("Operación desconocida"); return }
    }

    println("Resultado: $resultado")
}
