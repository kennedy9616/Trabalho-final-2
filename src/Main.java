import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import HashTable.*;
import RB.*;
import BTree.*;

/**
 *
 * @author Kennedy Anderson
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        RBTree rb = new RBTree();
        BTree bin = new BTree();
        HashTentativaLinear tab = new HashTentativaLinear(1000);
        HashListaEncadeada tab2 = new HashListaEncadeada(1000);
        
        if(lerArquivoHashTentativaLinear(tab, 1000)){
            String aux = "njrrgxtshbbuzawcotwn";
            Object valor = tab.get(aux);

            System.out.println("Hash Tentativa Linear - Valor associado a "+aux+": " + valor);
            
            System.out.println("Quantidade: "+tab.retornaKeys()+"\n");
        }
        
        if(lerArquivoHashListaEncadeada(tab2, 1000)){
            String aux = "knaftehjulupjslbdhsl";
            Object valor = tab2.get(aux);

            System.out.println("Hash Lista Encadeada - Valor associado a "+aux+": " + valor);
            
            System.out.println("Quantidade: "+tab2.retornaKeys()+"\n");
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
    
    public static boolean lerArquivoRBTree(RBTree rb) throws FileNotFoundException{
        int count = 0;
        
        File arquivo = new File("Tabela_RBTree.txt"); //selecione o arquivo string_double de tamanho qualquer
        
        if(arquivo.exists()){   
            if(arquivo.canRead()){
                try{
                    Scanner input = new Scanner(arquivo).useDelimiter("\\,|\\r\\n");
                    while (input.hasNext() && count<100) {//lendo atributos do arquivo
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
        
        File arquivo = new File("Tabela_BTree.txt"); //selecione o arquivo string_double de tamanho qualquer
        
        if(arquivo.exists()){   
            if(arquivo.canRead()){
                try{
                    Scanner input = new Scanner(arquivo).useDelimiter("\\,|\\r\\n");
                    while (input.hasNext() && count<100) {//lendo atributos do arquivo
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
        
        File arquivo = new File("Tabela_String_Double.txt"); //selecione o arquivo string_double de tamanho qualquer
        
        if(arquivo.exists()){   
            if(arquivo.canRead()){
                try{
                    Scanner input = new Scanner(arquivo).useDelimiter("\\,|\\r\\n");
                    while (input.hasNext() && count<tam) {//lendo atributos do arquivo
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
        
        File arquivo = new File("Tabela_String_Double.txt"); //selecione o arquivo string_double de tamanho qualquer
        
        if(arquivo.exists()){   
            if(arquivo.canRead()){
                try{
                    Scanner input = new Scanner(arquivo).useDelimiter("\\,|\\r\\n");
                    while (input.hasNext() && count<tam) {//lendo atributos do arquivo
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