package main;

import java.awt.Dimension;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;

public class Client extends javax.swing.JFrame 
{
    private Socket cliente;
    private String nomeUsuario;

    public Client() throws IOException
    {
        initComponents();
        initCliente();
        
        nomeUsuario = JOptionPane.showInputDialog("Insira seu nome de usuário:");
        setTitle("Conectado como: " + nomeUsuario);

        Chat();
    }

    private void initCliente()
    {
        try 
        {
            this.cliente = new Socket("192.168.0.3",25565);
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private int contadorOcorrencia(String txtProcurado, String txtAlvo)
    {
        int tamProcurado = txtProcurado.length();         
        int tamAlvo = txtAlvo.length();         
        int tamanho = 0; 
  
        for (int i = 0; i <= tamAlvo - tamProcurado; i++) 
        { 
            int j;             
            for (j = 0; j < tamProcurado; j++) 
            { 
                if (txtAlvo.charAt(i + j) != txtProcurado.charAt(j)) 
                { 
                    break; 
                } 
            } 
   
            if (j == tamProcurado) 
            {                 
                tamanho++;                 
                j = 0;                 
            }             
        }         
        return tamanho;  
    }
    
    private int numeroDeChars(String s)
    {
        int numero = 0;
        
        for(int i = 0; i < s.length(); i++) 
        {    
            if(s.charAt(i) != ' ') numero++;    
        } 
        
        return numero;
    }
    
    private void Chat()
    {
        new Thread() 
        {
            @Override
            public void run() 
            {     
                while (true)
                {      
                    try 
                    {                           
                        // Cria a conexão com o servidor pra receber as coisas.
                        DataInputStream msg = new DataInputStream(cliente.getInputStream());                     
                        
                        // Simples container pra botar o texto.
                        JTextArea texto = new JTextArea();
                        texto.setEditable(false);
                        texto.setFocusable(false);
                        
                        // Lê a mensagem da conexão; toda vez que o 'readUTF()' é chamado, ele "tira" fora 
                        // uma das coisas que tavam na fila para serem recebidas pelo client. Por isso, eu tenho
                        // que salvar numa variável, porque toda vez que o 'readUTF()' for chamado, vai ser algo diferente.
                        // Ele também trava o código, porque ele fica esperando algo chegar pra ler.
                        String mem = msg.readUTF();
                        
                        // As mensagens são enviadas com '[[[[[' no lugar das newline porque se eu envio com newline,
                        // o 'readUTF()' vai ativar separado pra cada newline. Por causa disso, eu envio tudo numa só coisa,
                        // e o '.split()' já remove na hora da separação. 
                        String[] bruh = mem.split("\\[\\[\\[\\[\\[");
                        
                        // Reutilizo o mem, agora que a informação já foi pra variável 'bruh'.
                        mem = "";
                        
                        for (String bruh1 : bruh) 
                        {
                            // Cai aqui se a string for longa demais pra uma só linha.
                            if (numeroDeChars(bruh1) > 52) 
                            {
                                final int meio = bruh1.length() / 2; 
                                String[] partes = {bruh1.substring(0, meio), bruh1.substring(meio)};
                                mem = mem + partes[0] + "\n" + partes[1];
                            } 
                            else 
                            {
                                if (mem.equals("")) mem = bruh1;
                                else mem = mem + "\n" + bruh1;
                            }
                        }
                        
                        DateTimeFormatter data = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");  
                        LocalDateTime agora = LocalDateTime.now();          
                        mem = data.format(agora) + "\n" + mem;
                                                
                        texto.setText(mem);
                        
                        // Se eu não seto o tamanho do 'texto', o programa vai ficar colocando todos na mesma row até ela encher, pra aí passar pra debaixo.
                        // Pra isso não acontecer, eu tenho que setar um tamanho fixo; a horizontal eu usei o tamanho do 'chat', mas pra vertical tem que
                        // ser dinâmico, porque cada mensagem pode variar de tamanho.
                        // Essa equação fodida aí faz com que tudo funcione nos trinques, usando como base a ideia de adicionar mais tamanho dependendo do
                        // número de newlines que a mensagem tem.
                        texto.setPreferredSize(new Dimension(chat.getWidth(), 
                                (int) (((contadorOcorrencia("\n", mem) + 1) * 30) - ((contadorOcorrencia("\n", mem) * 30) * 0.47))));

                        chat.insertComponent(texto);                       
                    } 
                    catch (IOException ex) 
                    {
                        Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }.start();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        areaMensagem = new javax.swing.JTextArea();
        botaoEnviar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        chat = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        areaMensagem.setColumns(20);
        areaMensagem.setTabSize(0);
        areaMensagem.setHighlighter(null);
        jScrollPane2.setViewportView(areaMensagem);

        botaoEnviar.setText("Enviar mensagem");
        botaoEnviar.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        botaoEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoEnviarActionPerformed(evt);
            }
        });

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setAutoscrolls(true);
        jScrollPane1.setHorizontalScrollBar(null);

        chat.setEditable(false);
        chat.setFocusable(false);
        chat.setMaximumSize(new java.awt.Dimension(1, 2147483647));
        chat.setMinimumSize(new java.awt.Dimension(1, 6));
        chat.setPreferredSize(new java.awt.Dimension(1, 20));
        jScrollPane1.setViewportView(chat);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(143, 143, 143)
                        .addComponent(botaoEnviar, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 136, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(botaoEnviar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botaoEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoEnviarActionPerformed
        try 
        {
            PrintStream saida = new PrintStream(this.cliente.getOutputStream());
            String bruh = areaMensagem.getText().replace("\n", "[[[[[");
            saida.println(nomeUsuario + ": " + bruh);
            areaMensagem.setText("");
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_botaoEnviarActionPerformed

    public static void main(String args[])
    {
        try 
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) 
            {
                if ("Nimbus".equals(info.getName())) 
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } 
        catch (ClassNotFoundException ex) 
        {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } 
        catch (InstantiationException ex) 
        {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } 
        catch (IllegalAccessException ex) 
        {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } 
        catch (javax.swing.UnsupportedLookAndFeelException ex) 
        {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            try {
                new Client().setVisible(true);
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        });        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea areaMensagem;
    private javax.swing.JButton botaoEnviar;
    private javax.swing.JTextPane chat;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}