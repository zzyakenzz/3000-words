package qlearn.com.quang.english.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import qlearn.com.quang.english.R;
import qlearn.com.quang.english.customview.CustomRippleLayout;
import qlearn.com.quang.english.model.WordModel;

/**
 * Created by quang.nguyen on 16/03/2016.
 */
public class Vocabulary_List_Adapter extends ArrayAdapter<WordModel>{
    ArrayList<WordModel> objects;
    Context mContext;
    LayoutInflater inflater;
    View v;
    TextToSpeech tts;
    HolderView mHolderView;
    int resource;

    public Vocabulary_List_Adapter(Context context, int resource, ArrayList<WordModel> objects) {
        super(context, resource, objects);
        this.objects = objects;
        mContext = context;
        inflater = LayoutInflater.from(context);
        this.resource = resource;
        tts = new TextToSpeech(mContext, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    if(tts.isLanguageAvailable(Locale.UK)==TextToSpeech.LANG_AVAILABLE)
                        tts.setLanguage(Locale.UK);
                } else if(status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.UK);
                    Toast.makeText(mContext, "Sorry! Text To Speech failed...", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        v = convertView;
        if(v == null){
            mHolderView = new HolderView();
            v = this.inflater.inflate(resource,parent,false);
//            v.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(mContext, DetailVocabularyActivity.class);
//                    intent.putExtra("itentWord",objects.get(position));
//                    mContext.startActivity(intent);
//                }
//            });
            mHolderView.img_speaker = (ImageView)v.findViewById(R.id.img_speaker);
            //mHolderView.img_bookmark = (ImageView)v.findViewById(R.id.img_bookmark);
            mHolderView.index = (TextView)v.findViewById(R.id.index);
            mHolderView.txt_pronouce = (TextView)v.findViewById(R.id.txt_pronouce);
            mHolderView.txt_vocabulary = (TextView)v.findViewById(R.id.txt_vocabulary);
            mHolderView.txt_wordform = (TextView)v.findViewById(R.id.txt_wordform);
            mHolderView.ripple_speech = (CustomRippleLayout)v.findViewById(R.id.ripple_speech);

            v.setTag(mHolderView);
        }else{
            mHolderView = (HolderView)v.getTag();


        }
        mHolderView.txt_vocabulary.setText(objects.get(position).getVocabulary());
        mHolderView.txt_pronouce.setText(objects.get(position).getPronounce());
        mHolderView.txt_wordform.setText(objects.get(position).getWordForm());
        mHolderView.index.setText(String.valueOf(position + 1));
        mHolderView.ripple_speech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Hello", Toast.LENGTH_LONG).show();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ttsGreater21(objects.get(position).getVocabulary().trim());
                } else {
                    ttsUnder20(objects.get(position).getVocabulary().trim());
                }
            }
        });
        return v;
    }
    @SuppressWarnings("deprecation")
    private void ttsUnder20(String text) {
        HashMap<String, String> map = new HashMap<>();
        map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "MessageId");
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, map);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void ttsGreater21(String text) {
        String utteranceId=this.hashCode() + "";
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId);
    }
    private class HolderView{
        TextView txt_vocabulary;
        TextView txt_pronouce;
        TextView txt_wordform;
        ImageView img_bookmark;
        ImageView img_speaker;
        TextView index;
        CustomRippleLayout ripple_speech;

    }
    public ArrayList<WordModel> getList(){
        return objects;
    }
}
