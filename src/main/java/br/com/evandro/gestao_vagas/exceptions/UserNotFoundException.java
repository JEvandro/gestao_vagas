package br.com.evandro.gestao_vagas.exceptions;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(){
        super("Candidade dont exists");
    }

}
