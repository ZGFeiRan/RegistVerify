package com.feiran.zg.core.base.service.impl;

import com.feiran.zg.core.base.mapper.AccountMapper;
import com.feiran.zg.core.base.domain.Account;
import com.feiran.zg.core.base.service.IAccountService;
import com.feiran.zg.core.base.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhangguangfei on 2017/1/13.
 */
@Service
public class AccountServiceImpl implements IAccountService{
    @Autowired
    private AccountMapper accountMapper;
    @Override
    public void update(Account account) {
        int count = this.accountMapper.updateByPrimaryKey(account);
        if (count<=0){
            throw new RuntimeException("乐观锁失败,Account: "+account.getId());// 默认情况下,SpringMVC只有在接收到RuntimeException异常的时候才会进行事务的回滚。
        }
    }

    @Override
    public void add(Account account) {
        this.accountMapper.insert(account);
    }

    @Override
    public Account getCurrent() {
        return this.getById(UserContext.getCurrent().getId());
    }

    @Override
    public Account getById(Long id) {
        Account account = this.accountMapper.selectByPrimaryKey(id);
        if (account.isValidate()){
            return account;
        }else {
            throw new RuntimeException("id值为:"+UserContext.getCurrent().getId()+"的账户(Account)的数据存在异常,可能被人恶意篡改过");
        }
    }

    @Override
    public void chechAccountVerify() {
        List<Account> list = this.accountMapper.selectAll();
        for (Account account : list) {
            if (!account.isValidate()){
//                throw new RuntimeException("id值为:"+account.getId()+"的Account数据库表被恶意篡改过");
                System.out.println("id值为:"+account.getId()+"的Account数据库表被恶意篡改过,以阻止业务继续执行,请联系客服");
            }
        }

    }
}
