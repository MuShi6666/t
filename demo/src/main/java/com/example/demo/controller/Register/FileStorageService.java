package com.example.demo.controller.Register;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class FileStorageService {

    private static final String USER_DATA_FILE = "users.dat";
    private final List<User> users = new ArrayList<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 初始化加载用户数据
     */
    @PostConstruct
    public void init() {
        loadUsersFromFile();
    }

    /**
     * 从文件加载用户数据
     */
    private synchronized void loadUsersFromFile() {
        try {
            if (Files.exists(Paths.get(USER_DATA_FILE))) {
                byte[] jsonData = Files.readAllBytes(Paths.get(USER_DATA_FILE));
                User[] userArray = objectMapper.readValue(jsonData, User[].class);
                users.clear();
                Collections.addAll(users, userArray);
                System.out.println("已加载 " + users.size() + " 个用户数据");
            }
        } catch (IOException e) {
            System.err.println("加载用户数据失败: " + e.getMessage());
        }
    }

    /**
     * 保存用户数据到文件
     */
    private synchronized void saveUsersToFile() {
        try {
            String json = objectMapper.writeValueAsString(users);
            Files.write(Paths.get(USER_DATA_FILE), json.getBytes());
        } catch (IOException e) {
            System.err.println("保存用户数据失败: " + e.getMessage());
        }
    }

    /**
     * 添加用户
     */
    public void addUser(User user) {
        users.add(user);
        saveUsersToFile();
    }

    /**
     * 根据用户名查找用户
     */
    public Optional<User> findByUsername(String username) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
    }

    /**
     * 检查用户名是否存在
     */
    public boolean existsByUsername(String username) {
        return users.stream()
                .anyMatch(user -> user.getUsername().equals(username));
    }

    /**
     * 检查手机号是否存在
     */
    public boolean existsByPhone(String phone) {
        return users.stream()
                .anyMatch(user -> phone != null && phone.equals(user.getPhone()));
    }

    /**
     * 获取所有用户
     */
    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }
}