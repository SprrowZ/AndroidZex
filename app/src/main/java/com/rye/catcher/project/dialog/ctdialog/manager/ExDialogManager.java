package com.rye.catcher.project.dialog.ctdialog.manager;

import com.rye.catcher.project.dialog.ctdialog.ExDialog;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created at 2018/9/27.
 *因为可以多个dialog依次展示，所以需要一个管理器
 * @author Zzg
 */
public class ExDialogManager {
    /**
     * volatile这个关键字
     */
    private volatile boolean showing=false;//是否有dialog显示
    private ConcurrentLinkedQueue<DialogWrapper> dialogQueue=new ConcurrentLinkedQueue<DialogWrapper>();
    private ExDialogManager(){

    }

    /**
     * 静态内部类单例模式
     */
    private static class DialogHoler{
        private static ExDialogManager INSTANCE=new ExDialogManager();
    }
    public static ExDialogManager getInstance(){
        return DialogHoler.INSTANCE;
    }

    /**d
     * 请求加入队列并展示
     * @param dialogWrapper
     * @return 加入队列是否成功
     */
    public synchronized boolean requestShow(DialogWrapper dialogWrapper){
        boolean b=dialogQueue.offer(dialogWrapper);
        checkAndDispatch();
        return b;
    }

    /**
     * 结束一次展示 并且检查下一个弹窗
     */
    public synchronized  void over(){
        showing=false;
        next();
    }



    private synchronized void checkAndDispatch() {
    if (!showing){
        next();
    }

    }
    /**
     * 弹出下一个弹窗
     */
    private synchronized void next(){
    DialogWrapper poll=dialogQueue.poll();
    if (poll==null) return ;
    ExDialog.Builder dialog=poll.getDialog();
    if (dialog!=null){
        showing=true;
        dialog.show();
    }
    }
}
