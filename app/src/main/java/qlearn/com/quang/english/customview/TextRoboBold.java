package qlearn.com.quang.english.customview;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.TextView;

import com.elmargomez.typer.Font;
import com.elmargomez.typer.Typer;

/**
 * Created by quang.nguyen on 06/04/2016.
 */
public class TextRoboBold extends TextView{
    public TextRoboBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            init(context);
        }
    }
    private void init(Context context){
        this.setTypeface(Typer.set(context).getFont(Font.ROBOTO_BOLD));
        this.setTextColor(Color.WHITE);
    }
}
