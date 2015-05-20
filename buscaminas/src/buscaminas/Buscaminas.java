package buscaminas;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;

public class Buscaminas {

	// // dimensiones del tablero por defecto 10x10
	private int alto = 10;
	private int ancho = 10;
	private int anchoBoton = 32;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Buscaminas buscaminas = new Buscaminas();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	public Buscaminas() {
		initialize();
	}

	private void initialize() {

		// Inicializar los componentes (JFrame, JPanels..)
		// El frame está compuesto de un JPanel Contenedor con 2 paneles (1 para
		// las estadísticas y el otro para el tablero)
		JFrame frame = new JFrame();
		JPanel contenedor = new JPanel();
		JPanel titulo = new JPanel();
		JPanel panelPrincipal = new JPanel();
		frame.setLayout(new BorderLayout());
		contenedor.setLayout(new BorderLayout());
		frame.setTitle("Buscaminas");

		Estadisticas estadisticas = new Estadisticas();
		estadisticas.prepararPanel(titulo, alto * ancho / 5);

		// ActionListener de smiley del panel superior
		estadisticas.getBtnSmiley().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				estadisticas.setMarcadorTiempo("0");
				reset(ancho, alto, panelPrincipal, frame, estadisticas);

			}
		});

		frame.getContentPane().add(titulo, BorderLayout.NORTH);

		// Inicializar el tablero del buscaminas
		Tablero tablero = new Tablero(alto, alto, estadisticas);
		tablero.crearTablero(panelPrincipal);
		frame.getContentPane().add(panelPrincipal, BorderLayout.CENTER);

		// Crear el menú superior
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("Archivo");
		JMenuItem nuevo = new JMenuItem("Nueva partida");

		nuevo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				estadisticas.setMarcadorTiempo("0");
				reset(ancho, alto, panelPrincipal, frame, estadisticas);
			}
		});

		JMenuItem salir = new JMenuItem("Salir");

		salir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		JMenu nivelMenu = new JMenu("Nivel");
		JRadioButtonMenuItem principiante = new JRadioButtonMenuItem(
				"Principiante");
		JRadioButtonMenuItem intermedio = new JRadioButtonMenuItem("Intermedio");
		JRadioButtonMenuItem experto = new JRadioButtonMenuItem("Experto");

		principiante.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				intermedio.setSelected(false);
				experto.setSelected(false);
				estadisticas.getMarcadorTiempo().setFont(
						new Font("OCR A Extended", Font.BOLD, 20));
				estadisticas.setMarcadorTiempo("0");
				alto = 5;
				ancho = 5;
				reset(ancho, alto, panelPrincipal, frame, estadisticas);
			}
		});

		intermedio.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				principiante.setSelected(false);
				experto.setSelected(false);
				estadisticas.getMarcadorTiempo().setFont(
						new Font("OCR A Extended", Font.BOLD, 35));
				estadisticas.setMarcadorTiempo("0");
				alto = 10;
				ancho = 10;
				reset(ancho, alto, panelPrincipal, frame, estadisticas);
			}
		});

		experto.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				intermedio.setSelected(false);
				principiante.setSelected(false);
				estadisticas.getMarcadorTiempo().setFont(
						new Font("OCR A Extended", Font.BOLD, 35));
				estadisticas.setMarcadorTiempo("0");
				alto = 18;
				ancho = 18;
				reset(18, 18, panelPrincipal, frame, estadisticas);

			}
		});

		intermedio.setSelected(true);
		fileMenu.add(nuevo);
		fileMenu.add(salir);
		nivelMenu.add(principiante);
		nivelMenu.add(intermedio);
		nivelMenu.add(experto);
		menuBar.add(fileMenu);
		menuBar.add(nivelMenu);
		frame.setJMenuBar(menuBar);

		frame.setBounds(0, 0, anchoBoton * ancho + 15, anchoBoton * alto + 100);
		frame.setResizable(false);
		frame.setVisible(true);

	}

	public void reset(int ancho, int alto, JPanel panel, JFrame frame,
			Estadisticas estadisticas) {
		// Reiniciar tablero, temporizador, etc..
		Tablero tablero = new Tablero(alto, alto, estadisticas);
		estadisticas.getTimer().cancel();
		estadisticas.getTimer().purge();
		estadisticas.getBtnSmiley().setIcon(estadisticas.getSmiley());
		estadisticas.iniciarCronometro();
		estadisticas.inicializarBanderas(tablero.getnColumnas()
				* tablero.getnFilas() / 5);
		tablero.crearTablero(panel);
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		frame.setBounds(0, 0, anchoBoton * ancho + 15, anchoBoton * alto + 100);
	}

}
