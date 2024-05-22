package br.com.evandro.gestao_vagas.modules.candidate.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileCandidateDTO {

    @Schema(example = "Desenvolvedora Java")
    private String description;
    @Schema(example = "maria")
    private String username;
    @Schema(example = "maria@gmail.com")
    private String email;
    private UUID id;
    @Schema(example = "Maria de Souza")
    private String name;

}
