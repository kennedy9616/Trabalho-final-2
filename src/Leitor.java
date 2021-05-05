import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Leitor {

   public static  String[] leitor1(int quantidade){

       File arquivo = new File("Tabela_String_Double.csv");
       String[] arr = new String[quantidade];

       try{
           Scanner leitor = new Scanner(arquivo);

           String linhas = new String();
           linhas = leitor.nextLine();

           for(int i=0;i<quantidade;i++){
               linhas = leitor.nextLine();
               String[] linha = linhas.split(",");
               arr[i] = gerarObjeto1(linha);
           }
       }catch (FileNotFoundException e){
           System.out.println("not found");
       }
       return arr;
    }

    private static String gerarObjeto1(String[] linha){
       String chave = linha[0].toUpperCase();
       return new String(chave);
    }



}