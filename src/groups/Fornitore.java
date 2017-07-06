package groups;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Fornitore {
	private String nome;
	private Map<String, Product> prodotti = new TreeMap();
	private List<Bid> offerte = new ArrayList<>();
	
	public Fornitore(String nome) {
		this.nome = nome;
	}
	public String getName() {
		return nome;
	}
	public void setName(String nome) {
		this.nome = nome;
	}
	public List<Product> getProdotti() {
		return prodotti.values().stream()
				.collect(Collectors.toList());
	}
	public List<String> getProdottiNames(){
		return prodotti.keySet().stream()
				.collect(Collectors.toList());
	}
	public void setProdotti(Map<String, Product> prodotti) {
		this.prodotti = prodotti;
	}
	public void addProdotto(String nome, Product p){
		if(!prodotti.containsValue(p)){
		prodotti.put(nome, p);}
	}
	public int getNumOfferte(){
		return offerte.size();
	}
	public void addOfferta(Bid o){
		if(!offerte.contains(o)){
			offerte.add(o);
		}
	}
	public List<Bid> getOfferte(){
		return offerte;
	}
	
	
	

}
