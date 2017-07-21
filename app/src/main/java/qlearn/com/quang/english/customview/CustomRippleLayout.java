package qlearn.com.quang.english.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.gc.materialdesign.views.LayoutRipple;

/**
 * Created by quang.nguyen on 28/03/2016.
 */
public class CustomRippleLayout extends LayoutRipple {
    Context mContext;
    public CustomRippleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return super.onTouchEvent(event);
    }
}
