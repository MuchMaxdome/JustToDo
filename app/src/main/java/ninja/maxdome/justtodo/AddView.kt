package ninja.maxdome.justtodo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 * Created by Maximilian on 24.08.2017.
 */
class AddView: View {
    val _Background: Paint = Paint()
    val _Border: Paint = Paint()
    val _Plus: Paint = Paint()

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
        _Background.color = resources.getColor(R.color.colorAccent)
        _Background.style = Paint.Style.FILL
        _Background.isAntiAlias = true

        _Border.color = resources.getColor(R.color.blackborder)
        _Border.style = Paint.Style.STROKE
        _Background.isAntiAlias = true

        _Plus.color = Color.WHITE
        _Plus.style = Paint.Style.FILL_AND_STROKE
        _Plus.isAntiAlias = true

        this.setOnClickListener { onClick() }
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

        for (i in 0..120){
            val pos: Float = 1 + i*0.01F
            canvas?.drawRoundRect(pos, pos, width.toFloat()-pos,height.toFloat()-pos,radius,radius,_Border)
        }

        /*---------------------------------------------------*/

        // draw the Plus in the middle
        val plusThickness = mWidth/12

        canvas?.drawRect(mWidth/2-plusThickness/2, plusThickness*3, mWidth/2+plusThickness/2, mHeight-plusThickness*3, _Plus)
        canvas?.drawRect(plusThickness*3, mHeight/2-plusThickness/2, mWidth-plusThickness*3, mHeight/2+plusThickness/2, _Plus)

    }

    public fun onClick(){
        mIsClicked = true

        _Background.color =if (_Background.color == Color.WHITE) resources.getColor(R.color.colorAccent) else Color.WHITE
        _Plus.color =if (_Background.color == Color.WHITE) resources.getColor(R.color.colorAccent) else Color.WHITE
        invalidate()

        mIsClicked = false
    }
}