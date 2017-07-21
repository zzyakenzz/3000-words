package qlearn.com.quang.english.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by quang.nguyen on 23/02/2016.
 */
public class CustomBorderCard extends View {
    public CustomBorderCard(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    int background;
    float rippleSpeed = 10f;
    int rippleSize = 3;

    OnClickListener onClickListener;
    int backgroundColor = Color.parseColor("#FFFFFF");

    Integer rippleColor;
    Float xRippleOrigin;
    Float yRippleOrigin;



    // Set color of background
    public void setBackgroundColor(int color) {
        this.backgroundColor = color;

        super.setBackgroundColor(color);
    }

    public void setRippleSpeed(int rippleSpeed) {
        this.rippleSpeed = rippleSpeed;
    }

    // ### RIPPLE EFFECT ###

    float x = -1, y = -1;
    float radius = -1;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        invalidate();
        if (isEnabled()) {

            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                radius = getHeight() / rippleSize;
                x = event.getX();
                y = event.getY();
            } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                radius = getHeight() / rippleSize;
                x = event.getX();
                y = event.getY();
                if (!((event.getX() <= getWidth() && event.getX() >= 0) && (event
                        .getY() <= getHeight() && event.getY() >= 0))) {

                    x = -1;
                    y = -1;
                }
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                if ((event.getX() <= getWidth() && event.getX() >= 0)
                        && (event.getY() <= getHeight() && event.getY() >= 0)) {
                    radius++;
                } else {

                    x = -1;
                    y = -1;
                }
            }if (event.getAction() == MotionEvent.ACTION_CANCEL) {

                x = -1;
                y = -1;
            }
        }
        return true;
    }

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction,
                                  Rect previouslyFocusedRect) {
        if (!gainFocus) {
            x = -1;
            y = -1;
        }
    }


    public Bitmap makeCircle() {
        Bitmap output = Bitmap.createBitmap(getWidth(), getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        canvas.drawARGB(0, 0, 0, 0);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        if (rippleColor == null)
            rippleColor = makePressColor();
        paint.setColor(rippleColor);
        x = (xRippleOrigin == null) ? x : xRippleOrigin;
        y = (yRippleOrigin == null) ? y : yRippleOrigin;
        canvas.drawCircle(x, y, radius, paint);
        if (radius > getHeight() / rippleSize)
            radius += rippleSpeed;
        if (radius >= getWidth()) {
            x = -1;
            y = -1;
            radius = getHeight() / rippleSize;
            if (onClickListener != null)
                onClickListener.onClick(this);
        }
        return output;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (x != -1) {
            Rect src = new Rect(0, 0, getWidth(), getHeight());
            Rect dst = new Rect(0, 0, getWidth(), getHeight());
            canvas.drawBitmap(makeCircle(), src, dst, null);
            invalidate();
        }
    }

    /**
     * Make a dark color to ripple effect
     *
     * @return
     */
    protected int makePressColor() {
        int r = (this.backgroundColor >> 16) & 0xFF;
        int g = (this.backgroundColor >> 8) & 0xFF;
        int b = (this.backgroundColor >> 0) & 0xFF;
        r = (r - 30 < 0) ? 0 : r - 30;
        g = (g - 30 < 0) ? 0 : g - 30;
        b = (b - 30 < 0) ? 0 : b - 30;
        return Color.rgb(r, g, b);
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        onClickListener = l;
    }

    public void setRippleColor(int rippleColor) {
        this.rippleColor = rippleColor;
    }

    public void setxRippleOrigin(Float xRippleOrigin) {
        this.xRippleOrigin = xRippleOrigin;
    }

    public void setyRippleOrigin(Float yRippleOrigin) {
        this.yRippleOrigin = yRippleOrigin;
    }
}
