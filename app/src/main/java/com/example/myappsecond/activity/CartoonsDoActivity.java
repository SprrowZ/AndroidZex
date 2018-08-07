package com.example.myappsecond.activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myappsecond.BaseActivity;
import com.example.myappsecond.EChatApp;
import com.example.myappsecond.GreenDaos.Base.Cartoons;
import com.example.myappsecond.GreenDaos.Base.CartoonsDao;
import com.example.myappsecond.GreenDaos.Base.DaoSession;
import com.example.myappsecond.R;
import com.example.myappsecond.project.catcher.eventbus.MessageEvent;
import com.example.myappsecond.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.greendao.query.Query;

import java.util.Date;
import java.util.List;

/**
 * Created by Zzg on 2018/7/8.
 */
public class CartoonsDoActivity extends BaseActivity implements View.OnClickListener {
    private EditText edit_name;
    private EditText edit_isend;
    private EditText edit_hero;
    private EditText edit_heroine;
    private TextView tShow;
    private DaoSession daoSession;
    private CartoonsDao cartoonsDao;
    private String name;
    private String isend;
    private String hero;
    private String heroine;
    private Query<Cartoons> cartoonsQuery;
    private StringBuilder res = new StringBuilder();
    private Button btn_add;
    private Button btn_show;
    private Button btn_del;
    private Button btn_modify;
    private EditText edit_id;
    private TextView newData;
    private List<Cartoons> dataList;
    /**
     * 判断是隐藏数据还是显示数据
     */
    private boolean SHOW_DATA = true;

    /**
     *
     * 查询是异步的，通过handler
     */
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(final Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 666:
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            List<Cartoons> list= (List<Cartoons>) msg.obj;
                            res.delete(0, res.length());
                            for (Cartoons cartoons : list) {
                                res.append("NAME:" + cartoons.getNAME() + "\t\t\t\t\t\t");
                                res.append("IS_END:" + cartoons.getIS_END() + "\n");
                                res.append("HERO:" + cartoons.getHERO() + "\t\t\t\t\t\t");
                                res.append("HEROINE:" + cartoons.getHEROINE() + "\n\n");
                            }
                            tShow.setText(res);
                        }
                    });
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.greendao_cartoons_do);
        initView();
        daoSession = EChatApp.getInstance().getDaoSession();
        cartoonsDao = daoSession.getCartoonsDao();
        initEvent();
    }

    private void initView() {
        edit_name = findViewById(R.id.edit_name);
        edit_isend = findViewById(R.id.edit_isend);
        edit_isend.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);//只能输入数字或小数
        edit_hero = findViewById(R.id.edit_hero);
        edit_heroine = findViewById(R.id.edit_heroine);
        btn_add = findViewById(R.id.but_add_data);
        btn_del = findViewById(R.id.but_del_data);
        btn_show = findViewById(R.id.but_show_data);
        btn_modify = findViewById(R.id.but_modify_data);
        tShow = (TextView) findViewById(R.id.text_show_data);
        edit_id = findViewById(R.id.edit_id);
        newData = findViewById(R.id.new_data);
        btn_add.setOnClickListener(this);
        btn_del.setOnClickListener(this);
        btn_show.setOnClickListener(this);
        btn_modify.setOnClickListener(this);
    }

    private void initEvent() {
        edit_id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                newData.setVisibility(View.GONE);
                if (s.length() > 0) {
                    edit_name.setText(s.toString());
                } else {

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.but_add_data:
                insert();
                break;
            case R.id.but_show_data://添加隐藏
               // find();
                jumpToList();
                break;
            case R.id.but_del_data:
                delete();
                break;
            case R.id.but_modify_data:
                modify();
                break;
        }
    }

    private void insert() {
        //不能初始化的时候取值，因为这样为空，刚开始是空的。。。
         getTexts();
        if (name.length() != 0) {
            Cartoons cartoons = new Cartoons();
            cartoons.setNAME(name);
            if (isend.equals("0")) {
                cartoons.setIS_END(false);
            } else if (isend.equals("1")) {
                cartoons.setIS_END(true);
            }else {
                cartoons.setIS_END(true);
            }
            cartoons.setHERO(hero);
            cartoons.setHEROINE(heroine);
            Date date=new Date(System.currentTimeMillis());
            cartoons.setINSERT_TIME(date);
            //插入的时候查询一下本地数据库看有没有已经存在的数据，根据Name查询
            cartoonsDao.insert(cartoons);//插入
        } else {
            ToastUtils.shortMsg("请输入动漫撒~");
        }

        //插入完之后清空一下edittext
        edit_name.setText("");
        edit_isend.setText("");
        edit_hero.setText("");
        edit_heroine.setText("");
        edit_name.requestFocus();
        edit_name.setFocusable(true);//焦点重新

    }

    private void find() {
        //加载个动画
        ObjectAnimator animator;

        //进行判断是显示数据还是隐藏数据
        if (SHOW_DATA) {//如果是显示数据
            tShow.setVisibility(View.VISIBLE);
            animator = ObjectAnimator.ofFloat(tShow, "alpha", 0F, 1F);
            animator.setDuration(1000);
            animator.start();
//            cartoonsQuery = cartoonsDao.queryBuilder().orderAsc(CartoonsDao.Properties.NAME).build();
//            List<Cartoons> list = cartoonsQuery.list();//直接拿到list
            queryByThread();

            SHOW_DATA = false;
        } else {
            animator = ObjectAnimator.ofFloat(tShow, "alpha", 1F, 0F);
            animator.setDuration(2000);
            animator.start();
            tShow.setVisibility(View.GONE);
            SHOW_DATA = true;
        }
    }
    //为了测试EventBus，这里不通过Intent传递数据
    private void jumpToList(){
        EventBus.getDefault().postSticky(new MessageEvent("NewMessage"));
        Intent intent=new Intent(CartoonsDoActivity.this,CartoonsListActivity.class);
        startActivityForResult(intent,CartoonsListActivity.FROM_CARTOON);
    }

    private void getTexts(){
        name = edit_name.getText().toString().trim();
        isend = edit_isend.getText().toString().trim();
        hero = edit_hero.getText().toString().trim();
        heroine = edit_heroine.getText().toString().trim();
    }

    //数据过大时，开启线程进行查询
    private void queryByThread(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (cartoonsQuery!=null){

                }else{
                    cartoonsQuery=cartoonsDao.queryBuilder().orderAsc(CartoonsDao.Properties.NAME).build();
                }

                dataList=cartoonsQuery.forCurrentThread().list();
                for (Cartoons cartoons:dataList){
                    if (cartoons.getINSERT_TIME()==null){
                        Date date=new Date(System.currentTimeMillis());
                        cartoons.setINSERT_TIME(date);
                        cartoonsDao.update(cartoons);
                    }
                }
                //查到数据后异步通知数据
                Message message=new Message();
                message.obj=dataList;
                message.what=666;
                handler.sendMessage(message);
            }
        }).start();

    }

    private void delete(){
        //根据动漫名进行删除操作
        if (edit_id.getText().toString() != null) {
            Cartoons cartoons = daoSession.getCartoonsDao().queryBuilder()
                    .where(CartoonsDao.Properties.NAME.eq(edit_id.getText())).unique();
            if (cartoons != null) {
                cartoons.delete();
                ToastUtils.shortMsg("数据已删除");
            } else {
                ToastUtils.shortMsg("查无此漫");
            }

        } else {
            ToastUtils.shortMsg("妈卖批，输入数据撒！");
        }
    }
    private void modify(){
        //更新数据通过Name查吧
        getTexts();
        if (name.length() != 0 && isend.length() != 0
                && hero.length() != 0 && heroine.length() != 0) {
            Cartoons cartoons1 = daoSession.getCartoonsDao().queryBuilder()
                    .where(CartoonsDao.Properties.NAME.eq(edit_id.getText())).unique();
            cartoons1.setNAME(name);
            cartoons1.setIS_END(Boolean.valueOf(isend));
            cartoons1.setHERO(hero);
            cartoons1.setHEROINE(heroine);
//               insert();
            cartoonsDao.update(cartoons1);
            String newdata = "名字： " + cartoons1.getNAME() + "  完结？" + cartoons1.getIS_END()
                    + "\n" + "男主角：" + cartoons1.getHERO() + "女主角：" + cartoons1.getHEROINE();
            newData.setVisibility(View.VISIBLE);
            newData.setText(newdata);
        } else {
            ToastUtils.shortMsg("龟儿子，输入数据再修改！");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==CartoonsListActivity.FROM_CARTOON){

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
