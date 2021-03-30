package main;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;

public class CopiarImagem implements ClipboardOwner
{
    public void copiar(BufferedImage bi)
    {
        TransferableImage trans = new TransferableImage(bi);
        Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
        c.setContents(trans, this);
    }

    @Override
    public void lostOwnership(Clipboard clip, Transferable trans) 
    {
        
    }

    private class TransferableImage implements Transferable 
    {
        Image i;

        public TransferableImage(Image img) 
        {
            this.i = img;
        }

        @Override
        public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException
        {
            if (flavor.equals(DataFlavor.imageFlavor) && i != null) 
            {
                return i;
            }
            else 
            {
                throw new UnsupportedFlavorException(flavor);
            }
        }

        @Override
        public DataFlavor[] getTransferDataFlavors() 
        {
            DataFlavor[] flavors = new DataFlavor[1];
            flavors[0] = DataFlavor.imageFlavor;
            return flavors;
        }

        @Override
        public boolean isDataFlavorSupported(DataFlavor flavor) 
        {
            DataFlavor[] flavors = getTransferDataFlavors();
            
            for (DataFlavor flavor1 : flavors) 
            {
                if (flavor.equals(flavor1)) 
                {
                    return true;
                }
            }

            return false;
        }
    }
}
