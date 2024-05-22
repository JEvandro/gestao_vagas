package br.com.evandro.gestao_vagas.modules.candidate.controller;

import br.com.evandro.gestao_vagas.exceptions.UserFoundException;
import br.com.evandro.gestao_vagas.modules.candidate.DTO.ProfileCandidateDTO;
import br.com.evandro.gestao_vagas.modules.candidate.entity.ApplyJobEntity;
import br.com.evandro.gestao_vagas.modules.candidate.entity.CandidateEntity;
import br.com.evandro.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import br.com.evandro.gestao_vagas.modules.candidate.usecases.ApplyJobCandidateUseCase;
import br.com.evandro.gestao_vagas.modules.candidate.usecases.CandidateUseCase;
import br.com.evandro.gestao_vagas.modules.candidate.usecases.ListAllJobsByFilterUseCase;
import br.com.evandro.gestao_vagas.modules.candidate.usecases.ProfileCandidateUseCase;
import br.com.evandro.gestao_vagas.modules.company.entities.JobEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
/*
*Annotation that defines that class below will be a controller rest, focused on web application development
*and will support HTTPMethods
 */
@RequestMapping("/candidate") //Define the path in URL to this Controller
@Tag(name = "Candidato", description = "Informações do candidato")
public class CandidateController {

    @Autowired //Annotation that tells to Spring to manage the life cycle this object and uses IOC/ID
    private CandidateUseCase candidateUseCase;

    @Autowired
    private ProfileCandidateUseCase profileCandidateUseCase;

    @Autowired
    private ListAllJobsByFilterUseCase listAllJobsByFilterUseCase;

    @Autowired
    private ApplyJobCandidateUseCase applyJobCandidateUseCase;

    @PostMapping("/") //HTTPMethod POST with the path to access him
    @Operation(summary = "Cadastro de candidato", description = "Essa função é responsável por cadastrar o candidato")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(
                            schema = @Schema(implementation = CandidateEntity.class)
                    )
            }),
            @ApiResponse(responseCode = "400", description = "Usuário já existe")
    })
    /*
     * @Valid -> Annotation that tells to Spring validate entity parameters
     * @RequestBody -> Defines the type parameter Body, will use in the body url/request body
    * */
    public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidateEntity){
        try{
            var result = candidateUseCase.execute(candidateEntity);
            return ResponseEntity.ok().body(result);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Perfil do candidato", description = "Essa função é responsável por buscar as informações do perfil")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(
                            schema = @Schema(implementation = ProfileCandidateDTO.class)
                    )
            }),
            @ApiResponse(responseCode = "400", description = "User not found")
    })
    public ResponseEntity<Object> get(HttpServletRequest request){

        var candidateId = request.getAttribute("candidate_id");

        try{
            var profile = profileCandidateUseCase.execute(UUID.fromString(candidateId.toString()));
            return ResponseEntity.ok().body(profile);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/job")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Listagem de Vagas disponível para o candidato", description = "Essa função é responsável por listar todas as vagas disponíveis, baseada no filtro")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(
                            array = @ArraySchema(schema = @Schema(implementation = JobEntity.class))
                    )
            })
    })
    @SecurityRequirement(name = "jwt_auth")
    public List<JobEntity> findJobByFilter(@RequestParam String filter){
        return listAllJobsByFilterUseCase.execute(filter);
    }

    @PostMapping("/job/apply")
    @PreAuthorize("hasRole('Candidate')")
    @Operation(summary = "Inscrição de candidato para uma vaga", description = "Essa função é responsável por realizar a inscrição de um candidato a vaga")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(
                            schema = @Schema(implementation = ApplyJobEntity.class)
                    )
            }),
            @ApiResponse(responseCode = "400", description = "Candidate don´t exists"),
            @ApiResponse(responseCode = "400", description = "Job don´t exists")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> applyJob(HttpServletRequest request, @RequestBody UUID idJob){
        var idCandidate = request.getAttribute("candidate_id");

        try{
            var result = applyJobCandidateUseCase.execute(UUID.fromString(idCandidate.toString()), idJob);
            return ResponseEntity.ok().body(result);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
