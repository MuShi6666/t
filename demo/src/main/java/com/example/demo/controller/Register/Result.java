package com.example.demo.controller.Register;
import lombok.Data;

@Data
public class Result<T> {
    private Integer code; // 状态码，200表示成功，其他表示失败
    private String message; // 提示信息
    private T data; // 响应数据（泛型）

    // 成功的静态方法
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage("成功");
        result.setData(data);
        return result;
    }

    // 失败的静态方法
    public static <T> Result<T> error(Integer code, String message) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
}
