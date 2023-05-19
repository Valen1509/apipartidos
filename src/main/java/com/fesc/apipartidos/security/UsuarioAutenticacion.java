package com.fesc.apipartidos.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fesc.apipartidos.models.peticiones.UsuarioSingUpRequestModel;
import com.fesc.apipartidos.services.IUsuarioService;
import com.fesc.apipartidos.shared.UsuarioDto;
import com.fesc.apipartidos.utils.AppContexto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class UsuarioAutenticacion extends UsernamePasswordAuthenticationFilter { //aca se permite la autentificacion del usuario

    private final AuthenticationManager authenticationManager;

    public UsuarioAutenticacion(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) //recibe la peticion
            throws AuthenticationException {

            try {

                UsuarioSingUpRequestModel usuarioSingUpRequestModel= new ObjectMapper().readValue( //se hace el mapeo
                        request.getInputStream(), UsuarioSingUpRequestModel.class);

                UsernamePasswordAuthenticationToken upat= new UsernamePasswordAuthenticationToken( //modelamos la peticion
                        usuarioSingUpRequestModel.getUsername(),
                        usuarioSingUpRequestModel.getPassword(),
                        new ArrayList<>());

                Authentication authentication= authenticationManager.authenticate(upat); // se hace la autenticacion

                return authentication;

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        String username= ((User) authResult.getPrincipal()).getUsername(); //se obtiene el username

        SecretKey key= Keys.hmacShaKeyFor(Decoders.BASE64.decode(ConstantesSecurity.TOKEN_SECRETO)); //generar la clave secreta

        String token= Jwts.builder() //se crea el token
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis()+ConstantesSecurity.FECHA_EXPIRACION))
                .signWith(key)
                .compact();

        IUsuarioService iUsuarioService= (IUsuarioService) AppContexto.getBean("usuarioService");
        UsuarioDto usuarioDto= iUsuarioService.leerUsuario(username);

            response.addHeader("Access-Control-Exponse-Headers", "Authorization. IdUsuario");
            response.addHeader("idUsuario", usuarioDto.getIdUsuario());
            response.addHeader(ConstantesSecurity.HEADER_STRING, ConstantesSecurity.TOKEN_PREFIJO + token); //respuesta con el token
    }
}
