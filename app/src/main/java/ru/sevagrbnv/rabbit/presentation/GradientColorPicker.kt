package ru.sevagrbnv.rabbit.presentation

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
        private val color2 = floatArrayOf(60f, 1f, 1f)
        private val color3 = floatArrayOf(120f, 1f, 1f)
        private val color4 = floatArrayOf(180f, 1f, 1f)
        private val color5 = floatArrayOf(240f, 1f, 1f)
        private val color6 = floatArrayOf(300f, 1f, 1f)
        private val endColor = floatArrayOf(360f, 1f, 1f)

        val gradient = GradientDrawable(
            GradientDrawable.Orientation.LEFT_RIGHT,
            intArrayOf(
                Color.HSVToColor(startColor),
                Color.HSVToColor(color2),
                Color.HSVToColor(color3),
                Color.HSVToColor(color4),
                Color.HSVToColor(color5),
                Color.HSVToColor(color6),
                Color.HSVToColor(endColor)))
    }
}