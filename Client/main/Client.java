package main;

import java.util.Random; 
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import static org.apache.commons.io.FileUtils.readFileToByteArray;

public class Client extends javax.swing.JFrame 
{
    private static Socket cliente;
    volatile private static String nomeUsuario;  
    volatile private static int id; 
    
    public Client() throws IOException
    {
        initComponents();
        initCliente();
        InterfaceEmoji.telaDosEmoji();
        InterfaceArquivos.telaDosArquivos();
      
        // Isso aqui tá aqui só porque o NetBeans não deixa editar o 'initComponents()'.
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - getHeight()) / 2);
        setLocation(x, y);
        
        areaMensagem.setLineWrap(true);
        areaMensagem.setWrapStyleWord(true);
        
        botaoEmoji.setIcon(resizeIcon(new ImageIcon(getClass().getResource("/main/smiley.png")), 
                botaoEmoji.getWidth() - botaoEmoji.getInsets().left, 
                botaoEmoji.getHeight() - botaoEmoji.getInsets().left));
         
        botaoArquivo.setIcon(resizeIcon(new ImageIcon(getClass().getResource("/main/file.png")), 
                botaoEmoji.getWidth() - botaoEmoji.getInsets().left, 
                botaoEmoji.getHeight() - botaoEmoji.getInsets().left));
        
        botaoEnviar.setIcon(resizeIcon(new ImageIcon(getClass().getResource("/main/send.png")), 
                botaoEmoji.getWidth() - botaoEmoji.getInsets().left, 
                botaoEmoji.getHeight() - botaoEmoji.getInsets().left));
        
        dadosUsuario();
        Chat();
    }
    
    private void dadosUsuario()
    {
        while (true)
        {
            nomeUsuario = JOptionPane.showInputDialog(null, 
                    "Insira seu nome de usuário:", 
                    "Nome de usuário", 
                    JOptionPane.QUESTION_MESSAGE);
            
            if (nomeUsuario.isEmpty())
            {
                JOptionPane.showMessageDialog(null, 
                        "Você não inseriu um nome válido!", 
                        "Nome inválido", 
                        JOptionPane.ERROR_MESSAGE);
            }
            else
            {                              
                Random rand = new Random();
                id = rand.nextInt(100000);
                
                setTitle("Conectado como: " + nomeUsuario + " | ID: " + id);
                break;
            }
        } 
    }

    private void initCliente() throws IOException
    {
        cliente = new Socket("192.168.0.3",25565);
    }
    
    private Icon resizeIcon(ImageIcon icon, int resizedWidth, int resizedHeight) 
    {
        Image img = icon.getImage();  
        Image resizedImage = img.getScaledInstance(resizedWidth, resizedHeight,  java.awt.Image.SCALE_SMOOTH);  
        
        return new ImageIcon(resizedImage);
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
                        
                        // Mensagem de texto
                        if (msg.getTipo() == 0)
                        {
                            // Placeholder muito foda pra fazer gambiarra de ter espaço entre as msg.
                            JTextPane placeHolder = new JTextPane();
                            placeHolder.setPreferredSize(new Dimension(chat.getWidth(), 10));
                            chat.insertComponent(placeHolder);                          
                            
                            chat.insertComponent(MensagemTexto.Criar(msg, id));
                        }
                        // Imagem
                        else if (msg.getTipo() == 1)
                        {
                            JTextPane placeHolder = new JTextPane();
                            placeHolder.setPreferredSize(new Dimension(chat.getWidth(), 10));
                            chat.insertComponent(placeHolder);
                            
                            chat.insertComponent(MensagemImagem.Criar(msg, id));
                        }
                        // Ping
                        else
                        {

                        }
                    } 
                    catch (ClassNotFoundException ex) 
                    {
                        
                    }
                    catch (IOException ex) 
                    {
                        
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
        jScrollPane3 = new javax.swing.JScrollPane();
        areaMensagem = new javax.swing.JTextArea();
        botaoEmoji = new javax.swing.JButton();
        botaoArquivo = new javax.swing.JButton();
        botaoEnviar = new javax.swing.JButton();

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

        areaMensagem.setColumns(20);
        areaMensagem.setRows(5);
        areaMensagem.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Digite aqui:"));
        jScrollPane3.setViewportView(areaMensagem);

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
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
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(botaoEmoji, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botaoArquivo, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botaoEnviar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
    }// </editor-fold>//GEN-END:initComponents

    public static void EnviarArquivo(String enderecoArqv) throws FileNotFoundException, IOException
    {          
        String extArquivo = ExtrairDeString.extrair(enderecoArqv, '.');
        String nomeArquivo = ExtrairDeString.extrair(enderecoArqv, '\\');
        
        File arqv = new File(enderecoArqv);
        
        byte[] img = readFileToByteArray(arqv);
        
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
    
    private void botaoEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoEnviarActionPerformed
        try 
        {
            if (!areaMensagem.getText().equals(""))
            {
                ObjectOutputStream outputStream = new ObjectOutputStream(cliente.getOutputStream());
        
                outputStream.writeObject(new ObjetoEnviado(
                        id, 
                        0, 
                        null, 
                        areaMensagem.getText(),
                        "<b>" + nomeUsuario + ": </b>",
                        null, 
                        null));

                areaMensagem.setText("");
            }                     
        } 
        catch (IOException ex) 
        {
            
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

        java.awt.EventQueue.invokeLater(() -> 
        {
            try 
            {
                new Client().setVisible(true);
            } 
            catch (IOException ex) 
            {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        });        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JTextArea areaMensagem;
    private javax.swing.JButton botaoArquivo;
    private javax.swing.JButton botaoEmoji;
    private javax.swing.JButton botaoEnviar;
    public static javax.swing.JTextPane chat;
    public static javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    // End of variables declaration//GEN-END:variables
}