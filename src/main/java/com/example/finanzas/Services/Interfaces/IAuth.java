package com.example.finanzas.Services.Interfaces;

import com.example.finanzas.models.dto.AuthResponse;

import com.example.finanzas.models.dto.CredentialsDTO;


public interface IAuth {
    AuthResponse login(CredentialsDTO credentialsDTO);

}
