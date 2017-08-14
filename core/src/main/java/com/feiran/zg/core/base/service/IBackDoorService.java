package com.feiran.zg.core.base.service;

/**
 * 后门操作的Service
 * Created by zhangguangfei on 2017/2/15.
 */
public interface IBackDoorService {
    /**
     * 在Account类中新增了verify字段,对原有数据的verify设置值
     */
    void updateAccountVerify();
}
