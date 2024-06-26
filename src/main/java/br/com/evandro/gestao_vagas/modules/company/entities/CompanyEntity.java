package br.com.evandro.gestao_vagas.modules.company.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "company")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;

    @NotBlank()
    @Pattern(regexp = "\\S+", message = "O campo [user] não pode conter espaços")
    private String username;

    @Email(message = "O campo [email] deve conter umm e-mail válido")
    private String email;

    @Length(min = 10, max = 100, message = "O campo [password] deve ter de (10 a 100) caracteres")
    private String password;
    private String description;
    private String website;

    @CreationTimestamp
    private LocalDateTime createAt;

}
