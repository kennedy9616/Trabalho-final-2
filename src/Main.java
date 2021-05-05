import HashTable.*;

public class Main {

    public static void main(String[] args) {
        HashTentativaLinear table = new HashTentativaLinear();
        HashListaEncadeada table2 = new HashListaEncadeada();

        String a = new String("bbb");
        String b = new String("bbb");
        table.put(a, 20);
        System.out.println(table.get(a));
        table.put(b,10);
        System.out.println(table.get(b));

        table.deleteNoRemove(b);
        System.out.println(table.get(b));

    }
}
