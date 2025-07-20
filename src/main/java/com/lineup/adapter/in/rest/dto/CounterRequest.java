package com.lineup.adapter.in.rest.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CounterRequest {

    @NotBlank
    private String counterId;

    private String tokenId;
}
