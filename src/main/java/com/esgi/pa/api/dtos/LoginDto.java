package com.esgi.pa.api.dtos;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Builder;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

@Builder
@JsonAutoDetect(fieldVisibility = ANY)
public record LoginDto(String token) {}
