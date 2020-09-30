package com.rmendes.model;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

public class RequestObject {
	
	@FormParam("fileName")
	@PartType(MediaType.TEXT_PLAIN)
	public String fileName;
	
	@FormParam("department")
	@PartType(MediaType.TEXT_PLAIN)
	public String department;
	
	@FormParam("base64File")
	@PartType(MediaType.TEXT_PLAIN)
	public String base64EncodedFile;
	
	@FormParam("file")
	public byte[] file;

}
