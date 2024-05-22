package br.com.evandro.gestao_vagas.modules.candidate.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthCandidateResponseDTO {

    private String access_token;
    private Long expiresIn;

}
