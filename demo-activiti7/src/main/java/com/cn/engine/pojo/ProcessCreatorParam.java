package com.cn.engine.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProcessCreatorParam {

    private String processName;
    private String creator;
    private List<String> approvers;

    // Getters and setters
}
