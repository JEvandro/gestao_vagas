package br.com.evandro.gestao_vagas.exceptions;

public class UserFoundException extends RuntimeException{
    public UserFoundException(){
        super("Usuario Já Existe");
    }
}
