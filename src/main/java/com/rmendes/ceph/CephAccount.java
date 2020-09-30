package com.rmendes.ceph;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.javaswift.joss.client.factory.AccountConfig;
import org.javaswift.joss.client.factory.AccountFactory;
import org.javaswift.joss.client.factory.AuthenticationMethod;
import org.javaswift.joss.model.Account;

import io.quarkus.runtime.StartupEvent;


@ApplicationScoped
public class CephAccount {
	
	
	private Account account; 
	
	@ConfigProperty(name = "ceph.api.account.user")
	protected String user;
	
	@ConfigProperty(name = "ceph.api.account.password")
	protected String password;
	
	@ConfigProperty(name = "ceph.api.account.auth.url")
	protected String authUrl;
	
	void startup(@Observes StartupEvent event) {
		System.out.println(authUrl);
		System.out.println(user);
		System.out.println(password);
		AccountConfig cfg = new AccountConfig();
		cfg.setUsername(user);
		cfg.setPassword(password);
		cfg.setAuthUrl(authUrl);
		cfg.setAuthenticationMethod(AuthenticationMethod.BASIC);
		this.account = new AccountFactory(cfg).createAccount();
	}

	public Account getAccount() {
		return account;
	}

}
