package com.rmendes;



import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import com.rmendes.ceph.CephService;
import com.rmendes.model.RequestObject;


@Path("/file")
public class ObjectResource {
	
	@Inject
	CephService cephService;
	
	@GET
	@Path("/teste")
	public String teste() {
		System.out.println("Teste");
		return "teste";
	}

    @PUT
    @Path("/upload")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Timed(name = "uploadTimerBase64", description = "A measure of how long it takes to upload a file to ceph.", unit = MetricUnits.MILLISECONDS)
    public String upload(RequestObject rq) {
    	return cephService.uploadObject(rq.fileName, rq.base64EncodedFile, "teste");
    }
    
    @GET
    @Path("/metadata/{filename}")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> getMetadata(@PathParam("filename") String filename){
    	return null;
    }
    
    @GET
    @Path("/download/base64/{container}/{filename}")
    public String downloadBase64String(@PathParam("filename")String filename, @PathParam("container")String container) {
    	return cephService.downloadObjectAsBase64String(filename, container);
    }
    
    @POST
    @Path("/upload/binary")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Timed(name = "uploadTimerBinary", description = "A measure of how long it takes to upload a file to ceph.", unit = MetricUnits.SECONDS)
    public String uploadBinary(@MultipartForm RequestObject rq) {
    	return cephService.uploadObject(rq.fileName, rq.file, "teste");
    }
    
}