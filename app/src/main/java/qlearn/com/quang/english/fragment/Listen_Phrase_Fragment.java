package qlearn.com.quang.english.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import qlearn.com.quang.english.R;
import qlearn.com.quang.english.activity.DetailListenPhraseActivity;
import qlearn.com.quang.english.adapter.Grid_listen_Adapter;

/**
 * Created by quang.nguyen on 07/03/2016.
 */
public class Listen_Phrase_Fragment extends Fragment implements AdapterView.OnItemClickListener{
    Grid_listen_Adapter adapter;
    ArrayList<String> listAlphabet;
    View v;
    private LinearLayout layout_sub;
    Context mContext;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity().getApplicationContext();
        v = inflater.inflate(R.layout.fragment_listen_phrases,container,false);
        initView();
        return v;
    }
    private void initView(){
        layout_sub = (LinearLayout)v.findViewById(R.id.layout_sub);
        layout_sub.setBackgroundResource(R.color.listen_screen);
        layout_sub.setVisibility(View.VISIBLE);
        animationTransactionSceen();
        listAlphabet = new ArrayList<>();
        for(int i = 64; i< 91; i++){
            if(i == 64){
                listAlphabet.add("All");
            }else{
                listAlphabet.add(String.valueOf((char)i));
            }

        }
        adapter = new Grid_listen_Adapter(getContext(),R.layout.item_grid_listen,listAlphabet);
        GridView grid_listen = (GridView)v.findViewById(R.id.grid_listen);
        grid_listen.setAdapter(adapter);
        grid_listen.setOnItemClickListener(this);
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
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), DetailListenPhraseActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("list_phrase_fragment",listAlphabet.get(position).toUpperCase().trim());
        getActivity().startActivity(intent);
    }

}
