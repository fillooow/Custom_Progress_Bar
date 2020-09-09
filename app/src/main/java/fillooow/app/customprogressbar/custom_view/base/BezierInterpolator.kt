package fillooow.app.customprogressbar.custom_view.base

import android.graphics.PointF
import android.view.animation.Interpolator
import android.view.animation.PathInterpolator

object BezierInterpolator {

    private val PRINCIPLE_DEFAULT_EASE by lazy {
        BezierCurveParameters(
            bottomPoint = PointF(0.25f, 0.1f),
            topPoint = PointF(0.25f, 1f)
        )
    }

    fun getDefaultPrincipleInstance(): Interpolator =
        createPathInterpolator(
            PRINCIPLE_DEFAULT_EASE
        )

    fun getInstance(bottomPoint: PointF, topPoint: PointF): Interpolator =
        createPathInterpolator(
            BezierCurveParameters(
                bottomPoint, topPoint
            )
        )

    private fun createPathInterpolator(parameters: BezierCurveParameters) = with(parameters) {

        return@with PathInterpolator(
            bottomPoint.x,
            bottomPoint.y,
            topPoint.x,
            topPoint.y
        )
    }

    private class BezierCurveParameters(val bottomPoint: PointF, val topPoint: PointF)
}