package HashTable;
import javax.swing.*;
import java.util.*;

public class HashListaEncadeada<Key, Value>{
    private int N; // numero de pares de chaves na tabela
    private int M = 16; // Tamanho da tabela hash com tratamento linear
    private ListaEncadeada<Key, Value>[] lista;

    public HashListaEncadeada(int capacidade) {
        this.M = capacidade;
       lista = (ListaEncadeada<Key,Value>[]) new ListaEncadeada[M] ;
       for (int i = 0; i < M; i++)
           lista[i] = new ListaEncadeada<Key, Value>();
    }

    public HashListaEncadeada() {
        lista = (ListaEncadeada<Key,Value>[]) new ListaEncadeada[M] ;
        for (int i = 0; i < M; i++)
            lista[i] = new ListaEncadeada<Key, Value>();
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
     * @param
     */

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

    public Value get(Key key){
        int i = hash(key);
        return lista[i].get(key);
    }

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
       lista[i].put(key, val);
    }



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

    public Iterable<Key> keys(){
        Queue<Key> queue = new LinkedList<Key>();
        for (int i=0; i<M;i++){
            for(Key key : lista[i].keys())
                ((LinkedList<Key>) queue).add(key);
        }
        return queue;
    }


}
