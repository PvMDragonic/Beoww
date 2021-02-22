package main;

import com.vdurmont.emoji.EmojiParser;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Client extends javax.swing.JFrame 
{
    private Socket cliente;
    volatile private String nomeUsuario;    

    private Icon resizeIcon(ImageIcon icon, int resizedWidth, int resizedHeight) 
    {
        Image img = icon.getImage();  
        Image resizedImage = img.getScaledInstance(resizedWidth, resizedHeight,  java.awt.Image.SCALE_SMOOTH);  
        
        return new ImageIcon(resizedImage);
    }
    
    public Client() throws IOException
    {
        initComponents();
        initCliente();
        InterfaceEmoji.telaDosEmoji();
      
        // Isso aqui tá aqui só porque o NetBeans não deixa editar o 'initComponents()'.
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - getHeight()) / 2);
        setLocation(x, y);
        
        areaMensagem.setLineWrap(true);
        areaMensagem.setWrapStyleWord(true);
        
        ImageIcon icon = new ImageIcon(getClass().getResource("/main/smiley.png"));  
        botaoEmoji.setIcon(resizeIcon(icon, botaoEmoji.getWidth() - botaoEmoji.getInsets().left, 
                botaoEmoji.getHeight() - botaoEmoji.getInsets().left));
        icon = new ImageIcon(getClass().getResource("/main/file.png"));  
        botaoArquivo.setIcon(resizeIcon(icon, botaoEmoji.getWidth() - botaoEmoji.getInsets().left, 
                botaoEmoji.getHeight() - botaoEmoji.getInsets().left));
        icon = new ImageIcon(getClass().getResource("/main/send.png"));  
        botaoEnviar.setIcon(resizeIcon(icon, botaoEmoji.getWidth() - botaoEmoji.getInsets().left, 
                botaoEmoji.getHeight() - botaoEmoji.getInsets().left));
        
        // Vai ficar preso aqui até o usuário botar um nome válido - algo que não seja vazio.
        while (true)
        {
            nomeUsuario = JOptionPane.showInputDialog(null, "Insira seu nome de usuário:", "Nome de usuário", JOptionPane.QUESTION_MESSAGE);
            if (nomeUsuario.isEmpty())
            {
                JOptionPane.showMessageDialog(null, "Você não inseriu um nome válido!", "Nome inválido", JOptionPane.ERROR_MESSAGE);
            }
            else
            {               
                setTitle("Conectado como: " + nomeUsuario);
                break;
            }
        }   

        Chat();
    }

    private void initCliente()
    {
        try 
        {
            this.cliente = new Socket("192.168.0.3",25565);
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private int contadorOcorrencia(String txtProcurado, String txtAlvo)
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

    private String formatarTexto(String texto)
    {
        int contador = 0;
        String fraseFinal = ""; 
        String temp = "";

        // Vai rodar por todos os chars da string.
        for (int i = 0; i < texto.length(); i++) 
        {    
            // Vai cair aqui se já tiver passado 45 chars, que é o tamanho
            // da linha na janela do programa.
            if (contador <= 45)
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
    
    private void Chat()
    {
        new Thread() 
        {
            @Override
            public void run() 
            {     
                while (true)
                {      
                    try 
                    {             
                        // Cria a conexão com o servidor pra receber as coisas.
                        DataInputStream msg = new DataInputStream(cliente.getInputStream());                     
                        
                        // Lê a mensagem da conexão. Toda vez que o 'readUTF()' é chamado, ele "tira" fora 
                        // uma das coisas que tavam na fila para serem recebidas pelo client. Por isso, eu tenho
                        // que salvar numa variável, porque toda vez que o 'readUTF()' for chamado, vai ser algo diferente.
                        // Ele também trava o código, porque ele fica esperando algo chegar pra ler.
                        String mem = msg.readUTF();
                        
                        if (!mem.equals(""))
                        {
                            // Simples container pra botar o texto.
                            JTextPane texto = new JTextPane();                           
                            texto.setEditable(false);
                            
                            // Tem que ser html, em vez de plain, pra poder meter bold e etc no meio da string.
                            texto.setContentType("text/html");
                                                       
                            texto.setFont(new java.awt.Font("Segoe UI Emoji", 0, 14));
                            
                            // Placeholder muito foda pra fazer gambiarra de ter espaço entre as msg.
                            JTextPane placeHolder = new JTextPane();
                            placeHolder.setPreferredSize(new Dimension(chat.getWidth(), 10));
                            chat.insertComponent(placeHolder);

                            String[] bruh = mem.split(":");
                            
                            // Caso seja msg do próprio usuário, ela vai ficar com outline azul;
                            // caso contrário (outros usuários), fica com outline cinza.
                            if (bruh[0].equals(nomeUsuario))
                                texto.setBorder(BorderFactory.createCompoundBorder(
                                    BorderFactory.createLineBorder(Color.BLUE, 1),
                                    BorderFactory.createEmptyBorder(5, 10, 0, 10)));
                            else
                                texto.setBorder(BorderFactory.createCompoundBorder(
                                    BorderFactory.createLineBorder(Color.GRAY, 1),
                                    BorderFactory.createEmptyBorder(5, 10, 0, 10)));
                            
                            // 'bruh[0]' é o nome do usuário.
                            bruh[0] = "<b>" + bruh[0] + "</b>";
                            
                            mem = bruh[0] + ":" + bruh[1];                                            
                            
                            // As mensagens são enviadas com '[[[[[' no lugar das newline porque se eu envio com newline,
                            // o 'readUTF()' vai ativar separado pra cada newline. Por causa disso, eu envio tudo numa só coisa,
                            // e aí separo. 
                            // O 'formatarTexto' é uma implementação do line wrapping, porque a do próprio Java buga loucamente
                            // as caixinhas de texto.
                            mem = formatarTexto(mem.replace("[[[[[","<br/>"));                                            

                            DateTimeFormatter data = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");  
                            LocalDateTime agora = LocalDateTime.now();          
                            mem = "<html>" + data.format(agora) + "<br/>" + mem + "</html>";

                            texto.setText(EmojiParser.parseToUnicode(mem));

                            // Se eu não seto o tamanho do 'texto', o programa vai ficar colocando todos na mesma row até ela encher, pra aí passar pra debaixo.
                            // Pra isso não acontecer, eu tenho que setar um tamanho fixo; a horizontal eu usei o tamanho do 'chat', mas pra vertical tem que
                            // ser dinâmico, porque cada mensagem pode variar de tamanho.
                            // Essa equação fodida aí faz com que tudo funcione nos trinques, usando como base a ideia de adicionar mais tamanho dependendo do
                            // número de newlines que a mensagem tem.
                            texto.setPreferredSize(new Dimension(chat.getWidth(), 
                                    (int) (((contadorOcorrencia("<br/>", mem) + 1) * 30) - ((contadorOcorrencia("<br/>", mem) * 30) * 0.4785))));

                            chat.insertComponent(texto);    
                        }
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

    private void botaoEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoEnviarActionPerformed
        try 
        {
            if (!areaMensagem.getText().equals(""))
            {
                PrintStream saida = new PrintStream(this.cliente.getOutputStream());
                String bruh = areaMensagem.getText().replace("\n", "[[[[[");
                saida.println(nomeUsuario + ": " + bruh);
                areaMensagem.setText("");
            }                     
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_botaoEnviarActionPerformed

    private void botaoArquivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoArquivoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_botaoArquivoActionPerformed

    private void botaoEmojiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoEmojiActionPerformed
        if (InterfaceEmoji.ativo()) 
        {
            InterfaceEmoji.desativar();
        }
        else 
        {
            InterfaceEmoji.ativar();
        }
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
    private javax.swing.JTextPane chat;
    public static javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    // End of variables declaration//GEN-END:variables
}