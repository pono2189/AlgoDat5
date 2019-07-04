
public class Point {
	private float x;
	private float y;
	boolean firstPoint = false;
	//boolean secondPoint = false;
	boolean intersectionPoint = false;
	Line a;
	Line b;
	
	Point(){
		x = 0;
		y = 0;
	} 
	
	Point(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}
	
	public float getY(){
		return y; 
	}
	
	public void setY(float y){
		this.y = y; 
	}
}



