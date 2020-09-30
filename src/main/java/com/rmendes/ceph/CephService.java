package com.rmendes.ceph;



import java.io.FileNotFoundException;

import javax.inject.Inject;

import org.apache.commons.codec.binary.Base64;
import org.javaswift.joss.model.Container;
import org.javaswift.joss.model.StoredObject;
import org.springframework.stereotype.Service;

@Service
public class CephService {

//	private static final String user = "api-ceph:swift-user";
//	private static final String password = "PYtzpN71TlPZHj7iMqiexpwCo0NNbbpxkiY5AdBt";
//	private static final String authUrl = "http://rgws-0.rmendescephcluster.lab.pnq2.cee.redhat.com:8080/auth/";
	
	@Inject
	CephAccount accountObject;
	
	public Container CreateOrGetContainer(String containerName) { 
		Container container = accountObject.getAccount().getContainer(containerName);
		if(container.exists()) {
			container.makePublic();
			return container;
		}else {
			container.create();
			container.makePublic();
			return container;
		}
	}
	
	public String uploadObject(String filename, byte[] file, String containerName) {
		Container container = CreateOrGetContainer(containerName);
		StoredObject object = container.getObject(filename);
		object.uploadObject(file);
		return object.getURL();
	}
	
	public String uploadObject(String fileName, String base64File, String containerName) {
		Container container = CreateOrGetContainer(containerName);
		StoredObject object = container.getObject(fileName);
		object.uploadObject(Base64.decodeBase64(base64File));
		return object.getURL();
	}
	
	public String downloadObjectAsBase64String(String fileName, String containerName) {
		Container container = CreateOrGetContainer(containerName);
		StoredObject object = container.getObject(fileName);
		if(!object.exists()) {
			new FileNotFoundException(fileName);
		}
		return Base64.encodeBase64String(object.downloadObject());
	}
	
//	private Account configAccount() {
//		AccountConfig cfg = new AccountConfig();
//		cfg.setUsername(user);
//		cfg.setPassword(password);
//		cfg.setAuthUrl(authUrl);
//		cfg.setAuthenticationMethod(AuthenticationMethod.BASIC);
//		return new AccountFactory(cfg).createAccount();
//	}
//	
	
	
}
