import java.util.Stack

class Calc {
    private val operators = arrayOf("+", "-", "*", "/")

    private fun isOperator(token: String): Boolean {
        return operators.contains(token)
    }

    private fun getOperatorPriority(operator: String): Int {
        return when (operator) {
            "*", "/" -> 2
            "+", "-" -> 1
            else -> 0
        }
    }

    private fun convertToRPN(input: String): String {
        val output = StringBuilder() //変数の中身を文字列に変換
        val stack = Stack<String>()

        val tokens = input.trim().split(" ")

        for (token in tokens) {
            if (isOperator(token)) {
                while (!stack.empty() && isOperator(stack.peek()) && getOperatorPriority(stack.peek()) >= getOperatorPriority(token)) {
                    output.append(stack.pop()).append(" ")
                }
                stack.push(token)
            } else if (token == "(") {
                stack.push(token)
            } else if (token == ")") {
                while (!stack.empty() && stack.peek() != "(") {
                    output.append(stack.pop()).append(" ")
                }
                stack.pop()
            } else {
                output.append(token).append(" ")
            }
        }

        while (!stack.empty()) {
            output.append(stack.pop()).append(" ")
        }

        return output.toString().trim()
    }

    private val stack: Stack<Double> = Stack()

    fun evaluate(expression: String): Double {
        val tokens = expression.split(" ")

        for (token in tokens) {
            if (isNumeric(token)) {
                stack.push(token.toDouble())
            } else {
                val operand2 = stack.pop()
                val operand1 = stack.pop()
                val result = performOperation(token, operand1, operand2)
                stack.push(result)
            }
        }

        return stack.pop()
    }

    private fun isNumeric(token: String): Boolean {
        return token.matches(Regex("-?\\d+(\\.\\d+)?"))
    }

    private fun performOperation(operator: String, operand1: Double, operand2: Double): Double {
        return when (operator) {
            "+" -> operand1 + operand2
            "-" -> operand1 - operand2
            "*" -> operand1 * operand2
            "/" -> operand1 / operand2
            else -> throw IllegalArgumentException("Invalid operator: $operator")
        }
    }

    fun calculate(expression: String): Double {
        val rpnExpression = convertToRPN(expression)
        return evaluate(rpnExpression)
    }
}