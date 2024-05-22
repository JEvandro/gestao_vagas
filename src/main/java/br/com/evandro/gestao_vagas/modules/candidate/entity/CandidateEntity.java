package br.com.evandro.gestao_vagas.modules.candidate.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.UUID;

@Data // Annotation that tell to dependency SpringData create the getters and setters for each attribute of the class
@Entity(name = "candidate") // Annotation that defines the class below is an Entity to the database and create the table in database with name candidate
public class CandidateEntity {

    @Id // Defines the Primary Key database is ID
    @GeneratedValue(strategy = GenerationType.UUID) //Tells to Spring generate the value automatic following the strategy instruct
    private UUID id;

    // The code below tells the validations of the parameters
    @NotBlank
    @Pattern(regexp = "\\S+", message = "O campo [user] não pode conter espaços")
    @Schema(example = "daniel")
    private String username;

    @Schema(example = "Daniel de Almeida")
    private String name;

    // The code below tells the validations of the parameters
    @Email(message = "O campo [email] deve conter umm e-mail válido")
    @Schema(example = "Daniel@gmail.com")
    private String email;

    // The code below tells the validations of the parameters
    @Length(min = 10, max = 100, message = "O campo [password] deve ter de (10 a 100) caracteres")
    @Schema(example = "admin@1234", minLength = 10, maxLength = 100, requiredMode = Schema.RequiredMode.REQUIRED, description = "Senha do candidato")
    private String password;
    @Schema(example = "Técnico em Eletrônica", requiredMode = Schema.RequiredMode.REQUIRED, description = "Breve descrição do candidato")
    private String description;
    private String curriculum;

    @CreationTimestamp
    private LocalDateTime createAt;

}
