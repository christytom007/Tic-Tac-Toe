package com.isi.java.tictactoe.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GameLogFileHandler
{

	private String path = "Game Logs" + File.separator + "Log_";
	private String fileSuffix;

	private PrintWriter logFile;

	public GameLogFileHandler()
	{
		fileSuffix = new SimpleDateFormat("yyyyMMddHHmmss'.txt'").format(new Date());
		path += fileSuffix;
		try
		{
			new File("Game Logs\\").mkdir();
			logFile = new PrintWriter(path, "UTF-8");
		}
		catch (FileNotFoundException | UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}

	}

	public void writeLog(String message)
	{
		String timeStamp = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss -> ").format(new Date());
		logFile.println(timeStamp + message);
		logFile.flush();
	}

	public void closeLogFile()
	{
		logFile.close();
	}
}
