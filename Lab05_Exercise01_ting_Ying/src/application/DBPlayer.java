package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class DBPlayer {

	private DBConnection _objDBConnection=new DBConnection();
	private Statement _playerDBRetrive=null; 
	private ToggleGroup chPlayerGroup_=new ToggleGroup();
	
	private ArrayList<Player> _playerArray=new ArrayList<Player>();
	
	public ArrayList<Player> getPlayerArray()
	{
		return this._playerArray;
	}
	
	public void buildPlayerDB()
	{
		Connection connection=_objDBConnection.getDBConnection();
		
		try {
			_playerDBRetrive=connection.createStatement();
			
			//Retrive Data from player table
			ResultSet playerDBResult=_playerDBRetrive.executeQuery("Select * from LAB05_PLAYER");
			
			int index=1;
			while(playerDBResult.next())
			{
//				{
					Player aPlayer=new Player();
					RadioButton selectPlayer=new RadioButton("");
					selectPlayer.setId("RbPlayer"+index);
					index++;
					selectPlayer.setToggleGroup(chPlayerGroup_);
					aPlayer.setPlayerId(playerDBResult.getInt("PLAYER_ID"));
					aPlayer.setPlayerFirstName(playerDBResult.getString("FIRST_NAME"));
					aPlayer.setPlayerLastName(playerDBResult.getString("LAST_NAME"));
					aPlayer.setPlayerAddress(playerDBResult.getString("ADDRESS"));
					aPlayer.setPlayerPostCode(playerDBResult.getString("POSTAL_CODE"));
					aPlayer.setPlayerProvince(playerDBResult.getString("PROVINCE"));
					aPlayer.setPlayerPhoneNum(playerDBResult.getInt("PHONE_NUMBER"));
					aPlayer.setchPlayer(selectPlayer);
					
					_playerArray.add(aPlayer);
//				}
			}
			
		}
			catch(SQLException e) {

				System.err.println("ERROR! Couldn't connect to the DB: " + e.getMessage());
			}
		
		finally {
			try {
				//close connection
				connection.close();
				_playerDBRetrive.close();
			} catch (SQLException e) {
				System.err.println("ERROR! Couldn't close the connection to the DB: " + e.getMessage());

			}
		}//End of Finally


		
	}
	
}
