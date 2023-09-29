import java.util.*

sealed class Item(val value: String) {
    class Number(value: String) : Item(value)
    class Symbol(value: String) : Item(value)

    companion object {
        fun create(value: String) = if (value.toDoubleOrNull() != null) Number(value) else Symbol(value)
    }
}

fun String.toInfixExpression(): String {
    val mappedExpression = split(' ').map(Item::create)
    val stack = Stack<String>()

    for (item in mappedExpression.reversed()) {
        when (item) {
            is Item.Number -> {
                stack.push(item.value)
            }

            is Item.Symbol -> {
                if (stack.size < 2) throw IllegalArgumentException("Incorrect input")
                val operand1 = stack.pop()
                val operand2 = stack.pop()
                val infixExpression = "($operand1 ${item.value} $operand2)"
                stack.push(infixExpression)
            }
        }
    }

    return stack.pop()
}

fun main() {
    val input = readlnOrNull() ?: return
    try {
        println(input.toInfixExpression())
    } catch (e: IllegalArgumentException) {
        println(e.message)
    }
}