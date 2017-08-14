package com.feiran.zg.core.base.mapper;

import com.feiran.zg.core.base.domain.LoginInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface LoginInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LoginInfo record);

    LoginInfo selectByPrimaryKey(Long id);

    List<LoginInfo> selectAll();

    int updateByPrimaryKey(LoginInfo record);

    // 查询新注册的用户名是否已经被别人注册过了
    int countUserByUserName(String userName);

    LoginInfo login(@Param("userName")String userName, @Param("password")String password,@Param("userType")int userType);

    // 按照用户类型统计当前数据库中有多少该类型的用户,用户类型指的是普通用户,还是后台管理员
    int queryCountByUserType(int userManager);

    /**
     * 自动补全
     */
    List<Map<String, Object>> autoComplate(String keyWord);
}