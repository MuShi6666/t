package com.example.demo.controller.Register;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CaptchaService captchaService;

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public Result<String> register(@RequestBody Map<String, String> params) {
        String username = params.get("username");
        String password = params.get("password");
        String phone = params.get("phone");
        String nickname = params.get("nickname");
        String captchaCode = params.get("captchaCode");
        String uuid = params.get("uuid"); // 验证码的唯一标识

        // 验证验证码
        if (!captchaService.validateCaptcha(uuid, captchaCode)) {
            return Result.error(400, "验证码错误");
        }

        // 注册用户
        try {
            User user = userService.registerUser(username, password, phone, nickname);
            return Result.success("注册成功，用户ID: " + user.getId());
        } catch (RuntimeException e) {
            return Result.error(400, e.getMessage());
        }
    }

    //用户登录
    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody Map<String, String> params) {
        String username = params.get("username");
        String password = params.get("password");

        // 验证用户
        if (userService.validateUser(username, password)) {
            // 登录成功，返回用户信息
            User user = userService.findByUsername(username).orElse(null);
            if (user != null) {
                Map<String, Object> userInfo = new java.util.HashMap<>();
                userInfo.put("id", user.getId());
                userInfo.put("username", user.getUsername());
                userInfo.put("nickname", user.getNickname());
                userInfo.put("phone", user.getPhone());
                return Result.success(userInfo);
            }
        }

        return Result.error(401, "用户名或密码错误");
    }

    /**
     * 获取所有用户（用于测试）
     */
    @GetMapping("/all")
    public Result<java.util.List<User>> getAllUsers() {
        return Result.success(userService.getAllUsers());
    }
}