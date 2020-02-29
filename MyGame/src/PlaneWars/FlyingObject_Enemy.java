package PlaneWars;

public abstract class FlyingObject_Enemy extends FlyingObject{
	public FlyingObject_Enemy(int width, int height) {
		super(width, height);
	}
	public FlyingObject_Enemy(int width, int height, int x, int y) {
		super(width, height, x, y);
	}
}
