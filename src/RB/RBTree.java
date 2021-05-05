package RB;

public class RBTree<Key extends Comparable<Key>, Value>
{	
	    protected static final boolean RED = true;
	    protected static final boolean BLACK = false;
	    private double quantidadeBlack = 0; // Contadores da quantidade de nós pretos e de nós vermelhos
	    private double quantidadeRed = 0;	

	    public class Node {
	        public Key chave;
	        public Value valor;
	        public Node noEsquerda;
	        public Node noDireita;
	        public Node pai;
	        boolean cor;
	        int size;

	        Node(){
	            noEsquerda = null;
	            noDireita = null;
	            pai = null;
	            cor = BLACK;
	        }
	        
	        Node(Key key, Value value){
	            this.chave = key;
	            this.valor = value;
	            this.noEsquerda = null;
	            this.noDireita = null;
	            this.pai = null;
	            this.cor = BLACK;
	        }
	    }

	    protected Node raiz, nulo;
	    
	    public RBTree(){
	        nulo = new Node();
	        raiz = nulo;
	    }

	    //Rotação a Esquerda em um nó 'no'
	    //O filho noDireitaeito de 'no' sobe
	    // e 'no'	 se torna filho dele
	    // como um filho a Esquerda
	    private void rotacaoEsquerda(Node node) {
	        Node novaRaiz = node.noDireita;
	        node.noDireita = novaRaiz.noEsquerda;
	        
	        if(novaRaiz.noEsquerda != nulo)
	            novaRaiz.noEsquerda.pai = node;
	        
	        novaRaiz.pai = node.pai;
	        
	        if (node.pai == nulo)
	            raiz = novaRaiz;
	        else{
	            if(node == node.pai.noEsquerda)
	                node.pai.noEsquerda = novaRaiz;
	            else
	                node.pai.noDireita = novaRaiz;
	        }
	        
	        novaRaiz.noEsquerda = node; 
	        node.pai = novaRaiz;
	    }

	    //O mesmo do acima, porém invertendo Esquerda por Direita
	    // Assim as trocas são feitas a Direita
	    private void rotacaoDireita(Node node) { 
	        Node novaRaiz = node.noEsquerda;
	        node.noEsquerda = novaRaiz.noDireita;
	        
	        if(novaRaiz.noDireita != nulo)
	            novaRaiz.noDireita.pai = node;
	        
	        novaRaiz.pai = node.pai;
	        if(node.pai == nulo)
	            raiz = novaRaiz;
	        else{
	            if (node == node.pai.noEsquerda)
	                node.pai.noEsquerda = novaRaiz;
	            else
	                node.pai.noDireita = novaRaiz;
	        }
	        
	        novaRaiz.noDireita = node;
	        node.pai = novaRaiz;
	    }
	    
	    public void inserir(Key key, Value val){
	        Node ant = nulo, p = raiz;
	        while(p != nulo){
	            ant = p;
	            int cmp = key.compareTo(p.chave);
	            if (cmp < 0) 
	                p = p.noEsquerda;
	            else
	                p = p.noDireita;
	        }
	        
	        Node novo = new Node(key, val);
	        novo.pai = ant;
	        novo.noEsquerda = novo.noDireita = nulo;
	        novo.cor = RED;
	        
	        if (raiz == nulo){
	            raiz = novo;
	        }else{
	            int cmp = key.compareTo(ant.chave);
	            if (cmp < 0)
	                ant.noEsquerda = novo;
	            else
	                ant.noDireita = novo;     
	        }
	        
	        restaurarPropriedadesRB(novo);    
	    }
	    
	    public void restaurarPropriedadesRB(Node node){
	        Node novo;
	        
	        while(node.pai.cor == RED){
	            if(node.pai == node.pai.pai.noEsquerda){
	                novo = node.pai.pai.noDireita; 

	                if(novo.cor == RED){ // caso 1: node tem um tio novo vermelho
	                    novo.cor = BLACK;
	                    node.pai.cor = BLACK;
	                    node.pai.pai.cor = RED;
	                    node = node.pai.pai;
	                }else{
	                    if(node == node.pai.noDireita){ // caso 2: x tem um tio preto e x é filho direito
	                        node = node.pai;
	                        rotacaoEsquerda(node);   
	                    }

	                    node.pai.cor = BLACK; // caso 3: 
	                    node.pai.pai.cor = RED;
	                    rotacaoDireita(node.pai.pai);
	                }
	            }else{
	                novo = node.pai.pai.noEsquerda;
	                
	                if(novo.cor == RED){ // caso 1: espelhamento
	                    novo.cor = BLACK;
	                    node.pai.cor = BLACK;
	                    node.pai.pai.cor = RED;
	                    node = node.pai.pai;
	                }else{
	                    if(node == node.pai.noEsquerda){ // caso 2: espelhamento
	                        node = node.pai;
	                        rotacaoDireita(node);
	                    }
	                    node.pai.cor = BLACK; // caso 3: espelhamento
	                    node.pai.pai.cor = RED;
	                    rotacaoEsquerda(node.pai.pai);
	                }
	            }
	        }
	        raiz.cor = BLACK;
	    }
	    
	    public void mostrar(){
	        inOrdem(raiz, "   ");
	    }
	    
	    public void inOrdem(Node node, String str){
	        if(node != nulo){
	            inOrdem(node.noEsquerda, "   "+str);
	            System.out.println(str+node.chave);
	            inOrdem(node.noDireita, "   "+str);
	        }
	    }
	    
	    //Invoca a função de contar a quantidade de nós pretos e vermelhos e calcula a porcentagem
	    //Depois zera os valores da quantidade de nós pretos e vermelhos para um futura contagem.
	    public double percentualRED(){
	        contaBlackRed(raiz);
	        
	        double percentualRED = ((this.quantidadeRed/(this.quantidadeBlack+this.quantidadeRed)))*100;

	        this.quantidadeRed = 0;
	        this.quantidadeBlack = 0;

	        return percentualRED;
	    }
	    
	    public void contaBlackRed(Node node){
	        if(node != nulo){
	            contaBlackRed(node.noEsquerda);
	            if(node.cor == RED)
	                this.quantidadeRed++;
	            else
	                this.quantidadeBlack++;
	            contaBlackRed(node.noDireita);
	        }
	    }

	    private Value buscaValor(Node no, Key key){
	        if(key.compareTo(no.chave) == 0) return no.valor;

	        if(key.compareTo(no.chave)<0) 
	            return buscaValor(no.noEsquerda, key);
	        else 
	            return buscaValor(no.noDireita,key);
	    }

	    public Value buscaValor(Key key){
	        Node no = raiz;
	        Value valor = buscaValor(no, key);
	        return valor;
	    }
	    
	    public int altura() {
	        return altura(raiz);
	    }

	    private int altura(Node no) {
	        if (no != null) {
	            int he, hd;
	            he=altura(no.noEsquerda);
	            hd=altura(no.noDireita);
	            
	            if(he > hd) return he + 1;
	            else return hd + 1;
	        }

	        return 0;
	    }
	    
	    public boolean balanceada(){
	        return balanceada(raiz);
	    }

	    private boolean balanceada(Node node) {
	        int hd, he;
	        if (node != null) {
	            if(!balanceada(node.noEsquerda)) return false;
	            if(!balanceada(node.noDireita)) return false;
	            
	            hd = altura(node.noDireita);
	            he = altura(node.noEsquerda);
	            
	            if((he - hd)<-1 || 1<(he - hd)){
	                return false;
	            }
	        }
	        return true;
	    }
}
