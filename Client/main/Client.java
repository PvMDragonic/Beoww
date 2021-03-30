package main;

import com.vdurmont.emoji.EmojiParser;
import java.util.Random; 
import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextPane;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.net.ConnectException;
import javax.swing.JOptionPane;
import static org.apache.commons.io.FileUtils.readFileToByteArray;

public class Client extends javax.swing.JFrame 
{
    volatile private static Socket cliente;
    volatile private static String nomeUsuario;  
    volatile private static int id; 
    
    public Client() throws IOException, InterruptedException
    {   
        initComponents();

        InterfaceEmoji.telaDosEmoji();
        InterfaceArquivos.telaDosArquivos();
      
        // Isso aqui tá aqui só porque o NetBeans não deixa editar o 'initComponents()'. 
        setTitle("Conectado como: " + nomeUsuario + " | ID: " + id);

        botaoEmoji.setIcon(resizeIcon(new ImageIcon(getClass().getResource("/main/smiley.png")), 
                botaoEmoji.getWidth() - botaoEmoji.getInsets().left, 
                botaoEmoji.getHeight() - botaoEmoji.getInsets().left));
         
        botaoArquivo.setIcon(resizeIcon(new ImageIcon(getClass().getResource("/main/file.png")), 
                botaoEmoji.getWidth() - botaoEmoji.getInsets().left, 
                botaoEmoji.getHeight() - botaoEmoji.getInsets().left));
        
        botaoEnviar.setIcon(resizeIcon(new ImageIcon(getClass().getResource("/main/send.png")), 
                botaoEmoji.getWidth() - botaoEmoji.getInsets().left, 
                botaoEmoji.getHeight() - botaoEmoji.getInsets().left));
        
        Chat();
        EmojiTempoReal();
    }

    private static void loginCliente() throws IOException, InterruptedException
    {
        TelaLogin login = new TelaLogin();       

        while (true)
        {
            login.setVisible(true);
            
            // Impedir do código continuar a rodar durante a tela de login.
            while (login.porta == 0)
            {                    
                Thread.sleep(500);         
            }

            nomeUsuario = login.nomeUsuario;
            id = new Random().nextInt(100000);
            
            try
            {
                cliente = new Socket(login.endereco, login.porta); // 192.168.0.3:25565
                break;
            }
            catch (ConnectException e)
            {
                login.porta = 0;
                JOptionPane.showMessageDialog(null, 
                    "Houve um erro ao conectar-se com o servidor!", 
                    "Erro de conexão", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }       
    }
    
    private Icon resizeIcon(ImageIcon icon, int resizedWidth, int resizedHeight) 
    {
        Image img = icon.getImage();  
        Image resizedImage = img.getScaledInstance(resizedWidth, resizedHeight,  java.awt.Image.SCALE_SMOOTH);  
        
        return new ImageIcon(resizedImage);
    }
    
    private void EmojiTempoReal()
    {
        new Thread()
        {
            @Override
            public void run() 
            {         
                String antigo = "";
                String novo = "";
                
                while(true)
                {
                    try 
                    {
                        Thread.sleep(200);
                        
                        if (!areaMensagem.getText().equals(""))
                        {   
                            antigo = areaMensagem.getText();

                            novo = EmojiParser.parseToUnicode(antigo);
                            
                            if (!novo.equals(antigo))
                            {
                                areaMensagem.setText(novo);
                                areaMensagem.setCaretPosition(novo.length());                              
                            }
                        } 
                    } 
                    catch (InterruptedException ex) 
                    {
                        Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                    }                                 
                }
            }
        }.start();
    }
    
    private void Chat()
    {
        new Thread() 
        {
            @Override
            public void run() 
            {                     
                ObjectInputStream inputStream = null;
                
                try 
                {
                    inputStream = new ObjectInputStream(cliente.getInputStream());
                } 
                catch (IOException ex) 
                {

                }
                
                while (true)
                {        
                    try 
                    {                         
                        ObjetoEnviado msg = (ObjetoEnviado) inputStream.readObject();
                        
                        switch (msg.getTipo()) 
                        {                           
                            case 0: // Mensagem de texto
                            {
                                // Placeholder muito foda pra fazer gambiarra de ter espaço entre as msg.
                                JTextPane placeHolder = new JTextPane();
                                placeHolder.setPreferredSize(new Dimension(chat.getWidth(), 10));
                                chat.insertComponent(placeHolder);
                                chat.insertComponent(MensagemTexto.Criar(msg, id));                              
                                break;
                            }                           
                            case 1: // Imagem
                            {
                                JTextPane placeHolder = new JTextPane();
                                placeHolder.setPreferredSize(new Dimension(chat.getWidth(), 10));
                                chat.insertComponent(placeHolder);
                                chat.insertComponent(MensagemImagem.Criar(msg, id));
                                break;
                            }                            
                            default: // Ping
                                break;
                        }
                    } 
                    catch (ClassNotFoundException ex) 
                    {
                        
                    }
                    catch (IOException ex) 
                    {
                        
                    }
                    catch (Exception ex)
                    {
                        // Isso aqui tá aqui por causa de um furo que causa um crash.
                        // Essa gambiarra elimina o crash, e o bug não afeta o funcionamento.
                    }
                }
            }
        }.start();
    }  
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        chat = new javax.swing.JTextPane();
        jPanel1 = new javax.swing.JPanel();
        botaoEmoji = new javax.swing.JButton();
        botaoArquivo = new javax.swing.JButton();
        botaoEnviar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        areaMensagem = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setAutoscrolls(true);
        jScrollPane1.setHorizontalScrollBar(null);

        chat.setEditable(false);
        chat.setFocusable(false);
        chat.setMargin(new java.awt.Insets(5, 3, 3, 3));
        chat.setMaximumSize(new java.awt.Dimension(1, 2147483647));
        chat.setMinimumSize(new java.awt.Dimension(1, 6));
        chat.setPreferredSize(new java.awt.Dimension(1, 20));
        jScrollPane1.setViewportView(chat);

        botaoEmoji.setBackground(new java.awt.Color(100, 105, 110));
        botaoEmoji.setForeground(new java.awt.Color(80, 80, 80));
        botaoEmoji.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoEmojiActionPerformed(evt);
            }
        });

        botaoArquivo.setBackground(new java.awt.Color(100, 105, 110));
        botaoArquivo.setForeground(new java.awt.Color(80, 80, 80));
        botaoArquivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoArquivoActionPerformed(evt);
            }
        });

        botaoEnviar.setBackground(new java.awt.Color(100, 105, 110));
        botaoEnviar.setForeground(new java.awt.Color(80, 80, 80));
        botaoEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoEnviarActionPerformed(evt);
            }
        });

        areaMensagem.setColumns(20);
        areaMensagem.setFont(new java.awt.Font("Segoe UI Emoji", 0, 14)); // NOI18N
        areaMensagem.setLineWrap(true);
        areaMensagem.setRows(5);
        areaMensagem.setWrapStyleWord(true);
        areaMensagem.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        jScrollPane2.setViewportView(areaMensagem);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(botaoEmoji, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botaoArquivo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botaoEnviar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(botaoEmoji, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botaoArquivo, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botaoEnviar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 1, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public static void EnviarArquivo(String enderecoArqv) throws FileNotFoundException, IOException
    {          
        String extArquivo = ExtrairDeString.extrair(enderecoArqv, '.');
        String nomeArquivo = ExtrairDeString.extrair(enderecoArqv, '\\');
        
        File arqv = new File(enderecoArqv);
        
        byte[] img = readFileToByteArray(arqv);
               
        try
        {
            ObjectOutputStream outputStream = new ObjectOutputStream(cliente.getOutputStream());
            
            outputStream.writeObject(new ObjetoEnviado(
                id, 
                1, 
                img,
                "", 
                "<b>" + nomeUsuario + ": </b>",
                extArquivo, 
                nomeArquivo));
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, 
                "Houve um erro na comunicação com o servidor!\nO cliente será encarrado.", 
                "Servidor off-line", 
                JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }     
    }
    
    private void botaoEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoEnviarActionPerformed

        String msg = areaMensagem.getText();    
        
        if (!msg.equals(""))
        {
            try 
            {
                ObjectOutputStream outputStream = new ObjectOutputStream(cliente.getOutputStream());

                outputStream.writeObject(new ObjetoEnviado(
                        id,
                        0,
                        null,
                        msg,
                        "<b>" + nomeUsuario + ": </b>",
                        null,
                        null));

                areaMensagem.setText("");
                
                // Caso a pessoa abra a interface, e clique para enviar uma msg.
                InterfaceArquivos.desativar();
                InterfaceEmoji.desativar();
            } 
            catch (IOException ex) 
            {
                JOptionPane.showMessageDialog(null, 
                    "Houve um erro na comunicação com o servidor!\nO cliente será encarrado.", 
                    "Servidor off-line", 
                    JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
        }
    }//GEN-LAST:event_botaoEnviarActionPerformed

    private void botaoArquivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoArquivoActionPerformed
        if (InterfaceEmoji.ativo()) InterfaceEmoji.desativar();
        
        if (InterfaceArquivos.ativo()) InterfaceArquivos.desativar();
        else InterfaceArquivos.ativar();
    }//GEN-LAST:event_botaoArquivoActionPerformed

    private void botaoEmojiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoEmojiActionPerformed
        if (InterfaceArquivos.ativo()) InterfaceArquivos.desativar();
        
        if (InterfaceEmoji.ativo()) InterfaceEmoji.desativar();
        else InterfaceEmoji.ativar();
    }//GEN-LAST:event_botaoEmojiActionPerformed

    public static void main(String args[]) throws InterruptedException, IOException
    {
        loginCliente();      
        
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
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) 
        {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> 
        {
            try 
            {      
                new Client().setVisible(true);
            } 
            catch (IOException | InterruptedException ex) 
            {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        });        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static volatile javax.swing.JTextArea areaMensagem;
    private javax.swing.JButton botaoArquivo;
    private javax.swing.JButton botaoEmoji;
    private javax.swing.JButton botaoEnviar;
    public static javax.swing.JTextPane chat;
    public static javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}