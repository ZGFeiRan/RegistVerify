package com.feiran.zg.core.base.service.impl;

import com.feiran.zg.core.base.service.IVerifyCodeService;
import com.feiran.zg.core.base.utils.BidConst;
import com.feiran.zg.core.base.utils.DateUtil;
import com.feiran.zg.core.base.utils.UserContext;
import com.feiran.zg.core.base.vo.VerifyCodeVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.UUID;

/**
 * 验证码相关服务
 * Created by zhangguangfei on 2017/1/16.
 */
@Service
public class VerifyCodeServiceImpl implements IVerifyCodeService {

    // 通过@Autowired注入复杂数据类型的值,通过@Value注入简单数据类型的值
    @Value("${sms.userName}")
    private String userName;
    @Value("${sms.password}")
    private String password;
    @Value("${sms.apikey}")
    private String apikey;
    @Value("${sms.url}")
    private String url;


    @Override
    public void sendVerifyCode(String phoneNumber) {
        // 首先得到保存在session中的VerifuCode
        VerifyCodeVO verifyCodeVO = UserContext.getVerifyCodeVO();
        // 判断当前是否能够发送短信
        if (verifyCodeVO==null || (verifyCodeVO!=null && DateUtil.getSecondInterval(verifyCodeVO.getSendTime(),new Date())>= BidConst.SEND_VERIFYCODE_INTERVAL)){// 当前能够发送短信,执行以下代码
            // 生成验证码
            String code = UUID.randomUUID().toString().substring(0,4);
            // 使用HttpURLConnection对象发送短信
            try {
                URL tagertUrl = new URL(this.url);
                HttpURLConnection urlConnection = (HttpURLConnection) tagertUrl.openConnection();
                // 配置输入参数
                urlConnection.setDoOutput(true);// 设置POST请求有输出
                urlConnection.setRequestMethod("POST");// POST必须大写

                String params = new StringBuilder(100)
                        .append("userName=").append(userName)
                        .append("&password=").append(password)
                        .append("&apikey=").append(apikey)
                        .append("&ur;=").append(url)
                        .append("&mobile=").append(phoneNumber)
                        .append("&content=").append("您的验证码是:").append(code)
                        .append(",有效时间是").append(BidConst.VERIFYCODE_VALID_TIME)
                        .append("秒").toString();
                // 写入参数
                urlConnection.getOutputStream().write(params.getBytes());
                // 链接
                urlConnection.connect();
                // 读入响应
                String response = StreamUtils.copyToString(urlConnection.getInputStream(), Charset.forName("UTF-8"));
                if (response.startsWith("success")){// 短信发送成功后 再 继续进行后续操作
                    // 生成一个VerifyCodeVO对象
                    verifyCodeVO = new VerifyCodeVO(code,phoneNumber,new Date());
                    // 把VerifyCodeVO对象放到session中
                    UserContext.putVerifyCodeVO(verifyCodeVO);
                }else {
                    throw new RuntimeException("发送失败");
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("发送失败");
            }
        }else{
            throw new RuntimeException("发送过于频繁,请稍后再发送");
        }
    }

    @Override
    public boolean validate(String phoneNumber, String code) {
        // 首先得到保存在session中的VerifuCode
        VerifyCodeVO vc = UserContext.getVerifyCodeVO();
        if (vc!=null // 发送了验证码
                && vc.getVerifyCode().equalsIgnoreCase(code) // 两次发送的验证码相同,不区分大小写
                && vc.getPhoneNumber().equals(phoneNumber)// 两次电话号码相同
                && DateUtil.getSecondInterval(vc.getSendTime(),new Date())<=BidConst.VERIFYCODE_VALID_TIME){// 在有效期内
            return true;
        }else{
            return false;
        }
    }
}
