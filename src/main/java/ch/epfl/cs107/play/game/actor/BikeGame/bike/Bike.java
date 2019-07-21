

/*package ch.epfl.cs107.play.game.actor.BikeGame.bike;

import java.awt.Color;

import com.sun.glass.events.KeyEvent;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.game.actor.BikeGame.general.Finish;
import ch.epfl.cs107.play.math.BasicContactListener;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Contact;
import ch.epfl.cs107.play.math.ContactListener;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Bike extends GameEntity implements Actor {
	private Entity hitBox;
	private ShapeGraphics headGraphics;
	private ShapeGraphics rightArmGraphics;
	private ShapeGraphics leftArmGraphics;
	private ShapeGraphics backGraphics;
	private ShapeGraphics rightThighGraphics;
	private ShapeGraphics leftThighGraphics;
	private ShapeGraphics tibiaRightGraphics;
	private ShapeGraphics tibiaLeftGraphics;
	private Vector headLocation = new Vector(0f, 1.75f);
	private Vector shoulderLocation = new Vector(-0.2f, 1.55f);
	private Vector rightHandLocation = new Vector(0.5f, 1f);
	private Vector leftHandLocation = new Vector(0.5f, 1f);
	private Vector buttLocation = new Vector(-0.5f, 1f);
	private Vector leftKneeLocation = new Vector(0.f, 0.65f);
	private Vector rightKneeLocation = new Vector(0f, 0.65f);
	private Vector rightFootLocation = new Vector(-0.25f, 0f);
	private Vector leftFootLocation = new Vector(0.25f, 0f);
	private final Vector leftFootStartPosition = new Vector(0f, 0f);
	private final Vector leftKneeStartPosition = new Vector(0.f, 0.65f);
	private final Vector RightFootStartPosition = new Vector(0f, 0f);
	private final Vector RightKneeStartPosition = new Vector(0.f, 0.65f);
	
	private Polygon hitBoxShape = new Polygon(
			0.0f, 0.5f,
			0.5f, 1f,
			0.0f, 2.0f,
			-0.5f, 1f
			);
	private Circle head = new Circle (0.2f, getHeadLocation ());
	private Polyline rightArm = new Polyline (getShoulderLocation (), getRightHandLocation());
	private Polyline leftArm = new Polyline(getShoulderLocation(), getLeftHandLocation());
	private Polyline back = new Polyline (getShoulderLocation (), getButtLocation());
	private Polyline rightThigh = new Polyline (getButtLocation(), getRightKneeLocation());
	private Polyline leftThigh = new Polyline(getButtLocation(), getLeftKneeLocation());
	private Polyline tibiaRight = new Polyline (getRightKneeLocation(), getRightFootLocation());
	private Polyline tibiaLeft = new Polyline (getLeftKneeLocation(), getLeftFootLocation());
	private Wheel wheelRight;
	private Wheel wheelLeft;
	private static float boostCoefficient = 1;
	protected static final float MAX_WHEEL_SPEED = 20f;
	private boolean orientationToTheRight = true;
	private ContactListener listener;
	private double angleOfRotation = 0;
	private BasicContactListener jumpListener;

	private final Vector rightHandEndPosition = new Vector(1f, 2.1F);
	private final Vector leftHandEndPosition = new Vector(-1f, 2.1f);

	
	public boolean compareHitbox(Entity entity) {
		if(entity == hitBox)
			return true;
		else
			return false;
	}
	public void applyImpulse(Vector vector) {
		hitBox.applyImpulse(vector, null);
	}
	
	public boolean compareWheel(Entity entity) {
		if(wheelRight.compareWheel(entity) || wheelLeft.compareWheel(entity))
			return true;
		else
			return false;
	}
		
	public Bike(ActorGame game, boolean fixed, Vector position) {
		super(game, fixed, position);
		hitBox = super.getEntity();
		boostCoefficient = 1;
		rintln(position.x + "" + position.getX() );
		entityPart (hitBox);
		entityGraphics(hitBox);
		wheelRight = new Wheel (game, false,  new Vector (position.x + 1f, position.y), 0.5f);
		wheelLeft = new Wheel (game, false, new Vector (position.x - 1f, position.y), 0.5f);
		attachWheelsToBike();
		jumpListener = new BasicContactListener();
		listener = new ContactListener() {
			@Override
			public void beginContact(Contact contact) {
				if (contact.getOther().isGhost())
						return ;
				else if (wheelRight.compareWheel(contact.getOther().getEntity()) 
						|| wheelLeft.compareWheel(contact.getOther().getEntity())) {
				
					return ;
				}
				destroy = true;
			}
			@Override
			public void endContact(Contact contact) {}
		};
		hitBox.addContactListener(listener);
		wheelRight.addBasicContactListner(jumpListener);
		wheelLeft.addBasicContactListner(jumpListener);
	}
	public Bike(ActorGame game, boolean fixed) {
		super(game, fixed);
		hitBox = super.getEntity();
		entityPart (hitBox);
		entityGraphics(hitBox);
		wheelRight = new Wheel (game, false, new Vector (1f, 0f), 0.5f);
		wheelLeft = new Wheel (game, false, new Vector (- 1f, 0f), 0.5f);
		attachWheelsToBike();
		listener = new ContactListener() {
			@Override
			public void beginContact(Contact contact) {
			if (contact.getOther().isGhost()) {
			return;
			}
			else if (wheelRight.compareWheel(contact.getOther().getEntity()) 
					|| wheelLeft.compareWheel(contact.getOther().getEntity())) {
			return ;
			}
			destroy = true;
			}
			@Override
			public void endContact(Contact contact) {}
		};
		hitBox.addContactListener(listener);
			
	}
	@Override
	public void draw(Canvas canvas) {
		headGraphics.draw(canvas);
		backGraphics.draw(canvas);
		leftArmGraphics.draw(canvas);
		rightArmGraphics.draw(canvas);
		leftThighGraphics.draw(canvas);
		rightThighGraphics.draw(canvas);
		tibiaRightGraphics.draw(canvas);
		tibiaLeftGraphics.draw(canvas);
		wheelRight.draw(canvas);
		wheelLeft.draw(canvas);
		
	}

	@Override
	public Transform getTransform() {
		return hitBox.getTransform();
		
	}

	@Override
	public Vector getVelocity() {
		return hitBox.getVelocity();
	}

	@Override
	protected void entityGraphics(Entity entity) {
		headGraphics = new ShapeGraphics (head, Color.YELLOW, Color.YELLOW, 0f);
		rightArmGraphics = new ShapeGraphics (rightArm, Color.YELLOW, Color.YELLOW, 0.1f);
		leftArmGraphics = new ShapeGraphics (leftArm, Color.YELLOW, Color.YELLOW, 0.1f);
		backGraphics = new ShapeGraphics (back, Color.YELLOW, Color.YELLOW, 0.1f);
		leftThighGraphics = new ShapeGraphics(leftThigh, Color.YELLOW, Color.YELLOW, 0.1f);
		rightThighGraphics = new ShapeGraphics (rightThigh, Color.YELLOW, Color.YELLOW, 0.1f); 
		tibiaRightGraphics = new ShapeGraphics (tibiaRight, Color.YELLOW, Color.YELLOW, 0.1f);
		tibiaLeftGraphics = new ShapeGraphics (tibiaLeft, Color.YELLOW, Color.YELLOW, 0.1f);
		headGraphics.setParent(hitBox);
		rightArmGraphics.setParent(hitBox);
		leftArmGraphics.setParent(hitBox);
		backGraphics.setParent(hitBox);
		rightThighGraphics.setParent(hitBox);
		leftThighGraphics.setParent(hitBox);
		tibiaRightGraphics.setParent(hitBox);
		tibiaLeftGraphics.setParent(hitBox);
		
		
	}
	public Vector getHeadLocation () {
		return new Vector(headLocation.getX(), headLocation.getY());
	}
	public Vector getShoulderLocation () {
		return new Vector (shoulderLocation.getX(), shoulderLocation.getY());
	}
	public Vector getButtLocation() {
		return new Vector (buttLocation.getX(), buttLocation.getY());
	}
	public Vector getRightHandLocation() {
		return new Vector (rightHandLocation.getX(), rightHandLocation.getY());
	}
	public Vector getLeftHandLocation() {
		return new Vector(leftHandLocation.getX(), leftHandLocation.getY());
	}
	public Vector getRightKneeLocation() {
		return new Vector (rightKneeLocation.getX(), rightKneeLocation.getY());
	}
	public Vector getLeftKneeLocation() {
		return new Vector (leftKneeLocation.getX(), leftKneeLocation.getY());
	}
	public Vector getRightFootLocation () {
		return new Vector (rightFootLocation.getX(), rightFootLocation.getY());
	}
	public Vector getLeftFootLocation () {
		return new Vector (leftFootLocation.getX(), leftFootLocation.getY());
	}
	
	public void setHeadLocation(Vector vector) {
		headLocation = new Vector(vector.getX(), vector.getY());
	}
	public void setShoulderLocation(Vector vector) {
		shoulderLocation = new Vector(vector.getX(), vector.getY());
	}
	public void setButtLocation(Vector vector) {
		buttLocation = new Vector(vector.getX(), vector.getY());
	}
	public void setRightHandLocation(Vector vector) {
		rightHandLocation = new Vector(vector.getX(), vector.getY());
	}
	public void setLeftHandLocation(Vector vector) {
		leftHandLocation = new Vector(vector.getX(), vector.getY());
	}
	public void setRightKneeLocation(Vector vector) {
		rightKneeLocation = new Vector(vector.getX(), vector.getY());
	}
	public void setLeftKneeLocation(Vector vector) {
		leftKneeLocation = new Vector(vector.getX(), vector.getY());
	}
	public void setRightFootLocation(Vector vector) {
		rightFootLocation = new Vector(vector.getX(), vector.getY());
	}
	public void setLeftFootLocation(Vector vector) {
		leftFootLocation = new Vector(vector.getX(), vector.getY());
	}
	@Override
	protected void entityPart(Entity entity) {
		PartBuilder hitBoxPart = entity.createPartBuilder();
		hitBoxPart.setShape(hitBoxShape);
		hitBoxPart.build();
	}
	public static void setBoostCoefficient(float value) {
		boostCoefficient = value;
	}
	
	@Override
	public boolean getDestroy() {
		return destroy;
	}
	
	
	private void attachWheelsToBike () {
		wheelLeft.attach(hitBox , new Vector(-1.0f, 0.0f), new
			Vector(-0.5f, -1.0f)) ;
		wheelRight.attach(hitBox , new Vector(1.0f, 0.0f), new
			Vector(0.5f, -1.0f)) ;
	}
	public void reorient () {
		setHeadLocation(getHeadLocation().mirrored(new Vector(1f, 0f)));
		setShoulderLocation(getShoulderLocation().mirrored(new Vector(1f, 0f)));
		setRightHandLocation(getRightHandLocation().mirrored(new Vector(1f, 0f)));
		setLeftHandLocation(getLeftHandLocation().mirrored(new Vector(1f, 0f)));
		setButtLocation(getButtLocation().mirrored(new Vector(1f, 0f)));
		setRightKneeLocation(getRightKneeLocation().mirrored(new Vector(1f, 0f)));
		setLeftKneeLocation(getLeftKneeLocation().mirrored(new Vector(1f, 0f)));
		setRightFootLocation(getRightFootLocation().mirrored(new Vector(1f, 0f)));
		setLeftFootLocation(getLeftFootLocation().mirrored(new Vector(1f, 0f)));
		head = new Circle (0.2f, getHeadLocation ());
		leftArm = new Polyline(getShoulderLocation(), getLeftHandLocation());
		rightArm = new Polyline (getShoulderLocation (), getRightHandLocation());
		back = new Polyline (getShoulderLocation (), getButtLocation());
		rightThigh = new Polyline (getButtLocation(), getRightKneeLocation());
		leftThigh = new Polyline(getButtLocation(), getLeftKneeLocation());
		tibiaRight = new Polyline (getRightKneeLocation(), getRightFootLocation());
		tibiaLeft = new Polyline (getLeftKneeLocation(), getLeftFootLocation());
		entityGraphics(hitBox);
		
	}

	public void update (float deltatime) {
		if (Finish.getArrived()) {
			if (getRightHandLocation().getX() <= rightHandEndPosition.getX()) {
				setRightHandLocation(new Vector(getRightHandLocation().getX() + 0.01f, getRightHandLocation().getY()));
				rightArm = new  Polyline(getShoulderLocation(), getRightHandLocation());
			}
			if(getRightHandLocation().getY() <= rightHandEndPosition.getY()) {
				setRightHandLocation(new Vector(getRightHandLocation().getX(), getRightHandLocation().getY() + 0.01f));
				rightArm = new Polyline(getShoulderLocation(), getRightHandLocation());
			}
			if (getLeftHandLocation().getX() >= leftHandEndPosition.getX()) {
				setLeftHandLocation(new Vector(getLeftHandLocation().getX() - 0.01f, getLeftHandLocation().getY()));
				leftArm = new  Polyline(getShoulderLocation(), getLeftHandLocation());
			}
			if(getLeftHandLocation().getY() <= leftHandEndPosition.getY()) {
				setLeftHandLocation(new Vector(getLeftHandLocation().getX(), getLeftHandLocation().getY() + 0.01f));
				leftArm = new Polyline(getShoulderLocation(), getLeftHandLocation());
			}
			
			entityGraphics(hitBox);
		}
		else {
			if (getOwner().getKeyboard().get(KeyEvent.VK_SPACE ).isPressed()) {
				orientationToTheRight = !orientationToTheRight;
				reorient();
			}
			if(!jumpListener.getEntities().isEmpty()
					&& getOwner().getKeyboard().get(KeyEvent.VK_Z).isPressed()) {
				hitBox.applyImpulse(new Vector(0f, 10f), null);
			}
			wheelRight.relax();
			wheelLeft.relax();
			if (orientationToTheRight) {
				if(getOwner().getKeyboard().get(KeyEvent.VK_UP ).isDown()) {
					if (wheelLeft.getSpeed() > -MAX_WHEEL_SPEED  *  boostCoefficient ) {
						wheelLeft.power(MAX_WHEEL_SPEED  *  boostCoefficient);
						angleOfRotation = (angleOfRotation - 0.03 * wheelLeft.getAngularVelocity()/15) % (Math.PI * 2);
						setLeftFootLocation(new Vector((float)Math.sin(angleOfRotation)*(0.2f) - leftFootStartPosition.getX(), 
											(float)Math.cos(angleOfRotation)*0.2f - leftFootStartPosition.getY()));
						setLeftKneeLocation(new Vector((float)Math.sin(angleOfRotation)*(0.2f) + leftKneeStartPosition.getX(), 
											(float)Math.cos(angleOfRotation)*0.2f + leftKneeStartPosition.getY()));
						setRightFootLocation(new Vector((float)Math.sin(angleOfRotation+ + Math.PI)*(0.2f) - RightFootStartPosition.getX(), 
											(float)Math.cos(angleOfRotation+ Math.PI)*0.2f - RightFootStartPosition.getY()));
						setRightKneeLocation(new Vector((float)Math.sin(angleOfRotation+ Math.PI)*(0.2f) + RightKneeStartPosition.getX(), 
											(float)Math.cos(angleOfRotation + Math.PI)*0.2f + RightKneeStartPosition.getY()));
						
						rightThigh = new Polyline (getButtLocation(), getRightKneeLocation());
						leftThigh = new Polyline(getButtLocation(), getLeftKneeLocation());
						tibiaRight = new Polyline (getRightKneeLocation(), getRightFootLocation());
						tibiaLeft = new Polyline (getLeftKneeLocation(), getLeftFootLocation());
						entityGraphics(hitBox);
					}
					else {
						wheelLeft.relax();
					}
					
				}
				if(getOwner().getKeyboard().get(KeyEvent.VK_DOWN ).isDown()) {
					wheelLeft.power(0f);
					wheelRight.power(0.0f);
					
				}
				if(getOwner().getKeyboard().get(KeyEvent.VK_RIGHT ).isDown()) {
					hitBox.applyAngularForce(-20f);
					
				}
				if(getOwner().getKeyboard().get(KeyEvent.VK_LEFT ).isDown()) {
					hitBox.applyAngularForce(20f);
					
				}
			}
			else {
				if(getOwner().getKeyboard().get(KeyEvent.VK_UP ).isDown()) {
					if (wheelRight.getSpeed() < MAX_WHEEL_SPEED  *  boostCoefficient) {
						wheelRight.power(-MAX_WHEEL_SPEED  * boostCoefficient);
						
						angleOfRotation = ((angleOfRotation + 0.03 * wheelRight.getAngularVelocity()/15)) % (Math.PI * 2);
						setLeftFootLocation(new Vector((float)Math.cos(angleOfRotation)*(0.2f) - leftFootStartPosition.getX(), 
											(float)Math.sin(angleOfRotation)*0.2f - leftFootStartPosition.getY()));
						setLeftKneeLocation(new Vector((float)Math.cos(angleOfRotation)*(0.2f) + leftKneeStartPosition.getX(), 
											(float)Math.sin(angleOfRotation)*0.2f + leftKneeStartPosition.getY()));
						setRightFootLocation(new Vector((float)Math.cos(angleOfRotation+ + Math.PI)*(0.2f) - RightFootStartPosition.getX(), 
											(float)Math.sin(angleOfRotation+ Math.PI)*0.2f - RightFootStartPosition.getY()));
						setRightKneeLocation(new Vector((float)Math.cos(angleOfRotation+ Math.PI)*(0.2f) + RightKneeStartPosition.getX(), 
											(float)Math.sin(angleOfRotation + Math.PI)*0.2f + RightKneeStartPosition.getY()));
						
						rightThigh = new Polyline (getButtLocation(), getRightKneeLocation());
						leftThigh = new Polyline(getButtLocation(), getLeftKneeLocation());
						tibiaRight = new Polyline (getRightKneeLocation(), getRightFootLocation());
						tibiaLeft = new Polyline (getLeftKneeLocation(), getLeftFootLocation());
						entityGraphics(hitBox);
					}
					}
					else {
						wheelRight.relax();
				    }
				}
				
				if(getOwner().getKeyboard().get(KeyEvent.VK_DOWN ).isDown()) {
					wheelRight.power(0.0f);
					wheelLeft.power(0.0f);
				}
					
				if(getOwner().getKeyboard().get(KeyEvent.VK_RIGHT ).isDown()) {
					hitBox.applyAngularForce(-20f);
				}
				if(getOwner().getKeyboard().get(KeyEvent.VK_LEFT ).isDown()) {
					hitBox.applyAngularForce(20f);
				}
			}
		}

	@Override
	public void destroy() {
		hitBox.destroy();
		wheelRight.destroy();
		wheelLeft.destroy();
		
	}
}*/
package ch.epfl.cs107.play.game.actor.BikeGame.bike;

		import com.sun.glass.events.KeyEvent;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.BikeGame.BikeGame;
import ch.epfl.cs107.play.game.actor.BikeGame.general.Finish;
import ch.epfl.cs107.play.math.BasicContactListener;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Contact;
import ch.epfl.cs107.play.math.ContactListener;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Bike extends GameEntity implements Actor {
	private Entity hitBox;

	//Declaring attributes for the cyclist graphics.
	private ImageGraphics headGraphics;
	private ImageGraphics rightArmGraphics;
	private ImageGraphics leftArmGraphics;
	private ImageGraphics backGraphics;
	private ImageGraphics rightThighGraphics;
	private ImageGraphics leftThighGraphics;
	private ImageGraphics tibiaRightGraphics;
	private ImageGraphics tibiaLeftGraphics;
	private double armAngle = 0;
	
	//initial positions of the character body part location. 
	private Vector headLocation = new Vector(0f, 1.75f).add(new Vector (-0.2f, 0f));
	private Vector shoulderLocation = new Vector(-0.2f, 1.55f);
	private Vector rightHandLocation = new Vector(0.5f, 1f).add(new Vector (0.15f, 0f));
	private Vector leftHandLocation = new Vector(0.5f, 1f).add(new Vector (0.15f, 0f));
	private Vector buttLocation = new Vector(-0.5f, 1f).add(new Vector (-0.1f, 0f));
	private Vector leftKneeLocation = new Vector(0.f, 0.8f).add(new Vector (-0.1f,0f));
	private Vector rightKneeLocation = new Vector(0f, 0.8f).add(new Vector (-0.1f,0f));
	private Vector rightFootLocation = new Vector(-0.25f, 0f).add(new Vector (-0.1f, 0.21f));
	private Vector leftFootLocation = new Vector(0.25f, 0f).add(new Vector(-0.01f, 0.07f));
	
	//Geometry of the hitBox.
	private Polygon hitBoxShape = new Polygon(
			0.0f, 0.5f,
			0.5f, 1f,
			0.0f, 2.0f,
			-0.5f, 1f
			);
	
	//Attributes relative to the wheels.
	private Wheel wheelRight;
	private Wheel wheelLeft;
	private static float boostCoefficient = 1;
	private BasicContactListener jumpListener;
	private static final float MAX_WHEEL_SPEED = 20f;
	private boolean orientationToTheRight = true;
	
	private int compteur = 0;
	int compteur1 = 0;
	int compteur2 = 0;
	private ContactListener listener;
	private boolean drawn = false;
	private String player;
	

	private double angleOfRotation = 0.0f;
	private double angleOfElevation = 0.0f;
	
	private final Vector leftKneeStartPosition = new Vector(0.f, 0.65f).add(new Vector (-0.1f,0f));
	private final Vector RightKneeStartPosition = new Vector(0.f, 0.65f).add(new Vector(-0.01f, 0.07f));
	private final Vector RightKneeEndPosition = new Vector(0.f, 0.65f).add(new Vector(-0.01f, 0.07f)).add(new Vector (0f, 0.2f));
	
	
		
		
	public Bike(ActorGame game, boolean fixed, Vector position) {
		super(game, fixed, position);
		boostCoefficient = 1;
		hitBox = super.getEntity();
		
		
		entityPart (hitBox);
		entityGraphics(hitBox);
		
		wheelRight = new Wheel (game, false,  new Vector (position.x + 1f, position.y), 0.5f);
		wheelLeft = new Wheel (game, false, new Vector (position.x - 1f, position.y), 0.5f);
		attachWheelsToBike();
		
		jumpListener = new BasicContactListener();
		wheelRight.addBasicContactListner(jumpListener);
		wheelLeft.addBasicContactListner(jumpListener);
		
		//Building the listener for the hitbox.
		listener = new ContactListener() {
			@Override
			public void beginContact(Contact contact) {
				if (contact.getOther().isGhost())
						return ;
				else if (wheelRight.compareWheel(contact.getOther().getEntity()) 
						|| wheelLeft.compareWheel(contact.getOther().getEntity())) {
				
					return ;
				}
				ActorGame.setGameOver(true);
				destroy = true;
			}
			@Override
			public void endContact(Contact contact) {}
		};
		hitBox.addContactListener(listener);

	}
	public Bike(ActorGame game, boolean fixed) {
		super(game, fixed);
		hitBox = super.getEntity();
		boostCoefficient = 1;
		entityPart (hitBox);
		entityGraphics(hitBox);
		wheelRight = new Wheel (game, false, new Vector (1f, 0f), 0.5f);
		wheelLeft = new Wheel (game, false, new Vector (- 1f, 0f), 0.5f);
		attachWheelsToBike();
			
	}
	public Bike(ActorGame game, boolean fixed, Vector position, int i) {
		super(game, fixed, position);
		hitBox = super.getEntity();
		entityPart (hitBox);
		
		//Switch to define the cyclist appearance depending on the interface output
		switch (i) {
			case 1: {
				player = "male";
				entityGraphics(hitBox);
				drawn = true;
				break;
			}
			case 2:{
				player = "female";
				entityGraphics(hitBox);
				drawn = true;
				break;
			}
			case 3: {
				player = "zombie";
				entityGraphics(hitBox);
				drawn = true;
				break;
			}
		}
		wheelRight = new Wheel (game, false,  new Vector (position.x + 1f, position.y), 0.5f);
		wheelLeft = new Wheel (game, false, new Vector (position.x - 1f, position.y), 0.5f);
		attachWheelsToBike();
		
		jumpListener = new BasicContactListener();
		wheelRight.addBasicContactListner(jumpListener);
		wheelLeft.addBasicContactListner(jumpListener);
		
		//Building the listener for the wheels.
		listener = new ContactListener() {
			@Override
			public void beginContact(Contact contact) {
				if (contact.getOther().isGhost())
						return ;
				else if (wheelRight.compareWheel(contact.getOther().getEntity()) 
						|| wheelLeft.compareWheel(contact.getOther().getEntity())) {
				
					return ;
				}
				ActorGame.setGameOver(true);
				destroy = true;
			}
			@Override
			public void endContact(Contact contact) {}
		};
		hitBox.addContactListener(listener);

	}
	
	//Draw method, draws  all graphics relative to the bike and the cyclist.
	@Override
	public void draw(Canvas canvas) {
		headGraphics.draw(canvas);
		backGraphics.draw(canvas);
		leftArmGraphics.draw(canvas);
		rightArmGraphics.draw(canvas);
		leftThighGraphics.draw(canvas);
		rightThighGraphics.draw(canvas);
		tibiaRightGraphics.draw(canvas);
		tibiaLeftGraphics.draw(canvas);
		wheelRight.draw(canvas);
		wheelLeft.draw(canvas);
	}
	
	@Override
	public Transform getTransform() {
		return hitBox.getTransform();
		
	}
	
	@Override
	public Vector getVelocity() {
		return hitBox.getVelocity();
	}
	
	@Override
	protected void entityGraphics(Entity entity) {
		headGraphics = new ImageGraphics ( player + "_head_reversed.png", 0.4f, 0.4f);
		headGraphics.setRelativeTransform(Transform.I.translated(getHeadLocation()));
		rightArmGraphics = new ImageGraphics (player + "_arm.png", 0.2f, 1.1f);
		rightArmGraphics.setRelativeTransform(Transform.I.rotated(1.1f).translated(getRightHandLocation()));
		leftArmGraphics = new ImageGraphics (player + "_arm.png", 0.2f, 1.1f);
		leftArmGraphics.setRelativeTransform(Transform.I.rotated(1.1f).translated(getLeftHandLocation()));
		backGraphics = new ImageGraphics (player  + "_body.png", 0.4f, 0.62f);
		backGraphics.setRelativeTransform(Transform.I.rotated(-0.5f).translated(getButtLocation()));
		leftThighGraphics = new ImageGraphics("male_thigh.png", 0.2f, -0.7f);
		leftThighGraphics.setRelativeTransform(Transform.I.rotated(1.1f).translated(getButtLocation()));
		rightThighGraphics = new ImageGraphics("male_thigh.png", 0.2f, -0.7f);
		rightThighGraphics.setRelativeTransform(Transform.I.rotated(1.1f).translated(getButtLocation()));
		tibiaRightGraphics = new ImageGraphics(player + "_leg.png", 0.2f, -0.7f);
		tibiaRightGraphics.setRelativeTransform(Transform.I.translated(getRightKneeLocation()));
		tibiaLeftGraphics = new ImageGraphics(player + "_leg.png", 0.2f, -0.7f);
		tibiaLeftGraphics.setRelativeTransform(Transform.I.translated(getLeftKneeLocation()));
		headGraphics.setParent(hitBox);
		rightArmGraphics.setParent(hitBox);
		leftArmGraphics.setParent(hitBox);
		backGraphics.setParent(hitBox);
		rightThighGraphics.setParent(hitBox);
		leftThighGraphics.setParent(hitBox);
		tibiaRightGraphics.setParent(hitBox);
		tibiaLeftGraphics.setParent(hitBox);
	}
	
	//Getters and setters for the position of the head and the limbs.
	private Vector getHeadLocation () {
		return new Vector(headLocation.getX(), headLocation.getY());
	}
	private Vector getShoulderLocation () {
		return new Vector (shoulderLocation.getX(), shoulderLocation.getY());
	}
	private Vector getButtLocation() {
		return new Vector (buttLocation.getX(), buttLocation.getY());
	}
	private Vector getRightHandLocation() {
		return new Vector (rightHandLocation.getX(), rightHandLocation.getY());
	}
	private Vector getLeftHandLocation() {
		return new Vector(leftHandLocation.getX(), leftHandLocation.getY());
	}
	private Vector getRightKneeLocation() {
		return new Vector (rightKneeLocation.getX(), rightKneeLocation.getY());
	}
	private Vector getLeftKneeLocation() {
		return new Vector (leftKneeLocation.getX(), leftKneeLocation.getY());
	}
	public Vector getRightFootLocation () {
		return new Vector (rightFootLocation.getX(), rightFootLocation.getY());
	}
	private Vector getLeftFootLocation () {
		return new Vector (leftFootLocation.getX(), leftFootLocation.getY());
	}
	
	private void setHeadLocation(Vector vector) {
		headLocation = new Vector(vector.getX(), vector.getY());
	}
	private void setShoulderLocation(Vector vector) {
		shoulderLocation = new Vector(vector.getX(), vector.getY());
	}
	private void setButtLocation(Vector vector) {
		buttLocation = new Vector(vector.getX(), vector.getY());
	}
	private void setRightHandLocation(Vector vector) {
		rightHandLocation = new Vector(vector.getX(), vector.getY());
	}
	private void setLeftHandLocation(Vector vector) {
		leftHandLocation = new Vector(vector.getX(), vector.getY());
	}
	private void setRightKneeLocation(Vector vector) {
		rightKneeLocation = new Vector(vector.getX(), vector.getY());
	}
	private void setLeftKneeLocation(Vector vector) {
		leftKneeLocation = new Vector(vector.getX(), vector.getY());
	}
	private void setRightFootLocation(Vector vector) {
		rightFootLocation = new Vector(vector.getX(), vector.getY());
	}
	private void setLeftFootLocation(Vector vector) {
		leftFootLocation = new Vector(vector.getX(), vector.getY());
	}
	
	//Method to build the part associated to the hitbox.
	@Override
	protected void entityPart(Entity entity) {
		PartBuilder hitBoxPart = entity.createPartBuilder();
		hitBoxPart.setShape(hitBoxShape);
		hitBoxPart.build();
	}
	
	//Public method that allows comparing entities with the cyclist entity for contact listeners.
	public boolean compareHitbox(Entity entity) {
		if(entity == hitBox)
			return true;
		else
			return false;
	}
	
	//Public method that allows applying a force to the cyclist.
	public void applyImpulse(Vector vector) {
		hitBox.applyImpulse(vector, null);
	}
	
	//Public method that allows comparing an entity with the those of the wheels.
	public boolean compareWheel(Entity entity) {
		if(wheelRight.compareWheel(entity) || wheelLeft.compareWheel(entity))
			return true;
		else
			return false;
	}
	
	@Override
	public boolean getDestroy() {
		return destroy;
	}
	
	//static method to modify the boost value
	public static void setBoostCoefficient(float boost) {
		boostCoefficient = boost;
	}
	
	//private method to create the constraint between the wheels and the hitBox.
	private void attachWheelsToBike () {
		wheelLeft.attach(hitBox , new Vector(-1.0f, 0.0f), new
			Vector(-0.5f, -1.0f)) ;
		wheelRight.attach(hitBox , new Vector(1.0f, 0.0f), new
			Vector(0.5f, -1.0f)) ;
	}
	
	//Allows for the cyclist to change the direction he is facing.
	private void reorient () {
		headGraphics = new ImageGraphics (player + "_head.png", 0.4f, 0.4f);
		headGraphics.setRelativeTransform(Transform.I.translated(getHeadLocation()));
		rightArmGraphics = new ImageGraphics (player + "_arm.png", -0.2f, 1.1f);
		rightArmGraphics.setRelativeTransform(Transform.I.rotated(-1.1f).translated(getRightHandLocation().mirrored(new Vector (1f,0f))));
		leftArmGraphics = new ImageGraphics (player + "_arm.png", -0.2f, 1.1f);
		leftArmGraphics.setRelativeTransform(Transform.I.rotated(-1.1f).translated(getLeftHandLocation().mirrored(new Vector (1f,0f))));
		backGraphics = new ImageGraphics (player + "_body.png", -0.4f, 0.62f);
		backGraphics.setRelativeTransform(Transform.I.rotated(0.5f).translated(getButtLocation().mirrored(new Vector (1f, 0f))));
		leftThighGraphics = new ImageGraphics("male_thigh.png", -0.2f, -0.7f);
		leftThighGraphics.setRelativeTransform(Transform.I.rotated(-1.1f).translated(getButtLocation().mirrored(new Vector (1f,0f))));
		rightThighGraphics = new ImageGraphics("male_thigh.png", -0.2f, -0.7f);
		rightThighGraphics.setRelativeTransform(Transform.I.rotated(-1.1f).translated(getButtLocation().mirrored(new Vector (1f,0f))));
		tibiaRightGraphics = new ImageGraphics(player + "_leg.png", -0.2f, -0.7f);
		tibiaRightGraphics.setRelativeTransform(Transform.I.translated(getRightKneeLocation().mirrored(new Vector (1f,0f))));
		tibiaLeftGraphics = new ImageGraphics(player + "_leg.png", -0.2f, -0.7f);
		tibiaLeftGraphics.setRelativeTransform(Transform.I.translated(getLeftKneeLocation().mirrored(new Vector (1f,0f))));
		headGraphics.setParent(hitBox);
		rightArmGraphics.setParent(hitBox);
		leftArmGraphics.setParent(hitBox);
		backGraphics.setParent(hitBox);
		rightThighGraphics.setParent(hitBox);
		leftThighGraphics.setParent(hitBox);
		tibiaRightGraphics.setParent(hitBox);
		tibiaLeftGraphics.setParent(hitBox);
		
	}
	
	
	public void update (float deltatime) { 
		
		//Animations when the cyclist arrives.
		if (Finish.getArrived()) {
			if (orientationToTheRight) {
				rightArmGraphics = new ImageGraphics ("male_arm.png", 0.2f, 1.1f);
				rightArmGraphics.setRelativeTransform(Transform.I.rotated((float)armAngle).translated(getRightHandLocation()));
				leftArmGraphics = new ImageGraphics ("male_arm.png", 0.2f, 1.1f);
				leftArmGraphics.setRelativeTransform(Transform.I.rotated((float)armAngle ).translated(getLeftHandLocation()));
				rightArmGraphics.setParent(hitBox);
				leftArmGraphics.setParent(hitBox);
			}
			else {
					rightArmGraphics = new ImageGraphics ("male_arm_reversed.png", -0.2f, 1.1f);
					rightArmGraphics.setRelativeTransform(Transform.I.translated(getShoulderLocation().mirrored(new Vector (1f,0f))));
					leftArmGraphics = new ImageGraphics ("male_arm_reversed.png", -0.2f, 1.1f);
					leftArmGraphics.setRelativeTransform(Transform.I.translated(getShoulderLocation().mirrored(new Vector (1f,0f))));
					rightArmGraphics.setParent(hitBox);
					leftArmGraphics.setParent(hitBox);
			}
		}
		
		//Movement of the cyclist.
		else {
			if (getOwner().getKeyboard().get(KeyEvent.VK_SPACE ).isPressed()) {
				orientationToTheRight = !orientationToTheRight;
				if (orientationToTheRight) {
					
					entityGraphics(hitBox);
					
				}
				if (!orientationToTheRight) {
					reorient();
				}
			}
			wheelRight.relax();
			wheelLeft.relax();
			if(!jumpListener.getEntities().isEmpty() && getOwner().getKeyboard().get(KeyEvent.VK_Z).isPressed()) {
				hitBox.applyImpulse(new Vector(0f, 12f), null);
			}
			if (orientationToTheRight) {
				if(getOwner().getKeyboard().get(KeyEvent.VK_UP ).isDown()) {
					if (wheelLeft.getSpeed() > -MAX_WHEEL_SPEED  *  boostCoefficient) {
						wheelLeft.power(MAX_WHEEL_SPEED  *  boostCoefficient);
						angleOfRotation = (angleOfRotation - 0.03 * wheelLeft.getAngularVelocity() /20) % (2*Math.PI);
						if (angleOfElevation <= (Math.asin(0.2f/RightKneeEndPosition.sub(getButtLocation()).getLength())) && compteur % 2 ==1 ) {
							angleOfElevation += 0.0073;
						}
						if ( angleOfElevation > (Math.asin(0.2f/RightKneeEndPosition.sub(getButtLocation()).getLength())) && compteur % 2 == 1) {
							compteur ++;
							compteur = compteur % 2;
							rightThighGraphics = new ImageGraphics("male_thigh.png", 0.2f, getRightKneeLocation().sub(getButtLocation()).getLength());
							rightThighGraphics.setRelativeTransform(Transform.I.rotated((float)(1.1f + Math.asin(0.2f/RightKneeEndPosition.sub(getButtLocation()).getLength()))));
							rightThighGraphics.setParent(hitBox);
							angleOfElevation = Math.asin(0.2f/RightKneeEndPosition.sub(getButtLocation()).getLength());
							
						}
						if (angleOfElevation <= Math.asin(0.2f/RightKneeEndPosition.sub(getButtLocation()).getLength()) && compteur % 2 == 0) {
							angleOfElevation -= 0.0073;
							compteur2++;
							
						}
						if ( angleOfElevation < - Math.asin(0.2f/RightKneeEndPosition.sub(getButtLocation()).getLength()) && compteur % 2 == 0 ) {
							compteur ++;
							compteur2 = 0;
							compteur = compteur % 2;
							rightThighGraphics = new ImageGraphics("male_thigh.png", 0.2f, getRightKneeLocation().sub(getButtLocation()).getLength()-0.2f);
							rightThighGraphics.setRelativeTransform(Transform.I.rotated((float)(1.1f - Math.asin(0.2f/RightKneeEndPosition.sub(getButtLocation()).getLength()))).translated(getButtLocation()));
							rightThighGraphics.setParent(hitBox);
						}
						setLeftKneeLocation(new Vector((float)Math.sin(angleOfRotation)*(0.2f) + leftKneeStartPosition.getX(), 
											(float)Math.cos(angleOfRotation)*0.2f + leftKneeStartPosition.getY()));
						setRightKneeLocation(new Vector((float)Math.sin(angleOfRotation+ Math.PI)*(0.2f) + RightKneeStartPosition.getX(), 
											(float)Math.cos(angleOfRotation + Math.PI)*0.2f + RightKneeStartPosition.getY()));
						tibiaRightGraphics = new ImageGraphics("male_leg.png", 0.2f, -0.7f);
						tibiaRightGraphics.setRelativeTransform(Transform.I.translated(getRightKneeLocation()));
						tibiaLeftGraphics = new ImageGraphics("male_leg.png", 0.2f, -0.7f);
						tibiaLeftGraphics.setRelativeTransform(Transform.I.translated(getLeftKneeLocation()));
						rightThighGraphics.setParent(hitBox);
						leftThighGraphics.setParent(hitBox);
						tibiaRightGraphics.setParent(hitBox);
						tibiaLeftGraphics.setParent(hitBox);
						leftThighGraphics = new ImageGraphics("male_thigh.png", 0.2f, -getLeftKneeLocation().sub(getButtLocation()).getLength()-0.2f);
						leftThighGraphics.setRelativeTransform(Transform.I.rotated((float)(1.1f + angleOfElevation )).translated(getButtLocation()));
						rightThighGraphics = new ImageGraphics("male_thigh.png", 0.2f, -getRightKneeLocation().sub(getButtLocation()).getLength()-0.2f);
						rightThighGraphics.setRelativeTransform(Transform.I.rotated((float)(1.1f - angleOfElevation)).translated(getButtLocation()));
						rightThighGraphics.setParent(hitBox);
						leftThighGraphics.setParent(hitBox);
					}
					else {
						wheelLeft.relax();
					}
					
				}
				if(getOwner().getKeyboard().get(KeyEvent.VK_DOWN ).isDown()) {
					wheelLeft.power(0f);
					wheelRight.power(0.0f);
					
				}
				if(getOwner().getKeyboard().get(KeyEvent.VK_RIGHT ).isDown()) {
					hitBox.applyAngularForce(-20f);
					
				}
				if(getOwner().getKeyboard().get(KeyEvent.VK_LEFT ).isDown()) {
					hitBox.applyAngularForce(20f);
					
				}
				
				
			}
			
			else {
			
				
				if(getOwner().getKeyboard().get(KeyEvent.VK_UP ).isDown()) {
					//System.out.println(boostCoefficient);
					if (wheelRight.getSpeed() < MAX_WHEEL_SPEED  * boostCoefficient) {
						wheelRight.power(-MAX_WHEEL_SPEED  * boostCoefficient);
						angleOfRotation = (angleOfRotation - 0.03 * wheelRight.getAngularVelocity() / 20) % (2*Math.PI);
						if (angleOfElevation <= (Math.asin(0.2f/RightKneeEndPosition.sub(getButtLocation().mirrored(new Vector (1f, 0f))).getLength())) && compteur % 2 ==1 ) {
							angleOfElevation += 0.0073 * wheelRight.getAngularVelocity() / 20;
							compteur1++;
							
							
						}
						if ( angleOfElevation > (Math.asin(0.2f/RightKneeEndPosition.sub(getButtLocation().mirrored(new Vector (1f, 0f))).getLength())) && compteur % 2 == 1) {
							compteur ++;
							compteur1 = 0;
							compteur = compteur % 2;
							rightThighGraphics = new ImageGraphics("male_thigh.png", 0.2f, -getRightKneeLocation().sub(getButtLocation()).getLength());
							rightThighGraphics.setRelativeTransform(Transform.I.rotated((float)(1.1f + Math.asin(0.2f/RightKneeEndPosition.sub(getButtLocation()).getLength()))));
							rightThighGraphics.setParent(hitBox);
							angleOfElevation = Math.asin(0.2f/RightKneeEndPosition.sub(getButtLocation()).getLength());
							
						}
						if (angleOfElevation <= Math.asin(0.2f/RightKneeEndPosition.sub(getButtLocation()).getLength()) && compteur % 2 == 0) {
							angleOfElevation -= 0.0073 * wheelLeft.getAngularVelocity() / 20;
							
							compteur2++;
							
						}
						if ( angleOfElevation < - Math.asin(0.2f/RightKneeEndPosition.sub(getButtLocation()).getLength()) && compteur % 2 == 0 ) {
							compteur ++;
							compteur2 = 0;
							compteur = compteur % 2;
							rightThighGraphics = new ImageGraphics("male_thigh.png", 0.2f, -getRightKneeLocation().sub(getButtLocation()).getLength()-0.2f);
							rightThighGraphics.setRelativeTransform(Transform.I.rotated((float)(1.1f - Math.asin(0.2f/RightKneeEndPosition.sub(getButtLocation()).getLength()))).translated(getButtLocation()));
							rightThighGraphics.setParent(hitBox);
						}
						setLeftKneeLocation(new Vector((float)Math.sin(angleOfRotation)*(0.2f) + leftKneeStartPosition.getX(), 
											(float)Math.cos(angleOfRotation)*0.2f + leftKneeStartPosition.getY()));
						setRightKneeLocation(new Vector((float)Math.sin(angleOfRotation+ Math.PI)*(0.2f) + RightKneeStartPosition.getX(), 
											(float)Math.cos(angleOfRotation + Math.PI)*0.2f + RightKneeStartPosition.getY()));
						tibiaRightGraphics = new ImageGraphics("male_leg.png", 0.2f, -0.7f);
						tibiaRightGraphics.setRelativeTransform(Transform.I.translated(getRightKneeLocation()));
						tibiaLeftGraphics = new ImageGraphics("male_leg.png", 0.2f, -0.7f);
						tibiaLeftGraphics.setRelativeTransform(Transform.I.translated(getLeftKneeLocation()));
						rightThighGraphics.setParent(hitBox);
						leftThighGraphics.setParent(hitBox);
						tibiaRightGraphics.setParent(hitBox);
						tibiaLeftGraphics.setParent(hitBox);
						leftThighGraphics = new ImageGraphics("male_thigh.png", 0.2f,getLeftKneeLocation().sub(getButtLocation()).getLength()+0.2f);
						leftThighGraphics.setRelativeTransform(Transform.I.rotated((float)(-(1.1f - angleOfElevation + Math.PI ) )).translated(getButtLocation().mirrored(new Vector (1f,0f))));
						rightThighGraphics = new ImageGraphics("male_thigh.png", 0.2f, getRightKneeLocation().sub(getButtLocation()).getLength()+0.2f);
						rightThighGraphics.setRelativeTransform(Transform.I.rotated((float)(-(1.1f + angleOfElevation) + Math.PI )).translated(getButtLocation().mirrored(new Vector (1f,0f))));
						rightThighGraphics.setParent(hitBox);
						leftThighGraphics.setParent(hitBox);
	
					}
					
					else {
						wheelRight.relax();
				    }
				}
				
				if(getOwner().getKeyboard().get(KeyEvent.VK_DOWN ).isDown()) {
					wheelRight.power(0.0f);
					wheelLeft.power(0.0f);
				}
					
				if(getOwner().getKeyboard().get(KeyEvent.VK_RIGHT ).isDown()) {
					hitBox.applyAngularForce(-20f);
				}
				if(getOwner().getKeyboard().get(KeyEvent.VK_LEFT ).isDown()) {
					hitBox.applyAngularForce(20f);
				}
			}
		}
	}
		@Override
		public void destroy() {
			hitBox.destroy();
			wheelRight.destroy();
			wheelLeft.destroy();
			
		}
	}