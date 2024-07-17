/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MazeSolver;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author xhu
 */
public class BinaryMaze {
    public static String fileName;
    public final static List<String> path = new ArrayList<>();
    
    public static void main(String[] args) {
        //Creating Frame
        JFrame frame = new JFrame("Maze");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(647, 700);
        frame.setResizable(false);
        
        //Buttons to ask use for maze
        JPanel buttonPanel = new JPanel();
        JButton maze1Button = new JButton("Run Maze1");
        JButton maze2Button = new JButton("Run Maze2");
        
        buttonPanel.add(maze1Button);
        buttonPanel.add(maze2Button);
        
        frame.add(buttonPanel);
        frame.setVisible(true);
        
        
        // Adding selected maze to Frame
        maze1Button.addActionListener((ActionEvent e) -> {
            runMaze(frame, buttonPanel, "Maze1.txt");
        });

        maze2Button.addActionListener((ActionEvent e) -> {
            runMaze(frame, buttonPanel, "Maze2.txt");
        });
    }

    private static void runMaze(JFrame frame, JPanel buttonPanel, String fileName) {
        Panel panel = new Panel(fileName);
        frame.add(panel);
        frame.setVisible(true);
        frame.remove(buttonPanel);

        //Retrieve the maze file data
        FileManager fileManager = new FileManager(fileName);
        Map<String, int[]> nodePosition = fileManager.getNodePosition();
        Map<String, String[]> nodeConnections = fileManager.getNodeConnections();

        //Define start and end nodes
        String startNode = "START";
        String endNode = "W";

        //Clear the path list before starting
        path.clear();

        //Run DepthFirst to find the path
        Thread searchThread = new Thread(() -> {
            DepthFirst.searchPath(nodePosition, nodeConnections, startNode, endNode, path, panel);
        });
        searchThread.start();
        
        //Repaint the panel to show the path
        panel.setPath(path);
        panel.repaint();
    }
}