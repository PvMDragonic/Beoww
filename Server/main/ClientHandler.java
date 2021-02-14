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
    private final Socket cliente;
    private final Scanner entrada;
    private final DataOutputStream saida;
    private ArrayList<ClientHandler> clientes;
    
    public ClientHandler(Socket clientSocket, ArrayList<ClientHandler> clientes) throws IOException
    {
        this.cliente = clientSocket;
        this.clientes = clientes;
        entrada = new Scanner(cliente.getInputStream());
        saida = new DataOutputStream(cliente.getOutputStream());
    }
    
    private void enviarParaTodos(String mensagem) throws IOException
    {
        for (ClientHandler todosClientes : clientes)
        {
            todosClientes.saida.writeUTF(mensagem);
        }
    }
    
    @Override
    public void run() 
    {
        while(entrada.hasNextLine())
        {
            try 
            {
                String mensagem = entrada.nextLine();
                System.out.println(mensagem);
                enviarParaTodos(mensagem);
            } 
            catch (IOException ex) 
            {
                Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        try 
        {
            saida.close();
            entrada.close();
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
