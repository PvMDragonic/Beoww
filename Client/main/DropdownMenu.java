package main;

import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.AbstractAction;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.filechooser.FileSystemView;

class DropdownMenu extends JPopupMenu 
{    
    public DropdownMenu(String nomeArqv) 
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
    }
}
