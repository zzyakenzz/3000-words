package qlearn.com.quang.english.activity;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Random;

import qlearn.com.quang.english.R;
import qlearn.com.quang.english.model.WordModel;
import qlearn.com.quang.english.util.GeneralUtils;

/**
 * Created by quang.nguyen on 18/03/2016.
 */
public class DetailVocabularyActivity extends Activity {
    ImageView image_vocabulary;
    TextView txt_detail_vocabulary,txt_detail_wordform,txt_detail_pronouce,txt_detail_translate,
            txt_detail_relate;
    LinearLayout layout_content_des;
    int[] arrColor = new int[]{R.color.brown_500,R.color.blue_500,
            R.color.teal_500,R.color.amber_500,R.color.deep_orange_500,
            R.color.deep_purple_500,R.color.yellow_500,R.color.red_500,
            R.color.orange_500,R.color.green_500,R.color.teal_500,};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_vocabulary);
        initView();
        setData();
    }
    private void initView(){
        image_vocabulary = (ImageView)findViewById(R.id.image_vocabulary);
        //image_vocabulary.getLayoutParams().width = GeneralUtils.getSizeScreenW(this);
        image_vocabulary.getLayoutParams().height = GeneralUtils.getSizeScreenW(this)-GeneralUtils.getSizeScreenW(this)/3;
        txt_detail_vocabulary = (TextView)findViewById(R.id.txt_detail_vocabulary);
        txt_detail_wordform = (TextView)findViewById(R.id.txt_detail_wordform);
        txt_detail_pronouce = (TextView)findViewById(R.id.txt_detail_pronouce);
        txt_detail_translate = (TextView)findViewById(R.id.txt_detail_translate);
        txt_detail_relate = (TextView)findViewById(R.id.txt_detail_relate);
        layout_content_des = (LinearLayout)findViewById(R.id.layout_content_des);



    }
    private String generateURLImage(WordModel wordModel){
        String url = "http://quang3000words.ml/";
        String alpha = String.valueOf(wordModel.getVocabulary().charAt(0));
        if(alpha.toUpperCase().equals("S")){
            if((int)wordModel.getVocabulary().charAt(1)>=((int)(char)('k'))){
                url += "S2/"+wordModel.getVocabulary()+".jpg";
            }else{
                url += "S/"+wordModel.getVocabulary()+".jpg";
            }
        }else{
            url += alpha.toUpperCase()+"/" +wordModel.getVocabulary()+ ".jpg";
        }
        return url;
    }
    private void setData() {
        int color = arrColor[new Random().nextInt(11)];
        Bundle bundle = getIntent().getExtras();
        WordModel wordModel = (WordModel) bundle.getSerializable("intentWord");
        String ref = "";
        ref = bundle.getString("intentRefWord", "");
        txt_detail_vocabulary.setText(wordModel.getVocabulary().toUpperCase());
        txt_detail_vocabulary.setTextColor(ContextCompat.getColor(getApplicationContext(), color));
        txt_detail_wordform.setText(wordModel.getWordForm());
        txt_detail_pronouce.setText(wordModel.getPronounce());
        txt_detail_translate.setText(wordModel.getTranslate());
        txt_detail_relate.setText(ref);

        Picasso.with(this)
                .load(generateURLImage(wordModel))
                .placeholder(R.drawable.load_image) // optional
                .error(R.drawable.error_load)
                .skipMemoryCache()// optional
                .into(image_vocabulary);
        if (wordModel.getDescription() != null) {
            String[] description = wordModel.getDescription().split("~~");

            for (int i = 1; i < description.length; i++) {
                LinearLayout cardView = new LinearLayout(this);
                LinearLayout.LayoutParams prCardView = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                prCardView.setMargins(0, 0, 0, GeneralUtils.dpToPixel(getApplicationContext(), 10));

                cardView.setLayoutParams(prCardView);

                cardView.setBackgroundResource(R.drawable.white_background);
                cardView.setPadding(0,
                        GeneralUtils.dpToPixel(getApplicationContext(), 10),
                        0,
                        GeneralUtils.dpToPixel(getApplicationContext(), 10));
                LinearLayout linearOfCard = new LinearLayout(this);
                linearOfCard.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                linearOfCard.setOrientation(LinearLayout.VERTICAL);
                String[] splitDes = description[i].split("~");
                for (int j = 0; j < splitDes.length; j++) {
                    String title = "";
                    String detail = "";
                    if (j == 0) {
                        title = splitDes[j].toUpperCase();
                        TextView txt_Title = new TextView(this);
                        LinearLayout.LayoutParams pr_txt_Title = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        pr_txt_Title.setMargins(GeneralUtils.dpToPixel(getApplicationContext(), 5),
                                0,
                                GeneralUtils.dpToPixel(getApplicationContext(), 5),
                                0);
                        txt_Title.setLayoutParams(pr_txt_Title);
                        txt_Title.setTypeface(null, Typeface.BOLD);
                        txt_Title.setText(title);
                        txt_Title.setTextColor(ContextCompat.getColor(getApplicationContext(), color));
                        linearOfCard.addView(txt_Title);
                    } else {
                        detail = splitDes[j];
                        TextView txt_Detail = new TextView(this);
                        LinearLayout.LayoutParams pr_txt_Detail = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        pr_txt_Detail.setMargins(GeneralUtils.dpToPixel(getApplicationContext(), 5),
                                0,
                                GeneralUtils.dpToPixel(getApplicationContext(), 10),
                                0);
                        txt_Detail.setLayoutParams(pr_txt_Detail);
                        txt_Detail.setText("- " + detail);
                        txt_Detail.setTextColor(Color.BLACK);
                        linearOfCard.addView(txt_Detail);
                    }
                }
                cardView.addView(linearOfCard);
                layout_content_des.addView(cardView);
            }
        }
    }
}
