/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MazeSolver;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author xhu
 */
public class FileManager {
    public String name;
    public int numberOfLines;
    public String[] lineData;
    public String data = "";
    public int rows;
    public int columns;
    public int[][] maze;
    public int[] startCoords = new int[2];
    
    //Hashmap to store node name and coordinates
    Map<String, int[]> nodePosition = new HashMap<>();
    
    //Hashmap to store node name and connected nodes
    Map<String, String[]> nodeConnections = new HashMap<>();
    
  
    public FileManager(String fileName) {
        this.name = fileName;
        File f = new File(name);
        
        try {
            Scanner myScanner = new Scanner(f);
            
            //Reading first line to get rows and columns of Maze
            if (myScanner.hasNextLine()) {
                //Splitting line with the commas
                String[] firstLine = myScanner.nextLine().split(",");
                columns = Integer.parseInt(firstLine[1]);
                rows = Integer.parseInt(firstLine[2]);
                maze = new int[rows][columns]; // Initialize the maze array
            }
            
            //Reading every other line to get node info and store in hashmaps
            while (myScanner.hasNextLine()) {
                String line = myScanner.nextLine();
                String[] parts = line.split(",");
                String nodeName = parts[0];
                int y = Integer.parseInt(parts[1]);
                int x = Integer.parseInt(parts[2]);
                String nextNode1 = parts[3];
                String nextNode2 = parts[4];
                
                nodePosition.put(nodeName, new int[]{x, y});
                nodeConnections.put(nodeName, new String[]{nextNode1, nextNode2});
                
                /*
                * Maze is a 2D array. 
                * Different values mean different things.
                * 0/Null = No Node | 1: Node | 2: Start | 3: End 
                */
                
                switch (nodeName) {
                    case "START":
                        maze[x][y] = 2;
                        startCoords[0] = x;
                        startCoords[1] = y;
                        break;
                    case "EXIT":
                        maze[x][y] = 3;
                        break;
                    default:
                        maze[x][y] = 1;
                        break;
                }
            }
            myScanner.close();
        } catch (IOException e) {
            System.out.println("Cannot read the file" + e.getMessage());
        }
    }

    public int[][] getMaze() {
        return maze;
    }
    
    public Map<String, int[]> getNodePosition(){
        return nodePosition;
    }
    
    public Map<String, String[]> getNodeConnections(){
        return nodeConnections;
    }
}
