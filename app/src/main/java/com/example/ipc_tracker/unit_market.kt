package com.example.ipc_tracker

import android.content.Context
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private var spacing = 16.dp


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

    label: Int,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier = Modifier
){
//    val unitSilhouette =


    TextField(
        value = value,
        label = { Image(
            painter = painterResource(label),
            contentDescription = stringResource(R.string.infantryPrice),
            modifier = Modifier.width(30.dp).height(30.dp)
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

private var bomberSilhouette = R.drawable.ussr_bomber

private var fighterSilhouette =  R.drawable.ussr_fighter

@Composable
fun SetSilhuoettes(country:Int){
    when(stringResource(country)){
        stringResource(R.string.soviet) -> {
            bomberSilhouette = R.drawable.ussr_bomber
            fighterSilhouette = R.drawable.ussr_fighter
        }
        stringResource(R.string.germany) -> {
            bomberSilhouette = R.drawable.germany_bomber
            fighterSilhouette = R.drawable.germany_fighter
        }
        stringResource(R.string.england) -> {
            bomberSilhouette = R.drawable.uk_bomber
            fighterSilhouette = R.drawable.uk_fighter
        }
        stringResource(R.string.japan) -> {
            bomberSilhouette = R.drawable.japan_bomber
            fighterSilhouette = R.drawable.japan_fighter
        }
        stringResource(R.string.america) -> {
            bomberSilhouette = R.drawable.us_bomber
            fighterSilhouette = R.drawable.us_fighter
        }
    }

}

@Composable
fun UnitMarket(income:String, incomeVal:String, playerCountry:Int,IPCs:String,incomeLabel:Int){
    var playerIncome by remember{mutableStateOf(income)}
    var playerIncomeVal by remember{mutableStateOf(incomeVal)}
    var playerIPCs by remember{mutableStateOf(IPCs)}

    var spentIPCs by remember{mutableStateOf("0")}
    // Number of purchased units.
    // Land Units
    var purchasedTanks by remember {mutableStateOf("0")}
    var purchasedArtillery by remember{mutableStateOf("0")}
    var purchasedInfantry by remember{mutableStateOf("0")}

    var showPurchasedUnits by remember{mutableStateOf(false)}
    var triggerNotEnoughIPCs by remember { mutableStateOf(false) }
    var purchasedAAGuns by remember{mutableStateOf("")}
    var purchasedIndustrialComplexes by remember{mutableStateOf("")}
    var purchasedFighters by remember{mutableStateOf("")}
    var purchasedBombers by remember{mutableStateOf("")}
    var purchasedTransports by remember{mutableStateOf("")}
    var purchasedSubmarines by remember{mutableStateOf("")}
    var purchasedDestroyers by remember{mutableStateOf("")}
    var purchasedCruisers by remember{mutableStateOf("")}
    var purchasedAircraftCarriers by remember{mutableStateOf("")}
    var purchasedBattleships by remember{mutableStateOf("")}

    val screenDimensions = LocalConfiguration.current
    SetSilhuoettes(country = playerCountry)

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
//        Column() {
        Text(text = stringResource(playerCountry), fontSize = 50.sp)

        Spacer(modifier = Modifier.height(spacing))
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(text = "Income: $playerIncome", fontSize = 25.sp)
            Spacer(modifier = Modifier.width(spacing))
            Text(text = "IPC's ${playerIPCs.toInt() - spentIPCs.toInt()}", fontSize = 25.sp)
//                spentIPCs = "0"
        }
        Spacer(modifier = Modifier.height(spacing))


        IPCInputField(
            value = playerIncomeVal,
            onValueChange = {
                playerIncomeVal = it
                },
//            label = incomeLabel,
            label = R.drawable.ussr_bomber,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    if(!playerIncomeVal.isNullOrBlank()){
                        playerIncome = (playerIncome.toInt() + playerIncomeVal.toInt()).toString()
                    }
                },
                modifier = Modifier
                    .width(screenDimensions.screenWidthDp.dp / 2)
                    .padding(spacing)
            ) {
                Text(text = "Add To Income")
            }


            Button(
                onClick = {
                    if(!playerIncomeVal.isNullOrBlank()){
                        playerIncome = (playerIncome.toInt() - playerIncomeVal.toInt()).toString()

                    }
                },
                modifier = Modifier
                    .width(screenDimensions.screenWidthDp.dp / 2)
                    .padding(spacing)
            ) {
                Text(text = "Subtract From Income")
            }
        }

        Spacer (modifier = Modifier.height(spacing))
        if(!showPurchasedUnits){
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {


                Text(text = stringResource(R.string.purchasePrompt),modifier = Modifier.padding(spacing))
                val unitsInputsModifier: Modifier = Modifier
                    .width(screenDimensions.screenWidthDp.dp / 3)
                    .padding(spacing)
//                val unitsInputsModifier:Modifier = Modifier.width(screenDimensions.screenWidthDp.dp / 3)

                Row() {

                    SelectNumOfUnit(unitPrice = 3,
                        numIPCs = playerIPCs,
                        spentIPCs = spentIPCs,
                        toast = {
                            triggerNotEnoughIPCs = true
                        },
                        updateUnits = { numUnits, -> purchasedInfantry = numUnits },
//                        unitType = R.string.infantryPrice,
                        unitType = R.drawable.ussr_bomber,
                        modifier = unitsInputsModifier,
                        setCost = { cost -> spentIPCs = cost }
                    )

//                    Spacer(modifier = Modifier.width(spacing))
                    SelectNumOfUnit(unitPrice = 4,
                        numIPCs = playerIPCs,
                        spentIPCs = spentIPCs,
                        toast = {
                            triggerNotEnoughIPCs = true
                        },
                        updateUnits = { numUnits, -> purchasedArtillery = numUnits },
//                        unitType = R.string.artilleryPrice,
                        unitType = R.drawable.ussr_bomber,
                        modifier = unitsInputsModifier,
                        setCost = { cost -> spentIPCs = cost }
                    )

//                    Spacer(modifier = Modifier.width(spacing))
                    SelectNumOfUnit(unitPrice = 5,
                        numIPCs = playerIPCs,
                        spentIPCs = spentIPCs,
                        toast = {
                            triggerNotEnoughIPCs = true
                        },
                        updateUnits = { numUnits, -> purchasedTanks = numUnits },
//                        unitType = R.string.tankPrice,
                        unitType = R.drawable.ussr_bomber,
                        modifier = unitsInputsModifier,
                        setCost = { cost -> spentIPCs = cost }
                    )


                }
                val industryModifier = Modifier
                    .width(screenDimensions.screenWidthDp.dp / 2)
                    .padding(spacing)
                Row() {
//                    Spacer(modifier = Modifier.width(spacing))
                    SelectNumOfUnit(unitPrice = 6,
                        numIPCs = playerIPCs,
                        spentIPCs = spentIPCs,
                        toast = {
                            triggerNotEnoughIPCs = true
                        },
                        updateUnits = { numUnits, -> purchasedAAGuns = numUnits },
//                        unitType = R.string.AAGunPrice,
                        unitType = R.drawable.ussr_bomber,
                        modifier = industryModifier,
                        setCost = { cost -> spentIPCs = cost }
                    )

                    SelectNumOfUnit(unitPrice = 15,
                        numIPCs = playerIPCs,
                        spentIPCs = spentIPCs,
                        toast = {
                            triggerNotEnoughIPCs = true
                        },
                        updateUnits = { numUnits, -> purchasedIndustrialComplexes = numUnits },
//                        unitType = R.string.industrialComplexPrice,
                        unitType = R.drawable.ussr_bomber,
                        modifier = industryModifier,
                        setCost = { cost -> spentIPCs = cost }
                    )
                }

                Row() {
                    Spacer(modifier = Modifier.width(spacing))
                    SelectNumOfUnit(unitPrice = 10,
                        numIPCs = playerIPCs,
                        spentIPCs = spentIPCs,
                        toast = {
                            triggerNotEnoughIPCs = true
                        },
                        updateUnits = { numUnits, -> purchasedFighters = numUnits },
                        unitType = fighterSilhouette,
                        modifier = industryModifier,
                        setCost = { cost -> spentIPCs = cost }
                    )

                    SelectNumOfUnit(unitPrice = 12,
                        numIPCs = playerIPCs,
                        spentIPCs = spentIPCs,
                        toast = {
                            triggerNotEnoughIPCs = true
                        },
                        updateUnits = { numUnits, -> purchasedBombers = numUnits },
                        unitType = bomberSilhouette,
                        modifier = industryModifier,
                        setCost = { cost -> spentIPCs = cost }
                    )
                }

                Row() {
//                    Spacer(modifier = Modifier.width(spacing))
                    SelectNumOfUnit(unitPrice = 7,
                        numIPCs = playerIPCs,
                        spentIPCs = spentIPCs,
                        toast = {
                            triggerNotEnoughIPCs = true
                        },
                        updateUnits = { numUnits, -> purchasedTransports = numUnits },
                        unitType = R.drawable.ussr_bomber,
//                        unitType = R.string.transportPrice,
                        modifier = unitsInputsModifier,
                        setCost = { cost -> spentIPCs = cost }
                    )

                    SelectNumOfUnit(unitPrice = 6,
                        numIPCs = playerIPCs,
                        spentIPCs = spentIPCs,
                        toast = {
                            triggerNotEnoughIPCs = true
                        },
                        updateUnits = { numUnits, -> purchasedSubmarines = numUnits },
//                        unitType = R.string.submarinePrice,
                        unitType = R.drawable.ussr_bomber,
                        modifier = unitsInputsModifier,
                        setCost = { cost -> spentIPCs = cost }
                    )

                    SelectNumOfUnit(unitPrice = 8,
                        numIPCs = playerIPCs,
                        spentIPCs = spentIPCs,
                        toast = {
                            triggerNotEnoughIPCs = true
                        },
//                        unitType = R.string.destroyerPrice,
                        unitType = R.drawable.ussr_bomber,
                        updateUnits = { numUnits -> purchasedDestroyers = numUnits },
                        modifier = unitsInputsModifier,
                        setCost = { cost -> spentIPCs = cost }
                    )

                }

                Row() {
//                    Spacer(modifier = Modifier.width(spacing))
                    SelectNumOfUnit(unitPrice = 12,
                        numIPCs = playerIPCs,
                        spentIPCs = spentIPCs,
                        toast = {
                            triggerNotEnoughIPCs = true
                        },
                        updateUnits = { numUnits, -> purchasedCruisers = numUnits },
//                        unitType = R.string.cruiserPrice,
                        unitType = R.drawable.ussr_bomber,
                        modifier = unitsInputsModifier,
                        setCost = { cost -> spentIPCs = cost }
                    )

                    SelectNumOfUnit(unitPrice = 14,
                        numIPCs = playerIPCs,
                        spentIPCs = spentIPCs,
                        toast = {
                            triggerNotEnoughIPCs = true
                        },
                        updateUnits = { numUnits, -> purchasedAircraftCarriers = numUnits },
//                        unitType = R.string.aircraftCarrierPrice,
                        unitType = R.drawable.ussr_bomber,
                        modifier = unitsInputsModifier,
                        setCost = { cost -> spentIPCs = cost }
                    )

                    SelectNumOfUnit(unitPrice = 20,
                        numIPCs = playerIPCs,
                        spentIPCs = spentIPCs,
                        toast = {
                            triggerNotEnoughIPCs = true
                        },
//                        unitType = R.string.battleshipPrice,
                        unitType = R.drawable.ussr_bomber,
                        updateUnits = { numUnits -> purchasedBattleships = numUnits },
                        modifier = unitsInputsModifier,
                        setCost = { cost -> spentIPCs = cost },
                        imeAction = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done)
                    )

                }
                ActionButton(text = stringResource(R.string.buyUnits), onClick = {
                    playerIPCs = (playerIPCs.toInt() - spentIPCs.toInt()).toString()
                    showPurchasedUnits = true
                    spentIPCs = "0"
                })
            }
        }else{
            Row(){
                NumberOfUnitsPurchased(numUnits = purchasedInfantry,"Infantry: ")
                Spacer(modifier = Modifier.width(spacing))
                NumberOfUnitsPurchased(numUnits = purchasedArtillery, "Artillery: ")
                Spacer(modifier = Modifier.width(spacing))
                NumberOfUnitsPurchased(numUnits = purchasedTanks, "Tank(s): ")
            }
            Row {
                NumberOfUnitsPurchased(numUnits = purchasedAAGuns, text = "AAgGun(s): ")
                NumberOfUnitsPurchased(
                    numUnits = purchasedIndustrialComplexes,
                    text = "Industrial Complex(s): "
                )
                NumberOfUnitsPurchased(numUnits = purchasedFighters, text = "Fighter(s): ")
            }
            Row {
                NumberOfUnitsPurchased(numUnits = purchasedBombers, text = "Bomber(s):")
                NumberOfUnitsPurchased(numUnits = purchasedTransports, text = "Transport(s): ")
                NumberOfUnitsPurchased(numUnits = purchasedSubmarines, text = "Submarine(s): ")
            }
            Row() {
                NumberOfUnitsPurchased(numUnits = purchasedDestroyers, text = "Destroyer(s): ")
                NumberOfUnitsPurchased(numUnits = purchasedCruisers, text = "Cruiser(s): ")
                NumberOfUnitsPurchased(
                    numUnits = purchasedAircraftCarriers,
                    text = "Aircraftcarrier(s): "
                )
            }
            NumberOfUnitsPurchased(numUnits = purchasedBattleships, text = "Battleship(s): ")
        }
//            Spacer(modifier = Modifier.height(spacing

        // Button to purchase units on and remove the ability to change  the number of units.
//            Spacer(modifier = Modifier.height(spacing))

        ActionButton(text = stringResource(R.string.collectIncome), onClick = {
            playerIPCs = (playerIPCs.toInt() + playerIncome.toInt()).toString()
            showPurchasedUnits = false
            purchasedArtillery = ""
            purchasedInfantry = ""
            purchasedTanks = ""
            purchasedAAGuns = ""
            purchasedFighters = ""
            purchasedBombers = ""
            purchasedTransports = ""
            purchasedSubmarines = ""
            purchasedDestroyers = ""
            purchasedCruisers = ""
            purchasedAircraftCarriers = ""
            purchasedBattleships = ""
        })



        Spacer(modifier = Modifier.height(16.dp))
    }
    if (triggerNotEnoughIPCs) {
        NotEnoughIPCs()
        triggerNotEnoughIPCs = false
    }
}
