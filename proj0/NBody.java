public class NBody {
    public static double readRadius(String fileName){
        In in = new In(fileName);
        in.readInt();
        double radius = in.readDouble();
        return radius;
    }
    public static Body[] readBodies(String fileName){
        In in = new In(fileName);
        int bodyNums = in.readInt();
        in.readDouble();
        Body[] bodyArr = new Body[bodyNums];
        String path = "images/"; 
        for(int i=0;i<bodyNums;i++){
            bodyArr[i] = new Body(in.readDouble(), in.readDouble(), in.readDouble(),
                    in.readDouble(), in.readDouble(), path+in.readString());
        }
        return bodyArr;
    }
    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);

        String fileName = args[2];

        double radius = readRadius(fileName);
        Body[] bodyArr = readBodies(fileName);

        StdDraw.enableDoubleBuffering();
        
        double initTime = 0;

        StdAudio.play("audio/2001.mid");

/*        
double freq = 440.0D;

        for(int i = 0; i <= 44100; ++i) {
            StdAudio.play(0.5D * Math.sin(6.283185307179586D * freq * (double)i / 44100.0D));
        }*/
	
	
        while (initTime < T){
            double []xForces = new double[bodyArr.length];
            double []yForces = new double[bodyArr.length];
            for(int i=0;i<bodyArr.length;i++) {
                xForces[i] = bodyArr[i].calcNetForceExertedByX(bodyArr);
                yForces[i] = bodyArr[i].calcNetForceExertedByY(bodyArr);
            }
            for(int i=0;i<bodyArr.length;i++) {
                bodyArr[i].update(dt, xForces[i], yForces[i]);
            }

            // Drawing the Background
            StdDraw.setScale(-radius, radius);
            String background = "images/starfield.jpg";
            StdDraw.picture(0, 0, background);

            for(Body b : bodyArr){
                b.draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
            
            initTime += dt;
        }
	StdAudio.close();
        //System.exit(0);

        StdOut.printf("%d\n", bodyArr.length);
        StdOut.printf("%.2e\n", radius);
        for(int i =0; i<bodyArr.length;i++){
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %12s\n",
                    bodyArr[i].xxPos, bodyArr[i].yyPos, bodyArr[i].xxVel, 
                    bodyArr[i].yyVel, bodyArr[i].imgFileName);
        }

    }
}

