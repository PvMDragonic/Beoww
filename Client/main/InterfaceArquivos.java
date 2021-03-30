package main;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.plaf.basic.BasicInternalFrameUI;

public class InterfaceArquivos 
{
    private static JInternalFrame internalFrame;
    private static JTextField textoCaminhoArqv;
    private static String enderecoArqv = "";
    
    public static boolean ativo()
    {
        if (internalFrame.isShowing()) 
            return true;
        else 
            return false;
    }
            
    public static void ativar()
    {
        internalFrame.setLocation(10, 10);
        internalFrame.setVisible(true);
    }
    
    public static void desativar()
    {
        internalFrame.setVisible(false);
    }
    
    private static void butaoProcurarActionPerformed() 
    {
        EscolherArquivo arqv = new EscolherArquivo();
        textoCaminhoArqv.setText(arqv.arquivo());
        enderecoArqv = arqv.arquivo();
    }
    
    private static void butaoEnviarActionPerformed() throws IOException 
    {
        if (!enderecoArqv.equals(""))
        {
            Client.EnviarArquivo(enderecoArqv);
            textoCaminhoArqv.setText("");
            desativar();
        }       
    }
    
    public static void telaDosArquivos()
    {
        internalFrame = new JInternalFrame();
        internalFrame.setSize(368, 119);       
        internalFrame.setEnabled(true);
        internalFrame.setFocusable(true);
        internalFrame.setLocation(10, 10);
        internalFrame.setTitle("Selecionar arquivo");               
        
        JPanel bruh = new JPanel(new FlowLayout());
        internalFrame.add(bruh);
        
        JButton butaoProcurar = new JButton();
        butaoProcurar.setText("Procurar");
        butaoProcurar.setPreferredSize(new Dimension(80, 30));
        butaoProcurar.setLocation(100, 0);
        butaoProcurar.addActionListener(new java.awt.event.ActionListener() 
        {
            public void actionPerformed(java.awt.event.ActionEvent evt) 
            {
                butaoProcurarActionPerformed();
            }
        });
        
        JButton butaoEnviar = new JButton();
        butaoEnviar.setText("Enviar");
        butaoEnviar.setPreferredSize(new Dimension(70, 30));
        butaoEnviar.setLocation(0, -80);
        butaoEnviar.addActionListener(new java.awt.event.ActionListener() 
        {
            public void actionPerformed(java.awt.event.ActionEvent evt) 
            {
                try 
                {
                    butaoEnviarActionPerformed();
                } 
                catch (IOException ex) 
                {
                    Logger.getLogger(InterfaceArquivos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        textoCaminhoArqv = new JTextField();
        textoCaminhoArqv.setPreferredSize(new Dimension(250, 30));
        textoCaminhoArqv.setLocation(20, 0);
        
        JTextPane placeHolder = new JTextPane();
        placeHolder.setPreferredSize(new Dimension(bruh.getWidth(), 40));
        
        bruh.add(textoCaminhoArqv);
        bruh.add(butaoProcurar);
        bruh.add(placeHolder);
        bruh.add(butaoEnviar);
        
        // Isso aqui é pra gambiarrizar e tirar o dropdown menu que ficava na esquerda do 'internalFrame'.
        BasicInternalFrameUI ui = (BasicInternalFrameUI)internalFrame.getUI();
        Container north = (Container)ui.getNorthPane();
        north.remove(0);
        north.validate();
        north.repaint();  
        
        // Por fim, quando tudo estiver pronto, isso é adicionado ao 'jPanel1'.
        Client.jPanel1.add(internalFrame, 0);
    }
}
