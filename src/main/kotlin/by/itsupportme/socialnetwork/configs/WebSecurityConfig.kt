package by.itsupportme.socialnetwork.configs


import by.itsupportme.socialnetwork.filters.JwtRequestFilter
import by.itsupportme.socialnetwork.points.JwtAuthenticationEntryPoint
import by.itsupportme.socialnetwork.services.JwtUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.provider.token.AccessTokenConverter
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfig(
        @Autowired
        private var jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint,
        @Autowired
        private var jwtUserDetailsService: JwtUserDetailsService,
        @Autowired
        private var jwtRequestFilter: JwtRequestFilter
) : WebSecurityConfigurerAdapter() {
//  eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJuIiwiZXhwIjoxNTk0Mzk4MjIyLCJpYXQiOjE1OTQzODAyMjJ9.O_cRjzgl099bnXFd4us9D-E0xyyXhk-lByOKstw9naG6UQVuJuT9vfxlqnwc1ilBk5a_xHgCqKKignuCXh5HnA
//  eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJuIiwiZXhwIjoxNTk0Mzk4MTk4LCJpYXQiOjE1OTQzODAxOTh9.hmSqn4fxRSUZ3QG3jMnkynndCKTflcUWdUPyUtkewyplocgNToefS2vVqFgsvSClo2Ogybt6pfaK9TNBTF7Syw
    @Autowired
    @Throws(Exception::class)
    fun configureGlobal(auth: AuthenticationManagerBuilder) {
// configure AuthenticationManager so that it knows from where to load
// user for matching credentials
// Use BCryptPasswordEncoder
        auth.userDetailsService<UserDetailsService?>(jwtUserDetailsService).passwordEncoder(passwordEncoder())
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(authProvider())
    }

    @Bean
    fun authProvider(): DaoAuthenticationProvider? {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(jwtUserDetailsService)
        authProvider.setPasswordEncoder(passwordEncoder())
        return authProvider
    }

    @Bean
    @Throws(Exception::class)
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    @Bean
    fun tokenStore(): InMemoryTokenStore? {
        return InMemoryTokenStore()
    }

    @Throws(Exception::class)
    override fun configure(httpSecurity: HttpSecurity) {
        httpSecurity.csrf().disable()
                .authorizeRequests().antMatchers("/registration", "/login").permitAll().anyRequest().authenticated().and().exceptionHandling()
        .authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter::class.java)
    }


//
//    @Throws(java.lang.Exception::class)
//    fun configure(configurer: AuthorizationServerEndpointsConfigurer) {
//        configurer.authenticationManager(authenticationManagerBean())
//        configurer.userDetailsService(userDetailsServiceBean())
//        configurer.accessTokenConverter(accessTokenConverter())
//        configurer.tokenStore(tokenStore())
//    }


}