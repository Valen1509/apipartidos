package com.fesc.apipartidos;

import com.fesc.apipartidos.utils.AppContexto;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.crypto.SecretKey;

@SpringBootApplication //(exclude = {SecurityAutoConfiguration.class})
@EnableJpaAuditing //se utiliza para activar la auditoria a nivel global
public class ApipartidosApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApipartidosApplication.class, args);

		System.out.println("Api corriendo...");

		//SecretKey key= Keys.secretKeyFor(SignatureAlgorithm.HS512); //generar una clave secreta con un algoritmo para crear los tokens
		//String key64= Encoders.BASE64.encode(key.getEncoded()); //convierte la clave en string para poderla mostrar
		//System.out.println(key64); //muestra la clave

		// clave: Ierdj19cNQAd7PBiiUd1xP2Rxl2o9pGyPjyDyfebfW85T2NpvBAOcX5+IjiWDVSXT0RvXNouNDd7B74JP6PYgg==
	}

	@Bean
	public ModelMapper modelMapper(){ //crear la instancia de la libreria para que sirva en las demas clases

		ModelMapper modelMapper=new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return modelMapper;
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder(){ //se hace la instacia de "BCryptPasswordEncoder" para la encriptacion de la contraseña

		return new BCryptPasswordEncoder();
	}

	@Bean
	public AppContexto appContexto(){
		return new AppContexto();
	}
}
