package com.dawn.zgstep.others.threads.alarm;

import org.w3c.dom.CDATASection;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Create by rye
 * at 2021/4/22
 *
 * @description:告警代理类
 */
public class AlarmAgent {
    private final static AlarmAgent INSTANCE = new AlarmAgent();

    //是否连接上告警服务器
    private boolean connectedToServer = false;
    private final HeartbeatThread heartbeatThread = new HeartbeatThread();

    public static AlarmAgent getInstance() {
        return INSTANCE;
    }

    public void init() {
        connectToServer();
        heartbeatThread.setDaemon(true);
        heartbeatThread.start();
    }

    //新开线程连接服务器
    private void connectToServer() {
        new Thread() {
            @Override
            public void run() {
                doConnect();
            }
        }.start();
    }

    public void sendAlarm(String message) throws InterruptedException {
        synchronized (this) {
            while (!connectedToServer &&sout11()) {
                sout("报警代理还没连接上服务器,释放锁");
                wait();
                sout("wait....after");
            }
            sout("sendAlarm 被唤醒");
            //真正将告警消息上报到告警服务器
            doSendAlarm(message);
        }
    }
    private boolean sout11(){
        sout("while judge..");
        return true;
    }

    public static void main(String[] args) {
        AlarmAgent agent = new AlarmAgent();

        try {
            //初始化连接服务器
            agent.init();
            agent.sendAlarm("一级警报！！");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void doSendAlarm(String message) {
        sout("代理发消息给服务器：" + message);
    }


    //连接
    private void doConnect() {
        sout("开始连接服务器...");
        try {
            Thread.sleep(3000);
            synchronized (this) {
                connectedToServer = true;
                //连接已经建立完毕，通知以唤醒告警发送线程
                sout("已经连上服务器~notify");
                notify();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //心跳线程
    class HeartbeatThread extends Thread {
        @Override
        public void run() {
            try {
                //留一定的时间给网络连接线程与告警服务器建立连接
                Thread.sleep(1000);
                while (true) {
//                    if (checkConnection()) {
                    if (true) {
                        connectedToServer = true;
                    } else {
                        connectedToServer = false;
                        sout("告警代理已断开服务器");
                        connectToServer();
                    }
                    Thread.sleep(2000);
                }
            } catch (Exception e) {

            }
        }

        private boolean checkConnection() {
            boolean isConnected = true;
            final Random random = new Random();
            int rand = random.nextInt(1000);
            if (rand <= 500) {
                isConnected = false;
            }
            return isConnected;
        }
    }

    public void sout(String content) {
        System.out.println(getCurrentTime() + ".." + content);
    }

    private String getCurrentTime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss:SSS");
        return sdf.format(date);
    }
}


