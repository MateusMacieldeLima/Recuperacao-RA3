package Recuperação.Ra3;

import java.util.Random;

class Registro {
    int codigo;

    public Registro(int codigo) {
        this.codigo = codigo;
    }
}

class Node {
    Registro registro;
    Node next;

    public Node(Registro registro) {
        this.registro = registro;
        this.next = null;
    }
}

class HashTable {
    private Node[] table;
    private int size;
    private int collisions;

    public HashTable(int size) {
        this.size = size;
        this.table = new Node[size];
        this.collisions = 0;
    }

    public int hashFunction(int key, int method) {
        if (method == 1) {
            return key % size;
        } else if (method == 2) {
            double A = 0.6180339887;
            double fractional = (key * A) - (int) (key * A);
            return (int) (size * fractional);
        } else if (method == 3) {
            int sum = 0;
            int number = key;
            while (number > 0) {
                int part = number % 1000;
                sum += part;
                number /= 1000;
            }
            return sum % size;
        }
        return -1;
    }

    public void insert(Registro registro, int method) {
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

    public boolean search(int codigo, int method) {
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

    public int getCollisions() {
        return collisions;
    }
}

public class HashTableTest {
    public static void main(String[] args) {
        int[] tableSizes = {1000, 10000, 100000};
        int[] dataSizes = {10000, 100000, 1000000};
        int[] hashMethods = {1, 2, 3};

        for (int tableSize : tableSizes) {
            for (int dataSize : dataSizes) {
                System.out.println("Tabela: " + tableSize + ", Dados: " + dataSize);

                Registro[] data = generateData(dataSize);

                for (int method : hashMethods) {
                    HashTable hashTable = new HashTable(tableSize);

                    long startInsert = System.nanoTime();
                    for (int i = 0; i < dataSize; i++) {
                        hashTable.insert(data[i], method);
                    }
                    long endInsert = System.nanoTime();
                    System.out.println("Método: " + method + ", Tempo de Inserção: " + (endInsert - startInsert) + " ns");
                    System.out.println("Colisões: " + hashTable.getCollisions());

                    long startSearch = System.nanoTime();
                    for (int i = 0; i < 5; i++) {
                        int randomIndex = new Random().nextInt(dataSize);
                        hashTable.search(data[randomIndex].codigo, method);
                    }
                    long endSearch = System.nanoTime();
                    System.out.println("Tempo de Busca: " + (endSearch - startSearch) + " ns");
                }
                System.out.println();
            }
        }
    }

    public static Registro[] generateData(int size) {
        Registro[] vetor = new Registro[size];
        Random random = new Random(42);
        for (int i = 0; i < size; i++) {
            vetor[i] = new Registro(random.nextInt(900000000) + 100000000);
        }
        return vetor;
    }
}
