package groups;

public class Bid {
	private String fornitore;
	private int prezzo;
	private int votiOfferta = 0;
	
	public Bid(String fornitore, int prezzo) {
		this.fornitore = fornitore;
		this.prezzo = prezzo;
	}
	
	public String getFornitore() {
		return fornitore;
	}
	public void setFornitore(String fornitore) {
		this.fornitore = fornitore;
	}
	public int getPrezzo() {
		return prezzo;
	}
	public void setPrezzo(int prezzo) {
		this.prezzo = prezzo;
	}
	
	public void incVoti(){
		votiOfferta++;
	}
	
	public int getVoti(){
		return votiOfferta;
	}
	
	public String fornitoriVoti(){
		return fornitore + ":" + votiOfferta;
	}
	@Override
	public String toString(){
		return fornitore + ":" +prezzo;
	}


}
