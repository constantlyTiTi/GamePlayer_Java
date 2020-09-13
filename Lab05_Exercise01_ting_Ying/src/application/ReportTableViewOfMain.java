package application;

import java.sql.Date;

public class ReportTableViewOfMain {
	
	private int _aPlayerId;
	private int _aGameId;
	private String _aGameName;
	private String _aPlayingDate;
	private int _score;
	private int _aPlayGameId;
	
	public ReportTableViewOfMain(int playerId,int gameId,int aPlayGameId,String gameName,String aPlayingDate,int score)
	{
		this._aPlayerId=playerId;
		this._aGameId=gameId;
		this._aPlayGameId=aPlayGameId;
		this._aGameName=gameName;
		this._aPlayingDate=aPlayingDate;
		this._score=score;
	}
	public int getAPlayerId()
	{
		return this._aPlayerId;
	}
	public int getPlayGameId()
	{
		return this._aPlayGameId;
	}
	public int getaGameId()
	{
		return this._aGameId;
	}
	public String getaGameName()
	{
		return this._aGameName;
	}
	public String getaPlayingDate()
	{
		return this._aPlayingDate;
	}
	public int getScore()
	{
		return this._score;
	}
}
