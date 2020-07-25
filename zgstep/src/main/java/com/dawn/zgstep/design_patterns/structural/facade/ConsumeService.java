package com.dawn.zgstep.design_patterns.structural.facade;

/**
 * Create by rye
 * at 2020-07-25
 *
 * @description:
 */
public class ConsumeService {
    //店铺系统
    private StoreService mStoreService = new StoreService();
    //仓库系统
    private WareHouseService mWareHouseService =new WareHouseService();
    //物流系统
    private LogisticsService mLogisticsService =new LogisticsService();
    //开始购物
    public void startShopping(){
        System.out.println("开始购物...");
        if (mStoreService.isOntheShelf()){
            if (mWareHouseService.hasStock()){
                mLogisticsService.sendToConsumer();
            }
        }
    }
}
