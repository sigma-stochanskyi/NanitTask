package com.stochanskyi.nanittask.presentation.ui.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.use
import com.stochanskyi.nanittask.androidcore.data.utils.dp
import com.stochanskyi.nanittask.presentation.R
import com.stochanskyi.nanittask.presentation.utils.isFixedSize
import com.stochanskyi.nanittask.presentation.utils.isSpecFixed
import com.stochanskyi.nanittask.presentation.utils.paddingHorizontalSum
import com.stochanskyi.nanittask.presentation.utils.paddingVerticalSum
import kotlin.math.absoluteValue

class AgeView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : View(context, attrs) {

    companion object {
        private const val LETTERS_SPACING_DP = 5
    }

    private val numbersDefinition = AgeNumbersDefinition()

    private var digitDrawables: List<Drawable> = emptyList()

    private var space: Float = 0f

    init {
        initAttrs(attrs)
        setAge(12)
    }

    fun setAge(age: Int) {
        digitDrawables = resolveAgeNumbersDrawables(age)
        requestLayout()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val totalIntrinsicWidth = digitDrawables.sumOf { it.intrinsicWidth }
        val totalIntrinsicHeight = digitDrawables.maxOfOrNull { it.intrinsicHeight } ?: 0

        val totalSpacing = (digitDrawables.size - 1)
            .takeIf { it >= 0 }
            ?.run { space * this }
            ?: 0f

        val desiredWidth = totalIntrinsicWidth + paddingHorizontalSum() + totalSpacing.toInt()
        val desiredHeight = totalIntrinsicHeight + paddingVerticalSum()

        val aspectRatio = desiredWidth / desiredHeight.toFloat()

        var resolvedWidth = resolveSize(desiredWidth, widthMeasureSpec)
        var resolvedHeight = resolveSize(desiredHeight, heightMeasureSpec)

        when {
            isFixedSize(widthMeasureSpec, heightMeasureSpec) -> {}
            isSpecFixed(widthMeasureSpec) -> {
                val newDesiredHeight = (resolvedHeight / aspectRatio).toInt()
                resolvedHeight = resolveSize(newDesiredHeight, heightMeasureSpec)
            }
            isSpecFixed(heightMeasureSpec) -> {
                val newDesiredWidth = (resolvedHeight * aspectRatio).toInt()
                resolvedWidth = resolveSize(newDesiredWidth, widthMeasureSpec)
            }
        }

        setMeasuredDimension(resolvedWidth, resolvedHeight)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (!changed) return

        val scaleValue = (height - paddingTop - paddingBottom).toFloat() /
            digitDrawables.maxOf { it.intrinsicHeight }

        var previousLeft = paddingStart

        digitDrawables.forEach {
            setDrawableBound(it, scaleValue, previousLeft)
            previousLeft += it.bounds.right + space.toInt()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas ?: return

        digitDrawables.forEach {
            it.draw(canvas)
        }
    }

    private fun initAttrs(
        attrs: AttributeSet? = null
    ) {
        context.obtainStyledAttributes(
            attrs,
            R.styleable.AgeView,
            0,
            0
        ).use {
            space = it.getDimension(R.styleable.AgeView_spacing, context.dp(LETTERS_SPACING_DP))
        }
    }

    private fun setDrawableBound(drawable: Drawable, scale: Float, left: Int) {
        val scaledWidth = drawable.intrinsicWidth * scale
        val scaledHeight = drawable.intrinsicHeight * scale

        val bottom = height - paddingBottom
        val top = bottom - scaledHeight.toInt()
        val right = (left + scaledWidth).toInt()

        drawable.setBounds(left, top, right, bottom)
    }

    private fun resolveAgeNumbersDrawables(age: Int): List<Drawable> {
        return splitIntoDigits(age)
            .mapNotNull { resolveDigitDrawable(it) }
    }

    private fun resolveDigitDrawable(digit: Int): Drawable? {
        val digitRes = numbersDefinition.getAgeNumberDrawableRes(digit)
        return context.getDrawable(digitRes)
    }

    private fun splitIntoDigits(age: Int): List<Int> {
        val numbers = mutableListOf<Int>()

        var remainingValue = age.absoluteValue

        while (remainingValue >= 10) {
            numbers += remainingValue % 10
            remainingValue /= 10
        }

        numbers += remainingValue

        return numbers.asReversed()
    }
}