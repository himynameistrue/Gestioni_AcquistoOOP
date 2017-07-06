package groups;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Client {
	private String nome;
	private Map<String, Group> gruppi = new TreeMap<>();
	
	public Client(String nome) {
		this.nome = nome;
	}
	public String getName() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Map<String, Group> getGruppi() {
		return gruppi;
	}
	
	public List<String> getGruppiNames(){
		gruppi.values().stream()
		.map(x -> x.getName())
		.forEach(System.out::println);
		
		return gruppi.values().stream()
				.map(x -> x.getName())
				.collect(Collectors.toList());
//		return gruppi.keySet().stream()
//				.collect(Collectors.toList());
	}
	
	public void setGruppi(Map<String, Group> gruppi) {
		this.gruppi = gruppi;
	}
	public void addGruppo(String name, Group g){
		//if(!gruppi.containsKey(name)){
			gruppi.put(name, g);
		//}
	}

	
}
