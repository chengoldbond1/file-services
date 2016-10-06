package com.projects;
import java.io.BufferedReader;
import java.io.File;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class FilesHandlerObj {

	private String filesExtension;               //Type of Files
	private String m_deafultTitels;             //Columns titels
	private String m_SaveDirPath; 				//Base path for output files
	private String m_FilePath; 					//Source file path
	
	private int m_filesCounter = 0; 			 //Counts the number of files already created
	private int m_currFileLinesCounter = 0; 	 //Counts the current number of lines in the file
	private int linesPerFile = 1000;			   //Number of lines in each created file
	
	private FileWriter m_wr = null;
	private BufferedReader m_fr = null;
	

	
	
	/**
	 * Initialize new instance of FilesHandlerObj
	 * This handler iterate through a given source file and split it to smaller files
	 */
	
	public FilesHandlerObj(String sourceFilePath, String titels, String linesPerFile ) throws IOException {
		setLinesPerFile(linesPerFile);
		File src = new File(sourceFilePath);
		String outPutFolderPath = System.getProperty("user.dir")+"/outputs-" + src.getName() ;
		new File(outPutFolderPath).mkdirs();
		this.m_SaveDirPath = outPutFolderPath;
		this.filesExtension = sourceFilePath.substring(sourceFilePath.lastIndexOf('.') + 1);
		this.m_FilePath = sourceFilePath;
		this.m_deafultTitels = titels;
		this.m_fr = new BufferedReader(new FileReader(this.m_FilePath));
		CreateWriter();
		
		System.out.println("sub files are located in : " + outPutFolderPath);
		
		///delete the temp file after the procces
		src.delete();
	}

	
	/**
	 * Set the variable linesPerFile
	 */
	private void setLinesPerFile(String numOfLinesFromConfigFile)
	{
		int numOfFiles = 0;
		try
		{
			numOfFiles = Integer.parseInt(numOfLinesFromConfigFile);
		}
		catch(Exception e)
		{
			System.out.println("number of lines per file wasnt configured, deafult value its 1000");
		}
		
		if(numOfFiles!=0)
		this.linesPerFile = numOfFiles;
	}

	/**
	 * Iterate source file and split it to small files with the specified number of lines per file
	 */
	public void IterateSourceFile() {
		try {
			String currLine;
			while ((currLine = m_fr.readLine()) != null) {
				
				//if we got LINES_PER_FILE lines in the current file - create new file
				if (m_currFileLinesCounter == linesPerFile) {
					CreateWriter();
				}
				this.m_wr.write(currLine + "\n");
				m_currFileLinesCounter++;
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (m_fr != null)
					m_fr.close();
				CloseWriter();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * Create new writer with default titles
	 * @throws IOException
	 */
	private void CreateWriter() throws IOException {
		CloseWriter();
		
		this.m_filesCounter++;
		this.m_currFileLinesCounter = 0;
		this.m_wr = new FileWriter(m_SaveDirPath + "/" + m_filesCounter + "." + this.filesExtension);
		this.m_wr.write(m_deafultTitels);
		this.m_wr.write("\n");

	}
	
	/**
	 * Close the current writer - flush and close
	 * @throws IOException
	 */
	private void CloseWriter() throws IOException {
		if (this.m_wr != null) {
			this.m_wr.flush();
			this.m_wr.close();
		}
	}
}
