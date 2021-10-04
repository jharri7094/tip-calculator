package com.example.TipCalculator

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.TipCalculator.ui.theme.LabsAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LabsAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    var inputTotal by remember { mutableStateOf("") }
    var currentTipPercent by remember {mutableStateOf(TipType.T10)}
    var tip by remember { mutableStateOf("") }
    var totalWithTip by remember { mutableStateOf("")}
    var none by remember { mutableStateOf("")}
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Tip Calculator",
            fontSize = 28.sp,
            modifier = Modifier.padding(16.dp)
        )
        Text(
            text = "Enter your check amount: ",
            fontSize = 12.sp,
            modifier = Modifier.padding(5.dp)
        )
        OutlinedTextField(
            value = inputTotal,
            onValueChange = {newValue -> inputTotal = newValue},
            placeholder = {
                Text("Check Amount")
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions
                .Default
                .copy(keyboardType = KeyboardType.Number, imeAction = ImeAction.Go),
            modifier = Modifier.padding(16.dp)
        )

            Text("Select a tip percentage: ",
                fontSize = 12.sp,
                modifier = Modifier.padding(5.dp)
            )
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            RadioButton(
                selected = currentTipPercent == TipType.T10,
                onClick = {
                    currentTipPercent = TipType.T10
                }
            )
            Text("10%")
        }
        Row (
            modifier = Modifier.padding(16.dp)
                ){
            RadioButton(
                selected = currentTipPercent == TipType.T20 ,
                onClick = {
                    currentTipPercent = TipType.T20
                }
            )
            Text("20%")
        }
        Row (
            modifier = Modifier.padding(16.dp)
                ){
            RadioButton(
                selected = currentTipPercent == TipType.T30 ,
                onClick = {
                    currentTipPercent = TipType.T30
                }
            )
            Text("30%")
        }
        Button(
            onClick = {
                tip = calculateTip(currentTipPercent, inputTotal)
                totalWithTip = calculateTotal(tip, inputTotal)
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Calculate")
        }
        Text(
            "Your calculated tip is: $" + tip + " and your total is: $" + totalWithTip ,
            fontSize = 20.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LabsAppTheme {
        MainScreen()
    }
}

enum class TipType {
    T10, T20, T30
}

fun calculateTip(convertType: TipType, inputTotal: String): String {
    try {
        val inputTotalAsDouble = inputTotal.toDouble()
    } catch(exception: Exception){
        Log.d("TAG", exception.message!!)
        return "ERROR"
    }
    val inputTotalAsDouble = inputTotal.toDouble()
    val tip10 = inputTotalAsDouble * .10
    val tip20 = inputTotalAsDouble * .20
    val tip30 = inputTotalAsDouble * .30
    if(inputTotal.isEmpty()){
        return ""
    }
    return when(convertType){
        TipType.T10 -> {
            String.format("%.2f", tip10.toString())

        }
        TipType.T20 -> {
            String.format("%.2f", tip20.toString())
        }
        TipType.T30 -> {
            String.format("%.2f", tip30.toString())
        }
    }
}

fun calculateTotal(tip: String, inputTotal: String): String {
    try {
        val inputTotalAsDouble = inputTotal.toDouble()
        val tipAsDouble = tip.toDouble()
    } catch(exception: Exception){
        Log.d("TAG", exception.message!!)
        return "ERROR"
    }
    val inputTotalAsDouble = inputTotal.toDouble()
    val tipAsDouble = tip.toDouble()
    return String.format("%.2f", (inputTotalAsDouble + tipAsDouble).toString())
}

fun noInput(input: String): String {
    if(input.isEmpty()){
        return "Please enter a check amount before clicking calculate"
    }
    return ""
}