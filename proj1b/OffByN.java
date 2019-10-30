/**
 * @ClassName OffByN
 * @Description TODO
 * @Author hao6
 * @Data 10/28/19 8:36 PM
 * @Version 1.0
 **/
public class OffByN implements CharacterComparator {
    private int off;
    public OffByN(int N){
        off = N;
    }
    @Override
    public boolean equalChars(char x, char y){
        return (x - y) == off || (y - x) == off;
    }
}
