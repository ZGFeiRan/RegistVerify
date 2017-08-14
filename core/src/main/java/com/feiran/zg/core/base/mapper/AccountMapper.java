package com.feiran.zg.core.base.mapper;

import com.feiran.zg.core.base.domain.Account;

import java.util.List;

public interface AccountMapper {

    int insert(Account record);

    Account selectByPrimaryKey(Long id);

    int updateByPrimaryKey(Account record);

    List<Account> selectAll();
}