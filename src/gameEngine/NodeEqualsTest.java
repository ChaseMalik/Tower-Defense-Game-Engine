package gameEngine;

import java.util.HashMap;
import javafx.scene.Node;
import javafx.scene.shape.Circle;

public class NodeEqualsTest{

	public static void main(String[] args) {
		Circle x = new Circle();
		Circle y = x;
		System.out.println("Should be true " + x.equals(y));
		
		Circle z = new Circle();
		System.out.println("Should be false " + x.equals(z));
		
		HashMap<Node, Integer> map = new HashMap<Node, Integer>();
		map.put(x, 1);
		System.out.println("Should be 1 " + map.get(x));
		map.put(y, 2);
		System.out.println("Should be 2 " + map.get(x));
		System.out.println(x.hashCode());
		System.out.println(y.hashCode());
		System.out.println(z.hashCode());
	}

}
