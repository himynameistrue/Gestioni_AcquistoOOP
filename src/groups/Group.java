package groups;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Group {
	private String nome;
	private Product prodotto;
	private List<String> clienti = new ArrayList<>();
	private List<String> fornitori = new ArrayList<>();
	private Map<String, Bid> offerte = new HashMap<>();
	private Bid offertaVincitrice;
	
	
	public Group(String nome, Product p, List<String> clienti) {
		this.nome = nome;
		this.prodotto = p;
		this.clienti = clienti;
	}
	
	public String getName() {
		return nome;
	}
	public void setName(String nome) {
		this.nome = nome;
	}
	
	public Product getProdotto() {
		return prodotto;
	}
	
	public void setProdotto(Product p) {
		this.prodotto = p;
	}
	public List<String> getClienti() {
		return clienti.stream()
				.sorted()
				.collect(Collectors.toList());
	}
	public void setClienti(List<String>  clienti) {
		this.clienti =  clienti;
	}
	
	public void addFornitore(String f){
		if(!fornitori.contains(f)){
		fornitori.add(f);}
	}
	
	public List<String> getFornitori() {
		return fornitori.stream()
				.sorted()
				.collect(Collectors.toList());
	}
	
	public void addBid(String f, Bid b){
		if(!offerte.containsKey(f)){
			offerte.put(f, b);}
	}
	public List<String> getFornitoriOfferte(){
		return offerte.values().stream()
				.map(Bid::getFornitore)
				.collect(Collectors.toList());
	}
	
	public Map<String, Bid> getOfferte(){
		if(offerte.isEmpty()){
			return null;
		}
		return offerte;
	}
	
	public String getBids(){
		return offerte.values().stream()
				.sorted(Comparator.comparing(Bid::getPrezzo).thenComparing(Bid::getFornitore))
				.map(Bid::toString)
				.collect(Collectors.joining(","));
	}
	public void setOffertaVincitrice(Bid bid){
		this.offertaVincitrice = bid; 
	}
	
	public int getPrezzoMassimo(){
		Bid massimo = offerte.values().stream()
				.max(Comparator.comparing(Bid::getPrezzo)).get();
		return massimo.getPrezzo();
	}

}
