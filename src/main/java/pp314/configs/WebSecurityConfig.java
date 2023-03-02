package pp314.configs;

import pp314.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.context.annotation.SessionScope;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private SuccessUserHandler successUserHandler;
//	private UserService userService;
	private UserDetailsService userDetailsService;


	@Autowired
	public WebSecurityConfig(SuccessUserHandler successUserHandler,
	                         UserDetailsService userDetailsService) {
		this.successUserHandler = successUserHandler;
		this.userDetailsService = userDetailsService;
	}


	@Override
    protected void configure(HttpSecurity http) throws Exception {
	    http.csrf().disable();
		http
		        .authorizeRequests()
			        .antMatchers("/admin/**", "/user").hasRole("ADMIN")
			        .antMatchers("/user").hasRole("USER")
                .and()
                .formLogin().successHandler(successUserHandler)
                    .permitAll()
                .and()
                .logout()
			        .logoutUrl("/logout")
			        .logoutSuccessUrl("/login")
			        .permitAll();
    }

	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		authenticationProvider.setUserDetailsService(userDetailsService);

		return authenticationProvider;
	}

    @Bean
    @SessionScope
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
