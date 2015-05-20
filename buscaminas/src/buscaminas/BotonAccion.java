package buscaminas;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class BotonAccion extends MouseAdapter {

	private Celda celda;
	private Tablero tablero;
	private Celda[][] celdas;
	private Estadisticas estadisticas;

	public BotonAccion(Celda celda, Tablero tablero, Estadisticas estadisticas) {
		this.celda = celda;
		this.tablero = tablero;
		this.celdas = tablero.getCeldas();
		this.estadisticas = estadisticas;
	}

	public void mouseClicked(MouseEvent e) {
		if (!tablero.isJuegoAcabado())
			jugar(e);
	}

	public void mousePressed(MouseEvent e) {
		if (!tablero.isJuegoAcabado())
			estadisticas.getBtnSmiley().setIcon(estadisticas.getSurprise());
	}

	public void mouseReleased(MouseEvent e) {
		if (!tablero.isJuegoAcabado())
			estadisticas.getBtnSmiley().setIcon(estadisticas.getSmiley());
	}

	public void jugar(MouseEvent e) {

		Celda celda = this.celda;

		// Click botón izquierdo
		if (e.getButton() == e.BUTTON1 && celda.getEstado() != 2) {
			if (celda.tieneMina()) {
				celda.setEstado(1);
				finJuego(estadisticas.getCry());
				celda.destaparCelda();
			} else {
				vaciar(celda.getX(), celda.getY());
			}

			// Click botón derecho
		} else if (e.getButton() == e.BUTTON3) {
			if (celda.getEstado() == 2) {
				celda.setEstado(0);
				celda.quitarBandera();
				estadisticas.sumarBandera();
			} else if (celda.getEstado() == 0
					&& Integer.valueOf(estadisticas.getMarcadorBanderas()
							.getText()) > 0) {
				celda.setEstado(2);
				celda.ponerBandera();
				estadisticas.restarBandera();
				if (Integer.valueOf(estadisticas.getMarcadorBanderas()
						.getText()) == 0) {
					if (tablero.comprobar()) {
						finJuego(estadisticas.getLOL());
						JOptionPane
								.showMessageDialog(new JFrame(),
										"Enhorabuena has encontrado todas las minas!! ;)");
					} else {
						finJuego(estadisticas.getCry());
						JOptionPane
								.showMessageDialog(new JFrame(),
										"No has colocado las banderas correctamente :(");
					}
				}
			}
		}
	}

	public void finJuego(ImageIcon icon) {
		estadisticas.getBtnSmiley().setIcon(icon);
		estadisticas.getTimer().cancel();
		estadisticas.getTimer().purge();
		tablero.setJuegoAcabado();
	}

	public void vaciar(int x, int y) {
		if (x < 0 || x > this.tablero.getnColumnas() - 1 || y < 0
				|| y > this.tablero.getnFilas() - 1)
			return;
		Celda celda = celdas[y][x];
		if (celda.getVecinos() > 0 && celda.getEstado() != 2) {
			celda.setEstado(1);
			celda.destaparCelda();
		}
		if (x >= 0 && y >= 0 && x < this.tablero.getnColumnas()
				&& y < this.tablero.getnFilas()) {
			if (!celda.tieneMina() && celda.getEstado() == 0) {
				celda.setEstado(1);
				celda.destaparCelda();
				// Vaciar recursivamente el tablero
				vaciar(x, y + 1);
				vaciar(x, y - 1);
				vaciar(x + 1, y);
				vaciar(x - 1, y);
				vaciar(x - 1, y - 1);
				vaciar(x - 1, y + 1);
				vaciar(x + 1, y + 1);
				vaciar(x + 1, y - 1);
			}
		}
	}

}
