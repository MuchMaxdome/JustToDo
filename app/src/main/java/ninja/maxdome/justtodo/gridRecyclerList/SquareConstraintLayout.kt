package ninja.maxdome.myrecycler

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet

/**
 * Created by Maximilian on 23.08.2017.
 */
class SquareConstraintLayout(context: Context?, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {
    public override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(heightMeasureSpec, heightMeasureSpec)
    }
}