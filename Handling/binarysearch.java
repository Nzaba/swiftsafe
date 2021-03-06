package Handling;

import java.util.ArrayList;
import java.util.Arrays;
import java.awt.List;
import java.io.*;
import java.lang.reflect.Array;
import java.lang.Object;
public class binarysearch{
	
    
    public static String[] getarray(File filename, String column) {
	
    	ArrayList<String> details = new ArrayList<String>();
    	
        BufferedReader br = null;
        String line = "";
        String SplitBy = ",";
        try {
            br = new BufferedReader(new FileReader(filename));
            while ((line = br.readLine()) != null) {
		
            	// Works if comma is separator
                String[] toadd = line.split(SplitBy);
                
		if(column=="name")
		    details.add(toadd[0]);
		if(column=="number")
		    details.add(toadd[1]);
		if(column=="csc")
		    details.add(toadd[2]);
		if(column=="expdate")
		    details.add(toadd[3]);
		if(column=="type")
		    details.add(toadd[4]);
		if(column=="bank")
		    details.add(toadd[5]);
		
                
	    }
            
	    
            
            
	    
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
	
        String[] arr = new String[details.size()];
        arr = details.toArray(arr);
	return arr;
    }
    
    public static String[][] getcarddetails(String name, File filename){
    	Node<String, String> findnumber = new Node<String, String>(getarray(filename, "name"),getarray(filename,"number"));
    	Node<String, String> findcsc = new Node<String, String>(getarray(filename, "name"),getarray(filename,"csc"));
    	Node<String, String> findexpdate = new Node<String, String>(getarray(filename, "name"),getarray(filename,"expdate"));
    	Node<String, String> findtype = new Node<String, String>(getarray(filename, "name"),getarray(filename,"type"));
    	Node<String, String> findbank = new Node<String, String>(getarray(filename, "name"),getarray(filename,"bank")); 
    	
    	String[][] toReturn = {{name, findnumber.find(name).toString(), findcsc.find(name).toString(), findexpdate.find(name).toString(), findtype.find(name).toString(), findbank.find(name).toString()}};
    	return toReturn;	
    }
    
    public static boolean checkname(File filename, String name) {
    	String [] names = getarray(filename,"name");
	for (String s: names) {
	    if (s.equals(name))
		return true;
	}
	return false;
	
    }
    
}


class Node<K extends Comparable<K>, V>{
    Node<K, V> leftChild;
    Node<K, V> rightChild;
    K key;
    V value;
    public Node(K[] keys, V[] values){
  	//recursively make tree.
	//Go through array of keys and values
	if (keys.length == 1){
	    this.leftChild = null;
	    this.rightChild = null;
	    this.value = values[0];
	    this.key = keys[0];
	}
	else if(keys.length == 0){
	    this.value = null;
	    this.key = null;
	    this.leftChild = null;
	    this.rightChild = null;
	}
	else{
	    int splitPoint = keys.length / 2;
	    this.leftChild = new Node<K, V>(Arrays.copyOfRange(keys, 0, splitPoint),
					    Arrays.copyOfRange(values, 0, splitPoint));
	    this.rightChild = new Node<K, V>(Arrays.copyOfRange(keys, splitPoint + 1, keys.length),
					     Arrays.copyOfRange(values, splitPoint + 1, keys.length));
	    this.value = values[splitPoint];
	    this.key = keys[splitPoint];
	}
    }
    public V find(K key){
	int comparison = this.key.compareTo(key);
	if (comparison  == 0){//they match!
	    return this.value;
	}
	else if (comparison > 0){
	    if (this.leftChild == null){return null;}
	    return this.leftChild.find(key);
	}
	else{// (comparison < 0)
	    if (this.rightChild == null){return null;}
	    return this.rightChild.find(key);
	}
	
    }
    
    private String asString(int depth){
	
	String toR = "";
	String tabs = "";
	for (int i = 0; i < depth; i ++){
	    tabs += "\t";
	}
	toR += tabs + "Node:\n";
	toR += tabs + "Key: " + this.key;
	toR += "\n" + tabs + "Value: " + this.value;
	if (this.leftChild != null)
	    toR += "\n" + tabs + "Left:\n" + this.leftChild.asString(depth +1);
	if (this.rightChild != null)
	    toR += "\n" + tabs + "Right:\n" + this.rightChild.asString(depth +1);
	
	return toR;
    }
    public String toString(){
    	return this.asString(0);
    }
    
}
