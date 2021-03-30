package main;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList; 
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server
{     
    private static ArrayList<ClientHandler> clientes = new ArrayList<>();
    private static ExecutorService pool = Executors.newFixedThreadPool(5);
    
    public static int porta = 0;
    public static String endereco = "";
    
    public static void removerDesconectado() throws IOException
    {   
        // Isso aqui vai rodar até remover todo(s) client(es) faltando. 
        while (true)
        {
            int b = 0;
            try
            {
                for (b = 0; b < clientes.size(); b++)
                {
                    clientes.get(b).saida.writeObject(new ObjetoEnviado(-1, 3, null, "", "", "", ""));
                }
             
                // Se chegar aqui, é porque tá tudo nos trinques, então o 'while (true)' quebra.
                break;
            }
            catch (Exception e)
            {
                clientes.get(b).gambiarraMuitoFoda = false;
                clientes.remove(b);
            }
        }
    }
    
    public static void main(String args[]) throws InterruptedException
    {  
        TelaSetup iniciar = new TelaSetup();
        iniciar.setVisible(true);
        
        while (iniciar.porta == 0)
        {
            Thread.sleep(500);
        }
        
        try 
        {
            // Cria o socket.
            ServerSocket server = new ServerSocket(iniciar.porta, 0, InetAddress.getByName(iniciar.endereco));
            System.out.println("Servidor iniciado em " + server.getInetAddress().getHostAddress() + ":" + iniciar.porta);

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

        }
    }   
}