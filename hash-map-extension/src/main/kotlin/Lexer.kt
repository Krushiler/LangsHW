import exception.BadTokenException
import token.Token

internal class Lexer {
    private var input = ""
    private var index = 0
    private val currentChar: Char? get() = input.getOrNull(index)

    private fun forward() {
        index++
    }

    private fun number(): Int {
        var result = 0
        var multiplier = 1
        while (currentChar?.isDigit() == true) {
            currentChar?.let { char ->
                result += char.digitToInt() * multiplier
                multiplier *= 10
                forward()
            }
        }
        return result
    }

    fun nextToken(): Token? {
        while (currentChar != null) {
            if (currentChar?.isDigit() == true) {
                return Token.Number(number())
            } else if (currentChar == '>') {
                forward()
                if (currentChar == '=') {
                    forward()
                    return Token.Operator.GreaterThanOrEquals
                }
                return Token.Operator.GreaterThan
            } else if (currentChar == '<') {
                forward()
                if (currentChar == '=') {
                    forward()
                    return Token.Operator.LessThanOrEquals
                }
                return Token.Operator.LessThan
            } else if (currentChar == '!') {
                forward()
                if (currentChar == '=') {
                    forward()
                    return Token.Operator.NotEquals
                }
                throw BadTokenException("Bad token: $currentChar without '='")
            } else if (currentChar == '=') {
                forward()
                return Token.Operator.Equals
            }
            throw BadTokenException("Bad token: $currentChar")
        }
        return null
    }

    fun start(input: String) {
        this.input = input
        index = 0
    }
}