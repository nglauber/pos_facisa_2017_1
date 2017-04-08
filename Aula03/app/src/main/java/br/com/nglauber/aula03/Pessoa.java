package br.com.nglauber.aula03;

import java.io.Serializable;

public class Pessoa implements Serializable {

    public static final int RS_FACEBOOK = 0;
    public static final int RS_TWITTER = 1;
    public static final int RS_GPLUS = 2;

    public String nome;
    public int redesocial;

    @Override
    public String toString() {
        return nome +" - "+ redesocial;
    }
}
