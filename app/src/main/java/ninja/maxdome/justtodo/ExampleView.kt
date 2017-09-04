package ninja.maxdome.justtodo

// this is a dummy object

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.provider.CalendarContract
import android.util.AttributeSet
import android.view.View
import android.widget.Button

/**
 * Created by Maximilian on 12.08.2017.
 */
class ExampleView: View {
    val _Background: Paint = Paint()
    val _Border: Paint = Paint()

    var mHeight: Float = 0F
    var mWidth: Float = 0F

    var mRange: Float = 0F
    var mIsClicked: Boolean = false

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int){
        _Background.color = Color.WHITE
        _Background.style = Paint.Style.FILL
        _Background.isAntiAlias = true

        _Border.color = resources.getColor(R.color.colorPrimaryDark)
        _Border.style = Paint.Style.STROKE
        _Background.isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        mHeight = height.toFloat()
        mWidth  = width.toFloat()
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)

        val radius: Float = Math.sqrt((height*height).toDouble()+(width*width).toDouble()).toFloat() / 10

        if (mIsClicked){
            canvas?.drawRoundRect(mRange, mRange, mWidth-mRange, mHeight-mRange, radius, radius, _Background)
        }

        canvas?.drawRoundRect(1F, 1F, width.toFloat()-1F,height.toFloat()-1F,radius,radius,_Background)

        /*--------------------------------------------------*/

        for (i in 0..100){
            val pos: Float = 1 + i*0.01F
            canvas?.drawRoundRect(pos, pos, width.toFloat()-pos,height.toFloat()-pos,radius,radius,_Border)
        }
    }
}