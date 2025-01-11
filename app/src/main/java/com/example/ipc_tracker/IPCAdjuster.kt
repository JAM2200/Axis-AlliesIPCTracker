package com.example.ipc_tracker

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


private val spacing = 16.dp
@Composable
fun IPCAdjusterLayout(modifier:Modifier = Modifier){
    Column(horizontalAlignment = Alignment.CenterHorizontally){
        Text("Hello, World!!!")



    }
}

@Composable
fun AdjustIncome(onClick: () -> Unit,powerInsignia:Int,modifier: Modifier = Modifier){
    Column() {

        Image(
            painter = painterResource(powerInsignia),
            contentDescription = null,
            modifier = Modifier
                .width(40.dp)
                .height(40.dp)
                .padding(spacing)
        )
        Row() {


            Button(
                onClick = onClick
            ) {
                Text(text = "+")
            }

            Button(
                onClick = onClick
            ) {
                Text(text = "+")
            }
        }
    }
}

@Preview
@Composable
fun IPCAdjusterLayoutPreview(){
    IPCAdjusterLayout()
}