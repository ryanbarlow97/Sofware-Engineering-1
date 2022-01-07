package swan.group7.dragon;

import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

/**
 * Entity.java This class is used to represent an entity in the game. It stores
 * the health (HP), and (x, y) coordinates of an entity.
 * 
 * @author Lukas Kundelis (JavaDoc Mollie Stamp)
 * @version 1.6
 *
 */
public class Entity {
	/**
	 * Directions that can exist, referenced to move object around grid
	 * 
	 */
	public enum Direction {
		/**
		 * {@link #UP}
		 */
		UP,
		/**
		 * {@link #DOWN}
		 */
		DOWN,
		/**
		 * {@link #RIGHT}
		 */
		RIGHT,
		/**
		 * {@link #LEFT}
		 */
		LEFT
	}

	protected Direction lastDirection = Direction.UP;
	protected int HP; // health of the entity
	protected int x; // x coordinate of the entity
	protected int y; // y coordinate of the entity

	/**
	 * Constructor used for creating an entity object with the specified heath and
	 * position.
	 *
	 * @param HP Health of entity.
	 * @param x  Horizontal location on game map.
	 * @param y  Vertical location on game map.
	 */
	public Entity(int HP, int x, int y) {
		this.HP = HP;
		this.x = x;
		this.y = y;
	}

	/**
	 * This is used to change x coordinate of entity position.
	 * 
	 * @param x Represents x coordinate.
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * This method is used to change y coordinate of entity position.
	 * 
	 * @param y Represents y coordinate.
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * This is used to set Entities health.
	 * 
	 * @param HP New health of entity.
	 */
	public void setHP(int HP) {
		this.HP = HP;
	}

	/**
	 * This is used to get current x coordinate.
	 * 
	 * @return x Current x coordinate.
	 */
	public double getX() {
		return x;
	}

	/**
	 * This method gets the current y coordinate position of entity.
	 * 
	 * @return y Current y coordinate.
	 */
	public double getY() {
		return y;
	}

	/**
	 * This is used to get entities current health (HP).
	 * 
	 * @return HP Current health.
	 */
	public int getHP() {
		return HP;
	}

	/**
	 * This is used to assign or change the direction the dragon has just moved.
	 * 
	 * @param newDirection Direction they are moving to.
	 */
	public void setLastDirection(Direction newDirection) {
		this.lastDirection = newDirection;
	}

	/**
	 * This returns last direction moved so this can be the lowest priority
	 * direction moved to in the next move.
	 * 
	 * @return lastDirection Previous direction moved.
	 */
	public Direction getLastDirection() {
		return lastDirection;
	}

	/**
	 * Changes x coordinate and assigns lastDirection if entity moved right.
	 * 
	 */
	protected void moveRight() {
		setX(x + 1);
		setLastDirection(Direction.RIGHT);
	}

	/**
	 * Changes y coordinate and assigns lastDirection if entity moved up.
	 * 
	 */
	protected void moveUp() {
		setY(y - 1);
		setLastDirection(Direction.UP);
	}

	/**
	 * Changes y coordinate and assigns lastDirection if entity moved down.
	 * 
	 */
	protected void moveDown() {
		setY(y + 1);
		setLastDirection(Direction.DOWN);
	}

	/**
	 * Changes x coordinate and assigns lastDirection if entity moved left.
	 * 
	 */
	protected void moveLeft() {
		setX(x - 1);
		setLastDirection(Direction.LEFT);
	}

	/**
	 * If entity is moving same direction as previous, then calls corresponding
	 * method.
	 * 
	 * @param lastDirection Used as previous direction moved.
	 */
	private void moveSameDirection(Direction lastDirection) {
		switch (lastDirection) {
		case UP: {
			moveUp();
			break;
		}
		case DOWN: {
			moveDown();
			break;
		}
		case RIGHT: {
			moveRight();
			break;
		}
		case LEFT: {
			moveLeft();
			break;
		}
		}
	}

	/**
	 * Used to compute which directions are valid for the entity to move.
	 * 
	 * @param tilegrid Array of all tiles.
	 * @param weapons  ArrayList of weapons in play.
	 * @return availableDirections String of all possible valid directions.
	 */
	public String getAvailableDirections(Tile[][] tilegrid, CopyOnWriteArrayList<Weapon> weapons) {
		String availableDirections = "";
		boolean isblocked = false;
		if (tilegrid[x - 1][y].getTileType().equals(TileTypeEnum.PATH)
				|| tilegrid[x - 1][y].getTileType().equals(TileTypeEnum.TUNNEL_STONE)) {
			for (Weapon i : weapons) {
				if (i.getX() == x - 1 && i.getY() == y && i.getWeaponType() == WeaponTypeEnum.CASTLE) {
					isblocked = true;
					i.setHP(i.getHP() - 1);
				}
			}
			if (!isblocked) {
				availableDirections += "LEFT ";
			}
		}
		isblocked = false;
		if (tilegrid[x + 1][y].getTileType().equals(TileTypeEnum.PATH)
				|| tilegrid[x + 1][y].getTileType().equals(TileTypeEnum.TUNNEL_STONE)) {
			for (Weapon i : weapons) {
				if (i.getX() == x + 1 && i.getY() == y && i.getWeaponType() == WeaponTypeEnum.CASTLE) {
					isblocked = true;
					i.setHP(i.getHP() - 1);
				}
			}
			if (!isblocked) {
				availableDirections += "RIGHT ";
			}
		}
		isblocked = false;
		if (tilegrid[x][y + 1].getTileType().equals(TileTypeEnum.PATH)
				|| tilegrid[x][y + 1].getTileType().equals(TileTypeEnum.TUNNEL_STONE)) {
			for (Weapon i : weapons) {
				if (i.getX() == x && i.getY() == y + 1 && i.getWeaponType() == WeaponTypeEnum.CASTLE) {
					isblocked = true;
					i.setHP(i.getHP() - 1);
				}
			}
			if (!isblocked) {
				availableDirections += "DOWN ";
			}
		}
		isblocked = false;
		if (tilegrid[x][y - 1].getTileType().equals(TileTypeEnum.PATH)
				|| tilegrid[x][y - 1].getTileType().equals(TileTypeEnum.TUNNEL_STONE)) {
			for (Weapon i : weapons) {
				if (i.getX() == x && i.getY() == y - 1 && i.getWeaponType() == WeaponTypeEnum.CASTLE) {
					isblocked = true;
					i.setHP(i.getHP() - 1);
				}
			}
			if (!isblocked) {
				availableDirections += "UP ";
			}
		}

		return availableDirections;

	}

	/**
	 * Calls correct methods to physically move entity around the board.
	 * 
	 * @param tilegrid Array of all tiles.
	 * @param weapons  ArrayList of weapons.
	 */
	public void moveAround(Tile[][] tilegrid, CopyOnWriteArrayList<Weapon> weapons) {
		String availableDirections = getAvailableDirections(tilegrid, weapons);
		Random rand = new Random();
		int x = rand.nextInt(1000);

		if (availableDirections.contains(lastDirection.toString())) {
			moveSameDirection(lastDirection);
		} else {
			if (availableDirections.equals("UP ")) {
				moveUp();
			} else if (availableDirections.equals("DOWN ")) {
				moveDown();
			} else if (availableDirections.equals("RIGHT ")) {
				moveRight();
			} else if (availableDirections.equals("LEFT ")) {
				moveLeft();
			} else if (availableDirections.equals("LEFT RIGHT ")) {
				if (x < 500) {
					moveLeft();
				} else {
					moveRight();
				}
			} else if (availableDirections.equals("LEFT DOWN ")) {
				if (x < 500) {
					moveLeft();
				} else {
					moveDown();
				}
			} else if (availableDirections.equals("LEFT UP ")) {
				if (x < 500) {
					moveLeft();
				} else {
					moveUp();
				}
			} else if (availableDirections.equals("RIGHT DOWN ")) {
				if (x < 500) {
					moveRight();
				} else {
					moveDown();
				}
			} else if (availableDirections.equals("RIGHT UP ")) {
				if (x < 500) {
					moveRight();
				} else {
					moveUp();
				}
			} else if (availableDirections.equals("DOWN UP ")) {
				if (x < 500) {
					moveDown();
				} else {
					moveUp();
				}
			} else if (availableDirections.equals("LEFT RIGHT DOWN UP ")) {
				if (x < 250) {
					moveDown();
				} else if (x > 250 && x < 500) {
					moveUp();
				} else if (x > 500 && x < 750) {
					moveLeft();
				} else {
					moveRight();
				}
			} else if (availableDirections.equals("LEFT RIGHT DOWN ")) {
				if (x < 333) {
					moveDown();
				} else if (x > 333 && x < 666) {
					moveRight();
				} else {
					moveLeft();
				}
			} else if (availableDirections.equals("LEFT RIGHT UP ")) {
				if (x < 333) {
					moveUp();
				} else if (x > 333 && x < 666) {
					moveRight();
				} else {
					moveLeft();
				}
			} else if (availableDirections.equals("LEFT DOWN UP ")) {
				if (x < 333) {
					moveUp();
				} else if (x > 333 && x < 666) {
					moveDown();
				} else {
					moveLeft();
				}
			} else if (availableDirections.equals("RIGHT DOWN UP ")) {
				if (x < 333) {
					moveUp();
				} else if (x > 333 && x < 666) {
					moveDown();
				} else {
					moveRight();
				}
			}
		}
	}

	/**
	 * Rotates image of entity as it moves and turns on board.
	 * 
	 * @param image  Image of entity.
	 * @param entity The entity being rotated.
	 * @return view ImageView.
	 */
	public Image applyRotation(Image image, Entity entity) {
		ImageView view = new ImageView(image);
		SnapshotParameters parameters = new SnapshotParameters();
		switch (entity.getLastDirection()) {
		case DOWN: {
			view.setRotate(0);
			break;
		}
		case LEFT: {
			view.setRotate(90);
			break;
		}
		case RIGHT: {
			view.setRotate(270);
			break;
		}
		case UP: {
			view.setRotate(180);
			break;
		}
		}
		parameters.setFill(Color.TRANSPARENT);
		return view.snapshot(parameters, null);
	}

	/**
	 * This is an abstract method to be implemented.
	 * 
	 */
	public void update() {

	}

}