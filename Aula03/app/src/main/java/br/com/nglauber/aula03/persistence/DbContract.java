package br.com.nglauber.aula03.persistence;

public interface DbContract {
    interface Pessoa {
        String TABELA = "pessoa";
        String CAMPO_ID = "_id";
        String CAMPO_NOME = "nome";
        String CAMPO_REDE_SOCIAL = "rede_social";
    }
}
