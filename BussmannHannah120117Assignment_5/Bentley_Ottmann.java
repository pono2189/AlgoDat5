import java.util.*;
public class Bentley_Ottmann {
	public static ArrayList<Point> sortPoints(ArrayList<Point> array) { //sorts Points from left to right
		for (int i = 0; i < array.size() - 1; i++) {
			for (int j = i + 1; j < array.size(); j++) {
				if (array.get(i).getX() > array.get(j).getX()) {
					Point temp = array.get(i);
					array.set(i, array.get(j));
					array.set(j, temp);
				}
				if(array.get(i).getX() == array.get(j).getX()){
					if(array.get(i).getY() > array.get(j).getY()){
						Point temp = array.get(i);
						array.set(i, array.get(j));
						array.set(j, temp);
					}
				}
			}
		}

		return array;
	}
	
//	public static ArrayList<Line> sort_along_Line(ArrayList<Line> array, Line l) { //sorts Points from left to right
//		for (int i = 0; i < array.size(); i++) {
//			Point p = intersect(l, array.get(i));
//			array.get(i).sweep_intersection = p; // the intersection point with sweepline 
//		}
//		for(int k = 0; k < array.size(); k++){
//			for (int j = k + 1; j < array.size(); j++) {
//				if (array.get(k).sweep_intersection.getX() > array.get(j).sweep_intersection.getX()) {
//					Line temp = array.get(k);
//					array.set(k, array.get(j));
//					array.set(j, temp);
//				}
//				if(array.get(k).sweep_intersection.getX() == array.get(j).sweep_intersection.getX()){
//					if(array.get(k).getP1().getY() > array.get(j).getP1().getY()){
//						Line temp = array.get(k);
//						array.set(k, array.get(j));
//						array.set(j, temp);
//					}
//				}
//			}
//		}
//		for(int d = 0; d < array.size(); d++){
//			System.out.println(array.get(d).sweep_intersection.getX() +  "," + array.get(d).sweep_intersection.getY());
//		}
//		
//		
//		return array;
//	}
	
	public static ArrayList<Line> sortX_Y(ArrayList<Line> array) { //sorts Lines by their first point from left to right 
		// and if two first points have the same x value sorts them by their y value (up to down)
		for (int i = 0; i < array.size() - 1; i++) {
			for (int j = i + 1; j < array.size(); j++) {
				if (array.get(i).getP1().getX() > array.get(j).getP1().getX()) {
					Line temp = array.get(i);
					array.set(i, array.get(j));
					array.set(j, temp);
				}
				if(array.get(i).getP1().getX() == array.get(j).getP1().getX()){
					if(array.get(i).getP1().getY() > array.get(j).getP1().getY()){
						Line temp = array.get(i);
						array.set(i, array.get(j));
						array.set(j, temp);
					}
				}
			}
		}

		return array;
	}
	
	public static ArrayList<Line> sortYP2(ArrayList<Line> array) { //sorts lines from up down by second y value 
		for (int i = 0; i < array.size() - 1; i++) {
			for (int j = i + 1; j < array.size(); j++) {
				if (array.get(i).getP2().getY() > array.get(j).getP2().getY()) {
					Line temp = array.get(i);
					array.set(i, array.get(j));
					array.set(j, temp);
				}
			}
		}

		return array;
	}
	
	public static ArrayList<Line> sortYP1(ArrayList<Line> array) { //sorts lines from up down by first y value
		for (int i = 0; i < array.size() - 1; i++) {
			for (int j = i + 1; j < array.size(); j++) {
				if (array.get(i).getP1().getY() > array.get(j).getP1().getY()) {
					Line temp = array.get(i);
					array.set(i, array.get(j));
					array.set(j, temp);
				}
			}
		}

		return array;
	}
	
	public static ArrayList<Line> randomLines(){
		ArrayList<Line> lines = new ArrayList<Line>();
		Random rand = new Random();
		int size = Math.abs(rand.nextInt() % 15); //a random number of lines are generated
		
		for(int i = 0; i < size; i++){  //the random lines get random x and y values 
			Point p1 = new Point(rand.nextInt()%60, rand.nextInt()%60);
			Point p2 = new Point(rand.nextInt()%60, rand.nextInt()% 60);
			
			if(p1.getX() == p2.getX()){ //to avoid vertical lines
				float temp = p1.getX();
				p1.setX(temp+1);
			}
			
			Line l = new Line();
			// to make sure p1 is further to the left than p2:
			if(p1.getX() < p2.getX()){
				p1.firstPoint = true; //checks which one of the two points is the smaller one -> the one further to the left
				l.setP1(p1);
				l.setP2(p2);
			}
			else{
				p2.firstPoint = true;
				l.setP1(p2);
				l.setP2(p1);
			}
			lines.add(l); //the radnom line is added to lines
		}
		return lines;
	}
	
	public static void printLines(ArrayList<Line> lines){ //function to print an arraylist of lines
		for (int i = 0; i < lines.size(); i++){
			System.out.println("Line_" + i + ": ((" + lines.get(i).getP1().getX() + ", " + lines.get(i).getP1().getY() + "),("
					+ lines.get(i).getP2().getX() + ", " + lines.get(i).getP2().getY() + ")) ");
		}
		System.out.println();
	}
	
	public static ArrayList<Point> naiveAlg(ArrayList<Line> lines){
		//the naive algorithm compares every line with every other line to find intersections
		ArrayList<Point> solution = new ArrayList<Point>();
		Point p = new Point (100, 100);
		if(lines.size() == 0){
			System.out.println("Error - There are no lines in the array - Naive Algorithm not possible");
			return solution;
		}
		for (int i = 0; i < lines.size(); i++ ){
			for (int k = i+1; k < lines.size(); k++){
				float x = intersect(lines.get(i), lines.get(k)).getX();
				float y = intersect(lines.get(i), lines.get(k)).getY();
				if(x != p.getX() && y != p.getY()){
					Point inter = new Point(x, y);
					inter.a = lines.get(i);
					inter.b = lines.get(k);
					inter.intersectionPoint = true;
					//Intersection in = new Intersection(lines.get(i), lines.get(k), intersect(lines.get(i), lines.get(k)));
					solution.add(inter);
				}
			}
		}
		if(solution.size() == 0){
			System.out.println("The naive Algorithm could not find any intersections");
		}
		return solution;
	}

	public static ArrayList<Point> bentley_ottmann(ArrayList<Line> lines){
		ArrayList<Point> ILsolution = new ArrayList<Point>();
		ArrayList<Line> SL = new ArrayList<Line>();
		Point noIntersection = new Point(100, 100); //point outside of the possible coordinate system
		// if the point noIntersection is returned we know that there is not an intersection because no lines could ever 
		// have (100,100) as intersection point, because it is outside of the range of the coordinate system
		Line no = new Line(noIntersection, noIntersection);
		if(lines.size() == 0){ // if the array is empty the algorithm is not possible
			System.out.println("Error - There are no lines in the array - Bentley Ottmann not possible");
			return ILsolution;
		}

		sortX_Y(lines); //sorting lines by x value 
		
		ArrayList<Point> EQpoints = new ArrayList<Point>();
		for (int i = 0; i < lines.size(); i++){ //all of the points from all of the lines
			EQpoints.add(lines.get(i).getP1());  // are added to the sweep list (only the points!!!)
			EQpoints.add(lines.get(i).getP2());
		}
		
		int p_x =(int) (lines.get(0).getP1().getX())-1; 
		// as sweepline is a vertical line, it only has one x value
		//int stop =(int) lines.get(lines.size()-1).getP2().getX();
		sortYP1(lines);
		int p1_y =(int) lines.get(0).getP1().getY();
		sortYP2(lines); 
//		if(lines.get(0).getP2().getY() < p1_y){ // to get the smallest y value
//			p1_y = (int) lines.get(0).getP2().getY();
//		}
//		int p2_y = (int) lines.get(lines.size()- 1).getP2().getY();// to get the biggest y value
		Line sweepline = new Line(p_x, 60, p_x, -60);// p2_y);
		// declared here but initialized in "while loop"
		//creates sweep line that starts at the smallest x point and is as long as the distance 
		//between the smallest and the largest y value so it sweeps over every possible point
		
		sortPoints(EQpoints); //works!!
		Point current = new Point();
		while(EQpoints.size() != 0){
			current = EQpoints.get(0);
			Point s1 = new Point(current.getX(), 60);//p1_y);
			sweepline.setP1(s1); //sets sweepline to cross current point
			Point s2 = new Point(current.getX(),-60); //p2_y);
			sweepline.setP2(s2);
			if(current.firstPoint == true && current.intersectionPoint == false){ //first event: current is the starting point of a line
				for(int i = 0; i < lines.size(); i++){
					if(lines.get(i).getP1() == current || lines.get(i).getP2() == current){ 
						//looking the line of which current is the starting point 
						//to get the index of current in lines in order to get the whole line 
						//SL.add(lines.get(i));
						int insert = -1; // insert starts at -1 for the special case that SL is empty
						// the element is inserted at insert -1 
						// to add the line to the correct place in SL:
						if(SL.size() == 0){ //skip next steps if SL is empty
							for (int j = 0; j < SL.size(); j++ ){ 
								//iterates through SL until an element whose intersection point(y value) with sweepline
								// is smaller than the y value of current -> the line of element at j cuts sweepline
								// under the current point
								if(current.getY() < intersect(SL.get(j), sweepline).getY()){
									insert = j;
								}
								else{
									break;
								}
							}
						}
						SL.add(insert +1, lines.get(i)); //adds the line of current at the right position in SL
			
						Line previous = no; //initializing previous and next with (100, 100,100,100)
						// because the point (100, 100) can never be a random point because the coordinate system allows only values from -60 to 60
						// if previous and next are still (100,100,100,100) after the following two if cases, we know
						// that they have not been changed -> that the if cause was false and we did not execute the code inside of them 
						Line next = no;
						//int index = 0;
//						for(int k = 0; k < SL.size(); k++){
//							if(SL.get(k).getP2()== current || SL.get(k).getP2() == current){
//								index = k;
//								break;
//							}
//						}
						if(insert > -1){
							previous = SL.get(insert);
						}
						if(insert < SL.size()-2){
							next = SL.get(insert+2);
						}
						
						if(previous != no  && intersect(previous, lines.get(i)).getX() != noIntersection.getX()){
							Point new_inter = intersect(previous, lines.get(i));
							new_inter.intersectionPoint = true;
							new_inter.a = next;
							new_inter.b = lines.get(i);
							EQpoints.add(new_inter);
						}
						
						if(next != no && intersect(next, lines.get(i)).getX() != noIntersection.getX()){
							Point new_inter = intersect(next,lines.get(i));
							new_inter.intersectionPoint = true;
							new_inter.a = next;
							new_inter.b = lines.get(i);
							EQpoints.add(new_inter);
						}
						
						break;
					}
				}
			}
			else if(current.firstPoint == false && current.intersectionPoint == false){
				//if current is the second point of a line 
				for(int i = 0; i < lines.size(); i++){
					if(lines.get(i).getP2() == current || lines.get(i).getP1() == current){
						Line previous = no; //again line with (100,100,100,100)
						Line next = no; // just like in the if case before
						int index = 0;
						for(int k = 0; k < SL.size(); k++){
							if(SL.get(k).getP2()== current || SL.get(k).getP1() == current){
								index = k; //saves the index of the line of current in SL in "index "
							}
						}
						if(index > 0){ 
							previous = SL.get(index-1); //saves the line before current in previous
						}
						if(index < SL.size()-1){
							next = SL.get(index+1); //saves the line after current in next
						}
						
						SL.remove(index); //remove current line from SL
						if(next != no && previous != no && intersect(next, previous).getX() != noIntersection.getX()){
							// if next and previous exist
							for(int j = 0; j < EQpoints.size(); j++){
								if(EQpoints.get(j).getX() == intersect(next, previous).getX() && EQpoints.get(j).getY() == intersect(next, previous).getY() ){
									break;
								}
								else if (j == EQpoints.size()){
									Point new_inter = intersect(next, previous);
									new_inter.a = next;
									new_inter.b = previous;
									new_inter.intersectionPoint = true;
									EQpoints.add(new_inter);
									break;
								}
							}
													
						}
					}
				}
			}
			else if (current.intersectionPoint == true){ //the current point is an intersection point				
				if(current.a.getP1().getX() != 100 && current.b.getP1().getX() != 100 && current.a.getP2().getX() != 100 && current.b.getP2().getX() != 100){
				ILsolution.add(current); //adds the intersection point to the solution array
				int indexA = 0;
				int indexB = 0;
				
				for(int k = 0; k < SL.size(); k++){
					if(SL.get(k)== current.a){
						indexA = k; //indexA is the index of current.a
					}
					if(SL.get(k)==current.b){
						indexB = k; //indexB is the index of current.b
					}
				}
				Line segNext = new Line();
				Line segPrev = new Line();
				Line segE1 = new Line();
				Line segE2 = new Line();
				
				if(indexA > 0 && indexB < SL.size()-1  && indexA > indexB){ //if current.a is before current.b in SL
					segE1 = current.a; //segE1 is the element above segE2 in SL
					segE2 = current.b; //segE2 "------------" below segE1 in SL
					SL.set(indexB, segE1); //swap in SL
					SL.set(indexA, segE2);
					Line temp = segE1; //swap variable values
					segE1 = segE2;
					segE2 = temp;
					segNext = SL.get(indexA+1); // saves the value above the new segE2 in segNext
					segPrev = SL.get(indexB-1);	// saves the value below the new segE1 in segPrev
				}
				
				else if(indexA > 0 && indexB < SL.size()-1 && indexB > indexA){
					segE1 = current.b;
					segE2 = current.a;
					SL.set(indexA, segE1);
					SL.set(indexB, segE2);
					Line temp = segE1;
					segE1 = segE2;
					segE2 = temp;
					segNext = SL.get(indexB+1);
					segPrev = SL.get(indexA-1);			
				}
				
				if(indexA > 0 && indexB < SL.size()-1 && intersect(segE2, segNext).getX() != noIntersection.getX()){
					for(int j = 0; j < EQpoints.size(); j++){
						if(EQpoints.get(j).getX() == intersect(segE2, current.a).getX() && EQpoints.get(j).getY() == intersect(segE2, current.a).getY()){
							break;
						}
						else{
							Point new_inter = intersect(segE2,segNext);
							new_inter.intersectionPoint = true;
							new_inter.a = segE2;
							new_inter.b = segNext;
							EQpoints.add(new_inter);
//							System.out.println("Line" + ": ((" + new_inter.a.getP1().getX()+ ", " + new_inter.a.getP1().getY() + "),("
//									+ new_inter.a.getP2().getX() + ", " + new_inter.a.getP2().getY() + ")) & Line" + ": (("
//									+ new_inter.b.getP1().getX()+ ", " + new_inter.b.getP1().getY() + "),("
//									+ new_inter.b.getP2().getX() + ", " + new_inter.b.getP2().getY() + ")) intersect in (" + new_inter.getX() + ", " + new_inter.getY());	
//						
						}
					}
					if(intersect(segE1, segPrev).getX() != noIntersection.getX()){
						for(int j = 0; j < EQpoints.size(); j++){
							if(EQpoints.get(j).getX() == intersect(segE1, segPrev).getX() && EQpoints.get(j).getY() == intersect(segE1, segPrev).getY()){
								break;
							}
							else{
								Point new_inter2 = intersect(segE1,segPrev);
								new_inter2.intersectionPoint = true;
								new_inter2.a = segE1;
								new_inter2.b = segPrev;
								EQpoints.add(new_inter2);
//								System.out.println("Line" + ": ((" + new_inter2.a.getP1().getX()+ ", " + new_inter2.a.getP1().getY() + "),("
//										+ new_inter2.a.getP2().getX() + ", " + new_inter2.a.getP2().getY() + ")) & Line" + ": (("
//										+ new_inter2.b.getP1().getX()+ ", " + new_inter2.b.getP1().getY() + "),("
//										+ new_inter2.b.getP2().getX() + ", " + new_inter2.b.getP2().getY() + ")) intersect ");	
							
								}
							}
						}
					}
				}
		
			}
			EQpoints.remove(current);
			for (int a = 0; a < ILsolution.size(); a++){ 
				if(ILsolution.get(a).getX() == noIntersection.getX()){
					ILsolution.remove(a);
				}
			}
		}	
		if(ILsolution.size() == 0){
			System.out.println("The Bentley-Ottmann Algorithm could not find any intersections");
		}
		return ILsolution;
	}
	
	public static Point intersect(Line a, Line b){
		Point intersection = new Point(100, 100);
		// to get the intersection of two lines you have to set the two functions euqal
		// y = m*x+t if you calculate m and t you can form the function for a line
		float m1 = (a.getP1().getY() - a.getP2().getY())/(a.getP1().getX() - a.getP2().getX());
		float t1 = (a.getP1().getX()*a.getP2().getY() - a.getP2().getX()*a.getP1().getY())/(a.getP1().getX()-a.getP2().getX());
		float m2 = (b.getP1().getY() - b.getP2().getY())/(b.getP1().getX() - b.getP2().getX());
		float t2 = (b.getP1().getX()*b.getP2().getY() - b.getP2().getX()*b.getP1().getY())/(b.getP1().getX()-b.getP2().getX());
		//until here correct!!!
		if (m1 == m2 || (int)a.getP1().getX() == 100 || (int)b.getP1().getX() == 100){ 
			//if m1 and m2 are equal, the two lines are either parallel or the same
			// so there is either no intersection or infinite
			// if one of the points == 100 we know that one of the points is an exception and we break out of the method
			return  intersection;
		}
		//System.out.println("B: " + b.getP1().getX() + " " + b.getP1().getY()+ ", " + b.getP2().getX() + " " + b.getP2().getY());
		//System.out.println("t1 " + t1 + " m1 " + m1 + " t2 " + t2 + " m2 " + m2);
		float x_intersect = -(t1 - t2)/(m1 - m2);
		float y_intersect = (m1*t2 - m2*t1)/(m1-m2); 
		
		// finding the smallest x and y value of the two lines 
		// to find the range that the intersection can be in 
		// because the lines start and the first point and stop at the second one , 
		// but the equations of the lines do not! they go on infinitely so that way all lines would eventually intersect
		// to avoid that I will only look for the intersection found within the "box" the two lines fix
		// (from max_y1,max_x1 to max_y2, max_x2 to min_x1,min_y1 to min_x2, min_y2 -> the four corner points of the "box"/range of the two lines)
		float max_y1;
		float min_y1;
		float max_y2;
		float min_y2;
		float min_x1 = a.getP1().getX();
		float max_x1 = a.getP2().getX();
		if(a.getP1().getY() > a.getP2().getY()){
			max_y1 = a.getP1().getY();
			min_y1 = a.getP2().getY();
		}
		else{
			max_y1 = a.getP2().getY();
			min_y1 = a.getP1().getY();
		}
		
		float min_x2;
		float max_x2;
		if(b.getP1().getX() > b.getP2().getX()){
			max_x2 = b.getP1().getX();
			min_x2 = b.getP2().getX();
		}
		else{
			max_x2 = b.getP2().getX();
			min_x2 = b.getP1().getX();
		}
		if(b.getP1().getY() > b.getP2().getY()){
			max_y2 = b.getP1().getY();
			min_y2 = b.getP2().getY();
		}
		else{
			max_y2 = b.getP2().getY();
			min_y2 = b.getP1().getY();
		}
		
		intersection.setX(x_intersect);
		intersection.setY(y_intersect);
		intersection.intersectionPoint = true;
		
//		System.out.println("min_x1:" + min_x1 + "   max_x1:" + max_x1 + "   min_y1:" + min_y1 + "   max_y1"  + max_y1);
//		System.out.println("min_x2:" + min_x2 + "   max_x2:" + max_x2 + "   min_y2:" + min_y2 + "   max_y2" + max_y2);
		if(((x_intersect > min_x1) && (x_intersect < max_x1) && (y_intersect > min_y1) && (y_intersect < max_y1)
			&& (x_intersect > min_x2) && (x_intersect < max_x2) && (y_intersect > min_y2) && (y_intersect < max_y2))){
			return intersection;
		}
		
		else{
			Point p = new Point(100, 100); //if the intersection is not inside of the range,
			// -> that means the lines between do not intersect within the range 
			// so the point(100,100) is returned to show that the intersection is out of range 
			return  p;
			
		}
	}
	
	public static void main (String[] args){
		ArrayList<Line> lines = new ArrayList<Line>();
		System.out.println("TEST - algorithms with empty array: \n");
		bentley_ottmann(lines);
		naiveAlg(lines);
		lines = randomLines();
		System.out.println("\nSort Lines by X value(of first Point): \n"); 
		//printLines(lines);
		sortX_Y(lines);
		printLines(lines);
		
		
		ArrayList<Point> inter = new ArrayList<Point>();
		System.out.println("TEST - intersect:\n");
		Point a1 = new Point(-1, 3);
		Point a2 = new Point(3, -1);
		Point b1 = new Point(2, -2);
		Point b2 = new Point(1, 2);
		Line a = new Line(a1,a2);
		Line b = new Line(b1, b2);
		Point c = intersect(a, b);
		System.out.println("Schnittpunkt: " + c.getX() + ", " + c.getY());
		
		Point c1 = new Point(-20, -20);
		Point c2 = new Point(-30, -30);
		Line d = new Line(c1, c2);
		intersect(b, d);
		ArrayList<Line> no = new ArrayList<Line>();
		no.add(d);
		no.add(a);
		
		
		inter = bentley_ottmann(lines);
		// note: the algorithms might exchange the first and the second line of the intersection
		// so it might seem like have different intersection but the intersection point still remains the same 
		System.out.println("\nBentley-Ottmann Algorithm: \n");
		for (int i = 0; i < inter.size(); i++){
			System.out.println("Line" + ": ((" + inter.get(i).a.getP1().getX()+ ", " + inter.get(i).a.getP1().getY() + "),("
					+ inter.get(i).a.getP2().getX() + ", " + inter.get(i).a.getP2().getY() + ")) & Line" + ": (("
					+ inter.get(i).b.getP1().getX()+ ", " + inter.get(i).b.getP1().getY() + "),("
					+ inter.get(i).b.getP2().getX() + ", " + inter.get(i).b.getP2().getY() + ")) intersect in (" + inter.get(i).getX() + ", " + inter.get(i).getY() + ")");	
		}	
		
		inter = naiveAlg(lines);
		System.out.println("\nNaive Algorithm: \n");
		for (int i = 0; i < inter.size(); i++){
			System.out.println("Line" + ": ((" + inter.get(i).a.getP1().getX()+ ", " + inter.get(i).a.getP1().getY() + "),("
					+ inter.get(i).a.getP2().getX() + ", " + inter.get(i).a.getP2().getY() + ")) & Line" + ": (("
					+ inter.get(i).b.getP1().getX()+ ", " + inter.get(i).b.getP1().getY() + "),("
					+ inter.get(i).b.getP2().getX() + ", " + inter.get(i).b.getP2().getY() + ")) intersect in (" + inter.get(i).getX() + ", " + inter.get(i).getY() + ")");	
		}	
		
		
	}	
}

