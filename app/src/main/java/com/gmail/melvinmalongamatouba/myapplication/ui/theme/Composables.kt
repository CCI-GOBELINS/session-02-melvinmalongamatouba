package com.gmail.melvinmalongamatouba.myapplication.ui.theme

import Logic
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun Calculatrice(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp), // padding global
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ExpressionPrecedente(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.DarkGray)
                .padding(8.dp)
        )

        CalculEnCours(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Gray)
                .padding(12.dp)
        )

        ResultatBox(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .padding(12.dp)
        )

        Boutons(
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun ExpressionPrecedente(modifier: Modifier = Modifier) {
Row() {
    Text("Calcul précédent")
    ExpressionBox(
        text = Logic.expressionPrecedente.value,
        modifier = modifier
    )
}
}

@Composable
fun Boutons(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            BoutonExp("7")
            BoutonExp("8")
            BoutonExp("9")
            BoutonExp(" / ")
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            BoutonExp("4")
            BoutonExp("5")
            BoutonExp("6")
            BoutonExp(" * ")
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            BoutonExp("1")
            BoutonExp("2")
            BoutonExp("3")
            BoutonExp(" - ")
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            BoutonExp("0")
            BoutonEff()
            BoutonEgal()
            BoutonExp(" + ")
        }
    }
}

@Composable
fun BoutonExp(cle: String) {
    Button(
        onClick = { Logic.ajouterCaractere(cle) },
        shape = CircleShape,
        modifier = Modifier.size(72.dp) // Taille fixe carrée
    ) {
        Text(text = cle, fontSize = 20.sp)
    }
}

@Composable
fun BoutonEff() {
    Button(
        onClick = { Logic.effacer() },
        shape = CircleShape,
        modifier = Modifier.size(72.dp)
    ) {
        Text(text = "C", fontSize = 20.sp)
    }
}

@Composable
fun BoutonEgal() {
    Button(
        onClick = { Logic.calculerResultat() },
        shape = CircleShape,
        modifier = Modifier.size(72.dp)
    ) {
        Text(text = "=", fontSize = 20.sp)
    }
}

@Composable
fun CalculEnCours(expression: MutableState<String> = Logic.entree, modifier: Modifier = Modifier) {
    Row(modifier){
        Text("Calcul en cours: ")
        ExpressionBox(expression.value, modifier)
    }
}

@Composable
fun ResultatBox(resultat: MutableState<Double> = Logic.resultat, modifier: Modifier = Modifier) {
    Row(modifier) {
        Text("Resultat: ")
        ExpressionBox(resultat.value.toString(), modifier)
    }
}

@Composable
fun ExpressionBox(text: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterEnd // texte aligné à droite
    ) {
        Text(
            text = text,
            fontSize = 24.sp,
            color = Color.White,
            modifier = Modifier.padding(8.dp)
        )
    }
}
