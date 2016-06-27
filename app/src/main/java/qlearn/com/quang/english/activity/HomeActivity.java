package qlearn.com.quang.english.activity;


import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.flavienlaurent.discrollview.lib.DiscrollViewContent;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;

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
    private RevealFrameLayout transition_screen,transition_popup,reveal_layout;
    private FloatingActionButton btn_temp_float, btn_float;
    private FrameLayout main;
    // private RevealFrameLayout image_small;
    private CustomImageSplash img_smile;
    private PopupWindow mPopupWindow;
    private View view_achor;
    boolean check = true,isPopup = true;
    private View viewPopup;
    private DiscrollViewContent list_main;
    private LinearLayout title_layout;
    private TextView txt_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        initPopup();
        getSizeScreen();
    }

    private void initView() {
        viewPopup = LayoutInflater.from(getApplicationContext()).inflate(R.layout.popup_window, null, false);
        main = (FrameLayout) findViewById(R.id.main);
       // list_main = (DiscrollViewContent) findViewById(R.id.list_main);
        reveal_layout = (RevealFrameLayout) findViewById(R.id.reveal_layout);
        transition_screen = (RevealFrameLayout) findViewById(R.id.transition_screen);
        transition_popup = (RevealFrameLayout) viewPopup.findViewById(R.id.transition_popup);
        btn_temp_float = (FloatingActionButton) findViewById(R.id.btn_temp_float);
        btn_float = (FloatingActionButton) findViewById(R.id.btn_float);
        btn_float.bringToFront();
        btn_float.setOnClickListener(this);
        btn_float.post(new Runnable() {
            @Override
            public void run() {
                btn_float.setVisibility(View.INVISIBLE);
            }
        });
        btn_temp_float.setVisibility(View.INVISIBLE);

        img_smile = (CustomImageSplash) findViewById(R.id.img_smile);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                alphaImage();
//                //img_smile.setVisibility(View.INVISIBLE);
                disappearScreen();
            }
        }, 1500);
        title_layout = (LinearLayout) findViewById(R.id.title_layout);
        title_layout.bringToFront();
        title_layout.setOnClickListener(this);
        txt_title = (TextView)findViewById(R.id.txt_title);

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
                reveal_layout.setVisibility(View.GONE);
                ((ViewManager) reveal_layout.getParent()).removeView(reveal_layout);

                moveRealFloatButton();
                //raiseListMain(list_main);
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
    }

    private void getSizeScreen() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        mHeight = displaymetrics.heightPixels;
        mWidth = displaymetrics.widthPixels;
    }
    private void tranPopup(){

        transition_popup.post(new Runnable() {
            @Override
            public void run() {
                float radius = Math.max(transition_popup.getWidth(), transition_popup.getHeight()) * 1.5f;
                SupportAnimator supportAnimator = ViewAnimationUtils.createCircularReveal(transition_popup,
                        transition_popup.getBottom(),
                        transition_popup.getBottom(),
                        0,
                        radius);
                supportAnimator.setDuration(500);
                supportAnimator.start();
            }
        });
    }
    private void raiseListMain(DiscrollViewContent discrollViewContent){
        list_main.setVisibility(View.VISIBLE);
       int startBluePairBottom = discrollViewContent.getBottom();
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(discrollViewContent, "bottom",
                discrollViewContent.getTop(),
                discrollViewContent.getBottom());
        objectAnimator.addListener(new SimpleListener(){
            @Override
            public void onAnimationEnd(Animator animation) {

            }
        });
        objectAnimator.setDuration(500);
        objectAnimator.setInterpolator(ACCELERATE_DECELERATE);
        objectAnimator.start();
    }
    private void returnPopup(final PopupWindow popupWindow){
        transition_popup.post(new Runnable() {
            @Override
            public void run() {
                float radius = Math.max(transition_popup.getWidth(), transition_popup.getHeight()) * 1.5f;
                SupportAnimator supportAnimator = ViewAnimationUtils.createCircularReveal(transition_popup,
                        transition_popup.getBottom(),
                        transition_popup.getBottom(),
                        radius,
                        0);
                supportAnimator.addListener(new SimpleListener() {
                    @Override
                    public void onAnimationEnd() {
                        popupWindow.dismiss();
                    }
                });
                supportAnimator.setDuration(500);
                supportAnimator.start();
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_float:

               // isPopup = true;
                break;
            case R.id.title_layout:

                if(check&&!mPopupWindow.isShowing()){
                    mPopupWindow.showAtLocation(main, Gravity.CENTER, 0, 0);
                    tranPopup();
                    titleLayoutScale(v);
                }else{
                    titleLayoutScaleRevert(v);
                    mPopupWindow.dismiss();
                }
                check = !check;
              //  Toast.makeText(getApplicationContext(),"hello",Toast.LENGTH_SHORT).show();
                break;
        }
    }
    private void titleLayoutScaleRevert(View v){
        txt_title.setVisibility(View.VISIBLE);
        Animation a  = AnimationUtils.loadAnimation(this,R.anim.title_layout_scale_revert);
        a.setFillAfter(true);
        v.startAnimation(a);
    }
    private void titleLayoutScale(View v){
        txt_title.setVisibility(View.INVISIBLE);
        Animation a  = AnimationUtils.loadAnimation(this,R.anim.title_layout_scale);
        a.setFillAfter(true);
        v.startAnimation(a);
    }
    private void initPopup() {
        mPopupWindow = new PopupWindow(viewPopup, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());

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
