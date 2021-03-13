package main;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

public class EscolherArquivo 
{
    public String bruh;
    private JFileChooser jfc;
    
    public String arquivo()
    {
        return bruh;
    }           
    
    public EscolherArquivo()
    {
        UIManager.put("FileChooser.cancelButtonText","Cancelar");
        UIManager.put("FileChooser.filesOfTypeLabelText","Tipo do arquivo:");
        UIManager.put("FileChooser.fileNameLabelText","Nome do arquivo:");
        UIManager.put("FileChooser.lookInLabelText","Procurar em:");
        
        this.jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        this.jfc.setDialogTitle("Select an image");
        this.jfc.setAcceptAllFileFilterUsed(false);
        this.jfc.addChoosableFileFilter(new FileNameExtensionFilter("Imagem .png", "png"));
        this.jfc.addChoosableFileFilter(new FileNameExtensionFilter("Imagem .jpg", "jpg"));       
        this.jfc.setApproveButtonText("Confirmar");
        this.jfc.setDialogTitle("Selecione seu arquivo");
        
        while (true)
        {
            this.jfc.setSelectedFile(new File(""));
            int returnValue = jfc.showOpenDialog(null);
            File file = jfc.getSelectedFile();

            if (returnValue == JFileChooser.APPROVE_OPTION) 
            {
                if(file.exists())
                {
                    bruh = this.jfc.getSelectedFile().getPath();
                    break;
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Você não selecionou um arquivo válido!", "Arquivo inválido!", JOptionPane.ERROR_MESSAGE);
                }
            }
            else
            {
                break;
            }      
        }        
    }
}
