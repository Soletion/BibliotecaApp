package com.biblioteca;

import com.biblioteca.gui.BibliotecaGUI;
import com.biblioteca.dao.LibroDAO;

public class Main {
    public static void main(String[] args) {
        // Ejecutar la interfaz gráfica en el hilo de eventos de Swing
        javax.swing.SwingUtilities.invokeLater(() -> {
            BibliotecaGUI gui = new BibliotecaGUI();
            gui.setVisible(true);
        });

        // Al cerrar la aplicación, liberar recursos de la base de datos (se desconecta automáticamente)
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            LibroDAO.cerrar();
        }));
    }
}
