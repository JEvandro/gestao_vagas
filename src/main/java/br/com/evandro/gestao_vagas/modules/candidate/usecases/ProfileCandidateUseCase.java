package br.com.evandro.gestao_vagas.modules.candidate.usecases;

import br.com.evandro.gestao_vagas.modules.candidate.DTO.ProfileCandidateDTO;
import br.com.evandro.gestao_vagas.modules.candidate.entity.CandidateEntity;
import br.com.evandro.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProfileCandidateUseCase {

    @Autowired
    CandidateRepository candidateRepository;

    public ProfileCandidateDTO execute(UUID candidateId){
        var candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() ->{
                    throw new UsernameNotFoundException("User not found");
                });

        var candidateDTO = ProfileCandidateDTO.builder()
                .description(candidate.getDescription())
                .username(candidate.getUsername())
                .email(candidate.getEmail())
                .id(candidate.getId())
                .name(candidate.getName())
                .build();

        return candidateDTO;
    }

}
