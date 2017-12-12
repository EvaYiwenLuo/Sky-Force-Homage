package plane;

import java.util.Random;

public class bee extends FlyingObject implements Award{
   private int xspeed = 1;
   private int yspeed = 2;
   private int awardtype;
   public bee() {
	// TODO Auto-generated constructor stub
    image = main.bee;
    width = image.getWidth(null);
    hight = image.getHeight(null);
    Random random = new Random();
    this.x = random.nextInt(main.WIDTH - this.width);
    this.y = -this.hight;
    awardtype = random.nextInt(2);
   }
	@Override
	public void move() {
		// TODO Auto-generated method stub
		if(x > main.WIDTH - width)
		{
			xspeed = -1;
		}
		else
		{
			xspeed = 1;
		}
		x += xspeed;
		y += yspeed;
	}

	@Override
	public boolean outofbounds() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public int getType() {
		// TODO Auto-generated method stub
		return awardtype;
	}

}
