package main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable
{
    public Socket cliente;
    public ObjectOutputStream saida;
    public ArrayList<ClientHandler> clientes;
    
    public ClientHandler(Socket clientSocket, ArrayList<ClientHandler> clientes) throws IOException
    {
        this.cliente = clientSocket;
        this.clientes = clientes;
        saida = new ObjectOutputStream(this.cliente.getOutputStream());
    }
    
    private void verificarTodos() throws IOException
    {
        for (ClientHandler Cliente : clientes)
        {   
            Cliente.saida.writeObject(new ObjetoEnviado(-1, 3, null, "", "", "", ""));
        }
    }
    
    private void enviarParaTodos(ObjetoEnviado msg) throws IOException
    {
        for (ClientHandler Cliente : clientes)
        {
            System.out.println("enviando: " + msg.getTipo());
            Cliente.saida.writeObject(msg);
        }
    }
    
    @Override
    public void run() 
    {
        while (true)
        {   
            ObjetoEnviado msg = null;
            try 
            {
                ObjectInputStream inputStream = new ObjectInputStream(this.cliente.getInputStream());
                
                msg = (ObjetoEnviado) inputStream.readObject();

                verificarTodos();
                enviarParaTodos(msg);
            }           
            catch(IOException e) 
            {
                // Vai cair aqui se o 'verificarTodos()' der erro, porque um dos clientes ficou offline.
                Server.removerDesconectado();
                
                try 
                {
                    enviarParaTodos(msg);
                } 
                catch (IOException ex) 
                {

                }
            }
            catch (ClassNotFoundException e) 
            {
                
            }
        }
    }   
}
