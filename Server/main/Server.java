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
    
    public static void main(String args[])
    {  
        int porta = 25565;
        
        try 
        {
            InetAddress locIP = InetAddress.getByName("192.168.0.3");
            ServerSocket server = new ServerSocket(porta, 0, locIP);
            System.out.println("Servidor iniciado na porta " + porta + " com IP " + locIP);

            while (true)
            {
                Socket cliente = server.accept();
                System.out.println("Cliente conectado do IP " + cliente.getInetAddress().getHostAddress());
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