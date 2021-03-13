package main;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileSystemView;
import java.awt.Desktop;
import javax.swing.JOptionPane;
import static main.Client.chat;

public class MensagemImagem 
{   
    // Pasta padrão do JFileChooser, que no Windows é a 'Documents'.
    private static String path = FileSystemView.getFileSystemView().getDefaultDirectory().toString() + "\\Beoww";
    
    private static Image ResizeImage(BufferedImage bi)
    {        
        if (bi.getWidth() > 200)
        {
            return bi.getScaledInstance(
                    (int) (bi.getWidth() * (235.0 / bi.getWidth())), 
                    (int) (bi.getHeight() * (235.0 / bi.getWidth())), 
                    Image.SCALE_SMOOTH);
        }
 
        return bi;
    }
    
    private static void MouseClicado(MouseEvent me, String nomeArqv) throws IOException
    {
        if (SwingUtilities.isRightMouseButton(me))
        {
            DropdownMenu menu = new DropdownMenu(nomeArqv);
            menu.show(me.getComponent(), me.getX(), me.getY());
        }
        else
        {
            // Vai abrir o arquivo com o programa
            // associado àquele tipo de arquivo.
            Desktop programaPadrao = Desktop.getDesktop();
            File arqv = new File(path + "\\" + nomeArqv);

            programaPadrao.open(arqv);
        }
    }
    
    private static void SalvarImagem(BufferedImage bufferedImage, String nomeArqv, String arqvExt) throws IOException
    {
        File Beoww = new File(path);
        Beoww.mkdir();
        
        File outputfile = new File(path + "\\" + nomeArqv);
        ImageIO.write(bufferedImage, arqvExt, outputfile);
    }
    
    public static JTextPane Criar(ObjetoEnviado msg, int id) throws IOException
    {
        BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(msg.getArqv()));
        
        SalvarImagem(bufferedImage, msg.getArqvNome(), msg.getArqvExt());
        
        JTextPane imagem = new JTextPane();         
        imagem.setEditable(false); 
        imagem.setCursor(new Cursor(Cursor.HAND_CURSOR));
        imagem.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent me)
            {
                try 
                {
                    MouseClicado(me, msg.getArqvNome());
                } 
                catch (Exception ex) 
                {
                    // Vai cair aqui se a pessoa tentar abrir imagem que não existe.
                    JOptionPane.showMessageDialog(null, 
                        "Esta imagem já não existe mais!", 
                        "Caminho inválido", 
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        if (msg.getId() == id)
            imagem.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLUE, 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        else
            imagem.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
      
        Image img = ResizeImage(bufferedImage);
      
        JTextPane usuario = new JTextPane();
        usuario.setContentType("text/html");
        usuario.setFont(new java.awt.Font("Segoe UI Emoji", 0, 14));

        DateTimeFormatter data = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");  
        LocalDateTime agora = LocalDateTime.now(); 
        usuario.setText("<html>" + data.format(agora) + "<br/>" + msg.getNomeUser() + "</html>");
        usuario.setPreferredSize(new Dimension(chat.getWidth(), 40));

        imagem.insertIcon(new ImageIcon(img));
        imagem.insertComponent(usuario);   
      
        return imagem;
    }
}