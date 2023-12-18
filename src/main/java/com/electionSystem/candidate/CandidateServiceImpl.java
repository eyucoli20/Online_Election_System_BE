package com.electionSystem.candidate;

import com.electionSystem.candidate.dto.CandidateReq;
import com.electionSystem.candidate.dto.CandidateResponse;
import com.electionSystem.exceptions.customExceptions.ResourceAlreadyExistsException;
import com.electionSystem.exceptions.customExceptions.ResourceNotFoundException;
import com.electionSystem.position.Position;
import com.electionSystem.position.PositionService;
import com.electionSystem.userManager.user.UserService;
import com.electionSystem.userManager.user.Users;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepository candidateRepository;
    private final UserService userService;
    private final PositionService positionService;

    public CandidateServiceImpl(CandidateRepository candidateRepository, UserService userService, PositionService positionService) {
        this.candidateRepository = candidateRepository;
        this.userService = userService;
        this.positionService = positionService;
    }

    @Override
    public List<CandidateResponse> getAllCandidates() {
        List<Candidate> candidateList = candidateRepository.findAll();

        return candidateList
                .stream()
                .map(CandidateResponse::toResponse)
                .toList();
    }

    @Override
    public Candidate getCandidateById(Long id) {
        return candidateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate not found"));

    }

    @Override
    public List<CandidateResponse> getCandidatesByPositionId(Long positionId) {
        List<Candidate> candidateList = candidateRepository.findByPositionId(positionId);

        return candidateList
                .stream()
                .map(CandidateResponse::toResponse)
                .toList();
    }

    @Override
    public CandidateResponse createCandidate(CandidateReq candidateReq) {

        Long userId = candidateReq.getUserId();
        Long positionId = candidateReq.getPositionId();

        Optional<Candidate> existingCandidate = candidateRepository.findByUserId(userId);
        if (existingCandidate.isPresent())
            throw new ResourceAlreadyExistsException("The user is already a candidate for the " + existingCandidate.get().getPosition().getName() + " position.");

        Users user = userService.getUserById(userId);
        Position position = positionService.getPositionById(positionId);

        Candidate candidate = new Candidate(user, position);
        candidate = candidateRepository.save(candidate);
        return CandidateResponse.toResponse(candidate);
    }

    @Override
    public void deleteCandidate(Long id) {
        getCandidateById(id);
        candidateRepository.deleteById(id);
    }

}
