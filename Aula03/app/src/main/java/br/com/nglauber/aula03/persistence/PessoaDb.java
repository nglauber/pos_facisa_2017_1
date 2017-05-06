package br.com.nglauber.aula03.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.nglauber.aula03.Pessoa;

import static br.com.nglauber.aula03.persistence.DbContract.Pessoa.*;

public class PessoaDb {

    PessoaDbHelper helper;

    public PessoaDb(Context ctx) {
        helper = new PessoaDbHelper(ctx);
    }

    public List<Pessoa> listar(){
        List<Pessoa> list = new ArrayList<>();

        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM "+ TABELA +" ORDER BY "+ CAMPO_NOME, null);

        int idxId = cursor.getColumnIndex(CAMPO_ID);
        int idxNome = cursor.getColumnIndex(CAMPO_NOME);
        int idxRedeSocial = cursor.getColumnIndex(CAMPO_REDE_SOCIAL);

        while (cursor.moveToNext()) {
            Pessoa pessoa = new Pessoa(
                cursor.getLong(idxId),
                cursor.getString(idxNome),
                cursor.getInt(idxRedeSocial)
            );
            list.add(pessoa);
        }

        cursor.close();

        db.close();

        return list;
    }

    public void excluir(Pessoa pessoa){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(TABELA, CAMPO_ID +" = ?",
                new String[]{ String.valueOf(pessoa.id) });
        db.close();
    }

    public void salvar(Pessoa pessoa){
        salvar(pessoa, false);
    }

    public void salvar(Pessoa pessoa, boolean forceInsert) {
        if (pessoa.id == 0 || forceInsert){
            inserir(pessoa);
        } else {
            atualizar(pessoa);
        }
    }

    private void inserir(Pessoa pessoa){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.insert(TABELA, null, getValues(pessoa));
        db.close();
    }

    private void atualizar(Pessoa pessoa){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.update(TABELA, getValues(pessoa), CAMPO_ID +" = ?",
                new String[]{ String.valueOf(pessoa.id) });
        db.close();
    }

    private ContentValues getValues(Pessoa pessoa) {
        ContentValues cv = new ContentValues();
        cv.put(CAMPO_NOME, pessoa.nome);
        cv.put(CAMPO_REDE_SOCIAL, pessoa.redesocial);
        return cv;
    }
}
