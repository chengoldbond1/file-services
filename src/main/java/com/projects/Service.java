package com.projects;


import java.io.File;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.context.annotation.PropertySource;



@Controller
@RequestMapping("/myfileservice")

@PropertySource("classpath:config.properties")

public class Service {
	
	
	private String deafultsTitles = "NAME,EMAIL,ID,ADRESS";
	
	///Get number of lines from the configuration file if it exist
	@Value("${number.lines}")
	private String numberOfLines;
	
		
	////Main method of the service
	@RequestMapping(value = "/getFileService", method = RequestMethod.POST)
    public void getFileService(@RequestPart("file") MultipartFile file) throws IllegalStateException, IOException
    {
		
		File tempSrcFile = new File(System.getProperty("user.dir")+"/"+file.getOriginalFilename());
		file.transferTo(tempSrcFile);
		String srcPathFile = tempSrcFile.getAbsolutePath();
		FilesHandlerObj cs = new FilesHandlerObj(srcPathFile, deafultsTitles, this.numberOfLines);
		cs.IterateSourceFile();
		
    }	
	
	
	
	///adding a column to the service
	@RequestMapping(value = "/addCoulumnToTheService/{col}")
	public @ResponseBody void sum(@PathVariable String col)
	{
	this.deafultsTitles += "," + col;
		
	}
	
	
	
	
}
