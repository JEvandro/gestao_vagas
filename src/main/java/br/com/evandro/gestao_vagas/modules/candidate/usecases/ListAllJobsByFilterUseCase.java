package br.com.evandro.gestao_vagas.modules.candidate.usecases;

import br.com.evandro.gestao_vagas.modules.company.entities.JobEntity;
import br.com.evandro.gestao_vagas.modules.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListAllJobsByFilterUseCase {

    @Autowired
    JobRepository jobRepository;

    public List<JobEntity> execute(String filter){
        return this.jobRepository.findByDescriptionContainingIgnoreCase(filter);
    }

}
