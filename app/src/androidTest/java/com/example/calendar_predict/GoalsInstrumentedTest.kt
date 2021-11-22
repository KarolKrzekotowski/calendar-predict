package com.example.calendar_predict

import android.view.View
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class GoalsInstrumentedTest {
    @get:Rule
    val activityScenarioRule: ActivityScenarioRule<AddGoalActivity> = ActivityScenarioRule(AddGoalActivity::class.java, null)

    @Test
    fun selectedTimeAmount_isCorrect() {
        val scenario = activityScenarioRule.scenario

        scenario.onActivity { testObject ->
            val appContext = getInstrumentation().targetContext

            val testCount = 1024
            val baseUnit = 15

            Assert.assertTrue("Amount bazowo ustawione na 15", testObject.amount == baseUnit)

            for (i in 1..testCount) {
                testObject.amountDecrease(View(appContext))
            }

            Assert.assertTrue("Amount zostało zmienijszone poniżej 15", testObject.amount == baseUnit)

            for (i in 1..testCount) {
                testObject.amountIncrease(View(appContext))
                Assert.assertTrue("Amount nie zostało odpowiednia podwyższone", testObject.amount == baseUnit * (i + 1))
            }

            for (i in 1..testCount) {
                testObject.amountDecrease(View(appContext))
                Assert.assertTrue("Amount nie zostało odpowiednio zmienione", testObject.amount == baseUnit * (testCount))
                testObject.amountIncrease(View(appContext))
            }
        }
    }
}