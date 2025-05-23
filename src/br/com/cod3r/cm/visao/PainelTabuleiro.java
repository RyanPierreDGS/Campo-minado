package br.com.cod3r.cm.visao;

import java.awt.GridLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import br.com.cod3r.cm.modelo.Tabuleiro;

@SuppressWarnings("serial")
public class PainelTabuleiro extends JPanel {

	public PainelTabuleiro(Tabuleiro tabuleiro) {
		
		setLayout(new GridLayout(
				tabuleiro.getQuantidadeDeLinhas(), 
				tabuleiro.getQuantidadeDeColunas()));
		
		tabuleiro.paraCada(c -> add(new BotaoCampo(c)));
		tabuleiro.registrarObservador(e -> {
			SwingUtilities.invokeLater(() -> {
				if(e.isGanhou()) {
					JOptionPane.showMessageDialog(this, "ganhou");
				} else {
					JOptionPane.showMessageDialog(this, "perdeu");
				}
				tabuleiro.reiniciar();
			});	
		});
		
	}
	
}
