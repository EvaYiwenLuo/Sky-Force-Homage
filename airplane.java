package plane;

import java.util.Random;

import javax.swing.text.html.MinimalHTMLWriter;

public class airplane extends FlyingObject{
   private int speed = 2;
   public boolean life = true;
   
   public airplane() { 
	
	   image = main.airplane;
	   width = image.getWidth(null);
	   hight = image.getHeight(null);
	   Random random = new Random();
	   x = random.nextInt(main.WIDTH - this.width);
	   y = -this.hight;
}
	@Override
	public void move() {
		// TODO Auto-generated method stub
		y+=speed;
	}

	@Override
	public boolean outofbounds() {
		// TODO Auto-generated method stub
		return this.y > main.HIGHT;
	}
 
}
