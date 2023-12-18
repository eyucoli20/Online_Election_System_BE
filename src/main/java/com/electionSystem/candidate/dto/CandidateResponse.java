package com.electionSystem.candidate.dto;

import com.electionSystem.candidate.Candidate;
import com.electionSystem.userManager.user.Users;
import com.electionSystem.userManager.user.dto.UserResponse;
import lombok.Data;

@Data
public class CandidateResponse {

    private Long id;
    private String user;
    private String position;
    private Integer voteCount;

    public static CandidateResponse toResponse(Candidate candidate) {

        CandidateResponse candidateResponse = new CandidateResponse();
        candidateResponse.setId(candidate.getId());
        candidateResponse.setUser(candidate.getUser().getFullName());
        candidateResponse.setPosition(candidate.getPosition().getName());
        candidateResponse.setVoteCount(candidate.getVotes() != null ? candidate.getVotes().size() : 0);

        return candidateResponse;
    }

}
