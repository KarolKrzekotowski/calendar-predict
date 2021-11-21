package com.example.calendar_predict

import android.content.Context
import android.view.View
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert
import org.junit.Test

class GoalsTest {
    @Test
    fun remainingTime_isCorrect() {
        val testObject = GoalAdapter()
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