package trie;

import java.util.ArrayList;

/**
 * This class implements a Trie. 
 * 
 * @author Sesh Venugopal
 *
 */
public class Trie {
	
	// prevent instantiation
	private Trie() { }
	
	/**
	 * Builds a trie by inserting all words in the input array, one at a time,
	 * in sequence FROM FIRST TO LAST. (The sequence is IMPORTANT!)
	 * The words in the input array are all lower case.
	 * 
	 * @param allWords Input array of words (lowercase) to be inserted.
	 * @return Root of trie with all words inserted from the input array
	 */
	public static TrieNode buildTrie(String[] allWords) {
		TrieNode root = new TrieNode (null, null, null);
		int ero = 0;
		short serzo = (short) ero;
		int cool = allWords[0].length()-1;
		short endin = (short) cool;
		root.firstChild = nodeMaker5000(0,serzo,endin,null,null,allWords);	
		for (int i = 1; i < allWords.length; i++) {
			int sero = 0;
			short zero = (short) sero;
			int endi = allWords[i].length()-1;
			short end = (short) endi;
			TrieNode addThis = nodeMaker5000(i,zero,end, null, null, allWords);			
			traverseHorizontal(root.firstChild, addThis, allWords);
		}
		return root;
	}		
	private static void traverseHorizontal(TrieNode root, TrieNode addThis, String[] allWords) {
		TrieNode prev = null;
		TrieNode ptr = root;
		while (ptr != null) {
		if (allWords[ptr.substr.wordIndex].charAt((int)ptr.substr.startIndex) == allWords[addThis.substr.wordIndex].charAt((int)addThis.substr.startIndex)) {
			break;
			}
		else {
			prev = ptr;
			ptr = ptr.sibling;
			}
		}
		if (prev != null) {
			if (prev.sibling == null) {
			prev.sibling = addThis;
			}
			else {
				traverseVertical(ptr,addThis,allWords);
			}
		}
		else {
			traverseVertical(ptr,addThis,allWords);
		}
	}
	private static void traverseVertical(TrieNode root, TrieNode addThis, String[] allWords) {
		TrieNode ptr = root;
		short count = 0;
		short start = ptr.substr.startIndex;
		int end = ptr.substr.endIndex;
		TrieNode newbelowPtrNode = new TrieNode (null,null,null);
		while (start <= end) {
			if (allWords[ptr.substr.wordIndex].charAt(start) == allWords[addThis.substr.wordIndex].charAt(start)) {
				count++;
				start++;
			}
			else {
				break;
			}	
		}
		short newNodeEnd = ptr.substr.endIndex;
		short newNodeStart = (short) start;
		//changed count to start below
		short ptrEnd = (short) (start - 1);
		short addThisStart = (short) (start);	
		int checkptrlength = allWords[ptr.substr.wordIndex].substring(ptr.substr.startIndex, ptr.substr.endIndex +1).length();
		if (ptr.firstChild == null && ptr.sibling == null) {	
			addThis.substr.startIndex = addThisStart;
			ptr.substr.endIndex = ptrEnd;
			newbelowPtrNode = nodeMaker5000(ptr.substr.wordIndex, newNodeStart, newNodeEnd, null,null, allWords);
			ptr.firstChild = newbelowPtrNode;
			newbelowPtrNode.sibling = addThis;
		}
		else if (count == checkptrlength){
			ptr.substr.endIndex = ptrEnd;
			addThis.substr.startIndex = addThisStart;
			ptr = ptr.firstChild;
			traverseVertical(ptr, addThis, allWords);
		}	
		else if (ptr.sibling != null && count == 0){
			traverseHorizontal(ptr, addThis, allWords);
		}
		else {
			ptr.substr.endIndex = ptrEnd;
			addThis.substr.startIndex = addThisStart;   //was =count changed to addthisstart
			//newNodeStart might be different
			TrieNode temp = ptr.firstChild;
			newbelowPtrNode = nodeMaker5000(ptr.substr.wordIndex, newNodeStart, newNodeEnd, null,null,allWords);
			ptr.firstChild = newbelowPtrNode;
			newbelowPtrNode.sibling = addThis;
			newbelowPtrNode.firstChild = temp;
		}
	}
	private static TrieNode nodeMaker5000(int wordIndex, short startIndex, short endIndex, TrieNode firstChild, TrieNode sibling, String[] allWords) {
		Indexes in = new Indexes (wordIndex, startIndex, endIndex);
		TrieNode n = new TrieNode (in, firstChild, sibling);
		return n;
	}
	
	/**
	 * Given a trie, returns the "completion list" for a prefix, i.e. all the leaf nodes in the 
	 * trie whose words start with this prefix. 
	 * For instance, if the trie had the words "bear", "bull", "stock", and "bell",
	 * the completion list for prefix "b" would be the leaf nodes that hold "bear", "bull", and "bell"; 
	 * for prefix "be", the completion would be the leaf nodes that hold "bear" and "bell", 
	 * and for prefix "bell", completion would be the leaf node that holds "bell". 
	 * (The last example shows that an input prefix can be an entire word.) 
	 * The order of returned leaf nodes DOES NOT MATTER. So, for prefix "be",
	 * the returned list of leaf nodes can be either hold [bear,bell] or [bell,bear].
	 *
	 * @param root Root of Trie that stores all words to search on for completion lists
	 * @param allWords Array of words that have been inserted into the trie
	 * @param prefix Prefix to be completed with words in trie
	 * @return List of all leaf nodes in trie that hold words that start with the prefix, 
	 * 			order of leaf nodes does not matter.
	 *         If there is no word in the tree that has this prefix, null is returned.
	 */
	public static ArrayList<TrieNode> completionList(TrieNode root, String[] allWords, String prefix) {
		ArrayList<TrieNode> result = new ArrayList<TrieNode>();
		int movealonger = 0;
		TrieNode ptr = root.firstChild;
		traverseHorizontal (ptr,prefix,allWords, result, movealonger);
		return result;
	}
	
	private static void traverseHorizontal(TrieNode root, String prefix, String[] allWords, ArrayList<TrieNode> result, int movealonger){
        TrieNode ptr = root;
        while (ptr != null) {
            if (allWords[ptr.substr.wordIndex].charAt(ptr.substr.startIndex) == prefix.charAt(ptr.substr.startIndex)) {
                break;
            }
            else {
                ptr = ptr.sibling;
            }
        }    
        traverseVertical(ptr, prefix, allWords, result, movealonger);
    }
    
    private static void traverseVertical (TrieNode root, String prefix, String [] allWords, ArrayList<TrieNode> result, int movealonger) {
        int initializercheck = movealonger;
        TrieNode ptr = root;
        int ptrtracker = (ptr.substr.endIndex - ptr.substr.startIndex) + 1;
        int checkchar = ptr.substr.startIndex;
        
        while (movealonger != prefix.length() && ptrtracker > 0) {
            if (allWords[ptr.substr.wordIndex].charAt(checkchar) == prefix.charAt(checkchar)) {
                ptrtracker --;
                movealonger++;
                checkchar ++;
            }
            else {
                break;
            }
        }
        
        if (movealonger < prefix.length() && ptr.sibling != null && movealonger == initializercheck) {
            ptr = ptr.sibling;
            traverseHorizontal(ptr, prefix, allWords, result, movealonger);
        }
         else if (movealonger < prefix.length() && ptr.firstChild != null) {
            ptr = ptr.firstChild;
            traverseVertical (ptr, prefix, allWords, result, movealonger);
            }
         else if (movealonger == prefix.length()) {
             nodeAdder5000(ptr, result, prefix, allWords);
        }
    }
    
    private static void nodeAdder5000(TrieNode root, ArrayList<TrieNode> result, String prefix, String[] allWords) {
        TrieNode ptr = root;
        if (prefix != null) {
        if (prefix.equals(allWords[ptr.substr.wordIndex])) {
        	result.add(ptr);
        	return;
        }
        }
        if (ptr.firstChild != null) {
            ptr = ptr.firstChild;
        }
        if (ptr.firstChild == null) {
            result.add(ptr);
        }
        if (ptr.firstChild != null){
            nodeAdder5000(ptr.firstChild, result, null, allWords);
        }
        
        if (ptr.sibling != null) {
            nodeAdder5000(ptr.sibling, result, null, allWords);
        }
    }
	
	public static void print(TrieNode root, String[] allWords) {
		System.out.println("\nTRIE\n");
		print(root, 1, allWords);
	}
	
	private static void print(TrieNode root, int indent, String[] words) {
		if (root == null) {
			return;
		}
		for (int i=0; i < indent-1; i++) {
			System.out.print("    ");
		}
		
		if (root.substr != null) {
			String pre = words[root.substr.wordIndex]
							.substring(0, root.substr.endIndex+1);
			System.out.println("      " + pre);
		}
		
		for (int i=0; i < indent-1; i++) {
			System.out.print("    ");
		}
		System.out.print(" ---");
		if (root.substr == null) {
			System.out.println("root");
		} else {
			System.out.println(root.substr);
		}
		
		for (TrieNode ptr=root.firstChild; ptr != null; ptr=ptr.sibling) {
			for (int i=0; i < indent-1; i++) {
				System.out.print("    ");
			}
			System.out.println("     |");
			print(ptr, indent+1, words);
		}
	}
 }
