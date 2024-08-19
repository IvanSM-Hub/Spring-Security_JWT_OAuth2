package com.cursos.api.spring_security_course.util;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum RoleEnum {
    ADMINISTRATOR(Arrays.asList(
            RolePermissionEnum.READ_ALL_PRODUCTS,
            RolePermissionEnum.READ_ONE_PRODUCT,
            RolePermissionEnum.CREATE_ONE_PRODUCT,
            RolePermissionEnum.UPDATE_ONE_PRODUCT,
            RolePermissionEnum.DISABLE_ONE_PRODUCT,

            RolePermissionEnum.READ_ALL_CATEGORIES,
            RolePermissionEnum.READ_ONE_CATEGORY,
            RolePermissionEnum.CREATE_ONE_CATEGORY,
            RolePermissionEnum.UPDATE_ONE_CATEGORY,
            RolePermissionEnum.DISABLE_ONE_CATEGORY,

            RolePermissionEnum.READ_MY_PROFILE
    )),
    ASSISTANT_ADMINISTRATOR(Arrays.asList(
            RolePermissionEnum.READ_ALL_PRODUCTS,
            RolePermissionEnum.READ_ONE_PRODUCT,
            RolePermissionEnum.UPDATE_ONE_PRODUCT,

            RolePermissionEnum.READ_ALL_CATEGORIES,
            RolePermissionEnum.READ_ONE_CATEGORY,
            RolePermissionEnum.UPDATE_ONE_CATEGORY,

            RolePermissionEnum.READ_MY_PROFILE
    )),
    COSTUMER(Arrays.asList(
            RolePermissionEnum.READ_MY_PROFILE
    ));

    private List<RolePermissionEnum> permissions;

    RoleEnum(List<RolePermissionEnum> permissions) {
        this.permissions = permissions;
    }
}
