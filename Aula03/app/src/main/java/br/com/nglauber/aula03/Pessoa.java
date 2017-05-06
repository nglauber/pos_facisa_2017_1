package br.com.nglauber.aula03;

import org.parceler.Parcel;

@Parcel
public class Pessoa {

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
