package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class DBGame {
	
	private DBConnection _objDBConnection=new DBConnection();
	private Statement _gameDBRetrive=null; 
	@FXML private ToggleGroup chGameGroup=new ToggleGroup();
	
	private ArrayList<Game> _gameArray=new ArrayList<Game>();
	
	//Data List
	public ArrayList<Game> getGameArray()
	{
		return this._gameArray;
	}
	
	//Build Data
	public void buildGameDB()
	{
		Connection connection=_objDBConnection.getDBConnection();
		
		try {
			_gameDBRetrive=connection.createStatement();
			
			//Retrive Data from player table
			ResultSet gameDBResult=_gameDBRetrive.executeQuery("Select * from LAB05_GAME");
			
			int index=1;
			while(gameDBResult.next())
			{
					Game aGame=new Game();
					//Create checkbox
					RadioButton selectGame=new RadioButton("");
					selectGame.setId("chGame"+index);
					index++;
					selectGame.setToggleGroup(chGameGroup);
					
					aGame.setGameId(gameDBResult.getInt("GAME_ID"));
					aGame.setGameName(gameDBResult.getString("GAME_TITLE"));
					aGame.setChGame(selectGame);

					_gameArray.add(aGame);

			}
			
		}
			catch(SQLException e) {

				System.err.println("ERROR! Couldn't connect to the DB: " + e.getMessage());
			}
		
		finally {
			try {
				//close connection
				connection.close();
				_gameDBRetrive.close();
			} catch (SQLException e) {
				System.err.println("ERROR! Couldn't close the connection to the DB: " + e.getMessage());

			}
		}//End of Finally
	}

}
