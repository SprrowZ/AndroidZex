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
import com.rye.catcher.project.dialog.ctdialog.ExDialog;
import com.rye.catcher.utils.DensityUtil;
import com.rye.catcher.utils.DeviceUtils;
import com.rye.catcher.utils.EchatAppUtil;
import com.rye.catcher.utils.ExtraUtil.test.utils.OkHttpUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
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

    private String[] dataArr;

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
                create();
                break;
            case 1:
                consumer();
                break;
            case 2:
                just();
                break;
            case 3:
                from();
                break;
            case 4:
                interval();
                break;
            case 5:
                map();
                break;
            case 6:
                flatMap();
                break;
            case 7:
                zip();
                break;
            case 8:
                filter();
                break;
            case 9:
                testBackpressure();
                break;
            case 10:
                testBackpressure2();
                break;
            case 11:
                break;
        }
    }

    private void fakeData() {
        dataArr = new String[]{"create", "consumer", "just", "from", "interval", "map", "FlatMap"
                , "zip", "filter", "testBackpressure", "testBackpressure2"};
        dataList = Arrays.asList(dataArr);
    }

    private void create() {
        //上游
        Observable.create((ObservableOnSubscribe<String>) emitter -> {
            emitter.onNext("1---秦时明月");
            emitter.onNext("2---空山鸟语");
            emitter.onNext("3---天行九歌");
            emitter.onComplete();
            stringBuffer.append("发送数据：" + "\n"
                    + "1---秦时明月" + "\n" + "2---空山鸟语" + "\n" + "3---天行九歌" + "\n"
            );
            Log.i(TAG, "subscribe: .....");
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
                            setResult();
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


    /**
     * 一次性发送数据
     */
    private void just() {
        String[] arr = new String[]{"1", "2", "3", "4", "5"};
        List list = Arrays.asList(arr);
        Observable.just(list)
                .subscribe(new Consumer<List>() {
                    @Override
                    public void accept(List list) throws Exception {
                        for (int i = 0; i < list.size(); i++) {
                            if (i == 0) {
                                stringBuffer.append("接收到的数据为：" + "\n");
                            }
                            stringBuffer.append(list.get(i) + "\n");
                            Log.i("just", "accept: " + list.get(i));
                        }
                    }
                });
        setResult();
    }

    /**
     * 一批次发送数组或Collection
     */
    private void from() {
        String[] arr = new String[]{"1", "2", "3", "4", "5"};
        List list = Arrays.asList(arr);
        Observable.fromIterable(list)
                .subscribe(new Consumer<String>() {
                    int i = 0;

                    @Override
                    public void accept(String string) throws Exception {
                        if (i == 0) {
                            stringBuffer.append("接收到的数据为：" + "\n");
                        }
                        stringBuffer.append(string + "\n");
                        i++;
                        Log.i(TAG, "accept: " + string);
                    }
                });
        setResult();
    }

    /**
     * 返回一个每隔指定的时间间隔就发射一个序号的 Observable 对象，
     * 可用来做倒计时心跳包等操作，无限发送，除非调用dispose()可以终止
     */
    private void interval() {
        Observable.interval(1, 2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread()) //多余操作，其实本来就在主线程中
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.i(TAG, "accept: " + aLong);
                    }
                });
    }

    /**
     * 对数据进行变化后传入下游
     */
    private void map() {

        Observable<Integer> observable = Observable.create(emitter -> {
            int[] arr = new int[3];
            arr[0] = 111;
            arr[1] = 222;
            arr[2] = 333;
            for (int i = 0; i < arr.length; i++) {
                emitter.onNext(arr[i]);
                if (i == 0) {
                    stringBuffer.append("上游发送的数据：" + "\n");
                }
                stringBuffer.append(arr[i] + "\n");
            }
            emitter.onComplete();
        });

        Observer observer = new Observer<String>() {
            int i = 0;
            Disposable disposable;

            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onNext(String o) {
                if (i == 0) {
                    stringBuffer.append("接收到的数据为：" + "\n");
                }
                stringBuffer.append(o + "\n");
                i++;
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                setResult();
            }
        };

        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(integer -> "ZZG-DATA：" + integer).subscribe(observer);

    }

    /**
     *
     */
    private void flatMap() {
        String[] arr = new String[]{"1", "2", "3", "4", "5"};
        List list = Arrays.asList(arr);
        Observable.fromIterable(list) //取List的地方可以进行耗时操作
                .flatMap(new Function<String, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(String str) throws Exception {
                        List list2 = new ArrayList();
                        list2.add("I am value:" + str);
                        return Observable.fromIterable(list2).delay(2, TimeUnit.SECONDS);
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    int i = 0;

                    @Override
                    public void accept(String str) throws Exception {
                        if (i == 0) {
                            stringBuffer.append("接收到的数据为：" + "\n");
                        }
                        stringBuffer.append(str + "\n");
                        i++;
                        if (i == 5) {
                            setResult();
                        }
                        Log.i(TAG, "accept: " + str);
                    }
                });


    }

    /**
     * 打包，使用一个指定的函数将多个Observable发射的数据组合在一起，然后将这个函数的结果作为单项数据发射*
     */
    private void zip() {
        Observable.zip(getRyeObservable(), getCatcherObservable(), new BiFunction<String, Integer, String>() {
            @Override
            public String apply(String s, Integer integer) {
                String zips = "组合数据为：" + s + integer;
                return zips;
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    int i = 0;

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        if (i == 0) {
                            stringBuffer.append("接收到的数组为：" + "\n");
                        }
                        i++;
                        stringBuffer.append(s + "\n");
                        Log.i(TAG, "accept: " + s);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        setResult();
                    }
                });


    }

    /**
     * zip用
     *
     * @return
     */
    private Observable<String> getRyeObservable() {
        String[] arr = new String[]{"1", "2", "3", "4", "5"};
        return Observable.fromArray(arr);
    }

    /**
     * zip用
     *
     * @return
     */
    private Observable<Integer> getCatcherObservable() {
        return Observable.create(emitter -> {
            if (!emitter.isDisposed()) {
                emitter.onNext(11);
                emitter.onNext(22);
                emitter.onNext(33);
                emitter.onComplete();
            }
        });
    }

    /**
     * 过滤操作
     */
    private void filter() {
        String[] arr = new String[]{"1", "2", "3", "4", "5"};
        List list = Arrays.asList(arr);
        Observable.fromIterable(list)
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String o) throws Exception {
                        if (Integer.parseInt(o) > 3) {
                            return true;
                        }
                        return false;
                    }
                }).subscribe(new Consumer<String>() {
            int i = 0;

            @Override
            public void accept(String str) throws Exception {
                if (i == 0) {
                    stringBuffer.append("接收到的数据为：" + "\n");
                }
                stringBuffer.append(str + "\n");
                i++;
                Log.i(TAG, "accept: " + str);
            }
        });
        setResult();
    }


    private void doOnNext() {
        String[] arr = new String[]{"1", "2", "3", "4", "5"};
        List list = Arrays.asList(arr);
        Observable.fromIterable(list).doOnNext(new Consumer<String>() {
            @Override
            public void accept(String str) throws Exception {
                Log.i(TAG, "doOnNext:... " + str);
            }
        }).doAfterNext(new Consumer<String>() {
            @Override
            public void accept(String str) throws Exception {
                Log.i(TAG, "doAfterNext:... " + str);
            }
        }).doFinally(new Action() {
            @Override
            public void run() throws Exception {
                Log.i(TAG, "doFinally:... ");
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String str) throws Exception {

            }
        });
    }

    /**
     * 背压测试------------同线程
     */
    private void testBackpressure() {
        Observable.create((ObservableOnSubscribe<String>) emitter -> {
            while (true) {
                emitter.onNext("Zzz...");
            }
        }).subscribe(s -> {
            Thread.sleep(2000);
            Log.i(TAG, "accept: " + s);
        });
    }

    /**
     * 背压测试--------异步线程
     */
    private void testBackpressure2() {
        Observable.create((ObservableOnSubscribe<String>) emitter -> {
            while (true) {
                emitter.onNext("Zzz...");
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    Thread.sleep(2000);
                    Log.i(TAG, "acceptZZZ: " + s);
                });
    }


    private void setResult() {
        result.setText(stringBuffer);
        stringBuffer.delete(0, stringBuffer.length());
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
                .addHeader("X-ZZ-TOKEN", DeviceUtils.getUUID(EchatAppUtil.getAppContext()))
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
