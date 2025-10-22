import androidx.compose.runtime.mutableStateOf


interface Abonne{
    fun update()
}

enum class Operation{
    ADDITION, SOUSTRACTION, MULTIPLICATION, DIVISION
}

fun getOp(string : String) : Operation {
    return when (string){
        " + " -> Operation.ADDITION
        " - " -> Operation.SOUSTRACTION
        " * " -> Operation.MULTIPLICATION
        " / " -> Operation.DIVISION
        else -> throw IllegalArgumentException("Opérateur inconnu: |$string")
    }
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


        fun parentheseParse(expression: String) : Calculable? {
            if (expression.contains('(')){
                val startIndex = expression.indexOf('(')

                println(startIndex)
                if (!expression.contains(')'))
                    return null;
                val endIndex = expression.indexOf(')')
                if (endIndex<startIndex)
                    return null;
                //find Left operand
                if (startIndex < 4 && endIndex +5 > expression.length){ //TOute l'expression est entre parenthese
                    return parseExpression(expression.substring(startIndex+1, endIndex-1))
                } else {
                    if (startIndex < 4){ //Pas d'operand à gauche
                        return Noeud(
                            getOp(expression.substring(endIndex+2, endIndex+5)),
                            parseExpression(expression.substring(startIndex +1, endIndex)),
                            parseExpression(expression.substring(endIndex +5))
                        )
                    }
                    if (endIndex +5 > expression.length){ //Pas d'operand à droite
                        return Noeud(
                            getOp(expression.substring(startIndex-4, startIndex-1)),
                            parseExpression(expression.substring(0, startIndex-5)),
                            parseExpression(expression.substring(startIndex +1, endIndex))
                        )
                    }
                }

                return Noeud(
                    getOp(expression.substring(startIndex-4, startIndex-1)),
                    parseExpression(expression.substring(0, startIndex-5)),
                    Noeud(
                        getOp(expression.substring(endIndex+2, endIndex+5)),
                        parseExpression(expression.substring(startIndex +1, endIndex)),
                        parseExpression(expression.substring(endIndex +5))
                    )
                )

            }
            return null
        }
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
                    //throw exception or error car operand au debut ou fin
                    return null
                }
            }
            return null //Pas d'operand concerné dans l'expression
        }

        val pass0 = parentheseParse(expression)
        if (pass0 != null) {
            return pass0
        }
        val pass1 = parseBy('+', Operation.ADDITION, expression)
        if (pass1 != null)
            return pass1;
        val pass2 = parseBy('-', Operation.SOUSTRACTION, expression)
        if (pass2 != null)
            return pass2;
        val pass3 = parseBy('*', Operation.MULTIPLICATION, expression)
        if (pass3 != null)
            return pass3;
        val pass4 = parseBy('/', Operation.DIVISION, expression)
        if (pass4 != null) {
            return pass4;
        } else {
            //println("Casting expression $expression to double")
            return Feuille(expression.toDouble())
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