package com.example.calendar_predict

import android.app.Application
import android.view.View
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
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
    fun remainingTime_isCorrect() {
        activityScenarioRule.scenario.onActivity {
            val testObject = GoalAdapter(it.application)
        Assert.assertTrue("Liczba sekund do końca dnia mniejsza niż zero",
            testObject.getSecsTillDayEnd() >= 0)
        Assert.assertTrue("Liczba sekund do końca dnia większa niż 1 dzień",
            testObject.getSecsTillDayEnd() < 24 * 60 * 60)

        Assert.assertTrue("Liczba dni do końca tygodnia mniejsza niż zero",
            testObject.getDaysTillWeekEnd() >= 0)
        Assert.assertTrue("Liczba dni do końca tygodnia większa niż 1 dzień",
            testObject.getDaysTillWeekEnd() < 7)

        Assert.assertTrue("Liczba dni do końca miesiąca mniejsza niż zero",
            testObject.getDaysTillMonthEnd() >= 0)
        Assert.assertTrue("Liczba dni do końca miesięca większa niż 30",
            testObject.getDaysTillMonthEnd() < 30)
        }
    }

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