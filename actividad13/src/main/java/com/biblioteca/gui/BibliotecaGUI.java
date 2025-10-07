package com.biblioteca.gui;

import com.biblioteca.dao.LibroDAO;
import com.biblioteca.model.Libro;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class BibliotecaGUI extends JFrame {
    private LibroDAO libroDAO = new LibroDAO();

    private JTextField txtTitulo = new JTextField(15);
    private JTextField txtAutor = new JTextField(15);
    private JTextField txtGenero = new JTextField(15);
    private JTextField txtCantidad = new JTextField(5);

    private JTextField txtBuscar = new JTextField(15);
    private JComboBox<String> cmbBuscar = new JComboBox<>(new String[] { "Título", "Autor", "Género" });

    private JButton btnAgregar = new JButton("Agregar");
    private JButton btnEliminar = new JButton("Eliminar");
    private JButton btnModificar = new JButton("Modificar");
    private JButton btnBuscar = new JButton("Buscar");

    private DefaultListModel<Libro> modeloLista = new DefaultListModel<>();
    private JList<Libro> listaLibros = new JList<>(modeloLista);

    private JLabel lblMensaje = new JLabel(" ");

    // Ventana principal de la biblioteca
    public BibliotecaGUI() {
        setTitle("Biblioteca");
        setSize(1500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel de formulario
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del libro"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Se que se puede agregar x para que ocupe el espacio que necesite, pero no me gusta como queda
        // Fila 1: Título
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        panelFormulario.add(new JLabel("Título:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        // Se agrega el campo de texto escribible para el título
        panelFormulario.add(txtTitulo, gbc);

        // Fila 2: Autor
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panelFormulario.add(new JLabel("Autor:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        // Se agrega el campo de texto escribible para el autor
        panelFormulario.add(txtAutor, gbc);

        // Fila 3: Género y Cantidad
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panelFormulario.add(new JLabel("Género:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        // Se agrega el campo de texto escribible para el género
        panelFormulario.add(txtGenero, gbc);
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panelFormulario.add(new JLabel("Cantidad:"), gbc);
        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        // Se agrega el campo de texto escribible para la cantidad
        panelFormulario.add(txtCantidad, gbc);

        // Fila 4: Botón Agregar centrado
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        // Se agrega el botón de agregar
        panelFormulario.add(btnAgregar, gbc);

        // Panel para el mensaje debajo del formulario para tener feedback de las acciones
        JPanel panelMensaje = new JPanel(new BorderLayout());
        lblMensaje.setHorizontalAlignment(SwingConstants.CENTER);
        lblMensaje.setForeground(new Color(0, 102, 0));
        panelMensaje.add(lblMensaje, BorderLayout.CENTER);

        // Panel de búsqueda
        JPanel panelBusqueda = new JPanel();
        panelBusqueda.setBorder(BorderFactory.createTitledBorder("Buscar"));
        panelBusqueda.add(new JLabel("Buscar por:"));
        panelBusqueda.add(cmbBuscar);
        panelBusqueda.add(txtBuscar);
        panelBusqueda.add(btnBuscar);

        // Panel de lista de libros seleccionable
        listaLibros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaLibros.setCellRenderer(new LibroCellRenderer());
        JScrollPane scrollLista = new JScrollPane(listaLibros);
        scrollLista.setPreferredSize(new Dimension(1000, 350));

        // CABECERA alineada con los mismos anchos
        JLabel header = new JLabel(
                String.format(" %-4s %-60s %-40s %-20s %8s",
                        "ID", "Título", "Autor", "Género", "Cantidad"));
        // Se va a usar monospaced para que se vea bien alineado
        header.setFont(new Font("Monospaced", Font.BOLD, 12));
        header.setBorder(new EmptyBorder(3, 5, 3, 5));

        JPanel panelLista = new JPanel(new BorderLayout());
        panelLista.setBorder(BorderFactory.createTitledBorder("Libros en la biblioteca"));
        panelLista.add(header, BorderLayout.NORTH);
        panelLista.add(scrollLista, BorderLayout.CENTER);

        // Panel de acciones modificar y eliminar
        JPanel panelAcciones = new JPanel();
        panelAcciones.add(btnModificar);
        panelAcciones.add(btnEliminar);

        // Layout principal el de la lista a la izquierda (para que se vea bien)
        setLayout(new BorderLayout(10, 10));
        JPanel panelIzquierda = new JPanel(new BorderLayout(10, 10));
        panelIzquierda.add(panelLista, BorderLayout.CENTER);
        panelIzquierda.add(panelBusqueda, BorderLayout.SOUTH);

        // Layout de la derecha el del formulario y el mensaje
        JPanel panelDerecha = new JPanel(new BorderLayout(10, 10));
        panelDerecha.add(panelFormulario, BorderLayout.NORTH);
        panelDerecha.add(panelMensaje, BorderLayout.CENTER);
        panelDerecha.add(panelAcciones, BorderLayout.SOUTH);

        // Añadir los paneles al layout principal
        add(panelIzquierda, BorderLayout.WEST);
        add(panelDerecha, BorderLayout.CENTER);

        // Cargar los libros al inicio
        cargarLibros(libroDAO.obtenerTodos());

        // Agregar listeners a los botones y a la lista
        btnAgregar.addActionListener(e -> agregarLibro());
        btnEliminar.addActionListener(e -> eliminarLibro());
        btnModificar.addActionListener(e -> modificarLibro());
        btnBuscar.addActionListener(e -> buscarLibros());

        // Se agrega un listener a la lista para mostrar los datos del libro seleccionado en los campos de texto
        listaLibros.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String criterio = cmbBuscar.getSelectedItem().toString();
                Libro libro = listaLibros.getSelectedValue();
                if (libro != null) {
                    txtTitulo.setText(libro.getTitulo());
                    txtAutor.setText(libro.getAutor());
                    txtGenero.setText(libro.getGenero());
                    txtCantidad.setText(String.valueOf(libro.getCantidad()));
                    switch (criterio) {
                    case "Título":
                        txtBuscar.setText(libro.getTitulo());
                        break;
                    case "Autor":
                        txtBuscar.setText(libro.getAutor());
                        break;
                    case "Género":
                        txtBuscar.setText(libro.getGenero());
                        break;
                    default:
                        txtBuscar.setText("");
                        break;
                }
                } else {
                    limpiarCampos();
                }
            }
        });

        // Se agrega un listener al comboBox de búsqueda para que al seleccionar un criterio se limpie el campo de texto
        cmbBuscar.addActionListener(e -> {
            String criterio = cmbBuscar.getSelectedItem().toString();
            txtBuscar.setText("");
            Libro libro = listaLibros.getSelectedValue();
            if (libro != null) {
                    switch (criterio) {
                    case "Título":
                        txtBuscar.setText(libro.getTitulo());
                        break;
                    case "Autor":
                        txtBuscar.setText(libro.getAutor());
                        break;
                    case "Género":
                        txtBuscar.setText(libro.getGenero());
                        break;
                    default:
                        txtBuscar.setText("");
                        break;
                }
                } else {
                    limpiarCampos();
                }
        });

        // Deshabilita botones de eliminar y modificar al inicio y/o si no hay selección
        btnEliminar.setEnabled(false);
        btnModificar.setEnabled(false);
        listaLibros.addListSelectionListener(e -> {
            boolean seleccion = listaLibros.getSelectedIndex() != -1;
            btnEliminar.setEnabled(seleccion);
            btnModificar.setEnabled(seleccion);
        });
    }

    // Método para cargar los libros en la lista, lo usaremos para cargar al inicio y después de agregar, eliminar o modificar
    private void cargarLibros(List<Libro> libros) {
        modeloLista.clear();
        for (Libro libro : libros) {
            modeloLista.addElement(libro);
        }
    }

    // Metodo para agregar un libro a la lista y a la base de datos
    private void agregarLibro() {
        /*
         * Obtenemos los datos de los campos de texto y validamos que no estén vacíos
         * y que la cantidad sea un número entero positivo. Si alguno de los campos está
         * vacío o la cantidad no es válida, mostramos un mensaje de error y
         * no agregamos el libro.
         * Si la cantidad es correcta, creamos un nuevo objeto Libro y lo agregamos a la base de datos y a la lista.
        */
        try {
            String titulo = txtTitulo.getText().trim();
            String autor = txtAutor.getText().trim();
            String genero = txtGenero.getText().trim();
            int cantidad = Integer.parseInt(txtCantidad.getText().trim());

            if (titulo.isEmpty() || autor.isEmpty() || genero.isEmpty() || cantidad < 0) {
                mostrarMensaje("Por favor, rellena todos los campos correctamente.", true);
                return;
            }

            if (cantidad == 0) {
                mostrarMensaje("No se puede agregar un libro con cantidad 0. No se ha agregado.", true);
                return;
            }

            Libro libro = new Libro(titulo, autor, genero, cantidad);
            libroDAO.agregarLibro(libro);
            cargarLibros(libroDAO.obtenerTodos());
            limpiarCampos();
            mostrarMensaje("Se ha agregado correctamente.", false);
        } catch (NumberFormatException ex) {
            mostrarMensaje("Cantidad debe ser un número entero.", true);
        }
    }

    // Método para eliminar un libro de la lista y de la base de datos
    private void eliminarLibro() {
        Libro libro = listaLibros.getSelectedValue();
        if (libro == null) {
            mostrarMensaje("Selecciona un libro para eliminar.", true);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "¿Estás seguro de eliminar este libro?", "Confirmar",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            libroDAO.eliminarLibro(libro.getId());
            cargarLibros(libroDAO.obtenerTodos());
            limpiarCampos();
            mostrarMensaje("Se ha eliminado correctamente.", false);
        }
    }

    // Método para modificar un libro de la lista y de la base de datos
    private void modificarLibro() {
        Libro libro = listaLibros.getSelectedValue();
        if (libro == null) {
            mostrarMensaje("Selecciona un libro para modificar.", true);
            return;
        }
        try {
            String titulo = txtTitulo.getText().trim();
            String autor = txtAutor.getText().trim();
            String genero = txtGenero.getText().trim();
            int cantidad = Integer.parseInt(txtCantidad.getText().trim());

            if (titulo.isEmpty() || autor.isEmpty() || genero.isEmpty() || cantidad < 0) {
                mostrarMensaje("Por favor, rellena todos los campos correctamente.", true);
                return;
            }

            if (cantidad == 0) {
                // Notificar y eliminar el libro, si la cantidad es 0 se elimina porque no esta disponible
                int confirm = JOptionPane.showConfirmDialog(this,
                        "La cantidad es 0. ¿Estás seguro de eliminar este libro?", "Confirmar",
                        JOptionPane.YES_NO_OPTION);
                if (confirm != JOptionPane.YES_OPTION) {
                    return;
                }
                libroDAO.eliminarLibro(libro.getId());
                cargarLibros(libroDAO.obtenerTodos());
                limpiarCampos();
                mostrarMensaje("La cantidad llegó a 0. Se entiende que el libro ya no esta disponible.", true);
                JOptionPane.showMessageDialog(this, "La cantidad llegó a 0. Se entiende que el libro ya no esta disponible.", "Eliminado",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Modificar el libro existente generando un nuevo objeto Libro
            libro.setTitulo(titulo);
            libro.setAutor(autor);
            libro.setGenero(genero);
            libro.setCantidad(cantidad);
            libroDAO.modificarLibro(libro);
            cargarLibros(libroDAO.obtenerTodos());
            limpiarCampos();
            mostrarMensaje("Se ha modificado correctamente.", false);
        } catch (NumberFormatException ex) {
            mostrarMensaje("Cantidad debe ser un número entero.", true);
        }
    }

    // Método para buscar libros en la lista y en la base de datos
    private void buscarLibros() {
        String criterio = cmbBuscar.getSelectedItem().toString();
        String valor = txtBuscar.getText().trim();
        List<Libro> resultados;
        switch (criterio) {
            case "Título":
                resultados = libroDAO.buscarPorTitulo(valor);
                break;
            case "Autor":
                resultados = libroDAO.buscarPorAutor(valor);
                break;
            case "Género":
                resultados = libroDAO.buscarPorGenero(valor);
                break;
            default:
                resultados = libroDAO.obtenerTodos();
        }
        cargarLibros(resultados);
        mostrarMensaje("Búsqueda realizada.", false);
    }

    // Método para limpiar los campos de texto y la selección de la lista en los campos de texto de la derecha y busqueda
    private void limpiarCampos() {
        txtTitulo.setText("");
        txtAutor.setText("");
        txtGenero.setText("");
        txtCantidad.setText("");
        txtBuscar.setText("");
        listaLibros.clearSelection();
    }

    // Método para mostrar mensajes en la etiqueta
    private void mostrarMensaje(String mensaje, boolean error) {
        lblMensaje.setText(mensaje);
        if (error) {
            lblMensaje.setForeground(new Color(153, 0, 0));
        } else {
            lblMensaje.setForeground(new Color(0, 102, 0));
        }
        // Borra el mensaje después de 3 segundos
        Timer timer = new Timer(3000, e -> lblMensaje.setText(" "));
        timer.setRepeats(false);
        timer.start();
    }

    // Renderizador personalizado alineado tipo tabla
    private static class LibroCellRenderer extends DefaultListCellRenderer {
        private static final int ANCHO_TITULO = 60;
        private static final int ANCHO_AUTOR = 40;
        private static final int ANCHO_GENERO = 20;

        @Override
        // Se usa para mostrar el libro en la lista con el formato deseado
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                boolean cellHasFocus) {
            if (value instanceof Libro) {
                Libro libro = (Libro) value;
                String titulo = ajustarCampo(libro.getTitulo(), ANCHO_TITULO);
                String autor = ajustarCampo(libro.getAutor(), ANCHO_AUTOR);
                String genero = ajustarCampo(libro.getGenero(), ANCHO_GENERO);
                value = String.format(" %-4s %-60s %-40s %-20s %8d",
                        "[" + libro.getId() + "]",
                        titulo,
                        autor,
                        genero,
                        libro.getCantidad());
            }
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            label.setFont(new Font("Monospaced", Font.PLAIN, 12));
            return label;
        }

        // Recorta el campo si se pasa de largo, añade un punto al final
        private static String ajustarCampo(String texto, int ancho) {
            if (texto == null)
                texto = "";
            if (texto.length() > ancho) {
                return texto.substring(0, ancho - 1) + ".";
            } else {
                return String.format("%-" + ancho + "s", texto);
            }
        }
    }
}
