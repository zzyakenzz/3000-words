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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gc.materialdesign.views.ButtonRectangle;

import java.util.ArrayList;

import qlearn.com.quang.english.R;
import qlearn.com.quang.english.database.Database;
import qlearn.com.quang.english.model.WordModel;
import qlearn.com.quang.english.util.GeneralUtils;

/**
 * Created by quang.nguyen on 07/03/2016.
 */
public class Ref_Vocabulary_Fragment extends Fragment implements View.OnClickListener{
    View v;
    EditText edit_word;
    LinearLayout layout_ref;
    ButtonRectangle btn_confirm;
    Database db;
    ArrayList<WordModel> listWords;
    Context mContext;
    int count = 0;
    private LinearLayout layout_sub;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity().getApplicationContext();
        v = inflater.inflate(R.layout.fragment_ref_vocabulary,container,false);
        initView();

        return v;
    }
    private void initView(){
        layout_sub = (LinearLayout)v.findViewById(R.id.layout_sub);
        layout_sub.setBackgroundResource(R.color.ralate_screen);
        layout_sub.setVisibility(View.VISIBLE);
        animationTransactionSceen();

        edit_word = (EditText)v.findViewById(R.id.edit_word);
        layout_ref = (LinearLayout)v.findViewById(R.id.layout_ref);
        btn_confirm = (ButtonRectangle)v.findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(this);
        listWords = new ArrayList<WordModel>();
//        db = new Database(getContext());
//        try{
//            db.open();
//        }catch (Exception e){
//        }
//        listWords = db.getAllWord();

    }
   private void setData(){

       String word = "";
       word = edit_word.getText().toString();
       word.toLowerCase();

       for(int i = 0 ; i < listWords.size(); i++){
           String w = listWords.get(i).getVocabulary();
           if(!word.isEmpty()
                   && word.contains(w.trim())
                   && w.length()>=3
                   && !word.equals(w)){
               addView(w.toString());
               count++;
           }
           if (count>5){
               break;
           }
       }

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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_confirm:
                count = 0;
                layout_ref.removeAllViews();
               setData();
                break;
        }
    }
    private void addView(String word){
        TextView tv = new TextView(getActivity());
        ViewGroup.LayoutParams param = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(param);
        tv.setPadding(0, GeneralUtils.dpToPixel(getContext(),10),0,GeneralUtils.dpToPixel(getContext(),10));
        tv.setText(word);
        layout_ref.addView(tv);
    }

}
