package main;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client extends javax.swing.JFrame 
{
    private static Socket cliente;

    public Client() throws IOException
    {
        initComponents();
        initCliente();
        Chat();
    }

    private void initCliente()
    {
        try 
        {
            cliente = new Socket("192.168.0.3",25565);
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void Chat()
    {
        new Thread() 
        {
            @Override
            public void run() 
            {
                DataInputStream msg = null;
                try 
                {
                    msg = new DataInputStream(cliente.getInputStream());
                } 
                catch (IOException ex) 
                {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
                while (true)
                {
                    try 
                    {
                        if (chat.getText().equals("")) chat.setText(msg.readUTF());
                        else chat.setText(chat.getText() + "\n" + msg.readUTF());
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
        jScrollPane3 = new javax.swing.JScrollPane();
        chat = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        areaMensagem.setColumns(20);
        areaMensagem.setRows(10);
        jScrollPane2.setViewportView(areaMensagem);

        botaoEnviar.setText("Enviar mensagem");
        botaoEnviar.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        botaoEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoEnviarActionPerformed(evt);
            }
        });

        chat.setEditable(false);
        chat.setFocusable(false);
        jScrollPane3.setViewportView(chat);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(142, 142, 142)
                        .addComponent(botaoEnviar, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 137, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane3)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(botaoEnviar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botaoEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoEnviarActionPerformed
        try 
        {
            PrintStream saida = new PrintStream(cliente.getOutputStream());
            saida.println("Usuário: " + areaMensagem.getText());
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
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    // End of variables declaration//GEN-END:variables
}