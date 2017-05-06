package br.com.nglauber.aula03.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PessoaDbHelper extends SQLiteOpenHelper {
    public static final String NOME_BANCO = "dbPessoa";
    public static final int VERSAO_BANCO = 1;

    public PessoaDbHelper(Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(String.format(
                "CREATE TABLE %s (" +
                "%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                "%s TEXT NOT NULL," +
                "%s INTEGER NOT NULL)",
                DbContract.Pessoa.TABELA,
                DbContract.Pessoa.CAMPO_ID,
                DbContract.Pessoa.CAMPO_NOME,
                DbContract.Pessoa.CAMPO_REDE_SOCIAL));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
