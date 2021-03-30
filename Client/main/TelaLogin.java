package main;

import javax.swing.JOptionPane;

public class TelaLogin extends javax.swing.JFrame 
{
    public int porta = 0;
    public String endereco = "";
    public String nomeUsuario = "";
    
    public TelaLogin() 
    {        
        initComponents();
    }
    
    private boolean validarIP(String ip) 
    {
        if(ip == null || ip.length() < 7 || ip.length() > 15) 
            return false;

        for (int i = 0; i < ip.length(); i++)
            if (!Character.isDigit(ip.charAt(i)) && ip.charAt(i) != '.') 
                return false;
        
        try 
        {
            int x = 0;
            int y = ip.indexOf('.');

            if (y == -1 || ip.charAt(x) == '-' || Integer.parseInt(ip.substring(x, y)) > 255) 
                return false;

            x = ip.indexOf('.', ++y);
            if (x == -1 || ip.charAt(y) == '-' || Integer.parseInt(ip.substring(y, x)) > 255) 
                return false;

            y = ip.indexOf('.', ++x);
            return  !(y == -1 ||
                    ip.charAt(x) == '-' ||
                    Integer.parseInt(ip.substring(x, y)) > 255 ||
                    ip.charAt(++y) == '-' ||
                    Integer.parseInt(ip.substring(y, ip.length())) > 255 ||
                    ip.charAt(ip.length()-1) == '.');

        } 
        catch (NumberFormatException e) 
        {
            return false;
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        enderecoField = new javax.swing.JTextField();
        portaField = new javax.swing.JTextField();
        enderecoLabel = new javax.swing.JLabel();
        portaLabel = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        nomeUsuarioField = new javax.swing.JTextField();
        nomeUsuarioLabel = new javax.swing.JLabel();
        conectarButton = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Login");

        enderecoLabel.setText("Endreço IP do servidor");

        portaLabel.setText("Porta");

        jLabel3.setText(":");

        nomeUsuarioLabel.setText("Nome de usuário");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nomeUsuarioLabel)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nomeUsuarioField, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(enderecoField, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel3))
                                    .addComponent(enderecoLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(portaLabel)
                                    .addComponent(portaField, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addComponent(nomeUsuarioLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nomeUsuarioField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(portaLabel)
                    .addComponent(enderecoLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(portaField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(enderecoField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4))
        );

        conectarButton.setText("Conectar!");
        conectarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                conectarButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(109, 109, 109)
                .addComponent(conectarButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(conectarButton)
                .addContainerGap(10, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void conectarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_conectarButtonActionPerformed
        if (nomeUsuarioField.getText().equals("") || nomeUsuarioField.getText().matches("^\\s*$"))
        {
            JOptionPane.showMessageDialog(null, 
                    "Você não inseriu um nome de usuário válido!", 
                    "Nome inválido", 
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        else if (nomeUsuarioField.getText().length() > 32)
        {
            JOptionPane.showMessageDialog(null, 
                    "Seu nome é muito grande! Escolha um nome com até 32 caracteres.", 
                    "Nome inválido", 
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        else
            nomeUsuario = nomeUsuarioField.getText().replace(" ", "");
        
        if (validarIP(enderecoField.getText()))
            endereco = enderecoField.getText();                
        else
        {
            JOptionPane.showMessageDialog(null, 
                    "Você não inseriu um endereço IP válido!", 
                    "Porta inválida", 
                    JOptionPane.ERROR_MESSAGE);
            return;
        }  
        
        try
        {
            porta = Integer.parseInt(portaField.getText().replace(" ", ""));
        }
        catch (NumberFormatException e)
        {
            JOptionPane.showMessageDialog(null, 
                    "Você não inseriu um número de porta válido!", 
                    "Porta inválida", 
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        this.setVisible(false);
    }//GEN-LAST:event_conectarButtonActionPerformed

    public static void main(String args[]) 
    {
        java.awt.EventQueue.invokeLater(new Runnable() 
        {
            public void run() {
                new TelaLogin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton conectarButton;
    private javax.swing.JTextField enderecoField;
    private javax.swing.JLabel enderecoLabel;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField nomeUsuarioField;
    private javax.swing.JLabel nomeUsuarioLabel;
    private javax.swing.JTextField portaField;
    private javax.swing.JLabel portaLabel;
    // End of variables declaration//GEN-END:variables
}
