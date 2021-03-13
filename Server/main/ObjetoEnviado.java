package main;

import java.io.Serializable;

public class ObjetoEnviado implements Serializable
{
    private int id;
    private int tipo;
    private byte[] arqv;
    private String msg;
    private String nomeUser;
    private String arqvExt;
    private String arqvNome;
    
    public ObjetoEnviado(int id, int tipo, byte[] arqv, String msg, String nomeUser, String arqvExt, String arqvNome)
    {
        this.id = id;
        this.tipo = tipo;
        this.arqv = arqv;
        this.msg = msg;
        this.arqvExt = arqvExt;
        this.arqvNome = arqvNome;
        this.nomeUser = nomeUser;
    }
    
    public int getId()
    {
        return this.id;
    }
    
    public int getTipo()
    {
        return this.tipo;
    }
    
    public byte[] getArqv()
    {
        return this.arqv;
    }
    
    public String getMsg()
    {
        return this.msg;
    }
    
    public String getNomeUser()
    {
        return this.nomeUser;
    }
    
    public String getArqvExt()
    {
        return this.arqvExt;
    }
    
    public String getArqvNome()
    {
        return this.arqvNome;
    }  
}
