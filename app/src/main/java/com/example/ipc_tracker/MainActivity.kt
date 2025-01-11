package com.example.ipc_tracker

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

private val spacing = 16.dp

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
//    var playerIPCs by remember{mutableStateOf("")}
//    var playerCountry by remember{mutableStateOf(R.string.noCountry)}
//    var playerIncomeVal by remember{mutableStateOf("")}
//    var playerIncome by remember{mutableStateOf("0")}
//    var pickCountry by remember{mutableStateOf(true)}
//    var incomeLabel by remember{mutableStateOf(0)}
//    // Number of purchased units.
//    // Land Units
//
//
//
//    if(pickCountry){
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Bottom,
//            modifier = Modifier.background(militaryTan)) {
//            Text(text = stringResource(R.string.prompt),fontWeight = FontWeight.Bold,fontSize = 20.sp, modifier = Modifier)
//
//            Spacer(modifier = Modifier.height(spacing))
//
//            PickPlayerButton(action = {
//                    playerCountry = R.string.soviet
//                },
//                powerInsignia = R.drawable.ussr_insignia
//            )
//
//            Spacer(modifier = Modifier.height(spacing))
//
//            PickPlayerButton(action = {
//                playerCountry = R.string.germany
//            },
//            powerInsignia = R.drawable.germany_insignia
//            )
//
//            Spacer(modifier = Modifier.height(spacing))
//            PickPlayerButton(action = {
//                playerCountry = R.string.england
//            },
//            powerInsignia = R.drawable.uk_insignia
//            )
//
//            Spacer(modifier = Modifier.height(spacing))
//            PickPlayerButton(action = {
//                playerCountry = R.string.japan
//            },
//            powerInsignia = R.drawable.japan_insignia
//            )
//
//
//            Spacer(modifier = Modifier.height(spacing))
//            PickPlayerButton(action = {
//                playerCountry = R.string.america
//            },
//                powerInsignia = R.drawable.usa_insignia
//            )
//            Spacer(modifier = Modifier.height(spacing))
//
//        }
//        Log.d("[TrackerAPP]", "playerCountry = $playerCountry")
//
//        when (stringResource(playerCountry)) {
//            stringResource(R.string.soviet) -> {
//                playerIPCs = "24"
//                incomeLabel = R.string.sovietIPCsIncome
//                playerIncome = playerIPCs
//                pickCountry = false
//            }
//
//            stringResource(R.string.germany) -> {
//                playerIPCs = "41"
//                incomeLabel = R.string.germanyIPCsIncome
//                playerIncome = playerIPCs
//                pickCountry = false
//            }
//
//            stringResource(R.string.england) -> {
//                playerIPCs = "31"
//                incomeLabel = R.string.englandIPCsIncome
//                playerIncome = playerIPCs
//                pickCountry = false
//            }
//
//            stringResource(R.string.japan) -> {
//                playerIPCs = "30"
//                incomeLabel = R.string.japanIPCsIncome
//                playerIncome = playerIPCs
//                pickCountry = false
//            }
//
//            stringResource(R.string.america) -> {
//                playerIPCs = "42"
//                incomeLabel = R.string.americanIPCsIncome
//                playerIncome = playerIPCs
//                pickCountry = false
//            }
//
//            else -> pickCountry = true
//        }
//
//    }else {
//
//        UnitMarket(playerIncome,playerIncomeVal,playerCountry,playerIPCs,incomeLabel)
//
//
//    }

    IPCAdjusterLayout()


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
        TrackerApp()
    }
}
