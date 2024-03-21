package Ventanas;

import Clases.Rutas;
import Clases.listaRutas;
import Funcionalidad.editarRuta;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class Admin extends JFrame implements ActionListener, Serializable {

    public static listaRutas lista_Rutas = practica2.Practica2.lista_Rutas;

    private JTable tabla;
    private JScrollPane scrollPane;
    private DefaultTableModel model;

    JPanel panel = new JPanel();

    String encabezado[] =
    {
        "Código", "Inicio", "Fin", "Distancia"
    };
    DefaultTableModel modeloTabla = new DefaultTableModel(encabezado, 0);
    JTable tablaRutas = new JTable(modeloTabla);
    JScrollPane jsRutas = new JScrollPane(tablaRutas);
    JLabel lbrutas = new JLabel("Ingrese las Rutas:");
    JButton cargar = new JButton("Cargar Rutas");
    JButton editar = new JButton("Editar Rutas");

    JButton borrar = new JButton("Borrar Datos CSV");

    public Admin() {
        this.setSize(800, 500);
        this.setLocationRelativeTo(this);
        this.setTitle("Udrive Admin");
        this.setResizable(false);
        this.setContentPane(panel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel.setBackground(Color.orange);
        panel.setLayout(null);

        lbrutas.setBounds(10, 10, 500, 30);
        lbrutas.setFont(new Font("Consolas", Font.BOLD, 30));

        cargar.setBounds(30, 50, 150, 30);
        cargar.setForeground(Color.black);
        cargar.setBackground(Color.GREEN);
        cargar.addActionListener(this);

        editar.setBounds(600, 50, 150, 30);
        editar.setForeground(Color.BLACK);
        editar.setBackground(Color.cyan);
        editar.addActionListener(this);

        borrar.setBounds(475, 50, 100, 30);
        borrar.setForeground(Color.BLACK);
        borrar.setBackground(Color.RED);
        borrar.addActionListener(this);

        tabla = new JTable();
        jsRutas = new JScrollPane(tabla);
        jsRutas.setBounds(10, 100, 760, 350);

        mostrarDatosEnTablaDesdeBinario(new File("R.bin"));

        // Cargar datos al iniciar la aplicación
        panel.add(lbrutas);
        panel.add(cargar);
        panel.add(editar);
        panel.add(jsRutas);
        panel.add(borrar);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cargar)
        {
            JFileChooser selectorArchivo = new JFileChooser();
            selectorArchivo.setCurrentDirectory(new File(System.getProperty("user.dir")));
            selectorArchivo.setFileFilter(new FileNameExtensionFilter("Archivos CSV", "csv"));
            selectorArchivo.addChoosableFileFilter(new FileNameExtensionFilter("Archivos Binarios", "bin")); // Permitir archivos binarios

            int resultado = selectorArchivo.showOpenDialog(null);

            if (resultado == JFileChooser.APPROVE_OPTION)
            {
                File archivoSeleccionado = selectorArchivo.getSelectedFile();

                if (archivoSeleccionado.getName().endsWith(".csv"))
                {
                    // Archivo CSV seleccionado
                    mostrarDatosEnTabla(archivoSeleccionado);
                    serializarRutasDesdeArchivo(archivoSeleccionado);
                } else if (archivoSeleccionado.getName().endsWith(".bin"))
                {
                    // Archivo binario seleccionado
                    mostrarDatosEnTablaDesdeBinario(archivoSeleccionado);
                } else
                {
                    // Tipo de archivo no válido
                    JOptionPane.showMessageDialog(null, "Tipo de archivo no válido. Por favor, seleccione un archivo CSV o binario.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else if (resultado == JFileChooser.CANCEL_OPTION)
            {
                JOptionPane.showMessageDialog(null, "Carga de archivo cancelada", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (e.getSource() == editar)
        {
            editarRuta pestaña = new editarRuta();
            pestaña.setVisible(true);

        } else if (e.getSource() == borrar)
        {
            limpiarArchivoBinario("R.bin");
        }
    }

    public void limpiarArchivoBinario(String nombreArchivo) {
        File archivo = new File(nombreArchivo);
        if (archivo.exists())
        {
            if (archivo.delete())
            {
                System.out.println("Archivo binario limpiado con éxito.");
            } else
            {
                System.err.println("No se pudo limpiar el archivo binario.");
            }
        } else
        {
            System.out.println("El archivo binario no existe.");
        }
    }

    private void mostrarDatosEnTabla(File archivo) {
        // Cargar datos desde el archivo binario

        ArrayList<Rutas> listaRutas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(archivo)))
        {
            String linea;
            boolean primeraLinea = true;
            while ((linea = br.readLine()) != null)
            {
                if (primeraLinea)
                {
                    primeraLinea = false;
                    continue;
                }
                String[] datos = linea.split(",");
                if (datos.length >= 3)
                {
                    String inicios = datos[0];
                    String fines = datos[1];
                    try
                    {
                        int distancia = Integer.parseInt(datos[2]);
                        Rutas ruta = new Rutas(inicios, fines, distancia);
                        listaRutas.add(ruta);

                    } catch (NumberFormatException e)
                    {
                        System.err.println("Error al convertir la distancia a número: " + e.getMessage());
                    }
                }
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        // Agregar las nuevas rutas a la lista de rutas existente
        for (Rutas ruta : listaRutas)
        {
            lista_Rutas.guardarRutas(ruta);
        }

        // Si el modelo de la tabla no está inicializado, crearlo
        if (model == null)
        {
            String[] columnNames =
            {
                "Codigo", "Inicio", "Fin", "Distancia"
            };
            model = new DefaultTableModel(columnNames, 0);
            tabla.setModel(model);
        }

        // Agregar las nuevas filas al modelo de la tabla
        for (Rutas ruta : listaRutas)
        {
            Object[] fila =
            {
                ruta.getCodigo(), ruta.getInicio(), ruta.getFin(), ruta.getDistancia()
            };
            model.addRow(fila);
        }
    }

    private void mostrarDatosEnTablaDesdeBinario(File archivoBinario) {
        // Cargar datos desde el archivo binario
        ArrayList<Rutas> listaRutas = new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivoBinario)))
        {
            Object obj;
            while ((obj = ois.readObject()) != null)
            {
                if (obj instanceof Rutas)
                {
                    Rutas ruta = (Rutas) obj;
                    listaRutas.add(ruta);

                }
            }
        } catch (EOFException e)
        {
            // Se alcanzó el final del archivo, no hacer nada
        } catch (ClassNotFoundException | IOException e)
        {
            e.printStackTrace();
        }

        // Agregar las nuevas rutas a la lista de rutas existente
        for (Rutas ruta : listaRutas)
        {
            lista_Rutas.guardarRutas(ruta);
        }

        // Si el modelo de la tabla no está inicializado, crearlo
        if (model == null)
        {
            String[] columnNames =
            {
                "Codigo", "Inicio", "Fin", "Distancia"
            };
            model = new DefaultTableModel(columnNames, 0);
            tabla.setModel(model);
        }

        // Agregar las nuevas filas al modelo de la tabla
        for (Rutas ruta : listaRutas)
        {
            Object[] fila =
            {
                ruta.getCodigo(), ruta.getInicio(), ruta.getFin(), ruta.getDistancia()
            };
            model.addRow(fila);
        }
    }

   public void serializarRutasDesdeArchivo(File archivo) {
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("R.bin"))) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            boolean primeraLinea = true;
            while ((linea = br.readLine()) != null) {
                if (primeraLinea) {
                    primeraLinea = false;
                    continue;
                }
                String[] datos = linea.split(",");
                if (datos.length >= 3) {
                    String inicios = datos[0];
                    String fines = datos[1];
                    try {
                        int distancia = Integer.parseInt(datos[2]);
                        // Crear un objeto Ruta y serializarlo
                        Rutas ruta = new Rutas(inicios, fines, distancia);
                        oos.writeObject(ruta);
                    } catch (NumberFormatException e) {
                        // Maneja errores de conversión de distancia a número
                    }
                }
            }
        } catch (IOException e) {
            // Maneja errores de lectura del archivo CSV
            e.printStackTrace();
        }
    } catch (IOException e) {
        // Maneja errores de apertura o cierre del ObjectOutputStream
        e.printStackTrace();
    }
}
}
