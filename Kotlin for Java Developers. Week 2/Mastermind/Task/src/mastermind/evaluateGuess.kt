package mastermind

data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

fun evaluateGuess(secret: String, guess: String): Evaluation {

    var rightPosition = 0
    var wrongPosition = 0
    var notGuessedGuess = ""
    var notGuessedSecret = ""

    for ((i, ch) in guess.withIndex()) {
        val secretCh = secret[i]
        if (ch == secretCh) {
            rightPosition++
        }
        else {
            notGuessedGuess += ch
            notGuessedSecret += secretCh
        }
    }
    for (ch in notGuessedGuess) {
        if (notGuessedSecret.contains(ch)) {
            wrongPosition++
            notGuessedSecret = notGuessedSecret.replaceFirst(ch.toString(),"")
        }
    }
    return Evaluation(rightPosition, wrongPosition)
}
