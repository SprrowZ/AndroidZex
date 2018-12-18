package com.rye.catcher.project.ctmviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rye.catcher.R;
import com.rye.catcher.utils.DensityUtil;

import java.util.List;

/**
 * Created at 2018/12/14.
 *
 * @author Zzg
 */
public class DropDownMenu extends LinearLayout {
    //popu当前位置,初始未选中
    private int currentPosition=-1;
    //顶部菜单布局
    private LinearLayout tabMenuView;
    //底部容器 包含内容区域 遮罩区域 菜单弹出区域
    private FrameLayout containerView;
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
        this(context,null);
    }

    public DropDownMenu(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DropDownMenu(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init( context,attrs) ;
    }
   private void init(Context context,AttributeSet attrs){
        //垂直方向
        setOrientation(VERTICAL);
        //获取属性
       TypedArray ta=context.obtainStyledAttributes(attrs,R.styleable.dropDownMenu);
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
       initViews(context);
   }
   private void initViews(Context context){
        //上面Tab
       tabMenuView=new LinearLayout(context);
       LayoutParams params=new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
       tabMenuView.setOrientation(HORIZONTAL);
       tabMenuView.setLayoutParams(params);
       addView(tabMenuView,0);//这个0应该就是代表位置吧
       //下划线
       View underLine=new View(context);
       underLine.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
               DensityUtil.dip2px(context,1.0f)));
       underLine.setBackgroundColor(underlineColor);
       addView(underLine,1);
       //内容区域
       containerView=new FrameLayout(context);
       containerView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
               ViewGroup.LayoutParams.MATCH_PARENT));
       addView(containerView,2);
       //where is mask？

   }

    /**
     * 初始化DropDownMenu显示的具体内容
     * @param tabTexts 最上方按钮里的内容
     * @param popuViews 点击按钮之后显示的内容
     * @param contentView 内容区域
     */
   public void setDropDownMenu(List<String> tabTexts,List<View> popuViews,View contentView){
        this.contentView=contentView;
      //tabTexts数量要和popuViews数量一致
       if (tabTexts.size()!=popuViews.size()){
           throw new IllegalArgumentException("pupuViwes.size() should be equal tabTexts.size()");
       }
       //挨个将tabMenuView里的子Item加进来
       for (int i = 0; i <tabTexts.size() ; i++) {
           addTab(tabTexts,i);
       }
       //初始化内容区域！！三个视图在一块，内容视图，mask，弹出框视图！！
       containerView.addView(contentView,0);
       //创建遮罩层
       maskView=new View(getContext());
       maskView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
               ViewGroup.LayoutParams.MATCH_PARENT));
       maskView.setBackgroundColor(maskColor);
       maskView.setOnClickListener(new OnClickListener() {
           @Override
           public void onClick(View v) {
               closeMenu();
           }
       });
       maskView.setVisibility(GONE);
       containerView.addView(maskView,1);
       //创建popuMenu
       popupMenuViews=new FrameLayout(getContext());
       popupMenuViews.setVisibility(GONE);
       for (int i = 0; i <popuViews.size() ; i++) {
           popuViews.get(i).setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                   ViewGroup.LayoutParams.WRAP_CONTENT));
           popupMenuViews.addView(popuViews.get(i),i);
       }
       containerView.addView(popupMenuViews,2);
   }
    /**
     * 构建tabMenuView里的子View
     * @param
     * @param
     */
    private void addTab(List<String> tabTexts, int index) {
        TextView itemView=new TextView(getContext());
        itemView.setSingleLine();
        //超过一行怎么显示
        itemView.setEllipsize(TextUtils.TruncateAt.END);
        itemView.setGravity(Gravity.CENTER);
        //
        itemView.setTextSize(TypedValue.COMPLEX_UNIT_PX,menuTextSize);
        //等比排列
        itemView.setLayoutParams(new LayoutParams(0,ViewGroup.LayoutParams.WRAP_CONTENT,1.0F));
        itemView.setTextColor(textUnSelectedColor);
        //设置右边的图标,可以在上、下、左、右设置图标，如果不想在某个地方显示，则设置为null
        itemView.setCompoundDrawablesWithIntrinsicBounds(null,null,
                getResources().getDrawable(menuUnSelectedIcon),null);

        itemView.setText(tabTexts.get(index));
        //设置内边距，不然都挤在一块难看
        itemView.setPadding(DensityUtil.dip2px(getContext(),5.0f),DensityUtil.dip2px(getContext(),12.0f)
        ,DensityUtil.dip2px(getContext(),5.0f),DensityUtil.dip2px(getContext(),12.0f));

        itemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                switchMenu(itemView);
            }
        });
        //将子Item加进来
        tabMenuView.addView(itemView);
        //加分割线
        if (index<tabTexts.size()-1){
            View divideLine=new View(getContext());
            divideLine.setLayoutParams(new LayoutParams(DensityUtil.dip2px(getContext(),0.5f),
                   ViewGroup.LayoutParams.MATCH_PARENT));
            divideLine.setBackgroundColor(dividerColor);
            tabMenuView.addView(divideLine);
        }

    }
    private void switchMenu(View targetView) {
        for (int i = 0; i <tabMenuView.getChildCount() ; i=i+2) {//每次跳两个，因为竖线也算一个
            if (targetView==tabMenuView.getChildAt(i)){//找到要切换的这个位置
                if (currentPosition==i){//关闭菜单
                    closeMenu();
                }else{//弹出菜单
                    if (currentPosition==-1){//初始状态
                    popupMenuViews.setVisibility(VISIBLE);
                    popupMenuViews.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.popu_open));
                    maskView.setVisibility(VISIBLE);
                    maskView.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.mask_in));
                    //为什么除以2呢，因为pupupMenu里的index和tabMenu里的下标是不是一样的，前者没有下划线。
                    popupMenuViews.getChildAt(i/2).setVisibility(VISIBLE);
                    }else{
                        popupMenuViews.getChildAt(i/2).setVisibility(VISIBLE);
                    }

                    currentPosition=i;
                    ((TextView)tabMenuView.getChildAt(i)).setTextColor(textSelectedColor);
                    ((TextView)tabMenuView.getChildAt(i)).setCompoundDrawablesWithIntrinsicBounds(null,
                            null,getResources().getDrawable(menuSelectedIcon),null);

                }
            }else{//没有点击的其他tabMenu里的子Item也设置一下默认的样式
                popupMenuViews.getChildAt(i/2).setVisibility(GONE);
                ((TextView)tabMenuView.getChildAt(i)).setTextColor(textUnSelectedColor);
                ((TextView)tabMenuView.getChildAt(i)).setCompoundDrawablesWithIntrinsicBounds(null,
                        null,getResources().getDrawable(menuUnSelectedIcon),null);
            }
        }
    }
    //关闭菜单
    private void closeMenu() {
        if (currentPosition!=-1){
            ((TextView)tabMenuView.getChildAt(currentPosition)).setTextColor(textUnSelectedColor);
            ((TextView)tabMenuView.getChildAt(currentPosition)).setCompoundDrawablesWithIntrinsicBounds(null,
                    null,getResources().getDrawable(menuUnSelectedIcon),null);
            popupMenuViews.setVisibility(GONE);
            popupMenuViews.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.popu_close));
            maskView.setVisibility(GONE);
            maskView.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.mask_out));
           currentPosition=-1;//表示没有菜单被选中，=========可以优化吗？
        }
    }

}
