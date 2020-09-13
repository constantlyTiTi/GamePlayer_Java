package application;

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
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.sql.*;
import java.util.ArrayList;

public class MainController {
	
	@FXML private Button _btnAddNewPlayer;
	@FXML private Button _btnShowInfor;
	@FXML private Button _btnDisplayPlayerTable;
	
	private DBPlayer aPlayerData=new DBPlayer();
	@FXML private TableView<ReportTableViewOfMain> _reportTableView = new TableView<ReportTableViewOfMain>();
	@FXML private ObservableList<ReportTableViewOfMain> _mainReportTableOberservableList=null;
	@FXML private ArrayList<ReportTableViewOfMain> _mainReportTableArrayList=new ArrayList<>();
	@FXML private TableColumn<ReportTableViewOfMain,Integer> _colPlayerId=new TableColumn<ReportTableViewOfMain,Integer>();
	@FXML private TableColumn<ReportTableViewOfMain,Integer> _colPlayGameId=new TableColumn<ReportTableViewOfMain,Integer>();
	@FXML private TableColumn<ReportTableViewOfMain,Integer> _colGameId=new TableColumn<ReportTableViewOfMain,Integer>();
	@FXML private TableColumn<ReportTableViewOfMain,String> _colGameName=new TableColumn<ReportTableViewOfMain,String>();
	@FXML private TableColumn<ReportTableViewOfMain,String> _colPlayingDate=new TableColumn<ReportTableViewOfMain,String>();
	@FXML private TableColumn<ReportTableViewOfMain,Integer> _colScore=new TableColumn<ReportTableViewOfMain,Integer>();
	
	@FXML private TableView<Player> _playerShowTableView = new TableView<Player>();
	@FXML private ObservableList<Player> _playerShowTableOberservableList=null;
	@FXML private TableColumn<Player,Integer> _colShowPlayerId;
	@FXML private TableColumn<Player,String> _colShowPlayerFirstName;
	@FXML private TableColumn<Player,String> _colShowPlayerLastName;
	@FXML private TableColumn<Player,RadioButton> _colSelect;
	
	@FXML private TextArea _playerInfor;
	
	//Change to Add new player window when click the btn	
	@FXML
	private void toAddNewPlayerPage(ActionEvent event) {
		try {			
			AnchorPane rootAddPlayer = (AnchorPane)FXMLLoader.load(getClass().getResource("AddPlayer.fxml"));
			Scene sceneAddPlayer = new Scene(rootAddPlayer,691,521);
			
			//This line gets the Stage information
			Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
			window.setScene(sceneAddPlayer);
			window.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}//End of toAddNewPlayerPage method;
	
//	//Change to Add new game window when click the btn  this windows is a increment window, maybe updated in future 
//	@FXML
//	private void toAddNewGamePage(ActionEvent event) {
//		try {			
//			AnchorPane rootAddGame = (AnchorPane)FXMLLoader.load(getClass().getResource("AddGame.fxml"));
//			Scene sceneAddGame = new Scene(rootAddGame,614,446);
//			
//			//This line gets the Stage information
//			Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
//			window.setScene(sceneAddGame);
//			window.show();
//			
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//	}//End of toAddNewGamePage method
	
	@FXML
	private void displayPlayerTable()
	{
		aPlayerData.buildPlayerDB();
//		_playerShowTableView.getItems().clear();

		this._playerShowTableOberservableList=FXCollections.observableArrayList(aPlayerData.getPlayerArray());
		_playerShowTableView.setItems(this._playerShowTableOberservableList);
		_colShowPlayerId.setCellValueFactory(cellData->new SimpleIntegerProperty(cellData.getValue().getPlayerId()).asObject());
		_colShowPlayerFirstName.setCellValueFactory(cellData->new ReadOnlyStringWrapper(cellData.getValue().getPlayerFirstName()));
		_colShowPlayerLastName.setCellValueFactory(cellData->new ReadOnlyStringWrapper(cellData.getValue().getPlayerLastName()));
		_colSelect.setCellValueFactory(cellData->new ReadOnlyObjectWrapper<RadioButton>(cellData.getValue().getchPlayer()));
	}
	
	private Player getChPlayer()
	{
		int tableSize=_playerShowTableView.getItems().size();
		int playerId=0;
		for (int index=0;index<tableSize;index++)
		{
			if (_colSelect.getCellData(index).isSelected())
			{playerId=_colShowPlayerId.getCellData(index);}
		}
		Player selectPlayer=new Player();
		for(Player p: aPlayerData.getPlayerArray()) {
			if(p.getPlayerId()==playerId)
				selectPlayer = p;
		}
		
		return selectPlayer;
	}
	private void reportTableDisplay(int playerId)
	{
		DBConnection objDBConnection=new DBConnection();
		Connection connection=objDBConnection.getDBConnection();
		PreparedStatement reportDBRetrive=null; 
		try {
//			Retrive Data from 3 tables
			reportDBRetrive=connection.prepareStatement("select pl.PLAYER_ID,plg.player_game_id,plg.game_id,ga.game_title,plg.playing_date,plg.SCORE " + 
					"from LAB05_PLAYER pl " + 
					"join LAB05_PLAYER_AND_GAME plg on pl.PLAYER_ID=plg.PLAYER_ID " + 
					"join LAB05_GAME ga on plg.game_id=ga.game_id " + 
					"where pl.PLAYER_ID=? ");
			
			reportDBRetrive.setInt(1, playerId);
			ResultSet playerGameDBResult=reportDBRetrive.executeQuery();
			int index=1;
			while(playerGameDBResult.next())
			{
//					RadioButton selectPlayer=new RadioButton("");
//					selectPlayer.setId("RbPlayer"+index);
					index++;
					int reportPlayerId=Integer.parseInt(playerGameDBResult.getString(1));
					int reportPlayGameId=Integer.parseInt(playerGameDBResult.getString(2));
					int reportGameId=Integer.parseInt(playerGameDBResult.getString(3));
					String reportGameName=playerGameDBResult.getString(4);
					String reportPlayingDate=playerGameDBResult.getString(5);
					int reportScore=Integer.parseInt(playerGameDBResult.getString(6));
					
					_mainReportTableArrayList.add
					(new ReportTableViewOfMain(reportPlayerId,reportPlayGameId,reportGameId,reportGameName
							,reportPlayingDate,reportScore));
					
			}
			this._mainReportTableOberservableList=FXCollections.observableArrayList(_mainReportTableArrayList);
			_reportTableView.setItems(_mainReportTableOberservableList);
			_colPlayerId.setCellValueFactory(cellData->new SimpleIntegerProperty(cellData.getValue().getAPlayerId()).asObject());
			_colPlayGameId.setCellValueFactory(cellData->new SimpleIntegerProperty(cellData.getValue().getPlayGameId()).asObject());
			_colGameId.setCellValueFactory(cellData->new SimpleIntegerProperty(cellData.getValue().getaGameId()).asObject());
			_colGameName.setCellValueFactory(cellData->new ReadOnlyStringWrapper(cellData.getValue().getaGameName()));
			_colPlayingDate.setCellValueFactory(cellData->new ReadOnlyStringWrapper(cellData.getValue().getaPlayingDate()));
			_colScore.setCellValueFactory(cellData->new SimpleIntegerProperty(cellData.getValue().getScore()).asObject());	
					
		}
			catch(SQLException e) {

				System.err.println("ERROR! Couldn't connect to the DB: " + e.getMessage());
			}
		
		finally {
			try {
				//close connection
				reportDBRetrive.close();
				connection.close();
			} catch (SQLException e) {
				System.err.println("ERROR! Couldn't close the connection to the DB: " + e.getMessage());

			}
		}//End of Finally
		
	}//End of reportTableDisplay method
	
	private void reportTextAreaDisplay(int playerId)
	{
		DBConnection objDBConnection=new DBConnection();
		Connection connectionText=objDBConnection.getDBConnection(); 
		PreparedStatement textAreaRetrive=null;
		try {
//			Retrive Data from 3 tables
			textAreaRetrive=connectionText.prepareStatement("select * from LAB05_PLAYER where PLAYER_ID=?");
			textAreaRetrive.setInt(1, playerId);
			ResultSet textAreaDBResults= textAreaRetrive.executeQuery();
		
			//TextArea Show
			while(textAreaDBResults.next())
			{

				_playerInfor.setText(String.format("PLAYER_ID: %s\n"+"FIRST_NAME: %s\n"+"LAST_NAME: %s\n"
			+"ADDRESS: %s\n"+"POSTAL_CODE: %s\n"+"PROVINCE: %s\n"+"PHONE_NUMBER: %s\n",
			textAreaDBResults.getString(1),
			textAreaDBResults.getString(2),
			textAreaDBResults.getString(3),
			textAreaDBResults.getString(4),
			textAreaDBResults.getString(5),
			textAreaDBResults.getString(6),
			textAreaDBResults.getString(7)));
			}
					
		}
			catch(SQLException e) {

				System.err.println("ERROR! Couldn't connect to the DB: " + e.getMessage());
			}
		
		finally {
			try {
				//close connection
				textAreaRetrive.close();
				connectionText.close();
			} catch (SQLException e) {
				System.err.println("ERROR! Couldn't close the connection to the DB: " + e.getMessage());

			}
		}//End of Finally
		
	}//End of tableViewDisplay method
	
	
	@FXML
	private void reportDisplay() {
		//Get PlayerId;
		int playerId=getChPlayer().getPlayerId();
		reportTableDisplay(playerId);
		reportTextAreaDisplay(playerId);
		
	}
	
	
}
