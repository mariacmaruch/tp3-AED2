package exercicio1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.*;

class ArquivoTextoLeitura {

	private BufferedReader entrada;
	
	public void abrirArquivo(String nomeArquivo) {	
		
		try {
			entrada = new BufferedReader(new FileReader(nomeArquivo));
		}
		catch (FileNotFoundException excecao) {
			System.out.println("Arquivo não encontrado");
		}
	}
	
	public void fecharArquivo() {
		
		try {
			entrada.close();
		}
		catch (IOException excecao) {
			System.out.println("Erro no fechamento do arquivo de leitura: " + excecao);	
		}
	}
	
	public String ler() {
		
		String textoEntrada;
		
		try {
			textoEntrada = entrada.readLine();
		}
		catch (EOFException excecao) { //Exceção de final de arquivo.
			return null;
		}
		catch (IOException excecao) {
			System.out.println("Erro de leitura: " + excecao);
			return null;
		}
			return textoEntrada;
	}
}

class ArquivoTextoEscrita {

    private BufferedWriter saida;

    public void abrirArquivo(String nomeArquivo) {

        try {
            saida = new BufferedWriter(new FileWriter(nomeArquivo));
        } catch (FileNotFoundException excecao) {
            System.out.println("Arquivo não encontrado");
        } catch (IOException excecao) {
            System.out.println("Erro na abertura do arquivo de escrita: " + excecao);
        }
    }

    public void fecharArquivo() {

        try {
            saida.close();
        } catch (IOException excecao) {
            System.out.println("Erro no fechamento do arquivo de escrita: " + excecao);
        }
    }

    public void escrever(String textoEntrada) {

        try {
            saida.write(textoEntrada);
            saida.newLine();
        } catch (IOException excecao) {
            System.out.println("Erro de entrada/saída " + excecao);
        }
    }
}

class PesquisaSequencial {

	public Musica buscarId(Musica[] listaMusicas, String id) {
		
		for (int i = 0; i < listaMusicas.length; i++)
            if (listaMusicas[i].getId().equals(id) && listaMusicas[i] != null )
					return listaMusicas[i];
		
		return null;
	}	
}


class Pilha{
	
	private Musica[] array;
	private int qnt;
	
	public Pilha() {
		this(1000);
	}
	
	public Pilha(int tamanho) {
		array = new Musica[tamanho];
		qnt=0; 
	}
		
	public void empilhar (Musica musica) {
		if (qnt < array.length) {
			array[qnt] = musica;
			qnt++;
		}
	}
	
	public Musica desempilhar() {
		if (qnt > 0) {
			return array[--qnt];
		}
		return null;
	}
	
	public void mostrar() {
		int aux = 0; 
		for (int i = (qnt-1); i >= 0; i--) {
			MyIO.print("[" + aux + "] ");
			array[aux].imprimir();
			aux++; 
		}
	}	
}

class Musica {

	private String id;
	private String name;             
	private String key;              
	private String[] artists;        
	private Date release_date;       
	private double acousticness;     
	private double danceability;     
	private double energy;           
	private int duration_ms;         
	private double instrumentalness; 
	private double valence;          
	private int popularity;          
	private double time;             
	private double liveness;         
	private double loudness;        
	private double speechiness;     
	private int year;                
	
	private int i;                   
	
	public Musica(String id, String name, String key, String[] artists, Date release_date, double acousticness,
			double danceability, double energy, int duration_ms, double instrumentalness, double valence,
			int popularity, double time, double liveness, double loudness, double speechiness, int year) {
		
		this.id = id;
		this.name = name;
		this.key = key;
		this.artists = artists;
		this.release_date = release_date;
		this.acousticness = acousticness;
		this.danceability = danceability;
		this.energy = energy;
		this.duration_ms = duration_ms;
		this.instrumentalness = instrumentalness;
		this.valence = valence;
		this.popularity = popularity;
		this.time = time;
		this.liveness = liveness;
		this.loudness = loudness;
		this.speechiness = speechiness;
		this.year = year;		
	}
	
	public Musica(String dados) {
		
		i = 0;
		this.valence = Double.parseDouble(getToken(dados));
		this.year = Integer.parseInt(getToken(dados));
		this.acousticness = Double.parseDouble(getToken(dados));
		
		this.artists = getArtistas(dados);
		
		this.danceability = Double.parseDouble(getToken(dados));
		this.duration_ms = Integer.parseInt(getToken(dados));
		this.energy = Double.parseDouble(getToken(dados));
		
		getToken(dados); // saltar campo da string original que não será utilizado
		
		this.id = getToken(dados);
		this.instrumentalness = Double.parseDouble(getToken(dados));
		this.key = getToken(dados);
		this.liveness = Double.parseDouble(getToken(dados));
		this.loudness = Double.parseDouble(getToken(dados));
		
		getToken(dados); // saltar campo da string original que não será utilizado
		
		this.name = getNome(dados);
		
		this.popularity = Integer.parseInt(getToken(dados));
		
		this.release_date = getData(getToken(dados));
		
		this.speechiness = Double.parseDouble(getToken(dados));
		this.time = Double.parseDouble(getToken(dados));
	}
	


	private String getNome(String dados) {
	
		String campo = "";
		boolean terminou = false;
		
		i+=2; // percorrendo a string até encontrar o início do texto.
	
		while (!terminou) {
			if ((dados.charAt(i) == ']') && (dados.charAt(i+1) == '\"') && (dados.charAt(i+2) == ',')) 
				terminou = true;
			else {
				campo += dados.charAt(i);
				i++;
			}
		}
			
		i+=3;
		
		return campo;
	}
	
	private Date getData(String dataString) {
		
		SimpleDateFormat formato;
		String[] dataCampos = dataString.split("-");
		Date data = new Date();
		
		if (dataCampos.length == 1) {
			dataString += "-01-01"; 
		}
		
		formato = new SimpleDateFormat("yyyy-MM-dd"); 
		try {
			data = formato.parse(dataString);
		} catch (ParseException excecao) {
			excecao.getMessage();
		}
		return data;
	}
	
	private String[] getArtistas (String dados) {
		
		String campo = "";
		String[] artistas;
		String nomeArtista = "";
		int inicio = 0;
		int fim;
		
		while (dados.charAt(i) != '[') // percorrendo a string até encontrar o início da lista de artistas.
			i++;
		
		i++;
		
		while (dados.charAt(i) != ']') { // percorrendo a string até encontrar o final da lista de artistas.
			campo += dados.charAt(i);
			i++;
		}
		
		while (dados.charAt(i) != ',') // percorrendo a string até encontrar o separador para o próximo campo.
			i++;
		
		i++;
		
		artistas = campo.split("\',"); // separador entre artistas: ',
	
		for (int j = 0; j < artistas.length; j++) {
			
			nomeArtista = "";
			inicio = 0;
			while ((artistas[j].charAt(inicio) == '\'') || (artistas[j].charAt(inicio) == ' ')) // percorrendo até encontrar o início do nome do artista.
				inicio++;
			
			for (fim = artistas[j].length()-1; (artistas[j].charAt(fim) == '\''); fim--);
			
			while (inicio <= fim) {
				nomeArtista += artistas[j].charAt(inicio);
				inicio++;
			}
			
			artistas[j] = nomeArtista;
		}
		
		return artistas;
	}
	
	private String getToken (String dados) {
	
		String campo = "";
		while ((i < dados.length()) && (dados.charAt(i) != ',')) {
			campo += dados.charAt(i);
			i++;
		}
		i++;
		return campo;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public String[] getArtists() {
		return artists;
	}
	
	public void setArtists(String[] artists) {
		this.artists = artists;
	}
	
	public Date getRelease_date() {
		return release_date;
	}
	
	public void setRelease_date(Date release_date) {
		this.release_date = release_date;
	}
	
	public double getAcousticness() {
		return acousticness;
	}
	
	public void setAcousticness(double acousticness) {
		this.acousticness = acousticness;
	}
	
	public double getDanceability() {
		return danceability;
	}
	
	public void setDanceability(double danceability) {
		this.danceability = danceability;
	}
	
	public double getEnergy() {
		return energy;
	}
	
	public void setEnergy(double energy) {
		this.energy = energy;
	}
	
	public int getDuration_ms() {
		return duration_ms;
	}
	
	public void setDuration_ms(int duration_ms) {
		this.duration_ms = duration_ms;
	}
	
	public double getInstrumentalness() {
		return instrumentalness;
	}
	
	public void setInstrumentalness(double instrumentalness) {
		this.instrumentalness = instrumentalness;
	}
	
	public double getValence() {
		return valence;
	}
	
	public void setValence(double valence) {
		this.valence = valence;
	}
	
	public int getPopularity() {
		return popularity;
	}
	
	public void setPopularity(int popularity) {
		this.popularity = popularity;
	}
	
	public double getTime() {
		return time;
	}
	
	public void setTime(double time) {
		this.time = time;
	}
	
	public double getLiveness() {
		return liveness;
	}
	
	public void setLiveness(double liveness) {
		this.liveness = liveness;
	}
	
	public double getLoudness() {
		return loudness;
	}
	
	public void setLoudness(double loudness) {
		this.loudness = loudness;
	}
	
	public double getSpeechiness() {
		return speechiness;
	}
	
	public void setSpeechiness(double speechiness) {
		this.speechiness = speechiness;
	}
	
	public int getYear() {
		return year;
	}
	
	public void setYear(int year) {
		this.year = year;
	}
	
	public Musica clone() {

        Musica copia;
        Date release_date;
        String[] artists = new String[this.artists.length];

        for (int i = 0; i < this.artists.length; i++) {
            artists[i] = this.artists[i];
        }

        release_date = (Date)this.release_date.clone();

        copia = new Musica(this.id, this.name, this.key, artists, release_date, this.acousticness, this.danceability, this.energy, this.duration_ms, this.instrumentalness, this.valence, this.popularity, this.time, this.liveness, this.loudness, this.speechiness, this.year);

        return copia;
    }

    public void ler() {

        int qtdeArtistas;
        String dataString;

        System.out.print("Informe o id da musica: ");
        this.id = MyIO.readString();

        System.out.print("Informe o nome da música: ");
        this.name = MyIO.readLine();

        System.out.print("Informe a chave da música: ");
        this.key = MyIO.readString();

        System.out.print("Informe a quantidade de artistas associados a essa música: ");
        qtdeArtistas = MyIO.readInt();

        this.artists = new String[qtdeArtistas];
        for (int i = 0; i < qtdeArtistas; i++) {
            System.out.println("Informe o nome do " + (i + 1) + ".o artista associado a essa música: ");
            this.artists[i] = MyIO.readLine();
        }

        System.out.print("Informe a data de lançamento da música, no formato yyyy-MM-dd: ");
        dataString = MyIO.readString();
        this.release_date = getData(dataString);

        System.out.print("Informe a acústica da música: ");
        this.acousticness = MyIO.readDouble();

        System.out.print("Informe a dança da música: ");
        this.danceability = MyIO.readDouble();

        System.out.print("Informe a energia da música: ");
        this.energy = MyIO.readDouble();

        System.out.print("Informe a duração da música, em milissegundos: ");
        this.duration_ms = MyIO.readInt();

        System.out.print("Informe a instrumentabilidade da música: ");
        this.instrumentalness = MyIO.readDouble();

        System.out.print("Informe a valência da música: ");
        this.valence = MyIO.readDouble();

        System.out.print("Informe a popularidade da música: ");
        this.popularity = MyIO.readInt();

        System.out.print("Informe o tempo da música: ");
        this.time = MyIO.readDouble();

        System.out.print("Informe a vivacidade da música: ");
        this.liveness = MyIO.readDouble();

        System.out.print("Informe o volume da música: ");
        this.loudness = MyIO.readDouble();

        System.out.print("Informe o discurso da música: ");
        this.speechiness = MyIO.readDouble();

        System.out.print("Informe o ano da música: ");
        this.year = MyIO.readInt();
    }

	public void imprimir() {

		int j; 
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

		System.out.print(this.id + " ## [");
		for (j = 0; j < (this.artists.length - 1); j++) {
			System.out.print(this.artists[j] + ", ");
		}
		System.out.print(this.artists[j] + "] ## " + this.name + " ## ");
		System.out.print(formato.format(this.release_date));
		System.out.println(" ## " + this.acousticness + " ## " + this.danceability + " ## " + this.instrumentalness
				+ " ## " + this.liveness + " ## " + this.loudness + " ## " + this.speechiness + " ## " + this.energy
				+ " ## " + this.duration_ms);
	}
}


public class PilhaVetorJava {

    public static int contarTotalMusicas() {

        int numMusicas = 0;
        ArquivoTextoLeitura arquivoMusicas = new ArquivoTextoLeitura();

        arquivoMusicas.abrirArquivo("/tmp/dataAEDs.csv");

        while (arquivoMusicas.ler() != null)
            numMusicas++;

        arquivoMusicas.fecharArquivo();

        return numMusicas;
    }

    public static void lerMusicas(Musica[] listaMusicas) {

        ArquivoTextoLeitura arquivoMusicas = new ArquivoTextoLeitura();

        arquivoMusicas.abrirArquivo("/tmp/dataAEDs.csv");

        int i = 0;
        String dadosMusica;

        dadosMusica = arquivoMusicas.ler();

        while ((dadosMusica = arquivoMusicas.ler()) != null) {

            listaMusicas[i] = new Musica(dadosMusica);
            i++;
        }

        arquivoMusicas.fecharArquivo();
    }

	public static void main(String[] args) {
	
		Pilha pilha = new Pilha();
		int numTotalMusicas = contarTotalMusicas();
        Musica[] listaMusicas = new Musica[numTotalMusicas];
        Musica pesquisado;
        PesquisaSequencial pesquisa = new PesquisaSequencial();

		String id = MyIO.readLine();
		lerMusicas(listaMusicas);

		while (!(id.equals("FIM"))) {
			pesquisado = pesquisa.buscarId(listaMusicas, id);
			pilha.empilhar(pesquisado);
			id = MyIO.readLine();
		}

		int n = MyIO.readInt();
		String entrada2; 

		for (int i = 0; i < n; i++) {
			entrada2 = MyIO.readLine();
			String[] arrayAux = entrada2.split(" ");
			if (arrayAux[0].equals("I")) {
				pesquisado = pesquisa.buscarId(listaMusicas, arrayAux[1]);
				pilha.empilhar(pesquisado);
			} else if (arrayAux[0].equals("R")) {
                pesquisado = pilha.desempilhar();
				MyIO.println("(R) " + pesquisado.getName());
			}
		}
		pilha.mostrar();
	}
}
