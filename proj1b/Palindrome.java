/**
 * @ClassName Palindrome
 * @Description TODO
 * @Author hao6
 * @Data 10/27/19 9:43 PM
 * @Version 1.0
 **/
public class Palindrome {
    public Deque<Character> wordToDeque(String word){
//        Deque<Character> returnDeque = new LinkedListDeque<>();
        Deque<Character> returnDeque = new ArrayDeque<>();
        if (word != null) {
            for (int i=0; i<word.length(); i++) {
                returnDeque.addLast(word.charAt(i));
            }
        }
        return returnDeque;
    }
    public boolean isPalindrome(String word){
        Deque<Character> wordDeque = wordToDeque(word);
        String reverseWord = "";
        for (int i = 0; i < word.length(); i++){
            reverseWord += wordDeque.removeLast();
        }
        return word.equals(reverseWord);
    }

    public boolean isPalindrome(String word, CharacterComparator cc){
        Deque<Character> wordDeque = wordToDeque(word);
        for(int i = 0; i < word.length()/2; i++){
            if ( !cc.equalChars( wordDeque.removeFirst(), wordDeque.removeLast() ) ) {
                return false;
            }
        }
        return true;
    }
}
