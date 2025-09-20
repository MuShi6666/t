package com.example.demo.controller.Register;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class User {
    private Long id;
    private String username; // QQ号
    private String password; // 密码
    private String phone; // 手机号
    private String nickname; // 昵称
    private LocalDateTime createTime; // 创建时间

    // 生成唯一ID（简单实现）
    private static long idCounter = 1;

    public User() {
        this.id = idCounter++;
        this.createTime = LocalDateTime.now();
    }
}