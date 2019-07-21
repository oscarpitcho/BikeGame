package ch.epfl.cs107.play.game.tutorial;

import java.awt.Color;

import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.EntityBuilder;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.RopeConstraintBuilder;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.World;
import ch.epfl.cs107.play.window.Window;

public class RopeGame implements Game
{
	
	 private Window window;
	 private World world;
	 private Entity ball;
	 private Entity crate;
	 private ShapeGraphics ballGraphics;
	 private ImageGraphics crateGraphics;
	 
	
	 public boolean begin(Window window, FileSystem fileSystem) {
		 // instantiate the world and the window
	        this.window = window; 
	        world = new World ();
	        world.setGravity(new Vector (0.0f, -9.81f));
	        
	        // The Crate:
	        // Building Crate:
	        EntityBuilder entity1 = world.createEntityBuilder();
	        entity1.setFixed(true);
	        entity1.setPosition(new Vector (0.0f, 0.0f));
	        crate = entity1.build();
	        
	        
	        // Instantiating the Crate's image
	        crateGraphics = new ImageGraphics ("crate.1.png" , 1, 1);
	        crateGraphics.setDepth(0);
	        crateGraphics.setParent(crate);
	        
	        
	        // Creating Crate's geometry 
	        PartBuilder part1 = crate.createPartBuilder();
	        Polygon square = new Polygon(new Vector (0.0f, 1.0f), 
	 					new Vector (1.0f, 0.0f), 
	 					new Vector (1.0f, 1.0f), 
	 					new Vector (0.0f, 0.0f));
	        part1.setShape(square); 
	        part1.setFriction(0.5f);
	        part1.build();
	        
	        // The Ball :
	        // Building Ball:
	        EntityBuilder ballBuilder = world.createEntityBuilder();
	        ballBuilder.setFixed(false);
	        ballBuilder.setPosition(new Vector (0.6f, 4.0f));
	        ball = ballBuilder.build();
	        
	        // Creating the Ball Geometry:
	        PartBuilder part2 = ball.createPartBuilder();
	        Circle circle = new Circle (0.6f);
	        part2.setShape(circle);
	        part2.setFriction(0.5f);
	        part2.build();
	        
	        
	        // The Constraint:
	        // Building the constraint:
	        RopeConstraintBuilder ropeConstraintBuilder =
	        		world.createRopeConstraintBuilder() ;
	        		ropeConstraintBuilder.setFirstEntity(crate) ;
	        		ropeConstraintBuilder.setFirstAnchor(new Vector(crateGraphics.getWidth()/2f, crateGraphics.getHeight()/2f)) ;
	        		ropeConstraintBuilder.setSecondEntity(ball) ;
	        		ropeConstraintBuilder.setSecondAnchor(Vector.ZERO) ;
	        		ropeConstraintBuilder.setMaxLength(6.0f) ;
	        		ropeConstraintBuilder.setInternalCollision(true) ;
	        		ropeConstraintBuilder.build() ;

	        
	        
	        
	        
	        // Store context
	       
	        return true;
	    }

	    // This event is called at each frame
	    @Override
	    public void update(float deltaTime) {
	        world.update(deltaTime);
	        ballGraphics.draw(window);
	        crateGraphics.draw(window);
	     window.setRelativeTransform(Transform.I.scaled(10.0f));
	      // The actual rendering will be done now, by the program loop
	    
	    }

	    // This event is raised after game ends, to release additional resources
	    @Override
	    public void end() {
	        // Empty on purpose, no cleanup required yet
	    }
	    
	

}
