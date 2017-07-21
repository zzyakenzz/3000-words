package qlearn.com.quang.english.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gc.materialdesign.views.LayoutRipple;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import qlearn.com.quang.english.R;
import qlearn.com.quang.english.adapter.Listen_List_Adapter;

/**
 * Created by quang.nguyen on 25/03/2016.
 */
public class DetailListenPhraseActivity extends Activity implements View.OnClickListener{
    private Listen_List_Adapter adapter;
    private ArrayList<String> list;
    private ListView listView;
    private Context mContext;
    String alphabet= "";
    private TextView txt_title_phrase;
    private LayoutRipple ic_arrow_left,ic_settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_listen_phrase);
        initView();
        initData();
    }
    private void initView(){
        listView = (ListView)findViewById(R.id.list_listen_phrase);
        mContext = getApplicationContext();
        txt_title_phrase = (TextView)findViewById(R.id.txt_title_phrase);
        ic_arrow_left = (LayoutRipple)findViewById(R.id.ic_arrow_left);
        ic_settings = (LayoutRipple)findViewById(R.id.ic_settings);
        ic_settings.setOnClickListener(this);
    }
    private void initData(){
        alphabet = getIntent().getStringExtra("list_phrase_fragment");
        list = new ArrayList<>();

        InputStream in = mContext.getResources().openRawResource(R.raw.listenphrase);
        BufferedReader buff = new BufferedReader(new InputStreamReader(in));
        try {
            String line = buff.readLine();
            while (line!=null){
                line = buff.readLine();
                if(alphabet.equals("ALL")){
                    list.add(line);
                }else {
                    if (line.substring(0, 1) != null && alphabet != null && alphabet.equals(line.substring(0, 1))) {
                        list.add(line);
                    }
                }

            }
        }catch (Exception e){

        }
        adapter = new Listen_List_Adapter(getApplicationContext(),R.layout.item_list_listen_phrase,list);
        listView.setAdapter(adapter);
        ic_arrow_left.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ic_arrow_left:
                finish();
                adapter.killMediaPlayer();
                break;
            case R.id.ic_settings:
                Toast.makeText(getApplicationContext(),"Start",Toast.LENGTH_SHORT).show();

                //new AsynDownLoadMp3().execute("");
                break;
            default:
                break;
        }
    }
//    private class AsynDownLoadMp3 extends AsyncTask<String,Integer,String>{
//
//        @Override
//        protected String doInBackground(String... str) {
//            int count;
//
//                for(int i = 0 ; i < list.size(); i++){
//                    try {
//                    URL url = new URL(generateURL(list.get(i)));
//                    URLConnection conexion = url.openConnection();
//                    conexion.connect();
//                    // this will be useful so that you can show a tipical 0-100% progress bar
//                    int lenghtOfFile = conexion.getContentLength();
//
//                    // downlod the file
//                    InputStream input = new BufferedInputStream(url.openStream());
//                    OutputStream output = new FileOutputStream("/sdcard/SaveQuang/"+ generateSaveMp3(list.get(i)));
//
//                    byte data[] = new byte[1024];
//
//                    long total = 0;
//
//                    while ((count = input.read(data)) != -1) {
//                        total += count;
//                        // publishing the progress....
//                        publishProgress((int)(total*100/lenghtOfFile));
//                        output.write(data, 0, count);
//                    }
//                        Log.e("down"," --- "+i);
//                    output.flush();
//                    output.close();
//                    input.close();
//                    } catch (Exception e) {
//                        Log.e("3000words","Error at Quang: "+i);
//                    }
//                }
//
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            Toast.makeText(getApplicationContext(),"Done",Toast.LENGTH_SHORT).show();
//        }
//    }
//    private String generateURL(String root){
//        String url = Contanst.URL_MP3;
//        String[] temp = {",",".","?","'"};
//        for(int i = 0; i < temp.length; i++){
//            if(root.contains(temp[i])){
//                root = root.replace(temp[i],"");
//            }
//        }
//
//        String[] split = root.split(" ");
//        for(int i=0 ; i<split.length;i++){
//            if(i==0){
//                url+= split[i].toLowerCase();
//            }else{
//                char[] stringArray = split[i].trim().toCharArray();
//                String str = "";
//                for(int j=0; j < stringArray.length;j++){
//                    if(j == 0){
//                        stringArray[0] = Character.toUpperCase(stringArray[0]);
//                        str += stringArray[0];
//                    }else{
//                        str += stringArray[j];
//                    }
//
//                }
//
//                url += str;
//            }
//        }
//        return url+".mp3";
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        adapter.killMediaPlayer();
    }
}
