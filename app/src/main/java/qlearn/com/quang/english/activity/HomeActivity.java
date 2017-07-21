package qlearn.com.quang.english.activity;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import qlearn.com.quang.english.R;
import qlearn.com.quang.english.fragment.Game_Fragment;
import qlearn.com.quang.english.fragment.Home_Fragment;
import qlearn.com.quang.english.fragment.List_Vocabulary_Fragment;
import qlearn.com.quang.english.fragment.Listen_Phrase_Fragment;
import qlearn.com.quang.english.fragment.Picture_Through_Fragment;
import qlearn.com.quang.english.fragment.Ref_Vocabulary_Fragment;

/**
 * Created by quang.nguyen on 30/01/2016.
 */
public class HomeActivity extends ActionBarActivity {
    public  static enum Frag {
        BOOK, REF, HEADPHONE, PICTURE, GAME
    }
    Frag mFrag;
    LinearLayout layout_sub;
    ImageView img_sub;
    Context mContext;
    RelativeLayout layout_title_home;
    TextView txt_title_home;
  //  RevealFrameLayout reveal_layout,transition_screen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initView();

        Home_Fragment home_fragment = new Home_Fragment();
        replaceFirst(home_fragment);
    }
    private void initView(){
        mContext = getApplicationContext();
        layout_sub = (LinearLayout)findViewById(R.id.layout_sub);
        img_sub = (ImageView)findViewById(R.id.img_sub);
        img_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_sub.setVisibility(View.GONE);
                layout_sub.setVisibility(View.GONE);
            }
        });
    }
    public void animationSub(final Frag frag,int color,int img){
        mFrag = frag;
        img_sub.setVisibility(View.VISIBLE);
        img_sub.setBackgroundResource(img);
        layout_sub.setVisibility(View.VISIBLE);
        layout_sub.setBackgroundResource(color);
        Animation anim = AnimationUtils.loadAnimation(mContext, R.anim.anim_replace_fragment_from_home);
        layout_sub.startAnimation(anim);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {


                chooseFragment();
                layout_sub.setVisibility(View.GONE);

                try {
                    img_sub.setVisibility(View.GONE);
                } catch (Exception e) {

                }


            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void chooseFragment(){
        Fragment fragment = new Home_Fragment();
        switch (mFrag){
            case BOOK:
                fragment = new List_Vocabulary_Fragment();
                break;
            case REF:
                fragment = new Ref_Vocabulary_Fragment();
                break;
            case HEADPHONE:
                fragment = new Listen_Phrase_Fragment();
                break;
            case PICTURE:
                fragment = new Picture_Through_Fragment();
                break;
            case GAME:
                fragment = new Game_Fragment();
                break;
        }

        replaceFragment(fragment);
    }
//    public void animationSubReveal(int x,int y){
//        layout_title_home = (RelativeLayout)findViewById(R.id.layout_title_home);
//        reveal_layout= (RevealFrameLayout)findViewById(R.id.reveal_layout_home);
//        transition_screen = (RevealFrameLayout)findViewById(R.id.transition_screen_home);
//        layout_title_home.setVisibility(View.VISIBLE);
//        reveal_layout.setVisibility(View.VISIBLE);
//        transition_screen.setVisibility(View.VISIBLE);
//        float finalRadius = Math.max(transition_screen.getWidth(), transition_screen.getHeight()) * 1.5f;
//
//        SupportAnimator anim = ViewAnimationUtils.createCircularReveal(transition_screen,x,y,0,finalRadius);
//        anim.setDuration(1000);
//        anim.addListener(new SimpleListener() {
//            @Override
//            public void onAnimationEnd() {
//                layout_title_home.setVisibility(View.INVISIBLE);
//                reveal_layout.setVisibility(View.INVISIBLE);
//                transition_screen.setVisibility(View.INVISIBLE);
//                List_Vocabulary_Fragment listVocabularyFragment = new List_Vocabulary_Fragment();
//                replaceFragment(listVocabularyFragment);
//            }
//        });
//        anim.start();
//
//    }
    public void replaceFragment(Fragment fragment){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.content_main, fragment);
        transaction.addToBackStack("");
        transaction.setCustomAnimations(0,0);
        transaction.commit();
    }
    public void replaceFirst(Fragment fragment){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.content_main, fragment);
        transaction.commit();
    }
    public boolean removeTopFragment(){
        FragmentManager fm = getSupportFragmentManager();
        if(fm.getBackStackEntryCount()>0){
            try{
                fm.popBackStack();
                return true;
            }catch (Exception e){

            }
        }
        return false;
    }
    public void clearTopFragment(){
        FragmentManager fm = getSupportFragmentManager();
        for(int i = 0 ; i < fm.getBackStackEntryCount(); i++){
            try{
                fm.popBackStack();
            }catch (Exception e){

            }
        }
    }

}
