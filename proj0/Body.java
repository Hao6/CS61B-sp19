public class Body {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;

    public Body(double xP, double yP, double xV, double yV, double m, String img){
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Body(Body b){
        xxPos = b.xxPos;
        yyPos = b.yyPos;
        xxVel = b.xxVel;
        yyVel = b.yyVel;
        mass = b.mass;
        imgFileName = b.imgFileName;
    }
    public double calcDistance(Body b){
        double dx = xxPos - b.xxPos;
        double dy = yyPos - b.yyPos;
        return Math.sqrt(dx*dx+dy*dy);
    }
    public double calcForceExertedBy(Body b){
        double gPara = 6.67e-11;
        double distance = calcDistance(b);
        return gPara*mass*b.mass / (distance*distance);
    }
    
    public double calcForceExertedByX(Body b){
        double dx = b.xxPos - xxPos;
        return calcForceExertedBy(b)*dx / calcDistance(b);
    }

    public double calcForceExertedByY(Body b){
        double dy = b.yyPos - yyPos;
        return calcForceExertedBy(b)*dy / calcDistance(b);
    }
    public double calcNetForceExertedByX(Body[] allBodys){
        double netForceExertedByX = 0;
        for(Body b : allBodys){
            if(!this.equals(b)){
                netForceExertedByX += calcForceExertedByX(b);
            }
        }
        return netForceExertedByX;
    }

    public double calcNetForceExertedByY(Body[] allBodys){
        double netForceExertedByY = 0;
        for(Body b : allBodys){
            if(!this.equals(b)){
                netForceExertedByY += calcForceExertedByY(b);
            }
        }
        return netForceExertedByY;
    }
    public void update(double times, double forceExertedByX, double forceExertedByY){
        double accelerationX = forceExertedByX / mass;
        double accelerationY = forceExertedByY / mass;
        
        xxVel += accelerationX*times;
        yyVel += accelerationY*times;
        
        xxPos += xxVel*times;
        yyPos += yyVel*times;
    }
    public void draw(){
        StdDraw.picture(xxPos, yyPos, imgFileName);
    }
    
}
