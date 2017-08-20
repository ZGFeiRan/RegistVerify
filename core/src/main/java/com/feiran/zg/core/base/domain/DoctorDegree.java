package com.feiran.zg.core.base.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by FeiRan_ZG on 2017/8/20.
 *
 * 医生学位
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDegree extends BaseDomain{
    // 职称
    private String doctorDegree;

}