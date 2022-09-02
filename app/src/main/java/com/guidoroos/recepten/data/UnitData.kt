package com.guidoroos.recepten.data


import androidx.annotation.StringRes
import com.guidoroos.recepten.R

enum class UnitType (@StringRes nameRes : Int) {
    Volume (R.string.volume),
    Weight (R.string.weight),
    Distance (R.string.distance),
    Temperature (R.string.temperature),
    Other(R.string.other)
}

data class UnitData(
    @StringRes
    val nameResource: Int,
    val abbreviationResource: Int? = null,
    val perStandardUnit: Float? = null,
    val unitType: UnitType,
    val isStandardUnit: Boolean,
)



fun getCelsiusFromFahrenheit (F:Float):Float {
    return (F-32f) * (5f/9f)
}

fun getFahrenheitFromCelsius (C:Float):Float {
    return C * (5f/9f) + 35
}

fun Int.minutesToSeconds() = this / 60

enum class UnitEnum {
    Celsius,
    Fahrenheit,
    CentiMeter,
    MilliMeter,
    Inch,
    Gram,
    KiloGram,
    MilliGram,
    Pound,
    Ounce,
    Liter,
    DeciLiter,
    CentiLiter,
    MilliLiter,
    TableSpoon,
    TeaSpoon,
    Cup,
    FluidOunce,
    Pinch,
    ToTaste
}

val unitData = mapOf(
    UnitEnum.ToTaste to UnitData(
        nameResource = R.string.to_taste,
        unitType = UnitType.Other,
        isStandardUnit = false
    ),
    UnitEnum.Celsius to UnitData(
        nameResource = R.string.celsius,
        unitType = UnitType.Temperature,
        isStandardUnit = true,
        abbreviationResource = R.string.abbr_celsius
    ),
    UnitEnum.Fahrenheit to UnitData(
        nameResource = R.string.fahrenheit,
        unitType = UnitType.Temperature,
        isStandardUnit = false,
        abbreviationResource = R.string.abbr_fahrenheit
    ),
    UnitEnum.CentiMeter to UnitData(
        nameResource = R.string.centimeter,
        unitType = UnitType.Distance,
        isStandardUnit = true,
        abbreviationResource = R.string.abbr_centimeter
    ),
    UnitEnum.MilliMeter to UnitData(
        nameResource = R.string.millimeter,
        unitType = UnitType.Distance,
        isStandardUnit = false,
        abbreviationResource = R.string.abbr_millimeter
    ),
    UnitEnum.Inch to UnitData(
        nameResource = R.string.inch,
        perStandardUnit = 2.54f,
        unitType = UnitType.Distance,
        isStandardUnit = false
    ),
    UnitEnum.Gram to UnitData(
        nameResource = R.string.gram,
        unitType = UnitType.Weight,
        isStandardUnit = true,
        abbreviationResource = R.string.abbr_gram
    ),
    UnitEnum.KiloGram to UnitData(
        nameResource = R.string.kilogram,
        perStandardUnit = 1000f,
        unitType = UnitType.Weight,
        isStandardUnit = false,
        abbreviationResource = R.string.abbr_kilogram
    ),
    UnitEnum.MilliGram to UnitData(
        nameResource = R.string.milligram,
        perStandardUnit = 0.001f,
        unitType = UnitType.Weight,
        isStandardUnit = false,
        abbreviationResource = R.string.abbr_milligram
    ),
    UnitEnum.Pound to UnitData(
        nameResource = R.string.pound,
        perStandardUnit = 450f,
        unitType = UnitType.Weight,
        isStandardUnit = false
    ),
    UnitEnum.Ounce to UnitData(
        nameResource = R.string.ounce,
        perStandardUnit = 28f,
        unitType = UnitType.Weight,
        isStandardUnit = false,
        abbreviationResource = R.string.abbr_ounce
    ),
    UnitEnum.Liter to UnitData(
        nameResource = R.string.liter,
        perStandardUnit = 1000f,
        unitType = UnitType.Volume,
        isStandardUnit = false,
        abbreviationResource = R.string.abbr_liter
    ),
    UnitEnum.DeciLiter to UnitData(
        nameResource = R.string.deciLiter,
        perStandardUnit = 100f,
        unitType = UnitType.Volume,
        isStandardUnit = false,
        abbreviationResource = R.string.abbr_deciLiter
    ),
    UnitEnum.CentiLiter to UnitData(
        nameResource = R.string.centiLiter,
        perStandardUnit = 10f,
        unitType = UnitType.Volume,
        isStandardUnit = false,
        abbreviationResource = R.string.abbr_centiLiter
    ),
    UnitEnum.MilliLiter to UnitData(
        nameResource = R.string.milliLiter,
        unitType = UnitType.Volume,
        isStandardUnit = true,
        abbreviationResource = R.string.abbr_milliLiter
    ),
    UnitEnum.TeaSpoon to UnitData(
        nameResource = R.string.tea_spoon,
        perStandardUnit = 5f,
        unitType = UnitType.Volume,
        isStandardUnit = false,
        abbreviationResource = R.string.abbr_tea_spoon
    ),
    UnitEnum.TableSpoon to UnitData(
        nameResource = R.string.table_spoon,
        perStandardUnit = 15f,
        unitType = UnitType.Volume,
        isStandardUnit = false,
        abbreviationResource = R.string.abbr_table_spoon
    ),
    UnitEnum.Pinch to UnitData(
        nameResource = R.string.pinch,
        perStandardUnit = 0.35f,
        unitType = UnitType.Volume,
        isStandardUnit = false
    ),
    UnitEnum.FluidOunce to UnitData(
        nameResource = R.string.fluid_ounce,
        perStandardUnit = 30f,
        unitType = UnitType.Volume,
        isStandardUnit = false,
        abbreviationResource = R.string.abbr_fluid_ounce
    ),
    UnitEnum.Cup to UnitData(
        nameResource = R.string.cup,
        perStandardUnit = 235f,
        unitType = UnitType.Volume,
        isStandardUnit = false
    )

)