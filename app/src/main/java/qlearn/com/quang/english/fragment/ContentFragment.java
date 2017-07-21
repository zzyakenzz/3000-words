package qlearn.com.quang.english.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import qlearn.com.quang.english.R;
import qlearn.com.quang.english.activity.DetailVocabularyActivity;
import qlearn.com.quang.english.adapter.Vocabulary_List_Adapter;
import qlearn.com.quang.english.model.WordModel;
import qlearn.com.quang.english.util.GeneralUtils;
import yalantis.com.sidemenu.interfaces.ScreenShotable;


/**
 * Created by Konstantin on 22.12.2014.
 */
public class ContentFragment extends Fragment implements ScreenShotable,AdapterView.OnItemClickListener {
    public static final String CLOSE = "Close";

    protected ListView mListView;
    protected int res;
    private View containerView;
    private Bitmap bitmap;
    public static Vocabulary_List_Adapter mAdapter;

    public static ContentFragment newInstance(int resId,Vocabulary_List_Adapter adapter) {
        ContentFragment contentFragment = new ContentFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Integer.class.getName(), resId);
        contentFragment.setArguments(bundle);
        mAdapter = adapter;
        return contentFragment;
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
        View rootView = inflater.inflate(R.layout.fragment_main_vocabulary, container, false);
        mListView  = (ListView)rootView.findViewById(R.id.list_word);
        if(res!=0)
        rootView.setBackgroundColor(res);
        mListView.setAdapter(mAdapter);
        mListView.bringToFront();
        mListView.setOnItemClickListener(this);
        return rootView;
    }

    @Override
    public void getLabel() {

    }

    @Override
    public void takeScreenShot() {
        Thread thread = new Thread() {
            @Override
            public void run() {
//                Bitmap bitmap = Bitmap.createBitmap(containerView.getWidth(),
//                        containerView.getHeight(), Bitmap.Config.ARGB_8888);
//                Canvas canvas = new Canvas(bitmap);
//                containerView.draw(canvas);
//                ContentFragment.this.bitmap = bitmap;

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
//        if (bitmap != null) {
//            bitmap.recycle();
//            bitmap = null;
//        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (view.getId()){
            case R.id.img_speaker:
                break;
            default:
                Intent intent = new Intent(getActivity(), DetailVocabularyActivity.class);
                intent.putExtra("intentWord",(WordModel)mListView.getItemAtPosition(position));
                intent.putExtra("intentRefWord", GeneralUtils.getRefWord(
                        ((WordModel) mListView.getItemAtPosition(position)).getVocabulary(),
                        mAdapter.getList()
                ));
                getActivity().startActivity(intent);
                break;
        }
    }
}

