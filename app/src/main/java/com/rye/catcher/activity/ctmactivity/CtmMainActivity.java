package com.rye.catcher.activity.ctmactivity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.rye.catcher.BaseActivity;
import com.rye.catcher.project.ctmviews.dellist.DelMainActivity;
import com.rye.catcher.project.ctmviews.Mypractice.PracticeWave;
import com.rye.catcher.R;

/**
 * Created by ZZG on 2017/11/9.
 */

public class CtmMainActivity extends BaseActivity implements View.OnClickListener {
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;
    private Button btn7;
    private Button btn8;
    private Button btn9;
    private Button btn10;
    private Button btn11;
    private Button btn12;
    private Button btn13;
    private Button btn14;
    private LinearLayout container;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bcustom_main);
        btn1=findViewById(R.id.orr);
        btn2=findViewById(R.id.javaMore);
        btn3=findViewById(R.id.translate);
        btn4=findViewById(R.id.retrofit);
        btn5=findViewById(R.id.btn5);
        btn6=findViewById(R.id.btn6);
        btn7=findViewById(R.id.btn7);
        btn8=findViewById(R.id.btn8);
        btn9=findViewById(R.id.btn9);
        btn10=findViewById(R.id.btn10);
        btn11=findViewById(R.id.btn11);
        btn12=findViewById(R.id.btn12);
        btn13=findViewById(R.id.btn13);
        btn14=findViewById(R.id.btn14);
        container=findViewById(R.id.container);



        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btn10.setOnClickListener(this);
        btn11.setOnClickListener(this);
        btn12.setOnClickListener(this);
        btn13.setOnClickListener(this);
        btn14.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.orr:
                Intent intent1=new Intent(this,CtmFirstActivity.class);
                startActivity(intent1);
                break;
            case R.id.javaMore:
                Intent intent2=new Intent(this,CtmSecondActivity.class);
                startActivity(intent2);
                break;
            case R.id.translate:
                Intent intent3=new Intent(this,CtmThirdActivity.class);
                startActivity(intent3);
                break;
            case R.id.retrofit:
                Intent intent4=new Intent(this,CtmFivthActivity.class);
                startActivity(intent4);
                break;
            case R.id.btn5:
                startActivity(new Intent(this,CtmSixthActivity.class));
                break;
            case R.id.btn6:
                startActivity(new Intent(this,DelMainActivity.class));
                break;
            case R.id.btn7:
                startActivity(new Intent(this,CtmSeventhActivity.class));
                break;
            case R.id.btn8:
                //startActivity(new Intent(this, RoundMainActivity.class));
                break;
            case R.id.btn9:
                LayoutInflater inflater=LayoutInflater.from(this);
                View view=inflater.inflate(R.layout.bcustom_practicefirst,null);
                container.removeAllViews();
                container.addView(view);
                break;
            case R.id.btn10:
                LayoutInflater inflater2=LayoutInflater.from(this);
                View view2=inflater2.inflate(R.layout.bcustom_practicefont,null);
                //让波浪动起来
                PracticeWave wave=view2.findViewById(R.id.wave);
                wave.startAnim();
                container.removeAllViews();
                container.addView(view2);
                break;
            case R.id.btn11:
//                LayoutInflater inflater1= LayoutInflater.from(this);
//                View view3=inflater1.inflate(R.layout.bcustom_practicefont,null);
// //               PracticeWave wave2=view3.findViewById(R.id.wave);
////                wave2.setVisibility(View.GONE);
//                Custom_setXfermode first=view3.findViewById(R.id.XfermodeFirst);
//
//                container.removeAllViews();
//                container.addView(view3);
//                first.setVisibility(View.VISIBLE);
                break;
            case R.id.btn12:
//                LayoutInflater inflater3= LayoutInflater.from(this);
//                View view4=inflater3.inflate(R.layout.bcustom_practicefont,null);
//                Custom_setXfermode2 fives=view4.findViewById(R.id.XfermodeSecond);
//
////                PracticeWave wave3=view4.findViewById(R.id.wave);
////                Custom_setXfermode first=view4.findViewById(R.id.XfermodeFirst);
////                first.setVisibility(View.GONE);
////                wave3.setVisibility(View.GONE);
//                container.removeAllViews();
//                container.addView(view4);
//                fives.setVisibility(View.VISIBLE);
                break;
            case R.id.btn13:
//                LayoutInflater inflater5= LayoutInflater.from(this);
//                View view5=inflater5.inflate(R.layout.bcustom_practicefont,null);
////                PracticeWave wave5=view5.findViewById(R.id.wave);
////                Custom_setXfermode five=view5.findViewById(R.id.XfermodeFirst);
////                Custom_setXfermode2 fives=view5.findViewById(R.id.XfermodeSecond);
////                fives.setVisibility(View.GONE);
////                five.setVisibility(View.GONE);
////                wave5.setVisibility(View.GONE);
//                Custom_setXfermode3 six=view5.findViewById(R.id.XfermodeThird);
//
//                container.removeAllViews();
//                container.addView(view5);
//                six.setVisibility(View.VISIBLE);
                break;
            case  R.id.btn14:
                startActivity(new Intent(this,CtmMain2Activity.class));
                break;
            default:
                break;
        }
    }
}
