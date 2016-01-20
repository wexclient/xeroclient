package com.livngroup.gds.xero.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import com.connectifier.xeroclient.XeroClient;

@Component("xero")
public class ZeroHealthIndicator implements HealthIndicator {

	@Autowired
	private XeroClient xeroClient;
	
	@Override
	public Health health() {
		Health.Builder healthBuilder = new Health.Builder();
		try {
			xeroClient.getBrandingThemes();
			healthBuilder.up();
		} catch (Throwable exc) {
			healthBuilder.outOfService().withDetail("error", exc.getMessage());
		}
		return healthBuilder.build();
	}

}
