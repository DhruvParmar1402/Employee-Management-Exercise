package com.example.demo.DTO;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseDTO<T> {
    private boolean success;
    private String message;
    private T data;
    private LocalDateTime localDateTime;

    public ResponseDTO(boolean success, String msg, T data) {
        this.success = success;
        this.message = msg;
        this.data = data;
        this.localDateTime = LocalDateTime.now();
    }
}
