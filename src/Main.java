import AVL.AVLTree;
import HashTable.*;
import RB.*;
import BTree.*;

public class Main {

    public static void main(String[] args) {
        
       HashTentativaLinear TabelaLinear = new HashTentativaLinear(1000);
       BTree bt = new BTree();
       RBTree rb = new RBTree();

        String[] arr = Leitor.leitor1(1000);
        for(int i = 0; i<1000;i++){
            TabelaLinear.putDoubleHash(arr[i],i);
        }
        for(int i = 0; i<1000;i++) {
        	rb.inserir(arr[i], i);
        }
        System.out.println("Numero de chaves: "+ TabelaLinear.contaKey(arr));
      
        Object valorHash1 = TabelaLinear.get(arr[10]);

        System.out.println("Busca com 1000 elementos: HashDuplo");
        System.out.println("Valor: " + valorHash1);

        Object valorHash2 = TabelaLinear.get(arr[10]);

        System.out.println("Busca com 20000 elementos: HashDuplo");
        System.out.println("Valor: " + valorHash2);
    	
    
        bt.inserir(7, 1);
        bt.inserir(7, 2);
        bt.inserir(22, 3);
        bt.inserir(14, 4);
        bt.inserir(20, 5);
        bt.inserir(63, 6);
        bt.inserir(12, 7);


        Object valorRB = rb.getValue(arr[500]);
 
        System.out.println("Busca em rb com 20000 elementos:");
        System.out.println("Valor:" + valorRB);

        System.out.println();
        
        if(bt.balanceada()) {
        	System.out.println("\n É AVL");
        }else {
        	System.out.println("\n NÃO É AVL");
        }

        System.out.println("Porcentagem de Vermelhos: ");
        System.out.println(rb.PercentRed());
    }
}
