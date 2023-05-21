package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import br.com.KauanPaulino.ListaObject.Lista_Object;
import br.com.KauanPaulino.ListaString.Lista_String;
import model.Grupos;

public class ConsultarAreaController implements ActionListener {
	private JTextField area;
	private JTextArea ta;
	private Lista_String[] lista;
	private Grupos gp;

	public ConsultarAreaController(JTextField area, JTextArea ta) {
		super();
		this.area = area;
		this.ta = ta;
		gp = new Grupos();
		lista=new Lista_String[26];
		for (int i = 0; i < 26; i++) {
			lista[i] = new Lista_String();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			ta.setText("");
			TabelaDeEspalhamento();
			Consulta();
			LimpaLista();
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	//Cria Tabela de Esplhamento Baseada em Ordem alfabetica
	private void TabelaDeEspalhamento() throws Exception {
		String path = System.getProperty("user.home") + File.separator + "SistemaTCC";
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdir();
		}
		boolean status = true;
		File arqgrupos = new File(path, "BancoGrupos.csv");
		if (arqgrupos.exists() && arqgrupos.isFile()) {
			FileInputStream fluxo = new FileInputStream(arqgrupos);
			InputStreamReader leitorfluxo = new InputStreamReader(fluxo);
			BufferedReader buffer = new BufferedReader(leitorfluxo);
			String linha = buffer.readLine();
			while (linha != null) {
				String[] vtlinha = linha.split(";");
				String vtlinhaM = vtlinha[1].toLowerCase();
				int hash = HashCode(vtlinhaM);
				VerificaLista(hash, vtlinhaM, vtlinha);
				linha=buffer.readLine();
			}
		}

	}

	//Verifica Se possui o mesma area na lista;
	private void VerificaLista(int hash, String vtarea, String[] vtlinha) throws Exception {
		int tamanho = lista[hash].size();
		String txt = "";
		String txt2 = "";
		boolean status = true;
		if (lista[hash].isEmpty()) {
			int tam = vtlinha.length;
			for (int i = 0; i < tam; i++) {
				txt += vtlinha[i] + " ";
			}
			lista[hash].addFirst(txt);
		} else {
			for (int i = 0; i < tamanho; i++) {
				String compara = lista[hash].get(i);
				if (compara.equals(vtarea)) {
					status = false;
					break;
				}
			}
			if (status) {
				int tam = vtlinha.length;
				for (int i = 0; i < tam; i++) {
					txt2 += vtlinha[i] + " ";
				}
				lista[hash].addLast(txt2);
			}
		}
	}

	private int HashCode(String area) {
		int posicao = area.toLowerCase().charAt(0);
		posicao -= 97;
		return posicao;
	}

	private void Consulta() throws Exception {
		
		String txt="";
		String areaCompara=area.getText().toLowerCase();
		int hash=HashCode(areaCompara);
		int tamanho = lista[hash].size();
		boolean status = false;
		for(int i=0;i<tamanho;i++) {
			String[] vtlinha = lista[hash].get(i).split(" ");
			if(areaCompara.equals(vtlinha[1].toLowerCase())) {
				int tam = vtlinha.length;
				for (int x = 0; x < tam; x++) {
					txt += vtlinha[x] + " ";
					if(x==tam-1) {
						txt+="\n";
					}
				}
				status = true;
				
			}
		}
		ta.setText(txt);
		if(!status) {
			JOptionPane.showMessageDialog(null, "Nenhum grupo contém essa area", "Erro",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void LimpaLista() throws Exception {
		int hash = HashCode(area.getText());
		int tamanho=lista[hash].size();
		for (int i = 0; i < tamanho; i++) {
			lista[hash].remove(0);
		}
	}

}
