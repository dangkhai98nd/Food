package com.test.food.util.custom

import android.content.Context
import android.content.res.Resources
import android.graphics.RectF
import android.util.AttributeSet
import android.util.TypedValue
import android.view.ViewTreeObserver
import android.widget.LinearLayout
import com.google.android.material.tabs.TabLayout
import com.test.food.R
import com.test.food.util.custom.CustomTabLayout.TabIndicatorRectF.FixedWidthModifier
import java.io.Serializable
import java.lang.reflect.Field


class CustomTabLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : TabLayout(context, attrs) {

    init {
        isTabIndicatorFullWidth = false
        TabIndicatorRectF(
            FixedWidthModifier(
                resources.getDimension(R.dimen.dp_4)
            )
        )
            .replaceBoundsRectF(this)
    }

    private class DefPreDrawListener : ViewTreeObserver.OnPreDrawListener {
        private var tabStrip: LinearLayout? = null
        private var tabWidth = 0
        private var fieldLeft: Field? = null
        private var fieldRight: Field? = null
        fun setTabStrip(tabStrip: LinearLayout, width: Int) {
            try {
                this.tabStrip = tabStrip
                tabWidth = width
                val cls: Class<*> = tabStrip.javaClass
                fieldLeft = cls.getDeclaredField("indicatorLeft")
                fieldLeft?.setAccessible(true)
                fieldRight = cls.getDeclaredField("indicatorRight")
                fieldRight?.setAccessible(true)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        override fun onPreDraw(): Boolean {
            try {
                if (tabWidth > 0) {
                    var left: Int = fieldLeft?.getInt(tabStrip) ?: 0
                    var right: Int = fieldRight?.getInt(tabStrip) ?: 0
                    //根据目标宽度及现在的宽度调整为合适的left和right
                    val diff = right - left - tabWidth
                    left += diff / 2
                    right -= diff / 2
                    fieldLeft?.setInt(tabStrip, left)
                    fieldRight?.setInt(tabStrip, right)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return true
        }
    }

    private val defPreDrawListener = DefPreDrawListener()

    fun setIndicatorWidth(widthDp: Int) {
        val tabLayout: Class<*> = TabLayout::class.java
        var tabStrip: Field? = null
        try {
            tabStrip = tabLayout.getDeclaredField("slidingTabIndicator")
            tabStrip.setAccessible(true)
            val tabIndicator = tabStrip.get(this) as LinearLayout
            val width =
                TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    widthDp.toFloat(),
                    Resources.getSystem().getDisplayMetrics()
                )
                    .toInt()
            //avoid add preDrawListener multi times
            tabIndicator.viewTreeObserver.removeOnPreDrawListener(defPreDrawListener)
            tabIndicator.viewTreeObserver.addOnPreDrawListener(defPreDrawListener)
            defPreDrawListener.setTabStrip(tabIndicator, width)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    class TabIndicatorRectF(private val indicatorBoundsModifier: IndicatorBoundsModifier) :
        RectF(), Serializable {
        private val temp = RectF()

        interface IndicatorBoundsModifier {
            fun modify(bounds: RectF?)
        }

        override fun set(left: Float, top: Float, right: Float, bottom: Float) {
            temp[left, top, right] = bottom
            indicatorBoundsModifier.modify(temp)
            super.set(temp)
        }

        fun replaceBoundsRectF(tabLayout: TabLayout?) {
            try {
                val field = TabLayout::class.java.getDeclaredField("tabViewContentBounds")
                field.isAccessible = true
                field[tabLayout] = this
            } catch (e: NoSuchFieldException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }
        }

        class FixedWidthModifier(width: Float) : IndicatorBoundsModifier {
            private val halfWidth: Float
            override fun modify(bounds: RectF?) {
                val centerX = bounds?.centerX() ?: 0f
                bounds?.left = centerX - halfWidth
                bounds?.right = centerX + halfWidth
            }

            init {
                halfWidth = Math.abs(width) / 2
            }
        }
    }

}