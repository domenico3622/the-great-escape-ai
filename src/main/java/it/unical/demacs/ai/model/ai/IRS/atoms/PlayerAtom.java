package it.unical.demacs.ai.model.ai.IRS.atoms;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;
import it.unical.mat.embasp.languages.asp.SymbolicConstant;

@Id("player")
public class PlayerAtom {


	@Param(0)
	private int id;
	@Param(1)
	private int x;
	@Param(2)
	private int y;
	@Param(3)
	private SymbolicConstant direction;
	@Param(4)
	private int numWall;

	public PlayerAtom(int id, int x, int y, SymbolicConstant direction, int numWall) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.numWall = numWall;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public SymbolicConstant getDirection() {
		return direction;
	}

	public void setDirection(SymbolicConstant direction) {
		this.direction = direction;
	}

	public int getNumWall() {
		return numWall;
	}

	public void setNumWall(int numWall) {
		this.numWall = numWall;
	}

	public PlayerAtom() {
	}


	

}
