package br.com.evandro.gestao_vagas.modules.candidate.usecases;

import br.com.evandro.gestao_vagas.exceptions.UserFoundException;
import br.com.evandro.gestao_vagas.modules.candidate.entity.CandidateEntity;
import br.com.evandro.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service //Tells the Spring that class below will be manage for him and tells that is a service, a component and help the use
public class CandidateUseCase {

    @Autowired //Annotation that tells to Spring to manage the life cycle this object and uses IOC/ID
    private CandidateRepository candidateRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public CandidateEntity execute(CandidateEntity candidateEntity){
        this.candidateRepository
                .findByUsernameOrEmail(candidateEntity.getUsername(), candidateEntity.getEmail())
                .ifPresent((user) -> {
                    throw new UserFoundException();
                });

        var password = passwordEncoder.encode(candidateEntity.getPassword());
        candidateEntity.setPassword(password);


        return this.candidateRepository.save(candidateEntity);
    }

}
