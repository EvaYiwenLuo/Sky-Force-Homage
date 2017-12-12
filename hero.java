package plane;

import java.awt.image.BufferedImage;

import javax.net.ssl.X509ExtendedKeyManager;
import javax.swing.text.Highlighter.Highlight;

public class hero extends FlyingObject{
      private int life = 3;
      public int getLife() {
		return life;
	}
	public void setLife(int life) {
		this.life = life;
	}
	private int doublefile = 0;
      public int getDoublefile() {
		return doublefile;
	}
	public void setDoublefile(int doublefile) {
		this.doublefile = 0;
	}
	private int index = 0;
      private BufferedImage[] images = null;
      public void addDoubleFire(){
      	doublefile += 40;
      }
      public hero()
      {
    	  image = main.hero0;
    	  width = image.getWidth(null);
    	  hight = image.getHeight(null);
    	  x = 150;
    	  y = 400;
    	  life = 3;
    	  doublefile = 0;
    	  images = new BufferedImage[]{main.hero0,main.hero1};
    	  index = 0;
      }

	@Override
	public void move() {
		// TODO Auto-generated method stub
		image = images[index++/10%images.length];
	}
    public void moveto(int x,int y)
    {
    	this.x = x - width/2;
    	this.y = y - hight/2;
    }
    public bullet[] shoot()
    {   int xstep = width/4;
    	if(this.doublefile > 0)
    	{
    		bullet[] bullets = new bullet[2];
    		bullets[0] = new bullet(this.x+1*xstep, this.y-20);
    		bullets[1] = new bullet(this.x+3*xstep, this.y-20);
    		doublefile-=2;
    		return bullets;
    	}
    	else
    	{
    		bullet[] bullets = new bullet[1];
    		bullets[0] = new bullet(this.x + 2 * xstep,this.y - 20);
    		return bullets;
    	}
    }
    public void addlife() {
		// TODO Auto-generated method stub
         life++;
	}
    public boolean hit(FlyingObject other)
    {
       	int x1 = other.x - this.width/2;
    	int x2 = other.x + other.width + this.width/2;
    	int y1 = other.y - this.hight/2;
    	int y2 = other.y + other.hight + this.hight/2;
    	int hx = this.x + this.width/2;
    	int hy = this.y + this.hight/2;
    	return hx > x1 && hx < x2
    			&&
    		   hy > y1 && hy < y2;
    }
    public void reduselife()
    {
    	life--;
    }
	@Override
	public boolean outofbounds() {
		// TODO Auto-generated method stub
		return false;
	}
}
