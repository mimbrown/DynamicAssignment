import java.util.*;


public class Demo {
	
	public static String findPalRecurs(String s) {
		if(s.length() == 0 || s.length() == 1) return s;
		int front = 0, back = s.length()-1;
		if(s.charAt(front) == s.charAt(back)) {
			return Character.toString(s.charAt(front)) +
					findPalRecurs(s.substring(front + 1, back)) + Character.toString(s.charAt(back));
		}
		List<String> possibles = new ArrayList<String>(s.length());
		possibles.add(findPalRecurs(s.substring(front + 1, back + 1)));
		possibles.add(findPalRecurs(s.substring(front, back)));
		String longestPal = "";
		for(String c : possibles) {
			if(c.length() > longestPal.length()) longestPal = c;
		}
		return longestPal;
	}
	
	public static String findPalDynam(String s) {
		if(s.length() < 2) return s; // no need to do any work if this is the case
		// now we set up the 2x2 matrix we will use to store intermediate results
		int arraySize = s.length() + 1;
		Node[][] m = new Node[arraySize][arraySize];
		// initialize the first row and column
		m[0][0] = new Node(s, 0, null, "");
		for(int i = 1; i < arraySize - 1; i++) {
			m[i][0] = new Node(s.substring(0, s.length() - i), 0, m[i-1][0], "");
			m[0][i] = new Node(s.substring(i, s.length()), 0, m[0][i-1], "");
		}
		m[arraySize - 1][0] = new Node("", 1, m[arraySize - 2][0], s.substring(0,1));
		m[0][arraySize - 1] = new Node("", 1, m[0][arraySize - 2], s.substring(s.length() - 1, s.length()));
		// this will traverse through the matrix, filling in the intermediate results
		int isSame, up, left; // these will store how many characters we are adding to 
		                      // the palindrome in a single step
		int diagonalOption, upOption, leftOption; // these will store the total characters now
												  // in the palindrome. We maximize this.
		for(int i = 1; i < arraySize - 1; i++) {
			for(int j = 1; j < arraySize - i; j++) {
				if(isSame(m[i-1][j-1].getS())) {
					isSame = 2;
				}
				else 
				{
					isSame = 0;
				}
				if(m[i-1][j].getS().length() == 1) 
				{
					up = 1;
				}	
				else 
				{
					up = 0;
				}
				if(m[i][j-1].getS().length() == 1) 
				{
					left = 1;
				}
				else 
				{
					left = 0;
				}
				
				diagonalOption = m[i-1][j-1].getI() + isSame;
				upOption = m[i-1][j].getI() + up;
				leftOption = m[i][j-1].getI() + left;
				// now choose the maximum of our three options
				int max = upOption;
				Node parent = m[i-1][j];
				String removed = "";
				
				if(up == 1) 
				{
					removed = m[i-1][j].getS();
				}
				if(leftOption > max) 
				{
					max = leftOption;
					parent = m[i][j-1];
					removed = "";
					if(left == 1) 
					{
						removed = m[i][j-1].getS();
					}
				}
				if(diagonalOption > max) {
					max = diagonalOption;
					parent = m[i-1][j-1];
					removed = "";
					if(isSame == 2) 
					{
						removed = Character.toString(m[i-1][j-1].getS().charAt(0))
								+ Character.toString(m[i-1][j-1].getS().charAt(0));
					}
				}
				// and finally put in a new node that maximizes what we want
				m[i][j] = new Node(s.substring(j, s.length() - i), max, parent, removed);
			}
		}
		// now find the maximum size palindrome from the last elements in each row
		int max = 0;
		Node currentNode = null;
		for(int i = 0; i < arraySize; i++) {
			if(m[i][arraySize - 1 - i].getI() > max) {
				max = m[i][arraySize - 1 - i].getI();
				currentNode = m[i][arraySize - 1 - i];
			}
		} //ACGTGTCAAAATCG for testing purposes
		System.out.println(max);
		// finally, trace back through the parent pointers and recreate the palindrome
		String toReturn = "";
		if(currentNode != null) {
			while(currentNode.getParent() != null) {
				
				if(currentNode.getRemoved().length() == 1) toReturn += currentNode.getRemoved();
				else if(currentNode.getRemoved().length() == 2)
				{
					toReturn = currentNode.getRemoved().substring(0, 1)
							+ toReturn + currentNode.getRemoved().substring(0, 1);
				}
				currentNode = currentNode.getParent();
			}
		}
		// print out the resulting matrix in a couple different forms
		for(int i = 0; i < arraySize; i++) {
			for(int j = 0; j < arraySize - i; j++) {
				System.out.print(m[i][j].getI() + " ");
			}
			System.out.print("\n");
		}
		for(int i = 0; i < arraySize; i++) {
			for(int j = 0; j < arraySize - i; j++) {
				System.out.print(m[i][j].getS() + " ");
			}
			System.out.print("\n");
		}
		return toReturn;
	}
	
	// returns true if the first and last characters are the same, false otherwise
	public static boolean isSame(String s) {
		return s.charAt(0) == s.charAt(s.length()-1);
	}
	
	public static String remove(String s, int i) {
		String toReturn = "";
		for(int j = 0; j < s.length(); j++) {
			if(j != i) {
				toReturn += s.charAt(j);
			}
		}
		return toReturn;
	}
	
	public static boolean isPalindrome(String s) {
		int length = s.length();
		for(int i = 0; i < length/2; i++) {
			if(s.charAt(i) != s.charAt(length - i - 1)) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		Scanner reader = new Scanner(System.in);
		System.out.print("Enter a string: ");
		String s = reader.nextLine();
		long t1 = System.currentTimeMillis();
		//System.out.println(findPalRecurs(s));
		System.out.println(findPalDynam(s));
		long t2 = System.currentTimeMillis();
		System.out.println((t2 - t1)/1000.0);
		reader.close();
	}

}
