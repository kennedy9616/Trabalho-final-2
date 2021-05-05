package HashTable;

public class HashTentativaLinear<Key, Value>{
	private int N; // numero de pares de chaves na tabela
	private int M = 16; // Tamanho da tabela hash com tratamento linear
	private Key[] keys; 
	private Value[] vals;
	private boolean[] stats; // status de vaga

	//stats
	// true = ALOCADO
	// false = REMOVIDO

	//Cria um vetor de chaves, valores e estados dessas chaves, podendo elas estar ocupadas ou livres;
	//Utiliza o tamanho padrao 16
	public HashTentativaLinear() {
		keys = (Key[]) new Object[M];
		vals = (Value[]) new Object[M];
		stats = new boolean[M];
	}

	//O mesmo de acima, porem utiliza um tamanho variavel passado por parametro
	public HashTentativaLinear(int cap) {
		keys = (Key[]) new Object[cap];
		vals = (Value[]) new Object[cap];
		stats = new boolean[cap];
		M = cap;
	}
	
	/**
	 * Calcula o Hash
	 * @param key
	 * @return
	 */

/*
 * QUESTÃO 1 A, CRIANDO O HASH AUXILIAR AÍ UTILIZAMOS EM putDoubleHash
 * fazendo (i+k)%M
 */
	//Funcao que faz o hash Auxiliar em caso de colisao
	private int hashAux(Key key){
		return 1 + (key.hashCode() & 0x7fffffff) % M;
	}

	//Retorna o hash entre 0 e M-1.
	private int hash(Key key)
	{
		return (key.hashCode() & 0x7fffffff) % M; 
	}
	
	/**
	 * Redimensiona a tabela de acordo com a quantidade de chaves.
	 * @param cap
	 */
	//Redimenciona a tabela para um novo tamanho, recolocando os elementos na nova tabela
	private void resize(int cap) {
		
		HashTentativaLinear<Key, Value> t;
		boolean st[] = new boolean[cap];
		t = new HashTentativaLinear<Key, Value> (cap);
		
		for (int i = 0; i < keys.length; i++) {
            if (keys[i] != null) {
                t.put(keys[i], vals[i]);
                st[i] = stats[i];
            }
		}
		stats = st;
		keys = t.keys;
		vals = t.vals;

		M = t.M;
		
	}

	 //Verifica se uma chave esta contida na tabela.
	 public boolean contains(Key key) {
	        if (key == null) {
	            throw new IllegalArgumentException("Argument to contains() cannot be null");
	        }

	        return get(key) != null;
	 }
	
	/**
	 * Insere um novo objeto no Hash 
	 * @param key
	 * @param val
	 */
	public void putDoubleHash(Key key, Value val) {
		int i = hash(key);
		int k = hashAux(key);
    	if (N >= M/2)
			resize(2*M); // dobro tamanho da tabela
		//i a hash inicial
		//Em caso de colisÃ£o a proxima posicao testa  a (i + k) % M onde k Ã© o valor da hash auxiliar

        for (; keys[i] != null; i = (i + k) % M) {
            if (keys[i].equals(key)) {            // Caso a chave ja¡ esteja na tabela o valor eh sobrescrito
                vals[i] = val;
                return;
            }
        }
        keys[i] = key;
		vals[i] = val;
		stats[i] = true;
		//Definimos o estado da chave para true, que significa alocado.
		N++;
	}


	//Insere um elemento na tabela utilizando uma hash simples e tentativa linear. Se a posicao que a hash cair estiver ocupada
	//Passa para posicao i+1 %M, utilizando o resto para o valor nao sair do tamanho da tabela;
	//Se a chave  ja existir na tabela o valor e sobrescrito.

	public void put(Key key, Value val) {
		int i;
		if (N >= M/2) 
			resize(2*M); // dobro o tamanho da tabela

		for (i = hash(key); keys[i] != null; i = (1 + i) % M)
			if (keys[i].equals(key)) {
				vals[i] = val;
				return; 
				}
		//Achou uma posicao livre

		keys[i] = key;
		vals[i] = val;
		stats[i] = true;
		//Definimos o estado da chave para true, que significa alocado.
		N++;
	}
	
	/**
	 * Remove um objeto do Hash
	 * @param key
	 */
/*
 * questao 1 c
 */
	//Executa a remocao sem deletar o elemento da memoria
	//Caso o elemento esteja contido na tabela, calculcamos a sua posicao nos pares de chaves e alteramos seus estado para false, ou seja Livre.
	public void deleteNoRemove(Key key){
		if (key == null)
			throw new IllegalArgumentException("Argument to delete() cannot be null");

		if (!contains(key))
			return;

		int i = hash(key);
		int k = hashAux(key);

		while (!key.equals(keys[i]))
			i = (i + k) % M;

		stats[i] = false;

		i = (i + k) % M;

		while (keys[i] != null){
			Key keyToRedo = keys[i];
			Value valToRedo = vals[i];
			stats[i] = false;
			N--;
			put(keyToRedo, valToRedo);
			i = (i + 1) % M;
		}

		//decrementamos o contador de pares.
		N--;
		//Verificamos se existe necessidade de redimensionameto apos a remocao da chave.
		if (N > 0 && N == M/8)
			resize(M/2);
	}

	//Faz a remocao fisica do elemento atribuindo null para chave e valor
	public void delete(Key key)
	{
		if (key == null) 
			throw new IllegalArgumentException("Argument to delete() cannot be null");
		
		if (!contains(key))
			return;
			
		int i = hash(key);
		while (!key.equals(keys[i]))
			i = (i + 1) % M;
		
		keys[i] = null;
		vals[i] = null;
		i = (i + 1) % M;
		
		while (keys[i] != null){
			Key keyToRedo = keys[i];
			Value valToRedo = vals[i];
			keys[i] = null;
			vals[i] = null;
			N--;
			put(keyToRedo, valToRedo);
			i = (i + 1) % M;
		}
		N--;
		if (N > 0 && N == M/8) 
			resize(M/2);
	}
	
	/**
	 * Busca um objeto no Hash
	 * @param key
	 * @return
	 */
/*
 * QUESTÃO 1 E 
 */
	public Value getHashDuplo(Key key) {
		int i = hash(key);
		int k = hash(key);
		for (; keys[i] != null; i = (i + k) % M)
			if (keys[i].equals(key))
				return vals[i];
		return null;
	}
	
	//Percorre a tabela de chaves utilizando tentativa linaar e retorna o valor correspondente a uma chave
	public Value get(Key key) {
		for (int i = hash(key); keys[i] != null; i = (i + 1) % M)
			if (keys[i].equals(key)) {
                return vals[i];
            }
		return null;
	}
	/*
	 * QUESTÃO 1 D
	 */
    public int retornaKeys(){
        return this.N;
    }
}
