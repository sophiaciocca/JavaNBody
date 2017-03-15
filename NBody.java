/**********************************************
  * Name: Sophia Ciocca
  * PennKey: sciocca
  * Recitation: 211
  * 
  * Compilation: javac NBody.java
  * Execution: java NBody
  * 
  * Creates an animation of four planets orbiting the sun.
  * 
  * % java NBody 60000.0 25000.0 planets.txt 
  * 5
  * 2.50e+11
  * 1.4958e+11  2.2349e+09 -4.4460e+02  2.9798e+04  5.9740e+24    earth.gif
  * 2.2789e+11  1.8075e+09 -1.9158e+02  2.4099e+04  6.4190e+23     mars.gif
  * 5.7752e+10  3.5905e+09 -2.9682e+03  4.7839e+04  3.3020e+23  mercury.gif
  * 1.9852e+02  1.1280e+00  3.9705e-03  3.3841e-05  1.9890e+30      sun.gif
  * 1.0816e+11  2.6248e+09 -8.4989e+02  3.4993e+04  4.8690e+24    venus.gif

  **********************************************/

public class NBody {
    public static void main(String[] args) {
        
        // error check the arguments
        if (args.length != 3) {
            System.err.println("Usage:  java NBody <filename>");
            System.exit(1);
        }
        
        //reading in the planets input
        String filename = args[2]; //changes variable filename to the file name the user enters
        In inStream = new In(filename); //creates a variable inStream we can use to read from the file
        
        //reading in the arguments N & R from user:
        int N = inStream.readInt();
        double R = inStream.readDouble();
        
        // initialize four parallel arrays
        double[] px = new double[N];
        double[] py = new double[N];
        double[] vx = new double[N];
        double[] vy = new double[N];
        double[] m = new double[N];
        String[] image = new String[N];
        
        // read in the data from the file, organize into px, py, vx, fy, m, image
        for (int i = 0; i < N; i++) {
            px[i] = inStream.readDouble();
            py[i] = inStream.readDouble();
            vx[i] = inStream.readDouble();
            vy[i] = inStream.readDouble();
            m[i] = inStream.readDouble();
            image[i] = inStream.readString();
            StdDraw.picture(px[i], py[i], image[i]);
            
        }  
        
        //PART TWO: DRAWING THE PLANETS
        
        //set scale of window to show entire universe
        StdDraw.setXscale(-R, R);
        StdDraw.setYscale(-R, R);
        StdDraw.picture(0, 0, "starfield.jpg");
        double G = 6.67e-11;
        
        //get time arguments
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        
        //here's the Time Loop:
        for (double j = 0; j <= T; j+=dt) {
            
            //for loop to go through and focus on each body
            for (int k = 0; k < N; k++) {
                
                double ax = 0;
                double ay = 0;
                
                //for loop to go through the OTHER bodies
                for (int l = 0; l < N; l++) {
                    
                    //if statement, to avoid calculating planet v. itself
                    if (k != l) {
                        
                        //the physics equations are here:
                        double dx = px[l] - px[k];
                        double dy = py[l] - py[k];
                        
                        double d = Math.sqrt(dx*dx + dy*dy);
                        
                        double F = G * (m[k] * m[l]) / (d * d);
                        double Fx = (F * dx / d); 
                        double Fy = (F * dy / d);
                        
                        //defining & updating acceleration
                        ax += Fx / m[k];
                        ay += Fy / m[k];               
                        
                    }                 
                }
                
                //updating the velocities
                vx[k] = vx[k] + dt * ax;
                vy[k] = vy[k] + dt * ay;
                
            }
            
            
            //show the N bodies - plot their positions
            for (int i = 0; i < N; i++) { 
                px[i] = px[i] + vx[i]*dt;
                py[i] = py[i] + vy[i]*dt;
                StdDraw.picture(px[i], py[i], image[i]);
            }
           
            //show / background
            StdDraw.show(20);
            StdDraw.picture(0, 0, "starfield.jpg");        
            
        }
        
        //print out results
        System.out.printf("%d\n", N);
        System.out.printf("%.2e\n", R);
        for (int i = 0; i < N; i++) {
            System.out.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                              px[i], py[i], vx[i], vy[i], m[i], image[i]);
        }
        
        
    }
}