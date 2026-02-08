package com.gabfmm.gerenciador_de_senhas.auth;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class SessionContext {

    private static ThreadLocal<Long> USER_ID;

    public SessionContext(){}

    // This method is only called after all dependency injections
    @PostConstruct
    public void init(){
        USER_ID = new ThreadLocal<>();
    }

    public void setUserId(String userId) {
        USER_ID.set(Long.parseLong(userId));
    }

    public Long getUserId() {
        return USER_ID.get();
    }

    public void clear(){
        USER_ID.remove();
    }
}
