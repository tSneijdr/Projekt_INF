/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.graph.transformation.subclasses;

import java.util.ArrayList;
import java.util.List;

import controller.graph.transformation.Transformation;
import model.graph.graph.Graph;
import model.graph.graph.Node;
import utils.datastructures.Pair;

/**
 *
 * @author ts
 */
public class CircularLayout2 extends Transformation {
    
    private static List<Pair> bucketSort(List<Pair> nodeList, int n, int maxDegree) {
        List<List<Pair>> buckets = new ArrayList<>(n);
        List<Pair> sortedNodes = new ArrayList<>();
        
        // intialize buckets with empty array lists for adding buckets at a specific index
        for (int i=0; i<n; i++) {
            buckets.add(i, new ArrayList());
        }
        
        // store the nodes in the buckets
        for (Pair pair : nodeList) {
            Node node = (Node) pair.firstValue;
            int currentDegree = (int) pair.secondValue;
            int bucketNumber = (int) Math.floor((currentDegree / maxDegree) * n);
            
            // read out old list in the bucket, append the current node and overwrite the old bucket
            List<Pair> oldList = buckets.get(bucketNumber);
            oldList.add(new Pair(node, currentDegree));
            buckets.set(bucketNumber, oldList);
        }
        
        // get the nodes from the bucket in one list
        // warning: normally insertion sort is used to sort the single buckets but it is useless here
        for (int i=0; i<n; i++) {
            for (Pair pair : buckets.get(i)) {
                sortedNodes.add(pair);
            }
        }
        
        return sortedNodes;
    }

    @Override
    public void applyOn(Graph g) {
        /*List<Node> allNodes = g.getAllNodes();
        List<Pair> table = new ArrayList<>();
        int n = allNodes.size();
        int minDegree = 0;
        int maxDegree = 0;
        
        // get minimum and maximum degree for every node and add them to table
        for (Node node : allNodes) {
            int currentDegree = 0;
            currentDegree += node.getChildren().size();
            currentDegree += node.getParents().size();
            
            table.add(new Pair(node, currentDegree));
            
            if (currentDegree > maxDegree) {
                maxDegree = currentDegree;
            } else if (currentDegree < minDegree) {
                minDegree = currentDegree;
            }
        }
        
        // 1. Bucket sort the nodes by ascending degree into a table
        table = bucketSort(table, n, maxDegree);*/
        
        
        /*
        einfach mal knoten auf kreis zeichnen und schauen wies aussieht
        */
        
        List<Node> nodeList = g.getAllNodes();
        int n = nodeList.size();
        
        int a = 100; // pane width / 2
        int b = 100; // pane heigth / 2
        int m = Math.min(a, b);
        int r = 4 * m / 5;
        
        for (int i=0; i<n; i++) {
            double theta = 2 * Math.PI * i / n;
            
            int x = (int) (a + r * Math.cos(theta));
            int y = (int) (b + r * Math.sin(theta));
            
            Node node = nodeList.get(i);
            node.setxCenter(x);
            node.setyCenter(y);
        }
        
        
    }
}

