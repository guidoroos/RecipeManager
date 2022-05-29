package com.guidoroos.recepten.filter.model

import com.guidoroos.recepten.R

enum class FilterType( val iconResource:Int? = null,
                      val nameResource:Int? = null) {
    NONE,
    CUISINE(R.drawable.ic_location,R.string.filter_cuisine),
    CREATED(R.drawable.ic_event,R.string.filter_time_created),
    LAST_SEEN(R.drawable.ic_clock,R.string.filter_time_last_seen),
    RECIPE_TYPE(R.drawable.ic_category,R.string.filter_recipe_type) }

data class Filter (val filterType: FilterType, val filterValue: Long)

