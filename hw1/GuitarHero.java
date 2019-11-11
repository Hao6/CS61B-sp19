import es.datastructur.synthesizer.GuitarString;
/**
 * @ClassName GuitarHero
 * @Description TODO
 * @Author hao6
 * @Data 10/30/19 7:46 PM
 * @Version 1.0
 **/
public class GuitarHero {
    private static String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";

    private static double CONCERT_START = 440.0;
    private static double[] CONCERT = new double[keyboard.length()];


    public static void main(String[] args) {
        for (int i = 0; i < keyboard.length(); i += 1) {
            CONCERT[i] = CONCERT_START * Math.pow(2, (i - 24) / 12.0);
        }
        /* create two guitar strings, for concert A and C */
        GuitarString[] string = new GuitarString[keyboard.length()];
        for (int i = 0; i < keyboard.length(); i += 1) {
            string[i] = new GuitarString(CONCERT[i]);
        }

        while (true) {

            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                int keyToIndex = keyboard.indexOf(key);
                if (keyToIndex != -1) {
                    string[keyToIndex].pluck();
                }
            }

            /* compute the superposition of samples */
            double sample = 0;
            for (int i = 0; i < keyboard.length(); i += 1) {
                sample += string[i].sample();
            }

            /* play the sample on standard audio */
            StdAudio.play(sample);

            /* advance the simulation of each guitar string by one step */
            for (int i = 0; i < keyboard.length(); i += 1) {
                string[i].tic();
            }
        }
    }
}
