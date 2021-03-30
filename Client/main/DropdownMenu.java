package main;

import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.AbstractAction;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.filechooser.FileSystemView;

class DropdownMenu extends JPopupMenu 
{    
    public DropdownMenu(String nomeArqv, BufferedImage bi) 
    {
        JMenuItem menuItem = new JMenuItem(new AbstractAction("Ver na pasta") 
        {
            public void actionPerformed(ActionEvent e) 
            {
                try 
                {
                    String path = FileSystemView.getFileSystemView().getDefaultDirectory().toString() + "\\Beoww";
                    
                    Runtime.getRuntime().exec("explorer.exe /select," + path + "\\" + nomeArqv);
                } 
                catch (IOException ex) 
                {

                }
            }
        });
        
        add(menuItem);
        
        menuItem = new JMenuItem(new AbstractAction("Copiar imagem") 
        {
            public void actionPerformed(ActionEvent e) 
            {
                CopiarImagem ci = new CopiarImagem();
                ci.copiar(bi);
            }
        });
        
        add(menuItem);
    }
}
