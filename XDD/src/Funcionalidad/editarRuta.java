package Funcionalidad;

import Clases.Rutas;
import Clases.listaRutas;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class editarRuta extends JFrame implements ActionListener {

    listaRutas lista_Rutas = practica2.Practica2.lista_Rutas;

    JPanel panel = new JPanel();
    JLabel idRuta = new JLabel("Ingrese el ID de la ruta");
    JTextField txtRuta = new JTextField();
    JLabel Distancia = new JLabel("Ingrese nueva Distancia");
    JTextField txtDistancia = new JTextField();
    JButton btnaceptar = new JButton("Aceptar");
    JButton btncancelar = new JButton("Cancelar");

    public editarRuta() {

        this.setSize(400, 250);
        this.setLocationRelativeTo(this);
        this.setTitle("Editar");
        this.setResizable(false);
        this.setContentPane(panel);

        panel.setBackground(Color.orange);
        panel.setLayout(null);

        idRuta.setBounds(75, 10, 500, 30);
        idRuta.setFont(new Font("Consolas", Font.BOLD, 16));

        txtRuta.setBounds(125, 40, 110, 30);

        Distancia.setBounds(75, 70, 500, 30);
        Distancia.setFont(new Font("Consolas", Font.BOLD, 16));

        txtDistancia.setBounds(125, 100, 110, 30);

        btncancelar.setBounds(50, 150, 100, 30);
        btncancelar.addActionListener(this);

        btnaceptar.setBounds(200, 150, 100, 30);
        btnaceptar.addActionListener(this);

        panel.add(idRuta);
        panel.add(Distancia);
        panel.add(txtRuta);
        panel.add(txtDistancia);
        panel.add(btnaceptar);
        panel.add(btncancelar);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnaceptar)
        {
            // Obtener el ID de la ruta y la nueva distancia desde los campos de texto
            int idRuta;
            int nuevaDistancia;
            try
            {
                idRuta = Integer.parseInt(txtRuta.getText());
                nuevaDistancia = Integer.parseInt(txtDistancia.getText());
            } catch (NumberFormatException ex)
            {
                JOptionPane.showMessageDialog(null, "Por favor, ingrese números válidos para el ID de la ruta y la nueva distancia.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Buscar la ruta en la lista de rutas
            Rutas ruta = null;
            for (Rutas r : lista_Rutas.getLista_rutas())
            {
                if (r.getCodigo() == idRuta)
                {
                    ruta = r;
                    break;
                }
            }

            // Verificar si se encontró la ruta
            if (ruta != null)
            {
                // Actualizar la distancia de la ruta
                ruta.setDistancia(nuevaDistancia);
                JOptionPane.showMessageDialog(null, "La distancia de la ruta con ID " + idRuta + " se ha actualizado correctamente.", "Información", JOptionPane.INFORMATION_MESSAGE);

                // Actualizar el archivo binario con la nueva distancia
                actualizarArchivoBinario("R.bin", lista_Rutas.getLista_rutas());
            } else
            {
                JOptionPane.showMessageDialog(null, "No se encontro", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == btncancelar)
        {
            this.dispose();
        }
    }

    private void actualizarArchivoBinario(String nombreArchivo, ArrayList<Rutas> rutas) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nombreArchivo)))
        {
            // Escribir todas las rutas en el archivo binario
            for (Rutas ruta : rutas)
            {
                oos.writeObject(ruta);
            }
            // Llamar a flush() para asegurar que los datos se escriban en el archivo
            oos.flush();
            JOptionPane.showMessageDialog(null, "Archivo binario actualizado correctamente.", "Información", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al actualizar el archivo binario.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
