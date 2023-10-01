package com.demo1010.researcherv2.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private int status;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    // 정적 팩토리 메서드: 성공 응답
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(200, message, data, LocalDateTime.now());
    }

    // 정적 팩토리 메서드: 실패 응답
    public static <T> ApiResponse<T> error(int status, String message) {
        return new ApiResponse<>(status, message, null, LocalDateTime.now());
    }
}

