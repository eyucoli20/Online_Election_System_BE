package com.electionSystem.candidate;

import com.electionSystem.candidate.dto.CandidateReq;
import com.electionSystem.candidate.dto.CandidateResponse;

import java.util.List;

public interface CandidateService {

    List<CandidateResponse> getAllCandidates();

    Candidate getCandidateById(Long id);

    List<CandidateResponse> getCandidatesByPositionId(Long positionId);

    CandidateResponse createCandidate(CandidateReq candidateReq);

    void deleteCandidate(Long id);
}

