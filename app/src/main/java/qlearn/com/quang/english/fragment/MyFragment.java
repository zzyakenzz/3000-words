package qlearn.com.quang.english.fragment;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import qlearn.com.quang.english.R;
import yalantis.com.sidemenu.interfaces.ScreenShotable;


/**
 * Created by quang.nguyen on 06/01/2016.
 */
public class MyFragment extends ContentFragment implements ScreenShotable {


    protected int res;
    private View containerView;
    private Bitmap bitmap;

    public static MyFragment newInstance(int resId) {
        MyFragment myFragment = new MyFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Integer.class.getName(), resId);
        myFragment.setArguments(bundle);
        return myFragment;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.containerView = view.findViewById(R.id.container);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        res = getArguments().getInt(Integer.class.getName());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.myfragment, container, false);
//        mImageView = (ImageView) rootView.findViewById(R.id.image_content);
//        mImageView.setClickable(true);
//        mImageView.setFocusable(true);
//        mImageView.setImageResource(res);
        return rootView;
    }

    @Override
    public void takeScreenShot() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                Bitmap bitmap = Bitmap.createBitmap(containerView.getWidth(),
                        containerView.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                containerView.draw(canvas);
                MyFragment.this.bitmap = bitmap;

            }
        };

        thread.start();

    }

    @Override
    public Bitmap getBitmap() {
        return this.bitmap;

    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (bitmap != null) {
            bitmap.recycle();
            bitmap = null;
        }
    }

}
