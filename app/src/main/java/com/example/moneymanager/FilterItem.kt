package com.example.moneymanager

class FilterItem (var timePeriod:String?, var type:String?, var amount:String?, var categories:Array<String>?) {

    fun getValuesArray():Array<String?>{
        return arrayOf(type, amount, timePeriod)
    }
}