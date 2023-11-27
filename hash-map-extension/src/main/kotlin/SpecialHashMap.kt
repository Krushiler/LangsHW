import exception.BadTokenException
import getter.mapGetter
import token.Token

class SpecialHashMap<T : Comparable<T>> : HashMap<String, T>() {
    private val lexer = Lexer()

    override fun put(key: String, value: T): T? {
        return super.put(key.formattedKey, value)
    }

    val iloc = mapGetter<Int, T?> { index ->
        this@SpecialHashMap[keys.sorted()[index]]
    }

    val ploc = mapGetter<String, SpecialHashMap<T>> { conditions ->
        val conditionList = conditions.replace(" ", "").split(",")

        val result = SpecialHashMap<T>()

        val selectedKeys = keys.filter { key ->
            val formattedKey = key.keyValuesList
            if (formattedKey.size != conditionList.size) return@filter false
            formattedKey.zip(conditionList).all { (part, condition) ->
                parseCondition(part, condition)
            }
        }

        for (key in selectedKeys) {
            result[key] = this@SpecialHashMap[key] ?: continue
        }

        result
    }

    private fun parseCondition(keyPart: String, condition: String): Boolean {
        lexer.start(condition)

        val leftValue = keyPart.toIntOrNull() ?: return false

        val expression = lexer.nextToken() as? Token.Operator ?: throw BadTokenException("Incorrect condition")

        val numberToken = lexer.nextToken()

        if (numberToken !is Token.Number) {
            throw BadTokenException("Incorrect condition")
        }

        val rightValue = numberToken.value

        return when (expression) {
            Token.Operator.Equals -> leftValue == rightValue
            Token.Operator.NotEquals -> leftValue != rightValue
            Token.Operator.LessThan -> leftValue < rightValue
            Token.Operator.GreaterThan -> leftValue > rightValue
            Token.Operator.LessThanOrEquals -> leftValue <= rightValue
            Token.Operator.GreaterThanOrEquals -> leftValue >= rightValue
        }
    }

    private val String.formattedKey
        get() = replace("(", "").replace(")", "").replace(" ", "")

    private val String.keyValuesList
        get() = formattedKey.split(",")
}

fun <T : Comparable<T>> specialHashMapOf(vararg pairs: Pair<String, T>): SpecialHashMap<T> {
    val map = SpecialHashMap<T>()
    for (pair in pairs) {
        map[pair.first] = pair.second
    }
    return map
}