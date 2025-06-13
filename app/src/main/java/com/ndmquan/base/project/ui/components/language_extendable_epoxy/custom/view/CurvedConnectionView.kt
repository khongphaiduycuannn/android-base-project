package com.ndmquan.base.project.ui.components.language_extendable_epoxy.custom.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PointF
import android.graphics.RectF
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
        const val DEFAULT_CURVE_INTENSITY = 0.3f  // Renamed for clarity
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

    var curveIntensity: Float = DEFAULT_CURVE_INTENSITY
        set(value) {
            field = value.coerceIn(0f, 1f)
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

    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.TRANSPARENT
    }

    private val path = Path()
    private val backgroundPath = Path()
    private val rectF = RectF()

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
                curveIntensity = getFloat(R.styleable.CurvedConnectionView_curveIntensity, DEFAULT_CURVE_INTENSITY)
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

        // Draw background with corner radius (like drawable)
        if (cornerRadius > 0f) {
            drawRoundedBackground(canvas)
        }

        // Draw connection lines
        path.reset()
        drawCurvedConnections(canvas)
    }

    private fun drawRoundedBackground(canvas: Canvas) {
        backgroundPath.reset()

        // Create rounded rectangle path
        rectF.set(0f, 0f, width.toFloat(), height.toFloat())
        backgroundPath.addRoundRect(rectF, cornerRadius, cornerRadius, Path.Direction.CW)

        // Clip canvas to rounded rectangle for proper corner radius effect
        canvas.clipPath(backgroundPath)
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

        val centerX = width / 2f
        val centerY = height / 2f
        val centerPoint = PointF(centerX, centerY)

        if (curveIntensity == 0f) {
            // Draw straight lines through center when no curve intensity
            path.moveTo(start.x, start.y)
            path.lineTo(centerX, centerY)
            path.lineTo(end.x, end.y)
        } else {
            // Draw lines with rounded corners at center (like drawable corner radius)
            drawRoundedCornerPath(start, centerPoint, end)
        }

        canvas.drawPath(path, paint)
    }

    private fun drawRoundedCornerPath(start: PointF, corner: PointF, end: PointF) {
        // Calculate the maximum possible radius based on distances
        val distToCorner1 = sqrt((corner.x - start.x).let { it * it } + (corner.y - start.y).let { it * it })
        val distToCorner2 = sqrt((end.x - corner.x).let { it * it } + (end.y - corner.y).let { it * it })
        val maxRadius = min(distToCorner1, distToCorner2) / 2f

        // Scale the radius by curveIntensity
        val actualRadius = maxRadius * curveIntensity

        if (actualRadius <= 0f) {
            // No rounding, draw straight lines
            path.moveTo(start.x, start.y)
            path.lineTo(corner.x, corner.y)
            path.lineTo(end.x, end.y)
            return
        }

        // Calculate unit vectors from corner to start and end
        val toStart = PointF(start.x - corner.x, start.y - corner.y)
        val toEnd = PointF(end.x - corner.x, end.y - corner.y)

        val toStartLength = sqrt(toStart.x * toStart.x + toStart.y * toStart.y)
        val toEndLength = sqrt(toEnd.x * toEnd.x + toEnd.y * toEnd.y)

        if (toStartLength == 0f || toEndLength == 0f) {
            // Degenerate case, draw straight line
            path.moveTo(start.x, start.y)
            path.lineTo(end.x, end.y)
            return
        }

        // Normalize vectors
        val unitToStart = PointF(toStart.x / toStartLength, toStart.y / toStartLength)
        val unitToEnd = PointF(toEnd.x / toEndLength, toEnd.y / toEndLength)

        // Calculate points where rounding starts
        val roundStart = PointF(
            corner.x + unitToStart.x * actualRadius,
            corner.y + unitToStart.y * actualRadius
        )
        val roundEnd = PointF(
            corner.x + unitToEnd.x * actualRadius,
            corner.y + unitToEnd.y * actualRadius
        )

        // Draw the path
        path.moveTo(start.x, start.y)
        path.lineTo(roundStart.x, roundStart.y)

        // Calculate control points for quadratic curve (like drawable corner radius)
        // Control point is at the corner itself
        path.quadTo(corner.x, corner.y, roundEnd.x, roundEnd.y)

        path.lineTo(end.x, end.y)
    }

    private fun drawStraightLine(canvas: Canvas, start: PointF, end: PointF) {
        canvas.drawLine(start.x, start.y, end.x, end.y, paint)
    }

    private fun hasOrientation(orientation: Int): Boolean {
        return (curvedOrientation and orientation) != 0
    }
}