package ch.epfl.cs107.play.game.actor.BikeGame;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.BikeGame.bike.Bike;
import ch.epfl.cs107.play.game.actor.BikeGame.general.Boost;
import ch.epfl.cs107.play.game.actor.BikeGame.general.ChoiceInterface;
import ch.epfl.cs107.play.game.actor.BikeGame.general.Coins;
import ch.epfl.cs107.play.game.actor.BikeGame.general.EndMessage;
import ch.epfl.cs107.play.game.actor.BikeGame.general.Finish;
import ch.epfl.cs107.play.game.actor.BikeGame.general.GravityWell;
import ch.epfl.cs107.play.game.actor.BikeGame.general.ItsRainingDucks;
import ch.epfl.cs107.play.game.actor.BikeGame.general.Pendulum;
import ch.epfl.cs107.play.game.actor.BikeGame.general.RotatingScale;
import ch.epfl.cs107.play.game.actor.BikeGame.general.Spikes;
import ch.epfl.cs107.play.game.actor.BikeGame.general.Spring;
import ch.epfl.cs107.play.game.actor.BikeGame.general.Terrain;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Window;

public class BikeGame extends ActorGame {
	
	//Declaring all basic attributes relative to the BikeGame.
	private static int playerScore = 0;
	private static long startTime;
	private boolean  interfaceInstantiated = false;
	
	
	//Declaring all actors relative to the BikeGame
	private Terrain terrain;
	private Bike bike;
	private ChoiceInterface choiceInterface;
	private Pendulum pendulum1;
	private Spikes spikes1;
	private GravityWell gravityWell1;
	private Pendulum pendulum2;
	private Pendulum pendulum3;
	private Boost boost1;
	private RotatingScale scale1;
	private RotatingScale scale2;
	private Spring spring1;
	private GravityWell gravityWell2;
	private GravityWell gravityWell3;
	private Boost boost2;
	private RotatingScale scale4;
	private ItsRainingDucks itsRainingDucks1;
	private Finish finish;
	private Spikes spikes2;
	private EndMessage endMessage;
	
	private Coins coin1;
	private Coins coin2;
	private Coins coin3;
	private Coins coin4;
	private Coins coin5;
	private Coins coin6;
	private Coins coin7;
	private Coins coin8;
	private Coins coin9;
	private Coins coin10;
	private Coins coin11;
	private Coins coin12;
	private Coins coin13;
	private Coins coin14;
	private Coins coin15;
	private Coins coin16;
	private Coins coin17;
	private Coins coin18;
	private Coins coin19;
	private Coins coin20;
	private Coins coin21;
	private Coins coin22;
	private Coins coin23;
	private Coins coin24;
	private Coins coin25;
	private Coins coin26;
	private Coins coin27;
	private Coins coin28;
	private Coins coin29;
	private Coins coin30;
	private Coins coin31;
	private Coins coin32;
	private Coins coin33;
	private Coins coin34;
	private Coins coin35;
	private Coins coin36;
	private Coins coin37;
	private Coins coin38;
	private Coins coin39;
	private Coins coin40;
	private Coins coin41;
	private Coins coin42;
	private Coins coin43;
	private Coins coin44;
	
	
		//Begining Game
		@Override
		public boolean begin(Window window, FileSystem fileSystem) {
			super.begin(window, fileSystem);
			
			//Starting the choiceInterface
			if(!interfaceInstantiated) {
				choiceInterface = new ChoiceInterface(this);
				interfaceInstantiated = true;
				System.out.println(6);
				addEntity(choiceInterface, actors);
				System.out.println(actors.size());
				return true;
			}
			
			//Starting the rest of the game.
			else if (ChoiceInterface.getChosen()){
				ActorGame.setGameOver(false);
				playerScore = 0;
				startTime = System.currentTimeMillis();
				
				
				//Instantiating all actors and adding them to the ActorGame array.
				bike = new Bike (this, false, new Vector (2f, 2f), ChoiceInterface.getChoice());
				addEntity(bike, actors);
				super.setViewCandidate(bike); //Setting the viewCandidate.
				
				terrain = new Terrain (this, true);	
				addEntity(terrain, actors);
				
				
				
				pendulum1 = new Pendulum(this, new Vector(16f, 4f), 4f, 1f);
				addEntity(pendulum1, actors);
				
				spikes1 = new Spikes(this, new Vector(16f, -2f), 1f, 1000);
				addEntity(spikes1, actors);
				
				gravityWell1 = new GravityWell(this, new Vector(28f, 1f), 15f, 12f, new Vector(0f, 30f));
				addEntity(gravityWell1, actors);
				
				pendulum2 = new Pendulum(this , new Vector(36f, 2f), 5.5f, 0.6f);
				addEntity(pendulum2, actors);
				
				pendulum3 = new Pendulum(this, new Vector(52f, 7f), 3, 0.5f);
				addEntity(pendulum3, actors);
				
				boost1 = new Boost(this, new Vector(47f, 2f), 8, 3000);
				addEntity(boost1, actors);
				
				scale1 = new RotatingScale(this, new Vector(70f, 3.2f), 11, 0);
				addEntity(scale1, actors);
				
				scale2 = new RotatingScale(this, new Vector(78f, 4.2f), 4f, 3f);
				addEntity(scale2, actors);
				
				spring1 = new Spring(this, new Vector(86f, 1f), 2f, new Vector(0f, 31f), 2000);
				addEntity(spring1, actors);
				
				gravityWell2 = new GravityWell(this, new Vector(100.5f, 1.5f), 3f, 14.7f, new Vector(0, 17f));
				addEntity(gravityWell2, actors);
				
				gravityWell3 = new GravityWell(this, new Vector(91f, 12f), 3f, 12f, new Vector(0f, 17f));
				addEntity(gravityWell3, actors); 
				
				boost2 =  new Boost(this, new Vector(100f, 18f), 10, 3000);
				addEntity(boost2, actors);
				
				scale4 = new RotatingScale(this, new Vector(57.5f, 25.5f), 8, -4);
				addEntity(scale4, actors);
				
				itsRainingDucks1 = new ItsRainingDucks(this, new Vector(14f, 19f), 6f, 500, 15, new Vector(1f, 0));
				addEntity(itsRainingDucks1, actors);
				
				spikes2 = new Spikes(this, new Vector(30f, 17f), 2, 1000);
				addEntity(spikes2, actors);
				
				finish = new Finish(this, new Vector(10f, 16));
				addEntity(finish, actors);
				
				endMessage = new EndMessage(this, "You loose.");
				addEntity(endMessage, actors);
				
				coin1 = new Coins(this, new Vector(5.6f, 2.6f), true);
				coin2 = new Coins(this, new Vector(9.1f, 1.89f), true);
				coin3 = new Coins(this, new Vector(23.01f , 1.9f), false);
				coin4 = new Coins(this, new Vector(38.5f , 5.7f), false);
				coin5 = new Coins(this, new Vector(40f, 7.7f), true);
				coin6 = new Coins(this, new Vector(46.1f, 7.3f), false);
				coin7 = new Coins(this, new Vector(46f, 4.3f), false);
				coin8 = new Coins(this, new Vector(48.27f, 3.44f), false);
				coin9 = new Coins(this, new Vector(58.2f, 4.9f), false);
				coin10 = new Coins(this, new Vector(63.2f, 6.63f), true);
				coin11 = new Coins(this, new Vector(69.1f, 8.1f), false);
				coin12 = new Coins(this, new Vector(77.2f, 7f), false);
				coin13 = new Coins(this, new Vector(82f, 4.6f), true);
				coin14 = new Coins(this, new Vector(84.3f, 3f), false);
				coin15 = new Coins(this, new Vector(88.6f, 8.5f), false);
				coin16 = new Coins(this, new Vector(92.5f, 10f), false);
				coin17 = new Coins(this, new Vector(97.6f, 7.1f), false);
				coin18 = new Coins(this, new Vector(101.5f, 7.2f), true);
				coin19 = new Coins(this, new Vector(93.84f, 14.36f), true);
				coin20 = new Coins(this, new Vector(101f, 14f), false);
				coin21 = new Coins(this, new Vector(90f, 17f), false);
				coin22 = new Coins(this, new Vector(94f, 19.5f), true);
				coin23 = new Coins(this, new Vector(100f, 20f), false);
				coin24 = new Coins(this, new Vector(102f, 19f), false);
				coin25 = new Coins(this, new Vector(106.5f, 19.5f), true);
				coin26 = new Coins(this, new Vector(112.36f, 23.5f), false);
				coin27 = new Coins(this, new Vector(100f, 30f), false);
				coin28 = new Coins(this, new Vector(92.5f, 30f), true);
				coin29 = new Coins(this, new Vector(87.7f, 27f), false);
				coin30 = new Coins(this, new Vector(83.3f, 23.5f), false);
				coin31 = new Coins(this, new Vector(79f, 20f), false);
				coin32 = new Coins(this, new Vector(74f, 19.5f), false);
				coin33 = new Coins(this, new Vector(68.9f, 21.2f), false);
				coin34 = new Coins(this, new Vector(65f, 21.2f), true);
				coin35 = new Coins(this, new Vector(60f, 23f), false);
				coin36 = new Coins(this, new Vector(53f, 21.8f), false);
				coin37 = new Coins(this, new Vector(50f, 19f), false);
				coin38 = new Coins(this, new Vector(44.7f, 18.1f), false);
				coin39 = new Coins(this, new Vector(37.32f, 19.38f), false);
				coin40 = new Coins(this, new Vector(34.1f, 20.3f), true);
				coin41 = new Coins(this, new Vector(30.7f, 21f), false);
				coin42 = new Coins(this, new Vector(27.6f, 20f), false);
				coin43 = new Coins(this, new Vector(25f, 19.5f), false);
				coin44 = new Coins(this, new Vector(15f, 18f), false);
				
				addEntity(coin1, actors);
				addEntity(coin2, actors);
				addEntity(coin3, actors);
				addEntity(coin4, actors);
				addEntity(coin5, actors);
				addEntity(coin6, actors);
				addEntity(coin7, actors);
				addEntity(coin8, actors);
				addEntity(coin9, actors);
				addEntity(coin10, actors);
				addEntity(coin11, actors);
				addEntity(coin12, actors);
				addEntity(coin13, actors);
				addEntity(coin14, actors);
				addEntity(coin15, actors);
				addEntity(coin16, actors);
				addEntity(coin17, actors);
				addEntity(coin18, actors);
				addEntity(coin19, actors);
				addEntity(coin20, actors);
				addEntity(coin21, actors);
				addEntity(coin22, actors);
				addEntity(coin23, actors);
				addEntity(coin24, actors);
				addEntity(coin25, actors);
				addEntity(coin26, actors);
				addEntity(coin27, actors);
				addEntity(coin28, actors);
				addEntity(coin29, actors);
				addEntity(coin30, actors);
				addEntity(coin31, actors);
				addEntity(coin32, actors);
				addEntity(coin33, actors);
				addEntity(coin34, actors);
				addEntity(coin35, actors);
				addEntity(coin36, actors);
				addEntity(coin37, actors);
				addEntity(coin38, actors);
				addEntity(coin39, actors);
				addEntity(coin40, actors);
				addEntity(coin41, actors);
				addEntity(coin42, actors);
				addEntity(coin43, actors);
				addEntity(coin44, actors);
				return true;
			}
			return true;
		}
		@Override
		
		/**
		 * Destroys all actors relative to the game.
		 */
		public void end() {
			for(int i = 0; i < actors.size();) {
				
				deleteEntity(actors.get(i), actors);
			}
			
		}
		/**
		 * Allows to actors to add to the player Score
		 * @param score
		 */
		public static void addScore(int score) {
			playerScore += score;
		}
		
		/**
		 * Returns the startTime of the game.
		 * @return
		 */
		public static long getStartTime() {
			return startTime;
		}
		
		/**
		 * Returns the playerScore.
		 * @return
		 */
		public static int getScore() {
			return playerScore;
		}
}
