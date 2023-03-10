package com.tanio.multitenant;

import org.springframework.stereotype.Component;

@Component
public class CurrentTenantKey {
    private final ThreadLocal<String> value = new ThreadLocal<>();

    public void set(String currentTenantKey) {
        value.set(currentTenantKey);
    }

    public String get() {
        return value.get();
    }

    public void reset() {
        value.remove();
    }
}
