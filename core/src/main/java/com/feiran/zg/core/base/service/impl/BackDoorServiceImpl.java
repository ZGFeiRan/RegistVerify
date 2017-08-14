package com.feiran.zg.core.base.service.impl;

import com.feiran.zg.core.base.mapper.AccountMapper;
import com.feiran.zg.core.base.domain.Account;
import com.feiran.zg.core.base.service.IBackDoorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhangguangfei on 2017/2/15.
 */
@Service
public class BackDoorServiceImpl implements IBackDoorService {
    @Autowired
    private AccountMapper accountMapper;
    @Override
    public void updateAccountVerify() {
        List<Account> list = this.accountMapper.selectAll();
        for (Account account : list) {
            this.accountMapper.updateByPrimaryKey(account);
        }
    }
}
