package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import dao.*;
import model.*;
import dto.CompraDTO;
import java.sql.Date;

public class Principal extends JFrame {
    private Usuario usuarioActual;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private JPanel panelFormulario;
    private String moduloActivo = "";
    
    // DAOs
    private PinturaDAO pinturaDAO = new PinturaDAOImpl();
    private CompraDAO compraDAO = new CompraDAOImpl();
    private UsuarioDAO usuarioDAO = new UsuarioDAOImpl();

    public Principal(Usuario usuario) {
        this.usuarioActual = usuario;
        setTitle("Dashboard - Tienda de Pinturas | Usuario: " + usuario.getUsername() + " (" + usuario.getRol() + ")");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        crearMenu();
        crearPanelLateral();
        crearPanelCentral();
        crearPanelFormulario();
        
        cargarModulo("Pinturas");
    }

    private void crearMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menuSesion = new JMenu("Sesión");
        JMenuItem itemCerrar = new JMenuItem("Cerrar Sesión");
        
        itemCerrar.addActionListener(e -> {
            new Login().setVisible(true);
            this.dispose();
        });

        menuSesion.add(itemCerrar);
        menuBar.add(menuSesion);
        setJMenuBar(menuBar);
    }

    private void crearPanelLateral() {
        JPanel panelLateral = new JPanel();
        panelLateral.setLayout(new BoxLayout(panelLateral, BoxLayout.Y_AXIS));
        panelLateral.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelLateral.setPreferredSize(new Dimension(150, 0));
        panelLateral.setBackground(Color.DARK_GRAY);

        JButton btnPinturas = crearBotonMenu("Pinturas");
        JButton btnCompras = crearBotonMenu("Compras");
        JButton btnUsuarios = crearBotonMenu("Usuarios");

        btnPinturas.addActionListener(e -> cargarModulo("Pinturas"));
        btnCompras.addActionListener(e -> cargarModulo("Compras"));
        btnUsuarios.addActionListener(e -> cargarModulo("Usuarios"));

        panelLateral.add(btnPinturas);
        panelLateral.add(Box.createRigidArea(new Dimension(0, 10)));
        panelLateral.add(btnCompras);
        panelLateral.add(Box.createRigidArea(new Dimension(0, 10)));
        if(usuarioActual.getRol().equals("empleado")) {
            panelLateral.add(btnUsuarios);
        }

        add(panelLateral, BorderLayout.WEST);
    }

    private JButton crearBotonMenu(String texto) {
        JButton btn = new JButton(texto);
        btn.setMaximumSize(new Dimension(130, 30));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        return btn;
    }

    private void crearPanelCentral() {
        modeloTabla = new DefaultTableModel();
        tabla = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tabla);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void crearPanelFormulario() {
        panelFormulario = new JPanel();
        panelFormulario.setPreferredSize(new Dimension(250, 0));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Operaciones"));
        add(panelFormulario, BorderLayout.EAST);
    }

    private void cargarModulo(String modulo) {
        this.moduloActivo = modulo;
        modeloTabla.setRowCount(0);
        modeloTabla.setColumnCount(0);
        panelFormulario.removeAll();

        if (modulo.equals("Pinturas")) {
            modeloTabla.setColumnIdentifiers(new String[]{"ID", "Nombre", "Color", "Tipo", "Precio", "Stock"});
            List<Pintura> lista = pinturaDAO.listarTodos();
            for (Pintura p : lista) {
                modeloTabla.addRow(new Object[]{p.getId(), p.getNombre(), p.getColor(), p.getTipo(), p.getPrecio(), p.getStock()});
            }
            construirFormularioPinturas();
        } else if (modulo.equals("Compras")) {
            if ("empleado".equals(usuarioActual.getRol())) {
                modeloTabla.setColumnIdentifiers(new String[]{"ID Compra", "Cliente", "Pintura", "Fecha", "Cantidad", "Total"});
                List<CompraDTO> lista = compraDAO.listarTodos();
                for (CompraDTO c : lista) {
                    modeloTabla.addRow(new Object[]{c.getId(), c.getNombreCliente(), c.getNombrePintura(), c.getFecha(), c.getCantidad(), c.getPrecioTotal()});
                }
            } else {
                modeloTabla.setColumnIdentifiers(new String[]{"Ticket", "Pintura", "Fecha", "Cantidad", "Total"});
                List<CompraDTO> lista = compraDAO.listarTodos();
                String nombreCompleto = usuarioActual.getNombre() + " " + usuarioActual.getApellidos();
                for (CompraDTO c : lista) {
                    if (c.getNombreCliente().equals(nombreCompleto)) {
                        modeloTabla.addRow(new Object[]{c.getId(), c.getNombrePintura(), c.getFecha(), c.getCantidad(), c.getPrecioTotal()});
                    }
                }
            }
            construirFormularioCompras();
        } else if (modulo.equals("Usuarios")) {
            modeloTabla.setColumnIdentifiers(new String[]{"ID", "Username", "Nombre", "Email", "Rol"});
            List<Usuario> lista = usuarioDAO.listarTodos();
            for (Usuario u : lista) {
                modeloTabla.addRow(new Object[]{u.getId(), u.getUsername(), u.getNombre(), u.getEmail(), u.getRol()});
            }
            panelFormulario.add(new JLabel("Edición de usuarios desde BD."));
        }

        panelFormulario.revalidate();
        panelFormulario.repaint();
    }

    private void construirFormularioPinturas() {
        if ("empleado".equals(usuarioActual.getRol())) {
            panelFormulario.setLayout(new GridLayout(8, 1, 5, 5));
            JTextField txtId = new JTextField(); txtId.setEditable(false);
            JTextField txtNombre = new JTextField();
            JTextField txtColor = new JTextField();
            JTextField txtTipo = new JTextField();
            JTextField txtPrecio = new JTextField();
            JTextField txtStock = new JTextField();

            panelFormulario.add(new JLabel("Nombre:")); panelFormulario.add(txtNombre);
            panelFormulario.add(new JLabel("Color:")); panelFormulario.add(txtColor);
            panelFormulario.add(new JLabel("Tipo:")); panelFormulario.add(txtTipo);
            panelFormulario.add(new JLabel("Precio:")); panelFormulario.add(txtPrecio);
            panelFormulario.add(new JLabel("Stock:")); panelFormulario.add(txtStock);

            JButton btnGuardar = new JButton("Guardar Nuevo");
            JButton btnEliminar = new JButton("Eliminar Seleccionado");

            btnGuardar.addActionListener(e -> {
                try {
                    Pintura p = new Pintura(0, txtNombre.getText(), txtColor.getText(), txtTipo.getText(), 
                                            Double.parseDouble(txtPrecio.getText()), Integer.parseInt(txtStock.getText()));
                    if(pinturaDAO.insertar(p)){
                        JOptionPane.showMessageDialog(this, "Pintura guardada");
                        cargarModulo("Pinturas");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Datos inválidos", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            btnEliminar.addActionListener(e -> {
                int fila = tabla.getSelectedRow();
                if(fila != -1) {
                    int id = (int) tabla.getValueAt(fila, 0);
                    if(pinturaDAO.eliminar(id)){
                        JOptionPane.showMessageDialog(this, "Pintura eliminada");
                        cargarModulo("Pinturas");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Seleccione un registro");
                }
            });

            panelFormulario.add(btnGuardar);
            panelFormulario.add(btnEliminar);
        } else {
            panelFormulario.setLayout(new GridLayout(4, 1, 5, 5));
            JTextField txtCantidad = new JTextField();
            panelFormulario.add(new JLabel("Cantidad a comprar:"));
            panelFormulario.add(txtCantidad);

            JButton btnComprar = new JButton("Comprar Pintura");
            btnComprar.addActionListener(e -> {
                int fila = tabla.getSelectedRow();
                if(fila != -1) {
                    try {
                        int cantidad = Integer.parseInt(txtCantidad.getText());
                        if (cantidad <= 0) {
                            JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor a 0", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        
                        int idPintura = (int) tabla.getValueAt(fila, 0);
                        double precio = (double) tabla.getValueAt(fila, 4);
                        int stock = (int) tabla.getValueAt(fila, 5);
                        
                        if (cantidad > stock) {
                            JOptionPane.showMessageDialog(this, "No hay suficiente stock", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        
                        double precioTotal = cantidad * precio;
                        int confirm = JOptionPane.showConfirmDialog(this,
                            "Precio total: $" + String.format("%.2f", precioTotal) + "\n¿Desea confirmar?",
                            "Confirmar", JOptionPane.YES_NO_OPTION);
                            
                        if (confirm == JOptionPane.YES_OPTION) {
                            Compra c = new Compra(0, usuarioActual.getId(), idPintura, 
                                                  new Date(System.currentTimeMillis()), cantidad, precioTotal);
                            if(compraDAO.insertar(c)){
                                pinturaDAO.actualizarStock(idPintura, cantidad);
                                JOptionPane.showMessageDialog(this, "Compra realizada con éxito.");
                                cargarModulo("Pinturas");
                            } else {
                                JOptionPane.showMessageDialog(this, "Error al procesar la compra", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Cantidad inválida", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Seleccione una pintura de la tabla");
                }
            });
            panelFormulario.add(btnComprar);
        }
    }

    private void construirFormularioCompras() {
        if ("empleado".equals(usuarioActual.getRol())) {
            panelFormulario.setLayout(new GridLayout(6, 1, 5, 5));
            JTextField txtClienteId = new JTextField();
            JTextField txtPinturaId = new JTextField();
            JTextField txtCantidad = new JTextField();
            JTextField txtPrecioTotal = new JTextField();

            panelFormulario.add(new JLabel("ID Cliente:")); panelFormulario.add(txtClienteId);
            panelFormulario.add(new JLabel("ID Pintura:")); panelFormulario.add(txtPinturaId);
            panelFormulario.add(new JLabel("Cantidad:")); panelFormulario.add(txtCantidad);
            panelFormulario.add(new JLabel("Precio Total:")); panelFormulario.add(txtPrecioTotal);

            JButton btnGuardar = new JButton("Registrar Compra");
            btnGuardar.addActionListener(e -> {
                try {
                    int idCliente = Integer.parseInt(txtClienteId.getText());
                    int idPintura = Integer.parseInt(txtPinturaId.getText());
                    int cantidad = Integer.parseInt(txtCantidad.getText());
                    double precioTotal = Double.parseDouble(txtPrecioTotal.getText());
                    
                    Compra c = new Compra(0, idCliente, idPintura, new Date(System.currentTimeMillis()), cantidad, precioTotal);
                    if(compraDAO.insertar(c)){
                        pinturaDAO.actualizarStock(idPintura, cantidad);
                        JOptionPane.showMessageDialog(this, "Compra registrada");
                        cargarModulo("Compras");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Datos inválidos", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            panelFormulario.add(btnGuardar);
        } else {
            panelFormulario.add(new JLabel("Historial de compras."));
        }
    }
}
