package qlearn.com.quang.english.adapter;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import qlearn.com.quang.english.R;
import qlearn.com.quang.english.customview.CustomRippleLayout;
import qlearn.com.quang.english.customview.TextRoboThin;
import qlearn.com.quang.english.util.Contanst;

/**
 * Created by quang.nguyen on 25/03/2016.
 */
public class Listen_List_Adapter extends ArrayAdapter {
    View v;
    Context mContext;
    ArrayList<String> objects;
    LayoutInflater inflater;
    HolderView mHolderView;
    MediaPlayer mediaPlayer;
    Activity mActivity;
    int res;
    public Listen_List_Adapter(Context context, int resource, ArrayList<String> objects) {
        super(context, resource, objects);
        this.objects = objects;
        inflater = LayoutInflater.from(context);
        mContext = context;
        res = resource;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        v = convertView;
        if(v == null){
            v = inflater.inflate(res, parent, false);
            mHolderView = new HolderView();
            mHolderView.txt_phrase = (TextRoboThin)v.findViewById(R.id.txt_phrase);
            mHolderView.rippleLayout = (CustomRippleLayout)v.findViewById(R.id.ripple_layout);

            v.setTag(mHolderView);
        }else{
            mHolderView = (HolderView)v.getTag();
        }
        mHolderView.txt_phrase.setText(objects.get(position));
        mHolderView.rippleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri myUri = Uri.parse(Contanst.URL_MP3);
                try {
                    killMediaPlayer();
//                    mediaPlayer = new MediaPlayer();
                    int resID=mContext.getResources().getIdentifier(
                            generateSaveMp3(objects.get(position)), "raw", mContext.getPackageName());
                    mediaPlayer = MediaPlayer.create(mContext,resID);

                   // mediaPlayer.setDataSource(generateURL(objects.get(position)));
                  //  mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                   // mediaPlayer.prepare(); //don't use prepareAsync for mp3 playback
                    mediaPlayer.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return v;
    }
    public void killMediaPlayer() {
        if(mediaPlayer!=null) {
            try {
               // mediaPlayer.release();
                mediaPlayer.stop();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
    private String generateSaveMp3(String root){
        String url = "";
        String[] temp = {",",".","?","'","!","-",":","\""};
        for(int i = 0; i < temp.length; i++){
            if(root.contains(temp[i])){
                root = root.replace(temp[i],"");
            }
        }

        String[] split = root.split(" ");
        for(int i=0 ; i<split.length;i++){
            if(i==0){
                url+= split[i].toLowerCase();
            }else{
                char[] stringArray = split[i].trim().toCharArray();
                String str = "";
                for(int j=0; j < stringArray.length;j++){
                    if(j == 0){
                        stringArray[0] = Character.toLowerCase(stringArray[0]);
                        str += stringArray[0];
                    }else{
                        str += stringArray[j];
                    }

                }

                url += str;
            }
        }

        return url;
    }
    private String generateURL(String root){
        String url = Contanst.URL_MP3;
        String up = "";
        String[] temp = {",",".","?","'"};
        for(int i = 0; i < temp.length; i++){
            if(root.contains(temp[i])){
                root = root.replace(temp[i],"");
            }
        }

        String[] split = root.split(" ");
        for(int i=0 ; i<split.length;i++){
            if(i==0){
                url+= split[i].toLowerCase();
            }else{
                char[] stringArray = split[i].trim().toCharArray();
                String str = "";
                for(int j=0; j < stringArray.length;j++){
                    if(j == 0){
                        stringArray[0] = Character.toUpperCase(stringArray[0]);
                        str += stringArray[0];
                    }else{
                        str += stringArray[j];
                    }

                }

                url += str;
            }
        }
        return url+".mp3";
    }
    private class HolderView{
        TextRoboThin txt_phrase;
        CustomRippleLayout rippleLayout;
    }
    public static String removechar(String fromString, Character character) {
        int indexOf = fromString.indexOf(character);
        if(indexOf==-1)
            return fromString;
        String front = fromString.substring(0, indexOf);
        String back = fromString.substring(indexOf+1, fromString.length());
        return front+back;
    }
}
