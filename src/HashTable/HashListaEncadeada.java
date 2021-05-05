package HashTable;
import java.util.*;

public class HashListaEncadeada<Key, Value>{
    private int N; // numero de pares de chaves na tabela
    private int M = 16; // Tamanho da tabela hash com tratamento linear
    private ListaEncadeada<Key, Value>[] lista; // Lista encadeada de pares Chave, Valor

    //Cria uma lista encadeada de tamanho variavel, com o tamanho passado por parametro
    public HashListaEncadeada(int capacidade) {
        this.M = capacidade;
       lista = (ListaEncadeada<Key,Value>[]) new ListaEncadeada[M] ;
       for (int i = 0; i < M; i++)
           lista[i] = new ListaEncadeada<Key, Value>();
    }

    //Cria uma lista encadeada de tamanho inicial 16
    public HashListaEncadeada() {
        lista = (ListaEncadeada<Key,Value>[]) new ListaEncadeada[M] ;
        for (int i = 0; i < M; i++)
            lista[i] = new ListaEncadeada<Key, Value>();
    }

    //Retorna o hash entre 0 e M-1.
    private int hash(Key key)
    {
        return (key.hashCode() & 0x7fffffff) % M;
    }

    /**
     * Redimensiona a tabela de acordo com a quantidade de chaves.
     * @param
     */
    //Redimenciona a tabela para um novo tamanho, recolocando os elementos na nova tabela
    private void resize(int nos) {
        HashListaEncadeada<Key, Value> temp;
        temp = new HashListaEncadeada<Key, Value>(nos);
        for(int i = 0;i < M; i++){
            for(Key key : lista[i].keys()){
                temp.put(key,lista[i].get(key));
            }
        }
        this.M = temp.M;
        this.N = temp.N;
        this.lista = temp.lista;
    }

    int getSize(){
        return N;
    }

    public boolean isEmpty(){
        return getSize() == 0;
    }

    public boolean contains(Key key){
        return get(key) != null;
    }
/*
 * QUESTÃO 1 E
 */
    public Value get(Key key){
        int i = hash(key);
        return lista[i].get(key);
    }

/*
 * QUESTÃO 1 B TRATANDO COLISÕES CRIANDO NA POSIÇÃO QUANDO HOUVER COLISÃO
 */
    public void put(Key key, Value val) {
       if (val == null){
           delete(key);
           return;
       }

       if(N>= 10*M){
           resize(2*M);
       }

       int i = hash(key);
       if (!lista[i].contains(key)){
           N++;
       }
       /*Chama o metodo put da lista encadeada que ira¡ inserir o chave valor em um no da lista encadeada, assim quando houver colisao
        * Sera¡ criado mais um no nessa posicao.
        * Pois cada posicao da nossa tabela corresponde a uma lista Encadeada.
        *
        */
       lista[i].put(key, val);
    }

/*
 * QUESTAO 1 LETRA C
 */
    //Mesmo procedimento da insercao
    //Porem, aqui utilizamos o metodo delete da lista Encadeda para deletar um no dessa lista.
    public void delete(Key key)
    {
        if (key == null)
            throw new IllegalArgumentException("Argument to delete() cannot be null");

        int i = hash(key);
        if(lista[i].contains(key)){
            N--;
            lista[i].delete(key);
        }

        if(M > 16 && N < 2*M){
            resize(M/2);
        }
    }

    //Iteramos pela lista encadeada e retornamos uma LinkedList de chaves da nossa Lista Encadeada
    public Iterable<Key> keys(){
        Queue<Key> queue = new LinkedList<Key>();
        for (int i=0; i<M;i++){
            for(Key key : lista[i].keys())
                ((LinkedList<Key>) queue).add(key);
        }
        return queue;
    }
    //Iteramos pela lista encadeada e retornamos uma LinkedList de chaves da nossa Lista Encadeada
    /*
     * QUESTÃO 1 D 
     */
    public int retornaKeys(){
        Queue<Key> queue = new LinkedList<Key>();
        for (int i=0; i<M;i++){
            for(Key key : lista[i].keys())
                ((LinkedList<Key>) queue).add(key);
        }
        return queue.size();
    }

}
