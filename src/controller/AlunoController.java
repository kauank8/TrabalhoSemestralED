package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import model.Aluno;

public class AlunoController implements ActionListener {
	private JTextField nome;
	private JTextField Ra;

	public AlunoController(JTextField nome, JTextField ra) {
		super();
		this.nome = nome;
		Ra = ra;
	}

	// Ação do botao
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("Gravar Aluno")) {
			try {
				RegistraAluno();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	private void RegistraAluno() throws IOException {
		Aluno aluno = new Aluno();
		aluno.setNome(nome.getText());
		aluno.setRA(Ra.getText());
		
		if(!aluno.getNome().equals("") && !aluno.getRA().equals("")) {
		// Verifica Tamanho Do Ra
		String[] vtRa = aluno.getRA().split("");
		int tamanho = vtRa.length;

		// verifica as condições
		if (tamanho > 0 && tamanho < 14) {
			String path = System.getProperty("user.home") + File.separator + "SistemaTCC";
			File dir = new File(path);
			if (!dir.exists()) {
				dir.mkdir();
			}
			File arq = new File(path, "BancoAlunos.csv");
			boolean existe = false;

			if (arq.exists()) {
				existe = true;
			}

			FileWriter filewrite = new FileWriter(arq, existe);
			PrintWriter print = new PrintWriter(filewrite);
			print.write(aluno.toString());
			print.flush();
			print.close();
			filewrite.close();

			JOptionPane.showMessageDialog(null, "Aluno(a) " + aluno.getNome() + " Foi Registrado", "Sucesso!",
					JOptionPane.INFORMATION_MESSAGE);
			nome.setText("");
			Ra.setText("");

		} else {
			JOptionPane.showMessageDialog(null, "Ra Invalido, Por favor Digite até 13 Números", "Erro",
					JOptionPane.ERROR_MESSAGE);
			Ra.setText("");
		}
	}
		else {
			JOptionPane.showMessageDialog(null, "Por favor, Preencha Todos os Campos", "Erro",
					JOptionPane.ERROR_MESSAGE);
		}

	}

}
