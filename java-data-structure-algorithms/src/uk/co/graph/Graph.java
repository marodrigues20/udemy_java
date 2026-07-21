package uk.co.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Graph {

    HashMap<String, List<String>> connectionFriendsList = new HashMap<>();


    public boolean addVertex(String vertex) {

        if (vertex == null) throw new IllegalArgumentException();

        if (connectionFriendsList.get(vertex) == null) {
            connectionFriendsList.put(vertex, new ArrayList<>());
            return true;
        }
        return false;
    }


    public boolean addEdge(String vertex1, String vertex2) {

        if (vertex1 == null || vertex2 == null) throw new IllegalArgumentException();

        if (connectionFriendsList.get(vertex1) != null && connectionFriendsList.get(vertex2) != null) {
            connectionFriendsList.get(vertex1).add(vertex2);
            connectionFriendsList.get(vertex2).add(vertex1);
            return true;
        }

        return false;
    }


    public boolean removeEdge(String vertex1, String vertex2) {

        if (vertex1 == null || vertex2 == null) throw new IllegalArgumentException();

        if (connectionFriendsList.get(vertex1) != null && connectionFriendsList.get(vertex2) != null) {
            connectionFriendsList.get(vertex1).remove(vertex2);
            connectionFriendsList.get(vertex2).remove(vertex1);
            return true;
        }
        return false;
    }


    public boolean removeVertex(String vertex){

        if (vertex == null ) throw new IllegalArgumentException();

        if(connectionFriendsList.get(vertex) != null){
            for(List<String> list : connectionFriendsList.values()){
                list.remove(vertex);
            }
            connectionFriendsList.remove(vertex);
            return true;
        }
        return false;
    }

}
