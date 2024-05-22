package br.com.evandro.gestao_vagas.modules.company.usecases;

import br.com.evandro.gestao_vagas.modules.company.dto.AuthCompanyResponseDTO;
import com.auth0.jwt.JWT;
import javax.naming.AuthenticationException;
import br.com.evandro.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import br.com.evandro.gestao_vagas.modules.company.repositories.CompanyRepository;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

@Service
public class AuthCompanyUseCase {

    @Value("${security.token.secret.company}")
    private String secret;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthCompanyResponseDTO execute(AuthCompanyDTO authCompanyDTO) throws AuthenticationException {

        //verifica se company existe
        var company = this.companyRepository.findByUsername(authCompanyDTO.getUsername()).orElseThrow(
                () -> {
                    throw new UsernameNotFoundException("Username/Password incorrect");
                }
        );

        var expiresIn = Instant.now().plus(Duration.ofHours(2));

        /*
        * Verifica se a senha passada e a senha guarda são iguais, senão é lançada uma exceção
        * Se iguais é gerado um token
        * */
        var passwordMatches = this.passwordEncoder.matches(authCompanyDTO.getPassword(), company.getPassword());
        if(!passwordMatches) {
            throw new AuthenticationException();
        }

        Algorithm algorithm = Algorithm.HMAC256(secret);
        var token = JWT.create().withIssuer("javagas")
                .withExpiresAt(expiresIn)
                .withSubject(company.getId().toString())
                .withClaim("roles", Arrays.asList("COMPANY"))
                .sign(algorithm);

        var authCompanyResponseDTO = AuthCompanyResponseDTO.builder()
                .access_token(token)
                .expiresIn(expiresIn.toEpochMilli())
                .build();

        return authCompanyResponseDTO;

    }

}
