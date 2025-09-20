package com.example.demo.controller.Register;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * 注册新用户
     */
    public User registerUser(String username, String password, String phone, String nickname) {
        // 检查用户名是否已存在
        if (fileStorageService.existsByUsername(username)) {
            throw new RuntimeException("用户名已存在");
        }

        // 检查手机号是否已存在
        if (fileStorageService.existsByPhone(phone)) {
            throw new RuntimeException("手机号已注册");
        }

        // 创建新用户
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password)); // 加密密码
        user.setPhone(phone);
        user.setNickname(nickname != null ? nickname : "用户_" + username);

        // 保存用户
        fileStorageService.addUser(user);

        return user;
    }

    /**
     * 根据用户名查找用户
     */
    public Optional<User> findByUsername(String username) {
        return fileStorageService.findByUsername(username);
    }

    /**
     * 验证用户登录
     */
    public boolean validateUser(String username, String password) {
        Optional<User> userOptional = fileStorageService.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return passwordEncoder.matches(password, user.getPassword());
        }
        return false;
    }

    /**
     * 获取所有用户
     */
    public java.util.List<User> getAllUsers() {
        return fileStorageService.getAllUsers();
    }
}