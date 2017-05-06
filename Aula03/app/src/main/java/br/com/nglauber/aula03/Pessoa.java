package br.com.nglauber.aula03;

import org.parceler.Parcel;

@Parcel
public class Pessoa {

    public static final int RS_FACEBOOK = 0;
    public static final int RS_TWITTER = 1;
    public static final int RS_GPLUS = 2;

    public long id;
    public String nome;
    public int redesocial;

    public Pessoa() {
    }

    public Pessoa(long id, String nome, int redesocial) {
        this.id = id;
        this.nome = nome;
        this.redesocial = redesocial;
    }

    @Override
    public String toString() {
        return nome +" - "+ redesocial;
    }
}
