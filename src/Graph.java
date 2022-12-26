import java.io.*;
import java.util.*;

public class Graph {

    public static void main(String[] args) throws IOException {
        Scanner choose = new Scanner(System.in);
        int chs;
        while (true) {
            System.out.println("choose output's method: " + "1. Read from file " + "2. Random graph adjacency table");
            if (choose.hasNextInt()) {
                chs = choose.nextInt();
                if (chs == 1) {
                    int[][] arr = readFromFile();
                    printArray(arr);
                    System.out.println(" Enter the number of node for Dijkstra algoritm: ");
                    while (true) {
                        if (choose.hasNextInt()) {
                            int getNum = choose.nextInt();
                            if (getNum < arr.length) {
                                dijkstra(arr, getNum);
                                System.exit(0);

                            } else {
                                System.out.println("Error, input correct value!");
                            }
                        }
                    }
                } else if (chs == 2) {
                    Scanner sc = new Scanner(System.in);
                    System.out.println("Enter the value of nodes: ");
                    int valueOfNodes = sc.nextInt();
                    int[][] graph = createMatrix(valueOfNodes);
                    System.out.println("Graph adjacency table:");
                    printArray(graph);
                    goToFile(graph);
                    System.out.println(" Enter the number of node for Dijkstra algoritm: ");
                    while (true) {
                        if (choose.hasNextInt()) {
                            int getNum = choose.nextInt();
                            if (getNum < graph.length) {
                                dijkstra(graph, getNum);
                                System.exit(0);
                            } else {
                                System.out.println("Error, input correct value!");
                            }
                        }
                    }
                }
                System.out.println("Error, enter the 1 or 2!");
            }
        }
    }

    public static int[][] createMatrix(int value) {
        int[][] gr = new int[value][value];
        for (int i = 0; i < gr.length; i++) {
            for (int j = i; j < gr[i].length; j++) {
                if (i == j) {
                    gr[i][j] = 0;
                } else {
                    gr[i][j] = (int) (Math.random() * 2);
                    if (gr[i][j] == 1) {
                        gr[i][j] = (int) (Math.random() * 100);
                    }
                    gr[j][i] = gr[i][j];
                }
            }
        }
        return gr;
    }

    public static void printArray(int[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {

                System.out.print(String.format("%4d", arr[i][j]));
            }
            System.out.println();
        }
    }

    public static void goToFile(int[][] array) {
        try (FileWriter writer = new FileWriter("Matrix.txt")) {
            for (int i = 0; i < array.length; ++i) {
                for (int j = 0; j < array[i].length; ++j) {
                    writer.write(String.format(array[i][j] + " " ));
                }
                writer.write("\r\n");
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void infoToFile(int[] arr, int node) {
        try (FileWriter writer = new FileWriter("Info.txt")) {
            writer.write("Node# \t Minimum Distance from node: " + node + "\n");

            for (int i = 0; i < arr.length; i++) {
                if (arr[i] == Integer.MAX_VALUE || arr[i] == 0) {
                    writer.write(i + "  " + " \t\t\t " + " no path " + "\n");
                } else {
                    writer.write(i + "  " + " \t\t\t " + arr[i] + "\n");
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    static void dijkstra(int graph[][], int src_node) {
        int dist[] = new int[graph.length];
        Boolean marked[] = new Boolean[graph.length];

        for (int i = 0; i < graph.length; i++) {
            dist[i] = Integer.MAX_VALUE;
            marked[i] = false;
        }

        dist[src_node] = 0;

        for (int count = 0; count < graph.length - 1; count++) {
            int u = findMinVert(dist, marked);
            marked[u] = true;
            for (int v = 0; v < graph.length; v++)
                if (!marked[v] && graph[u][v] != 0 && dist[u] != Integer.MAX_VALUE && dist[u] + graph[u][v] < dist[v])
                    dist[v] = dist[u] + graph[u][v];
        }
        printMinpath(dist, src_node);
        infoToFile(dist, src_node);

    }

    public static int findMinVert(int[] dist, Boolean[] marked) {
        int minVertex = -1;
        for (int i = 0; i < dist.length; i++) {
            if (!marked[i] && (minVertex == -1 || dist[i] < dist[minVertex])) {
                minVertex = i;
            }
        }
        return minVertex;
    }
    static void printMinpath(int arr[], int node) {
        System.out.println("Node# \t Minimum Distance from node: " + node);
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == Integer.MAX_VALUE || arr[i] == 0) {
                System.out.println(i + " \t\t\t " + " no path ");
            } else {
                System.out.println(i + " \t\t\t " + arr[i]);
            }
        }
    }

    public static int[][] readFromFile() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("Matrix.txt"));
        String[] firstLine;

        if (scanner.hasNextLine()) {
            firstLine = scanner.nextLine().split(" ");
        } else {
            throw new RuntimeException("File is empty");
        }
        int size = firstLine.length;
        int[][] matrix = new int[size][size];
        System.out.println("Size of matrix: " + size);

        matrix[0] = Arrays.stream(firstLine).mapToInt(Integer::valueOf).toArray();
        for (int i = 1; i < size; i++) {
            if (scanner.hasNextLine()) {
                matrix[i] = Arrays.stream(scanner.nextLine().split(" ")).mapToInt(Integer::valueOf).toArray();
            } else {
                throw new RuntimeException("Размер матрицы не соотвествует требованиям");
            }
        }
        return matrix;
    }
}










