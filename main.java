package plane;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.Thread.State;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.print.attribute.standard.Media;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import org.omg.CORBA.TRANSACTION_MODE;

public class main extends JPanel {
	public static final int WIDTH = 400;
	public static final int HIGHT = 654;
	public static BufferedImage background = null;
	public static BufferedImage start = null;
	public static BufferedImage pause = null;
	public static BufferedImage gameover = null;
	public static BufferedImage airplane = null;
	public static BufferedImage bee = null;
	public static BufferedImage bullet = null;
	public static BufferedImage hero0 = null;
	public static BufferedImage hero1 = null;
	public static final int START = 0;
	public static final int PAUSE = 2;
	public static final int RUNNING = 1;
	public static final int GAMEOVER = 3;
	public static BufferedImage bang1 = null;
	public static BufferedImage bang2 = null;
	public static BufferedImage bang3 = null;
	public int state = 0;

	private hero hero = new hero();
	private FlyingObject[] flyingObjects = {};
	private bullet[] bullets = {};
     private ArrayList<FlyingObject> flyingObjects2 = new ArrayList<>();
	public Timer timer;
	private int intervel = 10; 

	static
	{
		try {
			background = ImageIO.read(new File("images//background.png"));
			start = ImageIO.read(new File("images//start.png"));
			pause = ImageIO.read(new File("images//pause.png"));
			gameover = ImageIO.read(new File("images//gameover.png"));
			airplane = ImageIO.read(new File("images//airplane.png"));
			bee = ImageIO.read(new File("images//bee.png"));
			bullet = ImageIO.read(new File("images//bullet.png"));
			hero0 = ImageIO.read(new File("images//hero0.png"));
			hero1 = ImageIO.read(new File("images//hero1.png"));
			bang1 = ImageIO.read(new File("images//explosion.png"));
			bang2 = ImageIO.read(new File("images//enemyplanebullet.png"));
			bang3 = ImageIO.read(new File("images//enemyplanebullet (2).png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void paintbang(Graphics graphics)
	{
		for(int i = 0; i <flyingObjects2.size(); i++)
		{
			FlyingObject flyingObject = flyingObjects2.get(i);
			if(flyingObject.mini > 6)
			{
				graphics.drawImage(bang1, flyingObject.x+flyingObject.image.getWidth(null)/2, flyingObject.y+flyingObject.image.getHeight(null)/2,null );
			}
			if(flyingObject.mini > 3)
			{
				graphics.drawImage(bang2, flyingObject.x+flyingObject.image.getWidth(null)/2, flyingObject.y+flyingObject.image.getHeight(null)/2,null );
			}
			if(flyingObject.mini > 0)
			{
				graphics.drawImage(bang3, flyingObject.x+flyingObject.image.getWidth(null)/2, flyingObject.y+flyingObject.image.getHeight(null)/2,null );
			}
			if(flyingObject.mini <= 0)
			{
				flyingObjects2.remove(flyingObject);
			}
		}
	}
	int flyindex = 0;

	public void enteraction() {
		flyindex++;
		if (flyindex % 40 == 0) {
			FlyingObject flyingObject = nextone();
			flyingObjects = Arrays.copyOf(flyingObjects, flyingObjects.length + 1);
			flyingObjects[flyingObjects.length - 1] = flyingObject;

		}
	}

	int shootindex = 0;

	public void shootaction() {
		shootindex++;
		if (shootindex % 20 == 0)
		{
			bullet[] bs = hero.shoot();
			bullets = Arrays.copyOf(bullets, bullets.length + bs.length);
			System.arraycopy(bs, 0, bullets, bullets.length - bs.length, bs.length);
		}
	}

	public void stepaction() {
		hero.move();
		for (FlyingObject flyingObject : flyingObjects) {
			flyingObject.move();
		}
		for (bullet bullet : bullets) {
			bullet.move();
		}

	}

	public void outOfBoundsAction() {
		int index = 0;
		FlyingObject[] flyingLives = new FlyingObject[flyingObjects.length];
		for (int i = 0; i < flyingObjects.length; i++) {
			FlyingObject f = flyingObjects[i];
			if (!f.outofbounds()) {
				flyingLives[index] = f;
				index++;
			}
		}
		flyingObjects = Arrays.copyOf(flyingLives, index);

		index = 0;
		bullet[] bulletsLives = new bullet[bullets.length];
		for (int i = 0; i < bullets.length; i++) {
			bullet bs = bullets[i];
			if (!bs.outofbounds()) {
				bulletsLives[index] = bs;
				index++;
			}
		}
		bullets = Arrays.copyOf(bulletsLives, index);

	}

	public FlyingObject nextone()
	{
		Random random = new Random();
		int type = random.nextInt(20);
		if (type == 0)
		{
			return new bee();
		} else {
			return new airplane();
		}
	}

	public void action()
	{
		MouseAdapter mouseAdapter = new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				if (state == RUNNING) {
					int x = e.getX();
					int y = e.getY();
					hero.moveto(x, y);
					System.out.println("����action����ƶ��¼�");
				}
			}

			@Override
			public void mouseClicked(MouseEvent e)
			{
				if (state == START) {
					state = RUNNING;
					System.out.println("����action������¼� state :" + state);
				}
				if (state == GAMEOVER) {
					state = START;
					hero = new hero();
				}
		
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				if (state == PAUSE) {
					state = RUNNING;
					System.out.println("����action������λ���¼� ");
				}

			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (state == RUNNING) {
					state = PAUSE;
					System.out.println("����action����뿪λ���¼� ");
				}
			}
		};
		this.addMouseListener(mouseAdapter);
		this.addMouseMotionListener(mouseAdapter);

		timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (state == RUNNING) {
					// System.out.println("main.action().new TimerTask()
					// {...}.run()");
					enteraction();
					stepaction();
					shootaction();
					outOfBoundsAction();
					bangaction();
					chectdied();
				}
				repaint();
			}
		}, intervel, intervel);
	}

	public void chectdied() {
		if (islife()) {
			state = GAMEOVER;
		}
	}

	public boolean islife() {
		for (int i = 0; i < flyingObjects.length; i++) {
			FlyingObject flyingObject = flyingObjects[i];
			if (hero.hit(flyingObject)) {
				hero.reduselife();
				hero.setDoublefile(0);

				FlyingObject t = flyingObjects[i];
				flyingObjects[i] = flyingObjects[flyingObjects.length - 1];
				flyingObjects[flyingObjects.length - 1] = t;
				Thread thread = new Thread(flyingObjects[flyingObjects.length-1]);
				thread.start();
				flyingObjects2.add(flyingObjects[flyingObjects.length-1]);
				flyingObjects = Arrays.copyOf(flyingObjects, flyingObjects.length - 1);
			}
		}
		return hero.getLife() <= 0;
	}

	
	public void paintState(Graphics g) {
		switch (state) {
		case START:
			g.drawImage(start, 0, 0, null);
			break;
		case PAUSE: 
			g.drawImage(pause, 0, 0, null);
			break;
		case GAMEOVER:
			g.drawImage(gameover, 0, 0, null);
			break;

		}
	}

	public void paintFlyingObjects(Graphics g) {
		for (int i = 0; i < flyingObjects.length; i++) {
			FlyingObject f = flyingObjects[i];
			g.drawImage(f.image, f.x, f.y, null);
		}
	}

	public void paintBullets(Graphics g) {
		for (int i = 0; i < bullets.length; i++) {
			bullet b = bullets[i];
			g.drawImage(b.image, b.x, b.y, null);
		}

	}

	public void paintScore(Graphics g) { 
		g.setColor(new Color(0xFF0000));
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		g.drawString("SCORE: " + record.sore, 20, 25);
		g.drawString("LIFE: " + hero.getLife(), 20, 45);
	}

	public void paint(Graphics graphics) {
		graphics.drawImage(background, 0, 0, null);
		this.painthero(graphics);
		this.paintFlyingObjects(graphics);
		this.paintBullets(graphics);
		this.paintbang(graphics);
		this.paintScore(graphics);
		this.paintState(graphics);

	}

	public void painthero(Graphics graphics)
	{
		graphics.drawImage(hero.image, hero.x, hero.y, null);
	}

	public void bangaction()
	{
		for (bullet bullet : bullets) {
			bang(bullet);
		}
	}

	public void bang(bullet bullet)
	{
		int index = -1;
		for (int i = 0; i < flyingObjects.length; i++) {
			FlyingObject flyingObject = flyingObjects[i];
			if (flyingObject.shootby(bullet)) {
				index = i;
				break;
			}
		}
		if (index != -1)
		{
			FlyingObject flyingObject = flyingObjects[index];
			if (flyingObject instanceof airplane) {
				airplane airplane = (airplane) flyingObject;
				record.sore++;
			}
			if (flyingObject instanceof Award) {
				Award award = (Award) flyingObject;
				switch (award.getType()) {
				case Award.DOUBLE_FIRE:
					hero.addDoubleFire();
					break;
				case Award.LIFE:
					hero.addlife();
					break;
				}
			}
			
			flyingObjects2.add(flyingObjects[index]);
			Thread thread = new Thread(flyingObjects[index]);
			thread.start();
			FlyingObject tFlyingObject = flyingObjects[index];
			flyingObjects[index] = flyingObjects[flyingObjects.length - 1];
			flyingObjects[flyingObjects.length - 1] = tFlyingObject;
			flyingObjects = Arrays.copyOf(flyingObjects, flyingObjects.length - 1);
		}
	}

	public void launchJFrame() {
		action action = new action();
		JFrame jFrame = new JFrame();
		jFrame.setTitle("�ɻ���ս");
		main main = new main();
		jFrame.add(main);
		JMenuBar jMenuBar = new JMenuBar();
		JMenu jMenu = new JMenu("��Ϸ");
		JMenuItem jMenuItem = new JMenuItem("��ͣ");
		jMenuItem.addActionListener(action);
		jMenuItem.setActionCommand("��ͣ");
		JMenuItem jMenuItem2 = new JMenuItem("����");
		jMenuItem2.addActionListener(action);
		jMenuItem2.setActionCommand("����");
		JMenuItem jMenuItem3 = new JMenuItem("���¿�ʼ");
		jMenuItem3.addActionListener(action);
		jMenuItem3.setActionCommand("���¿�ʼ");
		jMenu.add(jMenuItem);
		jMenu.add(jMenuItem2);
		jMenu.add(jMenuItem3);
		jMenuBar.add(jMenu);
		jFrame.setJMenuBar(jMenuBar);
		main.action();
		jFrame.setSize(WIDTH, HIGHT);
		jFrame.setLocationRelativeTo(null); 
		jFrame.setVisible(true);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public static void main(String[] args) {
		main main = new main();
		main.launchJFrame();
	}

	private class action implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getActionCommand().equals("��ͣ")) {
				state = PAUSE;
			}
			if (e.getActionCommand().equals("����")) {
				state = RUNNING;
			}
			if (e.getActionCommand().equals("���¿�ʼ")) {
				if (state == GAMEOVER) {
					state = START;
					hero = new hero();
				}
				hero = new hero();
			}
		}

	}
}
