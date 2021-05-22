package com.rye.catcher.activity;

import android.animation.ObjectAnimator;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.rye.base.BaseActivity;
//import com.rye.catcher.GreenDaos.Base.TB_Cartoons;
//import com.rye.catcher.GreenDaos.Base.DaoSession;
//import com.rye.catcher.GreenDaos.Base.TB_CartoonsDao;
import com.rye.catcher.R;
import com.rye.catcher.ThirdSdk;
import com.rye.catcher.utils.ToastUtils;

//import org.greenrobot.greendao.query.Query;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Zzg on 2018/7/8.
 */
public class CartoonsDoActivity extends BaseActivity {
    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void initEvent() {

    }
//    @BindView(R.id.edit_name)
//    EditText editName;
//    @BindView(R.id.edit_isend)
//    EditText editIsend;
//    @BindView(R.id.edit_hero)
//    EditText editHero;
//    @BindView(R.id.edit_heroine)
//    EditText editHeroine;
//    @BindView(R.id.but_add_data)
//    Button butAddData;
//    @BindView(R.id.but_show_data)
//    Button butShowData;
//    @BindView(R.id.text_show_data)
//    TextView textShowData;
//    @BindView(R.id.but_del_data)
//    Button butDelData;
//    @BindView(R.id.edit_id)
//    EditText editId;
//    @BindView(R.id.but_modify_data)
//    Button butModifyData;
//    @BindView(R.id.new_data)
//    TextView newData;
//
//    private DaoSession daoSession;
//    private TB_CartoonsDao cartoonsDao;
//    private String name;
//    private String isend;
//    private String hero;
//    private String heroine;
//    private Query<TB_Cartoons> cartoonsQuery;
//    private StringBuilder res = new StringBuilder();
//
//    private List<TB_Cartoons> dataList;
//    /**
//     * 判断是隐藏数据还是显示数据
//     */
//    private boolean SHOW_DATA = true;
//
//    /**
//     * 查询是异步的，通过handler，会内存泄漏
//     */
//    private Handler handler = new Handler() {
//        @Override
//        public void handleMessage(final Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what) {
//                case 666:
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            List<TB_Cartoons> list = (List<TB_Cartoons>) msg.obj;
//                            res.delete(0, res.length());
//                            for (TB_Cartoons cartoons : list) {
//                                res.append("NAME:" + cartoons.getNAME() + "\t\t\t\t\t\t");
//                                res.append("IS_END:" + cartoons.getIS_END() + "\n");
//                                res.append("HERO:" + cartoons.getHERO() + "\t\t\t\t\t\t");
//                                res.append("HEROINE:" + cartoons.getHEROINE() + "\n\n");
//                            }
//                            textShowData.setText(res);
//                        }
//                    });
//            }
//        }
//    };
//
//
//    @Override
//    public int getLayoutId() {
//        return R.layout.greendao_cartoons_do;
//    }
//
//    @Override
//    public void initEvent() {
//        ButterKnife.bind(this);
//        daoSession = ThirdSdk.getInstance().getDaoSession();
//        cartoonsDao = daoSession.getTB_CartoonsDao();
//        initEvent();
//        editId.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                newData.setVisibility(View.GONE);
//                if (s.length() > 0) {
//                    editName.setText(s.toString());
//                } else {
//
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//    }
//
//
//    private void insert() {
//        //不能初始化的时候取值，因为这样为空，刚开始是空的。。。
//        getTexts();
//        if (name.length() != 0) {
//            TB_Cartoons cartoons = new TB_Cartoons();
//            cartoons.setNAME(name);
//            if (isend.equals("0")) {
//                cartoons.setIS_END(false);
//            } else if (isend.equals("1")) {
//                cartoons.setIS_END(true);
//            } else {
//                cartoons.setIS_END(true);
//            }
//            cartoons.setHERO(hero);
//            cartoons.setHEROINE(heroine);
//            Date date = new Date(System.currentTimeMillis());
//            cartoons.setINSERT_TIME(date);
//            //插入的时候查询一下本地数据库看有没有已经存在的数据，根据Name查询
//            cartoonsDao.insert(cartoons);//插入
//        } else {
//            ToastUtils.shortMsg("请输入动漫撒~");
//        }
//
//        //插入完之后清空一下edittext
//        editName.setText("");
//        editIsend.setText("");
//        editHero.setText("");
//        editHeroine.setText("");
//        editName.requestFocus();
//        editName.setFocusable(true);//焦点重新
//
//    }
//
//    private void find() {
//        //加载个动画
//        ObjectAnimator animator;
//
//        //进行判断是显示数据还是隐藏数据
//        if (SHOW_DATA) {//如果是显示数据
//            textShowData.setVisibility(View.VISIBLE);
//            animator = ObjectAnimator.ofFloat(textShowData, "alpha", 0F, 1F);
//            animator.setDuration(1000);
//            animator.start();
////            cartoonsQuery = cartoonsDao.queryBuilder().orderAsc(CartoonsDao.Properties.NAME).build();
////            List<Cartoons> list = cartoonsQuery.list();//直接拿到list
//            queryByThread();
//
//            SHOW_DATA = false;
//        } else {
//            animator = ObjectAnimator.ofFloat(textShowData, "alpha", 1F, 0F);
//            animator.setDuration(2000);
//            animator.start();
//            textShowData.setVisibility(View.GONE);
//            SHOW_DATA = true;
//        }
//    }
//
//    private void getTexts() {
//        name = editName.getText().toString().trim();
//        isend = editIsend.getText().toString().trim();
//        hero = editHero.getText().toString().trim();
//        heroine = editHeroine.getText().toString().trim();
//    }
//
//    //数据过大时，开启线程进行查询
//    private void queryByThread() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                if (cartoonsQuery != null) {
//
//                } else {
//                    cartoonsQuery = cartoonsDao.queryBuilder().orderAsc(TB_CartoonsDao.Properties.NAME).build();
//                }
//
//                dataList = cartoonsQuery.forCurrentThread().list();
//                for (TB_Cartoons cartoons : dataList) {
//                    if (cartoons.getINSERT_TIME() == null) {
//                        Date date = new Date(System.currentTimeMillis());
//                        cartoons.setINSERT_TIME(date);
//                        cartoonsDao.update(cartoons);
//                    }
//                }
//                //查到数据后异步通知数据
//                Message message = new Message();
//                message.obj = dataList;
//                message.what = 666;
//                handler.sendMessage(message);
//            }
//        }).start();
//
//    }
//
//    private void delete() {
//        //根据动漫名进行删除操作
//        if (editId.getText().toString() != null) {
//            TB_Cartoons cartoons = daoSession.getTB_CartoonsDao().queryBuilder()
//                    .where(TB_CartoonsDao.Properties.NAME.eq(editId.getText())).unique();
//            if (cartoons != null) {
//                cartoons.delete();
//                ToastUtils.shortMsg("数据已删除");
//            } else {
//                ToastUtils.shortMsg("查无此漫");
//            }
//
//        } else {
//            ToastUtils.shortMsg("妈卖批，输入数据撒！");
//        }
//    }
//
//    private void modify() {
//        //更新数据通过Name查吧
//        getTexts();
//        if (name.length() != 0 && isend.length() != 0
//                && hero.length() != 0 && heroine.length() != 0) {
//            TB_Cartoons cartoons1 = daoSession.getTB_CartoonsDao().queryBuilder()
//                    .where(TB_CartoonsDao.Properties.NAME.eq(editId.getText())).unique();
//            cartoons1.setNAME(name);
//            cartoons1.setIS_END(Boolean.valueOf(isend));
//            cartoons1.setHERO(hero);
//            cartoons1.setHEROINE(heroine);
////               insertBySqlite();
//            cartoonsDao.update(cartoons1);
//            String newdata = "名字： " + cartoons1.getNAME() + "  完结？" + cartoons1.getIS_END()
//                    + "\n" + "男主角：" + cartoons1.getHERO() + "女主角：" + cartoons1.getHEROINE();
//            newData.setVisibility(View.VISIBLE);
//            newData.setText(newdata);
//        } else {
//            ToastUtils.shortMsg("龟儿子，输入数据再修改！");
//        }
//    }
//
//
//    @OnClick({R.id.but_add_data, R.id.but_show_data, R.id.but_del_data, R.id.but_modify_data})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.but_add_data:
//                insert();
//                break;
//            case R.id.but_show_data:
//
//                break;
//            case R.id.but_del_data:
//                delete();
//                break;
//            case R.id.but_modify_data:
//                modify();
//                break;
//        }
//    }
}
