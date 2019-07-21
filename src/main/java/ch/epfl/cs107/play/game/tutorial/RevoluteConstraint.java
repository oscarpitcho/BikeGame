package ch.epfl.cs107.play.game.tutorial;

import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.EntityBuilder;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.RevoluteConstraintBuilder;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.World;
import ch.epfl.cs107.play.window.Window;

public class RevoluteConstraint implements Game 
{
	private Window window;
	private World world;
	private Entity ball;
	private Entity basis;
	private Entity plank;
	private ImageGraphics ballGraphics;
	private ImageGraphics basisGraphics;
	private ImageGraphics plankGraphics;
	
	
	 public boolean begin(Window window, FileSystem fileSystem) 
	 {
		 // world & window:
		 this.window = window;
		 world = new World ();
		 world.setGravity(new Vector (0.0f, -9.81f));
		 
		 // Ball Builder:
		 // Entity
		 EntityBuilder ballBuilder = world.createEntityBuilder();
		 ballBuilder.setFixed(false);
		 ballBuilder.setPosition(new Vector (0.5f, 4.0f));
		 ball = ballBuilder.build();
		 //Part
		 PartBuilder ballPart = ball.createPartBuilder();
		 Circle circle = new Circle (0.5f);
		 ballPart.setShape(circle);
		 ballPart.setFriction(0.5f);
		 ballPart.build();
		 //Graphics
		 ballGraphics = new ImageGraphics ("explosive.11.png", 2.0f*circle.getRadius(), 2.0f*circle.getRadius(), 
				 		new Vector (0.5f, 0.5f)  );
		 ballGraphics.setParent(ball);
		 
		 //Basis & plank:
		 //Entity
		 EntityBuilder basisBuilder = world.createEntityBuilder();
		 EntityBuilder plankBuilder = world.createEntityBuilder();
		 basisBuilder.setPosition(new Vector(-5.0f,-1.0f));
		 plankBuilder.setPosition(new Vector (-2.5f,-0.8f));
		 basisBuilder.setFixed(true);
		 plankBuilder.setFixed(false);
		 basis = basisBuilder.build();
		 plank = plankBuilder.build();
		 // Part
		 PartBuilder basisPart = basis.createPartBuilder();
		 PartBuilder plankPart = plank.createPartBuilder();
		 Polygon rectangle = new Polygon (new Vector(0f, 0f),
				 			  			  new Vector(10.0f, 0.0f),
				 			  			  new Vector(10.0f, 1.0f),
				 			  			  new Vector(0f, 1.0f));
		 Polygon rectangle2 = new Polygon (new Vector(0f, 0f),
	  			  						   new Vector(5f, 0f),
	  			  						   new Vector(5f, 0.2f),
	  			  						   new Vector(0f, 0.2f));
		 basisPart.setShape(rectangle);
		 plankPart.setShape(rectangle2);
		 basisPart.build();
		 plankPart.build();
		 
		 //Graphics
		 basisGraphics = new ImageGraphics ("stone.broken.4.png", 10.0f, 1.0f);
		 plankGraphics = new ImageGraphics ("wood.3.png", 5.0f, 0.2f );
		 basisGraphics.setParent(basis);
		 plankGraphics.setParent(plank);
		 
		 // Constraints
		RevoluteConstraintBuilder revoluteConstraintBuilder = world.createRevoluteConstraintBuilder() ;
		 revoluteConstraintBuilder.setFirstEntity(basis) ;
		 revoluteConstraintBuilder.setFirstAnchor(new Vector(basisGraphics.getWidth()/2,(basisGraphics.getHeight()*7)/4)) ;
		 revoluteConstraintBuilder.setSecondEntity(plank) ;
		 revoluteConstraintBuilder.setSecondAnchor(new Vector(plankGraphics.getWidth()/2,plankGraphics.getHeight()/2)) ;
		 revoluteConstraintBuilder.setInternalCollision(true) ;
		 revoluteConstraintBuilder.build();

		 
		 
		 return true;
	 }
	 public void update (float deltatime)
	 {
		 world.update(deltatime);
		 ballGraphics.draw(window);
		 plankGraphics.draw(window);
		 basisGraphics.draw(window);
		 window.setRelativeTransform(Transform.I.scaled(10.0f));
		 
	 }
	 public void end ()
	 {
	 }
}
