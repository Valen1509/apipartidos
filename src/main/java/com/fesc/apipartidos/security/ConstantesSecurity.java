package com.fesc.apipartidos.security;

public class ConstantesSecurity {

    public static final long FECHA_EXPIRACION= 864000000; //se declara la fecha de expiracion del token en milisegundos
    public static final String SINGUP_URL= "/usuario"; //se declara un endpoint para el login

    public static final String HEADER_STRING= "Authorization";
    public static final String TOKEN_PREFIJO= "Bearer "; //indica quetodo lo que vaya despues de "Bearer" es el token
    public static final String TOKEN_SECRETO= "Ierdj19cNQAd7PBiiUd1xP2Rxl2o9pGyPjyDyfebfW85T2NpvBAOcX5+IjiWDVSXT0RvXNouNDd7B74JP6PYgg==";
}
