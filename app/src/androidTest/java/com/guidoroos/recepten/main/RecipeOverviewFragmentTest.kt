package com.guidoroos.recepten.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.android.architecture.blueprints.todoapp.launchFragmentInHiltContainer
import com.guidoroos.recepten.R
import com.guidoroos.recepten.main.ui.RecipeOverviewFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@SmallTest
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class RecipeOverviewFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    // single task rule
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun testLaunch() {
        launchFragmentInHiltContainer<RecipeOverviewFragment>()
            onView(withId(R.id.button_ingredients)).check(matches(isDisplayed()))




    }

}


