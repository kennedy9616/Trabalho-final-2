package RB;
import AVL.*;

public class RBTree<Key extends Comparable<Key>, Value>
{	
	
	protected static final boolean RED = true;
    protected static final boolean BLACK = false;
	double QuantBlack = 0, QuantRed = 0;	// Contadores da quantidade de nós pretos e de nós vermelhos

    protected class Node {
        public Key chave;
        public Value valor;
        public Node esq, dir;
        boolean cor;
        int size;


        Node(Key key, Value value, int size, boolean color) {
            this.chave = key;
            this.valor = value;

            this.size = size;
            this.cor = color;
        }
    }

    protected Node raiz;

	private boolean isRed(Node h) {
		if(h == null){
			return false;
		}
		return h.cor == RED;
	}
	
	private boolean isBlack(Node h) {
		// Implementar método que verifica se o nó é preto
		if(h == null){
			return true;
		}
		return h.cor == BLACK;
	}
	
	 public int size() {
        return size(raiz);
    }

    protected int size(Node no) {
        if (no == null) {
            return 0;
        }

        return no.size;
    }

    public boolean isEmpty() {
        return size(raiz) == 0;
    }


    //Rotação a esquerda em um nó 'no'
	//O filho direito de 'no' sobe
	// e 'no'	 se torna filho dele
	// como um filho a esquerda
	 protected Node rotacaoEsquerda(Node no) {
        if (no == null || no.dir == null) {
            return no;
        }

        Node novaRaiz = no.dir;

        no.dir = novaRaiz.esq;
        novaRaiz.esq = no;

        novaRaiz.cor = no.cor;
        novaRaiz.cor = RED;

        novaRaiz.size = no.size;
        novaRaiz.size = size(no.esq) + 1 + size(no.dir);

        return novaRaiz;
    }

	/**
	 * Implementar o esse método
	 * @param h
	 * @return
	 */
	//O mesmo do acima, porém invertendo esquerda por direita
	// Assim as trocas são feitas a direita
	private Node rotacaoDireita(Node h) {
		// Implementar método que aplica a rotação à direita.
		if (h == null || h.dir == null) {
			return h;
		}

		Node novaRaiz = h.esq;

		h.esq = novaRaiz.dir;
		novaRaiz.dir = h;

		novaRaiz.cor = h.cor;
		h.cor = RED;

		novaRaiz.size = h.size;
		novaRaiz.size = size(h.esq) + 1 + size(h.dir);

		return novaRaiz;

	}

	//Faz com que o nó fique vermelho e seus dois filhos fiquem pretos
	private void trocaCor(Node h) {
		// Implementar método que troca as cores.
		h.cor = RED;
		h.esq.cor = BLACK;
		h.dir.cor = BLACK;
	}
	
	
	/**
	 * Insere um novo nó
	 * @param key
	 * @param val
	 */


	public void inserir(Key key, Value val){ 
		raiz = inserir(raiz, key, val);
		raiz.cor = BLACK;
	}

	private Node inserir(Node h, Key key, Value val)
	{
		if (h == null) // Do standard insert, with red link to parent.
			return new Node(key, val, 1, RED);
		
		int cmp = key.compareTo(h.chave);
		if (cmp < 0) 
			h.esq = inserir(h.esq, key, val);
		else if (cmp > 0) 
			h.dir = inserir(h.dir, key, val);
		else h.valor = val;
		
		if (isRed(h.dir) && !isRed(h.esq))	//Inserir um nó em um nó simples de modo há arvore ter um lado com menos nós pretos que outro
			h = rotacaoEsquerda(h);
		if (isRed(h.esq) && isRed(h.esq.esq)) // Inserir de forma a ter 2 nós vermelhos seguidos
			h = rotacaoDireita(h);
		if (isRed(h.esq) && isRed(h.dir)) // Caso os dois filhos sejam vermelhos recolorir
			trocaCor(h);
		
		h.size = size(h.esq) + size(h.dir) + 1;
		return h;
	}


	//Perccore a arvore rubro negra contando a quantidade de nós pretos e de nós vermelhos
	private void percorrer(Node h){
		if(isRed(h)){
			QuantRed++;
		}else{
			QuantBlack++;
		}

		if(h.esq != null)
			percorrer(h.esq);
		if(h.dir !=null)
			percorrer(h.dir);
	}

	//Invoca a função de contar a quantidade de nós pretos e vermelhos e calcula a porcentagem
	//Depois zera os valores da quantidade de nós pretos e vermelhos para um futura contagem.
	public double PercentRed(){
		if (raiz == null){
			return 0;
		}
		percorrer(raiz);
		double PercentualRed = ((QuantRed/(QuantBlack+QuantRed)))*100;

		QuantRed = 0;
		QuantBlack = 0;

		return PercentualRed;
	}

	//Percorre recursivamente cada nó da arvore RB e insere eles em uma arvore AVL
	private void InserirAVL(Node no, AVLTree avlTree){
		avlTree.put(no.chave, no.valor);
		if(no.esq != null)
			InserirAVL(no.esq, avlTree);
		if(no.dir != null)
			InserirAVL(no.dir,avlTree);
	}

	//Cria uma arvore avl e invoca o método InserirAVL que percorre arvore RB apartir de um nó passando a raiz da arvoreRB
	public AVLTree GetAVL(){
		AVLTree avlTree = new AVLTree();
		InserirAVL(raiz, avlTree);
		return avlTree;
	}


	private Value percorrer2(Node no, Key key){
		if(key.compareTo(no.chave) == 0){
			return no.valor;
		}
		if(key.compareTo(no.chave)<0)
			return percorrer2(no.esq, key);
		else{
			return percorrer2(no.dir,key);
		}
	}

	public Value getValue(Key key){
		Node no = raiz;
		Value valor = percorrer2(no, key);
		return valor;
	}

}
