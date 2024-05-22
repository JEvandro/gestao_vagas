package br.com.evandro.gestao_vagas.exceptions;

public class JobNotFoundException extends RuntimeException{

    public JobNotFoundException(){
        super("Job dont exists");
    }

}