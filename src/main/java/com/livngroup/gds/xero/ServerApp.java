package com.livngroup.gds.xero;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.livngroup.gds.xero.domain.LivnAccount;
import com.livngroup.gds.xero.repositories.LivnAccountRepository;


@Configuration
@EnableAutoConfiguration
@SpringBootApplication
@ImportResource(locations = "classpath:xero-client-rest-app-context.xml")
@ComponentScan(basePackages = "com.livngroup.gds.xero")
@EnableJpaRepositories("com.livngroup.gds.xero.repositories")
//@EnableScheduling
@PropertySource("classpath:application-properties.xml")
public class ServerApp {

	public static ApplicationContext APPLICATION_CONTEXT;
	
	final private static Logger logger = LoggerFactory.getLogger(ServerApp.class);

    public static void main(String[] args) {
    	System.setProperty("banner.location", "classpath:xero-client-banner.txt");
    	APPLICATION_CONTEXT = SpringApplication.run(ServerApp.class, args);
        logger.info("application started [" + System.getProperty("spring.profiles.active") + "]...");
    }

}


@Configuration
class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

    @Autowired
    @Qualifier("livnAccountRepository")
    LivnAccountRepository accountRepository;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
    	auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    @Bean
	@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    UserDetailsService userDetailsService() {
        return new UserDetailsService() {
        	@Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                LivnAccount account = accountRepository.findByUsername(username);
                if (account != null) {
                	return new User(account.getUsername(), account.getPassword(), true, true, true, true, 
                			AuthorityUtils.createAuthorityList(StringUtils.split(account.getRole(),",")));
                } else {
                	throw new UsernameNotFoundException("could not find the user '" + username + "'");
                }
            }
        };
    }
    
	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}

@EnableWebSecurity
@Configuration
class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
    	.antMatchers(HttpMethod.GET, "/management/**").hasRole("SUPERADMIN")
    	.antMatchers(HttpMethod.POST, "/management/**").hasRole("SUPERADMIN")
    	.antMatchers(HttpMethod.POST, "/admin/**").hasRole("SUPERADMIN")
    	.antMatchers(HttpMethod.DELETE).hasRole("ADMIN")
    	.anyRequest().hasRole("USER")
        .and().httpBasic()
        .and().csrf().disable();
    }
}