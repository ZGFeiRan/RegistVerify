package com.feiran.zg.core.base.mapper;

import com.feiran.zg.core.base.domain.MailVerify;

public interface MailVerifyMapper {

    int insert(MailVerify record);

    MailVerify selectByUUID(String  uuid);

}