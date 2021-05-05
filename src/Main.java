import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.*;
import HashTable.*;
import RB.*;
import BTree.*;

/**
 *
 * @author Kennedy Anderson
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        RBTree<String, Double> rb = new RBTree<String, Double>();
        BTree<String, Double> bin = new BTree<String, Double>();
        HashTentativaLinear<String, Double> TabelaLinear = new HashTentativaLinear<String, Double>(1000);
        HashListaEncadeada<String, Double> TabelaEncadeada = new HashListaEncadeada<String, Double>(1000);
        
        if(lerArquivoHashTentativaLinear(TabelaLinear, 1000)){
            String key = "njrrgxtshbbuzawcotwn";
            Object valor = TabelaLinear.get(key);

            System.out.println("Hash Tentativa Linear - chave: "+key+": valor: " + valor);
            
            System.out.println("Quantidade de chaves: "+TabelaLinear.retornaKeys()+"\n");
        }
        
        if(lerArquivoHashListaEncadeada(TabelaEncadeada, 1000)){
            String key = "knaftehjulupjslbdhsl";
            Object valor = TabelaEncadeada.get(key );

            System.out.println("Hash Lista Encadeada - chave: "+key +": valor: " + valor);
            
            System.out.println("Quantidade de chaves: "+TabelaEncadeada.retornaKeys()+"\n");
        }
        
        if(lerArquivoRBTree(rb)){
            System.out.println("Porcentagem de Vermelhos: "+rb.percentualRED()+"\n");
            /*
            if(rb.balanceada())
                System.out.println("RBTree está Balanceada");
            else
                System.out.println("RBTree não está balanceada");*/
        }
        System.out.println();
        System.out.println("-----------****************--------------");
       	System.out.println("------------ARVORE BINÁRIA----------------");
       	System.out.println("-----------****************---------------");
        if(lerArquivoBinaryTree(bin)){

            if(bin.balanceada())
                System.out.println("Arvore Binaria está Balanceada");
            else
                System.out.println("Arvore Binaria está Nao balanceada");
        }  
    }
    /*Leitura de Arquivos 
     * Hash Tentativa Linear
     * Hash Lista Encadeada
     * BTree
     * RBTree
     */
    public static boolean lerArquivoRBTree(RBTree<String, Double> rb) throws FileNotFoundException{
        int count = 0;
        
        File arquivo = new File("Tabela_RBTree.txt");
        
        if(arquivo.exists()){   
            if(arquivo.canRead()){
                try{
                    Scanner input = new Scanner(arquivo).useDelimiter("\\,|\\r\\n");
                    while (input.hasNext() && count<100) {
                        String chave = input.next();
                        double valor = Double.parseDouble(input.next());
                        rb.inserir(chave, valor);
                        count++;
                    }
                    
                    return true;
                }catch(IOException e){
                    System.out.println(e);
                }catch(NoSuchElementException e){
                    System.out.println("Arquivo não condiz com os dados a serem analisados!");
                }
            }
        }else{
            System.out.println("O arquivo não existe!");
        }
        return false;
    }
    
    public static boolean lerArquivoBinaryTree(BTree bin) throws FileNotFoundException{
        int count = 0;
        
        File arquivo = new File("Tabela_BTree.txt"); 
        
        if(arquivo.exists()){   
            if(arquivo.canRead()){
                try{
                    Scanner input = new Scanner(arquivo).useDelimiter("\\,|\\r\\n");
                    while (input.hasNext() && count<100) {
                        String chave = input.next();
                        double valor = Double.parseDouble(input.next());
                        bin.inserir(chave, valor);
                        count++;
                    }
                    
                    return true;
                }catch(IOException e){
                    System.out.println(e);
                }catch(NoSuchElementException e){
                    System.out.println("Arquivo não condiz com os dados a serem analisados!");
                }
            }
        }else{
            System.out.println("O arquivo não existe!");
        }
        return false;
    }
    
    public static boolean lerArquivoHashTentativaLinear(HashTentativaLinear tab, int tam) throws FileNotFoundException{
        int count = 0;
        
        File arquivo = new File("Tabela_String_Double.txt"); 
        
        if(arquivo.exists()){   
            if(arquivo.canRead()){
                try{
                    Scanner input = new Scanner(arquivo).useDelimiter("\\,|\\r\\n");
                    while (input.hasNext() && count<tam) {
                        String chave = input.next();
                        double valor = Double.parseDouble(input.next());
                        tab.putDoubleHash(chave, valor);
                        count++;
                    }
                    
                    return true;
                }catch(IOException e){
                    System.out.println(e);
                }catch(NoSuchElementException e){
                    System.out.println("Arquivo não condiz com os dados a serem analisados!");
                }
            }
        }else{
            System.out.println("O arquivo não existe!");
        }
        return false;
    }
    
    public static boolean lerArquivoHashListaEncadeada(HashListaEncadeada list, int tam) throws FileNotFoundException{
        int count = 0;
        
        File arquivo = new File("Tabela_String_Double.txt"); 
        
        if(arquivo.exists()){   
            if(arquivo.canRead()){
                try{
                    Scanner input = new Scanner(arquivo).useDelimiter("\\,|\\r\\n");
                    while (input.hasNext() && count<tam) {
                        String chave = input.next();
                        double valor = Double.parseDouble(input.next());
                        list.put(chave, valor);
                        count++;
                    }
                    
                    return true;
                }catch(IOException e){
                    System.out.println(e);
                }catch(NoSuchElementException e){
                    System.out.println("Arquivo não condiz com os dados a serem analisados!");
                }
            }
        }else{
            System.out.println("O arquivo não existe!");
        }
        return false;
    }
}