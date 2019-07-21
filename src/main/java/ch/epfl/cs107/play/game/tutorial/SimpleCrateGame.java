package ch.epfl.cs107.play.game.tutorial;

import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.io.FileSystem;
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
public class SimpleCrateGame implements Game {

    // Store context
    private Window window;
    
    // We need our physics engine
      private World world;
      private Entity block;
      private Entity crate;
      private ImageGraphics blockGraphics;
      private ImageGraphics crateGraphics;
    
    
    // This event is raised when game has just started
    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        
        // Store context
       this.window = window;
       world = new World ();
       world.setGravity(new Vector (0.0f, -9.81f));
       
       // Building the first crate
       EntityBuilder crateBuilder = world.createEntityBuilder();
       crateBuilder.setFixed(true);
       crateBuilder.setPosition(new Vector (1.0f, 0.5f));
       block = crateBuilder.build();
       // Instantiating the first crate's graphics
       blockGraphics = new ImageGraphics ("crate.1.png" , 1, 1);
       blockGraphics.setDepth(0);
       blockGraphics.setParent(block);
       
       // Building the second crate
       EntityBuilder crateBuilder2 = world.createEntityBuilder();
       crateBuilder2.setFixed (false);
       crateBuilder2.setPosition(new Vector (0.8f, 20f));
       crate = crateBuilder2.build ();
       // Instantiating the second crate's graphics
       crateGraphics = new ImageGraphics ("crate.2.png" , 1, 1);
       crateGraphics.setDepth(0);
       crateGraphics.setParent(crate);
       
       
       // Building the geometry of the crates
       Polygon square = new Polygon(new Vector (0.0f, 1.0f), 
					new Vector (1.0f, 0.0f), 
					new Vector (1.0f, 1.0f), 
					new Vector (0.0f, 0.0f));
       
       // Assigning and Building the first crate's Part
       PartBuilder partCrate1 = block.createPartBuilder();
       partCrate1.setShape(square); 
       partCrate1.setFriction(0.5f);
       partCrate1.build();
       
    // Assigning and Building the second crate's Part
       PartBuilder partCrate2 = crate.createPartBuilder();
       partCrate2.setShape(square);
       partCrate2.setFriction(0.5f);
       partCrate2.build();

        return true;
    }

    // This event is called at each frame
    @Override
    public void update(float deltaTime) {
      // Updating the World  
      world.update(deltaTime);
      // setting the Camera
      window.setRelativeTransform (Transform.I.scaled(20.0f));
      // Rendering the Crates' Graphics
      blockGraphics.draw(window);
      crateGraphics.draw(window);
      
      
    }

    // This event is raised after game ends, to release additional resources
    @Override
    public void end() {
        // Empty on purpose, no cleanup required yet
    }
    
}