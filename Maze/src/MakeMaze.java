import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class MakeMaze extends MyFrame implements KeyListener{
	 Random random;
	 int xt, yt, xn, yn;
	 int[][] mz;
	 int empty = 0, wall = 1, me = 2, in = 3, out = 4;
	
	MakeMaze() {
		random = new Random();
		addKeyListener(this);
	}
	
	 void makemaze() {
		 clear();
		xt = 7;
		yt = 7;
		xn = xt * 2 + 3; //最低4マス必要かつ奇数
		yn = yt * 2 + 3;

		mz = new int[yn][xn];
		for (int x = 0; x < xn; ++x) {
			mz[0][x] = mz[yn - 1][x] = wall; // 外枠 上辺と下辺
		}
		for (int y = 1; y < (yn - 1); ++y) {
			mz[y][0] = mz[y][xn - 1] = wall; // 外枠 左辺と右辺
		}

		for (int j = 0; j < yt; ++j) { // 各格子点から
			int y = j * 2 + 2;
			for (int i = 0; i < xt; ++i) {
				int x = i * 2 + 2;
				trace(y, x);
			}
		}
		mz[0][1] = in;
		mz[yn - 1][xn - 2] = out; // 入り口と出口をあける
		display(mz); // 表示
	}

	 void trace(int yy, int xx) {
		boolean ok = true;
		int x00 = xx;
		int y00 = yy;

		int dx, dy;
		while (mz[yy][xx] == empty) {
			mz[yy][xx] = me; // 格子点描画

			dx = 0;
			dy = 0;
			int rn = random.nextInt(4);

			if (rn == 0) //上
				dy = -1;
			else if (rn == 1) //右
				dx = 1;
			else if (rn == 2) //下
				dy = 1;
			else //左
				dx = -1;

			ok = true;
			if (mz[yy + dy + dy][xx + dx + dx] == me) {
				ok = false;
				for (int t = 1; t < 4; ++t) {
					int t2 = (rn + t) % 4;
					dx = dy = 0;

					if (t2 == 0)
						dy = -1; // 上
					else if (t2 == 1)
						dx = 1; // 右
					else if (t2 == 2)
						dy = 1; // 下
					else
						dx = -1; // 左

					if (mz[yy + dy + dy][xx + dx + dx] != me) {
						ok = true;
						break;
					}
				}
			}

			if (ok) {
				xx += dx;
				yy += dy;
				mz[yy][xx] = me;
				xx += dx;
				yy += dy;
			} else {
				change(empty);
				xx = x00;
				yy = y00;
			}
		}
		change(wall); // 壁にする
	}

	 void change(int d) { // 壁にする／やりなおす(消す)
		for (int y = 0; y < yn; ++y) {
			for (int x = 0; x < xn; ++x) {
				if (mz[y][x] == me)
					mz[y][x] = d;
			}
		}
	}

	 void display(int[][] zz) {
		for (int y = 0; y < yn; ++y) {
			for (int x = 0; x < xn; ++x) {
				if (zz[y][x] == wall) {
					setColor(175,100,255);
					fillRect(x * 20 + 20, y * 20 + 50, 20,20); // 壁
				}
				else if(zz[y][x] == in){
					setColor(255,0,0);
					fillRect(x * 20 + 20, y * 20 + 50, 20,20);  // スタート
				}
				else if(zz[y][x] == out)
				{
					setColor(0,0,255);
					fillRect(x * 20 + 20, y * 20 + 50, 20,20); //ゴール
				}
			}
			System.out.println();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			makemaze();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		
	}
	
	/*public static void make() {
		makemaze();
	}*/
}