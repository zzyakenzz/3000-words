package qlearn.com.quang.english.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import qlearn.com.quang.english.R;

/**
 * Created by quang.nguyen on 07/03/2016.
 */
public class Picture_Through_Fragment extends Fragment{
    private LinearLayout layout_sub;
    Context mContext;
    View v;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity().getApplicationContext();
        v = inflater.inflate(R.layout.fragment_through_picture,container,false);
        initView();
        return v;
    }
    private  void initView(){
        layout_sub = (LinearLayout)v.findViewById(R.id.layout_sub);
        layout_sub.setBackgroundResource(R.color.challegen_screen);
        layout_sub.setVisibility(View.VISIBLE);
        animationTransactionSceen();

    }
    public void animationTransactionSceen(){
        Animation anim = AnimationUtils.loadAnimation(mContext, R.anim.layout_sub_scale_top);
//        anim.setFillAfter(true);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                layout_sub.setVisibility(View.GONE);
                //layout_sub.setBackgroundColor(getResources().getColor(R.color.lime_500));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        layout_sub.startAnimation(anim);
    }

}
