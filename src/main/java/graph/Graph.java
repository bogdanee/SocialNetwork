package graph;

import domain.Friendship;
import domain.User;
import utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    private final List<User> nodes;
    private final List<Friendship> edges;
    public int[][] adjMatrix;
    public int[][] distanceMatrix;
    public int[] visited;

    public Graph(List<User> nodes, List<Friendship> edges) {
        this.nodes = nodes;
        this.edges = edges;
        this.adjMatrix = new int[nodes.size()][nodes.size()];
        this.distanceMatrix = new int[nodes.size()][nodes.size()];
        this.visited = new int[nodes.size()];
        createAdjMatrix();
        createDistanceMatrix();
        determinateConex();
    }

    /**
     * Transform friendship's ids into 2 indexes based on order in user's list
     * @param edge - Friendship
     * @return a list of 2 integers
     */
    private List<Integer> findIndexFriendship(Friendship edge)
    {
        List<Integer> edges = new ArrayList<Integer>();
        for (int i=0;i<nodes.size();i++)
            if (edge.getId1() == nodes.get(i).getId() || edge.getId2() == nodes.get(i).getId())
                edges.add(i);
        return edges;
    }

    /**
     * Create adjacency matrix based on user and friendship lists
     */
    private void createAdjMatrix()
    {
        List<Integer> index;
        for(Friendship f:edges)
        {
            index = findIndexFriendship(f);
            adjMatrix[index.get(0)][index.get(1)] = 1;
            adjMatrix[index.get(1)][index.get(0)] = 1;
        }
    }

    /**
     * Create distance matrix based on adjacency matrix
     */
    private void createDistanceMatrix()
    {
        for(int i=0;i<nodes.size();i++)
            for(int j=0;j<nodes.size();j++)
                if (adjMatrix[i][j]==1)
                    distanceMatrix[i][j]=1;
                else
                    distanceMatrix[i][j]= Constants.INF;
    }

    /**
     * Depth-First Search for an index from user's list
     * @param index - integer
     * @param visitValue - integer
     */
    private void DFS(int index, int visitValue)
    {
        visited[index] = visitValue;
        for (int i=0;i<nodes.size();i++)
            if (adjMatrix[i][index]==1 && visited[i]==0)
                DFS(i, visitValue);
    }

    /**
     * Determines the conex components of the graph
     */
    private void determinateConex()
    {
        int visitValue = 1;
        DFS(0,visitValue);
        for (int i=1;i<nodes.size();i++)
            if (visited[i]==0)
            {
                visitValue++;
                DFS(i, visitValue);
            }
    }

    /**
     * Getter visited list.
     * @return a list of integers that has as elements the community number of the user's index
     */
    public int[] getConexList()
    {
        return visited;
    }
}
