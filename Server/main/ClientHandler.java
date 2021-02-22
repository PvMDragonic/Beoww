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
    public DataOutputStream saida;
    public ArrayList<ClientHandler> clientes;
    
    public ClientHandler(Socket clientSocket, ArrayList<ClientHandler> clientes) throws IOException
    {
        this.cliente = clientSocket;
        this.clientes = clientes;
        entrada = new Scanner(this.cliente.getInputStream());
        saida = new DataOutputStream(this.cliente.getOutputStream());
    }
    
    private void verificarTodos() throws IOException
    {
        for (ClientHandler Cliente : clientes)
        {
            Cliente.saida.writeUTF("");
        }
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
        while(entrada.hasNextLine())
        {
            String mensagem = null;
            try 
            {
                // Vai ler sempre que chegar uma nova mensagem do servidor.
                mensagem = entrada.nextLine();
                System.out.println(mensagem.replace("[[[[[", "\n"));
                
                verificarTodos();
                enviarParaTodos(mensagem);
            } 
            catch (IOException ex) 
            {
                // Vai cair aqui se o 'verificarTodos()' der erro, porque um dos clientes ficou offline.
                Server.removerDesconectado(mensagem);
                
                try 
                {
                    enviarParaTodos(mensagem);
                } 
                catch (IOException ex1) 
                {
                    Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        }
    }   
}
