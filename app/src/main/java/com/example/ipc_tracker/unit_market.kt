package com.example.ipc_tracker

import android.content.Context
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.DimenRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp


@Composable
fun NumberOfUnitsPurchased(numUnits:String,text:String) {
    if (numUnits.isNotEmpty()) {
        if (numUnits.toInt() > 0) {
            Text(text = text + numUnits, modifier = Modifier.padding(16.dp))
        }
    }
}

private fun calculateCost(unitsOrdered:String, previousNumUnits: String,numIPCs:String, unitPrice:Int,action: () -> Unit):String {
    var cost = "0"
    var currentNumOfUnits = if(unitsOrdered.isNullOrBlank()){
        "0"
    }else{
        unitsOrdered
    }
//    if(!previousNumUnits.isNullOrBlank()) {
    cost = if (previousNumUnits != "0" && !previousNumUnits.isNullOrBlank()) {
        Log.d("[calculateCost]","previousNumUnits = $previousNumUnits")
        (currentNumOfUnits.toInt() * unitPrice - previousNumUnits.toInt() * unitPrice).toString()

    } else {
        Log.d("[calculateCost]","previousNumUnits = $previousNumUnits")

        (currentNumOfUnits.toInt() * unitPrice).toString()
    }
    Log.d("[calculateCost","cost = $cost")

    if (cost.toInt() > numIPCs.toInt()) {
        Log.d("[cost]","numIPCs = $numIPCs")

        cost = (numIPCs.toInt() / unitPrice * unitPrice).toString()
        action()
    }
//    }

    return cost
}

private fun getCost(unitsOrdered:String, previousNumOfOrderedUnits: String,numIPCs:String, unitPrice:Int,action: () -> Unit):String{
    var cost = "0"
    var previousNumUnits = previousNumOfOrderedUnits
    if(!unitsOrdered.isNullOrBlank()) {
        if(previousNumUnits == ""){
            previousNumUnits = "0"
        }

        cost = calculateCost(unitsOrdered, previousNumUnits,(numIPCs.toInt()).toString(),unitPrice = unitPrice,action = action)
//        previousNumUnits = (newlySpentIPCs.toInt() / unitPrice).toString()
//        cost = (numIPCs.toInt() + newlySpentIPCs.toInt()).toString()
    }else{
        if(previousNumUnits == ""){
            previousNumUnits = "0"
        }
        cost = calculateCost("0",previousNumUnits,numIPCs = numIPCs,unitPrice = unitPrice,action = action)
//        previousNumUnits = ""
    }

    return cost
}

@Composable
fun NotEnoughIPCs(toastContext: Context = LocalContext.current){
    Toast.makeText(toastContext,"Not Enough IPC's", Toast.LENGTH_SHORT,).show()
}

@Composable
fun IPCInputField(

    @StringRes label: Int,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier = Modifier
){
//    val unitSilhouette =


    TextField(
        value = value,
        label = { Image(
            painter = painterResource(R.drawable.ic_launcher_background),
            contentDescription = stringResource(R.string.infantryPrice),
            modifier = Modifier
//        .width(dimensionResource(R.dimen.button_image_width))
//        .height(dimensionResource(R.dimen.button_image_height))
//        .padding(dimensionResource(R.dimen.button_interior_padding))
        )},
        keyboardOptions = keyboardOptions,
        singleLine = true,
        onValueChange = onValueChange,
        modifier = modifier,
    )
}

@Composable
fun ActionButton(onClick: () -> Unit,text:String,modifier: Modifier = Modifier){
    Button(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),onClick = onClick){
        Text(text = text)
    }
}

@Composable
fun SelectNumOfUnit(unitPrice:Int,numIPCs:String,spentIPCs:String,toast:() -> Unit,updateUnits: (String) -> Unit,setCost:(String) -> Unit, unitType:Int,imeAction:KeyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number,
    imeAction = ImeAction.Next), modifier: Modifier = Modifier){
    var numberOfUnits by remember{ mutableStateOf("") }
    var newCost by remember{ mutableStateOf("0") }
    IPCInputField(
        value = numberOfUnits,
        onValueChange = {
            newCost = getCost(
                it,
                numberOfUnits,
                numIPCs = (numIPCs.toInt() - spentIPCs.toInt()).toString(),
                unitPrice = unitPrice,
                action = toast,
            )

            if (numberOfUnits.isEmpty()) {
                numberOfUnits = "0"
            }
            numberOfUnits = (numberOfUnits.toInt() + newCost.toInt() / unitPrice).toString()
            newCost = (spentIPCs.toInt() + newCost.toInt()).toString()
            setCost(newCost)
            if (it == "") {
                numberOfUnits = it
            }
            updateUnits(numberOfUnits)

        },
        keyboardOptions = imeAction,
        label = unitType,

        modifier = modifier
    )
}