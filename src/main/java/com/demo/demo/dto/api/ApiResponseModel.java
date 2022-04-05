package com.demo.demo.dto.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseModel extends ApiResponse{

    private Object object;

    private List<Object> objects;
}
