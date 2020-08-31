package com.example.loginservicems;

import java.security.Principal;
import java.time.LocalTime;
import java.util.Random;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@SpringBootApplication
@EnableOAuth2Sso //Enables OAuth2 Single Sign On, will automatically use application.yml properties for security
@RestController //Enabling REST functionality. With this, we can now expose our own endpoints
public class LoginServicemSApplication extends WebSecurityConfigurerAdapter{

	public static void main(String[] args) {
		SpringApplication.run(LoginServicemSApplication.class, args);
	}
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
 
        //Configuring Spring security access. For /login, /user, and /userinfo, we need authentication.
        //Logout is enabled.
        //Adding csrf token support to this configurer.
        http.authorizeRequests()
                .antMatchers("/login**", "/user","/userInfo").authenticated()
                .and().logout().logoutSuccessUrl("/").permitAll()
                .and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
 
    }
	
	@RequestMapping("/user")
    public Principal user(Principal principal) {
        //Principal holds the logged in user information.
        // Spring automatically populates this principal object after login.
        return principal;
    }
 
    @RequestMapping("/userInfo")
    public String userInfo(Principal principal){
        final OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) principal;
        final Authentication authentication = oAuth2Authentication.getUserAuthentication();
        //Manually getting the details from the authentication, and returning them as String.
        return authentication.getDetails().toString();
    }
    
    @RequestMapping("/userToken")
    public String getUserToken(Principal principal) {

    	String tokenValue = null;
    	final Authentication authenticationObject = SecurityContextHolder.getContext().getAuthentication();
    	final Object detailObject = authenticationObject.getDetails();
    	System.out.println("Access tokens");
    	System.out.println(detailObject);
    	if (detailObject instanceof OAuth2AuthenticationDetails) {

            final OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) detailObject;
            tokenValue = details.getTokenValue();
            System.out.println("Access tokens1="+tokenValue);

        } else if (detailObject instanceof OAuth2AccessToken) {

            final OAuth2AccessToken token = (OAuth2AccessToken) detailObject;
            tokenValue = token.getValue();
            System.out.println("Access tokens2="+tokenValue);

        } else {

            tokenValue = null;
            System.out.println("Access tokens3="+tokenValue);

        }

        String timeTokenCreated = LocalTime.now().toString();
        return tokenValue+":TTL:"+timeTokenCreated;

    }
    

}
