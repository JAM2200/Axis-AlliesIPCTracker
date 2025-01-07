package com.example.ipc_tracker

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.FontRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.FlowColumnScopeInstance.weight
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ipc_tracker.ui.theme.IPC_TrackerTheme
import com.example.ipc_tracker.ui.theme.lightMilitaryGreen
import com.example.ipc_tracker.ui.theme.militaryTan

/* todo
    ♣ Manage all player's income or more than one.
    ♣ Replace text with images in the unit input fields
    ♣ Figure out color themes
        - Brown for the Soviet Union
        - Gray for Germany
        - Tan for the United Kingdom
        - Yellow for Japan
        - Green for the United States
    ♣ Save  state just in the case the app is closed prematurely.
 */

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IPC_TrackerTheme (isSystemInDarkTheme(),dynamicColor = false){
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TrackerApp()
                }
            }
        }
    }
}

@Composable
fun TrackerApp( modifier: Modifier = Modifier) {
    val spacing = 16.dp
    var playerIPCs by remember{mutableStateOf("")}
    var playerCountry by remember{mutableStateOf(R.string.noCountry)}
    var playerIncomeVal by remember{mutableStateOf("")}
    var playerIncome by remember{mutableStateOf("0")}
    var pickCountry by remember{mutableStateOf(true)}
    var incomeLabel by remember{mutableStateOf(0)}
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



    if(pickCountry){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier.background(militaryTan)) {
            Text(text = stringResource(R.string.prompt),fontWeight = FontWeight.Bold,fontSize = 20.sp, modifier = Modifier)

            Spacer(modifier = Modifier.height(spacing))

            Button(onClick = {
                playerCountry = R.string.soviet
            },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(spacing)) {
                Text(text = stringResource(R.string.soviet))
            }
            Spacer(modifier = Modifier.height(spacing))

            Button(onClick = {
                playerCountry = R.string.germany
            },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(spacing)) {
                Text(text = stringResource(R.string.germany))
            }
            Spacer(modifier = Modifier.height(spacing))

            Button(onClick = {
                playerCountry = R.string.england
            },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(spacing)) {
                Text(text = stringResource(R.string.england))
            }

            Spacer(modifier = Modifier.height(spacing))

            Button(onClick = {
                playerCountry = R.string.japan
            },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(spacing)) {
                Text(text = stringResource(R.string.japan))
            }

            Spacer(modifier = Modifier.height(spacing))

            Button(onClick = {
                playerCountry = R.string.america
            },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(spacing))
            {
                Text(text = stringResource(R.string.america))
            }
        }
        Log.d("[TrackerAPP]", "playerCountry = $playerCountry")

        when (stringResource(playerCountry)) {
            stringResource(R.string.soviet) -> {
                playerIPCs = "24"
                incomeLabel = R.string.sovietIPCsIncome
                playerIncome = playerIPCs
                pickCountry = false
            }

            stringResource(R.string.germany) -> {
                playerIPCs = "41"
                incomeLabel = R.string.germanyIPCsIncome
                playerIncome = playerIPCs
                pickCountry = false
            }

            stringResource(R.string.england) -> {
                playerIPCs = "31"
                incomeLabel = R.string.englandIPCsIncome
                playerIncome = playerIPCs
                pickCountry = false
            }

            stringResource(R.string.japan) -> {
                playerIPCs = "30"
                incomeLabel = R.string.japanIPCsIncome
                playerIncome = playerIPCs
                pickCountry = false
            }

            stringResource(R.string.america) -> {
                playerIPCs = "42"
                incomeLabel = R.string.americanIPCsIncome
                playerIncome = playerIPCs
                pickCountry = false
            }

            else -> pickCountry = true
        }

    }else {

//        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.verticalScroll(state = rememberScrollState())) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = stringResource(playerCountry), fontSize = 50.sp)

            Spacer(modifier.height(spacing))
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(text = "Income: $playerIncome", fontSize = 25.sp)
                Spacer(modifier = Modifier.width(spacing))
                Text(text = "IPC's ${playerIPCs.toInt() - spentIPCs.toInt()}", fontSize = 25.sp)
//                spentIPCs = "0"
            }
            Spacer(modifier = Modifier.height(spacing))


            IPCInputField(
                value = playerIncomeVal,
                onValueChange = { playerIncomeVal = it },
                label = incomeLabel,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        playerIncome = (playerIncome.toInt() + playerIncomeVal.toInt()).toString()
                    },
                    modifier = Modifier
                        .width(screenDimensions.screenWidthDp.dp / 2)
                        .padding(spacing)
                ) {
                    Text(text = "Add To Income")
                }


                Button(
                    onClick = {
                        playerIncome = (playerIncome.toInt() - playerIncomeVal.toInt()).toString()
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
                            unitType = R.string.infantryPrice,
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
                            unitType = R.string.artilleryPrice,
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
                            unitType = R.string.tankPrice,
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
                            unitType = R.string.AAGunPrice,
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
                            unitType = R.string.industrialComplexPrice,
                            modifier = industryModifier,
                            setCost = { cost -> spentIPCs = cost }
                        )
                    }

                    Row() {
//                    Spacer(modifier = Modifier.width(spacing))
                        SelectNumOfUnit(unitPrice = 10,
                            numIPCs = playerIPCs,
                            spentIPCs = spentIPCs,
                            toast = {
                                triggerNotEnoughIPCs = true
                            },
                            updateUnits = { numUnits, -> purchasedFighters = numUnits },
                            unitType = R.string.AAGunPrice,
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
                            unitType = R.string.industrialComplexPrice,
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
                            unitType = R.string.transportPrice,
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
                            unitType = R.string.submarinePrice,
                            modifier = unitsInputsModifier,
                            setCost = { cost -> spentIPCs = cost }
                        )

                        SelectNumOfUnit(unitPrice = 8,
                            numIPCs = playerIPCs,
                            spentIPCs = spentIPCs,
                            toast = {
                                triggerNotEnoughIPCs = true
                            },
                            unitType = R.string.destroyerPrice,
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
                            unitType = R.string.cruiserPrice,
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
                            unitType = R.string.aircraftCarrierPrice,
                            modifier = unitsInputsModifier,
                            setCost = { cost -> spentIPCs = cost }
                        )

                        SelectNumOfUnit(unitPrice = 20,
                            numIPCs = playerIPCs,
                            spentIPCs = spentIPCs,
                            toast = {
                                triggerNotEnoughIPCs = true
                            },
                            unitType = R.string.battleshipPrice,
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


}





@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    IPC_TrackerTheme {
        TrackerApp()
    }
}
