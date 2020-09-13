package application;

import javafx.scene.control.RadioButton;

public class Player {
	
	private int _playerId;
	private String _playerFirstName;
	private String _playerLastName;
	private String _playerAddress;
	private String _playerPostCode;
	private String _playerProvince;
	private int _playerPhoneNum;
	private RadioButton _rbPlayer;
	
	//Get and Set the field of Player
	public void setPlayerId(int id){
		this._playerId=id;
	}
	public int getPlayerId() {
		return this._playerId;
	}
	
	public void setPlayerFirstName(String fn){
		this._playerFirstName=fn;
	}
	public String getPlayerFirstName() {
		return this._playerFirstName;
	}
	
	public void setPlayerLastName(String ln){
		this._playerLastName=ln;
	}
	public String getPlayerLastName() {
		return this._playerLastName;
	}
	
	public void setPlayerAddress(String address){
		this._playerAddress=address;
	}
	public String getPlayerAddress() {
		return this._playerAddress;
	}
	
	public void setPlayerPostCode(String pc){
		this._playerPostCode=pc;
	}
	public String getPlayerPostCode() {
		return this._playerPostCode;
	}
	
	public void setPlayerProvince(String pro){
		this._playerProvince=pro;
	}
	public String getPlayerProvince() {
		return this._playerProvince;
	}
	
	public void setPlayerPhoneNum(int num) {
		this._playerPhoneNum=num;
	}
	public int getPlayerPhoneNum() {
		return this._playerPhoneNum;
	}
	
	public void setchPlayer(RadioButton ch) {
		this._rbPlayer=ch;
	}
	public RadioButton getchPlayer() {
		return this._rbPlayer;
	}

}
