package Recuperação.Ra4;

import java.io.FileWriter;
import java.io.IOException;

public class SortingComparison {

    public static void main(String[] args) throws IOException {
        Vetor dataset1 = new Vetor(50);
        int[] data1 = {1, 100, 2, 99, 3, 98, 4, 97, 5, 96, 6, 95, 7, 94, 8, 93, 9, 92, 10, 91, 11, 90, 
                    12, 89, 13, 88, 14, 87, 15, 86, 16, 85, 17, 84, 18, 83, 19, 82, 20, 81, 21, 80, 
                    22, 79, 23, 78, 24, 77, 25, 76};
        for (int val : data1) {
            dataset1.add(val);
        }

        Vetor dataset2 = new Vetor(50);
        int[] data2 = {1, 100, 99, 98, 97, 96, 95, 94, 93, 92, 91, 90, 89, 88, 87, 86, 85, 84, 83, 82, 
                    81, 80, 79, 78, 77, 76, 75, 74, 73, 72, 71, 70, 69, 68, 67, 66, 65, 64, 63, 62, 
                    61, 60, 59, 58, 57, 56, 55, 54, 53, 52};
        for (int val : data2) {
            dataset2.add(val);
        }

        Vetor dataset3 = new Vetor(50);
        int[] data3 = {50, 49, 48, 47, 46, 45, 44, 43, 42, 41, 40, 39, 38, 37, 36, 35, 34, 33, 32, 31, 
                    30, 29, 28, 27, 26, 25, 24, 23, 22, 21, 20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 
                    10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
        for (int val : data3) {
            dataset3.add(val);
        }

        Vetor[] datasets = {dataset1, dataset2, dataset3};
        String[] algorithms = {"Merge Sort", "Radix Sort", "Quick Sort"};

        FileWriter writer = new FileWriter("results.csv");
        writer.append("Algorithm,Dataset,Time(ns),Swaps,Iterations\n");

        for (int datasetIndex = 0; datasetIndex < datasets.length; datasetIndex++) {
            for (int algorithmIndex = 0; algorithmIndex < algorithms.length; algorithmIndex++) {

                Vetor data = datasets[datasetIndex].clone();

                long startTime = System.nanoTime();

                Metrics metrics;
                if (algorithmIndex == 0) {
                    metrics = mergeSort(data);
                } else if (algorithmIndex == 1) {
                    metrics = radixSort(data);
                } else {
                    metrics = quickSort(data);
                }

                long endTime = System.nanoTime();
                long elapsedTime = endTime - startTime;

                System.out.println(algorithms[algorithmIndex] + " - Dataset " + (datasetIndex + 1) +
                        ": Time = " + elapsedTime + "ns, Swaps = " + metrics.swaps +
                        ", Iterations = " + metrics.iterations);

                writer.append(algorithms[algorithmIndex] + "," + (datasetIndex + 1) + "," +
                        elapsedTime + "," + metrics.swaps + "," + metrics.iterations + "\n");
            }
        }

        writer.flush();
        writer.close();
    }

    static class Metrics {
        int swaps;
        int iterations;

        Metrics(int swaps, int iterations) {
            this.swaps = swaps;
            this.iterations = iterations;
        }
    }

    static class Vetor {
        private int[] data;
        private int size;

        public Vetor(int capacity) {
            data = new int[capacity];
            size = 0;
        }

        public void add(int value) {
            if (size < data.length) {
                data[size++] = value;
            }
        }

        public int get(int index) {
            if (index >= 0 && index < size) {
                return data[index];
            }
            return -1;
        }

        public void set(int index, int value) {
            if (index >= 0 && index < size) {
                data[index] = value;
            }
        }

        public int size() {
            return size;
        }

        public Vetor clone() {
            Vetor copy = new Vetor(data.length);
            for (int i = 0; i < size; i++) {
                copy.add(data[i]);
            }
            return copy;
        }
    }

    public static Metrics mergeSort(Vetor arr) {
        int n = arr.size();
        int[] data = arr.data;
        int[] temp = new int[n];
        int iterations = 0, swaps = 0;

        for (int size = 1; size < n; size *= 2) {
            for (int start = 0; start < n; start += 2 * size) {
                int mid = Math.min(start + size, n);
                int end = Math.min(start + 2 * size, n);

                int left = start, right = mid, k = start;

                while (left < mid && right < end) {
                    iterations++;
                    if (data[left] <= data[right]) {
                        temp[k++] = data[left++];
                    } else {
                        temp[k++] = data[right++];
                        swaps++;
                    }
                }

                while (left < mid) {
                    iterations++;
                    temp[k++] = data[left++];
                }

                while (right < end) {
                    iterations++;
                    temp[k++] = data[right++];
                }

                for (int i = start; i < end; i++) {
                    data[i] = temp[i];
                }
            }
        }

        return new Metrics(swaps, iterations);
    }

    public static Metrics quickSort(Vetor arr) {
        return new Metrics(0, 0); // Placeholder
    }

    public static Metrics radixSort(Vetor arr) {
        return new Metrics(0, 0); // Placeholder
    }
}
