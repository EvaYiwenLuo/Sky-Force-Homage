package plane;

import java.awt.Image;

import javax.imageio.ImageTypeSpecifier;

public abstract class FlyingObject implements Runnable {
	protected int x = 0;
	protected int y = 0;
	protected int width = 0;
	protected int hight = 0;
	public int mini = 10;
	protected Image image = null;

	public abstract void move();

	public abstract boolean outofbounds();

	public boolean shootby(bullet bullet) {
		int x1 = this.x;
		int x2 = this.x + this.width;
		int y1 = this.y;
		int y2 = this.y + this.hight;
		int x = bullet.x;
		int y = bullet.y;
		return x > x1 && x < x2 && y > y1 && y < y2;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mini--;
		}
	}

}
