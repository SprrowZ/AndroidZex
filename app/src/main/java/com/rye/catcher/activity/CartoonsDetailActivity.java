package com.rye.catcher.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.widget.TextView;

import com.rye.catcher.BaseOldActivity;
import com.rye.catcher.R;
import com.dawn.zgstep.ui.ctm.views.CircleRectView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ZZG on 2018/8/19.
 */
public class CartoonsDetailActivity extends BaseOldActivity {
    /**
     * 属性集合
     */
    public static final String CARTOON_NAME="NAME";
    public static final String CARTOON_HEROS="HEROS";
    public static final String CARTOON_DIRECTOR="DIRECTOR";
    public static final String CARTOON_IS_END="IS_END";
    public static final String CARTOON_ACTORS="ACTORS";
    public static final String CARTOON_PLOT="PLOT";
    public static final String CARTOON_START_TIME="START_TIME";
    public static final String CARTOON_LIST_ACTOR="CARTOON_LIST_ACTOR";
    private String name;
    private String heros;
    private String director;
    private boolean isEnd;
    private String actors;
    private String plot;
    private String startTime;
    @BindView(R.id.header_img)
    CircleRectView headerImg;
    @BindView(R.id.top_name)
    TextView topName;
    @BindView(R.id.cartoon_name)
    TextView cartoonName;
    @BindView(R.id.cartoon_hero)
    TextView cartoonHero;
    @BindView(R.id.cartoon_director)
    TextView cartoonDirector;
    @BindView(R.id.cartoon_is_end)
    TextView cartoonIsEnd;
    @BindView(R.id.cartoon_actors)
    TextView cartoonActors;
    @BindView(R.id.details)
    TextView details;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cartoon_details);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
         Bundle bundle=getIntent().getExtras();
         name=bundle.getString(CartoonsDetailActivity.CARTOON_NAME,"");
         heros=bundle.getString(CartoonsDetailActivity.CARTOON_HEROS,"");
         director=bundle.getString(CartoonsDetailActivity.CARTOON_DIRECTOR,"");
         isEnd=bundle.getBoolean(CartoonsDetailActivity.CARTOON_IS_END,true);
         actors=bundle.getString(CartoonsDetailActivity.CARTOON_ACTORS,"");
         plot=bundle.getString(CartoonsDetailActivity.CARTOON_PLOT,"");
         startTime=bundle.getString(CartoonsDetailActivity.CARTOON_START_TIME,"");

    }

    @OnClick(R.id.header_img)
    public void onViewClicked() {
    }
}
