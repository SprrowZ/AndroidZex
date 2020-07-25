package com.dawn.zgstep.design_patterns.structural.facade;

/**
 * Create by rye
 * at 2020-07-25
 *
 * @description:
 */
public class NewConsumeService extends BaseConsumeService {
    private StoreService mStoreService = new StoreService();
    private WareHouseService mWareHouseService = new WareHouseService();
    private PayService mPayService = new PayService();
    private LogisticsService mLogisticsService = new LogisticsService();

    @Override
    void consume() {
        if (mStoreService.isOntheShelf()){
            //----商铺系统逻辑处理
            if (mPayService.isPaySuccess()){
                //支付系统处理
                if (mWareHouseService.hasStock()){
                    //仓库系统逻辑处理
                    mLogisticsService.sendToConsumer();
                }
            }
        }
    }
}
