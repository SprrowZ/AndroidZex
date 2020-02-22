package com.catcher.zzsdk;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.AutoCompleteTextView;

public class AutoCompletedEditTextStaticCursor extends AutoCompleteTextView {
    private int mCursorColor = Color.BLACK; //Cursor defaults to black
    private

    int mStroke = 5; //Default stroke is 5

    public AutoCompletedEditTextStaticCursor(Context context) {
        super(context);
        setCursorVisible(false);
    }

    public AutoCompletedEditTextStaticCursor(Context context, AttributeSet attrs){
        super(context, attrs);
        setCursorVisible(false);
    }
    public AutoCompletedEditTextStaticCursor(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
        setCursorVisible(false);
    }

    /**
     * Set the cursor color
     * Must have a static int reference.
     * If you wish to use a resource then use the following method
     * int color = getResources().getColor(R.color.yourcolor);
     *
     * Default value Color.BLACK
     * @param color
     */
    public void setCursorColor(int color){ //Cursor defaults to black
        mCursorColor = color;
    }

    /**
     * Set the cursor stroke width
     *
     * Default value is 5
     * @param stroke
     */
    public void setCursorStroke(int stroke){
        mStroke = stroke;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas = drawCursor(canvas);

        super.onDraw(canvas);
    }

    /**
     * Draw a cursor as a simple line,
     * It would be possible to render a drawable if you wanted rounded corners
     * or additional control over cursor
     *
     * @param canvas
     * @return
     */
    private Canvas drawCursor(Canvas canvas) {

        Layout layout = getLayout();

        if (layout == null){
            return canvas;
        }
        //Get the text Height
        float textHeight = getTextSize();
        Paint p = new Paint();
        p.setColor(mCursorColor);
        p.setStrokeWidth(mStroke);
        Log.i("AUTO_EDIT",canvas.getHeight()+","+layout.getHeight());
        canvas.drawLine(0, 10, 0, textHeight+10, p);

        return canvas;
    }

}
