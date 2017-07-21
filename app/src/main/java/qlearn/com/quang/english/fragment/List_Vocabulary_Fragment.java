package qlearn.com.quang.english.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;
import io.codetail.widget.RevealFrameLayout;
import qlearn.com.quang.english.R;
import qlearn.com.quang.english.activity.HomeActivity;
import qlearn.com.quang.english.adapter.Vocabulary_List_Adapter;
import qlearn.com.quang.english.customview.CustomImageSplash;
import qlearn.com.quang.english.database.Database;
import qlearn.com.quang.english.model.WordModel;
import yalantis.com.sidemenu.interfaces.Resourceble;
import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.model.SlideMenuItem;
import yalantis.com.sidemenu.util.ViewAnimator;

/**
 * Created by quang.nguyen on 04/03/2016.
 */
public class List_Vocabulary_Fragment extends Fragment implements ViewAnimator.ViewAnimatorListener {
    final static AccelerateInterpolator ACCELERATE = new AccelerateInterpolator();
    final static AccelerateDecelerateInterpolator ACCELERATE_DECELERATE = new AccelerateDecelerateInterpolator();
    final static DecelerateInterpolator DECELERATE = new DecelerateInterpolator();
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private List<SlideMenuItem> list = new ArrayList<>();
    private ContentFragment contentFragment;
    private ViewAnimator viewAnimator;
    private int res = R.drawable.content_music;
    private LinearLayout linearLayout;
    private RevealFrameLayout transition_screen;
    // private RevealFrameLayout image_small;
    private CustomImageSplash img_smile;
    int[] color = {R.color.teal_500,R.color.blue_500,R.color.cycan_500,R.color.light_blue_500};
    private Vocabulary_List_Adapter mAdapter;
    ArrayList<WordModel> listWord;
    Database db;
    private  final int MY_DATA_CHECK_CODE = 0;
    Context mContext;
    private LinearLayout layout_sub;
    View v;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        mContext = getActivity().getApplicationContext();
        v = inflater.inflate(R.layout.fragment_list_vocabulary, container, false);
        initView();

        setActionBar();
        createMenuList();
        viewAnimator = new ViewAnimator<>(((ActionBarActivity)getActivity()), list, contentFragment, drawerLayout, this);
        drawerToggle.syncState();
        initSpeech();
        return v;
    }
    private void initSpeech(){
//        Intent checkTTSIntent = new Intent();
//        checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
//        startActivityForResult(checkTTSIntent, MY_DATA_CHECK_CODE);
    }


    private void initView() {
        db = new Database(mContext);
        try {
            db.open();
        }catch (Exception e){

        }
        layout_sub = (LinearLayout)v.findViewById(R.id.layout_sub);
        layout_sub.setBackgroundResource(R.color.voca_screen);
        layout_sub.setVisibility(View.VISIBLE);
        animationTransactionSceen();
        listWord = db.getWord("a");
        mAdapter = new Vocabulary_List_Adapter(getActivity(),R.layout.item_list_vocabulary,listWord);
        contentFragment = ContentFragment.newInstance(0, mAdapter);
        ((HomeActivity)getActivity()).getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, contentFragment)
                .commit();
        drawerLayout = (DrawerLayout) v.findViewById(R.id.drawer_layout);
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        linearLayout = (LinearLayout) v.findViewById(R.id.left_drawer);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
            }
        });



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

    private void createMenuList() {
        SlideMenuItem menuItemClose = new SlideMenuItem(ContentFragment.CLOSE, R.drawable.icn_close,"");
        list.add(menuItemClose);
        for(int i = 65; i < 91; i++){
            SlideMenuItem menuItem = new SlideMenuItem(String.valueOf((char)i), 0 , String.valueOf((char)i));
            list.add(menuItem);
        }
//        SlideMenuItem menuItem0 = new SlideMenuItem(ContentFragment.CLOSE, R.drawable.icn_close);
//        list.add(menuItem0);
//        SlideMenuItem menuItem = new SlideMenuItem(ContentFragment.BUILDING, R.drawable.icn_1);
//        list.add(menuItem);
//        SlideMenuItem menuItem2 = new SlideMenuItem(ContentFragment.BOOK, R.drawable.icn_2);
//        list.add(menuItem2);
//        SlideMenuItem menuItem3 = new SlideMenuItem(ContentFragment.PAINT, R.drawable.icn_3);
//        list.add(menuItem3);
//        SlideMenuItem menuItem4 = new SlideMenuItem(ContentFragment.CASE, R.drawable.icn_4);
//        list.add(menuItem4);
//        SlideMenuItem menuItem5 = new SlideMenuItem(ContentFragment.SHOP, R.drawable.icn_5);
//        list.add(menuItem5);
//        SlideMenuItem menuItem6 = new SlideMenuItem(ContentFragment.PARTY, R.drawable.icn_6);
//        list.add(menuItem6);
//        SlideMenuItem menuItem7 = new SlideMenuItem(ContentFragment.MOVIE, R.drawable.icn_7);
//        list.add(menuItem7);
    }

    private void setActionBar() {
        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        toolbar.setTitle("");
        ((HomeActivity)getActivity()).setSupportActionBar(toolbar);
        ((HomeActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((HomeActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle = new ActionBarDrawerToggle(
                ((ActionBarActivity)getActivity()),                  /* host Activity */
                drawerLayout,         /* DrawerLayout object */
                toolbar,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                linearLayout.removeAllViews();
                linearLayout.invalidate();

            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (slideOffset > 0.6 && linearLayout.getChildCount() == 0)
                    viewAnimator.showMenuContent();
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
    }



    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private ScreenShotable replaceFragment(ScreenShotable screenShotable, ContentFragment contentFragment, int topPosition) {

        View view = v.findViewById(R.id.content_frame);
        int finalRadius = Math.max(view.getWidth(), view.getHeight());
        SupportAnimator animator = ViewAnimationUtils.createCircularReveal(view, 0, topPosition, 0, finalRadius);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);

        v.findViewById(R.id.content_overlay).setBackgroundDrawable(new BitmapDrawable(getResources(), screenShotable.getBitmap()));
        animator.start();
//        ContentFragment contentFragment = ContentFragment.newInstance(this.res);
        ((HomeActivity)getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, contentFragment).commit();

        return contentFragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MY_DATA_CHECK_CODE) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                //the user has the necessary data - create the TTS

            }
            else {
                //no data - install it now
//                Intent installTTSIntent = new Intent();
//                installTTSIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
//                startActivity(installTTSIntent);
            }
        }
    }

    @Override
    public ScreenShotable onSwitch(Resourceble slideMenuItem, ScreenShotable screenShotable, int position) {
        switch (slideMenuItem.getName()) {
            case ContentFragment.CLOSE:
                return screenShotable;
            default:
                //listWord.clear();
                listWord = db.getWord(slideMenuItem.getName().toLowerCase());
                Log.e("TAG","LIST_VOCA "+ listWord.size());
                mAdapter.clear();;
                mAdapter.addAll(listWord);
                mAdapter.notifyDataSetChanged();
                int index = new Random().nextInt(4);

                int colorScreen = ContextCompat.getColor(getContext(), color[index]);
                ContentFragment contentFragment = ContentFragment.newInstance(colorScreen,mAdapter);
                return replaceFragment(screenShotable, contentFragment, position);
        }
    }

    @Override
    public void disableHomeButton() {
        ((HomeActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(false);

    }

    @Override
    public void enableHomeButton() {
        ((HomeActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        drawerLayout.closeDrawers();

    }


    @Override
    public void addViewToContainer(View view) {
        linearLayout.addView(view);
    }


}
