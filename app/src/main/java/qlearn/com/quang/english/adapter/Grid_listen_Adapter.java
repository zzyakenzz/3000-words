package qlearn.com.quang.english.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import qlearn.com.quang.english.R;

/**
 * Created by quang.nguyen on 25/03/2016.
 */
public class Grid_listen_Adapter extends ArrayAdapter{
    Context mContext;
    int res;
    ArrayList<String> objects;
    View v;
    HolderView mHolderView;
    LayoutInflater inflater;
    public Grid_listen_Adapter(Context context, int resource, ArrayList<String> objects) {
     super(context, resource, objects);
        mContext = context;
        res = resource;
        this.objects = objects;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        v = convertView;
        if(v == null){
            v = inflater.inflate(res,parent,false);
            mHolderView = new HolderView();
            mHolderView.txt_alphabetical = (TextView)v.findViewById(R.id.txt_alphabetical);
            v.setTag(mHolderView);
        }else{
            mHolderView = (HolderView)v.getTag();
        }
        mHolderView.txt_alphabetical.setText(objects.get(position).toUpperCase());
        int R = (int)(Math.random()*256);
        int G = (int)(Math.random()*256);
        int B= (int)(Math.random()*256);
        int color = Color.rgb(R, G, B);
        mHolderView.txt_alphabetical.setBackgroundColor(color);



        return v;
    }
    private class HolderView{
        TextView txt_alphabetical;
    }

}
