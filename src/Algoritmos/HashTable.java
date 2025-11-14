/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Algoritmos;

/**
 *
 * @author aUreLi0
 */
class ListNode {
    String key;
    Object value;
    ListNode next;

    public ListNode(String k, Object v) {
        key = k;
        value = v;
        next = null;
    }
}

public class HashTable {

    private static final int SIZE = 6;
    private ListNode[] bins;

    public HashTable() {
        bins = new ListNode[SIZE];
        for (int i = 0; i < SIZE; i++) bins[i] = null;
    }

    private int hashFunction(String key) {
        int hash = 0;
        for (int i = 0; i < key.length(); i++) {
            hash = (hash * 31 + key.charAt(i)) % SIZE;
        }
        return hash;
    }

    public void insert(String key, Object value) {
        int index = hashFunction(key);
        ListNode newNode = new ListNode(key, value);
        newNode.next = bins[index];
        bins[index] = newNode;
    }

    public Object buscar(String key) {
        int index = hashFunction(key);
        ListNode current = bins[index];
        while (current != null) {
            if (current.key.equals(key)) return current.value;
            current = current.next;
        }
        return null;
    }

    public boolean borrar(String key) {
        int index = hashFunction(key);
        ListNode current = bins[index];
        ListNode prev = null;
        while (current != null) {
            if (current.key.equals(key)) {
                if (prev != null) prev.next = current.next;
                else bins[index] = current.next;
                return true;
            }
            prev = current;
            current = current.next;
        }
        return false;
    }

    public void display() {
        for (int i = 0; i < SIZE; i++) {
            System.out.print("Bin " + i + ": ");
            ListNode current = bins[i];
            while (current != null) {
                System.out.print("[" + current.key + ": " + current.value + "]->");
                current = current.next;
            }
            System.out.println();
        }
    }
}

