package com.caju.controllers.dto.request;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

public record AccountCreationRequest (@NotBlank @CPF String document, @NotBlank String name) {}
