package com.electionSystem.vote;

import com.electionSystem.vote.dto.CandidateVoteResult;
import com.electionSystem.vote.dto.VoteRequest;

import java.util.List;


public interface VoteService {
    List<Vote> getVotesByCandidateId(Long candidateId);

    void vote(VoteRequest voteRequest);

    List<CandidateVoteResult> getVoteResultsByPositionId(Long positionId);
}


