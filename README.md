# DynamicAssignment

The general idea for this first recursive approach is to
1) return the string if it is a palindrome, or
2) for every character in the string, recursively call the function with the string minus that character
and store the result in a list of possibilities.
Then, the maximum length palindrome is taken from the list of possibilities, and returned.
