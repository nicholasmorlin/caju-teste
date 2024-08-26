package com.caju.controllers.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AccountCreationRequest (@NotBlank String document, @NotBlank String name) {}
