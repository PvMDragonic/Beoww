package main;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientHandler implements Runnable
{
    private Socket cliente;
    private Scanner entrada;
    private DataOutputStream saida;
    private ArrayList<ClientHandler> clientes;
    
    public ClientHandler(Socket clientSocket, ArrayList<ClientHandler> clientes) throws IOException
    {
        this.cliente = clientSocket;
        this.clientes = clientes;
        entrada = new Scanner(this.cliente.getInputStream());
        saida = new DataOutputStream(this.cliente.getOutputStream());
    }
    
    private void enviarParaTodos(String mensagem) throws IOException
    {
        for (ClientHandler Cliente : clientes)
        {
            Cliente.saida.writeUTF(mensagem);
        }
    }
    
    @Override
    public void run() 
    {
        while(this.entrada.hasNextLine())
        {
            try 
            {
                String mensagem = this.entrada.nextLine();
                System.out.println(mensagem.replace("[[[[[", "\n"));
                enviarParaTodos(mensagem);
            } 
            catch (IOException ex) 
            {
                Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        try 
        {
            this.entrada.close();
            this.saida.close();
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
