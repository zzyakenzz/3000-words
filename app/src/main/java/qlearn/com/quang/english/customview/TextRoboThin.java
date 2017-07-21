package qlearn.com.quang.english.customview;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.TextView;

import com.elmargomez.typer.Font;
import com.elmargomez.typer.Typer;

/**
 * Created by Administrator on 10/02/2016.
 */
public class TextRoboThin extends TextView {
    public TextRoboThin(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            init(context);
        }
    }
    private void init(Context context){
        this.setTypeface(Typer.set(context).getFont(Font.ROBOTO_THIN));
        this.setTextColor(Color.BLACK);
    }

}
