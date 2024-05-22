package br.com.evandro.gestao_vagas.modules.company.controllers;

import br.com.evandro.gestao_vagas.exceptions.CompanyNotFoundException;
import br.com.evandro.gestao_vagas.modules.company.dto.CreateJobDTO;
import br.com.evandro.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.evandro.gestao_vagas.modules.company.repositories.CompanyRepository;
import br.com.evandro.gestao_vagas.utils.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CreateJobControllerTest {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup(){
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    public void should_be_able_to_create_a_new_job() throws Exception {

        var company = CompanyEntity.builder()
                .description("DESCRIPTION_TEST")
                .email("teste@gmail.com")
                .name("COMPANY_NAME")
                .password("0123456789")
                .username("COMPANY_USER").build();

        company = companyRepository.saveAndFlush(company);

        var createJobDTO = CreateJobDTO.builder()
                .description("DESCRIPTION TEST")
                .benefits("BENEFITS TEST")
                .level("LEVEL TEST")
                .build();

        var result = mvc.perform(
                MockMvcRequestBuilders.post("/company/job/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.objectToJSON(createJobDTO))
                        .header("Authorization", TestUtils.generateToken(
                                company.getId(),"JAVAGAS_@123#")))
                .andExpect(MockMvcResultMatchers.status().isOk());

        System.out.println(result);
    }

    @Test
    @DisplayName("should not be able to create a new job if company not found")
    public void should_not_be_able_to_create_a_new_job_if_company_not_found() throws Exception {
        var createJobDTO = CreateJobDTO.builder()
                .description("DESCRIPTION TEST")
                .benefits("BENEFITS TEST")
                .level("LEVEL TEST")
                .build();

        mvc.perform(MockMvcRequestBuilders.post("/company/job/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.objectToJSON(createJobDTO))
                .header("Authorization", TestUtils.generateToken(
                        UUID.randomUUID(), "JAVAGAS_@123#")))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}
