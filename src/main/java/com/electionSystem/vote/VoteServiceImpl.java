package com.electionSystem.vote;

import com.electionSystem.candidate.Candidate;
import com.electionSystem.candidate.CandidateService;
import com.electionSystem.candidate.dto.CandidateResponse;
import com.electionSystem.exceptions.customExceptions.ResourceAlreadyExistsException;
import com.electionSystem.position.Position;
import com.electionSystem.position.PositionService;
import com.electionSystem.userManager.user.UserService;
import com.electionSystem.userManager.user.Users;
import com.electionSystem.vote.dto.CandidateVoteResult;
import com.electionSystem.vote.dto.VoteRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;
    private final CandidateService candidateService;
    private final UserService userService;
    private final PositionService positionService;

    @Autowired
    public VoteServiceImpl(VoteRepository voteRepository, CandidateService candidateService, UserService userService, PositionService positionService) {
        this.voteRepository = voteRepository;
        this.candidateService = candidateService;
        this.userService = userService;
        this.positionService = positionService;
    }

    @Override
    public List<Vote> getVotesByCandidateId(Long candidateId) {
        return voteRepository.findByCandidateId(candidateId);
    }

    @Override
    public void vote(VoteRequest voteRequest) {

        Long voterId = voteRequest.getVoterId();
        Long candidateId = voteRequest.getCandidateId();
        Long positionId = voteRequest.getPositionId();

        System.out.println(voteRepository.existsByVoterIdAndPositionId(voterId, positionId));

        // Check if the user has already voted for the position
        if (voteRepository.existsByVoterIdAndPositionId(voterId, positionId))
            throw new ResourceAlreadyExistsException("Already voted for this position");

        Users voter = userService.getUserById(voterId);
        Candidate candidate = candidateService.getCandidateById(candidateId);
        Position position = positionService.getPositionById(positionId);

        Vote vote = new Vote(voter, candidate,position);
        voteRepository.save(vote);

        // Update the candidate's votes
        List<Vote> candidateVotes = candidate.getVotes();
        candidateVotes.add(vote);
        candidate.setVotes(candidateVotes);
    }

    @Override
    public List<CandidateVoteResult> getVoteResultsByPositionId(Long positionId) {
        List<CandidateResponse> candidates = candidateService.getCandidatesByPositionId(positionId);

        return candidates.stream()
                .map(candidate -> {
                    List<Vote> votes = voteRepository.findByCandidateId(candidate.getId());
                    int voteCount = votes.size();

                    CandidateVoteResult result = new CandidateVoteResult();
                    result.setCandidateId(candidate.getId());
                    result.setCandidateName(candidate.getUser());
                    result.setVoteCount(voteCount);

                    return result;
                })
                .toList();
    }

}

