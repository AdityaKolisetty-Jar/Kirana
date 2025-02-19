package com.example.springboot.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {
    private boolean success = true;
    private String errorCode;
    private String displayMsg;
    private Object errorResponse;
    private Object data;
}
