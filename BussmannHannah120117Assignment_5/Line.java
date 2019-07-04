import java.util.*;
public class Line {
	private Point p1;
	private Point p2;
	public Point sweep_intersection;


	Line(){
		Point p1 = new Point(0,0);
		Point p2 = new Point(0,0);
		p1.a = this;
		p2.a = this;
	}
	
	Line(Point p1, Point p2){
		this.p1 = p1; 
		this.p2 = p2;
	}
	
		
	Line(int x1, int y1, int x2, int y2){
		Point p1 = new Point(x1, y1);
		Point p2 = new Point(x2, y2);
		p1.a = this;
		p2.a = this;
	}
	
	public Point getP1(){
		return p1;
	}
	
	public Point getP2(){
		return p2;
	}
	
	public void setP1(Point p1){
		this.p1 = p1;
	}
	
	public void setP2(Point p2){
		this.p2 = p2;
	}
	
	public void setP2_x(int x){
		this.p2.setX(x);
	}
	
	public void setP2_y(int y){
		this.p2.setY(y);
	}
	
	public static Line sortX(Line l) {
		if (l.getP1().getX() < l.getP2().getX()){
			return l;
		}
		else {
			Point temp = l.p1;
			l.p1 = l.p2;
			l.p2 = temp;
		}
		
		return l;
	}
	
	public static Line sortY(Line l) {
		if (l.getP1().getY() > l.getP2().getY()){
			return l;
		}
		else {
			Point temp = l.p1;
			l.p1 = l.p2;
			l.p2 = temp;
		}
		
		return l;
	}
	

}