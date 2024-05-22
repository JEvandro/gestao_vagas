package br.com.evandro.gestao_vagas.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

//DTO -> Data to Object, class will be used for conversion Data to Object
@Data // Annotation that tell to dependency SpringData create the getters and setters for each attribute of the class
@AllArgsConstructor // Defines all constructors of the class, no necessary we build the constructors
public class ExceptionHandlerDTO {

    private String message;
    private String field;

}
