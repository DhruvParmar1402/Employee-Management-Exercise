package com.example.demo.DTO;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageDTO {
    private int size;
    private int offset;
    private String columnName;
    private String order;
}
