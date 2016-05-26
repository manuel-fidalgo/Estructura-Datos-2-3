package ule.edi.tree;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.Random;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class WorldTests {

	private World w = null;

	private LinkedList<Character> lC = null;

	@Before
	public void setupWorlds() {

		w = World.createEmptyWorld();

		lC = new LinkedList<Character>();
	}

	@Test
	public void testInsertRender() {

		w.insert("LL", Entity.warriors(2));		
		System.out.println("MundoTest->");
		System.out.println(w.render());	

	}
	@Test
	public void test00(){
		w = World.createEmptyWorld();
		w.insert("", new Entity(Entity.CASTLE));
		w.insert("", new Entity(Entity.CASTLE));
		w.insert("R", new Entity(Entity.FOREST));
		w.insert("LLLL", new Entity(Entity.DRAGON));
		w.insert("R", new Entity(Entity.PRINCESS));
		w.insert("L", new Entity(Entity.WARRIOR));
		w.insert("RR", new Entity(Entity.CASTLE));

		System.out.println("Mundo00->");
		System.out.println(w.render());

		for (int i = 0; i < 8; i++) {
			w.insert("LLLLLLLLLLLLLLLL",new Entity(Entity.CASTLE));
		}

		assertEquals(w.countCastlesCloserThan(0),2);
		assertEquals(w.countCastlesCloserThan(1),2);
		assertEquals(w.countCastlesCloserThan(2),3);
		assertEquals(w.countCastlesCloserThan(15),3);
		assertEquals(w.countCastlesCloserThan(16),11);

		for (int i = 0; i < 8; i++) {
			w.insert("RRRRRRRRRRRRRRRR",new Entity(Entity.CASTLE));
		}
		assertEquals(w.countCastlesCloserThan(15),3);
		assertEquals(w.countCastlesCloserThan(16),19);

	}
	@Test
	public void test01(){
		w = World.createEmptyWorld();
		w.insert("", new Entity(Entity.CASTLE));
		w.insert("", new Entity(Entity.CASTLE));
		w.insert("R", new Entity(Entity.FOREST));
		w.insert("LLLL", new Entity(Entity.DRAGON));
		w.insert("R", new Entity(Entity.PRINCESS));
		w.insert("L", new Entity(Entity.WARRIOR));
		w.insert("RR", new Entity(Entity.CASTLE));
		assertEquals(w.countAtLevel(Entity.CASTLE, 1),2);
		assertEquals(w.countAtLevel(Entity.CASTLE, 2),0);
		assertEquals(w.countAtLevel(Entity.CASTLE, 3),1);

		for (int i = 0; i < 6; i++) {
			w.insert("RRRR", new Entity(Entity.WARRIOR));
			w.insert("LLLL", new Entity(Entity.WARRIOR));
			w.insert("RRRR", new Entity(Entity.PRINCESS));
		}
		System.out.println("Mundo01->");
		System.out.println(w.render());
		
		assertEquals(w.countAtLevel(Entity.WARRIOR, 5),12);
		assertEquals(w.countAtLevel(Entity.PRINCESS, 5),6);
		assertEquals(w.countAtLevel(Entity.DRAGON, 5),1);
		assertEquals(w.countAtLevel(Entity.DRAGON, 6),0);
		System.out.println("Mundo02->\n"+w.render());
		assertEquals(w.findFirstDragonInBreadthOrder(),7);
		w.insert("RR", new Entity(Entity.DRAGON));
		assertEquals(w.findFirstDragonInBreadthOrder(),4);
		
		
	}

}
