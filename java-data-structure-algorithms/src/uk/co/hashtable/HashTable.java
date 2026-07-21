package uk.co.hashtable;

import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.List;

public class HashTable {
    int size = 7;
    Node[] dataMap;

    public HashTable() {
        dataMap = new Node[size];
    }


    public void set(String key, int value) {

        int index = hash(key);
        Node newNode = new Node(key, value);

        if (dataMap[index] == null) {
            dataMap[index] = newNode;
        } else {
            Node temp = dataMap[index];

            if (temp.key == key) {
                temp.value = value;
                return;
            }
            //temp = temp.next;


            while (temp.next != null) {
                temp = temp.next;

                if (temp.key == key) {
                    temp.value = value;
                    return;
                }

            }
            temp.next = newNode;
        }
    }


    public Integer get(String key) {

        int index = hash(key);
        Node temp = dataMap[index];

        if (temp != null) {

            if (temp.key == key) {
                return temp.value;
            }


            while (temp.next != null) {
                temp = temp.next;
                if (temp != null) {
                    if (temp.key == key) {
                        return temp.value;
                    }
                }
            }
        }

        return null;
    }


    public List<String> keys() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < dataMap.length; i++) {
            if (dataMap[i] != null) {
                Node temp = dataMap[i];
                while (temp != null) {
                    list.add(temp.key);
                    temp = temp.next;
                }
            }
        }
        return list;
    }

    public boolean remove(String key) {

        int index = hash(key);
        Node temp = dataMap[index];

        if (temp == null) return false;

        if (temp.key == key) {
            dataMap[index] = temp.next;
            return true;
        }
        Node prev;
        while (temp.next != null) {

            if (temp.next.key == key) {
                prev = temp;
                temp = temp.next.next;
                prev.next = temp;
                return true;
            } else {
                temp = temp.next;
            }
        }
        return false;
    }


    public void print(){

        for (int i = 0; i < dataMap.length; i ++){
            System.out.println("bucket[" + i + "]");
            Node temp = dataMap[i];

            if(temp == null){
                continue;
            }else {
                System.out.println("bucket[" + i + "] Node: Key: " + temp.key + "value: " + temp.value);
            }

            while(temp.next != null){
                temp = temp.next;
                System.out.println("bucket[" + i + "] Node: Key: " + temp.key + "value: " + temp.value);
            }
        }
    }


    private int hash(String key) {
        char[] charKeyArray = key.toCharArray();
        int hash = 0;

        for (int i = 0; i < charKeyArray.length; i++) {
            int codePoint = charKeyArray[i];
            hash = (hash + codePoint * 23) % dataMap.length;
        }
        return hash;
    }

    class Node {
        String key;
        int value;
        Node next;

        public Node(String key, int value) {
            this.key = key;
            this.value = value;
        }
    }


}
