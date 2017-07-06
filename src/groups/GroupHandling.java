package groups;
import static java.util.Comparator.reverseOrder;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;



public class GroupHandling {
	Map<String, Product> prodotti = new HashMap<>();
	Map<String, Fornitore> fornitori = new HashMap<>();
	Map<String , Group> gruppi = new HashMap<>();
	Map<String, Client> clienti = new HashMap<>();
	
	
//R1	
	public void addProduct (String productTypeName, String... supplierNames) throws GroupHandlingException {
		if(prodotti.containsKey(productTypeName)){
			throw new GroupHandlingException("prodotto gi� presente");
		}
		List<Fornitore> fornitoriProdotto = new ArrayList<>();
		for(String s : supplierNames){
			Fornitore f = new Fornitore(s);
			if(!fornitoriProdotto.contains(f)){
				fornitoriProdotto.add(f);
			}
			if(!fornitori.containsKey(s)){
				fornitori.put(s, f);
			}
		}
		Product p = new Product(productTypeName, fornitoriProdotto);
		for(String s : supplierNames){
			fornitori.get(s).addProdotto(productTypeName, p);
		}
		prodotti.put(productTypeName, p);
	}
	
	public List<String> getProductTypes (String supplierName) {
		return fornitori.get(supplierName).getProdottiNames();
	}
	
//R2	
	public void addGroup (String name, String productName, String... customerNames) throws GroupHandlingException {
		if(!prodotti.containsKey(productName)){
			throw new GroupHandlingException("prodotto non trovato");
		}
		List<String> partecipanti = new ArrayList<>();
		for(String s : customerNames){
		//	if(!partecipanti.contains(s)){
				partecipanti.add(s);
		//	}
			Client c = new Client(s);	
			if(clienti.get(s) == null){
				clienti.put(s, c);
			}
		}
		Group gruppo = new Group(name, prodotti.get(productName), partecipanti);
		if(gruppi.containsKey(name)){
			throw new GroupHandlingException("gruppo gi� presente");
		}
		gruppi.put(name, gruppo);
		
		for(String s : partecipanti){
			clienti.get(s).addGruppo(name, gruppo);
			prodotti.get(productName).addCliente(s);
		}
	}
	
	
	public List<String> getGroups (String customerName) {
		return clienti.get(customerName).getGruppi().keySet().stream()
        		.collect(Collectors.toList());
	}

//R3
	public void setSuppliers (String groupName, String... supplierNames) throws GroupHandlingException {
		for(String s : supplierNames){
			if(!fornitori.containsKey(s)){
				throw new GroupHandlingException("fornitore indefinito");
			}
			String prodottoRichiesto = gruppi.get(groupName).getProdotto().getName();
			if(!fornitori.get(s).getProdottiNames().contains(prodottoRichiesto)){
				throw new GroupHandlingException("fornitore non fornisce prodotto");
			}
			gruppi.get(groupName).addFornitore(s);
		}
	}
	
	public void addBid (String groupName, String supplierName, int price) throws GroupHandlingException {
		if(!gruppi.get(groupName).getFornitori().contains(supplierName)){
			throw new GroupHandlingException("fornitore non appartiene al gruppo");
		}
		Bid b = new Bid(supplierName, price);
		//if(!gruppi.get(groupName).getOfferte().containsKey(supplierName)){
			gruppi.get(groupName).addBid(supplierName, b);
			fornitori.get(supplierName).addOfferta(b);
		
	}
	
	public String getBids (String groupName) {
        return gruppi.get(groupName).getBids();
	}
	
	
//R4	
	public void vote (String groupName, String customerName, String supplierName) throws GroupHandlingException {
		if(!gruppi.get(groupName).getClienti().contains(customerName)){
			throw new GroupHandlingException("cliente non in gruppo");
		}
		
		if(!gruppi.get(groupName).getFornitoriOfferte().contains(supplierName)){
			throw new GroupHandlingException("fornitore non ha fatto offerta");
		}
		Bid offertaVincitrice = gruppi.get(groupName).getOfferte().get(supplierName);
		gruppi.get(groupName).setOffertaVincitrice(offertaVincitrice);
		gruppi.get(groupName).getOfferte().get(supplierName).incVoti();
	}
	
	public String  getVotes (String groupName) {
        return gruppi.get(groupName).getOfferte().values().stream()
        		.sorted(Comparator.comparing(Bid::getFornitore))
        		.filter(x -> x.getVoti()>0)
        		.map(Bid::fornitoriVoti)
        		.collect(Collectors.joining(","));
	}
	
	public String getWinningBid (String groupName) {
		if(gruppi.get(groupName).getOfferte()== null){
			return null;
		}
		Optional<String> vincitore = gruppi.get(groupName).getOfferte().values().stream()
				.filter(x -> x.getVoti()>=1)
        		.max(Comparator.comparing(Bid::getVoti).thenComparing(Bid::getPrezzo).reversed())
        		.map(Bid::fornitoriVoti);

		if(vincitore.isPresent()){
			return vincitore.orElse("");
		}
		return "";
	}
	
//R5
	public SortedMap<String, Integer> maxPricePerProductType() { //serve toMap
        Map<String, Integer> tmp = gruppi.values().stream() // stream di gruppi
        		.filter(x -> x.getOfferte() != null)
        		.sorted(Comparator.comparing(x -> x.getProdotto().getName()))
        		.collect(Collectors.toMap(x -> x.getProdotto().getName(), y -> y.getPrezzoMassimo()));
        return new TreeMap<String, Integer>(tmp);
	}
	
	public SortedMap<Integer, List<String>> suppliersPerNumberOfBids() {
		Map<Integer, List<String>>tmp = 
				fornitori.values().stream()
				.filter(x -> x.getNumOfferte()>0)
				.sorted(Comparator.comparing(Fornitore::getName))
				.collect(groupingBy(Fornitore::getNumOfferte,
						Collectors.collectingAndThen(toList(), s -> s.stream().map(Fornitore::getName).collect(toList()) 
													)
									)
						);
	
		TreeMap<Integer, List<String>> fornitoriPerOfferte = new TreeMap<Integer, List<String>>(Comparator.reverseOrder());
		fornitoriPerOfferte.putAll(tmp);
		return fornitoriPerOfferte;
		
		
//		return fornitori.values().stream()
//		.filter(x -> x.getNumOfferte()>0)
//		.sorted(Comparator.comparing(Fornitore::getName))
//		.collect(groupingBy(Fornitore::getNumOfferte, TreeMap::new,
//				Collectors.collectingAndThen(toList(), s -> s.stream().map(Fornitore::getName).collect(toList()) 
//											)
//							)
//				).descendingMap();
	}
	// ATTENZIONE: NEI TEST VENGONO CONTATI ANCHE I DUPLICATI! 
	public SortedMap<String, Long> numberOfCustomersPerProductType() {
		 Map<String, Long> tmp = prodotti.values().stream()
				 .filter(x -> x.getClienti()!= null)
				 .sorted(Comparator.comparing(Product::getName))
				 .collect(Collectors.toMap(x -> x.getName(), x -> new Long(x.getClienti().size())));
        return new TreeMap<String, Long>(tmp);
	}
	
}
