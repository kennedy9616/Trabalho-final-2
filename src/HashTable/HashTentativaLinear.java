package HashTable;

public class HashTentativaLinear<Key, Value>{
	private int N; // numero de pares de chaves na tabela
	private int M = 16; // Tamanho da tabela hash com tratamento linear
	private Key[] keys; // the keys
	private Value[] vals; // the values
	private boolean[] stats;

	//stats
	// true = ALOCADO
	// false = REMOVIDO

	public HashTentativaLinear() {
		keys = (Key[]) new Object[M];
		vals = (Value[]) new Object[M];
		stats = new boolean[M];
	}
	
	public HashTentativaLinear(int cap) {
		keys = (Key[]) new Object[cap];
		vals = (Value[]) new Object[cap];
		M = cap;
	}
	
	/**
	 * Calcula o Hash
	 * @param key
	 * @return
	 */


	//Função que faz o hash Auxiliar em caso de colisão
	private int hashAux(Key key){
		return 1 + ((key.hashCode() & 0x7fffffff) % M-1);
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
	private void resize(int cap) {
		
		HashTentativaLinear<Key, Value> t;
		t = new HashTentativaLinear<Key, Value> (cap);
		
		for (int i = 0; i < keys.length; i++)
			if (keys[i] != null)
				t.put(keys[i], vals[i]);
		keys = t.keys;
		vals = t.vals;
		M = t.M;
		
	}
	
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
		int i;
		int k;
		if (N >= M/2)
			resize(2*M); // double M
		//i é a hash inicial
		//Em caso de colisão a proxima posição testa é a (i + k) % M onde k é o valor da hash auxiliar
		for (i = hash(key),k= hashAux(key); keys[i] != null; i = (i + k) % M)
			if (keys[i].equals(key)) {			// Caso o elemento já esteja na tabela ele não faz nada
				return;
			}
		//Achou uma posição livre
		keys[i] = key;
		vals[i] = val;
		stats[i] = true;
		N++;
	}


	public void put(Key key, Value val) {
		int i;
		int k = hashAux(key);
		if (N >= M/2) 
			resize(2*M); // double M 

		for (i = hash(key); keys[i] != null; i = (1 + k) % M)
			if (keys[i].equals(key)) { 
				vals[i] = val;
				return; 
				}
		//Achou uma posição livre
		keys[i] = key;
		vals[i] = val;
		stats[i] = true;
		N++;
	}
	
	/**
	 * Remove um objeto do Hash
	 * @param key
	 */

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
		N--;
		if (N > 0 && N == M/8)
			resize(M/2);
	}
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
	public Value get(Key key) {
		for (int i = hash(key); keys[i] != null; i = (i + 1) % M)
			if (keys[i].equals(key) && stats[i])
				return vals[i];
		return null;
	}
}
