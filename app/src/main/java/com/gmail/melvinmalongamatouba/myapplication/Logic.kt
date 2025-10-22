import androidx.compose.runtime.mutableStateOf


interface Abonne{
    fun update()
}

enum class Operation{
    ADDITION, SOUSTRACTION, MULTIPLICATION, DIVISION
}

interface Calculable {
    fun calculerResultat() : Double
}

data class Feuille(val valeur: Double) : Calculable {
    init{
        require(valeur >= 0) { "La valeur doit être un entier positif." }
    }
    override fun calculerResultat() : Double {
        return valeur.toDouble()
    }
}

data class Noeud(
    val operateur : Operation,
    val gauche : Calculable,
    val droite : Calculable
) : Calculable {
    override fun calculerResultat(): Double {
        return when (operateur) {
            Operation.ADDITION -> gauche.calculerResultat() + droite.calculerResultat()
            Operation.SOUSTRACTION -> gauche.calculerResultat() - droite.calculerResultat()
            Operation.MULTIPLICATION -> gauche.calculerResultat() * droite.calculerResultat()
            Operation.DIVISION -> gauche.calculerResultat() / droite.calculerResultat()
            else -> throw IllegalArgumentException("Opérateur inconnu: $operateur")
        }
    }
}




object Logic{
    var resultat = mutableStateOf(0.0)
    val entree = mutableStateOf("")
    val expressionPrecedente = mutableStateOf("")


    public fun effacer() {
        entree.value = ""
    }

    fun parseExpression(expression: String): Calculable { //sera privée
        fun parseBy(symbole: Char, operateur: Operation, expression: String): Calculable? {
            if (expression.contains(symbole)) {
                val index = expression.indexOf(symbole)
                if (index != 0 && index != expression.length - 1) {
                    return Noeud(
                        operateur,
                        parseExpression(expression.substring(0, index - 1)),
                        parseExpression(expression.substring(index + 2))
                    )
                } else {
                    //throw exception or error operand au debut ou fin : Interdit
                    return null
                }
            }
            return null //Pas d'operand concerné dans l'expression
        }

        val pass1 = parseBy('+', Operation.ADDITION, expression)
        if (pass1 == null) {
            val pass2 = parseBy('-', Operation.SOUSTRACTION, expression)
            if (pass2 == null) {
                val pass3 = parseBy('*', Operation.MULTIPLICATION, expression)
                if (pass3 == null) {
                    val pass4 = parseBy('/', Operation.DIVISION, expression)
                    if (pass4 != null) {
                        return pass4;
                    } else {
                        //println("Casting expression $expression to double")
                        return Feuille(expression.toDouble())
                    }
                } else {
                    return pass3
                }
            } else {
                return pass2;
            }
        } else {
            return pass1;
        }
    }

    fun calculerResultat(calculable : Calculable) : Double{
        return calculable.calculerResultat()
    }

    fun calculerResultat(expression: String) : Double{
        return calculerResultat(parseExpression(expression))
    }

    fun calculerResultat() {
        resultat.value = calculerResultat(expression = entree.value)
        expressionPrecedente.value = entree.value
    }

    fun ajouterCaractere(cle: String) {
        entree.value += cle
    }

}