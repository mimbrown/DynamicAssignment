import java.util.*;


public class Demo {
	
	public static String findPalRecurs(String s) {
		/*if(isPalindrome(s)) {
			return s;
		}
		String copy, longestPal = "";
		List<String> possibles = new ArrayList<String>(s.length());
		for(int i = 0; i < s.length(); i++) {
			copy = remove(s, i);
			possibles.add(i, findPalRecurs(copy));
		}
		for(String c : possibles) {
			if(c.length() > longestPal.length()) longestPal = c;
		}
		return longestPal;*/
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
		return null;
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
		System.out.println(findPalRecurs(s));
		//System.out.println(findPalDynam(s));
		long t2 = System.currentTimeMillis();
		System.out.println((t2 - t1)/1000.0);
		reader.close();
	}

}
