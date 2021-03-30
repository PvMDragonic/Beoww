package main;

import com.vdurmont.emoji.EmojiParser;
import java.awt.Color;
import java.awt.Dimension;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.BorderFactory;
import javax.swing.JTextPane;

public class MensagemTexto 
{
    private static int contadorOcorrencia(String txtProcurado, String txtAlvo)
    {
        int tamProcurado = txtProcurado.length();         
        int tamAlvo = txtAlvo.length();         
        int tamanho = 0; 
  
        for (int i = 0; i <= tamAlvo - tamProcurado; i++) 
        { 
            int j;             
            for (j = 0; j < tamProcurado; j++) 
            { 
                if (txtAlvo.charAt(i + j) != txtProcurado.charAt(j)) 
                { 
                    break; 
                } 
            } 
   
            if (j == tamProcurado) 
            {                 
                tamanho++;                 
                j = 0;                 
            }             
        }         
        return tamanho;  
    }

    private static String formatarTexto(String texto)
    {
        int contador = 0;
        String fraseFinal = ""; 
        String temp = "";

        // Vai rodar por todos os chars da string.
        for (int i = 0; i < texto.length(); i++) 
        {    
            // Vai cair aqui se já tiver passado 50 chars, que é o tamanho
            // da linha na janela do programa.
            if (contador <= 50)
            {
                // Ele vai ir juntando os chars na var 'temp', mas caso
                // ele encontre uma newline, ele vai adicionar tudo pra
                // 'fraseFinal' e vai recomeçar a separar os chars na 'temp'.
                if (texto.charAt(i) != '\n')
                {
                    temp = temp + texto.charAt(i);
                }
                else
                {
                    fraseFinal = fraseFinal + temp + "\n";
                    temp = "";
                    contador = 0;
                }
            }
            // Assim como no caso da newline, quando passar dos 45 chars, ele
            // joga tudo pra 'fraseFinal' e recomeça a separar os chars na 'temp'.
            else
            {
                fraseFinal = fraseFinal + temp + "\n";
                temp = "";
                contador = 0;
            }
            contador++;
        }
        // Vai cair aqui se não tiver mais char, só que não tiver fechado 45 ainda.
        fraseFinal = fraseFinal + temp;
        
        return fraseFinal;
    }
    
    public static JTextPane Criar(ObjetoEnviado msg, int id)   
    {
        JTextPane texto = new JTextPane();                           
        texto.setEditable(false);
        texto.setFont(new java.awt.Font("Segoe UI Emoji", 0, 14));

        // Tem que ser html (em vez de plain) pra usar bold e etc no meio da string.
        texto.setContentType("text/html");                        

        if (msg.getId() == id)
            texto.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLUE, 1),
                BorderFactory.createEmptyBorder(5, 10, 0, 10)));
        else
            texto.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                BorderFactory.createEmptyBorder(5, 10, 0, 10)));                                                                                       

        DateTimeFormatter data = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");  
        LocalDateTime agora = LocalDateTime.now();                            

        String msgHtml = formatarTexto(msg.getNomeUser() + msg.getMsg());

        msgHtml = "<html>" + data.format(agora) + "<br/>" + msgHtml.replace("\n","<br/>") + "</html>";

        texto.setText(EmojiParser.parseToUnicode(msgHtml));

        // Ter que forçar o tamanho pro programa não ficar colocando todos na mesma row até ela encher e aí passar pra debaixo.
        // O problema é que o tamanho da vertical tem que ser dinâmico, porque cada mensagem pode variar de tamanho. Essa equação fodida faz com 
        // que tudo funcione nos trinques, usando como base a ideia de adicionar mais tamanho dependendo do número de newlines que a mensagem tem.
        texto.setPreferredSize(new Dimension(Client.chat.getWidth(), 
                (int) (((contadorOcorrencia("<br/>", msgHtml) + 1) * 30) - ((contadorOcorrencia("<br/>", msgHtml) * 30) * 0.48))));
        
        return texto;
    }
}
