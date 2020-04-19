package io.github.mariazevedo88.diffapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Security configurations for the API to allow Swagger docs without login
 * 
 * @author Mariana Azevedo
 * @since 08/03/2020
 */
@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	/**
	 * Method to configurate swagger routes to be allowed without auth
	 * @author Mariana Azevedo
	 * @since 08/03/2020
	 */
    @Override
    public void configure(WebSecurity web) throws Exception {
        
    	web.ignoring().antMatchers("/v2/api-docs",
                                   "/configuration/ui",
                                   "/swagger-resources/**",
                                   "/configuration/security",
                                   "/swagger-ui.html",
                                   "/webjars/**");
    }
    
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//    	
//    	http.csrf().disable().exceptionHandling()
//			.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//			.and().authorizeRequests().antMatchers("**")
//			.permitAll().anyRequest().authenticated();
//    }

}
