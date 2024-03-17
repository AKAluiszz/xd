package Funcionalidad;

import Clases.Rutas;
import Clases.listaRutas;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class editarDistancia extends JFrame implements ActionListener {

    listaRutas lista_rutas = CSV.CSV.lista_Rutas;

    JPanel panel = new JPanel();
    JLabel idRuta = new JLabel("Ingrese el ID de la ruta");
    JTextField txtRuta = new JTextField();
    JLabel Distancia = new JLabel("Ingrese nueva Distancia");
    JTextField txtDistancia = new JTextField();
    JButton aceptar = new JButton("Aceptar");
    JButton cancelar = new JButton("Cancelar");

    public editarDistancia() {

        this.setSize(400, 250);
        this.setLocationRelativeTo(this);
        this.setTitle("Editar");
        this.setResizable(false);
        this.setContentPane(panel);
        // this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel.setBackground(Color.orange);
        panel.setLayout(null);

        idRuta.setBounds(75, 10, 500, 30);
        idRuta.setFont(new Font("Consolas", Font.BOLD, 16));

        txtRuta.setBounds(125, 40, 110, 30);

        Distancia.setBounds(75, 70, 500, 30);
        Distancia.setFont(new Font("Consolas", Font.BOLD, 16));

        txtDistancia.setBounds(125, 100, 110, 30);

        cancelar.setBounds(50, 150, 100, 30);
        cancelar.addActionListener(this);

        aceptar.setBounds(200, 150, 100, 30);
        aceptar.addActionListener(this);

        panel.add(idRuta);
        panel.add(Distancia);
        panel.add(txtRuta);
        panel.add(txtDistancia);
        panel.add(aceptar);
        panel.add(cancelar);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == aceptar)
        {

        } else if (e.getSource() == cancelar)
        {
            // Cerrar la ventana si se presiona el botón "Cancelar"
            this.dispose();
        }
    }

}
