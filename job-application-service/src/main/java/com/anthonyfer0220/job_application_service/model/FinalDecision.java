package com.anthonyfer0220.job_application_service.model;

public enum FinalDecision {
    PENDING,
    OFFERED,
    REJECTED;

    public static FinalDecision fromString(String value) {
        return value == null ? null : FinalDecision.valueOf(value.toUpperCase());
    }
}