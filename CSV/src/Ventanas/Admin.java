package Ventanas;

import Clases.Rutas;
import Clases.listaRutas;
import Funcionalidad.editarDistancia;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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

public class Admin extends JFrame implements ActionListener {

    private JTable tabla;
    private JScrollPane scrollPane;
    listaRutas lista_Rutas = CSV.CSV.lista_Rutas;

    JPanel panel = new JPanel();
    String encabezado[] =
    {
        "Código", "Inicio", "Fin", "Distancia"
    };
    DefaultTableModel modeloTabla = new DefaultTableModel(encabezado, 0);
    JTable tablaRutas = new JTable(modeloTabla);
    JScrollPane jsRutas = new JScrollPane(tablaRutas);
    JButton cargar = new JButton("Cargar Rutas");
    JButton editar = new JButton("Editar Rutas");
    JButton volver = new JButton("Volver");

    public Admin() {
        this.setSize(800, 500);
        this.setLocationRelativeTo(this);
        this.setTitle("Udrive Admin");
        this.setResizable(false);
        this.setContentPane(panel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel.setBackground(Color.orange);
        panel.setLayout(null);

        JLabel lbrutas = new JLabel("Ingrese las Rutas:");
        lbrutas.setBounds(10, 10, 500, 30);
        lbrutas.setFont(new Font("Consolas", Font.BOLD, 30));

        cargar.setBounds(30, 50, 200, 30);
        cargar.setForeground(Color.black);
        cargar.setBackground(Color.GREEN);
        cargar.addActionListener(this);

        //Editar             posx   posy    ancho       altura
        editar.setBounds(500, 50, 200, 30);
        editar.setForeground(Color.BLACK);
        editar.setBackground(Color.cyan);
        editar.addActionListener(this);

        volver.setBounds(300, 50, 100, 30);
        volver.setForeground(Color.BLACK);
        volver.setBackground(Color.cyan);
        volver.addActionListener(this);
        
        tabla = new JTable();
        jsRutas = new JScrollPane(tabla);
        jsRutas.setBounds(10, 100, 760, 350);

        panel.add(lbrutas);
        panel.add(cargar);
        panel.add(editar);
        panel.add(jsRutas);
         panel.add(volver);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cargar)
        {
            JFileChooser selectorArchivo = new JFileChooser();
            selectorArchivo.setCurrentDirectory(new File(System.getProperty("user.dir")));
            selectorArchivo.setFileFilter(new FileNameExtensionFilter("Archivos CSV", "csv"));

            int resultado = selectorArchivo.showOpenDialog(null);

            if (resultado == JFileChooser.APPROVE_OPTION)
            {
                File archivoSeleccionado = selectorArchivo.getSelectedFile();
                mostrarDatosEnTabla(archivoSeleccionado);

            } else if (resultado == JFileChooser.CANCEL_OPTION)
            {
                JOptionPane.showMessageDialog(null, "Carga de archivo cancelada", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (e.getSource() == editar)
        {
            editarDistancia pestaña = new editarDistancia();
            pestaña.setVisible(true);
        }else if(e.getSource()==volver){
            this.dispose();
        }
    }

    private void mostrarDatosEnTabla(File archivo) {
        ArrayList<Rutas> listaRutas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(archivo)))
        {
            String linea;
            boolean primeraLinea = true; // Para saltar la primera línea del archivo CSV
            while ((linea = br.readLine()) != null)
            {
                if (primeraLinea)
                {
                    primeraLinea = false;
                    continue; // Saltar la primera línea
                }
                String[] datos = linea.split(",");
                if (datos.length >= 3)
                { // Asegurarse de que haya al menos tres campos
                    String inicio = datos[0];
                    String fin = datos[1];
                    try
                    {
                        int distancia = Integer.parseInt(datos[2]);
                        Rutas ruta = new Rutas(inicio, fin, distancia); // Crear instancia de Rutas con los datos del CSV
                        listaRutas.add(ruta);
                        System.out.println("Ruta agregada: Codigo " + ruta.getCodigo() + ", Inicio: " + inicio + ", Fin: " + fin + ", Distancia: " + distancia);
                    } catch (NumberFormatException e)
                    {
                        // Manejar la excepción si no se puede convertir la distancia a un número
                        System.err.println("Error al convertir la distancia a número: " + e.getMessage());
                    }
                }
            }
        } catch (IOException e)
        {
            e.printStackTrace(); // Manejo básico de errores, imprime en consola
        }

        // Ahora que tienes la lista de rutas, necesitas convertirla en un arreglo de objetos
        Object[][] data = new Object[listaRutas.size()][4];
        for (int i = 0; i < listaRutas.size(); i++)
        {
            Rutas ruta = listaRutas.get(i);
            data[i][0] = ruta.getCodigo();
            data[i][1] = ruta.getInicio();
            data[i][2] = ruta.getFin();
            data[i][3] = ruta.getDistancia();
        }

        // Definir los nombres de las columnas
        String[] columnNames =
        {
            "Codigo", "Inicio", "Fin", "Distancia"
        };

        // Crear el modelo de la tabla y establecer los datos y nombres de las columnas
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        tabla.setModel(model);
    }

}
