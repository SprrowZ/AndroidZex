package com.rye.catcher.activity.fragment.orr;


import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rye.catcher.R;
import com.rye.catcher.activity.adapter.RxjavaAdapter;
import com.rye.catcher.activity.fragment.BaseFragment;
import com.rye.catcher.project.dao.ServiceContext;
import com.rye.catcher.project.dialog.ctdialog.ExDialog;
import com.rye.catcher.utils.DensityUtil;
import com.rye.catcher.utils.ExtraUtil.test.utils.OkHttpUtil;
import com.rye.catcher.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class RxjavaFragment extends BaseFragment {

    private static final String TAG = "RxjavaFragment";

    private OkHttpClient client = OkHttpUtil.getInstance().getClient();
    private static final String url = "http://v.juhe.cn/weather/index?cityname=%E4%B8%8A%E6%B5%B7&dtype=&format=&key=3444d95f001d7765de768376c3a2d870";

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.result)
    TextView result;
    private StringBuffer stringBuffer = new StringBuffer();

    private RxjavaAdapter adapter;

    private List<String> dataList;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_rxjava;
    }

    @Override
    protected void initData() {
        fakeData();//填充数据
        adapter = new RxjavaAdapter(getActivity(), dataList);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.HORIZONTAL));
        recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(pos -> itemClick(pos));
        initEvent();
    }

    /**
     * item点击事件
     *
     * @param pos
     */
    private void itemClick(int pos) {
        switch (pos) {
            case 0:
                ToastUtils.longMsg("...");
                create();
                break;
            case 1:
                consumer();
                break;
            case 2:
                map();
                break;
            case 3:

                break;
            case 4:
                break;
            case 5:
                break;
        }
    }

    private void fakeData() {
        dataList = new ArrayList<>();
        dataList.add("create");
        dataList.add("consumer");
        dataList.add("Map");
        dataList.add("FlatMap");
    }

    private void create() {
        //上游
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("1---秦时明月");
                emitter.onNext("2---空山鸟语");
                emitter.onNext("3---天行九歌");
                emitter.onComplete();
                stringBuffer.append("发送数据：" + "\n"
                        + "1---秦时明月" + "\n" + "2---空山鸟语" + "\n" + "3---天行九歌" + "\n"
                );
                Log.i(TAG, "subscribe: .....");
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {//通过subscribe连接下游
                    private Disposable mDisposable;
                    private int i = 0;//计数器

                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "onSubscribe: ");
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(String s) {
                        Log.i(TAG, "onNext: ");
                        if (i == 0) {
                            stringBuffer.append("接收到的数据：" + "\n");
                        }
                        stringBuffer.append(s + "\n");
                        i++;//第几个事件
                        if (i == 2) {
                            result.setText(stringBuffer);
                            stringBuffer.delete(0, stringBuffer.length());
                            mDisposable.dispose();//阻断,dls上线！
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        //走不到，被上面的拦截了
                        Log.i(TAG, "onComplete: " + stringBuffer);

                    }
                });
    }

    /**
     * 下游只关心onNext事件
     */
    private void consumer() {
        Observable.create((ObservableOnSubscribe<Integer>) emitter -> emitter.onNext(666))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> Log.i(TAG, "accept: " + integer));
    }

    private void differentThread() {
        final Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("大浪淘沙");
            }
        });
        Consumer consumer = new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                Log.i(TAG, "accept: " + o.toString());
            }
        };
        observable.subscribeOn(Schedulers.newThread())//可以看出来提交是新开了一个线程
                .observeOn(AndroidSchedulers.mainThread())//接收还是在mainThread中的
                .subscribe(consumer);
    }

    private void map() {

    Observable<Integer> observable= Observable.create(emitter -> {
        int[] arr=new int[3];
        arr[0]=111;
        arr[1]=222;
        arr[2]=333;
        for (int i=0;i<arr.length;i++){
            emitter.onNext(arr[i]);
            if (i==0){
                stringBuffer.append("上游发送的数据："+"\n");
            }
            stringBuffer.append(arr[i]+"\n");
        }
        emitter.onComplete();
    });

      Observer observer=new Observer<String>() {
          int i=0;
          Disposable disposable;
          @Override
          public void onSubscribe(Disposable d) {
              disposable=d;
          }

          @Override
          public void onNext(String o) {
              if (i==0){
                  stringBuffer.append("接收到的数据为："+"\n");
              }
              stringBuffer.append(o+"\n");
              i++;
          }

          @Override
          public void onError(Throwable e) {

          }

          @Override
          public void onComplete() {
              result.setText(stringBuffer);
              stringBuffer.delete(0,stringBuffer.length());
          }
      };

   observable.subscribeOn(Schedulers.newThread())
   .observeOn(AndroidSchedulers.mainThread())
   .map(integer -> "ZZG-DATA："+integer).subscribe(observer);

    }

    private void initEvent() {

    }


    /**
     * 通过Dialog显示
     *
     * @param result
     */
    private void showDialog(String result) {
        TextView tv = new TextView(getContext());
        tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                DensityUtil.dip2px(getContext(), 300)));
        tv.setBackgroundColor(getResources().getColor(R.color.white));
        tv.setText(result);
        new ExDialog.Builder(getContext())
                .setDialogView(tv)
                .setCancelable(true)
                .setGravity(Gravity.CENTER)
                .setWindowBackgroundP(getResources().getColor(R.color.fuckAlpha))
                .setAnimStyle(R.style.translate_style)
                .show();

    }

    /**
     * 以rxjava方式查询天气数据
     */
    private void getWeatherData() {
        //被观察者
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) {
                emitter.onNext(getResponse());
            }
        });
        Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String o) throws Exception {
                showDialog(o);
            }
        };

        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer);
    }

    private String getResponse() {
        Request request = new Request.Builder()
                .get()
                .url(url)
                .addHeader("X-ZZ-TOKEN", ServiceContext.getUUID())
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new Exception("Unexpected result " + String.valueOf(response.code()));
            }
            String result = response.body().string();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
