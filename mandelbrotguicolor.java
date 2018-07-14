import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;
import java.util.*;

public class mandelbrotguicolor extends JPanel implements MouseInputListener, KeyListener{
    
    private static final long serialVersionUID = 1999L;
    private BufferedImage image; 
    private double[] center = {0.0,0.0};
    private double scale = 200.0;
	private double scaleRate = 0.5; 
	private int panningRate = 25;
	private int maxIterations = 1000;

	public mandelbrotguicolor() {
		addMouseListener(this);
		addKeyListener(this);
	};

	public static void main(String[] args) {
		mandelbrotguicolor mymandelbrot = new mandelbrotguicolor();
		JFrame frame = new JFrame("Mandelbrot");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(mymandelbrot);
		frame.setSize(1000, 1000);
		frame.setVisible(true);        
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int width = getWidth();
		int height = getHeight();
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		int black = 0x000000; 
		int[] colors = new int[maxIterations];
        for (int i = 0; i < maxIterations; i++) {
            colors[i] = Color.HSBtoRGB(i/256f, 1, i/(i+8f));
        }
		double cr,ci;
		double zr,zi;
		double zR,zI;
		int i,j,iterator; 
		double value;
		for(i = 0; i < width; i++){
			for(j = 0; j < height; j++){
				cr = (i-0.5*width)/scale + center[0];
				ci = -(j-0.5*height)/scale + center[1];
				zr = 0.0; zi = 0.0;
				for(iterator = 0; iterator < maxIterations; iterator++){
					zR = zr*zr - zi*zi + cr;
					zI = 2*zr*zi + ci;
					zi = zI; 
					zr = zR;
					if(zi*zi + zr*zr > 4.0){
						break;
					} 
				}
				if(iterator == maxIterations){
					value = 0.0;
				}
				else{ //http://linas.org/art-gallery/escape/escape.html
					iterator++; 
					zR = zr*zr - zi*zi + cr; 
					zI = 2*zr*zi + ci;	
					zi = zI; 
					zr = zR;
					iterator++; 
					zR = zr*zr - zi*zi + cr; 
					zI = 2*zr*zi + ci;	
					zi = zI; 
					zr = zR;
					value = Math.sqrt(zi*zi + zr*zr);
					value = iterator - (Math.log(Math.log(value))/Math.log(2.0));
					value = (value)/((double)(maxIterations));
				}
				if(iterator < maxIterations){
					image.setRGB(i, j, colors[iterator]);
				}
                else{
					image.setRGB(i, j, black);
				}	
			}
		}
		g.drawImage(image, 0,0, null);
	}

	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {
		int width = getWidth();
		int height = getHeight();
		double newscale;
		if (e.getButton() == 1){ 
			newscale = scale*(1.0+scaleRate);
		} else {
			newscale = scale*(1.0-scaleRate);
		}
		center[0] = (e.getX()-0.5*width)*(1/scale - 1/newscale) + center[0];
		center[1] = -(e.getY()-0.5*height)*(1/scale - 1/newscale) + center[1];
		scale = newscale;
		repaint();
	}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseDragged(MouseEvent e) {}
	@Override
	public void mouseMoved(MouseEvent e) {}
	@Override
	public void keyPressed(KeyEvent e) {		
		switch(e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			center[0] -= ((double)(panningRate))/scale;
			break;
		case KeyEvent.VK_RIGHT:
			center[0] += ((double)(panningRate))/scale;
			break;	
		case KeyEvent.VK_UP:
			center[1] -= ((double)(panningRate))/scale;
			break;
		case KeyEvent.VK_DOWN:
			center[1] += ((double)(panningRate))/scale;
			break;
		default:
		}}
	@Override
	public void keyReleased(KeyEvent e) {}
	@Override
	public void keyTyped(KeyEvent e) {}
}