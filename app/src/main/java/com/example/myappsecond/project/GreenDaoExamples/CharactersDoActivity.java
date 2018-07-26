package com.example.myappsecond.project.GreenDaoExamples;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myappsecond.BaseActivity;
import com.example.myappsecond.EChatApp;
import com.example.myappsecond.GreenDaos.Base.Cartoons;
import com.example.myappsecond.GreenDaos.Base.CartoonsDao;
import com.example.myappsecond.GreenDaos.Base.Character;
import com.example.myappsecond.GreenDaos.Base.CharacterDao;
import com.example.myappsecond.GreenDaos.Base.DaoSession;
import com.example.myappsecond.R;

import java.util.List;

/**
 * Created by ZZG on 2018/7/24.
 */
public class CharactersDoActivity extends BaseActivity implements View.OnClickListener {
    private EditText edit_name;
    private EditText edit_sex;
    private EditText edit_cartoon_name;
    private EditText edit_nationality;
    private EditText edit_cartoon_id;
    private Button btn_add;
    private Button btn_show;
    private Button btn_del;
    private Button btn_modify;
    private TextView tShow;
    private String name;
    private String sex;
    private String cartoonName;
    private String cartoonId;
    private String nationality;
    private DaoSession daoSession;
    private CartoonsDao cartoonsDao;
    private CharacterDao characterDao;
    private StringBuilder res = new StringBuilder();

    /**
     * 判断是隐藏数据还是显示数据
     */
    private boolean SHOW_DATA = true;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(final Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 666:
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            edit_cartoon_id.setText(msg.obj.toString());
                        }
                    });
                break;
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.greendao_characters_do);
     initView();
     initEvent();
    }

    private void initEvent() {
        daoSession= EChatApp.getInstance().getDaoSession();//获取session实例
        cartoonsDao=daoSession.getCartoonsDao();
        characterDao=daoSession.getCharacterDao();
//listener
        btn_add.setOnClickListener(this);
        btn_show.setOnClickListener(this);
        btn_modify.setOnClickListener(this);
        btn_del.setOnClickListener(this);


        edit_cartoon_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
              String condition=s.toString().trim();
             if (s.length()>0){
                 Cartoons cartoons=cartoonsDao.queryBuilder()
                         .where(CartoonsDao.Properties.NAME.eq(condition)).unique();
                 if (cartoons!=null){//如果查询到了数据
                     Message message=new Message();
                     String id=cartoons.getID().toString();
                     message.obj=id;
                     message.what=666;
                     handler.sendMessage(message);
                 }


             }else{

             }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initView() {
        edit_name=findViewById(R.id.edit_name);
        edit_sex=findViewById(R.id.edit_sex);
        edit_cartoon_name=findViewById(R.id.edit_cartoon_name);
        edit_nationality=findViewById(R.id.edit_nationality);
        edit_cartoon_id=findViewById(R.id.edit_cartoon_id);
        btn_add=findViewById(R.id.but_add_data);
        btn_del=findViewById(R.id.but_del_data);
        btn_modify=findViewById(R.id.but_modify_data);
        btn_show=findViewById(R.id.but_show_data);
        tShow = (TextView) findViewById(R.id.text_show_data);
    }

    private void getTexts(){
    name= edit_name.getText().toString().trim();
    sex= edit_sex.getText().toString().trim();
     cartoonName=edit_cartoon_name.getText().toString().trim();
    cartoonId= edit_cartoon_id.getText().toString().trim();
     nationality=edit_nationality.getText().toString();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.but_add_data:
                getTexts();
                Character character=new Character();
                character.setNAME(name);
                if (sex.length()>0&&sex.equals("男")){
                    character.setSEX(true);
                }else if (sex.length()>0&&sex.equals("女")){
                    character.setSEX(false);
                }
                character.setCARTOON_NAME(cartoonName);
                character.setCARTOON_ID(Long.valueOf(cartoonId));
                character.setNATIONALITY(nationality);
                characterDao.insertOrReplace(character);//插入数据
                break;
            case R.id.but_show_data:
                //搜索数据
                find();
                break;
            case R.id.but_modify_data:
                break;
            case R.id.but_del_data:
                break;
        }
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
            List<Character> characterList=characterDao.queryBuilder().
                    orderAsc(CharacterDao.Properties.CARTOON_ID).build().list();
            res.delete(0, res.length());
            for (Character character : characterList) {
                res.append("Name:" + character.getNAME() + "\t\t\t\t\t\t");
                res.append("Sex:" + character.getSEX() + "\n");
                res.append("CartoonName:" + character.getCARTOON_NAME()+ "\t\t\t\t\t\t");
                res.append("CartoonId:" + character.getCARTOON_ID() + "\n");
                res.append("\t\t\t\t\t\t"+"Nationality"+character.getNATIONALITY()+"\n\n");
            }
            tShow.setText(res);
            SHOW_DATA = false;
        } else {
            animator = ObjectAnimator.ofFloat(tShow, "alpha", 1F, 0F);
            animator.setDuration(2000);
            animator.start();
            tShow.setVisibility(View.GONE);
            SHOW_DATA = true;
        }
    }
}
