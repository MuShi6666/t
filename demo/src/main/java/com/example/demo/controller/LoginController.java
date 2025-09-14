package com.example.demo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController // 这个注解表明这个类是一个控制器，并且它的所有方法返回的数据都会直接写入HTTP响应体，而不是跳转到视图。
public class  LoginController {

    // 定义一个简单的用户数据库（模拟！正式项目绝对不能这样！）
    // key: username, value: password
    private final Map<String, String> userDatabase = new HashMap<>() {{
        put("123456", "password123"); // QQ号：123456, 密码：password123
        put("admin", "admin");
    }};

    /**
     * 处理登录请求的API接口
     * @param username 用户名（QQ号）
     * @param password 密码
     * @return 一个包含登录成功或失败信息的JSON对象
     */
    @PostMapping("/login") // 这个注解表示该方法处理POST请求，且请求路径是 "/login"
    public Map<String, Object> login(@RequestParam String username,
                                     @RequestParam String password) {

        Map<String, Object> result = new HashMap<>();

        // 1. 检查用户是否存在
        if (!userDatabase.containsKey(username)) {
            result.put("success", false);
            result.put("message", "QQ号不存在！");
            return result;
        }

        // 2. 检查密码是否正确
        String correctPassword = userDatabase.get(username);
        if (!correctPassword.equals(password)) {
            result.put("success", false);
            result.put("message", "密码错误！");
            return result;
        }

        // 3. 登录成功
        result.put("success", true);
        result.put("message", "登录成功！");
        // 将来这里可以返回token、用户信息等
        return result;
    }
}