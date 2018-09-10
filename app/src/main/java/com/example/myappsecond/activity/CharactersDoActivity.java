package com.example.myappsecond.activity;

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
import com.example.myappsecond.GreenDaos.Base.TB_Cartoons;
import com.example.myappsecond.GreenDaos.Base.TB_CartoonsDao;
import com.example.myappsecond.GreenDaos.Base.TB_Character;
import com.example.myappsecond.GreenDaos.Base.DaoSession;
import com.example.myappsecond.GreenDaos.Base.TB_CharacterDao;
import com.example.myappsecond.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ZZG on 2018/7/24.
 */
public class CharactersDoActivity extends BaseActivity {
    @BindView(R.id.edit_name)
    EditText editName;
    @BindView(R.id.edit_sex)
    EditText editSex;
    @BindView(R.id.edit_cartoon_name)
    EditText editCartoonName;
    @BindView(R.id.edit_nationality)
    EditText editNationality;
    @BindView(R.id.edit_cartoon_id)
    EditText editCartoonId;
    @BindView(R.id.but_add_data)
    Button butAddData;
    @BindView(R.id.but_show_data)
    Button butShowData;
    @BindView(R.id.text_show_data)
    TextView textShowData;
    @BindView(R.id.but_del_data)
    Button butDelData;
    @BindView(R.id.edit_id)
    EditText editId;
    @BindView(R.id.but_modify_data)
    Button butModifyData;
    @BindView(R.id.new_data)
    TextView newData;

    private String name;
    private String sex;
    private String cartoonName;
    private String cartoonId;
    private String nationality;
    private DaoSession daoSession;
    private TB_CartoonsDao cartoonsDao;
    private TB_CharacterDao characterDao;
    private StringBuilder res = new StringBuilder();

    /**
     * 判断是隐藏数据还是显示数据
     */
    private boolean SHOW_DATA = true;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(final Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 666:
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            editCartoonId.setText(msg.obj.toString());
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
        ButterKnife.bind(this);
        initEvent();
    }

    private void initEvent() {
        daoSession = EChatApp.getInstance().getDaoSession();//获取session实例
        cartoonsDao = daoSession.getTB_CartoonsDao();
        characterDao = daoSession.getTB_CharacterDao();
        editCartoonName.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String condition = s.toString().trim();
                if (s.length() > 0) {
                    TB_Cartoons cartoons = cartoonsDao.queryBuilder()
                            .where(TB_CartoonsDao.Properties.NAME.eq(condition)).unique();
                    if (cartoons != null) {//如果查询到了数据
                        Message message = new Message();
                        String id = cartoons.getID().toString();
                        message.obj = id;
                        message.what = 666;
                        handler.sendMessage(message);
                    }


                } else {

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


    private void getTexts() {
        name = editName.getText().toString().trim();
        sex = editSex.getText().toString().trim();
        cartoonName = editCartoonName.getText().toString().trim();
        cartoonId = editCartoonId.getText().toString().trim();
        nationality = editNationality.getText().toString();
    }


    private void add() {
        getTexts();
        TB_Character character = new TB_Character();
        character.setNAME(name);
        if (sex.length() > 0 && sex.equals("男")) {
            character.setSEX(true);
        } else if (sex.length() > 0 && sex.equals("女")) {
            character.setSEX(false);
        }
        character.setCARTOON_NAME(cartoonName);
        character.setCARTOON_ID(Long.valueOf(cartoonId));
        character.setNATIONALITY(nationality);
        characterDao.insertOrReplace(character);//插入数据
    }

    private void find() {

        //加载个动画
        ObjectAnimator animator;

        //进行判断是显示数据还是隐藏数据
        if (SHOW_DATA) {//如果是显示数据
            textShowData.setVisibility(View.VISIBLE);
            animator = ObjectAnimator.ofFloat(textShowData, "alpha", 0F, 1F);
            animator.setDuration(1000);
            animator.start();
            List<TB_Character> characterList = characterDao.queryBuilder().
                    orderAsc(TB_CharacterDao.Properties.CARTOON_ID).build().list();
            res.delete(0, res.length());
            for (TB_Character character : characterList) {
                res.append("Name:" + character.getNAME() + "\t\t\t\t\t\t");
                res.append("Sex:" + character.getSEX() + "\n");
                res.append("CartoonName:" + character.getCARTOON_NAME() + "\t\t\t\t\t\t");
                res.append("CartoonId:" + character.getCARTOON_ID() + "\n");
                res.append("\t\t\t\t\t\t" + "Nationality" + character.getNATIONALITY() + "\n\n");
            }
            textShowData.setText(res);
            SHOW_DATA = false;
        } else {
            animator = ObjectAnimator.ofFloat(textShowData, "alpha", 1F, 0F);
            animator.setDuration(2000);
            animator.start();
            textShowData.setVisibility(View.GONE);
            SHOW_DATA = true;
        }
    }

    @OnClick({R.id.but_add_data, R.id.but_show_data, R.id.but_del_data, R.id.but_modify_data})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.but_add_data:
                add();
                break;
            case R.id.but_show_data:
                find();
                break;
            case R.id.but_del_data:
                break;
            case R.id.but_modify_data:
                break;
        }
    }
}
