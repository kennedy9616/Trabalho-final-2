package HashTable;

import java.util.LinkedList;

public class HashTentativaLinear<Key, Value>{
	private int N; // numero de pares de chaves na tabela
	private int M = 16; // Tamanho da tabela hash com tratamento linear
	private Key[] keys; // the keys
	private Value[] vals; // the values
	private boolean[] stats;

	//stats
	// true = ALOCADO
	// false = REMOVIDO

	//Cria um vetor de chaves, valores e estados dessas chaves, podendo elas estar ocupadas ou livres;
	//Utiliza o tamanho padrão 16
	public HashTentativaLinear() {
		keys = (Key[]) new Object[M];
		vals = (Value[]) new Object[M];
		stats = new boolean[M];
	}

	//O mesmo de acima, porém utiliza um tamanho variavel passado por parametro
	public HashTentativaLinear(int cap) {
		keys = (Key[]) new Object[cap];
		vals = (Value[]) new Object[cap];
		stats = new boolean[cap];
		M = cap;
	}
	// contar a quantidade de chaves do hash
	public int contaKey(Key[] key) {
		int numKey = 0;
		LinkedList<Key> cont = new LinkedList<Key>();
		
		for(int i=0; i<key.length; i++) {
			cont.add((Key) key);
		}
		for(int i = 0; i < cont.size();i++){
			numKey++;
		}
		return numKey;
	}
	/**
	 * Calcula o Hash
	 * @param key
	 * @return
	 */


	//Função que faz o hash Auxiliar em caso de colisão
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
			resize(2*M); // double M
		//i é a hash inicial
		//Em caso de colisão a proxima posição testa é a (i + k) % M onde k é o valor da hash auxiliar

        for (; keys[i] != null; i = (i + k) % M) {
            if (keys[i].equals(key)) {            // Caso a chave já esteja na tabela o valor é sobrescrito
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


	//Insere um elemtno na tabela utilizando uma hash simples e tentativa linear. Se a posição que a hash cair estiver ocupada
	//Passa para posição i+1 %M, utilizando o resto para o valor não sair do tamanho da tabela;
	//Se a chave  já existir na tabela o valor é sobrescrito.

	public void put(Key key, Value val) {
		int i;
		if (N >= M/2) 
			resize(2*M); // double M 

		for (i = hash(key); keys[i] != null; i = (1 + i) % M)
			if (keys[i].equals(key)) {
				vals[i] = val;
				return; 
				}
		//Achou uma posição livre

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

	//Executa a remoção sem deletar o elemento da memória
	//Caso o elemento esteja contido na tabela, calculcamos a sua posição nos pares de chaves e alteramos seus estado para false, ou seja Livre.
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
		//Verificamos se existe necessidade de redimensionameto após a remoção da chave.
		if (N > 0 && N == M/8)
			resize(M/2);
	}

	//Faz a remoção fisica do elemento atribuindo null para chave e valor
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
}
