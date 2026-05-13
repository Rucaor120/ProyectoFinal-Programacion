package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import dao.UsuarioDAO;
import dao.UsuarioDAOImpl;
import model.Usuario;

public class Login extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnEntrar;
    private JLabel lblRegistro;

    public Login() {
        setTitle("Login - Tienda de Pinturas");
        setSize(350, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Componentes
        JPanel panelUser = new JPanel(new BorderLayout());
        panelUser.add(new JLabel("Usuario:"), BorderLayout.NORTH);
        txtUsername = new JTextField();
        panelUser.add(txtUsername, BorderLayout.CENTER);

        JPanel panelPass = new JPanel(new BorderLayout());
        panelPass.add(new JLabel("Contraseña:"), BorderLayout.NORTH);
        txtPassword = new JPasswordField();
        panelPass.add(txtPassword, BorderLayout.CENTER);

        btnEntrar = new JButton("Entrar");
        
        lblRegistro = new JLabel("<html><u>¿No tienes cuenta? Regístrate aquí</u></html>");
        lblRegistro.setForeground(Color.BLUE);
        lblRegistro.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblRegistro.setHorizontalAlignment(SwingConstants.CENTER);

        // Añadir componentes al panel
        panel.add(panelUser);
        panel.add(panelPass);
        panel.add(btnEntrar);
        panel.add(lblRegistro);

        add(panel);

        // Eventos
        btnEntrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validarLogin();
            }
        });

        lblRegistro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                abrirRegistro();
            }
        });
    }

    private void validarLogin() {
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, llene todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        UsuarioDAO dao = new UsuarioDAOImpl();
        Usuario usuario = dao.validar(username, password);

        if (usuario != null) {
            JOptionPane.showMessageDialog(this, "Bienvenido, " + usuario.getNombre());
            new Principal(usuario).setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Credenciales incorrectas.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirRegistro() {
        new Registro().setVisible(true);
        this.dispose();
    }
}
