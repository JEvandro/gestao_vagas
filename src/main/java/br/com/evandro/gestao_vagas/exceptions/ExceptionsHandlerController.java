package br.com.evandro.gestao_vagas.exceptions;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice //Defines that the Class Controller will treatment the exception throw before that exception be presents to client
public class ExceptionsHandlerController {

    private MessageSource messageSource;

    // necessary construct the constructor for the SpringBoot when initialize no put a null in messageSource
    public ExceptionsHandlerController(MessageSource messageSource){
        this.messageSource = messageSource;
    }

    /*
    * @ExceptionHandler() -> Instructs to SpringBoot for which exception him wil execute the method below.
    * This Method below will treatment a message returning the field which is going wrong and a respective message.
    * */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ExceptionHandlerDTO>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        List<ExceptionHandlerDTO> dto = new ArrayList<>();

        e.getBindingResult().getFieldErrors().forEach( err ->{
            String message = messageSource.getMessage(err, LocaleContextHolder.getLocale());
            dto.add(new ExceptionHandlerDTO(message, err.getField()));
        });

        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }
    /*
    * getBindingResult() -> will have access all errors with object of the interface BindingResult that extends interface Errors
    * getFieldErrors() -> will capture all fields of errors and return a list of errors
    * forEach() -> will scroll through each error of the list using an iterator
    *
    * Instantiation of ExceptionHandlerDTO will receiver two arguments, the message and the field of the error
    *
    * function lambda{
    * Is a function that is used at the moment that she is called, can have arguments or no, can return some like of return
    * or if no have return will return void. A function with no name, no type of return or modify access
    * }
    *
    * Class MessageSource{
    * The class have a function of basically manage/alter an error message for your necessity
    * }
    * getMessage() -> method of the class MessageSource, return a message of object passed more friendly
    *
    * Class ResponseEntity{
    * SpringBoot of RestController have an Entity of return, when is necessary return type different, but have more
    * The Class ResponseEntity give us a permission to manipulate the response as a whole with more flexibility when we want
    * return another status or content in a body of response
    * }
    * */

}
