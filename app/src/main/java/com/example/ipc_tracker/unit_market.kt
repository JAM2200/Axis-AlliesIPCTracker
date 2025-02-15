package com.example.ipc_tracker

import android.content.Context
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ipc_tracker.ui.theme.lightMilitaryTan
import com.example.ipc_tracker.ui.theme.militaryGreen

private var spacing = 16.dp


@Composable
fun NumberOfUnitsPurchased(numUnits:String,text:String,pictureResource:Int,modifier:Modifier = Modifier) {
    if (numUnits.isNotEmpty()) {
        if (numUnits.toInt() > 0) {
            Row(modifier = Modifier.padding(spacing)) {
                Image(
                    painter = painterResource(pictureResource),
                    contentDescription = stringResource(R.string.infantryPrice),
                    modifier = modifier
                        .width(100.dp)
                        .height(100.dp).padding(spacing)
                )
                Text(text = text + numUnits,color = Color.LightGray, modifier = modifier.padding(16.dp))
            }
        }
    }


}


@Composable
fun NotEnoughIPCs(toastContext: Context = LocalContext.current){
    Toast.makeText(toastContext,"Not Enough IPC's", Toast.LENGTH_SHORT,).show()
}


@Composable
fun ActionButton(onClick: () -> Unit,text:String,modifier: Modifier = Modifier){
    Button(modifier = modifier
        .fillMaxWidth()
        .padding(16.dp),onClick = onClick){
        Text(text = text,color = Color.LightGray)
    }
}

@Composable
fun SelectNumOfUnit(unitPrice:Int,numIPCs:String,spentIPCs:String,toast:() -> Unit,updateUnits: (String) -> Unit,setCost:(String) -> Unit, unitType:Int,
    unitName:String, modifier: Modifier = Modifier){
    var numberOfUnits by remember{ mutableStateOf("0") }
    var newCost by remember{ mutableStateOf("0") }


//    Spacer(modifier = modifier.height(spacing))
    Column(){


    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {

            Image(
                painter = painterResource(unitType),
                contentDescription = stringResource(R.string.infantryPrice),
                modifier = modifier
                    .width(60.dp)
                    .height(60.dp)
            )

            Spacer(modifier = Modifier.width(spacing))

            AddSubtract(onAdd = { addUnit ->

                newCost = (unitPrice * addUnit).toString()

                if (newCost.toInt() + spentIPCs.toInt() > numIPCs.toInt()) {
                    toast()
                    newCost = "0"
                } else {
                    if (!(numberOfUnits == "0" && addUnit < 0)) {
                        numberOfUnits = (numberOfUnits.toInt() + addUnit).toString()

                    } else {
                        newCost = "0"
                    }
                }

                setCost(newCost)
                updateUnits(numberOfUnits)
//                }
            }, modifier = Modifier)
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth().padding(spacing)) {

            Text(text = "$numberOfUnits",modifier = Modifier.padding(spacing), fontSize = 20.sp)
        }
    }
        Text(text = "$unitName Cost: $unitPrice",color = militaryGreen, modifier = modifier)


    }
}

private var bomberSilhouette = R.drawable.ussr_bomber
private var fighterSilhouette =  R.drawable.ussr_fighter
private var infantrySilhouette = R.drawable.ussr_infantry
private var tankSilhouette = R.drawable.ussr_tank
private var artillerySilhouette = R.drawable.ussr_artillery
private var transportSilhouette = R.drawable.ussr_transport
private var submarineSilhouette = R.drawable.ussr_sub
private var aircraftCarrierSilhouette = R.drawable.ussr_aircraft_carrier
private var battleshipSilhouette = R.drawable.ussr_battleship
private var destroyerSilhouette = R.drawable.ussr_destroyer
private var cruiserSilhouette = R.drawable.ussr_cruiser
private var industrialComplexSilhouette = R.drawable.industrial_complex
private var AAGunSilhouette = R.drawable.aagun

var globalPurchasedTanks = "0"
var globalPurchasedArtillery = "0"
var globalPurchasedInfantry = "0"

var globalShowPurchasedUnits = false
var globalTriggerNotEnoughIPCs = false
var globalPurchasedAAGuns = ""
var globalPurchasedIndustrialComplexes = ""
var globalPurchasedFighters = ""
var globalPurchasedBombers = ""
var globalPurchasedTransports = ""
var globalPurchasedSubmarines = ""
var globalPurchasedDestroyers = ""
var globalPurchasedCruisers = ""
var globalPurchasedAircraftCarriers = ""
var globalPurchasedBattleships = ""
var globalIndustrialComplexRepair = ""




@Composable
fun SetSilhuoettes(country:Int){
    when(stringResource(country)){
        stringResource(R.string.soviet) -> {
            bomberSilhouette = R.drawable.ussr_bomber
            fighterSilhouette =  R.drawable.ussr_fighter
            infantrySilhouette = R.drawable.ussr_infantry
            tankSilhouette = R.drawable.ussr_tank
            artillerySilhouette = R.drawable.ussr_artillery
            transportSilhouette = R.drawable.ussr_transport
            submarineSilhouette = R.drawable.ussr_sub
            aircraftCarrierSilhouette = R.drawable.ussr_aircraft_carrier
            battleshipSilhouette = R.drawable.ussr_battleship
            destroyerSilhouette = R.drawable.ussr_destroyer
            cruiserSilhouette = R.drawable.ussr_cruiser
        }
        stringResource(R.string.germany) -> {
            bomberSilhouette = R.drawable.germany_bomber
            fighterSilhouette =  R.drawable.germany_fighter
            infantrySilhouette = R.drawable.germany_infantry
            tankSilhouette = R.drawable.germany_tank
            artillerySilhouette = R.drawable.germany_artillery
            transportSilhouette = R.drawable.germany_transport
            submarineSilhouette = R.drawable.germany_sub
            aircraftCarrierSilhouette = R.drawable.germany_aircraft_carrier
            battleshipSilhouette = R.drawable.germany_battleship
            destroyerSilhouette = R.drawable.germany_destroyer
            cruiserSilhouette = R.drawable.germany_cruiser
        }
        stringResource(R.string.england) -> {
            bomberSilhouette = R.drawable.uk_bomber
            fighterSilhouette =  R.drawable.uk_fighter
            infantrySilhouette = R.drawable.uk_infantry
            tankSilhouette = R.drawable.uk_tank
            artillerySilhouette = R.drawable.uk_artillery
            transportSilhouette = R.drawable.uk_transport
            submarineSilhouette = R.drawable.uk_sub
            aircraftCarrierSilhouette = R.drawable.uk_aircraft_carrier
            battleshipSilhouette = R.drawable.uk_battleship
            destroyerSilhouette = R.drawable.uk_destroyer
            cruiserSilhouette = R.drawable.uk_cruiser
        }
        stringResource(R.string.japan) -> {
            bomberSilhouette = R.drawable.japan_bomber
            fighterSilhouette =  R.drawable.japan_fighter
            infantrySilhouette = R.drawable.japan_infantry
            tankSilhouette = R.drawable.japan_tank
            artillerySilhouette = R.drawable.japan_artillery
            transportSilhouette = R.drawable.japan_transport
            submarineSilhouette = R.drawable.japan_sub
            aircraftCarrierSilhouette = R.drawable.japan_aircraft_carrier
            battleshipSilhouette = R.drawable.japan_battleship
            destroyerSilhouette = R.drawable.japan_destroyer
            cruiserSilhouette = R.drawable.japan_cruiser
        }
        stringResource(R.string.america) -> {
            bomberSilhouette = R.drawable.us_bomber
            fighterSilhouette =  R.drawable.us_fighter
            infantrySilhouette = R.drawable.us_infantry
            tankSilhouette = R.drawable.us_tank
            artillerySilhouette = R.drawable.us_artillery
            transportSilhouette = R.drawable.us_transport
            submarineSilhouette = R.drawable.us_sub
            aircraftCarrierSilhouette = R.drawable.us_aircraft_carrier
            battleshipSilhouette = R.drawable.us_battleship
            destroyerSilhouette = R.drawable.us_destroyer
            cruiserSilhouette = R.drawable.us_cruiser
        }
    }

}

@Composable
fun UnitMarket( playerCountry:Int,IPCs:String,backToMainMenu:(String,Boolean) -> Unit,modifier:Modifier = Modifier){
    var playerIPCs by remember{mutableStateOf(IPCs)}

    var spentIPCs by rememberSaveable{mutableStateOf("0")}

    // Number of purchased units.
    // Land Units
    var purchasedTanks by rememberSaveable {mutableStateOf(globalPurchasedTanks)}
    var purchasedArtillery by rememberSaveable{mutableStateOf(globalPurchasedArtillery)}
    var purchasedInfantry by rememberSaveable{mutableStateOf(globalPurchasedInfantry)}

    var showPurchasedUnits by rememberSaveable{mutableStateOf(globalShowPurchasedUnits)}
    var triggerNotEnoughIPCs by rememberSaveable { mutableStateOf(false) }
    var purchasedAAGuns by rememberSaveable{mutableStateOf(globalPurchasedAAGuns)}
    var purchasedIndustrialComplexes by rememberSaveable{mutableStateOf(globalPurchasedIndustrialComplexes)}
    var purchasedFighters by rememberSaveable{mutableStateOf(globalPurchasedFighters)}
    var purchasedBombers by rememberSaveable{mutableStateOf(globalPurchasedBombers)}
    var purchasedTransports by rememberSaveable{mutableStateOf(globalPurchasedTransports)}
    var purchasedSubmarines by rememberSaveable{mutableStateOf(globalPurchasedSubmarines)}
    var purchasedDestroyers by rememberSaveable{mutableStateOf(globalPurchasedDestroyers)}
    var purchasedCruisers by rememberSaveable{mutableStateOf(globalPurchasedCruisers)}
    var purchasedAircraftCarriers by rememberSaveable{mutableStateOf(globalPurchasedAircraftCarriers)}
    var purchasedBattleships by rememberSaveable{mutableStateOf(globalPurchasedBattleships)}
    var industrialComplexRepair by rememberSaveable{mutableStateOf(globalIndustrialComplexRepair)}

    val screenDimensions = LocalConfiguration.current
    SetSilhuoettes(country = playerCountry)

    Column(horizontalAlignment = Alignment.CenterHorizontally,modifier = modifier.fillMaxHeight().background(color = lightMilitaryTan)) {
//        Column() {

        Text(text = stringResource(playerCountry),color = militaryGreen, fontSize = 50.sp)

        Spacer(modifier = modifier.height(spacing))
        Row(modifier = modifier.fillMaxWidth().background(lightMilitaryTan)) {
//            Text(text = "Income: $playerIncome", fontSize = 25.sp)
//            Spacer(modifier = modifier.width(spacing))
            Text(text = "IPC's ${playerIPCs.toInt() - spentIPCs.toInt()}", color = Color.LightGray,fontSize = 25.sp,modifier = Modifier.padding(spacing).background(color = lightMilitaryTan))
//                spentIPCs = "0"
        }
        Spacer(modifier = modifier.height(spacing))

        if (!showPurchasedUnits) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {


//                Column(modifier = modifier) {
//                Column(modifier = modifier.verticalScroll(rememberScrollState()).height(screenDimensions.screenHeightDp.dp / 2)) {
                    Column(modifier = modifier.verticalScroll(rememberScrollState())
                        .weight(weight = 1f, fill = false)) {

                val industryModifier = Modifier
                    .width(screenDimensions.screenWidthDp.dp / 2)
                    .padding(spacing)

                Text(
                    text = stringResource(R.string.purchasePrompt),
                    modifier = modifier.padding(spacing),
                    color = Color.LightGray
                )
                val unitsInputsModifier: Modifier = Modifier
//                    .width(screenDimensions.screenWidthDp.dp / 3)
                    .padding(spacing)
//                val unitsInputsModifier:Modifier = Modifier.width(screenDimensions.screenWidthDp.dp / 3)


                SelectNumOfUnit(unitPrice = 3,
                    numIPCs = playerIPCs,
                    spentIPCs = spentIPCs,
                    toast = {
                        triggerNotEnoughIPCs = true
                    },
                    updateUnits = { numUnits, -> purchasedInfantry = numUnits },
//                        unitType = R.string.infantryPrice,
                    unitType = infantrySilhouette,
                    modifier = unitsInputsModifier,
                    setCost = { cost -> spentIPCs = (spentIPCs.toInt() + cost.toInt()).toString()},
                    unitName = "Infantry"
                )

////                    Spacer(modifier = modifier.width(spacing))
                SelectNumOfUnit(unitPrice = 4,
                    numIPCs = playerIPCs,
                    spentIPCs = spentIPCs,
                    toast = {
                        triggerNotEnoughIPCs = true
                    },
                    updateUnits = { numUnits, -> purchasedArtillery = numUnits },
//                        unitType = R.string.artilleryPrice,
                    unitType = artillerySilhouette,
                    modifier = unitsInputsModifier,
                    setCost = { cost -> spentIPCs = (spentIPCs.toInt() + cost.toInt()).toString()},
                    unitName = "Artillery"
                )
//
                SelectNumOfUnit(unitPrice = 5,
                    numIPCs = playerIPCs,
                    spentIPCs = spentIPCs,
                    toast = {
                        triggerNotEnoughIPCs = true
                    },
                    updateUnits = { numUnits, -> purchasedTanks = numUnits },
//                        unitType = R.string.tankPrice,
                    unitType = tankSilhouette,
                    modifier = unitsInputsModifier,
                    setCost = { cost -> spentIPCs = (spentIPCs.toInt() + cost.toInt()).toString()},
                    unitName = "Tank"
                )

                SelectNumOfUnit(unitPrice = 6,
                    numIPCs = playerIPCs,
                    spentIPCs = spentIPCs,
                    toast = {
                        triggerNotEnoughIPCs = true
                    },
                    updateUnits = { numUnits, -> purchasedAAGuns = numUnits },
//                        unitType = R.string.AAGunPrice,
                    unitType = AAGunSilhouette,
                    modifier = unitsInputsModifier,
                    setCost = { cost -> spentIPCs = (spentIPCs.toInt() + cost.toInt()).toString()},
                    unitName = "AA Gun"
                )

                SelectNumOfUnit(unitPrice = 15,
                    numIPCs = playerIPCs,
                    spentIPCs = spentIPCs,
                    toast = {
                        triggerNotEnoughIPCs = true
                    },
                    updateUnits = { numUnits, -> purchasedIndustrialComplexes = numUnits },
//                        unitType = R.string.industrialComplexPrice,
                    unitType = industrialComplexSilhouette,
                    modifier = unitsInputsModifier,
                    setCost = { cost -> spentIPCs = (spentIPCs.toInt() + cost.toInt()).toString()},
                    unitName = "Industrial Complex"
                )

                SelectNumOfUnit(unitPrice = 10,
                    numIPCs = playerIPCs,
                    spentIPCs = spentIPCs,
                    toast = {
                        triggerNotEnoughIPCs = true
                    },
                    updateUnits = { numUnits, -> purchasedFighters = numUnits },
                    unitType = fighterSilhouette,
                    modifier = unitsInputsModifier,
                    setCost = { cost -> spentIPCs = (spentIPCs.toInt() + cost.toInt()).toString()},
                    unitName = "Fighter"
                )

                SelectNumOfUnit(unitPrice = 12,
                    numIPCs = playerIPCs,
                    spentIPCs = spentIPCs,
                    toast = {
                        triggerNotEnoughIPCs = true
                    },
                    updateUnits = { numUnits, -> purchasedBombers = numUnits },
                    unitType = bomberSilhouette,
                    modifier = unitsInputsModifier,
                    setCost = { cost -> spentIPCs = (spentIPCs.toInt() + cost.toInt()).toString()},
                    unitName = "Bomber"
                )

                SelectNumOfUnit(unitPrice = 7,
                    numIPCs = playerIPCs,
                    spentIPCs = spentIPCs,
                    toast = {
                        triggerNotEnoughIPCs = true
                    },
                    updateUnits = { numUnits, -> purchasedTransports = numUnits },
                    unitType = transportSilhouette,

                    modifier = unitsInputsModifier,
                    setCost = { cost -> spentIPCs = (spentIPCs.toInt() + cost.toInt()).toString()},
                    unitName = "Transport"
                )

                SelectNumOfUnit(unitPrice = 6,
                    numIPCs = playerIPCs,
                    spentIPCs = spentIPCs,
                    toast = {
                        triggerNotEnoughIPCs = true
                    },
                    updateUnits = { numUnits, -> purchasedSubmarines = numUnits },
//                        unitType = R.string.submarinePrice,
                    unitType = submarineSilhouette,
                    modifier = unitsInputsModifier,
                    setCost = { cost -> spentIPCs = (spentIPCs.toInt() + cost.toInt()).toString()},
                    unitName = "Submarine"
                )

                SelectNumOfUnit(unitPrice = 8,
                    numIPCs = playerIPCs,
                    spentIPCs = spentIPCs,
                    toast = {
                        triggerNotEnoughIPCs = true
                    },
//                        unitType = R.string.destroyerPrice,
                    unitType = destroyerSilhouette,
                    updateUnits = { numUnits -> purchasedDestroyers = numUnits },
                    modifier = unitsInputsModifier,
                    setCost = { cost -> spentIPCs = (spentIPCs.toInt() + cost.toInt()).toString()},
                    unitName = "Destroyer"
                )


                SelectNumOfUnit(unitPrice = 12,
                    numIPCs = playerIPCs,
                    spentIPCs = spentIPCs,
                    toast = {
                        triggerNotEnoughIPCs = true
                    },
                    updateUnits = { numUnits, -> purchasedCruisers = numUnits },
//                        unitType = R.string.cruiserPrice,
                    unitType = cruiserSilhouette,
                    modifier = unitsInputsModifier,
                    setCost = { cost -> spentIPCs = (spentIPCs.toInt() + cost.toInt()).toString()},
                    unitName = "Cruiser"
                )

                SelectNumOfUnit(unitPrice = 14,
                    numIPCs = playerIPCs,
                    spentIPCs = spentIPCs,
                    toast = {
                        triggerNotEnoughIPCs = true
                    },
                    updateUnits = { numUnits, -> purchasedAircraftCarriers = numUnits },
//                        unitType = R.string.aircraftCarrierPrice,
                    unitType = aircraftCarrierSilhouette,
                    modifier = unitsInputsModifier,
                    setCost = { cost -> spentIPCs = (spentIPCs.toInt() + cost.toInt()).toString()},
                    unitName = "Aircraft Carrier"
                )

                SelectNumOfUnit(
                    unitPrice = 20,
                    numIPCs = playerIPCs,
                    spentIPCs = spentIPCs,
                    toast = {
                        triggerNotEnoughIPCs = true
                    },
//                        unitType = R.string.battleshipPrice,
                    unitType = battleshipSilhouette,
                    updateUnits = { numUnits -> purchasedBattleships = numUnits },
                    modifier = unitsInputsModifier,
                    setCost = { cost -> spentIPCs = (spentIPCs.toInt() + cost.toInt()).toString()},
                    unitName = "Battleship"
                )

                SelectNumOfUnit(
                    unitPrice = 1,
                    numIPCs = playerIPCs,
                    spentIPCs = spentIPCs,
                    toast = {
                        triggerNotEnoughIPCs = true
                    },
                    unitType = R.drawable.industrial_complex_repair,
                    updateUnits = { numUnits -> industrialComplexRepair = numUnits },
                    modifier = unitsInputsModifier,
                    setCost = { cost -> spentIPCs = (spentIPCs.toInt() + cost.toInt()).toString()},
                    unitName = "Repair"
                )
                }

                Column() {
                    ActionButton(text = stringResource(R.string.buyUnits), onClick = {
                        playerIPCs = (playerIPCs.toInt() - spentIPCs.toInt()).toString()
                        showPurchasedUnits = true
                        spentIPCs = "0"
                    }, modifier = Modifier.background(color = lightMilitaryTan))
                }



            }

//
        }else{
            Column(modifier = Modifier.background(color = lightMilitaryTan)){
            Column(modifier = Modifier.verticalScroll(rememberScrollState())
                .weight(weight = 1f, fill = false)){
                NumberOfUnitsPurchased(numUnits = purchasedInfantry, "Infantry: ",infantrySilhouette,modifier = Modifier)
                NumberOfUnitsPurchased(numUnits = purchasedArtillery, "Artillery: ",artillerySilhouette,modifier = modifier)
                NumberOfUnitsPurchased(numUnits = purchasedTanks, "Tank(s): ",tankSilhouette,modifier = modifier)

                NumberOfUnitsPurchased(numUnits = purchasedAAGuns, text = "AAgGun(s): ",AAGunSilhouette,modifier = modifier)
                NumberOfUnitsPurchased(
                    numUnits = purchasedIndustrialComplexes,
                    text = "Industrial Complex(s): ",
                    pictureResource = industrialComplexSilhouette,
                    modifier = Modifier
                )
                NumberOfUnitsPurchased(numUnits = purchasedFighters, text = "Fighter(s): ",fighterSilhouette, modifier = modifier)
                NumberOfUnitsPurchased(numUnits = purchasedBombers, text = "Bomber(s):",bomberSilhouette,modifier = modifier)
                NumberOfUnitsPurchased(numUnits = purchasedTransports, text = "Transport(s): ",transportSilhouette,modifier = modifier)
                NumberOfUnitsPurchased(numUnits = purchasedSubmarines, text = "Submarine(s): ",submarineSilhouette,modifier = modifier)
                NumberOfUnitsPurchased(numUnits = purchasedDestroyers, text = "Destroyer(s): ",destroyerSilhouette,modifier = modifier)
                NumberOfUnitsPurchased(numUnits = purchasedCruisers, text = "Cruiser(s): ",cruiserSilhouette,modifier = modifier)
                NumberOfUnitsPurchased(
                    numUnits = purchasedAircraftCarriers,
                    text = "Aircraftcarrier(s): ",
                    aircraftCarrierSilhouette,
                    modifier = Modifier
                )

            NumberOfUnitsPurchased(numUnits = purchasedBattleships, text = "Battleship(s): ",battleshipSilhouette,modifier = modifier)
            NumberOfUnitsPurchased(numUnits = industrialComplexRepair, text = "Repair(s):",R.drawable.industrial_complex_repair,modifier = Modifier)
            }
            Column(){
                ActionButton(text = stringResource(R.string.adjustIPCs), onClick = {
                    backToMainMenu(playerIPCs,true)
                    globalShowPurchasedUnits = showPurchasedUnits
                    globalPurchasedArtillery = purchasedArtillery
                    globalPurchasedInfantry = purchasedInfantry
                    globalPurchasedTanks = purchasedTanks
                    globalPurchasedAAGuns = purchasedAAGuns
                    globalPurchasedIndustrialComplexes = purchasedIndustrialComplexes
                    globalPurchasedFighters =purchasedFighters
                    globalPurchasedBombers = purchasedBombers
                    globalPurchasedTransports = purchasedTransports
                    globalPurchasedSubmarines = purchasedSubmarines
                    globalPurchasedDestroyers = purchasedDestroyers
                    globalPurchasedCruisers = purchasedCruisers
                    globalPurchasedAircraftCarriers = purchasedAircraftCarriers
                    globalPurchasedBattleships = purchasedBattleships
                    globalIndustrialComplexRepair = industrialComplexRepair
                    },
                    modifier = modifier)

                ActionButton(text = stringResource(R.string.collectIncome), onClick = {
                    backToMainMenu(playerIPCs,false)
                    globalShowPurchasedUnits = false
                    globalPurchasedArtillery =  "0"
                    globalPurchasedInfantry =  "0"
                    globalPurchasedTanks =  "0"
                    globalPurchasedAAGuns =  "0"
                    globalPurchasedIndustrialComplexes =  "0"
                    globalPurchasedFighters = "0"
                    globalPurchasedBombers =  "0"
                    globalPurchasedTransports =  "0"
                    globalPurchasedSubmarines =  "0"
                    globalPurchasedDestroyers =  "0"
                    globalPurchasedCruisers =  "0"
                    globalPurchasedAircraftCarriers =  "0"
                    globalPurchasedBattleships =  "0"
                    globalIndustrialComplexRepair = "0"
                },
                    modifier = modifier)
                Spacer(modifier = modifier.height(16.dp))


                    }
                }
            }
        }

        if (triggerNotEnoughIPCs) {
            NotEnoughIPCs()
            triggerNotEnoughIPCs = false
        }

}






@Preview(showBackground = true)
@Composable
fun UnitMarketPreview(){
    UnitMarket(0,"",backToMainMenu = {_, _ -> }, modifier = Modifier.background(color = lightMilitaryTan))
}
