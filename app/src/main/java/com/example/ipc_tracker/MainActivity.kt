package com.example.ipc_tracker

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.FontRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
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
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.example.ipc_tracker.ui.theme.IPC_TrackerTheme
import com.example.ipc_tracker.ui.theme.lightMilitaryGreen
import com.example.ipc_tracker.ui.theme.lightMilitaryTan
import com.example.ipc_tracker.ui.theme.militaryBrown
import com.example.ipc_tracker.ui.theme.militaryGreen
import com.example.ipc_tracker.ui.theme.militaryTan
import java.io.File
import java.io.IOException

/* todo
    ♣ Manage all player's income or more than one. ✅
    ♣ Replace text with images in the unit input fields ✅
    ♣ Figure out color themes ✅
        - Brown for the Soviet Union
        - Gray for Germany
        - Tan for the United Kingdom
        - Yellow for Japan
        - Green for the United States
    ♣ Save  state just in the case the app is closed prematurely.


 */

private val spacing = 16.dp

var globalUSSRIPCs = "24"
var globalGermanyIPCs = "41"
var globalUKIPCs = "31"
var globalJapanIPCs = "30"
var globalUSAIPCs = "42"
var globalUSSRIncome = "24"
var globalGermanyIncome = "41"
var globalUKIncome = "31"
var globalJapanIncome = "30"
var globalUSAIncome = "42"
var globalAdjustIncome = true

class MainActivity : ComponentActivity() {
    val gameFile = "saved_game.txt"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loadFile(gameFile)
        deleteFile(gameFile)

        saveFile(gameFile,"Hello, World",newGame = true)
        loadFile(gameFile)
        setContent {
            IPC_TrackerTheme (isSystemInDarkTheme(),dynamicColor = false){
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TrackerApp(modifier = Modifier.background(color = lightMilitaryGreen))
                }
            }
        }
    }

    override fun onStop(){
        super.onStop()
        saveFile(gameFile,"On Stop",false)
    }

    override fun onDestroy(){
        super.onDestroy()

        saveFile(gameFile,"On Destroy",false)
    }





    private val saveDirectory = "SavedGames"

    private fun loadFile(fileName:String){
        try {
            var stream = File(applicationContext.filesDir,saveDirectory+"/$fileName")
            if(!stream.exists()){
                Log.d("[loadFile]","File not found")
            }else {
                stream.forEachLine { Log.d("reading from file $fileName\n", it) }
            }
        }catch(e: IOException){
            Log.d("[loadFile]","No file $e")
        }

//        var delFile = File(applicationContext.filesDir,"$fileName").delete()
    }
    private fun saveFile(fileName: String,textToWrite:String,newGame:Boolean) {
        //Check if file is has .txt in it add it if not
        try {
            val dir = File(applicationContext.filesDir, saveDirectory)
            if(!dir.isDirectory){
                dir.mkdir()
                Log.d("[saveFile]","Creating directory $saveDirectory/$fileName")
            }
            val file = File(applicationContext.filesDir, saveDirectory+"/$fileName")
            if (!file.exists()) {
                file.createNewFile()
            }


            file.appendText("Soviet IPC's: $globalUSSRIPCs Income: globalUSSRIncome\n")
            file.appendText("Germany IPC's: $globalGermanyIPCs Income: $globalGermanyIncome \n")
            file.appendText("England IPC's: $globalUKIPCs Income: $globalUKIncome\n")
            file.appendText("Japan IPC's: $globalJapanIPCs Income: $globalJapanIncome\n")
            file.appendText("America IPC's: $globalUSAIPCs Income: $globalUSAIncome\n")
            file.appendText("Adjusting Income: true")

        } catch (e: IOException) {
            Log.d("[saveTextFileToStorage]", "Could not open file $fileName: $e")
        }
    }

    private fun deleteSavedGame(fileName:String){
        val file = File(applicationContext.filesDir,saveDirectory+"$fileName")
        if(file.exists()){
            Log.d("[deleteSavedGames]","Deleting file $saveDirectory/$fileName")
            file.delete()
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackerApp( modifier: Modifier = Modifier) {
    var ussrIPCs by remember { mutableStateOf(globalUSSRIPCs) }
    var germanyIPCs by remember { mutableStateOf(globalGermanyIPCs) }
    var ukIPCs by remember { mutableStateOf(globalUKIPCs) }
    var japanIPCs by remember { mutableStateOf(globalJapanIPCs) }
    var usaIPCs by remember { mutableStateOf(globalUSAIPCs) }
    var ussrIncome by remember { mutableStateOf(globalUSSRIncome) }
    var germanyIncome by remember { mutableStateOf(globalGermanyIncome) }
    var ukIncome by remember { mutableStateOf(globalUKIncome) }
    var japanIncome by remember { mutableStateOf(globalJapanIncome) }
    var usaIncome by remember { mutableStateOf(globalUSAIncome) }
    var playerIncome by remember { mutableStateOf("0") }
    var adjustIncome by remember { mutableStateOf(globalAdjustIncome) }
    var playerCountry by remember { mutableIntStateOf(R.string.noCountry) }
    var playerIPCs by remember { mutableStateOf("0") }


        Column(modifier = Modifier.fillMaxSize().background(color = lightMilitaryGreen),horizontalAlignment = Alignment.Start){
            Row(horizontalArrangement = Arrangement.Center,modifier = Modifier.fillMaxWidth()) {
                Text(
                    "Axis & Allies", color = Color.LightGray,
                    fontWeight = FontWeight.Bold,
                    fontSize = 60.sp,
                    modifier = Modifier.background(color = militaryGreen).fillMaxWidth().padding(spacing),
                )
            }
        if (adjustIncome) {
            var storeCountry by remember{mutableStateOf(playerCountry)}

            IPCAdjusterLayout(
                playerTurn = playerCountry,
                purchaseUnits = { country ->
                    playerCountry = country
                    adjustIncome = false

                    playerIPCs = when (playerCountry) {
                        R.string.soviet -> ussrIPCs
                        R.string.germany -> germanyIPCs
                        R.string.england -> ukIPCs
                        R.string.japan -> japanIPCs
                        R.string.america -> usaIPCs
                        else -> {
                            "0"
                        }
                    }
                },
                IPCs = { country, income ->

                    storeCountry = playerCountry
                    playerCountry = country
                    playerIncome = income

                    when (playerCountry) {
                        R.string.soviet -> {
                            playerIPCs = ussrIPCs
                            ussrIncome = income
                        }
                        R.string.germany -> {
                            playerIPCs = germanyIPCs
                            germanyIncome = income
                        }
                        R.string.england -> {
                            playerIPCs = ukIPCs
                            ukIncome = income
                        }
                        R.string.japan -> {
                            playerIPCs = japanIPCs
                            japanIncome = income
                        }
                        R.string.america -> {
                            playerIPCs = usaIPCs
                            usaIncome = income
                        }
                    }
                    if(globalShowPurchasedUnits){
                        playerCountry = storeCountry


                    }
                    globalUSSRIPCs = ussrIPCs
                    globalGermanyIPCs = germanyIPCs
                    globalUKIPCs = ukIPCs
                    globalJapanIPCs = japanIPCs
                    globalUSAIPCs = usaIPCs
                    globalUSSRIncome = ussrIncome
                    globalGermanyIncome = germanyIncome
                    globalUKIncome = ukIncome
                    globalJapanIncome = japanIncome
                    globalUSAIncome = usaIncome
                    globalAdjustIncome = adjustIncome


                },
                ussrIncome = ussrIncome,
                germanyIncome = germanyIncome,
                ukIncome = ukIncome,
                japanIncome = japanIncome,
                usaIncome = usaIncome,
                ussrIPCs = ussrIPCs,
                germanyIPCs = germanyIPCs,
                ukIPCs = ukIPCs,
                japanIPCs = japanIPCs,
                usaIPCs = usaIPCs,
                modifier = modifier
            )
        } else {

            UnitMarket(
                playerCountry = playerCountry, IPCs = playerIPCs,
                backToMainMenu = { remainingIPCs, showUnits  ->
                    adjustIncome = true
                    if(!showUnits) {
                        when (playerCountry) {
                            R.string.soviet -> ussrIPCs =
                                (ussrIncome.toInt() + remainingIPCs.toInt()).toString()

                            R.string.germany -> germanyIPCs =
                                (germanyIncome.toInt() + remainingIPCs.toInt()).toString()

                            R.string.england -> ukIPCs =
                                (ukIncome.toInt() + remainingIPCs.toInt()).toString()

                            R.string.japan -> japanIPCs =
                                (japanIncome.toInt() + remainingIPCs.toInt()).toString()

                            R.string.america -> usaIPCs =
                                (usaIncome.toInt() + remainingIPCs.toInt()).toString()
                        }
                    }else{
                        when (playerCountry) {
                            R.string.soviet -> ussrIPCs = remainingIPCs

                            R.string.germany -> germanyIPCs = remainingIPCs

                            R.string.england -> ukIPCs = remainingIPCs

                            R.string.japan -> japanIPCs = remainingIPCs

                            R.string.america -> usaIPCs = remainingIPCs

                        }
                    }
                },
                modifier = modifier
            )
        }
    }

}


@Composable
fun PickPlayerButton(action: () -> Unit, powerInsignia:Int){
    Button(onClick = action,
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing)) {
        Image(painterResource(id = powerInsignia),contentDescription = null,
        modifier = Modifier
            .width(40.dp)
            .height(40.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    IPC_TrackerTheme {
        TrackerApp(modifier = Modifier.background(lightMilitaryGreen))
    }
}
