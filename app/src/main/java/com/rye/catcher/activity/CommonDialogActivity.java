package com.rye.catcher.activity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.rye.catcher.BaseActivity;
import com.rye.catcher.R;
import com.rye.base.widget.dialog.ctdialog.ExDialog;
import com.rye.base.widget.dialog.ctdialog.IDialog;
import com.rye.base.widget.dialog.ctdialog.manager.DialogWrapper;
import com.rye.base.widget.dialog.ctdialog.manager.ExDialogManager;
import com.rye.base.utils.DialogUtil;
import com.rye.catcher.utils.ToastUtils;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Created at 2018/10/10.
 *
 * @author Zzg
 */
public class CommonDialogActivity extends BaseActivity {
    private ExDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_dialog);
        init();
    }

    private void init() {

    }

    /**
     * 创建一个最简单的Dialog，指定positive的点击事件即可
     * 需要注意的是这个View 必须传！！！onClick用到的View就是这个View！！！！！！！！！！！！！！！！！！！！！！！！！！！！
     * @param view
     */
    public void showDefaultDialog(View view) {
        DialogUtil.createDefaultDialog(this, "Title", "一杯水是清澈的，而海水却是黑色的。" +
                "就像小的道理可以说明，真正的大道理却是沉默的", "", new IDialog.OnClickListener() {
            @Override
            public void onClick(IDialog dialog) {
                ToastUtils.shortMsg("你微微地笑着，不同我说什么话。而我觉得，为了这个，我已等待得很久了");
                dialog.dismiss();
            }
        });
    }

    /**
     * 最常用的两个点击按钮
     *
     * @param view
     */
    public void showDefaultDialogTwo(View view) {
        DialogUtil.createDefaultDialog(this, "Title", "完全理智的心，恰如一柄全是锋刃的刀，" +
                "会叫使用它的人手上流血", "TryAgain", new IDialog.OnClickListener() {
            @Override
            public void onClick(IDialog dialog) {
                ToastUtils.shortMsg("如果你把所有的失误都关在门外，真理也将被关在门外了。");
                dialog.dismiss();
            }
        }, "GiveUp", new IDialog.OnClickListener() {
            @Override
            public void onClick(IDialog dialog) {
                ToastUtils.shortMsg("逝去的爱，如今已步上高山");
                dialog.dismiss();
            }
        });
    }

    /**
     * 自定义xml
     * @param view
     */
    public void showBaseUseDialog(View view) {
        new ExDialog.Builder(this)
                .setDialogView(R.layout.layout_dialog)
                .setTitle("Title")
                .setAnimStyle(R.style.translate_style)//设置动画
                .setScreenWidthP(0.85f)//设置屏幕宽度比例
                .setGravity(Gravity.CENTER)
                .setWindowBackgroundP(0.2f)//设置背景透明度
                .setCancelable(true)//是否屏蔽物理返回键，true不屏蔽
                .setBuildChildListener(new IDialog.OnBuilderListener() {//子布局操作
                    @Override
                    public void onBuilderChildView(IDialog dialog, View view, int layoutRes) {
                        //dialog: IDialog
                        //view： DialogView
                        //layoutRes :Dialog的资源文件 如果一个Activity里有多个dialog 可以通过layoutRes来区分
                        final EditText editText = view.findViewById(R.id.et_content);
                        Button btn_ok = view.findViewById(R.id.btn_ok);
                        btn_ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String editTextStr = null;
                                if (!TextUtils.isEmpty(editText.getText())) {
                                    editTextStr = editText.getText().toString();
                                }
                                dialog.dismiss();
                                ToastUtils.shortMsg(editTextStr);
                            }
                        });
                    }
                }).show();

    }

    /**
     * 加载条Dialog
     */
    public void showLoadingDialog(View view) {
        DialogUtil.createLoadingDialog(this);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                DialogUtil.closeDialog(CommonDialogActivity.this);
            }
        }, 3000);

    }

    /**
     * 广告Dialog，全屏，伪全屏
     */
    public void showAdDialog(View view){
    new ExDialog.Builder(this)
            .setDialogView(R.layout.layout_ad_dialog)
            .setWindowBackgroundP(0.5f)
            .setBuildChildListener(new IDialog.OnBuilderListener() {
                @Override
                public void onBuilderChildView(IDialog dialog, View view, int layoutRes) {
                    ImageView iv_close = view.findViewById(R.id.iv_close);
                    iv_close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    ImageView iv_ad = view.findViewById(R.id.iv_ad);
                    iv_ad.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                           ToastUtils.shortMsg("点击了广告");
                            dialog.dismiss();
                        }
                    });
                }
            }).show();

    }

    /**
     *底部弹出框，还是很常用的
     */
    public void showBottomDialog(View view){
        new ExDialog.Builder(this)
                .setDialogView(R.layout.layout_bottom_up)
                .setWindowBackgroundP(0.5f)
                .setCancelable(true)
                .setCancelableOutSide(true)
                .setAnimStyle(R.style.AnimUp)
                .setGravity(Gravity.BOTTOM)
                .setScreenWidthP(1.0f)
                .setBuildChildListener(new IDialog.OnBuilderListener() {
                    @Override
                    public void onBuilderChildView(IDialog dialog, View view, int layoutRes) {
                        Button btn_take_photo = view.findViewById(R.id.btn_take_photo);
                        btn_take_photo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ToastUtils.shortMsg("拍照...");
                                dialog.dismiss();
                            }
                        });

                        Button btn_select_photo = view.findViewById(R.id.btn_select_photo);
                        btn_select_photo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                               ToastUtils.shortMsg("...");
                                dialog.dismiss();
                            }
                        });

                        Button btn_cancel_dialog = view.findViewById(R.id.btn_cancel_dialog);
                        btn_cancel_dialog.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ToastUtils.shortMsg("取消...");
                                dialog.dismiss();
                            }
                        });
                    }
                }).show();
    }

    /**
     *分享Dialog
     */
   public void showDialogShare(View view){
         new ExDialog.Builder(this)
                 .setDialogView(R.layout.layout_share)
                 .setWindowBackgroundP(0.5f)
                 .setScreenWidthP(1.0f)
                 .setAnimStyle(R.style.AnimUp)
                 .setCancelableOutSide(true)
                 .setCancelable(true)
                 .setGravity(Gravity.BOTTOM)
                 .setBuildChildListener(new IDialog.OnBuilderListener() {
                     @Override
                     public void onBuilderChildView(IDialog dialog, View view, int layoutRes) {
                         RecyclerView recyclerView =view.findViewById(R.id.recycler_view);
                         recyclerView.setLayoutManager(new LinearLayoutManager(
                                 CommonDialogActivity.this,LinearLayoutManager.HORIZONTAL,false));
                         recyclerView.setAdapter(new ShareAdapter());
                         Button btn_cancel_dialog = view.findViewById(R.id.btn_cancel_dialog);
                         btn_cancel_dialog.setOnClickListener(new View.OnClickListener() {
                             @Override
                             public void onClick(View v) {
                                ToastUtils.shortMsg("取消");
                                 dialog.dismiss();
                             }
                         });

                     }
                 }).show();
   }


   public void showGlobalDialog(View view){
       //build第一个dialog
      ExDialog.Builder builder1= new ExDialog.Builder(this)
               .setDialogView(R.layout.layout_ad_dialog)
               .setWindowBackgroundP(0.5f)
               .setBuildChildListener(new IDialog.OnBuilderListener() {
                   @Override
                   public void onBuilderChildView(IDialog dialog, View view, int layoutRes) {
                       ImageView iv_close = view.findViewById(R.id.iv_close);
                       iv_close.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               dialog.dismiss();
                           }
                       });

                       ImageView iv_ad = view.findViewById(R.id.iv_ad);
                       iv_ad.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                              ToastUtils.shortMsg("点击广告");
                               dialog.dismiss();
                           }
                       });
                   }
               });

       //Build第二个Dialog
      ExDialog.Builder builder2 = new ExDialog.Builder(this)
               .setDialogView(R.layout.layout_bottom_up)
               .setWindowBackgroundP(0.5f)
               .setAnimStyle(R.style.AnimUp)
               .setBuildChildListener(new ExDialog.OnBuilderListener() {
                   @Override
                   public void onBuilderChildView(IDialog dialog, View view, int layoutRes) {
                       Button btn_take_photo = view.findViewById(R.id.btn_take_photo);
                       btn_take_photo.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               ToastUtils.shortMsg("拍照");
                               dialog.dismiss();
                           }
                       });

                       Button btn_select_photo = view.findViewById(R.id.btn_select_photo);
                       btn_select_photo.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               ToastUtils.shortMsg("相册选取");
                               dialog.dismiss();
                           }
                       });

                       Button btn_cancel_dialog = view.findViewById(R.id.btn_cancel_dialog);
                       btn_cancel_dialog.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               ToastUtils.shortMsg("取消");
                               dialog.dismiss();
                           }
                       });
                   }


               })
               .setScreenWidthP(1.0f)
               .setGravity(Gravity.BOTTOM);
      //重头戏来了
      //添加第一个Dialog
       ExDialogManager.getInstance().requestShow(new DialogWrapper(builder1));
       ExDialogManager.getInstance().requestShow(new DialogWrapper(builder2));
   }





   class ShareAdapter extends RecyclerView.Adapter<ShareAdapter.ShareHolder>{
       /**
        * 判断子View
        * @param parent
        * @param viewType
        * @return 返回Holder
        */
       @NonNull
       @Override
       public ShareHolder onCreateViewHolder(ViewGroup parent, int viewType) {
           View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_share,parent,false);
           return new ShareHolder(view);
       }

       /**
        * 绑定View后，处理holder中view里相关逻辑，点击事件等
        * @param holder
        * @param position
        */
       @Override
       public void onBindViewHolder(ShareHolder holder, int position) {
         holder.ll_share.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 ToastUtils.shortMsg("分享...");
                 dismissDialog();
             }
         });
       }

       @Override
       public int getItemCount() {
           return 8;
       }

       class ShareHolder extends RecyclerView.ViewHolder{
         LinearLayout ll_share;
         public ShareHolder(View itemView) {
             super(itemView);
             ll_share=itemView.findViewById(R.id.ll_share);//findId前别忘了itemView。。。
         }
     }
   }
    /**
     * 关闭弹窗 注意dialog=null;防止内存泄漏
     */
    private void dismissDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }


}
