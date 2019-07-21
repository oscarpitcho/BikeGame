package ch.epfl.cs107.play.game.actor.BikeGame.general;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.Shape;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Terrain extends GameEntity implements Actor{
	private Entity terrain;
	private ImageGraphics backGround;
	private ShapeGraphics terrainGraphics13;
	private ShapeGraphics terrainGraphics11;
	private ShapeGraphics terrainGraphics12;
	private ShapeGraphics terrainGraphics10;
	private ShapeGraphics terrainGraphics9;
	private ShapeGraphics terrainGraphics8;
	private ShapeGraphics terrainGraphics7;
	private ShapeGraphics terrainGraphics6;
	private ShapeGraphics terrainGraphics5;
	private ShapeGraphics terrainGraphics4;
	private ShapeGraphics terrainGraphics3;
	private ShapeGraphics terrainGraphics2;
	private ShapeGraphics terrainGraphics1;

	
	Polyline terrainGeometry1 = new Polyline(
			-5f, 10f,
			-5f, 0f,
			6f, 0f,
			12f, -2,
			20f, -2f,
			28f, 1f);
	
	Polyline terrainGeometry2 = new Polyline(
			28f, 8f,
			34f,10f,
			44f,10f,
			50f, 8f);
	
	Polyline terrainGeometry3 = new Polyline (
			43f, 2f,
			46f, 1f,
			52f, 1.5f,
			61f, 3f);
	
	Polyline terrainGeometry4 = new Polyline(
			80f, 1f,
			90f, 1f,
			90f ,6f,
			95f, 5f,
			99f, 1.5f,
			102f, 1.3f,
			103f, 5f,
			103f, 15f,
			100f, 16.2f,
			96f, 16.5f);
	Polyline terrainGeometry12 = new Polyline (
			100f, 16.2f,
			106.6f, 17f,
			113f, 20f,
			114f, 21f,
			114.5f, 24.5f,
			114f, 26f,
			113f, 27.5f,
			111f, 28.5f,
			109f, 29f);
	
	Polyline terrainGeometry5 = new Polyline (
			96f, 12f,
			91f, 13.5f,
			90f, 16.2f,
			90, 19.3f,
			92f,22f,
			96f, 23f);
	
	Polyline terrainGeometry6 = new Polyline (
			110f, 24.6f,
			105f, 25.5f,
			101f, 27.5f,
			92f, 27.5f);
			
	Polyline terrainGeometry7 = new Polyline (
			92f, 27.5f,
			77.3f, 16.6f,
			77.3f, 14.2f);
	
	Polyline terrainGeometry8 = new Polyline (
			74.5f, 14.5f,
			63.75f, 20f,
			57.5f, 20.5f,
			54f, 19f,
			53f, 16f);
	
	Polyline terrainGeometry9 = new Polyline (
			53f, 16f,
			35f, 16f);
	
	Polyline terrainGeometry10 = new Polyline (
			35f, 16f,
			33f, 17f,
			25f, 17);
	
	Polyline terrainGeometry13 = new Polyline(
			22f, 16f,
			-6000f, 17f);
	Polyline terrainGeometry11 = new Polyline (
			61f, 3f,
			61f, -8f,
			62f, -6f,
			63f, -8f,
			64f, -6f,
			65f, -8f,
			66f, -6f,
			67f, -8f,
			68f, -6f,
			69f, -8f,
			70f, -6f,
			71f, -8f,
			72f, -6f, 
			73f, -8f,
			74f, -6f,
			75f, -8f,
			76f, -6f,
			77f, -8f,
			78f, -6f,
			79f, -8f,
			80f, -6f,
			80f, 1f);
	Polyline temp1 = new Polyline (
			56f,3f,
			80f, 1f);

	public Terrain(ActorGame game, boolean fixed) {
		super (game, fixed);
		terrain = super.getEntity();
		entityGraphics(terrain);
		 terrainGraphics1 = entityGraphics (terrain, terrainGeometry1, Color.BLACK);
		 terrainGraphics2 = entityGraphics (terrain, terrainGeometry2, Color.BLACK);
		 terrainGraphics3 = entityGraphics (terrain,  terrainGeometry3, Color.BLACK);
		 terrainGraphics4 = entityGraphics (terrain, terrainGeometry4, Color.BLACK);
		 terrainGraphics5 = entityGraphics (terrain,  terrainGeometry5, Color.BLACK);
		 terrainGraphics6 = entityGraphics (terrain,  terrainGeometry6, Color.BLACK);
		 terrainGraphics7 = entityGraphics (terrain,  terrainGeometry7, Color.BLUE);
		 terrainGraphics8 =	entityGraphics (terrain, terrainGeometry8, Color.BLACK);
		 terrainGraphics9 = entityGraphics (terrain,  terrainGeometry9, Color.GREEN);
		 terrainGraphics10 = entityGraphics (terrain, terrainGeometry10, Color.BLACK);
		 terrainGraphics11 = entityGraphics (terrain,  terrainGeometry11, Color.WHITE);
		 terrainGraphics12 = entityGraphics(terrain, terrainGeometry12, Color.BLACK);
		 terrainGraphics13 = entityGraphics(terrain, terrainGeometry13, Color.BLACK);
		
		entityPart (terrain, terrainGeometry1, 10f, 0f);
		entityPart (terrain, terrainGeometry2, 10f, 0f);
		entityPart (terrain, terrainGeometry3, 10f, 0f);
		entityPart (terrain, terrainGeometry4, 10f, 0f);
		entityPart (terrain, terrainGeometry5, 10f, 0f);
		entityPart (terrain, terrainGeometry6, 10f, 0f);
		entityPart (terrain, terrainGeometry7, 0f, 0f);
		entityPart (terrain, terrainGeometry8, 10f, 0f);
		entityPart (terrain, terrainGeometry9, 10f, 1.75f);
		entityPart (terrain, terrainGeometry10, 10f, 0f);
		entityPart (terrain, terrainGeometry11, 10f, 0f);
		entityPart(terrain, terrainGeometry12, 10f, 0f);
		entityPart(terrain, terrainGeometry13, 10f, 0f);
		//entityPart(terrain, temp1, 10f, 0f);

		
	}
	public Terrain (ActorGame game, boolean fixed, Vector position) {
		super (game, fixed, position);
		terrain = super.getEntity();
		 terrainGraphics1 = entityGraphics (terrain, terrainGeometry1, Color.BLACK);
		 terrainGraphics2 = entityGraphics (terrain, terrainGeometry2, Color.BLACK);
		 terrainGraphics3 = entityGraphics (terrain,  terrainGeometry3, Color.BLACK);
		 terrainGraphics4 = entityGraphics (terrain, terrainGeometry4, Color.BLACK);
		 terrainGraphics5 = entityGraphics (terrain,  terrainGeometry5, Color.BLACK);
		 terrainGraphics6 = entityGraphics (terrain,  terrainGeometry6, Color.BLACK);
		 terrainGraphics7 = entityGraphics (terrain,  terrainGeometry7, Color.BLUE);
		 terrainGraphics8 =	entityGraphics (terrain, terrainGeometry8, Color.BLACK);
		 terrainGraphics9 = entityGraphics (terrain,  terrainGeometry9, Color.GREEN);
		 terrainGraphics10 = entityGraphics (terrain, terrainGeometry10, Color.BLACK);
		 terrainGraphics11 = entityGraphics (terrain,  terrainGeometry11, Color.WHITE);
		
		entityPart (terrain, terrainGeometry1, 10f, 0f);
		entityPart (terrain, terrainGeometry2, 10f, 0f);
		entityPart (terrain, terrainGeometry3, 10f, 0f);
		entityPart (terrain, terrainGeometry4, 10f, 0f);
		entityPart (terrain, terrainGeometry5, 10f, 0f);
		entityPart (terrain, terrainGeometry6, 10f, 0f);
		entityPart (terrain, terrainGeometry7, 0f, 0f);
		entityPart (terrain, terrainGeometry8, 10f, 0f);
		entityPart (terrain, terrainGeometry9, 10f, 2.5f);
		entityPart (terrain, terrainGeometry10, 10f, 0f);
		entityPart (terrain, terrainGeometry11, 10f, 0f);
		entityPart(terrain, terrainGeometry12, 10f, 0f);
		entityPart(terrain, terrainGeometry13, 10f, 0f);
	}

	@Override
	public void draw(Canvas canvas) {
		backGround.draw(canvas);
		terrainGraphics13.draw(canvas);
		terrainGraphics12.draw(canvas);
		terrainGraphics11.draw(canvas);
		terrainGraphics10.draw(canvas);
		terrainGraphics9.draw(canvas);
		terrainGraphics8.draw(canvas);
		terrainGraphics7.draw(canvas);
		terrainGraphics6.draw(canvas);
		terrainGraphics5.draw(canvas);
		terrainGraphics4.draw(canvas);
		terrainGraphics3.draw(canvas);
		terrainGraphics2.draw(canvas);
		terrainGraphics1.draw(canvas);
	}

	@Override
	public Transform getTransform() {
		return terrain.getTransform();
	}

	@Override
	public Vector getVelocity() {
		return terrain.getVelocity();
	}

	@Override
	
	protected void entityGraphics(Entity entity) {
		backGround = new ImageGraphics ("skybox_sideClouds.png", 7000f, 7000f, new Vector (0.0f, 0.0f));
		backGround.setDepth(-2f);
		backGround.setParent(terrain);
		backGround.setRelativeTransform(Transform.I.translated(-3500f, -3500f));
	}
	
	//Modularized method to create the shape graphics for every portion of terrain
	private ShapeGraphics entityGraphics(Entity entity, Shape shape, Color color) {
		ShapeGraphics shapeGraphics;
		shapeGraphics = new ShapeGraphics (shape,  null , color, 0.1f);

		shapeGraphics.setParent(entity);
		return shapeGraphics;

	}

	@Override
	protected void entityPart(Entity entity) {	
	}
	
	//Overload of the method above to allow for better modularization.
	protected void entityPart (Entity entity, Shape shape, float friction, float restitution) {
		PartBuilder partBuilder = entity.createPartBuilder();
		partBuilder.setShape(shape);
		partBuilder.setFriction(friction);
		partBuilder.setRestitution(restitution);
		partBuilder.build();
	}

}