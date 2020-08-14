package com.rye.catcher.base.mvp.demos;

import com.rye.catcher.base.mvp.JokerBasePresenter;

/**
 * Created by 18041at 2019/5/26
 * Function:
 */
public class AnimePresenter extends JokerBasePresenter<AnimeView> {

       public void getData(){
           //......getData,return View
          mView.showRealmData();
       }
}
