/*
 * StringNode.java
 *
 * Computer Science 112, Boston University
 * 
 * modified by:
 *   name: Mohao Yi
 *   email: mohaoyi@bu.edu
 */

package hw4;

import java.io.*;
import java.util.*;

/**
 * A class for representing a string using a linked list.
 * Each character of the string is stored in a separate node.  
 *
 * This class represents one node of the linked list.  The string as a
 * whole is represented by storing a reference to the first node in
 * the linked list. The methods in this class are static methods that
 * take a reference to a string linked-list as a parameter. This
 * approach allows us to use recursion to write many of the methods,
 * and it also allows the methods to handle empty strings, which are 
 * represented using a value of null.
 */
public class StringNode {
    private char ch;
    private StringNode next;

    /**
     * Constructor
     */
    public StringNode(char c, StringNode n) {
        this.ch = c;
        this.next = n;
    }

    /**
     * getNode - private helper method that returns a reference to
     * node i in the given linked-list string.  If the string is too
     * short or if the user passes in a negative i, the method returns null.
     */
    private static StringNode getNode(StringNode str, int i) {
        if (i < 0 || str == null) {    // base case 1: not found
            return null;
        } else if (i == 0) {           // base case 2: just found
            return str;
        } else {
            return getNode(str.next, i - 1);
        }
    }

    /*****************************************************
     * Public methods (in alphabetical order)
     *****************************************************/

    /**
     * charAt - returns the character at the specified index of the
     * specified linked-list string, where the first character has
     * index 0.  If the index i is < 0 or i > length - 1, the method
     * will end up throwing an IllegalArgumentException.
     */
    public static char charAt(StringNode str, int i) {
        if (str == null) {
            throw new IllegalArgumentException("the string is empty");
        } 
          
        StringNode node = getNode(str, i);

        if (node != null) {
            return node.ch;     
        } else {
            throw new IllegalArgumentException("invalid index: " + i);
        }
    }

    /**
     * convert - converts a standard Java String object to a linked-list
     * string and returns a reference to the linked-list string
     */
    public static StringNode convert(String s) {
        if (s.length() == 0) {
            return null;
        }

        StringNode firstNode = new StringNode(s.charAt(0), null);
        StringNode prevNode = firstNode;
        StringNode nextNode;

        for (int i = 1; i < s.length(); i++) {
            nextNode = new StringNode(s.charAt(i), null);
            prevNode.next = nextNode;
            prevNode = nextNode;
        }

        return firstNode;
    }
    
    
    // copy - uses iteration to return a copy of the given linked-list string
    public static StringNode copy(StringNode str) {
    	
    	if (str == null) {
    		return null;
    	}
    	
    	// the first node of the copy
    	StringNode copyFirst = new StringNode(str.ch, null);
    	// the traversal variable that helps to update the copy
    	StringNode trav = copyFirst;
    	
    	// update the copy as long as there are remaining nodes to be copied
        while (str.next != null) {
        	// create the next node of the copy
        	trav.next = new StringNode(str.next.ch, null);
        	// update the traversal variable
        	trav = trav.next;
        	// update the next node to be copied
        	str = str.next;
        }
        
        return copyFirst;
    }

    /**
     * deleteChar - deletes character i in the given linked-list string and
     * returns a reference to the resulting linked-list string
     */
    public static StringNode deleteChar(StringNode str, int i) {
        if (str == null) {
            throw new IllegalArgumentException("string is empty");
        } else if (i < 0) { 
            throw new IllegalArgumentException("invalid index: " + i);
        } else if (i == 0) { 
            str = str.next;
        } else {
            StringNode prevNode = getNode(str, i-1);
            if (prevNode != null && prevNode.next != null) {
                prevNode.next = prevNode.next.next;
            } else {
                throw new IllegalArgumentException("invalid index: " + i);
            }
        }

        return str;
    }

    /**
     * insertChar - inserts the character ch before the character
     * currently in position i of the specified linked-list string.
     * Returns a reference to the resulting linked-list string.
     */
    public static StringNode insertChar(StringNode str, int i, char ch) {
        StringNode newNode, prevNode;

        if (i < 0) { 
            throw new IllegalArgumentException("invalid index: " + i);
        } else if (i == 0) {
            newNode = new StringNode(ch, str);
            str = newNode;
        } else {
            prevNode = getNode(str, i - 1);
            if (prevNode != null) {
                newNode = new StringNode(ch, prevNode.next);
                prevNode.next = newNode;
            } else {
                throw new IllegalArgumentException("invalid index: " + i);
            }
        }

        return str;
    }

    /**
     * insertSorted - inserts character ch in the correct position
     * in a sorted list of characters (i.e., a sorted linked-list string)
     * and returns a reference to the resulting list.
     */
    public static StringNode insertSorted(StringNode str, char ch) {
        StringNode newNode, trail, trav;

        // Find where the character belongs.
        trail = null;
        trav = str;
        while (trav != null && trav.ch < ch) {
            trail = trav;
            trav = trav.next;
        }

        // Create and insert the new node.
        newNode = new StringNode(ch, trav);
        if (trail == null) {
            // We never advanced the prev and trav references, so
            // newNode goes at the start of the list.
            str = newNode;
        } else { 
            trail.next = newNode;
        }
            
        return str;
    }

    // length - iteratively determines the number of characters in the 
    // linked-list string to which str refers
    public static int length(StringNode str) {
    	
        int length = 0;
        
        // for each node in str, add 1 to length
        // keep adding 1 until there is no remaining node
        while (str != null) {
        	length++;
        	str = str.next;
        }
        
        return length;
        
    }

    /**
     * numOccur - find the number of occurrences of the character
     * ch in the linked list to which str refers
     */
    public static int numOccur(StringNode str, char ch) {
        if (str == null) {
            return 0;
        }
     
        int numInRest = numOccur(str.next, ch);
        if (str.ch == ch) {
            return 1 + numInRest;
        } else {
            return numInRest;
        }
    }

    /**
     * print - recursively writes the specified linked-list string to System.out
     */
    public static void print(StringNode str) {
        if (str == null) {
            return;
        } else {
            System.out.print(str.ch);
            print(str.next);
        }
    }

    // printReverse - iteratively writes the reverse of the specified 
    // linked-list string to System.out
    public static void printReverse(StringNode str) {
    	
    	// if str is null, print nothing
    	// if str is not null, do the following
    	if (str != null) {
    		// the string node previous to the one we are currently looking at
    		// it is used to maintain a reference to that node in str
    		StringNode prev = null;
    		// the string node that is the one we are currently looking at
    		// it is used to modify str
    		StringNode curr = str;
    		// the string node next to the one we are currently looking at
    		// it is used to maintain a reference to that node in str
    		StringNode next = str.next;
        
    		// first, reverse str
    		while (next != null) {
    			// let the currently checked node point at its previous node
    			curr.next = prev;
    			// update the traversal variables
    			prev = curr;
   				curr = next;
   				next = next.next;
   			} // note that after this loop, curr will point at the last node, 
    		  // but the next field in the last node will still be null
    		
    		// therefore, we update it here
   			curr.next = prev;
    	
   			// next, work from back to front to: 
   			// 1. restore the linked list to its original form
   			// 2. reversely print the element in the original linked list
   			// exchange next and prev as we are working backward
   			next = prev;
   			prev = null;
   			while (next != null) {
   				// print the current character
   				System.out.print(curr.ch);
   				// change back where curr.next is pointing to
   				curr.next = prev;
   				// update the traversal variables
   				prev = curr;
   				curr = next;
   				next = next.next;
   			} 
   			
   			// again, we need to finish the final step here
   			System.out.print(curr.ch);
   			curr.next = prev;
   			
    	}
    }
    
    /**
     * read - reads a string from an input stream and returns a
     * reference to a linked list containing the characters in the string
     */
    public static StringNode read(InputStream in) throws IOException { 
        char ch = (char)in.read();

        if (ch == '\n') {    // the string ends when we hit a newline character
            return null;         
        } else {
            StringNode restOfString = read(in);
            StringNode first = new StringNode(ch, restOfString);
            return first;
        }
    }
    

    // removeFirst - takes the linked-list string specified by str and uses 
    // recursion to remove the first occurrence (if any) of the character 
    // ch in that string.
    public static StringNode removeFirst(StringNode str, char ch) { 
    	
    	// base case 1: the linked list is empty
    	if (str == null) {
    		return null;
    		
    	// base case 2: if the character of the first node is ch, 
    	// we can simply return the linked list beginning with str.next
    	} else if (str.ch == ch) {
    		return str.next;
    	
    	// the recursive case: the character of the first node is not ch
    	// the result will be a linked list beginning with the first node 
    	// of str, but referencing to the removeFirst-ch version of sublist 
    	// str.next
    	} else {
    		StringNode result = str;
    		result.next = StringNode.removeFirst(str.next, ch);
    		return result;
    		
    	}
        
    }
    
    /*
     * toString - creates and returns the Java string that
     * the current StringNode represents.  Note that this
     * method -- unlike the others -- is a non-static method.
     * Thus, it will not work for empty strings, since they
     * are represented by a value of null, and we can't use
     * null to invoke this method.
     */
    public String toString() {
        String str = "";
        
        StringNode trav = this;   // start trav on the current node    
        while (trav != null) {
            str = str + trav.ch;
            trav = trav.next;
        }
         
        return str;
    }
    
    // toUpperCase - uses recursion to convert all of the characters in the 
    // specified linked-list string to upper case. Modifies the list itself, 
    // rather than creating a new list.
    public static void toUpperCase(StringNode str) {        
        if (str != null) {
        	str.ch = Character.toUpperCase(str.ch);
        	StringNode.toUpperCase(str.next);
        }
    } 

    public static void main(String[] args) throws IOException {
        StringNode copy, str, str1, str2, str3;
        String line;
        int n;
        char ch;

        // convert, print, and toUpperCase
        str = StringNode.convert("fine");
        System.out.print("Here's a string: "); 
        StringNode.print(str);
        System.out.println();
        System.out.print("Here it is in upper-case letters: "); 
        StringNode.toUpperCase(str);
        StringNode.print(str);
        System.out.println();
        System.out.println();

        Scanner in = new Scanner(System.in);
        
        // read, toString, length, and printReverse.
        System.out.print("Type a string: ");
        String s = in.nextLine();
        str1 = StringNode.convert(s);
        System.out.print("Your string is: "); 
        System.out.println(str1);        // implicit toString call
        System.out.print("\nHere it is reversed: ");  
        StringNode.printReverse(str1);
        System.out.println("\nIts length is " + StringNode.length(str1) + 
            " characters.");

        // charAt
        n = -1;
        while (n < 0) {
            System.out.print("\nWhat # character to get (>= 0)? ");
            n = in.nextInt();
            in.nextLine();
        }
        try {
            ch = StringNode.charAt(str1, n);
            System.out.println("That character is " + ch);
        } catch (IllegalArgumentException e) {
            System.out.println("The string is too short.");
        }
           
        // deleteChar and copy
        n = -1;
        while (n < 0) {
            System.out.print("\nWhat # character to delete (>= 0)? ");
            n = in.nextInt();
            in.nextLine();
        }
        copy = StringNode.copy(str1);
        try {
            str1 = StringNode.deleteChar(str1, n);
            StringNode.print(str1);
        } catch (IllegalArgumentException e) {
            System.out.println("The string is too short.");
        }
        System.out.print("\nUnchanged copy: ");
        StringNode.print(copy);
        System.out.println();

        // insertChar
        n = -1;
        while (n < 0) {
            System.out.print("\nWhat # character to insert before (>= 0)? ");
            n = in.nextInt();
            in.nextLine();
        }
        System.out.print("What character to insert? ");
        line = in.nextLine();
        ch = line.charAt(0);
        try {
            str1 = StringNode.insertChar(str1, n, ch);
            StringNode.print(str1);
            System.out.println();
        } catch (IllegalArgumentException e) {
            System.out.println("The string is too short.");
        }
        
        // removeFirst
        System.out.print("What character to remove the first occurrence of? ");
        line = in.nextLine();
        ch = line.charAt(0);
        try {
            str1 = StringNode.removeFirst(str1, ch);
            StringNode.print(str1);
            System.out.println();
        } catch (IllegalArgumentException e) {
            System.out.println("The string is too short.");
        }
        
        // insertSorted
        System.out.print("\nType a string of characters in alphabetical order: ");
        s = in.nextLine();
        str3 = StringNode.convert(s);
        System.out.print("What character to insert in order? ");
        line = in.nextLine();
        str3 = StringNode.insertSorted(str3, line.charAt(0));
        StringNode.print(str3);
        System.out.println();
    }
}
