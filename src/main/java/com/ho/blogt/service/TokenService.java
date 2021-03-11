package com.ho.blogt.service;

public interface TokenService {
    String generatorToken(String str);

    void setToken(String key, Object value);

    void setToken(String key, Object value,long tiemout);

    Object getToken(String key);
}
