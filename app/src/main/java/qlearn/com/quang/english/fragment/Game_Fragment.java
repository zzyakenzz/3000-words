package qlearn.com.quang.english.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import qlearn.com.quang.english.R;
import qlearn.com.quang.english.customview.CustomRippleLayout;
import qlearn.com.quang.english.customview.TextRoboBold;
import qlearn.com.quang.english.database.Database;
import qlearn.com.quang.english.model.WordModel;

/**
 * Created by quang.nguyen on 07/03/2016.
 */
public class Game_Fragment extends Fragment implements View.OnClickListener{
    View v;
    private TextView txt_sugess_A,txt_sugess_B,txt_sugess_C,txt_sugess_D;
    private TextRoboBold txt_A,txt_B,txt_C,txt_D;
    private TextView txt_word_form_A,txt_word_form_B,txt_word_form_C,txt_word_form_D;
    private FrameLayout frag_img_number_A,frag_img_number_B,frag_img_number_C,frag_img_number_D;
    private TextView txt_number_A,txt_number_B,txt_number_C,txt_number_D;
    private CustomRippleLayout btn_A,btn_B,btn_C,btn_D;
    private ArrayList<WordModel> listWords = new ArrayList<>();
    private ArrayList<WordModel> listResult;
    private Database database;
    private Context mContext;
    private boolean tagA,tagB,tagC,tagD;
    private ArrayList<Integer> arrNumberCorrect;
    private int count = 1;
    private int[] lisNumber;
    private FrameLayout frag_next_question,frag_error_answer;
    private LinearLayout layout_sub;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_game,container,false);
        initView();
        initData();
        return v;
    }
    private void initView(){
        mContext = getContext();
        layout_sub = (LinearLayout)v.findViewById(R.id.layout_sub);

        txt_sugess_A = (TextView)v.findViewById(R.id.txt_sugess_A);
        txt_sugess_B = (TextView)v.findViewById(R.id.txt_sugess_B);
        txt_sugess_C = (TextView)v.findViewById(R.id.txt_sugess_C);
        txt_sugess_D = (TextView)v.findViewById(R.id.txt_sugess_D);


        txt_A = (TextRoboBold)v.findViewById(R.id.txt_A);
        txt_B = (TextRoboBold)v.findViewById(R.id.txt_B);
        txt_C = (TextRoboBold)v.findViewById(R.id.txt_C);
        txt_D = (TextRoboBold)v.findViewById(R.id.txt_D);

        txt_word_form_A = (TextView)v.findViewById(R.id.txt_word_form_A);
        txt_word_form_B = (TextView)v.findViewById(R.id.txt_word_form_B);
        txt_word_form_C = (TextView)v.findViewById(R.id.txt_word_form_C);
        txt_word_form_D = (TextView)v.findViewById(R.id.txt_word_form_D);

        frag_img_number_A = (FrameLayout)v.findViewById(R.id.fra_img_number_A);
        frag_img_number_B = (FrameLayout)v.findViewById(R.id.fra_img_number_B);
        frag_img_number_C = (FrameLayout)v.findViewById(R.id.fra_img_number_C);
        frag_img_number_D = (FrameLayout)v.findViewById(R.id.fra_img_number_D);
        frag_next_question = (FrameLayout)v.findViewById(R.id.frag_next_question);
        frag_next_question.setOnClickListener(this);
        frag_error_answer = (FrameLayout)v.findViewById(R.id.frag_error_answer);

        btn_A = (CustomRippleLayout)v.findViewById(R.id.btn_A);
        btn_B = (CustomRippleLayout)v.findViewById(R.id.btn_B);
        btn_C = (CustomRippleLayout)v.findViewById(R.id.btn_C);
        btn_D = (CustomRippleLayout)v.findViewById(R.id.btn_D);
        btn_A.setOnClickListener(this);
        btn_B.setOnClickListener(this);
        btn_C.setOnClickListener(this);
        btn_D.setOnClickListener(this);

        txt_number_A = (TextView)v.findViewById(R.id.txt_number_A);
        txt_number_B = (TextView)v.findViewById(R.id.txt_number_B);
        txt_number_C = (TextView)v.findViewById(R.id.txt_number_C);
        txt_number_D = (TextView)v.findViewById(R.id.txt_number_D);
    }
    private void initData(){
        layout_sub.setBackgroundResource(R.color.game_screen);
        layout_sub.setVisibility(View.VISIBLE);
        animationTransactionSceen();
        database = new Database(mContext);
        try {
            database.open();
        }catch (Exception e){

        }
                        listWords = database.getAllWord();
                        resetQuestion();


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

    private void resetQuestion(){
        hideCorrect();
        arrNumberCorrect = new ArrayList<>();
        listResult = new ArrayList<>();
        tagA = tagB = tagC = tagD = false;
        ArrayList<WordModel> listTemp = listWords;
        for(int i = 0; i<4 ; i++){
            int number = geneRandomInList(listTemp.size());
            listResult.add(listTemp.get(number));
            listTemp.remove(number);
        }
        for(int i = 0; i<listResult.size(); i++){
            if(i == 0){
                txt_sugess_A.setText(listResult.get(i).getTranslate());
                txt_word_form_A.setText(listResult.get(i).getWordForm());
            }
            if(i == 1){
                txt_sugess_B.setText(listResult.get(i).getTranslate());
                txt_word_form_B.setText(listResult.get(i).getWordForm());
            }
            if(i == 2){
                txt_sugess_C.setText(listResult.get(i).getTranslate());
                txt_word_form_C.setText(listResult.get(i).getWordForm());
            }
            if(i == 3){
                txt_sugess_D.setText(listResult.get(i).getTranslate());
                txt_word_form_D.setText(listResult.get(i).getWordForm());
            }
        }
        lisNumber = geneOneToFour();

        txt_A.setText(listResult.get(lisNumber[0]).getVocabulary());
        txt_A.setTag(lisNumber[0]);
        Log.e("Hello", "Tag - " + lisNumber[0]);


        txt_B.setText(listResult.get(lisNumber[1]).getVocabulary());
        txt_B.setTag(lisNumber[1]);



        txt_C.setText(listResult.get(lisNumber[2]).getVocabulary());
        txt_C.setTag(lisNumber[2]);


        txt_D.setText(listResult.get(lisNumber[3]).getVocabulary());
        txt_D.setTag(lisNumber[3]);


        frag_img_number_A.setVisibility(View.INVISIBLE);
        frag_img_number_B.setVisibility(View.INVISIBLE);
        frag_img_number_C.setVisibility(View.INVISIBLE);
        frag_img_number_D.setVisibility(View.INVISIBLE);

    }

    private void checkAgain(){
        count=0;
        tagA = tagB = tagC = tagD = false;
        arrNumberCorrect.clear();
        frag_img_number_A.setVisibility(View.INVISIBLE);
        frag_img_number_B.setVisibility(View.INVISIBLE);
        frag_img_number_C.setVisibility(View.INVISIBLE);
        frag_img_number_D.setVisibility(View.INVISIBLE);
    }

    private int[] geneOneToFour(){
        int[] arrnNumber = new int[]{-1,-1,-1,-1};
        boolean check;
            for(int i=0 ; i<4;i++){
                check = true;
                int number = new Random().nextInt(4);
                for (int j=0;j<arrnNumber.length;j++){
                    if(number==arrnNumber[j]){
                        check = false;
                        break;
                    }
                }
                if(check){
                    arrnNumber[i] = number;
                }else{
                    i--;
                }
             }

        return arrnNumber;

    }

    private int geneRandomInList(int size){
        return new Random().nextInt(size);
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_A:
                if(!tagA){
                    hideWrong();
                    tagA = true;
                    frag_img_number_A.setVisibility(View.VISIBLE);
                    arrNumberCorrect.add((int) txt_A.getTag());
                    txt_number_A.setText(""+count);
                    if(isAllCheck()){
                        if(isHasCheck()){
                            showCorrect();
                        }else{
                            checkAgain();
                            showWrong();
                        }
                    }
                    count++;
                }
                break;
            case R.id.btn_B:
                if(!tagB){
                    hideWrong();
                    tagB = true;
                    frag_img_number_B.setVisibility(View.VISIBLE);
                    arrNumberCorrect.add((int) txt_B.getTag());
                    txt_number_B.setText(""+count);
                    if(isAllCheck()){
                        if(isHasCheck()){
                            showCorrect();
                        }else{
                            checkAgain();
                            showWrong();
                        }
                    }
                    count++;
                }
                break;
            case R.id.btn_C:
                if(!tagC){
                    hideWrong();
                    tagC = true;
                    frag_img_number_C.setVisibility(View.VISIBLE);
                    arrNumberCorrect.add((int) txt_C.getTag());
                    txt_number_C.setText(""+count);
                    if(isAllCheck()){
                        if(isHasCheck()){
                            showCorrect();
                        }else{
                            checkAgain();
                            showWrong();
                        }
                    }
                    count++;
                }
                break;
            case R.id.btn_D:
                if(!tagD){
                    hideWrong();
                    tagD = true;
                    frag_img_number_D.setVisibility(View.VISIBLE);
                    arrNumberCorrect.add((int)txt_D.getTag());
                    txt_number_D.setText(""+count);
                    if(isAllCheck()){
                        if(isHasCheck()){
                            showCorrect();
                        }else{
                            checkAgain();
                            showWrong();
                        }
                    }
                    count++;
                }
                break;
            case R.id.frag_next_question:
                resetQuestion();
                count = 1;
                break;
            default:
                break;
        }
    }
    private void showCorrect(){
        frag_next_question.setVisibility(View.VISIBLE);
        frag_img_number_A.setVisibility(View.INVISIBLE);
        frag_img_number_B.setVisibility(View.INVISIBLE);
        frag_img_number_C.setVisibility(View.INVISIBLE);
        frag_img_number_D.setVisibility(View.INVISIBLE);
    }
    private void hideCorrect(){
        frag_next_question.setVisibility(View.INVISIBLE);
    }
    private void showWrong(){
        frag_error_answer.setVisibility(View.VISIBLE);
    }
    private void hideWrong(){
        frag_error_answer.setVisibility(View.INVISIBLE);
    }
    private boolean isHasCheck(){
        for(int i = 0; i<arrNumberCorrect.size() ; i++){
            Log.e("Hello","Quang - "+ arrNumberCorrect.get(i));
        }
        for(int i = 0; i<arrNumberCorrect.size() ; i++){
            Log.e("Hello","Quang list - "+ lisNumber[i]);
        }
        if(0==arrNumberCorrect.get(0)
                &&1==arrNumberCorrect.get(1)
                &&2==arrNumberCorrect.get(2)
                &&3==arrNumberCorrect.get(3)){
            return true;
        }
        return false;
    }
    private boolean isAllCheck(){
        if(tagA&&tagB&&tagC&&tagD){
            return true;
        }else {
            return false;
        }
    }
}
