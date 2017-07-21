package qlearn.com.quang.english.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import qlearn.com.quang.english.util.GeneralUtils;

/**
 * Created by quang.nguyen on 28/12/2015.
 */
public class CustomImageSplash extends ImageView {

    int DP_120 = 0;
    int DP_15 = 0;
    int DP_60 = 0;

    Paint mPaintLip, mPaintEye;
    Paint mPaintLipNoShadow;
    RectF mRectF;
    RectF mRectEyeL, mRectEyeR;
    int w = 0;
    int h = 0;
    int i = 110;
    int j = 70;
    int left = 0;
    int right = 0;

    public CustomImageSplash(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mPaintLip = new Paint();
        mPaintEye = new Paint();
        mPaintLipNoShadow = new Paint();


        mPaintLip.setAntiAlias(true);
        mPaintLip.setColor(Color.parseColor("#795548"));
        mPaintLip.setStyle(Paint.Style.STROKE);
        mPaintLip.setStrokeCap(Paint.Cap.ROUND);
        mPaintLip.setStrokeWidth(10f);
        mPaintLip.setShadowLayer(1.0f, 0.0f, 2.0f, 0xFF000000);

        mPaintLipNoShadow.setAntiAlias(true);
        mPaintLipNoShadow.setColor(Color.parseColor("#795548"));
        mPaintLipNoShadow.setStyle(Paint.Style.STROKE);
        mPaintLipNoShadow.setStrokeCap(Paint.Cap.ROUND);
        mPaintLipNoShadow.setStrokeWidth(10f);

        mPaintEye.setAntiAlias(true);
        mPaintEye.setColor(Color.parseColor("#795548"));
        mPaintEye.setStyle(Paint.Style.FILL);
        mPaintEye.setShadowLayer(1.0f, 0.0f, 2.0f, 0xFF000000);

        DP_120 = (int) GeneralUtils.convertDpToPixel(60, context);
        DP_15 = (int) GeneralUtils.convertDpToPixel(10, context);
        DP_60 = (int) GeneralUtils.convertDpToPixel(40, context);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
        canvas.drawOval(mRectEyeL, mPaintEye);
        canvas.drawOval(mRectEyeR, mPaintEye);
        if (left >= 25) {
            canvas.drawArc(mRectF, 45, 90, false, mPaintLip);
            canvas.drawArc(mRectF, 45, 90, false, mPaintLipNoShadow);
        } else {

            canvas.drawArc(mRectF, 70, 40, false, mPaintLip);
            canvas.drawArc(mRectF, i, left, false, mPaintLip);
            canvas.drawArc(mRectF, j, right, false, mPaintLip);

            canvas.drawArc(mRectF, 70, 40, false, mPaintLipNoShadow);
            canvas.drawArc(mRectF, i, left, false, mPaintLipNoShadow);
            canvas.drawArc(mRectF, j, right, false, mPaintLipNoShadow);
            left++;
            right--;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    invalidate();
                }
            }, 10);
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        w = MeasureSpec.getSize(widthMeasureSpec);
        h = MeasureSpec.getSize(heightMeasureSpec);
        mRectF = new RectF(0, -DP_120, w, h - DP_120);
        mRectEyeL = new RectF(w / 3 - DP_15, h / 2 - DP_120, w / 3 + DP_15, h / 2 + DP_60 - DP_120);
        mRectEyeR = new RectF(w * 2 / 3 - DP_15, h / 2 - DP_120, w * 2 / 3 + DP_15, h / 2 + DP_60 - DP_120);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
