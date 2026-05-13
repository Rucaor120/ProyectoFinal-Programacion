package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import dao.UsuarioDAO;
import dao.UsuarioDAOImpl;
import model.Cliente;
import model.Usuario;

public class Registro extends JFrame {
    private JTextField txtUsername, txtEmail, txtNombre, txtApellidos, txtDni;
    private JPasswordField txtPassword;
    private JComboBox<String> cbRol, cbTipoCliente;
    private JButton btnRegistrar, btnCancelar;
    private JPanel panelDinamico;

    public Registro() {
        setTitle("Registro - Tienda de Pinturas");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new GridLayout(10, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Username:"));
        txtUsername = new JTextField();
        panel.add(txtUsername);

        panel.add(new JLabel("Contraseña:"));
        txtPassword = new JPasswordField();
        panel.add(txtPassword);

        panel.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        panel.add(txtEmail);

        panel.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panel.add(txtNombre);

        panel.add(new JLabel("Apellidos:"));
        txtApellidos = new JTextField();
        panel.add(txtApellidos);

        panel.add(new JLabel("DNI:"));
        txtDni = new JTextField();
        panel.add(txtDni);

        panel.add(new JLabel("Rol:"));
        cbRol = new JComboBox<>(new String[]{"cliente", "empleado"});
        panel.add(cbRol);

        // Panel dinámico para Cliente
        panel.add(new JLabel("Tipo Cliente:"));
        cbTipoCliente = new JComboBox<>(new String[]{"minorista", "mayorista"});
        panel.add(cbTipoCliente);

        btnRegistrar = new JButton("Registrar");
        btnCancelar = new JButton("Cancelar");

        panel.add(btnRegistrar);
        panel.add(btnCancelar);

        add(panel);

        // Eventos
        cbRol.addActionListener(e -> {
            boolean isCliente = cbRol.getSelectedItem().equals("cliente");
            cbTipoCliente.setEnabled(isCliente);
        });

        btnCancelar.addActionListener(e -> {
            new Login().setVisible(true);
            this.dispose();
        });

        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarUsuario();
            }
        });
    }

    private void registrarUsuario() {
        try {
            String username = txtUsername.getText();
            String password = new String(txtPassword.getPassword());
            String email = txtEmail.getText();
            String nombre = txtNombre.getText();
            String apellidos = txtApellidos.getText();
            String dni = txtDni.getText();
            String rol = cbRol.getSelectedItem().toString();

            if (username.isEmpty() || password.isEmpty() || email.isEmpty() || nombre.isEmpty() || apellidos.isEmpty() || dni.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Llene todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Usuario usuario;
            if (rol.equals("cliente")) {
                String tipoCliente = cbTipoCliente.getSelectedItem().toString();
                usuario = new Cliente(0, username, password, email, nombre, apellidos, dni, tipoCliente);
            } else {
                usuario = new Usuario(0, username, password, email, nombre, apellidos, dni, rol);
            }

            UsuarioDAO dao = new UsuarioDAOImpl();
            if (dao.registrar(usuario)) {
                JOptionPane.showMessageDialog(this, "Registro exitoso.");
                new Login().setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error en el registro.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
