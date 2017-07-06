package it.polito.po;

public class Quiz {
	final static public String[] questions = {
	"Qual'e' la differenza tra verifica e convalida?",
	"Quali sono i limiti del modello di sviluppo waterfall?",
	"Che metodo usa SVN per risolvere conflitti?"
	};
	final static public String[][] options = {
	{
		"Una riguarda il test, l'altra le ispezioni",
		"Una riguarda il test, l'altra le ispezioni",
		"Una determina la qualita' l'altra l'utilita' del sistema",
		"Sono sinonimi, non c'e' differenza",
		"Una riguarda il test, l'altra no"	},
	{
		"Rigida sequenzialita'",
		"Scarsa formalizzazione",
		"Ridotta stabilita' dei requisiti",
		"Limitata documentazione",
		"Scarsa sequenzialita'"	},
	{
		"Modify-Commit-Unlock",
		"Copy-Modify-Merge",
		"Check-in/Check-out",
		"Check-out/Check-in",
		"Lock-Unlock-Modify"	}
	};
	
	/**
	 * Return the index of the right answer(s) for the given question 
	 */
	public static int[] answer(int question){
		// TODO: answer the question
		
		switch(question){
			case 0: return new int[]{2}; // replace with your answers
			case 1: return new int[]{3}; // replace with your answers
			case 2: return new int[]{1}; // replace with your answers
		}
		return null; // means: "No answer"
	}

	/**
	 * When executed will show the answers you selected
	 */
	public static void main(String[] args){
		for(int q=0; q<questions.length; ++q){
			System.out.println("Question: " + questions[q]);
			int[] a = answer(q);
			if(a==null || a.length==0){
				System.out.println("<undefined>");
				continue;
			}
			System.out.println("Answer" + (a.length>1?"s":"") + ":" );
			for(int i=0; i<a.length; ++i){
				System.out.println(a[i] + " - " + options[q][a[i]]);
			}
		}
	}
}

