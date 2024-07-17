package MazeSolver;

import java.util.*;
import javax.swing.SwingUtilities;

public class DepthFirst {

    public static boolean searchPath(Map<String, int[]> nodePosition, Map<String, String[]> nodeConnections, String currentNode, String endNode, List<String> path, Panel panel) {
        //For debugging
        System.out.println(path);
        
        //Check if the current node is the end node
        if (currentNode.equals(endNode)) {
            path.add("EXIT");
            System.out.println(path);
            panel.repaint();
            panel.setEndNodeFound(true);
            return true;
        }
        
        //Mark the current node as visited by adding it to the path
        path.add(currentNode);
        
        //Update GUI to highlight current node in red
        SwingUtilities.invokeLater(() -> {
            panel.setCurrentNode(currentNode);
            panel.repaint();
        });

        //Delay to display the traversal algorithm more clearly
        try {
            Thread.sleep(250); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Retrieve the connections of the current node
        String[] connections = nodeConnections.get(currentNode);
        
        //Check each connected node
        if (connections != null) {
            for (String nextNode : connections) {
                //If next node isn't in path, recursively search it
                if (!path.contains(nextNode)) {
                    boolean found = searchPath(nodePosition, nodeConnections, nextNode, endNode, path, panel);
                    if (found) {
                        return true;
                    }
                }
            }
        }

        //If none of the paths from the current node lead to the exit, backtrack
        path.remove(path.size() - 1);
        return false;
    }
}