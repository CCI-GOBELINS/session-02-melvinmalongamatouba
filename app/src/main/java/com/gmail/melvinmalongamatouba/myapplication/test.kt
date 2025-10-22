package com.gmail.melvinmalongamatouba.myapplication

import Feuille
import Noeud

fun main() {

    fun runTest() {
        println("🔍 Running Kotlin Functions Playground Tests...\n")
        var passed = 0
        var failed = 0
        fun verify(name: String, block: () -> Boolean) {
            try {
                check(block()) { "❌ Test failed: $name" }
                println("✅ $name")
                passed++
            } catch (e: Throwable) {
                println("❌ $name → ${e.message}")
                failed++
            }
        }


        var calcMarcheDepuisString : Boolean = true
        var temp = ((1.0 + 34.0 * 5.0 - 23.0 + 34.0).toDouble() == Logic.calculerResultat("1 + 34 * 5 - 23 + 34"))
        verify("calculer Expression à partir d'un string semble bon"){temp}
        calcMarcheDepuisString =  temp|| calcMarcheDepuisString

        val obtenu =Logic.calculerResultat("64 - 123 + 243 / 35")
        temp = ((64.0 - 123.0 + 243.0 / 35.0).toDouble() == Logic.calculerResultat("64 - 123 + 243 / 35"))
        verify("calculer Expression à partir d'un string semble bon"){temp}
        calcMarcheDepuisString =  temp|| calcMarcheDepuisString


        verify ("calculer expression à partir d'un string avec des parenthèses est bon"){
            Logic.calculerResultat(" ( 64 - 123 )  +  (  ( 243 / 35 )  * 17 - 23 + 12 ) ") == ((64.0 - 123.0) + ((243.0 / 35.0) *17.0-23.0+12.0) ).toDouble()
        }

        //Cas de 2 signes de parenthèses de suite encore problématique

        temp =Logic.parseExpression("3 + 1") == Noeud(Operation.ADDITION, Feuille(3.0),Feuille(1.0))
        verify("parse 1 lvl + works") {temp}
        calcMarcheDepuisString = calcMarcheDepuisString || temp
        verify("parse 1 lvl - works") {Logic.parseExpression("3 - 1") == Noeud(Operation.SOUSTRACTION, Feuille(3.0),Feuille(1.0))}
        verify("parse 1 lvl * works") {Logic.parseExpression("3 * 1") == Noeud(Operation.MULTIPLICATION, Feuille(3.0),Feuille(1.0))}
        verify("parse 1 lvl / works") {Logic.parseExpression("3 / 1") == Noeud(Operation.DIVISION, Feuille(3.0),Feuille(1.0))}
        verify("parse 1 lvl + works") {Logic.parseExpression("30 + 1") == Noeud(Operation.ADDITION, Feuille(30.0),Feuille(1.0))}

        verify("parse 2 lvls -+ works") {Logic.parseExpression("3 - 1 + 2") == Noeud(Operation.ADDITION, Noeud(Operation.SOUSTRACTION,Feuille(3.0),Feuille(1.0)), Feuille(2.0))}
        verify("parse 2 lvls *+ works") {Logic.parseExpression("3 * 1 + 2") == Noeud(Operation.ADDITION, Noeud(Operation.MULTIPLICATION, Feuille(3.0),Feuille(1.0)), Feuille(2.0))}
        verify("parse 2 lvls /+ works") {Logic.parseExpression("3 / 1 + 2") == Noeud(Operation.ADDITION, Noeud(Operation.DIVISION, Feuille(3.0),Feuille(1.0)), Feuille(2.0))}
        verify("parse 2 lvls *- works") {Logic.parseExpression("3 * 1 - 2") == Noeud(Operation.SOUSTRACTION, Noeud(Operation.MULTIPLICATION, Feuille(3.0),Feuille(1.0)), Feuille(2.0))}
        verify("parse 2 lvls /- works") {Logic.parseExpression("3 / 1 - 2") == Noeud(Operation.SOUSTRACTION, Noeud(Operation.DIVISION, Feuille(3.0),Feuille(1.0)), Feuille(2.0))}
        verify("parse 2 lvls /* works") {Logic.parseExpression("3 / 1 * 2") == Noeud(Operation.MULTIPLICATION, Noeud(Operation.DIVISION, Feuille(3.0),Feuille(1.0)), Feuille(2.0))}

        verify("parse 2 lvls ++ works") {Logic.parseExpression("3 + 1 + 2") == Noeud(Operation.ADDITION, Feuille(3.0),Noeud(Operation.ADDITION, Feuille(1.0),Feuille(2.0)))}
        verify("parse 2 lvls +- works") {Logic.parseExpression("3 + 1 - 2") == Noeud(Operation.ADDITION, Feuille(3.0),Noeud(Operation.SOUSTRACTION, Feuille(1.0),Feuille(2.0)))}
        verify("parse 2 lvls +* works") {Logic.parseExpression("3 + 1 * 2") == Noeud(Operation.ADDITION, Feuille(3.0),Noeud(Operation.MULTIPLICATION, Feuille(1.0),Feuille(2.0)))}
        verify("parse 2 lvls +/ works") {Logic.parseExpression("3 + 1 / 2") == Noeud(Operation.ADDITION, Feuille(3.0),Noeud(Operation.DIVISION, Feuille(1.0),Feuille(2.0)))}
        verify("parse 2 lvls ** works") {Logic.parseExpression("3 * 1 * 2") == Noeud(Operation.MULTIPLICATION, Feuille(3.0),Noeud(Operation.MULTIPLICATION, Feuille(1.0),Feuille(2.0)))}





    }
    runTest()
}