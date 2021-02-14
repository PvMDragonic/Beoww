package main;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server
{    
    public static void main(String args[])
    {  
        int porta = 25565;
        
        try 
        {
            InetAddress locIP = InetAddress.getByName("192.168.0.3");
            ServerSocket server = new ServerSocket(porta, 0, locIP);
            System.out.println("Servidor iniciado na porta " + porta + " com IP " + locIP);

            Socket cliente = server.accept();
            System.out.println("Cliente conectado do IP " + cliente.getInetAddress().getHostAddress());
            
            // Isso aqui recebe a mensagem do client.
            Scanner entrada = new Scanner(cliente.getInputStream());
            
            // Isso aqui é pra enviar de volta ao client a mensagem.
            DataOutputStream msg = new DataOutputStream(cliente.getOutputStream());
            
            while(entrada.hasNextLine())
            {
                String mensagem = entrada.nextLine();
                System.out.println(mensagem);
                msg.writeUTF(mensagem);
            }

            entrada.close();
            server.close();
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   
}