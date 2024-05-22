package br.com.evandro.gestao_vagas.modules.company.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "Job")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Schema(example = "Vaga Para Design")
    private String description;

    @NotBlank(message = "Esse campo é obrigatório")
    @Schema(example = "Senior")
    private String level;

    @Schema(example = "Vale Alimentação")
    private String benefits;

    @ManyToOne()
    @JoinColumn(name = "id_company", insertable = false , nullable = false)
    private CompanyEntity companyEntity;

    @Column(name = "id_company", insertable = false , updatable = false)
    private UUID idCompany;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
