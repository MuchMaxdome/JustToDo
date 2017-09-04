package ninja.maxdome.justtodo.gridRecyclerList

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import ninja.maxdome.justtodo.R

/**
 * Created by Maximilian on 23.08.2017.
 */
class BottomBorderLine: View {
    val _Background: Paint = Paint()

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs, defStyle)
    }

    fun init(attrs: AttributeSet?, defStyle: Int) {
        _Background.color = resources.getColor(R.color.divider)
        _Background.style = Paint.Style.FILL
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val thickness: Float = if (height*0.005 > 2) height.toFloat()*0.005F else 2F
        val clearside: Float = width.toFloat()/36F

        canvas?.drawRect(clearside, height.toFloat()-thickness,width. toFloat(), height.toFloat(), _Background)
    }
}