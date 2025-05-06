package br.com.cod3r.cm.modelo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

import br.com.cod3r.cm.excecao.ExplosaoException;

public class Tabuleiro {
	
	private int quantidadeDeLinhas;
	private int quantidadeDeColunas;
	private int quantidadeDeMinas;
	
	private final List<Campo> campos = new ArrayList<>();
	
	//Instanciando um construtor com os parametros acima
	public Tabuleiro(int quantidadeDeLinhas, 
			int quantidadeDeColunas, int quantidadeDeMinas) {
		this.quantidadeDeColunas = quantidadeDeColunas;
		this.quantidadeDeLinhas = quantidadeDeLinhas;
		this.quantidadeDeMinas = quantidadeDeMinas;
		gerarCampos();
		associarOsVizinhos();
		sortearMinas();
	}
	
	public void abrir(int linha, int coluna) {
		
		try {
			campos.parallelStream()
					.filter(c -> c.getLinha() 
					== linha && c.getColuna() == 
					coluna)
					.findFirst()
					.ifPresent(c -> c.abrir());	
			
		} catch(ExplosaoException e) {
			campos.forEach(c -> c.setAberto(true));
			throw e;
		}
		
	}
	
	public void alternarMarcacao(int linha, int coluna) {
		campos.parallelStream().filter(c -> c.getLinha() 
				== linha && c.getColuna() == 
				coluna).findFirst().ifPresent(c -> c.alternarMarcacao());
	}
	
	private void sortearMinas() {
		long minasArmadas = 0;
		Predicate<Campo> minado = c -> c.isMinado(); 
		
		do {
			int aleatorio = (int) (Math.random() * campos.size());
			campos.get(aleatorio).minar();
			minasArmadas = campos.stream().filter(minado).count();
		} while(minasArmadas < quantidadeDeMinas);
	}
	
	// Criar 2 forEach para percorrer a lista campos e relacionar os vizinhos
	private void associarOsVizinhos() {
		for(Campo c1: campos) {
			for(Campo c2: campos) {
				c1.adicionarVizinho(c2);
			}
		}
	}
	
	private void gerarCampos() {
		for(int i = 0; i < quantidadeDeLinhas; i++) {
			for(int j = 0; j < quantidadeDeColunas; j++) {
				campos.add(new Campo(quantidadeDeLinhas,
						quantidadeDeColunas));
			}
		}
	}
	
	public boolean objetivoAlcancado() {
		return campos.stream().allMatch(c -> c.objetivoAlcancado());
	}
	
	public void reiniciar() {
		campos.stream().forEach(c -> c.reiniciar());
		sortearMinas();
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(" ");
		for(int c = 0; c < quantidadeDeColunas; c++) {
			sb.append(" ");
			sb.append(c);
			sb.append(" ");
		}
		sb.append("\n");
		
		
		
		int i = 0;
		for(int l = 0; l < quantidadeDeLinhas; l++) {
			sb.append(l);
			sb.append(" ");
			for(int c = 0; c < quantidadeDeColunas; c++) {
				sb.append(" ");
				sb.append(campos.get(i));
				sb.append(" ");
				i++;
			}
			sb.append("\n");
		}
		
		return sb.toString();
	}
}
