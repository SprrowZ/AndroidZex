package com.rye.catcher.project.Ademos.mvp;

/**
 * Created at 2019/2/27.
 *
 * @author Zzg
 * @function: Presenter
 */
public class MvpPresenter extends BasePresenter<MvpView> {

 public void getData(String params){
     //显示正在加载进度条
     getView().showLoading();
     //调用Model请求数据
     MvpModel.getNetData(params, new MvpCallback() {
         @Override
         public void onSuccess(String data) {
             //调用接口显示数据
             if (isViewAttached()){
                 getView().showData(data);
             }
         }

         @Override
         public void onFailure(String msg) {
             if (isViewAttached()){
                 getView().showToast(msg);
             }

         }

         @Override
         public void onError() {
             if (isViewAttached()){
                 getView().showError();
             }
         }

         @Override
         public void onComplete() {
             if (isViewAttached()){
                 getView().hideLoading();
             }
         }
     });
 }


}
