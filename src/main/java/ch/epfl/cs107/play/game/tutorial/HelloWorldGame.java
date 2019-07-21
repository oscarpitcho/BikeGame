package ch.epfl.cs107.play.game.tutorial;

import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.EntityBuilder;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.World;
import ch.epfl.cs107.play.window.Window;

/**
 * Simple game, to show basic the basic architecture
 */
public class HelloWorldGame implements Game {

    // Store context
    private Window window;
    
    // We need our physics engine
      private World world;
    // The Entity
      private Entity body;
    // The Entity's graphics 
      private ImageGraphics graphBody1;
      private ImageGraphics graphBody2;
    
    
    // This event is raised when game has just started
    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        
        // Store context
       this.window = window;
       world = new World ();
       world.setGravity(new Vector (0.0f, -9.81f));
       
     // We build our Entity:
       EntityBuilder entity1 = world.createEntityBuilder();
       entity1.setFixed(true);
       entity1.setPosition(new Vector (0.0f, 0.0f));
       body = entity1.build();
     // We instantiate our Entity's graphics
       graphBody1 = new ImageGraphics ("stone.broken.4.png" , 1, 1);
       graphBody1.setDepth(0.0f);
       graphBody1.setAlpha(1f);
       graphBody1.setParent(body);
       graphBody2 = new ImageGraphics ("bow.png", 1, 1);
       graphBody2.setDepth(1.0f);
       graphBody1.setAlpha(1f);
       graphBody2.setParent(body);
       
       
    
        return true;
    }

    // This event is called at each frame
    @Override
    public void update(float deltaTime) {
      // We simulate the physics world 
      world.update(deltaTime);
      // We set our view perspective
      window.setRelativeTransform (Transform.I.scaled(10.0f));
      // We render the Entity's graphics
      graphBody1.draw(window);
      graphBody2.draw(window);
    }

    // This event is raised after game ends, to release additional resources
    @Override
    public void end() {
        // Empty on purpose, no cleanup required yet
    }
    
}
