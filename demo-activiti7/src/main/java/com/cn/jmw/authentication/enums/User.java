package com.cn.jmw.authentication.enums;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private String userId;
    private Set<Role> roles;
    private Set<Permission> permissions;
    private Object internalDeptId;

    // 其他用户信息...
}