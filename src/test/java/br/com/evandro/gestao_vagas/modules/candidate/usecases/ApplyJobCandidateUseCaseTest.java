package br.com.evandro.gestao_vagas.modules.candidate.usecases;

import br.com.evandro.gestao_vagas.exceptions.JobNotFoundException;
import br.com.evandro.gestao_vagas.exceptions.UserNotFoundException;
import br.com.evandro.gestao_vagas.modules.candidate.entity.ApplyJobEntity;
import br.com.evandro.gestao_vagas.modules.candidate.entity.CandidateEntity;
import br.com.evandro.gestao_vagas.modules.candidate.repositories.ApplyJobRepository;
import br.com.evandro.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import br.com.evandro.gestao_vagas.modules.company.entities.JobEntity;
import br.com.evandro.gestao_vagas.modules.company.repositories.JobRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ApplyJobCandidateUseCaseTest {

    @InjectMocks
    private ApplyJobCandidateUseCase applyJobCandidateUseCase;

    @Mock
    private CandidateRepository candidateRepository;

    @Mock
    private JobRepository jobRepository;

    @Mock
    private ApplyJobRepository applyJobRepository;

    @Test
    @DisplayName("should not be able to aply job with candidate not found")
    public void should_not_be_able_to_aply_job_with_candidate_not_found(){
        try{
            applyJobCandidateUseCase.execute(null, null);
        }catch (Exception e){
            assertThat(e).isInstanceOf(UserNotFoundException.class);
        }
    }

    @Test
    @DisplayName("should_not_be_able_to_aply_job_with_job_not_found")
    public void should_not_be_able_to_aply_job_with_job_not_found(){
        var idCandidate = UUID.randomUUID();

        CandidateEntity candidate = new CandidateEntity();
        candidate.setId(idCandidate);

        when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(candidate));

        try{
            applyJobCandidateUseCase.execute(idCandidate, null);
        } catch(Exception e){
            assertThat(e).isInstanceOf(JobNotFoundException.class);
        }

    }

    @Test
    @DisplayName("should be able to create a new apply job")
    public void should_be_able_to_create_a_new_apply_job(){
        var idCandidate = UUID.randomUUID();
        var idJob = UUID.randomUUID();

        var applyJob = ApplyJobEntity.builder()
                .candidateId(idCandidate)
                .jobId(idJob)
                .build();

        var applyJobCreated = ApplyJobEntity.builder().id(UUID.randomUUID()).build();

        when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(new CandidateEntity()));
        when(jobRepository.findById(idJob)).thenReturn((Optional.of(new JobEntity())));

        when(applyJobRepository.save(applyJob)).thenReturn(applyJobCreated);

        var result = applyJobCandidateUseCase.execute(idCandidate, idJob);

        assertThat(result).hasFieldOrProperty("id");
        assertNotNull(result.getId());

    }

}
