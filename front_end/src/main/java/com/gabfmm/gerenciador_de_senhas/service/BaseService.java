package com.gabfmm.gerenciador_de_senhas.service;

public abstract class BaseService {
    protected final ApiClient apiClient;

    public BaseService(){
        apiClient = new ApiClient();
    }
}
