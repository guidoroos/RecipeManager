package com.guidoroos.recepten.filter.model

import com.guidoroos.recepten.R

enum class FilterTimeUnit(val nameResourceId:Int) {
    LAST_WEEK(R.string.last_week),
    LAST_MONTH(R.string.last_month),
    LAST_QUARTER(R.string.last_quarter),
    LAST_YEAR(R.string.last_year),
}