package com.fesc.apipartidos.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class TokenAutorizacion extends BasicAuthenticationFilter { //validar el token

    public TokenAutorizacion(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) //recibe la peticion
            throws IOException, ServletException {

            String header= request.getHeader(ConstantesSecurity.HEADER_STRING); //traer el encabezado

            if (header==null || !header.startsWith(ConstantesSecurity.TOKEN_PREFIJO)) { //si el encabezado es igual a null o es diferente al prefijo
                chain.doFilter(request, response); //continue al siguiente filtro

                return;
            }

                UsernamePasswordAuthenticationToken authenticationToken= getAuthenticationToken(header); //si esta el encabezado lo enviamos al metodo "getAuthenticationToken"

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                chain.doFilter(request, response);
    }


    private UsernamePasswordAuthenticationToken getAuthenticationToken(String header) {

        if (header!=null){
            String token= header.replace(ConstantesSecurity.TOKEN_PREFIJO, ""); //quitamos el prefijo

            SecretKey key= Keys.hmacShaKeyFor(Decoders.BASE64.decode(ConstantesSecurity.TOKEN_SECRETO)); //genera la clave secreta con el token

            String username= Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject(); //va a comparar que el token fue hecho con esa clave, si es asi obtiene el cuerpo y el sujeto

            if (username!=null) {
                return new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>()); //si lo encuentra crea una autentificacio
            }

        }

        return null; //si no retorna null
    }


}
