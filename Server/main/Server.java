package main;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList; 
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server
{     
    private static ArrayList<ClientHandler> clientes = new ArrayList<>();
    private static ExecutorService pool = Executors.newFixedThreadPool(5);
    
    public static void removerDesconectado(String mensagem)
    {
        // Isso aqui vai rodar até remover todo(s) client(es) faltando. 
        while (true)
        {
            int b = 0;
            try
            {
                for (b = 0; b < clientes.size(); b++)
                {
                    clientes.get(b).saida.writeUTF("");
                }
                
                // Se chegar aqui, é porque tá tudo nos trinques, então o 'while (true)' quebra.
                break;
            }
            catch (Exception e)
            {
                clientes.remove(b);
            }
        }
    }
    
    public static void main(String args[])
    {  
        int porta = 25565;
        
        try 
        {
            // Cria o socket.
            ServerSocket server = new ServerSocket(porta, 0, InetAddress.getByName("192.168.0.3"));
            System.out.println("Servidor iniciado em " + server.getInetAddress().getHostAddress() + ":" + porta);

            while (true)
            {
                // Fica esperando por uma conexão ao socket.
                Socket cliente = server.accept();
                System.out.println("Cliente conectado do IP " + cliente.getInetAddress().getHostAddress());
                
                // Cria uma nova instância da conexão, e adiciona ela à lista.
                ClientHandler clientThread = new ClientHandler(cliente, clientes);
                clientes.add(clientThread);
                pool.execute(clientThread);
            }        
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   
}