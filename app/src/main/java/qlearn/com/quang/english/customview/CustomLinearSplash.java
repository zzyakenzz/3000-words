package qlearn.com.quang.english.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

import qlearn.com.quang.english.util.GeneralUtils;


/**
 * Created by quang.nguyen on 04/01/2016.
 */
public class CustomLinearSplash extends ImageView {
    private static final int DEFAULT_CIRCLE_COLOR = Color.GREEN;
    int DP_120 = 0;
    int check = 0;
    int cx;
    int cy;
    int radius;
    private int circleColor = DEFAULT_CIRCLE_COLOR;
    private Paint paint;

    public CustomLinearSplash(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        paint = new Paint();
        DP_120 = (int) GeneralUtils.convertDpToPixel(120, context);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        if(check == 0) {
//            int w = getWidth();
//            int h = getHeight();
//
//            int pl = getPaddingLeft();
//            int pr = getPaddingRight();
//            int pt = getPaddingTop();
//            int pb = getPaddingBottom();
//
//            int usableWidth = w - (pl + pr);
//            int usableHeight = h - (pt + pb);
//            check = usableHeight / 2 + DP_120;
//            radius = usableHeight / 2 + DP_120;
//            cx = pl + (usableWidth / 2);
//            cy = pt + (usableHeight / 2);
//        }else{
////            radius--;
//        }
//        paint.setColor(circleColor);
//        canvas.drawCircle(cx, cy, radius, paint);

    }
}
