package com.feiran.zg.core.base.service;

import com.feiran.zg.core.base.page.PageResult;
import com.feiran.zg.core.base.query.VedioAuthQueryObject;

/**
 * 视频认证相关服务
 * Created by zhangguangfei on 2017/2/7.
 */
public interface IVedioAuthService {
     PageResult queryForPageResult(VedioAuthQueryObject qo);

     /**
      * 视频审核
      * @param loginInfoValue
      * @param remark
      * @param state
     */
     void audit(Long loginInfoValue,String remark,int state);
}
