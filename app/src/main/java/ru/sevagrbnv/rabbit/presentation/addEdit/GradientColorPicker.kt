package ru.sevagrbnv.rabbit.presentation.addEdit

import android.graphics.Color
import android.graphics.drawable.GradientDrawable

class GradientColorPicker(private val steps: Int) {

    fun getColorArray(): List<Int> {

        val colorArray = mutableListOf<Int>()

        for (i in 0 until steps) {
            val fractionStart = i / steps.toFloat()
            val fractionEnd = (i + 1) / steps.toFloat()
            val interpolatedColor = interpolateColor(fractionStart, fractionEnd)
            colorArray.add(Color.HSVToColor(interpolatedColor))
        }
        return colorArray
    }

    private fun interpolateColor(fractionStart: Float, fractionEnd: Float): FloatArray {
        val result = FloatArray(3)
        val midFraction = (fractionStart + fractionEnd) / 2

        for (i in 0 until 3) {
            result[i] = startColor[i] + midFraction * (endColor[i] - startColor[i])
        }
        return result
    }

    companion object {

        private val startColor = floatArrayOf(0f, 1f, 1f)
        private val endColor = floatArrayOf(360f, 1f, 1f)

        private fun getArrayList(): IntArray {
            val list = mutableListOf<Int>()
            repeat(7) {
                list.add(Color.HSVToColor(floatArrayOf((it * 60).toFloat(), 1f, 1f)))
            }
            return list.toIntArray()
        }

        val gradient = GradientDrawable(
            GradientDrawable.Orientation.LEFT_RIGHT,
            getArrayList()
        )
    }
}