import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import java.util.*;

public class dragoncurve extends JPanel {
    
    private static final long serialVersionUID = 1999L;
    private int lines;
    private int lineDiff;
    private int avgwidth;
    private int avgheight;

	public dragoncurve(int mylines, int mylineDiff) {
        lines = mylines;
        lineDiff = mylineDiff;
	}

	public static void main(String[] args) {
		dragoncurve mymandelbrot = new dragoncurve(15, 15);
        JFrame frame = new JFrame("Mandelbrot");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(mymandelbrot);
        frame.setSize(1000, 1000);
        //frame.pack();
        frame.setLocationByPlatform( true );
		frame.setVisible(true); 
	}

    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension((lines * lineDiff), (lines * lineDiff));
    }

    @Override
	public void paintComponent(Graphics g) {
		int width = getWidth();
        int height = getHeight();
        int x = lineDiff;
        int y = 0;
        for (int i = 0; i < lines; i++)
        {
            g.drawLine(x, x, y, y);
            x += lineDiff;
            y += lineDiff;
        }
        x = 0;
        y = height - lineDiff;

        for (int i = 0; i < lines; i++)
        {
            g.drawLine(x, height, width, y);
            x += lineDiff;
            y -= lineDiff;
        }
    }
    
    private void painting(int x, int y){
        //rotate 90
        if(x < avgheight && y < avgheight){
            avgheight = (avgheight + x)/x;
            drawLine(x);
            painting(x+1, y+1);

        }
        else{

        }
    }

}