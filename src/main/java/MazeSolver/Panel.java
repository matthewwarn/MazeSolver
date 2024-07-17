/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MazeSolver;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;

/**
 *
 * @author xhu
 */
public class Panel extends JPanel{
    public int[][] maze;
    public Map<String, int[]> nodePosition;
    public Map<String, String[]> nodeConnections;
    private List<String> path;
    private String currentNodeName;
    private boolean endNodeFound = false;
    int nextX = 0;
    int nextY = 0;
    int currentX = 0;
    int currentY = 0;
    
    public Panel(String fileName)
    {
        FileManager fileManager = new FileManager(fileName);
        
        //Retrieving Maze and Node Data
        int[][] maze = fileManager.getMaze();
        Map<String, int[]> nodePosition = fileManager.getNodePosition();
        Map<String, String[]> nodeConnections = fileManager.getNodeConnections();
        
        this.maze = maze;
        this.nodePosition = nodePosition;
        this.nodeConnections = nodeConnections;
    }
    
    
    @Override
    public void paint(Graphics g)
    {
        this.paintComponent(g);
        
        //Drawing the nodes 
        for(int row = 0; row < maze.length; row++){
            for(int column = 0; column < maze[0].length; column++){
                switch (maze[row][column]) {
                    case 1: //Normal Node
                        g.setColor(Color.BLUE);
                        g.fillOval(90 * column + 30, 90 * row + 30, 30, 30);
                        break;
                    case 2: //Start Node
                        g.setColor(Color.RED);
                        g.fillOval(90 * column + 30, 90 * row + 30, 30, 30);
                        break;
                    case 3: //Exit Node
                        g.setColor(Color.GREEN);
                        g.fillOval(90 * column + 30, 90 * row + 30, 30, 30);
                        break;
                    default:
                        break;
                }
            }
        }
        
        //Drawing the node names
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 14));
        
        for (Map.Entry<String, int[]> entry : nodePosition.entrySet()) {
            String nodeName = entry.getKey();
            int[] position = entry.getValue();
            int y = position[0] * 90 + 20; 
            int x = position[1] * 90 + 25; 
            g.drawString(nodeName, x, y);
        }
        
        //Drawing paths between connected nodes
        g.setColor(Color.YELLOW);
        Graphics2D g2 = (Graphics2D) g;
        
        for(Map.Entry<String, String[]> entry : nodeConnections.entrySet()){
            //Tracking current node and connected nodes
            String current = entry.getKey();
            String[] connections = entry.getValue();
            String nextNode1 = connections[0];
            String nextNode2 = connections[1];
            int[] connectedPosition;
            
            //Getting position of current node
            int[] currentPosition = nodePosition.get(current);
            int currentX = currentPosition[1] * 90 + 45;
            int currentY = currentPosition[0] * 90 + 45;
            
            //Getting position of connected node
            for(String connectedNode : connections){
                if(!connectedNode.equals("A")){
                    //Exit node doesn't have the same name as it's reference
                    //in txt file so we check if connection is the exit node.
                    if(connectedNode.equals("W")){
                       connectedPosition = nodePosition.get("EXIT"); 
                    } else{
                        connectedPosition = nodePosition.get(connectedNode);
                    }
                    
                    if(connectedPosition != null){
                        int connectedX = connectedPosition[1] * 90 + 45;
                        int connectedY = connectedPosition[0] * 90 + 45;
                        
                        //Drawing the paths
                        Stroke newStroke = new BasicStroke(3);
                        g2.setStroke(newStroke);
                        g.drawLine(currentX, currentY, connectedX, connectedY);
                    }
                }
            }
        }
        //Drawing the path to exit
        if (path != null && !path.isEmpty()) {
            g.setColor(Color.RED);
            for (int i = 0; i < path.size() - 1; i++) {
                String currentNode = path.get(i);
                String nextNode = path.get(i + 1);

                int[] currentPosition = nodePosition.get(currentNode);
                int[] nextPosition = nodePosition.get(nextNode);
                
                if (currentPosition != null && nextPosition != null) {
                    currentX = currentPosition[1] * 90 + 45;
                    currentY = currentPosition[0] * 90 + 45;
                    nextX = nextPosition[1] * 90 + 45;
                    nextY = nextPosition[0] * 90 + 45;
                }

                Stroke newStroke = new BasicStroke(3);
                g2.setStroke(newStroke);
                g2.drawLine(currentX, currentY, nextX, nextY);
            } 
        }
        
        //Drawing "Success!" and Path on to GUI after Exit node is found
        if(endNodeFound){
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Success!", 270, 575); 
            
            g.setFont(new Font("Arial", Font.ITALIC, 15));
            g.drawString("Path: " + String.join(" -> ", path), 100, 600);
        }
    }
    
    public void setPath(List<String> path) {
        this.path = path;
    }
    
    public void setCurrentNode(String currentNodeName) {
        this.currentNodeName = currentNodeName;
    }
    
    public void setEndNodeFound(boolean found) {
        this.endNodeFound = found;
        repaint(); 
    }
}
