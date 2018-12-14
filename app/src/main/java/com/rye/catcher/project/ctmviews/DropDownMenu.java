package com.rye.catcher.project.ctmviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.rye.catcher.R;

/**
 * Created at 2018/12/14.
 *
 * @author Zzg
 */
public class DropDownMenu extends LinearLayout {
    //顶部菜单布局
    private LinearLayout tabMenuView;
    //底部容器 包含内容区域 遮罩区域 菜单弹出区域
    private FrameLayout containerViewFrameLayout;
    //内容区域
    private View contentView;
    //遮罩区域
    private View maskView;
    //菜单弹出区域
    private FrameLayout popupMenuViews;

    //属性值
    private int underlineColor;
    private int dividerColor;
    private int textSelectedColor;
    private int textUnSelectedColor;
    private int menuBackgroundColor;
    private int maskColor;
    private float menuTextSize;
    private int menuSelectedIcon;
    private int menuUnSelectedIcon;
    public DropDownMenu(Context context) {
        super(context,null);
    }

    public DropDownMenu(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs,0);
    }

    public DropDownMenu(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init( context) ;
    }
   private void init(Context context){
        //垂直方向
        setOrientation(VERTICAL);
        //获取属性
       TypedArray ta=context.obtainStyledAttributes(R.styleable.dropDownMenu);
       underlineColor=ta.getColor(R.styleable.dropDownMenu_underlineColor,
               context.getResources().getColor(R.color.soft7));
       dividerColor=ta.getColor(R.styleable.dropDownMenu_dividerColor,
               context.getResources().getColor(R.color.soft12));
       textSelectedColor=ta.getColor(R.styleable.dropDownMenu_textSelectedColor,
               context.getResources().getColor(R.color.black));
       textUnSelectedColor=ta.getColor(R.styleable.dropDownMenu_textUnSelectedColor,
               context.getResources().getColor(R.color.gray_9));
       menuBackgroundColor=ta.getColor(R.styleable.dropDownMenu_menuBackgroundColor,
               context.getResources().getColor(R.color.white));
       maskColor=ta.getColor(R.styleable.dropDownMenu_maskColor,
               context.getResources().getColor(R.color.fuckAlpha));
       menuTextSize=ta.getDimension(R.styleable.dropDownMenu_menuTextSize,16f);

       menuSelectedIcon=ta.getResourceId(R.styleable.dropDownMenu_menuSelectedIcon,
               menuSelectedIcon);
       menuUnSelectedIcon=ta.getResourceId(R.styleable.dropDownMenu_menuUnSelectedIcon,
               menuUnSelectedIcon);
       //一定记得回收
       ta.recycle();
   }

}
