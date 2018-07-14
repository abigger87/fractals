import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Color;

public class mandelbrot{
    
    public static void main(String[] args){
        mandelbrot test = new mandelbrot();
        test.drawMandelbrot(10000, 12000, 1000000);
    }

    public void drawMandelbrot(int height, int width, int max){
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        int black = 0x000000; 
        int[] colors = new int[max];
        for (int i = 0; i<max; i++) {
            colors[i] = Color.HSBtoRGB(i/256f, 1, i/(i+8f));
        }
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                double c_re = (col - width/2.0)*4.0/width;
                double c_im = (row - height/2.0)*4.0/width;
                double x = 0, y = 0;
                int iteration = 0;
                while (x*x+y*y <= 4 && iteration < max) {
                    double x_new = x*x - y*y + c_re;
                    y = 2*x*y + c_im;
                    x = x_new;
                    iteration++;
                }
                if (iteration < max) image.setRGB(col, row, colors[iteration]);
                else image.setRGB(col, row, black);
            }
        }
        try{
            ImageIO.write(image, "png", new File("mandelbrot.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}