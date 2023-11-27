package token

internal sealed class Token {
    sealed class Operator(val value: String) : Token() {
        data object Equals : Operator("=")
        data object NotEquals : Operator("!=")
        data object LessThan : Operator("<")
        data object GreaterThan : Operator(">")
        data object LessThanOrEquals : Operator("<=")
        data object GreaterThanOrEquals : Operator(">=")
    }

    data class Number(val value: Int) : Token()
}
