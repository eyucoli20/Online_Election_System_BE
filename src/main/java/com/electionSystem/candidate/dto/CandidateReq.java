package com.electionSystem.candidate.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CandidateReq {

    @NotNull
    private Long userId;

    @NotNull
    private Long positionId;
}
