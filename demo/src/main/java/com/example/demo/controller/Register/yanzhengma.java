package com.example.demo.controller.Register;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@Service
public class yanzhengma {

    // 存储验证码和对应的UUID
    private Map<String, String> captchaStore = new HashMap<>();

    // 验证码字符集
    private static final String CHAR_SET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CAPTCHA_LENGTH = 6;

    /**
     * 生成随机验证码
     */
    public String generateRandomCaptcha() {
        Random random = new Random();
        StringBuilder captcha = new StringBuilder();

        for (int i = 0; i < CAPTCHA_LENGTH; i++) {
            int index = random.nextInt(CHAR_SET.length());
            captcha.append(CHAR_SET.charAt(index));
        }

        return captcha.toString();
    }

    /**
     * 生成验证码并返回UUID和验证码文本
     */
    public Map<String, String> generateCaptcha() {
        String captchaText = generateRandomCaptcha();
        String uuid = UUID.randomUUID().toString();

        // 将验证码文本存入内存
        captchaStore.put(uuid, captchaText);

        Map<String, String> result = new HashMap<>();
        result.put("uuid", uuid);
        result.put("captcha", captchaText);

        return result;
    }

    /**
     * 验证验证码
     */
    public boolean validateCaptcha(String uuid, String code) {
        if (uuid == null || code == null) {
            return false;
        }

        String storedCode = captchaStore.get(uuid);
        if (storedCode == null) {
            return false;
        }

        // 验证成功后移除验证码（一次性使用）
        captchaStore.remove(uuid);

        return storedCode.equalsIgnoreCase(code);
    }
}