package ch.epfl.cs107.play.game.actor;
import java.util.ArrayList;
import java.util.Objects;

import com.sun.glass.events.KeyEvent;

import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.game.actor.BikeGame.general.ChoiceInterface;
import ch.epfl.cs107.play.game.actor.BikeGame.general.Finish;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.EntityBuilder;
import ch.epfl.cs107.play.math.Positionable;
import ch.epfl.cs107.play.math.RevoluteConstraint;
import ch.epfl.cs107.play.math.RevoluteConstraintBuilder;
import ch.epfl.cs107.play.math.RopeConstraint;
import ch.epfl.cs107.play.math.RopeConstraintBuilder;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.WheelConstraint;
import ch.epfl.cs107.play.math.WheelConstraintBuilder;
import ch.epfl.cs107.play.math.World;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Mouse;
import ch.epfl.cs107.play.window.Window;

public abstract class ActorGame implements Game {
	public ArrayList <Actor> actors;
	private World world;
	private Window window;
	private FileSystem fileSystem;
	
	private Vector viewCenter;
	private Vector viewTarget;
	
	private boolean beginGame = true;
	private static  boolean gameOver;

	//Attributes relative to the camera.
	private Positionable viewCandidate ;
	private static final float VIEW_TARGET_VELOCITY_COMPENSATION = 0.2f;
	private static final float VIEW_INTERPOLATION_RATIO_PER_SECOND = 0.1f;
	private static float VIEW_SCALE = 30.0f;
	
	/**
	 * @param entity: Actor to add to arrayList
	 * @param actors: Targeted ArrayList.
	 */
	public void addEntity (Actor entity, ArrayList <Actor> actors ) {
		actors.add(entity);
	}
	/**
	 * Overload of the method created to avoid creating the intrusive getter getActors().
	 * @param entity: Actor to add to the arayList actors.
	 */
	public void addEntity (Actor entity) {
		actors.add(entity);
	}
	
	/**
	 * 
	 * @param actor: Actor to be deleted using its .destroy() method.
	 * @param actors: Targeted ArrayList, destroyed actors are also removed.
	 */
	public void deleteEntity (Actor actor, ArrayList <Actor> actors) {
		actor.destroy ();
		actors.remove(actor);
	}
	
	/**
	 * Builds the world and the background tools for the game.
	 */
	public boolean begin(Window window, FileSystem fileSystem) {
		if(window == null || fileSystem == null)
			throw new NullPointerException("One of the arguments is null.");
		if(!(window instanceof Window) || !(fileSystem instanceof FileSystem))
			throw new IllegalArgumentException("One of the arguments is not valid");
		this.window = window;
		this.fileSystem = fileSystem;
		world = new World();
		world.setGravity(new Vector (0.0f, -9.81f));
		actors = new ArrayList <Actor>();
		viewCenter = Vector.ZERO;
		viewTarget = Vector.ZERO;
		return true;
	}
	
	/**
	 * Update method, updates everything relative to the game: actors and came.
	 * Runs the game.
	 */
	@Override
	public void update(float deltaTime) {
		
		//Begins the game after the interface indicates so.
		if(ChoiceInterface.getChosen() == true && beginGame) {
			begin(window, fileSystem);
			VIEW_SCALE = 15f;
			beginGame = false;
		}
		
		//Reset functionalities, available only available if the game has begun.
		else if (ChoiceInterface.getChosen() == true) {
			if(this.getPayLoad().getDestroy()) {
				gameOver = true;
			}
			
			if(getKeyboard().get(KeyEvent.VK_R).isPressed()) {
				reset();
			}
		}
		world.update(deltaTime);
		if (viewCandidate != null) 
			viewTarget = viewCandidate.getPosition().add(viewCandidate.getVelocity().mul(VIEW_TARGET_VELOCITY_COMPENSATION)) ;

		
			// Interpolate with previous location
		float ratio = (float)Math.pow(VIEW_INTERPOLATION_RATIO_PER_SECOND , deltaTime) ;
		viewCenter = viewCenter.mixed(viewTarget , 1.0f - ratio);
		
			// Compute new viewport
		Transform viewTransform = Transform.I.scaled(VIEW_SCALE).translated(viewCenter);
		window.setRelativeTransform(viewTransform);
		
		//Updating, drawing and destroying, if necessary, the actors.
		for (int i = 0; i < actors.size(); i++) {
			if(!actors.get(i).getDestroy()) {
				actors.get(i).update(deltaTime);
				actors.get(i).draw(window);
			}
			else {
				deleteEntity(actors.get(i), actors);
			}
		}
	}
	
	//Returns the center piece of the game.
	public GameEntity getPayLoad () {
		return (GameEntity) actors.get(0);
	}
	
	//Ends the game.
	@Override
	public void end() {
		for(int i = 0; i < actors.size();) {

			deleteEntity(actors.get(i), actors);
		}
	}
	
	/**
	 * Allows keyboard access.
	 * @return: the keyboard.
	 */
	public Keyboard getKeyboard(){
		return window.getKeyboard() ;
	}
	
	/**
	 * Allows access to the window through a canvas type.
	 * @return: returns the window.
	 */
	public Canvas getCanvas(){
		return window ;
	}
	
	/**
	 * Allows access to the mouse.
	 * @return: the mouse
	 */
	public Mouse getMouse() {
		return window.getMouse();
	}
	//Static getter that indicates if the game is over for concerned classes
	public static boolean getGameOver() {
		return gameOver;
	}
	
	//Static setter.
	public static void setGameOver(boolean over) {
		gameOver = over;
	}
	
	/**
	 * private method used to reset the game.
	 */
	private void reset() {
		end();
		Finish.setArrived(false);
		begin(window, fileSystem);
	}
	
	/**
	 * Allows classes to build entities.
	 * @param fixed: true if entity is fixed, false otherwise
	 * @param position: indicates start position of the created entity.
	 * @return
	 */
    public Entity entityBuilder (boolean fixed, Vector position) {
    	EntityBuilder entity = world.createEntityBuilder();
		entity.setFixed(fixed);
		entity.setPosition(position);
		return entity.build();
    }
    public void setViewCandidate (Positionable a){
    	viewCandidate = a;
    }
    
    //Overload of the method, entities will be created at the position (0,0).
    public Entity entityBuilder (boolean fixed) {
    	EntityBuilder entity = world.createEntityBuilder();
		entity.setFixed(fixed);
		return entity.build();
    }
    
    /**
     * 
     * @param vehicle: Main entity
     * @param anchor: Anchor point on the main entity.
     * @param axis of the constraint.
     * @param wheel: second entity
     * @param constraint: existing constraint.
     * @return
     */
	public WheelConstraint constraintBuilder (Entity vehicle , Vector anchor , Vector axis, Entity wheel, WheelConstraint constraint) {
		WheelConstraintBuilder constraintBuilder = world.createWheelConstraintBuilder() ;
		constraintBuilder.setFirstEntity(vehicle) ;
		constraintBuilder.setFirstAnchor(anchor) ;
		constraintBuilder.setSecondEntity(wheel) ;
		constraintBuilder.setSecondAnchor(Vector.ZERO) ;
		constraintBuilder.setAxis(axis) ;
		constraintBuilder.setFrequency(3.0f) ;
		constraintBuilder.setDamping(0.5f) ;
		constraintBuilder.setMotorMaxTorque(10.0f) ;
		constraintBuilder.setInternalCollision(true);
		constraint = constraintBuilder.build() ;
		return constraint;

	}
	
	/**
	 * Allows building rope constraints.
	 * @param attachPoint: first entity.
	 * @param object: second entity
	 * @param firstAnchorPoint: anchor point on the first entity.
	 * @param secondAnchorPoint: anchor point on the second entity.
	 * @param constraintLength: length of the constraint.
	 * @param internalCollision
	 * @return
	 */
	public RopeConstraint ropeConstraintBuilder (Entity attachPoint, Entity object, Vector firstAnchorPoint,
			Vector secondAnchorPoint, float constraintLength, boolean internalCollision) {
		RopeConstraintBuilder constraintBuilder =
        		world.createRopeConstraintBuilder();
        		constraintBuilder.setFirstEntity(attachPoint) ;
        		constraintBuilder.setFirstAnchor(firstAnchorPoint) ;
        		constraintBuilder.setSecondEntity(object) ;
        		constraintBuilder.setSecondAnchor(secondAnchorPoint) ;
        		constraintBuilder.setMaxLength(constraintLength) ;
        		constraintBuilder.setInternalCollision(internalCollision) ;
        		 RopeConstraint constraint = constraintBuilder.build();
        		 return constraint;

	}
	
	/**
	 * Allows building revolute constraints.
	 * @param firstEntity
	 * @param secondEntity
	 * @param firstEntityAttachPoint
	 * @param secondEntityAttachPoint
	 * @param internalCollision
	 * @return
	 */
	public RevoluteConstraint revoluteConstraintBuilder(Entity firstEntity, Entity secondEntity,
			Vector firstEntityAttachPoint, Vector secondEntityAttachPoint, boolean internalCollision) {
		 RevoluteConstraintBuilder revoluteConstraintBuilder = world.createRevoluteConstraintBuilder() ;
		 revoluteConstraintBuilder.setFirstEntity(firstEntity) ;
		 revoluteConstraintBuilder.setFirstAnchor(firstEntityAttachPoint) ;
		 revoluteConstraintBuilder.setSecondEntity(secondEntity) ;
		 revoluteConstraintBuilder.setSecondAnchor(secondEntityAttachPoint) ;
		 revoluteConstraintBuilder.setInternalCollision(true) ;
		 RevoluteConstraint constraint = revoluteConstraintBuilder.build();
		 return constraint;
	}
}
