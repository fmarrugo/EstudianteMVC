package com.miapp.vista;

import com.miapp.controlador.EstudianteController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

//Fernando Jose Marrugo Arnedo
/**
 * Vista: JFrame principal del módulo Estudiante.
 * Contiene un campo de búsqueda y una tabla de resultados.
 *
 * IMPORTANTE (MVC): esta clase NO conoce ni importa el Modelo (Estudiante).
 * Solo trabaja con tipos genéricos (Object[], List<Object[]>) que el
 * Controlador le entrega ya preparados. Así la Vista queda desacoplada
 * del Modelo y toda la comunicación pasa por el Controlador.
 */
public class EstudianteView extends JFrame {
    
    // ── Componentes UI ────────────────────────────────────────────────────────
    private JTextField             txtNombre;
    private JButton                btnBuscar;
    private JTable                 tblResultados;
    private DefaultTableModel      modeloTabla;
    private JLabel                 lblEstado;

    // ── Mi Interfaz ────────────────────────────────────────────────────────
    private JTextField            txtNombre1;
    private JTextField            txtCarrera;
    private JTextField            txtPromedio;
    private JButton               btnAgregar;  
    
    //Interfaz de orden
    private JComboBox<String>       criterio;
    private JButton                 btnOrdenar;
    
    // ── Controlador ───────────────────────────────────────────────────────────
    private EstudianteController controlador;

    // ── Constructor ───────────────────────────────────────────────────────────

    public EstudianteView() {
        initComponentes();
        initEventos();
    }

    // ── Inicialización de componentes ─────────────────────────────────────────

    private void initComponentes() {
        setTitle("Búsqueda de Estudiantes — MVC NetBeans");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Panel superior — barra de búsqueda
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelBusqueda.setBorder(BorderFactory.createTitledBorder("Buscar estudiante"));

        JLabel lblNombre = new JLabel("Nombre:");
        txtNombre = new JTextField(25);
        btnBuscar = new JButton("Buscar");
        btnBuscar.setBackground(new Color(44, 227, 230));
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setFocusPainted(false);

        panelBusqueda.add(lblNombre);
        panelBusqueda.add(txtNombre);
        panelBusqueda.add(btnBuscar);
        
        //Mi Interfaz (agregar)  
        JPanel panelAgregar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelAgregar.setBorder(BorderFactory.createTitledBorder("Agregar estudiante"));
        
        JLabel lblNombre1 = new JLabel("Nombre:");
        txtNombre1 = new JTextField(10);
        JLabel lblCarrera = new JLabel ("Carrera:");
        txtCarrera = new JTextField(10);
        JLabel lblPromedio = new JLabel("Promedio:");
        txtPromedio = new JTextField(5);
              
        btnAgregar = new JButton("Agregar");
        btnAgregar.setBackground(new Color(44, 227, 230));
        btnAgregar.setForeground(Color.WHITE);
        btnAgregar.setFocusPainted(false);
        
        panelAgregar.add(lblNombre1); 
        panelAgregar.add(txtNombre1);
        panelAgregar.add(lblCarrera);
        panelAgregar.add(txtCarrera);
        panelAgregar.add(lblPromedio);
        panelAgregar.add(txtPromedio);
        panelAgregar.add(btnAgregar);
        
        //Mi interfaz(ordenar)
        JPanel panelOrdenar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelOrdenar.setBorder(BorderFactory.createTitledBorder("Ordenar resultados"));
        
        panelOrdenar.add(new JLabel("Crieterio:"));
        criterio = new JComboBox<>(new String[]{"Nombre", "Promedio"});
        panelOrdenar.add(criterio);
        
        btnOrdenar = new JButton("Ordenar");
        btnOrdenar.setBackground(new Color(44, 227, 230));
        btnOrdenar.setForeground(Color.WHITE);
        btnOrdenar.setFocusPainted(false);
        panelOrdenar.add(btnOrdenar);
        
        // Panel central — tabla de resultados //Modificado
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelBusqueda.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelAgregar.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelOrdenar.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        panelPrincipal.add(panelBusqueda);
        panelPrincipal.add(panelAgregar);
        panelPrincipal.add(panelOrdenar);
        
        String[] columnas = {"ID", "Nombre", "Carrera", "Promedio"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tblResultados = new JTable(modeloTabla);
        tblResultados.setRowHeight(24);
        tblResultados.getTableHeader().setReorderingAllowed(false);
        tblResultados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scroll = new JScrollPane(tblResultados);
        scroll.setBorder(BorderFactory.createTitledBorder("Resultados"));

        // Panel inferior — estado
        lblEstado = new JLabel("Ingrese un nombre y presione Buscar.");
        lblEstado.setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));
        lblEstado.setForeground(Color.GRAY);

        add(panelPrincipal, BorderLayout.NORTH);
        add(scroll,        BorderLayout.CENTER);
        add(lblEstado,     BorderLayout.SOUTH);
    }

    // ── Eventos ───────────────────────────────────────────────────────────────

    private void initEventos() {
        btnAgregar.addActionListener((ActionEvent e1) -> {
        if (controlador != null) {
                //Aqui debes crear la funcion para agregar estudiantes
                controlador.agregarEstudiante(
                    txtNombre1.getText().trim(),
                    txtCarrera.getText().trim(),
                    txtPromedio.getText().trim()
                );
            }
    });
        
        btnBuscar.addActionListener((ActionEvent e) -> {
            if (controlador != null) {
                controlador.buscarEstudiante(txtNombre.getText().trim());
            }
        });
        
        btnOrdenar.addActionListener((ActionEvent e) -> {
            if (controlador != null) {
                String criterioSeleccionado = (String) criterio.getSelectedItem();
                controlador.ordenarPor(criterioSeleccionado);
            }
        });

        //Mi Interfaz
        txtNombre1.addActionListener((ActionEvent e1) -> btnAgregar.doClick());
        
        // También buscar al presionar Enter en el campo de texto
        txtNombre.addActionListener((ActionEvent e) -> btnBuscar.doClick());
    }
    
    //Funcion de limpieza
    public void limpiarCamposAgregar(){
        txtNombre1.setText("");
        txtCarrera.setText("");
        txtPromedio.setText("");
    }
    
    // ── Métodos públicos que llama el Controlador ─────────────────────────────
    // ninguno de estos métodos recibe un Estudiante: reciben
    // Object[] / List<Object[]> ya armados, que es lo único que la Vista
    // necesita saber para pintar la tabla.

    /**
     * Muestra una única fila en la tabla.
     * @param fila arreglo con {id, nombre, carrera, promedioFormateado}
     */
    public void mostrarEstudiante(Object[] fila) {
        limpiarTabla();
        agregarFila(fila);
        setEstado("Se encontró 1 estudiante.");
    }
    
    public void mostrarConfirmacion(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
        setEstado("Error: " + mensaje);
    }
    /**
     * Muestra varias filas en la tabla.
     * @param filas lista de arreglos {id, nombre, carrera, promedioFormateado}
     */
    public void mostrarEstudiantes(List<Object[]> filas) {
        limpiarTabla();
        if (filas == null || filas.isEmpty()) {
            setEstado("No se encontraron estudiantes con ese criterio.");
            return;
        }
        for (Object[] fila : filas) {
            agregarFila(fila);
        }
        setEstado("Se encontraron " + filas.size() + " estudiante(s).");
    }

    /**
     * Devuelve el texto ingresado en el campo de nombre.
     */
    public String getNombreBuscado() {
        return txtNombre.getText().trim();
    }

    // ── Setter del controlador ────────────────────────────────────────────────

    public void setControlador(EstudianteController controlador) {
        this.controlador = controlador;
    }

    // ── Helpers privados ──────────────────────────────────────────────────────

    private void agregarFila(Object[] fila) {
        modeloTabla.addRow(fila);
    }

    private void limpiarTabla() {
        modeloTabla.setRowCount(0);
    }

    private void setEstado(String texto) {
        lblEstado.setText(texto);
    }
}