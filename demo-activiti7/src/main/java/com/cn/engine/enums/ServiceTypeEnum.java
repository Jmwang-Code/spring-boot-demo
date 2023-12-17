package com.cn.engine.enums;

public enum ServiceTypeEnum {

    /**
     * 业务类型
     */
    SERVICE_TYPE_1("service_type_1");

    private String value;

    ServiceTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ServiceTypeEnum fromValue(String value) {
        for (ServiceTypeEnum serviceTypeEnum : ServiceTypeEnum.values()) {
            if (serviceTypeEnum.getValue().equals(value)) {
                return serviceTypeEnum;
            }
        }
        return null;
    }

}
