//package com.example.ipc_tracker
//
//import android.util.Log
//
//private fun calculateCost(unitsOrdered:String, previousNumUnits: String,numIPCs:String, unitPrice:Int,action: () -> Unit):String {
//    var cost = "0"
//    var currentNumOfUnits = if(unitsOrdered.isNullOrBlank()){
//        "0"
//    }else{
//        unitsOrdered
//    }
////    if(!previousNumUnits.isNullOrBlank()) {
//    cost = if (previousNumUnits != "0" && !previousNumUnits.isNullOrBlank()) {
//        Log.d("[calculateCost]","previousNumUnits = $previousNumUnits")
//        (currentNumOfUnits.toInt() * unitPrice - previousNumUnits.toInt() * unitPrice).toString()
//
//    } else {
//        Log.d("[calculateCost]","previousNumUnits = $previousNumUnits")
//
//        (currentNumOfUnits.toInt() * unitPrice).toString()
//    }
//    Log.d("[calculateCost","cost = $cost")
//
//    if (cost.toInt() > numIPCs.toInt()) {
//        Log.d("[cost]","numIPCs = $numIPCs")
//
//        cost = (numIPCs.toInt() / unitPrice * unitPrice).toString()
//        action()
//    }
////    }
//
//    return cost
//}
//
//fun getCost(unitsOrdered:String, previousNumOfOrderedUnits: String,numIPCs:String, unitPrice:Int,action: () -> Unit):String{
//    var cost = ((unitsOrdered.toInt() - previousNumOfOrderedUnits.toInt()) * unitPrice).toString()
//
//    if(cost.toInt() > numIPCs.toInt()){
//        action()
//        cost = ((numIPCs.toInt() / unitPrice) * unitPrice).toString()
//    }
//
//
////    var previousNumUnits = previousNumOfOrderedUnits
////    if(unitsOrdered.isNotBlank()) {
////        if(previousNumUnits == ""){
////            previousNumUnits = "0"
////        }
////
//        cost = calculateCost(unitsOrdered, previousNumUnits,(numIPCs.toInt()).toString(),unitPrice = unitPrice,action = action)
//////        previousNumUnits = (newlySpentIPCs.toInt() / unitPrice).toString()
//////        cost = (numIPCs.toInt() + newlySpentIPCs.toInt()).toString()
////    }else{
////        if(previousNumUnits == ""){
////            previousNumUnits = "0"
////        }
////        cost = calculateCost("0",previousNumUnits,numIPCs = numIPCs,unitPrice = unitPrice,action = action)
//////        previousNumUnits = ""
////    }
//
//    return cost
//}