package sv.edu.uesocc;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;;

@Resource
@EnableResourceServer
@EnableWebSecurity
public class GatewayConfiguration extends ResourceServerConfigurerAdapter{
	
    @Autowired
    private Environment env;
	
	@Bean
	public ResourceServerTokenServices createResourceServerTokenServices() {
	    RemoteTokenServices tokenServices = new RemoteTokenServices();
	    tokenServices.setCheckTokenEndpointUrl(env.getProperty("security.oauth2.resource.token-info-uri"));
	    tokenServices.setClientId(env.getProperty("security.oauth2.client.client-id"));
	    tokenServices.setClientSecret(env.getProperty("security.oauth2.client.client-secret"));
	    return tokenServices;
	}
	
    @Override
    public void configure(final HttpSecurity http) throws Exception {
    http.authorizeRequests()
          .antMatchers
          		 (
          		  "/actuator/**", "/api-docs/**"
        		  ,"/authorization-server/oauth/**"
        		  ,"/authorization-server/tokens/remove-token"
        		 )
          .permitAll()
          .antMatchers("/**")
      .authenticated()
      .and()
      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      ;
    }
}