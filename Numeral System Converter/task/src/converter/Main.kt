package converter

import kotlin.math.pow

fun main() {
    val sourceRadixString = readLine()
    if (sourceRadixString.isNullOrEmpty() || !sourceRadixString.all { it.isDigit() }) {
        println("error")
        return
    }
    val sourceRadix = sourceRadixString.toInt()
    if (sourceRadix !in 1..36) {
        println("error")
        return
    }
    val number = readLine()
    if (number.isNullOrEmpty() || !number.all { it.isLetterOrDigit() || it == '.' }) {
        println("error")
        return
    }
    val targetRadixString = readLine()
    if (targetRadixString.isNullOrEmpty() || !targetRadixString.all { it.isDigit() }) {
        println("error")
        return
    }
    val targetRadix = targetRadixString.toInt()
    if (targetRadix !in 1..36) {
        println("error")
        return
    }
    
    val decimal = sourceToDecimal(sourceRadix, number)
    
    val result = decimalToResult(targetRadix, decimal, number)
    
    println(result)
}

fun decimalToResult(targetRadix: Int, decimal: Double, number: String): String {
    return if (targetRadix == 1) {
        var result = ""
        for (i in 1..decimal.toInt()) result += "1"
        result
    } else {
        val integerPart = decimal.toInt()
        var result = integerPart.toString(targetRadix)
        val fractionalPart = decimal - integerPart
        if (number.contains(".")) {
            result += "."
            var remainder = fractionalPart
            for (i in 1..5) {
                remainder *= targetRadix
                val remainderInteger = remainder.toInt()
                result += remainderInteger.toString(targetRadix)
                remainder -= remainderInteger
            }
        }
        result
    }
}

fun sourceToDecimal(sourceRadix: Int, number: String): Double {
    return when (sourceRadix) {
        10 -> number.toDouble()
        1 -> number.length.toDouble()
        else -> {
            val parts = number.split(".")
            var result = parts[0].toInt(sourceRadix).toDouble()
            if (parts.size == 2) {
                val fractional = parts[1]
                for (i in fractional.indices) {
                    result += fractional[i].toString().toInt(sourceRadix).toDouble() / sourceRadix.toDouble().pow(i + 1)
                }
            }
            result
        }
    }
}
