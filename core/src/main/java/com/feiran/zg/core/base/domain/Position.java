package com.feiran.zg.core.base.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

/**
 * Created by FeiRan_ZG on 2017/8/22.
 *
 * 出诊场所
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Alias("position")
public class Position {
//    private String id;
    private String province;

    private String city;

    private String county;

    private String countryside;
}