package com.ndmquan.base.project.ui.components.language_extendable_epoxy.custom.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PointF
import android.util.AttributeSet
import android.view.View
import com.ndmquan.base.project.R
import kotlin.math.min
import kotlin.math.sqrt

class CurvedConnectionView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    companion object {
        const val TOP_TO_START = 1
        const val TOP_TO_END = 2
        const val TOP_TO_BOTTOM = 4
        const val BOTTOM_TO_START = 8
        const val BOTTOM_TO_END = 16
        const val START_TO_END = 32

        const val DEFAULT_RADIUS = 0f
        const val DEFAULT_STROKE_WIDTH = 5f
        const val DEFAULT_STROKE_COLOR = Color.BLUE
    }


    var curvedOrientation: Int = TOP_TO_END
        set(value) {
            field = value
            invalidate()
        }

    var cornerRadius: Float = DEFAULT_RADIUS
        set(value) {
            field = value.coerceAtLeast(0f)
            invalidate()
        }

    var strokeWidth: Float = DEFAULT_STROKE_WIDTH
        set(value) {
            field = value.coerceAtLeast(1f)
            updatePaintProperties()
            invalidate()
        }

    var strokeColor: Int = DEFAULT_STROKE_COLOR
        set(value) {
            field = value
            updatePaintProperties()
            invalidate()
        }


    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
        strokeJoin = Paint.Join.ROUND
    }

    private val path = Path()


    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CurvedConnectionView,
            0, 0
        ).apply {
            try {
                curvedOrientation =
                    getInt(R.styleable.CurvedConnectionView_curvedOrientation, TOP_TO_START)
                cornerRadius = getDimension(R.styleable.CurvedConnectionView_radius, DEFAULT_RADIUS)
                strokeWidth =
                    getDimension(R.styleable.CurvedConnectionView_strokeWidth, DEFAULT_STROKE_WIDTH)
                strokeColor =
                    getColor(R.styleable.CurvedConnectionView_strokeColor, DEFAULT_STROKE_COLOR)
            } finally {
                recycle()
            }
        }

        updatePaintProperties()
    }


    private fun updatePaintProperties() {
        paint.strokeWidth = strokeWidth
        paint.color = strokeColor
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredWidth = 200
        val desiredHeight = 200

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val width = when (widthMode) {
            MeasureSpec.EXACTLY -> widthSize
            MeasureSpec.AT_MOST -> min(desiredWidth, widthSize)
            else -> desiredWidth
        }

        val height = when (heightMode) {
            MeasureSpec.EXACTLY -> heightSize
            MeasureSpec.AT_MOST -> min(desiredHeight, heightSize)
            else -> desiredHeight
        }

        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (width == 0 || height == 0) return

        path.reset()
        drawCurvedConnections(canvas)
    }


    private fun drawCurvedConnections(canvas: Canvas) {
        val centerX = width / 2f
        val centerY = height / 2f

        val topPoint = PointF(centerX, 0f)
        val bottomPoint = PointF(centerX, height.toFloat())
        val startPoint = PointF(0f, centerY)
        val endPoint = PointF(width.toFloat(), centerY)

        if (hasOrientation(TOP_TO_START)) {
            drawCurvedLine(canvas, topPoint, startPoint)
        }

        if (hasOrientation(TOP_TO_END)) {
            drawCurvedLine(canvas, topPoint, endPoint)
        }

        if (hasOrientation(TOP_TO_BOTTOM)) {
            drawStraightLine(canvas, topPoint, bottomPoint)
        }

        if (hasOrientation(BOTTOM_TO_START)) {
            drawCurvedLine(canvas, bottomPoint, startPoint)
        }

        if (hasOrientation(BOTTOM_TO_END)) {
            drawCurvedLine(canvas, bottomPoint, endPoint)
        }

        if (hasOrientation(START_TO_END)) {
            drawStraightLine(canvas, startPoint, endPoint)
        }
    }

    private fun drawCurvedLine(canvas: Canvas, start: PointF, end: PointF) {
        path.reset()

        if (cornerRadius == 0f) {
            val centerX = width / 2f
            val centerY = height / 2f

            path.moveTo(start.x, start.y)
            path.lineTo(centerX, centerY)
            path.lineTo(end.x, end.y)
        } else {
            drawGradualCurve(start, end)
        }

        canvas.drawPath(path, paint)
    }

    private fun drawGradualCurve(start: PointF, end: PointF) {
        val centerX = width / 2f
        val centerY = height / 2f

        val maxRadius = min(width, height) / 4f
        val normalizedRadius = (cornerRadius / maxRadius).coerceIn(0f, 1f)

        val controlPoints =
            calculateGradualControlPoints(start, end, centerX, centerY, normalizedRadius)

        path.moveTo(start.x, start.y)
        path.cubicTo(
            controlPoints.first.x, controlPoints.first.y,
            controlPoints.second.x, controlPoints.second.y,
            end.x, end.y
        )
    }

    private fun drawStraightLine(canvas: Canvas, start: PointF, end: PointF) {
        canvas.drawLine(start.x, start.y, end.x, end.y, paint)
    }

    private fun calculateGradualControlPoints(
        start: PointF,
        end: PointF,
        centerX: Float,
        centerY: Float,
        intensity: Float
    ): Pair<PointF, PointF> {
        val dx = end.x - start.x
        val dy = end.y - start.y
        val length = sqrt(dx * dx + dy * dy)

        if (length == 0f) {
            return Pair(start, end)
        }

        val unitX = dx / length
        val unitY = dy / length

        val perpX = -unitY
        val perpY = unitX

        val baseDisplacement = length * 0.15f * intensity

        val t1 = 0.3f
        val displacement1 = baseDisplacement * 0.4f
        val cp1X = start.x + unitX * length * t1 + perpX * displacement1
        val cp1Y = start.y + unitY * length * t1 + perpY * displacement1

        val t2 = 0.7f
        val displacement2 = baseDisplacement * 0.4f
        val cp2X = start.x + unitX * length * t2 + perpX * displacement2
        val cp2Y = start.y + unitY * length * t2 + perpY * displacement2

        val centerPull = intensity * 0.3f
        val toCenterX1 = (centerX - cp1X) * centerPull
        val toCenterY1 = (centerY - cp1Y) * centerPull
        val toCenterX2 = (centerX - cp2X) * centerPull
        val toCenterY2 = (centerY - cp2Y) * centerPull

        return Pair(
            PointF(cp1X + toCenterX1, cp1Y + toCenterY1),
            PointF(cp2X + toCenterX2, cp2Y + toCenterY2)
        )
    }


    private fun hasOrientation(orientation: Int): Boolean {
        return (curvedOrientation and orientation) != 0
    }
}