package br.com.evandro.gestao_vagas.modules.candidate.repositories;

import br.com.evandro.gestao_vagas.modules.candidate.entity.CandidateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CandidateRepository extends JpaRepository<CandidateEntity, UUID> {

    Optional<CandidateEntity> findByUsernameOrEmail(String username, String email);
    // Optional<classEntity> returns a class of the type passed or a null when there is no return of that type

    Optional<CandidateEntity> findByUsername(String username);

}
