package com.example.calendar_predict.prediction

import kotlin.math.pow

class Tools {

    fun dot ( a : DoubleArray , b : DoubleArray ) : Double {
        var dotProduct = 0.0
        for( i in 0 until a.size ) {
            dotProduct += ( a[ i ] * b[ i ] )
        }
        return dotProduct
    }

    fun multidimMean( x : Array<DoubleArray> ) : Array<Double> {
        val mean = ArrayList<Double>()
        for ( i in 0 until x[0].size ) {
            var sum = 0.0
            for ( array in x ) {
                sum += array[i]
            }
            mean.add( sum / x.size)
        }
        return mean.toTypedArray()
    }

    fun subtract ( a : DoubleArray , b : DoubleArray ) : DoubleArray {
        val difference = DoubleArray( a.size )
        for( i in 0 until a.size ) {
            difference[i] =  ( a[ i ] - b[ i ] )
        }
        return difference
    }

    fun multiplyScalar ( a : DoubleArray , k : Double ) : DoubleArray {
        val results = DoubleArray( a.size )
        for ( i in 0 until a.size ) {
            results[ i ] = a[ i ] * k
        }
        return results
    }

    fun calculateGradients( inputs : DoubleArray , predY : Double , targetY : Double ) : Array<Any> {
        val dJ_dPred = meanSquaredErrorDerivative( predY , targetY )
        val dPred_dW = inputs
        val dJ_dW = multiplyScalar( dPred_dW , dJ_dPred )
        val dJ_dB = dJ_dPred
        return arrayOf( dJ_dW , dJ_dB )
    }

    fun meanSquaredErrorDerivative( predY : Double , targetY : Double ) : Double {
        return  2 * (predY - targetY)
    }

    fun batch ( x : Array<DoubleArray> , y : DoubleArray , batchSize : Int ) : List<List<Pair<DoubleArray,Double>>> {
        val data = x.zip( y.toTypedArray() )
        return data.chunked( batchSize )
    }

    fun countTime(from_hour: java.util.Date, to_hour: java.util.Date): Int {
        val diff: Long =  to_hour.getTime() - from_hour.getTime()
        val seconds = diff / 1000
        val minutes = seconds / 60
        return minutes.toInt()
    }
}