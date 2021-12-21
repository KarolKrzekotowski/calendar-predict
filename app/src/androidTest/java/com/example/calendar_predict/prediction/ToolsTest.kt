package com.example.calendar_predict.prediction

import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException

class ToolsTest {
    @get:Rule
    public var exceptionRule = ExpectedException.none()

    val tool = Tools()
    @Test
    fun dotGoodData() {
        val a = doubleArrayOf(1.0,0.0,3.0)
        val b = doubleArrayOf(1.0,0.0,3.0)
        val result = 10.0
        val ans = tool.dot(a,b)
        assertEquals(ans, result, 0.0001)
    }

    @Test
    fun dotGoodData1() {
        val a = doubleArrayOf(1.0,0.0,3.0, 12.0)
        val b = doubleArrayOf(1.0,0.0,3.0, -1.0)
        val result = -2.0
        val ans = tool.dot(a,b)
        assertEquals(ans, result, 0.0001)
    }

    @Test
    fun dotBadLong() {
        val a = doubleArrayOf(1.0,0.0,3.0, 12.0)
        val b = doubleArrayOf(1.0,0.0,3.0)
        exceptionRule.expect(IndexOutOfBoundsException::class.java)
        tool.dot(a,b)
    }

    @Test
    fun multiDimMeanGoodData() {
        val a = arrayOf(doubleArrayOf(0.0,2.0,4.0),doubleArrayOf(3.0,6.0,9.0))
        val result = arrayOf(1.5,4.0,6.5)
        val ans = tool.multidimMean(a)
        for (i in 0..2) {
            assertEquals(ans[i], result[i], 0.0001)
        }
    }

    @Test
    fun multiDimMeanBadData() {
        val a = arrayOf(doubleArrayOf(0.0,2.0,4.0,10.0),doubleArrayOf(3.0,6.0,9.0))
        val result = arrayOf(1.5,4.0,6.5)
        exceptionRule.expect(IndexOutOfBoundsException::class.java)
        val ans = tool.multidimMean(a)
    }

    @Test
    fun substractGoodData() {
        val a = doubleArrayOf(1.0,0.0,3.0)
        val b = doubleArrayOf(1.0,0.0,3.0)
        val result = doubleArrayOf(0.0,0.0,0.0)
        val ans = tool.subtract(a,b)
        assertArrayEquals(ans, result, 0.0001)
    }

    @Test
    fun substractGoodData1() {
        val a = doubleArrayOf(1.0,0.0,4.0)
        val b = doubleArrayOf(2.0,0.0,3.0)
        val result = doubleArrayOf(-1.0,0.0,1.0)
        val ans = tool.subtract(a,b)
        assertArrayEquals(ans, result, 0.0001)
    }

    @Test
    fun substractBadData() {
        val a = doubleArrayOf(1.0,0.0,4.0, 3.0)
        val b = doubleArrayOf(2.0,0.0,3.0)
        val result = doubleArrayOf(-1.0,0.0,1.0)
        exceptionRule.expect(IndexOutOfBoundsException::class.java)
        val ans = tool.subtract(a,b)
    }

    @Test
    fun multiplyScalarGoodData() {
        val a = doubleArrayOf(1.0,0.0,4.0)
        val b = 3.0
        val result = doubleArrayOf(3.0,0.0,12.0)
        val ans = tool.multiplyScalar(a,b)
        assertArrayEquals(ans, result, 0.0001)
    }

    @Test
    fun multiplyScalarGoodData2() {
        val a = doubleArrayOf(1.0,0.0,4.0)
        val b = 12.1
        val result = doubleArrayOf(12.1,0.0,48.4)
        val ans = tool.multiplyScalar(a,b)
        assertArrayEquals(ans, result, 0.0001)
    }

    @Test
    fun multiplyScalarBadData() {
        val a = doubleArrayOf(1.0, Double.NaN,4.0)
        val b = 12.1
        val result = doubleArrayOf(12.1,Double.NaN,48.4)
        val ans = tool.multiplyScalar(a,b)
        assertArrayEquals(ans, result, 0.0001)
    }

    @Test
    fun meanSquaredErrorDerivativeGoodData() {
        val a = 3.0
        val b = 0.0
        val result = 9.0
        val ans = tool.meanSquaredErrorDerivative(a,b)
        assertEquals(result, ans, 0.0001)
    }

    @Test
    fun meanSquaredErrorDerivativeGoodData1() {
        val a = -12.0
        val b = 0.0
        val result = 144.0
        val ans = tool.meanSquaredErrorDerivative(a,b)
        assertEquals(result, ans, 0.0001)
    }

}