package com.rye.catcher.project.ctmviews;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.rye.catcher.R;

/**
 * Created by ZZG on 2018/4/28.
 */

public class GestureDetectors extends Activity implements View.OnTouchListener,GestureDetector.OnDoubleTapListener {
    private GestureDetector mGestureDetector;
    private TextView text;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bcustom_gesturedetectors);
        mGestureDetector=new GestureDetector(this,new mGestureListener());
text=findViewById(R.id.text);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
//onDoubleTapListener,三个
    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        Toast.makeText(GestureDetectors.this,"onSingleTapConfirmed...",Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Toast.makeText(GestureDetectors.this,"onDoubleTap...",Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        Toast.makeText(GestureDetectors.this,"onDoubleTapEvent...",Toast.LENGTH_SHORT).show();
        return false;
    }

    private class  mGestureListener implements GestureDetector.OnGestureListener{

        @Override
        public boolean onDown(MotionEvent e) {
            Toast.makeText(GestureDetectors.this,"onDown...",Toast.LENGTH_SHORT).show();
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {
            Toast.makeText(GestureDetectors.this,"onShowPress...",Toast.LENGTH_SHORT).show();
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Toast.makeText(GestureDetectors.this,"onSingleTapUp...",Toast.LENGTH_SHORT).show();
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Toast.makeText(GestureDetectors.this,"onScroll...",Toast.LENGTH_SHORT).show();
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            Toast.makeText(GestureDetectors.this,"onLongPress...",Toast.LENGTH_SHORT).show();
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Toast.makeText(GestureDetectors.this,"onFling...",Toast.LENGTH_SHORT).show();
            return false;
        }
    }


}
