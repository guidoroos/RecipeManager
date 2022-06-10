package com.guidoroos.recepten.filter.model

data class FilterItem (val filterItemName:String, val filterItemValue:FilterValue)


interface FilterValue{
}

data class FilterValueString(val value:String):FilterValue {
}

data class FilterValueLong(val value:Long):FilterValue {
}