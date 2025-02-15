package com.example.ipc_tracker

import android.content.res.Resources
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ipc_tracker.ui.theme.IPC_TrackerTheme
import com.example.ipc_tracker.ui.theme.lightMilitaryGreen
import com.example.ipc_tracker.ui.theme.militaryGreen
import com.example.ipc_tracker.ui.theme.militaryTan


private val spacing = 16.dp
@Composable
fun IPCAdjusterLayout(purchaseUnits: (Int) -> Unit,
                      playerTurn:Int,
                      IPCs:(Int,String) -> Unit,
                      ussrIncome:String,
                      germanyIncome:String,
                      ukIncome:String,
                      japanIncome:String,
                      usaIncome:String,
                      ussrIPCs:String,
                      germanyIPCs:String,
                      ukIPCs:String,
                      japanIPCs:String,
                      usaIPCs:String,
                      modifier:Modifier = Modifier){

    var USSRsIncome by remember{mutableStateOf(ussrIncome)}
    var germanysIncome by remember{mutableStateOf(germanyIncome)}
    var UKsIncome by remember{mutableStateOf(ukIncome)}
    var japansIncome by remember{mutableStateOf(japanIncome)}
    var USAsIncome by remember{mutableStateOf(usaIncome)}
    var unitsButtonText by remember{mutableStateOf("Purchase Units")}

    if(globalShowPurchasedUnits){
        unitsButtonText = "View Purchased Units"
    }

    Column(horizontalAlignment = Alignment.Start,
        modifier = modifier){


        // USSR income management.
        Row(verticalAlignment = Alignment.CenterVertically) {


            AdjustIncome(addIncome = {num ->
                    USSRsIncome = (USSRsIncome.toInt() + num).toString()
                    IPCs(R.string.soviet,USSRsIncome)
                },
                powerInsignia = R.drawable.ussr_insignia,
                playerIncome = USSRsIncome,
                playerIPCs = ussrIPCs
            )
            if((globalShowPurchasedUnits && playerTurn == R.string.soviet) || !globalShowPurchasedUnits) {
                Button(onClick = {
                    purchaseUnits(R.string.soviet)
                }, modifier = Modifier.padding(spacing)) {

                    Text(text = unitsButtonText,color = Color.LightGray)
                }
            }
        }
        // Germany income management.
        Row() {
            AdjustIncome(addIncome = {num ->
                    germanysIncome = (germanysIncome.toInt() + num).toString()
                    IPCs(R.string.germany,germanysIncome)
                },
                powerInsignia = R.drawable.germany_insignia,
                playerIncome = germanysIncome,
                playerIPCs = germanyIPCs

            )
            if((globalShowPurchasedUnits && playerTurn == R.string.germany) || !globalShowPurchasedUnits) {

                Button(onClick = {
                    purchaseUnits(R.string.germany)
                }, modifier = Modifier.padding(spacing)) {
                    Text(text = unitsButtonText,color = Color.LightGray)
                }
            }
        }
        // Manage the United Kingdom's income
        Row() {

            AdjustIncome(addIncome = {num ->
                    UKsIncome = (UKsIncome.toInt() + num).toString()
                    IPCs(R.string.england,UKsIncome)
                },
                powerInsignia = R.drawable.uk_insignia,
                playerIncome = UKsIncome,
                playerIPCs = ukIPCs
            )
            if((globalShowPurchasedUnits && playerTurn == R.string.england) || !globalShowPurchasedUnits) {

            Button(onClick = {
                purchaseUnits(R.string.england)
            },modifier = Modifier.padding(spacing)) {
                Text(text = unitsButtonText,color = Color.LightGray)
            }
                }
        }
        // Manage Japan's income.
        Row(){

        AdjustIncome(addIncome = {num ->
            japansIncome = (japansIncome.toInt() + num).toString()
            IPCs(R.string.japan,japansIncome)
        },

        powerInsignia = R.drawable.japan_insignia,
        playerIncome = japansIncome,
            playerIPCs = japanIPCs)
            if((globalShowPurchasedUnits && playerTurn == R.string.japan) || !globalShowPurchasedUnits) {

                Button(onClick = {
                    purchaseUnits(R.string.japan)
                }, modifier = Modifier.padding(spacing)) {
                    Text(text = unitsButtonText)
                }
            }
        }


        // Manage the United States income.
        Row(modifier = modifier) {
            AdjustIncome(addIncome = {num ->
                    USAsIncome = (USAsIncome.toInt() + num).toString()
                    IPCs(R.string.america,USAsIncome)
                },
                powerInsignia = R.drawable.usa_insignia,
                playerIncome = USAsIncome,
                playerIPCs = usaIPCs
            )
            if((globalShowPurchasedUnits && playerTurn == R.string.america) || !globalShowPurchasedUnits) {
                Button(onClick = {
                    purchaseUnits(R.string.america)
                }, modifier = Modifier.padding(spacing)) {
                    Text(text = unitsButtonText,color = Color.LightGray)
                }
            }
        }




    }
}

@Composable
fun AdjustIncome(addIncome: (Int) -> Unit,powerInsignia:Int,playerIncome: String, playerIPCs:String, modifier: Modifier = Modifier){
    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
//        modifier = modifier.padding(spacing)) {
        modifier = modifier) {

        Text(text = "Income: $playerIncome, IPCs: $playerIPCs",color = militaryGreen,modifier = Modifier.padding(spacing))
        Row(verticalAlignment = Alignment.CenterVertically) {

            Image(
                painter = painterResource(powerInsignia),
                contentDescription = null,
                modifier = modifier
                    .width(100.dp)
                    .height(100.dp)
                    .padding(spacing)
            )

            AddSubtract(onAdd = addIncome,modifier = modifier)

        }
    }
}


@Composable
fun AddSubtract(onAdd:(Int) -> Unit,modifier: Modifier = Modifier){
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.background(color = lightMilitaryGreen)) {


        Button(onClick = {onAdd(+1)},
            colors = ButtonColors(
                containerColor = militaryGreen,
                contentColor = Color.White,
                disabledContainerColor = Color.Black,
                disabledContentColor = Color.White
            )

        ){
            Text(text = "+")
        }

        Spacer(modifier = Modifier.padding(8.dp))

        Button(onClick = {onAdd(-1)},
                colors = ButtonColors(
                containerColor = militaryTan,
                contentColor = Color.White,
                disabledContainerColor = Color.Black,
                disabledContentColor = Color.White
            )
        ) {
            Text(text = "-")
        }

    }

}




@Preview(showBackground = true)
@Composable
fun IPCAdjusterLayoutPreview(){
    IPC_TrackerTheme(dynamicColor = false) {


        IPCAdjusterLayout(purchaseUnits = { _ -> }, IPCs = { _, _ -> },
            playerTurn = R.string.soviet,
            ussrIncome = "24",
            germanyIncome = "41",
            ukIncome = "31",
            japanIncome = "30",
            usaIncome = "30",
            ussrIPCs = "24",
            germanyIPCs = "41",
            ukIPCs = "31",
            japanIPCs = "30",
            usaIPCs = "30",
            modifier = Modifier.background(color = lightMilitaryGreen),
        )
    }


}