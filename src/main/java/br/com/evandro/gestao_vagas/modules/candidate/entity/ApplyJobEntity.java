package br.com.evandro.gestao_vagas.modules.candidate.entity;

import br.com.evandro.gestao_vagas.modules.company.entities.JobEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "apply_job")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplyJobEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "job_id", insertable = false , nullable = false)
    private JobEntity jobEntity;

    @Column(name = "job_id", insertable=false, updatable=false)
    private UUID jobId;

    @ManyToOne
    @JoinColumn(name = "candidate_id", insertable = false , nullable = false)
    private CandidateEntity candidateEntity;

    @Column(name = "candidate_id", insertable=false, updatable=false)
    private UUID candidateId;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
