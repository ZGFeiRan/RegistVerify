package com.feiran.zg.core.base.service.impl;

import com.feiran.zg.core.base.service.IEmailService;
import com.feiran.zg.core.base.service.IUserInfoService;
import com.feiran.zg.core.base.domain.MailVerify;
import com.feiran.zg.core.base.domain.UserInfo;
import com.feiran.zg.core.base.mapper.MailVerifyMapper;
import com.feiran.zg.core.base.utils.BidConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

/**
 * Created by zhangguangfei on 2017/1/17.
 */
@Service
public class EmailServiceImpl implements IEmailService {
    @Autowired
    private MailVerifyMapper mailVerifyMapper;
    @Autowired
    private IUserInfoService userInfoService;
    @Value("${email.applicationUrl}")
    private String applicationUrl;
    @Value("${email.host}")
    private String host;
    @Value("${email.userName}")
    private String userName;
    @Value("${email.password}")
    private String password;

    @Override
    public void sendVerifyEmail(String email) {
        UserInfo current = userInfoService.getCurrent();
        // 判断用户是否已经绑定过邮箱
        if (!current.getIsBindEmail()) {// 用户没有绑定过邮箱的时候才允许绑定
            // 生成一个UUID
            String uuid = UUID.randomUUID().toString();
            //  构造邮件内容,并发送
            String content = new StringBuilder(100).append("这是验证邮件,请点击 <a href='")
                    .append(this.applicationUrl).append("bindEmail.do?uuid=")
                    .append(uuid).append("'>这里</a>,验证邮件的有效期是:")
                    .append(BidConst.VERIFYEMAIL_VALID_DAY).append("天")
                    .toString();
            try {
                System.out.println("发送邮件:" + content);

                // 创建发送邮件的对象
                JavaMailSenderImpl sender = new JavaMailSenderImpl();
                // 设置邮件服务器地址
                sender.setHost(this.host);
                // 创建一个邮件
                MimeMessage message = sender.createMimeMessage();
                MimeMessageHelper messageHelper = new MimeMessageHelper(message, "UTF-8");

                // 设置邮件相关内容
                messageHelper.setTo(email);
                messageHelper.setFrom(this.userName);
                messageHelper.setSubject("验证邮件");

                // 设置邮件主体内容
                messageHelper.setText(content,true);

                // 设置发送准备
                sender.setUsername(this.userName);
                sender.setPassword(this.password);
                Properties properties = new Properties();
                properties.put("mail.smtp.auth","true");
                properties.put("mail.smtp.timeout","25000");
                sender.setJavaMailProperties(properties);

                // 发送邮件
                sender.send(message);

                // 创建一个MainlVerify对象
                MailVerify mailVerify = new MailVerify();
                mailVerify.setEmail(email);
                mailVerify.setLoginInfoId(current.getId());
                mailVerify.setSendTime(new Date());
                mailVerify.setUuid(uuid);
                this.mailVerifyMapper.insert(mailVerify);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("发送验证邮件失败,请稍后重试!");
            }
        } else {
            throw new RuntimeException("您已经绑定过邮箱了,一个账号只能绑定一个邮箱!");
        }
    }
}
