package qlearn.com.quang.english.fragment;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.flavienlaurent.discrollview.lib.DiscrollViewContent;
import com.github.clans.fab.FloatingActionMenu;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;

import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;
import io.codetail.animation.arcanimator.ArcAnimator;
import io.codetail.animation.arcanimator.Side;
import io.codetail.widget.RevealFrameLayout;
import qlearn.com.quang.english.R;
import qlearn.com.quang.english.activity.HomeActivity;
import qlearn.com.quang.english.util.GeneralUtils;
import qlearn.com.quang.english.util.MyApplication;


/**
 * Created by quang.nguyen on 02/03/2016.
 */
public class Home_Fragment extends Fragment implements View.OnClickListener{
    final static AccelerateInterpolator ACCELERATE = new AccelerateInterpolator();
    final static AccelerateDecelerateInterpolator ACCELERATE_DECELERATE = new AccelerateDecelerateInterpolator();
    final static DecelerateInterpolator DECELERATE = new DecelerateInterpolator();
    int mWidth = 0;
    int mHeight = 0;
    int widthFloat = 0;
    private RevealFrameLayout transition_screen,transition_popup,reveal_layout;
    RelativeLayout box_one,box_two,box_three,box_four,box_five,box_six;
    private FloatingActionMenu btn_temp_float,btn_temp_float_two, btn_float;
    private FrameLayout main;
    // private RevealFrameLayout image_small;
    private ImageView img_smile;
    private PopupWindow mPopupWindow;
    private View view_achor;
    boolean check = true,isPopup = true;
    private View viewPopup;
    private DiscrollViewContent list_main;
    private LinearLayout title_layout;
    private ImageView icon_book,icon_ref,icon_headphone,icon_picture,icon_game;
    Context mContext;
    private boolean checkCreateFrag = true;
    //for fragment
    View v;
    private boolean isInit;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        mContext = getActivity();
        v = inflater.inflate(R.layout.fragment_home_layout, container, false);
        isInit = ((MyApplication)getActivity().getApplication()).isInitView();
        initView();
        initPopup();
        getSizeScreen();
        return v;
    }




    private void initView() {

        viewPopup = LayoutInflater.from(mContext).inflate(R.layout.popup_window, null, false);
        main = (FrameLayout) v.findViewById(R.id.main);
        // list_main = (DiscrollViewContent) findViewById(R.id.list_main);
        reveal_layout = (RevealFrameLayout) v.findViewById(R.id.reveal_layout);
        transition_screen = (RevealFrameLayout) v.findViewById(R.id.transition_screen);
        transition_popup = (RevealFrameLayout) viewPopup.findViewById(R.id.transition_popup);
        btn_temp_float = (FloatingActionMenu) v.findViewById(R.id.btn_temp_float);
        btn_temp_float_two = (FloatingActionMenu) v.findViewById(R.id.btn_temp_float_two);
        btn_float = (FloatingActionMenu) v.findViewById(R.id.btn_float);
//        btn_float.bringToFront();
        btn_float.setClosedOnTouchOutside(true);

//        btn_temp_float_two.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
//            @Override
//            public void onMenuToggle(boolean opened) {
//                if(opened){
//                    btn_float.toggleMenu(true);
//                }
//            }
//        });
        btn_float.setOnClickListener(this);

        btn_temp_float.setVisibility(View.INVISIBLE);


        title_layout = (LinearLayout) v.findViewById(R.id.title_layout);
        title_layout.bringToFront();
        title_layout.setOnClickListener(this);

        icon_book = (ImageView)v.findViewById(R.id.icon_book);
        icon_ref = (ImageView)v.findViewById(R.id.icon_ref);
        icon_headphone = (ImageView)v.findViewById(R.id.icon_headphone);
        icon_picture = (ImageView)v.findViewById(R.id.icon_picture);
        icon_game = (ImageView)v.findViewById(R.id.icon_game);


        box_one = (RelativeLayout)v.findViewById(R.id.box_one);
        box_two = (RelativeLayout)v.findViewById(R.id.box_two);
        box_three = (RelativeLayout)v.findViewById(R.id.box_three);
        box_four = (RelativeLayout)v.findViewById(R.id.box_four);
        box_four.bringToFront();
        box_five = (RelativeLayout)v.findViewById(R.id.box_five);
        box_six = (RelativeLayout)v.findViewById(R.id.box_six);
        box_one.setOnClickListener(this);
//        box_one.setOnTouchListener(this);
        box_two.setOnClickListener(this);
        box_three.setOnClickListener(this);
        box_four.setOnClickListener(this);
        box_five.setOnClickListener(this);
        box_six.setOnClickListener(this);

        if(isInit){
            btn_float.post(new Runnable() {
                @Override
                public void run() {
                    btn_float.setVisibility(View.INVISIBLE);
                }
            });
            img_smile = (ImageView) v.findViewById(R.id.img_smile);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    alphaImage();
//                //img_smile.setVisibility(View.INVISIBLE);
                    disappearScreen();
                }
            }, 2000);

        }else{
            img_smile.setVisibility(View.GONE);

            reveal_layout.setVisibility(View.GONE);

            transition_screen.setVisibility(View.GONE);

            btn_temp_float.setVisibility(View.GONE);

            btn_temp_float_two.setVisibility(View.GONE);

            title_layout.setVisibility(View.VISIBLE);
            box_one.setVisibility(View.VISIBLE);
            box_two.setVisibility(View.VISIBLE);
            box_three.setVisibility(View.VISIBLE);
            box_four.setVisibility(View.VISIBLE);
            box_five.setVisibility(View.VISIBLE);
            box_six.setVisibility(View.VISIBLE);
            btn_float.setVisibility(View.VISIBLE);
            checkCreateFrag = true;
        }
    }

    void disappearScreen() {
        float finalRadius = Math.max(transition_screen.getWidth(), transition_screen.getHeight()) * 1.5f;
        btn_temp_float.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        widthFloat = btn_temp_float.getMeasuredWidth();
        SupportAnimator animator = ViewAnimationUtils.createCircularReveal(transition_screen, transition_screen.getWidth() / 2,
                transition_screen.getHeight() / 2,
                finalRadius, widthFloat / 2f);
        animator.setDuration(700);
        animator.addListener(new SimpleListener() {
            @Override
            public void onAnimationEnd() {

                transition_screen.setVisibility(View.GONE);
                ((ViewManager) transition_screen.getParent()).removeView(transition_screen);
                reveal_layout.setVisibility(View.GONE);
                ((ViewManager) reveal_layout.getParent()).removeView(reveal_layout);
                moveRealFloatButton();
                layoutScale(title_layout);
                layoutScale(box_one);
                layoutScale(box_two);
                layoutScale(box_three);
                layoutScale(box_four);
                layoutScale(box_five);
                layoutScale(box_six);
                //raiseListMain(list_main);
            }
        });
        animator.setInterpolator(ACCELERATE_DECELERATE);
        animator.start();

    }

    void moveRealFloatButton() {
        btn_temp_float.setVisibility(View.VISIBLE);
        ArcAnimator arcAnimator = ArcAnimator.createArcAnimator(btn_temp_float,
                GeneralUtils.getRelativeLeft(btn_temp_float_two) + widthFloat / 2f,
                GeneralUtils.getRelativeTop(btn_temp_float_two) + widthFloat / 2f -getStatusBarHeight()
                        + GeneralUtils.convertDpToPixel(2,mContext),
                90, Side.LEFT)
                .setDuration(500);
        arcAnimator.addListener(new SimpleListener() {
            @Override
            public void onAnimationEnd(com.nineoldandroids.animation.Animator animation) {
                btn_temp_float.setVisibility(View.INVISIBLE);
                ((ViewManager) btn_temp_float.getParent()).removeView(btn_temp_float);
                btn_temp_float_two.setVisibility(View.INVISIBLE);
                ((ViewManager) btn_temp_float_two.getParent()).removeView(btn_temp_float_two);
                btn_float.setVisibility(View.VISIBLE);
                btn_float.setClickable(true);

                box_one.setClickable(true);

            }
        });
        arcAnimator.setInterpolator(ACCELERATE_DECELERATE);
        arcAnimator.start();
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
    void alphaImage() {
        Animation a = AnimationUtils.loadAnimation(getActivity(), R.anim.alpha_scale_face_smile);
        a.setFillAfter(true);
        img_smile.startAnimation(a);
    }

    private void getSizeScreen() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
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
                if(check&&!mPopupWindow.isShowing()){
//                   mPopupWindow.showAtLocation(main, Gravity.CENTER, 0, 0);
//                   tranPopup();

                }else{
                    mPopupWindow.dismiss();
                }
                check = !check;
                // isPopup = true;
                break;
            case R.id.title_layout:
////                   mPopupWindow.showAtLocation(main, Gravity.CENTER, 0, 0);
////                   tranPopup();
////                titleLayoutScale(v);
//                Intent intent = new Intent(mContext,MainActivity.class);
//                //intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                startActivity(intent);
//
//                // getWindow().setWindowAnimations(0);
                List_Vocabulary_Fragment list_vocabulary_fragment = new List_Vocabulary_Fragment();
                ((HomeActivity)getActivity()).replaceFragment(list_vocabulary_fragment);
                break;
            case R.id.box_one:
                icon_book.startAnimation(feedBackAnimation());

                ((HomeActivity)getActivity()).animationSub(HomeActivity.Frag.BOOK,R.color.voca_screen, R.drawable.book_sub);
                break;
            case R.id.box_two:
                icon_ref.startAnimation(feedBackAnimation());
                ((HomeActivity)getActivity()).animationSub(HomeActivity.Frag.REF,R.color.ralate_screen, R.drawable.ref_sub);
                break;
            case R.id.box_three:
                icon_headphone.startAnimation(feedBackAnimation());
                ((HomeActivity)getActivity()).animationSub(HomeActivity.Frag.HEADPHONE,R.color.listen_screen, R.drawable.headphone_sub);
                break;
            case R.id.box_four:
                icon_picture.startAnimation(feedBackAnimation());
                ((HomeActivity)getActivity()).animationSub(HomeActivity.Frag.PICTURE,R.color.challegen_screen, R.drawable.picture_sub);
                break;
            case R.id.box_five:
                icon_game.startAnimation(feedBackAnimation());
                ((HomeActivity)getActivity()).animationSub(HomeActivity.Frag.GAME,R.color.game_screen, R.drawable.game_sub);
                break;
        }
    }
    private Animation feedBackAnimation(){
        return AnimationUtils.loadAnimation(mContext,R.anim.feedback_icon_home);
    }
    private void layoutScale(View v){
        v.setVisibility(View.VISIBLE);
        Animation a  = AnimationUtils.loadAnimation(getActivity(),R.anim.layout_scale);
        a.setFillAfter(true);
        v.startAnimation(a);
    }
    private void initPopup() {
        mPopupWindow = new PopupWindow(viewPopup, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());

    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((MyApplication)getActivity().getApplication()).setIsInitView(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((MyApplication)getActivity().getApplication()).setIsInitView(true);
    }

//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//        int x = (int)event.getX();
//        int y = (int)event.getY();
//        y += GeneralUtils.getRelativeTop(v)-getStatusBarHeight();
//        x += GeneralUtils.getRelativeLeft(v);
//        switch (v.getId()){
//            case R.id.box_one:
//                if(checkCreateFrag) {
//                    icon_book.startAnimation(feedBackAnimation());
//                    ((HomeActivity) getActivity()).animationSubReveal(x, y);
//                    checkCreateFrag = false;
//                }
//
//                break;
//        }
//       return true;
//    }

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
