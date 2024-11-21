package Recuperação.Ra3;

import java.util.Random;

// Classe Registro: Representa um item a ser armazenado na tabela hash
class Registro {
    int codigo;

    public Registro(int codigo) {
        this.codigo = codigo;
    }
}

// Classe Node: Representa um nó de uma lista encadeada para resolver colisões
class Node {
    Registro registro;
    Node next;

    public Node(Registro registro) {
        this.registro = registro;
        this.next = null;
    }
}

// Classe HashTable: Implementa a tabela hash com resolução de colisões por lista encadeada
class HashTable {
    private Node[] table;
    private int size;
    private int collisions;

    public HashTable(int size) {
        this.size = size;
        this.table = new Node[size];
        this.collisions = 0;
    }

    // Método hashFunction: Calcula o índice para a chave usando o método especificado
    public int hashFunction(int key, String method) {
        if (method.equals("divisao")) {
            return key % size;
        } else if (method.equals("multiplicacao")) {
            double A = 0.6180339887;
            return (int) (size * ((key * A) % 1));
        } else if (method.equals("dobramento")) {
            String keyStr = String.valueOf(key);
            int sum = 0;
            for (int i = 0; i < keyStr.length(); i += 3) {
                int part = Integer.parseInt(keyStr.substring(i, Math.min(i + 3, keyStr.length())));
                sum += part;
            }
            return sum % size;
        }
        return -1;
    }

    // Método insert: Insere um registro na tabela hash, resolvendo colisões
    public void insert(Registro registro, String method) {
        int index = hashFunction(registro.codigo, method);
        Node newNode = new Node(registro);

        if (table[index] == null) {
            table[index] = newNode;
        } else {
            collisions++;
            Node current = table[index];
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
    }

    // Método search: Busca um registro na tabela hash
    public boolean search(int codigo, String method) {
        int index = hashFunction(codigo, method);
        Node current = table[index];

        while (current != null) {
            if (current.registro.codigo == codigo) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    // Método getCollisions: Retorna o número total de colisões
    public int getCollisions() {
        return collisions;
    }
}

// Classe HashTableTest: Testa as tabelas hash com diferentes combinações de tamanhos e funções hash
public class HashTableTest {
    public static void main(String[] args) {
        int[] tableSizes = {1000, 10000, 100000};
        int[] dataSizes = {10000, 100000, 1000000};
        String[] hashMethods = {"divisao", "multiplicacao", "dobramento"};

        for (int tableSize : tableSizes) {
            for (int dataSize : dataSizes) {
                System.out.println("Tabela: " + tableSize + ", Dados: " + dataSize);

                Registro[] data = generateData(dataSize);
                int dataLength = dataSize; 

                for (String method : hashMethods) {
                    HashTable hashTable = new HashTable(tableSize);

                    long startInsert = System.nanoTime();
                    for (int i = 0; i < dataLength; i++) {
                        hashTable.insert(data[i], method);
                    }
                    long endInsert = System.nanoTime();
                    System.out.println("Método: " + method + ", Tempo de Inserção: " + (endInsert - startInsert) + " ns");
                    System.out.println("Colisões: " + hashTable.getCollisions());

                    long startSearch = System.nanoTime();
                    for (int i = 0; i < 5; i++) {
                        int randomIndex = new Random().nextInt(dataLength);
                        hashTable.search(data[randomIndex].codigo, method);
                    }
                    long endSearch = System.nanoTime();
                    System.out.println("Tempo de Busca: " + (endSearch - startSearch) + " ns");
                }
                System.out.println();
            }
        }
    }

    // Método generateData: Gera registros com códigos aleatórios de 9 dígitos
    public static Registro[] generateData(int size) {
        Registro[] vetor = new Registro[size];
        Random random = new Random(42);
        int vetorSize = size;

        for (int i = 0; i < vetorSize; i++) {
            vetor[i] = new Registro(random.nextInt(900000000) + 100000000);
        }
        return vetor;
    }
}
