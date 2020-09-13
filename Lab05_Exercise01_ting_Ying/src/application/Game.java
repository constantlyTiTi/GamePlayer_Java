package application;

import javafx.scene.control.RadioButton;

public class Game {
	private int _gameId;
	private String _gameName;
	private RadioButton _rbGame;
	
	//Getter Setter for game fields;
	public void setGameId(int id){
		this._gameId=id;
	}
	public int getGameId() {
		return this._gameId;
	}
	
	public void setGameName(String name){
		this._gameName=name;
	}
	public String getGameName() {
		return this._gameName;
	}
	
	public void setChGame(RadioButton ch) {
		this._rbGame=ch;
	}
	public RadioButton getChGame() {
		return this._rbGame;
	}

}
