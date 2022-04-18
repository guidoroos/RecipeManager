package com.guidoroos.recepten.di

enum class FilterType {NONE, CUISINE, CREATED, LAST_SEEN,RECIPE_TYPE }

data class Filter (val filterType: FilterType, val filterValue: Long)

