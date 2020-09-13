package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AddPlayerController {

	@FXML private TextField _tfPlayerID;
	@FXML private TextField _tfFirstName;
	@FXML private TextField _tfLastName;
	@FXML private TextField _tfAddress;
	@FXML private TextField _tfPostCode;
	@FXML private TextField _tfProvince;
	@FXML private TextField _tfTeleNum;
	@FXML private TextField _tfPlayingDate;
	@FXML private TextField _tfPlayerScore;
	@FXML private TextField _tfGameID;
	@FXML private TextField _tfGameTitle;
	@FXML private TextField _tfPlayerGameID;
	@FXML private Button _btnSubmitPlayer;
	@FXML private Button _btnToHome;
	@FXML private Button _btnUpdatePlayer;
	@FXML private Button _btnRetrievePlayer;
	

	@FXML private Button _btnShowGame;	
	
	//TableView of Player Page
	@FXML private TableView<Game> _gameShowTableView = new TableView<Game>();
	@FXML private ObservableList<Game> _gameShowTableData;
	@FXML private TableColumn<Game,Integer> _colShowGameId=new TableColumn<Game,Integer>();
	@FXML private TableColumn<Game,String> _colShowGameName=new TableColumn<Game,String>();
	@FXML private TableColumn<Game,RadioButton> _colChGame=new TableColumn<Game,RadioButton>();
	
	private DBGame _aGameData=new DBGame();
	
	//Display Game List when create player
	public void playerPageOnLoad()
	{
		_gameShowTableView.getItems().clear();
		
		this._aGameData.buildGameDB();
		
		this._gameShowTableData=FXCollections.observableArrayList(_aGameData.getGameArray());
		_gameShowTableView.setItems(this._gameShowTableData);
		_colShowGameId.setCellValueFactory(cellData->new SimpleIntegerProperty(cellData.getValue().getGameId()).asObject());
		_colShowGameName.setCellValueFactory(cellData->new ReadOnlyStringWrapper(cellData.getValue().getGameName()));
		_colChGame.setCellValueFactory(cellData->new ReadOnlyObjectWrapper<RadioButton>(cellData.getValue().getChGame()));
	}
	
	private Game getSelectGame()
	{
		int tableSize=_gameShowTableView.getItems().size();
		int gameId=0;
		for (int index=0;index<tableSize;index++)
		{
			if (_colChGame.getCellData(index).isSelected())
			{gameId=_colShowGameId.getCellData(index);}
		}
		Game selectGame=new Game();
		for(Game p: _aGameData.getGameArray()) {
			if(p.getGameId()==gameId)
				selectGame = p;
		}
		
		return selectGame;
	}
	
	//method of Go back main page
	@FXML
	private void goToHome(ActionEvent event)
	{
		try {			
			AnchorPane rootToHome = (AnchorPane)FXMLLoader.load(getClass().getResource("Main.fxml"));
			Scene sceneToHome = new Scene(rootToHome,654,560);
			
			//This line gets the Stage information
			Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
			window.setScene(sceneToHome);
			window.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	@FXML
	public void toAddNewGamePage(ActionEvent event) {
		try {			
			AnchorPane rootAddGame = (AnchorPane)FXMLLoader.load(getClass().getResource("AddGame.fxml"));
			Scene sceneAddGame = new Scene(rootAddGame,614,446);
			
			//This line gets the Stage information
			Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
			window.setScene(sceneAddGame);
			window.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	//User Input Information to create new player with new game
	private void insertPlayer(String userInputPlayerID,String userInputFirstName,String userInputLastName,
			String userInputAddress,String userInputPostCode,String userInputProvince,String userInputTeleNu)
	{
		PreparedStatement insertPlayer=null;

		DBConnection objDBConnection=new DBConnection();
		Connection connection=objDBConnection.getDBConnection();

		try{

			insertPlayer = connection.prepareStatement(" INSERT INTO LAB05_PLAYER (PLAYER_ID, FIRST_NAME, LAST_NAME, ADDRESS, POSTAL_CODE, PROVINCE, PHONE_NUMBER) VALUES (?,?,?,?,?,?,?)");
			// 2.1 Initialize parameters of the LAB05_PLAYER table and execute
			insertPlayer.setInt(1, Integer.parseInt(userInputPlayerID));
			insertPlayer.setString(2, userInputFirstName);
			insertPlayer.setString(3, userInputLastName);
			insertPlayer.setString(4, userInputAddress);
			insertPlayer.setString(5, userInputPostCode);
			insertPlayer.setString(6, userInputProvince);
			insertPlayer.setString(7, userInputTeleNu);
			int insertNoRowsPlayer = insertPlayer.executeUpdate();
			System.out.printf("%d number of row(s) have been affected.", insertNoRowsPlayer);

		}//end of try
		catch(SQLException e) {

			System.err.println("ERROR! Couldn't connect to the DB: " + e.getMessage());
		}
		finally{

			try {
				//close Insert
				insertPlayer.close();

			} catch (SQLException e) {
				System.err.println("ERROR! Couldn't close the statement: " + e.getMessage());

			}
			try {
				//close connection
				connection.close();
			} catch (SQLException e) {
				System.err.println("ERROR! Couldn't close the connection to the DB: " + e.getMessage());

			}

		}//End of Finally
		
	}//end of player Insert
	
	private void insertGame(String userInputGameID,String userInputGameTitle) {

		PreparedStatement insertGame=null;

		DBConnection objDBConnection=new DBConnection();
		Connection connection=objDBConnection.getDBConnection();

		try{
			//2.2 Initialize parameters of the LAB05_GAME table and execute
			insertGame = connection.prepareStatement(" INSERT INTO LAB05_GAME (GAME_ID, GAME_TITLE) VALUES (?,?)");
			insertGame.setInt(1, Integer.parseInt(userInputGameID));
			insertGame.setString(2, userInputGameTitle);
			int insertNoRowsGame = insertGame.executeUpdate();
			System.out.printf("%d number of row(s) have been affected.", insertNoRowsGame);

		}//end of try
		catch(SQLException e) {

			System.err.println("ERROR! Couldn't connect to the DB: " + e.getMessage());
		}
		finally{

			try {
				//close Insert
				insertGame.close();

			} catch (SQLException e) {
				System.err.println("ERROR! Couldn't close the statement: " + e.getMessage());

			}
			try {
				//close connection
				connection.close();
			} catch (SQLException e) {
				System.err.println("ERROR! Couldn't close the connection to the DB: " + e.getMessage());

			}

		}//End of Finally
		
	}//End of insert Game
	
	private void insertPlayerGame(String userInputPlayingDate,String userInputPlayerScore,String userInputGameID,
	String userInputGameTitle,String userInputPlayerGameID,String userInputPlayerID) {
		
		PreparedStatement insertPlayerAndGame=null;

		DBConnection objDBConnection=new DBConnection();
		Connection connection=objDBConnection.getDBConnection();

		try{

			// 2.3 Initialize parameters of the LAB05_PLAYER_AND_GAME table and execute
			insertPlayerAndGame = connection.prepareStatement(" INSERT INTO LAB05_PLAYER_AND_GAME (PLAYER_GAME_ID, GAME_ID, PLAYER_ID, PLAYING_DATE, SCORE) VALUES (?,?,?,?,?)");
			insertPlayerAndGame.setInt(1, Integer.parseInt(userInputPlayerGameID));
			insertPlayerAndGame.setInt(2, Integer.parseInt(userInputGameID));
			insertPlayerAndGame.setInt(3, Integer.parseInt(userInputPlayerID));
			insertPlayerAndGame.setString(4, userInputPlayingDate);
			insertPlayerAndGame.setInt(5, Integer.parseInt(userInputPlayerScore));
			int insertNoRowsPlayerAndGame = insertPlayerAndGame.executeUpdate();
			System.out.printf("%d number of row(s) have been affected.", insertNoRowsPlayerAndGame);


		}//end of try
		catch(SQLException e) {

			System.err.println("ERROR! Couldn't connect to the DB: " + e.getMessage());
		}
		finally{

			try {
				//close Insert
				insertPlayerAndGame.close();

			} catch (SQLException e) {
				System.err.println("ERROR! Couldn't close the statement: " + e.getMessage());

			}
			try {
				//close connection
				connection.close();
			} catch (SQLException e) {
				System.err.println("ERROR! Couldn't close the connection to the DB: " + e.getMessage());

			}

		}//End of Finally
		
	}//end of insert player Game
	
	@FXML
	private void dataEntry() {
		//Get the text from the TextFiled
		String userInputPlayerID=this._tfPlayerID.getText();
		String userInputFirstName=this._tfFirstName.getText();
		String userInputLastName=this._tfLastName.getText();
		String userInputAddress=this._tfAddress.getText();
		String userInputPostCode=this._tfPostCode.getText();
		String userInputProvince=this._tfProvince.getText();
		String userInputTeleNu=this._tfTeleNum.getText();
		
		String userInputPlayingDate=this._tfPlayingDate.getText();
		String userInputPlayerScore=this._tfPlayerScore.getText();
		String userInputGameID=this._tfGameID.getText();
		String userInputGameTitle=this._tfGameTitle.getText();
		String userInputPlayerGameID=this._tfPlayerGameID.getText();
		
		insertPlayer(userInputPlayerID,userInputFirstName,userInputLastName,
			userInputAddress,userInputPostCode,userInputProvince,userInputTeleNu);
		insertGame(userInputGameID,userInputGameTitle);
		insertPlayerGame(userInputPlayingDate,userInputPlayerScore,userInputGameID,
				userInputGameTitle,userInputPlayerGameID,userInputPlayerID);
		

	}//END OF DATAENTRY

private void playerUpdate(String userInputPlayerID,String userInputFirstName,String userInputLastName,String userInputAddress,
				String userInputPostCode,String userInputProvince,String userInputTeleNu)
{

	PreparedStatement updatePlayer=null;
	DBConnection objDBConnection=new DBConnection();
    Connection connection=objDBConnection.getDBConnection();
    
    try{
		updatePlayer = connection.prepareStatement(" UPDATE LAB05_PLAYER SET PLAYER_ID, FIRST_NAME=?, LAST_NAME=?, ADDRESS=?, POSTAL_CODE=?, PROVINCE=?, PHONE_NUMBER=? WHERE PLAYER_ID=?");
		// 2.1 Update parameters of the LAB05_PLAYER table and execute

		updatePlayer.setString(1, userInputFirstName);
		updatePlayer.setString(2, userInputLastName);
		updatePlayer.setString(3, userInputAddress);
		updatePlayer.setString(4, userInputPostCode);
		updatePlayer.setString(5, userInputProvince);
		updatePlayer.setString(6, userInputTeleNu);
		updatePlayer.setInt(7, Integer.parseInt(userInputPlayerID));
		int updateNoRowsPlayer = updatePlayer.executeUpdate();
		System.out.printf("%d number of row(s) have been affected.", updateNoRowsPlayer);

	}//end of try
		catch(SQLException e) {

			System.err.println("ERROR! Couldn't connect to the DB: " + e.getMessage());
		}
		finally{

			try {
				//close Insert
				updatePlayer.close();
			} catch (SQLException e) {
				System.err.println("ERROR! Couldn't close the statement: " + e.getMessage());
			}
			try {
				//close connection
				connection.close();
			} catch (SQLException e) {
				System.err.println("ERROR! Couldn't close the connection to the DB: " + e.getMessage());
			}

		}//End of Finally
}//End of Player update


	//PlayerGameUpdate
	private void playerGameUpdate(int userInputGameID,String userInputPlayerID,String userInputPlayingDate,
			String userInputPlayerScore,String userInputPlayerGameID)
	{

		PreparedStatement updatePlayerAndGame=null;
		DBConnection objDBConnection=new DBConnection();
		Connection connection=objDBConnection.getDBConnection();

		try{
			// 2.3 update parameters of the LAB05_PLAYER_AND_GAME table and execute
			updatePlayerAndGame = connection.prepareStatement(" UPDATE LAB05_PLAYER_AND_GAME SET GAME_ID=?, PLAYER_ID=?, PLAYING_DATE=?, SCORE=? WHERE PLAYER_GAME_ID=?");
			updatePlayerAndGame.setInt(1, userInputGameID);
			updatePlayerAndGame.setInt(2, Integer.parseInt(userInputPlayerID));
			updatePlayerAndGame.setString(3, userInputPlayingDate);
			updatePlayerAndGame.setInt(4, Integer.parseInt(userInputPlayerScore));
			updatePlayerAndGame.setInt(5, Integer.parseInt(userInputPlayerGameID));
			int updateNoRowsPlayerAndGame = updatePlayerAndGame.executeUpdate();
			System.out.printf("%d number of row(s) have been affected.", updateNoRowsPlayerAndGame);			
		}//end of try
		catch(SQLException e) {

			System.err.println("ERROR! Couldn't connect to the DB: " + e.getMessage());
		}
		finally{

			try {
				//close update
				updatePlayerAndGame.close();

		} catch (SQLException e) {
			System.err.println("ERROR! Couldn't close the statement: " + e.getMessage());

		}
		try {
			//close connection
			connection.close();
		} catch (SQLException e) {
			System.err.println("ERROR! Couldn't close the connection to the DB: " + e.getMessage());

		}

	}//End of Finally
	}//End of PlayerGame Table
	
	@FXML
	private void dataUpdate() {
		//Get the text from the TextFiled
		String userInputPlayerID=this._tfPlayerID.getText();
		String userInputFirstName=this._tfFirstName.getText();
		String userInputLastName=this._tfLastName.getText();
		String userInputAddress=this._tfAddress.getText();
		String userInputPostCode=this._tfPostCode.getText();
		String userInputProvince=this._tfProvince.getText();
		String userInputTeleNu=this._tfTeleNum.getText();
		
		String userInputPlayingDate=this._tfPlayingDate.getText();
		String userInputPlayerScore=this._tfPlayerScore.getText();
		String userInputPlayerGameID=this._tfPlayerGameID.getText();
		
		
		playerUpdate(userInputPlayerID,userInputFirstName,userInputLastName,userInputAddress,
				userInputPostCode,userInputProvince,userInputTeleNu);

		playerGameUpdate(getSelectGame().getGameId(),userInputPlayerID,userInputPlayingDate,userInputPlayerScore,userInputPlayerGameID);
		


	}//END OF update
	
	@FXML
	private void retrieveData() {
		//Get the text from the TextFiled
				String userInputPlayerID=this._tfPlayerID.getText();
				PreparedStatement reportDBRetrive=null;
			    DBConnection objDBConnection=new DBConnection();
			    Connection connection=objDBConnection.getDBConnection();
				try {
					//Retrieve Data from 3 tables
					reportDBRetrive=connection.prepareStatement("select pl.FIRST_NAME,pl.LAST_NAME,pl.ADDRESS, pl.POSTAL_CODE, pl.PROVINCE, "
							+ "pl.PHONE_NUMBER, ga.GAME_TITLE,plg.playing_date,plg.SCORE, plg.PLAYER_GAME_ID\n" +
							"from LAB05_PLAYER pl\n" +
							"join LAB05_PLAYER_AND_GAME plg on pl.PLAYER_ID=plg.PLAYER_ID\n" +
							"join LAB05_GAME ga on plg.game_id=ga.game_id\n" +
							"where pl.PLAYER_ID=?");

					reportDBRetrive.setInt(1, Integer.parseInt(userInputPlayerID));
					ResultSet playerGameDBResult=reportDBRetrive.executeQuery();

					while (playerGameDBResult.next())
					{
						_tfFirstName.setText(playerGameDBResult.getString(1));
						_tfLastName.setText(playerGameDBResult.getString(2));
						_tfAddress.setText(playerGameDBResult.getString(3));
						_tfPostCode.setText(playerGameDBResult.getString(4));
						_tfProvince.setText(playerGameDBResult.getString(5));
						_tfTeleNum.setText(playerGameDBResult.getString(6));
						_tfGameTitle.setText(playerGameDBResult.getString(7));
						_tfPlayingDate.setText(playerGameDBResult.getString(8));
						_tfPlayerScore.setText(playerGameDBResult.getString(9));
						_tfPlayerGameID.setText(playerGameDBResult.getString(10));
					}
				}
					catch(SQLException e) {

						System.err.println("ERROR! Couldn't connect to the DB: " + e.getMessage());
					}
					finally{

						try {
							//close Insert
							reportDBRetrive.close();

						} catch (SQLException e) {
							System.err.println("ERROR! Couldn't close the statement: " + e.getMessage());

						}
						try {
							//close connection
							connection.close();
						} catch (SQLException e) {
							System.err.println("ERROR! Couldn't close the connection to the DB: " + e.getMessage());

						}

					}//End of Finally

			}//END OF DataRetrieve


}
