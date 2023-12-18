package com.electionSystem.vote.dto;

import lombok.Data;

@Data
public class CandidateVoteResult {
    private Long candidateId;
    private String candidateName;
    private int voteCount;
}