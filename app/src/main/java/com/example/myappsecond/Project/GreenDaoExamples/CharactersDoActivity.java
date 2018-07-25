package com.example.myappsecond.Project.GreenDaoExamples;

import android.app.DownloadManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myappsecond.BaseActivity;
import com.example.myappsecond.EChatApp;
import com.example.myappsecond.GreenDaos.Base.Cartoons;
import com.example.myappsecond.GreenDaos.Base.CartoonsDao;
import com.example.myappsecond.GreenDaos.Base.Character;
import com.example.myappsecond.GreenDaos.Base.CharacterDao;
import com.example.myappsecond.GreenDaos.Base.DaoSession;
import com.example.myappsecond.R;

import org.greenrobot.greendao.query.Query;

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
    private String name;
    private String sex;
    private String cartoonName;
    private String cartoonId;
    private String nationality;
    private DaoSession daoSession;
    private CartoonsDao cartoonsDao;
    private CharacterDao characterDao;
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
                
                break;
            case R.id.but_show_data:
                break;
            case R.id.but_modify_data:
                break;
            case R.id.but_del_data:
                break;
        }
    }
}
