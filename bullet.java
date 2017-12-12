package plane;

public class bullet extends FlyingObject{
    private int speed = 3;
    public bullet(int x,int y) {
		// TODO Auto-generated constructor stub
    	this.x = x;
    	this.y = y;
    	image = main.bullet;
    	width = image.getWidth(null);
    	hight = image.getHeight(null);
	}
	@Override
	public void move() {
		// TODO Auto-generated method stub
		y -= speed;
	}

	@Override
	public boolean outofbounds() {
		// TODO Auto-generated method stub
		return this.y < this.hight;
	}
    
}
