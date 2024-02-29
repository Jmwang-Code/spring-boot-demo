package com.cn.jmw.authentication.enums;

import cn.hutool.core.collection.CollectionUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.platform.commons.util.CollectionUtils;

import java.util.Collection;
import java.util.List;

public enum RoleConvertPermission {

    // 线索审判员 -> 审批权限
    CLUE_JUDGE_APPROVE(Role.CLUE_JUDGE, Permission.APPROVE),
    // 单位领导 -> 审批权限
    UNIT_LEADER_APPROVE(Role.UNIT_LEADER, Permission.APPROVE),
    // 内勤人员 -> 内部流程权限
    INTERNAL_STAFF_INTERNAL_FLOW(Role.INTERNAL_STAFF, Permission.INTERNAL_FLOW),
    // 线索管理员 -> 外部流程权限
    CLUE_MANAGER_EXTERNAL_FLOW(Role.CLUE_MANAGER, Permission.EXTERNAL_FLOW);

    private Role role;
    private Permission permission;

    RoleConvertPermission(Role role, Permission permission) {
        this.role = role;
        this.permission = permission;
    }

    public Role getRole() {
        return role;
    }

    public Permission getPermission() {
        return permission;
    }

    /**
     * 通过角色 获取权限范围
     */
    public static Permission getPermissionByRole(Role role) {
        for (RoleConvertPermission roleConvertPermission : RoleConvertPermission.values()) {
            if (roleConvertPermission.getRole().equals(role)) {
                return roleConvertPermission.getPermission();
            }
        }
        return null;
    }

    /**
     * 通过多个角色 获取多个权限范围
     */
    public static List<Permission> getPermissionByRoles(List<Role> roles) {
        List<Permission> permissions = null;
        for (Role role : roles) {
            Permission permissionByRole = getPermissionByRole(role);
            if (ObjectUtils.isNotEmpty(permissionByRole)) permissions.add(permissionByRole);
        }
        return permissions;
    }

    /**
     * 通过权限范围 获取角色
     */
    public static Role getRoleByPermission(Permission permission) {
        for (RoleConvertPermission roleConvertPermission : RoleConvertPermission.values()) {
            if (roleConvertPermission.getPermission().equals(permission)) {
                return roleConvertPermission.getRole();
            }
        }
        return null;
    }

    /**
     * 通过多个权限范围 获取多个角色
     */
    public static List<Role> getRoleByPermissions(List<Permission> permissions) {
        List<Role> roles = null;
        for (Permission permission : permissions) {
            Role roleByPermission = getRoleByPermission(permission);
            if (ObjectUtils.isNotEmpty(roleByPermission)) roles.add(roleByPermission);
        }
        return roles;
    }
}
