package ch.epfl.cs107.play.game.actor.crate;


import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Window;

public class CrateGame extends ActorGame {
	
	private Crate crate1;
	private Crate crate2;
	private Crate crate3;
	@Override
	public boolean begin(Window window, FileSystem fileSystem) {
		super.begin(window, fileSystem);
		crate1 = new Crate(this, false, new Vector(0.0f, 5.0f) );
		addEntity(crate1, actors);
		crate2 = new Crate(this, false, new Vector(0.2f, 7.0f));
		addEntity(crate2, actors);
		crate3 = new Crate(this, false, new Vector(2.0f, 6.0f));
		addEntity(crate3, actors);

		
		return true;
	}

	@Override
	public void end() {
		// TODO Auto-generated method stub
		
	}

}
