package main;

import java.awt.Container;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import com.vdurmont.emoji.EmojiParser;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class InterfaceEmoji 
{
    private static JInternalFrame internalFrame;
    private static String[] emojies = new String[]
    { 
        // Isso aqui tá entre '<html></html>' porque, por algum motivo, isso da fix num
        // problema que faz emoji na fonte Segoe UI Emoji aparecer fora do alinhamento.
        "<html>😄</html>", "<html>😃</html>", "<html>😀</html>", "<html>😊</html>", "<html>😉</html>", "<html>😍</html>", "<html>😘</html>", "<html>😚</html>",
        "<html>😗</html>", "<html>😙</html>", "<html>😜</html>", "<html>😝</html>", "<html>😛</html>", "<html>😳</html>", "<html>😁</html>", "<html>😔</html>",
        "<html>😌</html>", "<html>😒</html>", "<html>😞</html>", "<html>😣</html>", "<html>😢</html>", "<html>😂</html>", "<html>😭</html>", "<html>😪</html>",
        "<html>😥</html>", "<html>😰</html>", "<html>😅</html>", "<html>😓</html>", "<html>😩</html>", "<html>😫</html>", "<html>😨</html>", "<html>😱</html>",
        "<html>😠</html>", "<html>😡</html>", "<html>😤</html>", "<html>😖</html>", "<html>😆</html>", "<html>😋</html>", "<html>😷</html>", "<html>😎</html>",
        "<html>😴</html>", "<html>😵</html>", "<html>😲</html>", "<html>😟</html>", "<html>😦</html>", "<html>😧</html>", "<html>😈</html>", "<html>👿</html>",
        "<html>😮</html>", "<html>😬</html>", "<html>😐</html>", "<html>😕</html>", "<html>😯</html>", "<html>😶</html>", "<html>😇</html>", "<html>😏</html>",
        "<html>😑</html>", "<html>👲</html>", "<html>👳</html>", "<html>👮</html>", "<html>👷</html>", "<html>💂</html>", "<html>👶</html>", "<html>👦</html>",
        "<html>👧</html>", "<html>👨</html>", "<html>👩</html>", "<html>👴</html>", "<html>👵</html>", "<html>👱</html>", "<html>👼</html>", "<html>👸</html>",
        "<html>👨‍👩‍👦‍👦</html>", "<html>👨‍👩‍👧‍👧</html>", "<html>👨‍👩‍👧‍👦</html>", "<html>👩‍👩‍👧‍👦</html>", "<html>👩‍👩‍👦‍👦</html>", "<html>👩‍👩‍👧‍👧</html>", "<html>👨‍👨‍👧‍👦</html>", "<html>👨‍👨‍👦‍👦</html>",
        "<html>👨‍👨‍👧‍👧</html>", "<html>👩‍❤️‍💋‍👩</html>", "<html>👨‍❤️‍💋‍👨</html>", "<html>👨‍👩‍👦</html>", "<html>👨‍👩‍👧</html>", "<html>👩‍👩‍👦</html>", "<html>👩‍👩‍👧</html>", "<html>👨‍👨‍👦</html>",
        "<html>👨‍👨‍👧</html>", "<html>👩‍❤️‍👩</html>", "<html>👨‍❤️‍👨</html>", "<html>😺</html>", "<html>😸</html>", "<html>😻</html>", "<html>😽</html>", "<html>😼</html>", 
        "<html>🙀</html>", "<html>😿</html>", "<html>😹</html>", "<html>😾</html>", "<html>👹</html>", "<html>👺</html>", "<html>🙈</html>", "<html>🙉</html>", 
        "<html>🙊</html>", "<html>💀</html>", "<html>👽</html>", "<html>💩</html>", "<html>🔥</html>", "<html>👍</html>", "<html>👎</html>", "<html>👌</html>", 
        "<html>👊</html>", "<html>👋</html>", "<html>👐</html>", "<html>👆</html>", "<html>👇</html>", "<html>👉</html>", "<html>👈</html>", "<html>🙌</html>",
        "<html>🙏</html>", "<html>👏</html>", "<html>💪</html>", "<html>👀</html>", "<html>👅</html>", "<html>💢</html>"       
    };
    
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
    
    private static void butaoActionPerformed(String texto) 
    {
        // Isso aqui é pra remover as tags html que ficam no texto dos botões.
        texto = texto.replace("<html>","");
        texto = texto.replace("</html>","");
        
        Client.areaMensagem.setText(Client.areaMensagem.getText() + " " + texto);
        internalFrame.setVisible(false);
    }
    
    public static void telaDosEmoji()
    {
        internalFrame = new JInternalFrame();
        internalFrame.setSize(368, 119);       
        internalFrame.setEnabled(true);
        internalFrame.setFocusable(true);
        internalFrame.setLocation(10, 10);
        internalFrame.setTitle("Lista de emojies");
        
        // Esse JPanel existe só por causa do JScrollPane,
        // porque tava dando errado botar o scroll direto no 'internalFrame'.
        JPanel bruh = new JPanel(new FlowLayout());
        
        // Esse preferred size é pros emojis não ficarem só numa linha horizontal.
        bruh.setPreferredSize(new Dimension(335, 804));      
        
        JScrollPane scroll = new JScrollPane(bruh, 
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);        
        
        // Isso aqui é pra fazer a velocidade de scroll do JScrollPane ficar maior.
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        
        // Sim, o scroll se adiciona no 'internalFrame' e não no 'bruh'.
        internalFrame.add(scroll);
        
        // Isso aqui vai criar um botão pra cada emoji, e aí botar eles no 'bruh'.
        for (String emoji : emojies)
        {
            JButton butao = new JButton();
            butao.setText(emoji);
            butao.setToolTipText(EmojiParser.parseToAliases(emoji));
            butao.setFont(new java.awt.Font("Segoe UI Emoji", 0, 16));
            butao.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            butao.setPreferredSize(new Dimension(49, 33));
            butao.addActionListener(new java.awt.event.ActionListener() 
            {
                public void actionPerformed(java.awt.event.ActionEvent evt) 
                {
                    butaoActionPerformed(butao.getText());
                }
            });

            bruh.add(butao);
        }                  
        
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
