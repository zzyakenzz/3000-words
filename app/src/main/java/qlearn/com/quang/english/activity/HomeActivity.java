package qlearn.com.quang.english.activity;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

import com.nineoldandroids.animation.Animator;

import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;
import io.codetail.animation.arcanimator.ArcAnimator;
import io.codetail.animation.arcanimator.Side;
import io.codetail.widget.RevealFrameLayout;
import qlearn.com.quang.english.R;
import qlearn.com.quang.english.customview.CustomImageSplash;
import qlearn.com.quang.english.util.GeneralUtils;

/**
 * Created by quang.nguyen on 30/01/2016.
 */
public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    final static AccelerateInterpolator ACCELERATE = new AccelerateInterpolator();
    final static AccelerateDecelerateInterpolator ACCELERATE_DECELERATE = new AccelerateDecelerateInterpolator();
    final static DecelerateInterpolator DECELERATE = new DecelerateInterpolator();
    int mWidth = 0;
    int mHeight = 0;
    int widthFloat = 0;
    private RevealFrameLayout transition_screen;
    private FloatingActionButton btn_temp_float, btn_float;
    private FrameLayout main;
    // private RevealFrameLayout image_small;
    private CustomImageSplash img_smile;
    private PopupWindow mPopupWindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
//        getSizeScreen();


    }

    private void initView() {
        main = (FrameLayout) findViewById(R.id.main);
        transition_screen = (RevealFrameLayout) findViewById(R.id.transition_screen);
        btn_temp_float = (FloatingActionButton) findViewById(R.id.btn_temp_float);
        btn_float = (FloatingActionButton) findViewById(R.id.btn_float);

        btn_float.setOnClickListener(this);
        btn_float.post(new Runnable() {
            @Override
            public void run() {
                btn_float.setVisibility(View.INVISIBLE);
            }
        });
        btn_temp_float.setVisibility(View.INVISIBLE);

        img_smile = (CustomImageSplash) findViewById(R.id.img_smile);
        img_smile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alphaImage();
//                //img_smile.setVisibility(View.INVISIBLE);
                disappearScreen();

            }
        });
    }
    void disappearScreen() {
        float finalRadius = Math.max(transition_screen.getWidth(), transition_screen.getHeight()) * 1.5f;
        btn_temp_float.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        widthFloat = btn_temp_float.getMeasuredWidth();
        SupportAnimator animator = ViewAnimationUtils.createCircularReveal(transition_screen, transition_screen.getWidth() / 2,
                transition_screen.getHeight() / 2,
                finalRadius, widthFloat / 2f);
        animator.setDuration(500);
        animator.addListener(new SimpleListener() {
            @Override
            public void onAnimationEnd() {

                transition_screen.setVisibility(View.GONE);
                ((ViewManager) transition_screen.getParent()).removeView(transition_screen);
                moveRealFloatButton();
            }
        });
        animator.setInterpolator(DECELERATE);
        animator.start();

    }

    void moveRealFloatButton() {
        btn_temp_float.setVisibility(View.VISIBLE);
        ArcAnimator arcAnimator = ArcAnimator.createArcAnimator(btn_temp_float,
                GeneralUtils.getRelativeLeft(btn_float) + widthFloat / 2f,
                GeneralUtils.getRelativeTop(btn_float) + widthFloat / 4f,
                90, Side.LEFT)
                .setDuration(500);
        arcAnimator.addListener(new SimpleListener() {
            @Override
            public void onAnimationEnd(com.nineoldandroids.animation.Animator animation) {
                btn_temp_float.setVisibility(View.GONE);
                ((ViewManager) btn_temp_float.getParent()).removeView(btn_temp_float);
                btn_float.setVisibility(View.VISIBLE);
                btn_float.setClickable(true);
            }
        });
        arcAnimator.start();
    }

    void alphaImage() {
        Animation a = AnimationUtils.loadAnimation(this, R.anim.alpha_scale_face_smile);
        a.setFillAfter(true);
        img_smile.startAnimation(a);
//        float finalRadius = image_small.getWidth();
//
//        SupportAnimator animator = ViewAnimationUtils.createCircularReveal(image_small, image_small.getWidth()/2,
//                image_small.getHeight()/2,
//                finalRadius, image_small.getWidth() / 10);
//        animator.setDuration(500);
//        animator.addListener(new SimpleListener(){
//
//            public void onAnimationEnd() {
//
//            }
//        });
//        animator.setInterpolator(DECELERATE);
//        animator.start();
    }

    private void getSizeScreen() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        mHeight = displaymetrics.heightPixels;
        mWidth = displaymetrics.widthPixels;
    }

    @Override
    public void onClick(View v) {
        initPopup();
        switch (v.getId()) {
            case R.id.btn_float:
                mPopupWindow.showAtLocation(main, Gravity.CENTER, 100, 100);
                break;
        }
    }

    private void initPopup() {
        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.popup_window, null, false);

        mPopupWindow = new PopupWindow(v);

    }
    private static class SimpleListener implements SupportAnimator.AnimatorListener, com.nineoldandroids.animation.ObjectAnimator.AnimatorListener {

        @Override
        public void onAnimationStart() {

        }

        @Override
        public void onAnimationEnd() {

        }

        @Override
        public void onAnimationCancel() {

        }

        @Override
        public void onAnimationRepeat() {

        }

        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {

        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    }

}
