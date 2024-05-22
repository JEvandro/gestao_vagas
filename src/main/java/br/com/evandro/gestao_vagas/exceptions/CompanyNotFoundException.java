package br.com.evandro.gestao_vagas.exceptions;

public class CompanyNotFoundException extends RuntimeException{

    public  CompanyNotFoundException(){
        super("company not found");
    }

}
