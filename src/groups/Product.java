package groups;

import java.util.ArrayList;
import java.util.List;

public class Product {
	private String nome;
	private List<Fornitore> fornitori = new ArrayList<>();
	private List<String> clienti = new ArrayList<>();
	
	
	public Product(String name, List<Fornitore> listaFornitori){
		this.nome = name;
		fornitori = listaFornitori;
	}
	
	public String getName() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public void addCliente(String c){
		//if(!clienti.contains(c)){
			clienti.add(c);
		//}
	}

	public List<String> getClienti(){
		if(clienti.isEmpty()){
			return null;
		}
	return clienti;
	}
	
	
	
	
}
