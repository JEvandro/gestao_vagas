package br.com.evandro.gestao_vagas.modules.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
public class CreateJobDTO {

    @Schema(example = "Vaga Para pessoa Técnica em Eletrônica", requiredMode = Schema.RequiredMode.REQUIRED)
    @Getter
    private String description;

    @Schema(example = "Plano de saúde, Vale transporte", requiredMode = Schema.RequiredMode.REQUIRED)
    private String benefits;

    @Schema(example = "Junior", requiredMode = Schema.RequiredMode.REQUIRED)
    private String level;

}
