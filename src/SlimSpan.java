import java.util.*;

/**
 * 3522
 **/
public class SlimSpan {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Solver solver;
        while ((solver = newSolverWithScannedInput(scanner)) != null) {
            System.out.println(solver.resultAsString());
        }
    }

    private static Solver newSolverWithScannedInput(Scanner scanner) {
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        if (n == 0 && m == 0) {
            return null;
        }
        Solver solver = new Solver(n, m);
        for (int i = 0; i < m; i++) {
            solver.addEdge(new Edge(scanner.nextInt(), scanner.nextInt(), scanner.nextInt()));
        }
        return solver;
    }

    private static class Solver {
        private final int numberOfVertexes;
        private final int numberOfEdges;
        // Note: If I use List<Edge> here, poj gave me a TimeLimitExceeded error.. so silly.
        private Edge[] edges;

        private Solver(int numberOfVertexes, int numberOfEdges) {
            this.numberOfVertexes = numberOfVertexes;
            this.numberOfEdges = numberOfEdges;
            edges = new Edge[numberOfEdges];
        }

        private int index = 0;
        private void addEdge(Edge edge) {
            edges[index++] = edge;
        }

        public String resultAsString() {
            return Integer.toString(solve());
        }

        private int solve() {
            Arrays.sort(edges);
            int minSlimness = -1;
            for (int start = 0; start < numberOfEdges - numberOfVertexes + 2; start++) {
                Graphs graphs = new Graphs(numberOfVertexes);
                for (int end = start; end < numberOfEdges; end++) {
                    graphs.addEdge(edges[end]);
                    if (graphs.allVertexesConnected()) {
                        int slimness = edges[end].weight - edges[start].weight;
                        if (minSlimness < 0 || minSlimness > slimness) {
                            minSlimness = slimness;
                        }
                    }
                }
                if (start == 0 && minSlimness == -1) {
                    return -1;
                }
            }
            return minSlimness;
        }
    }

    private static class Graphs {
        private int numberOfVertexes;
        private List<Set<Integer>> subGraphs = new ArrayList<Set<Integer>>();

        public Graphs(int numberOfVertexes) {
            this.numberOfVertexes = numberOfVertexes;
        }

        public void addEdge(Edge edge) {
            Set<Integer> subGraph1 = containingSubGraph(edge.v1);
            Set<Integer> subGraph2 = containingSubGraph(edge.v2);
            if (subGraph1 == null && subGraph2 == null) {
                Set<Integer> newSubGraph = new HashSet<Integer>();
                newSubGraph.add(edge.v1);
                newSubGraph.add(edge.v2);
                subGraphs.add(newSubGraph);
            } else if (subGraph1 != null && subGraph2 == null) {
                subGraph1.add(edge.v2);
            } else if (subGraph1 == null && subGraph2 != null) {
                subGraph2.add(edge.v1);
            } else if (subGraph1.equals(subGraph2)) {
                // do nothing
            } else {
                subGraphs.remove(subGraph2);
                subGraph1.addAll(subGraph2);
            }
        }

        private Set<Integer> containingSubGraph(int vertex) {
            for (Set<Integer> subGraph: subGraphs) {
                if (subGraph.contains(vertex)) {
                    return subGraph;
                }
            }
            return null;
        }

        public boolean allVertexesConnected() {
            return subGraphs.size() == 1 && subGraphs.get(0).size() == numberOfVertexes;
        }
    }

    private static class Edge implements Comparable<Edge> {
        private final int v1;
        private final int v2;
        private final int weight;

        private Edge(int v1, int v2, int weight) {
            this.v1 = v1;
            this.v2 = v2;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge edge) {
            return weight - edge.weight;
        }
    }
}
