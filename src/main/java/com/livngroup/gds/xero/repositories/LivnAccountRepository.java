package com.livngroup.gds.xero.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.livngroup.gds.xero.domain.LivnAccount;

public interface LivnAccountRepository extends JpaRepository<LivnAccount, String> {
	 
	public com.livngroup.gds.xero.domain.LivnAccount findByUsername(String username);

}
