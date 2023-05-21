package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import br.com.KauanPaulino.pilhaString.Pilha;

public class ConsultaOrientacaoController implements ActionListener {
	private JTextArea ta;
	private JTextField codigo;
	private Pilha pilha;

	public ConsultaOrientacaoController(JTextArea ta, JTextField codigo) {
		this.ta = ta;
		this.codigo = codigo;
		pilha = new Pilha();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();

		if (cmd.equals("Ultima Orientação")) {
			try {
				montaPilha();
				UltimaOrientacao();
				codigo.setText("");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		if (cmd.equals("Todas Orientações")) {
			try {
				montaPilha();
				TodasOrientacao();
				codigo.setText("");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	private void montaPilha() throws IOException {
		String path = System.getProperty("user.home") + File.separator + "SistemaTCC";
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdir();
		}
		boolean status = false;
		File arqgrupos = new File(path, "BancoOrientacoes.csv");
		if (arqgrupos.exists() && arqgrupos.isFile()) {
			FileInputStream fluxo = new FileInputStream(arqgrupos);
			InputStreamReader leitorfluxo = new InputStreamReader(fluxo);
			BufferedReader buffer = new BufferedReader(leitorfluxo);
			String linha = buffer.readLine();
			while (linha != null) {
				String[] vtlinha = linha.split(";");
				if (codigo.getText().equals(vtlinha[0])) {
					String txt = vtlinha[0] + " " + vtlinha[1] + "\n";
					pilha.push(txt);
					status = true;
				}
				linha = buffer.readLine();
			}
			if (!status || codigo.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Codigo Invalido ou Codigo em branco", "Erro",
						JOptionPane.ERROR_MESSAGE);
				codigo.setText("");
			}
		}
	}

	private void UltimaOrientacao() throws Exception {
		ta.setText(pilha.top());
	}

	private void TodasOrientacao() throws Exception {
		Pilha pilhaaux = pilha;
		String txt = "";
		int tamanho = pilha.size();
		for (int i = 0; i < tamanho; i++) {
			txt += pilhaaux.pop();

		}
		ta.setText(txt);
	}

}
