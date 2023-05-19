package com.fesc.apipartidos.security;

import com.fesc.apipartidos.services.IUsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Configuration
@EnableWebSecurity //permite activar y configurar la seguridad de una aplicación web en Spring Boot
public class ConfiguracionSecurity extends WebSecurityConfigurerAdapter { //"WebSecurityConfigurerAdapter" se utiliza para configurar la seguridad en una aplicación web al exterdela se obtiene metodos que van a permitir hacer la configuracion de los endpoints y la autentificacion del usuario

    private final IUsuarioService iUsuarioService;
    private  final BCryptPasswordEncoder bCryptPasswordEncoder;

    public ConfiguracionSecurity(IUsuarioService iUsuarioService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.iUsuarioService = iUsuarioService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception { //sobreescribir los metodos
        http//decirle a que rutas podemos acceder
                .cors() //activar los cors
                .and()
                .csrf().disable() //desacticar la proteccion csrf
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/usuario").permitAll() //activar endpoints, en este caso todos pueden loggearse
                .antMatchers(HttpMethod.GET, "/partido").permitAll() //activar endpoints, en este caso todos pueden ver los partidos
                .antMatchers(HttpMethod.GET, "/partido/{ïd}").permitAll() //activar endpoints, en este caso todos pueden ver los detalles del partido
                .anyRequest().authenticated() //el resto de solicitudes a excepcion de las anteriores se necesita loggearse
                .and()
                .addFilter(getUsuarioAutentication())
                .addFilter(new TokenAutorizacion(authenticationManager()))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception { //este metodo nos cva a permitir autenticarnos
        auth.userDetailsService(iUsuarioService).passwordEncoder(bCryptPasswordEncoder);
    }

    public UsuarioAutenticacion getUsuarioAutentication() throws  Exception {

        final UsuarioAutenticacion usuarioAutenticacion= new UsuarioAutenticacion(authenticationManager());

        usuarioAutenticacion.setFilterProcessesUrl("/usuario/login");

        return usuarioAutenticacion;
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration= new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));

        UrlBasedCorsConfigurationSource source= new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
    //@Override
    //public void addCorsMappings(CorsRegistry registry) { //metodo para especificar que es lo que se quiere activar //es donde el va estar mapeando toda la informacion
       // registry.addMapping("/**") //se especifica de donde van a permitir los ingresos
                //.allowedMethods("*").allowedOrigins("*").allowedHeaders("*"); //que tipo de metodos va a recibir en este caso acepta tpdos
    //}

}
