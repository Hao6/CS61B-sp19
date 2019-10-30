/**
 * @ClassName OffByOne
 * @Description TODO
 * @Author hao6
 * @Data 10/28/19 8:23 PM
 * @Version 1.0
 **/
public class OffByOne implements CharacterComparator{
    @Override
    public boolean equalChars(char x, char y){
        return (x - y) == 1 || (y - x) == 1;
    }
}
