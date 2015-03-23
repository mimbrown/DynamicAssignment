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
		int arraySize = s.length() + 1;
		Node[][] m = new Node[arraySize][arraySize];
		for(int i = 0; i < arraySize; i++) {
			for(int j = 0; j < arraySize; j++) {
				m[i][j] = new Node("", 0, null, "");
			}
		}
		m[0][0] = new Node(s, 0, null, "");
		for(int i = 1; i < arraySize; i++) {
			m[i][0] = new Node(s.substring(0, s.length() - i), 0, m[i-1][0], "");
			m[0][i] = new Node(s.substring(i, s.length()), 0, m[0][i-1], "");
		}
		int isSame, up, left;
		for(int i = 1; i < arraySize; i++) {
			for(int j = 1; j < arraySize - i; j++) {
				if(isSame(m[i-1][j-1].getS())) {
					isSame = 2;
				}
				else isSame = 0;
				if(m[i-1][j].getS().length() == 1) up = 1;
				else up = 0;
				if(m[i][j-1].getS().length() == 1) left = 1;
				else left = 0;
				int diagonalOption = m[i-1][j-1].getI() + isSame;
				int upOption = m[i-1][j].getI() + up;
				int leftOption = m[i][j-1].getI() + left;
				int max = upOption;
				Node parent = m[i-1][j];
				String removed = "";
				if(up == 1) removed = m[i-1][j].getS();
				if(leftOption > max) {
					max = leftOption;
					parent = m[i][j-1];
					removed = "";
					if(left == 1) removed = m[i][j-1].getS();
				}
				if(diagonalOption > max) {
					max = diagonalOption;
					parent = m[i-1][j-1];
					removed = "";
					if(isSame == 2) removed = Character.toString(m[i-1][j-1].getS().charAt(0))
							+ Character.toString(m[i-1][j-1].getS().charAt(0));
				}
				m[i][j] = new Node(s.substring(i, s.length() - j), max, parent, removed);
			}
		}
		int max = 0;
		Node currentNode = null;
		for(int i = 0; i < arraySize; i++) {
			if(m[i][arraySize - 1 - i].getI() > max) {
				max = m[i][arraySize - 1 - i].getI();
				currentNode = m[i][arraySize - 1 - i];
			}
		} //ACGTGTCAAAATCG
		System.out.println(max);
		String toReturn = "";
		while(currentNode.getParent() != null) {
			if(currentNode.getRemoved().length() == 1) toReturn += currentNode.getRemoved();
			else if(currentNode.getRemoved().length() == 2) toReturn = currentNode.getRemoved().substring(0, 1)
					+ toReturn + currentNode.getRemoved().substring(0, 1);
			currentNode = currentNode.getParent();
		}
		for(int i = 0; i < arraySize; i++) {
			for(int j = 0; j < arraySize; j++) {
				System.out.print(m[i][j].getI() + " ");
			}
			System.out.print("\n");
		}
		for(int i = 0; i < arraySize; i++) {
			for(int j = 0; j < arraySize; j++) {
				System.out.print(m[i][j].getS() + " ");
			}
			System.out.print("\n");
		}
		return toReturn;
	}
	
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
