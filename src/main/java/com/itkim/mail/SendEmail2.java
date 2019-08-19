package com.itkim.mail;

import java.util.Date;

/**
 * @Description: TODO
 * @author: Vic
 * @date: 2019-08-11 上午1:38
 */
public class SendEmail2 {
    public static void sendEmail(String message) {

        String userName = "***";   //用户邮箱地址
        String password = "***";    //密码或者授权码
        String targetAddress = "***";     //接受者邮箱地址


        // 设置邮件内容
        MimeMessageDTO mimeDTO = new MimeMessageDTO();
        mimeDTO.setSentDate(new Date());
        mimeDTO.setSubject("新的订单");
        mimeDTO.setText(message);

//		// 发送单邮件
        if (MailUtil.sendEmail(userName, password, targetAddress, mimeDTO)) {
            System.out.println("邮件发送成功！");
        } else {
            System.out.println("邮件发送失败!!!");
        }
    }
}