package com.OrbitExpress.pasajes_intergalacticos.model;

/**
 * Clase modelo para solicitudes de inicio de sesión.
 * Contiene los datos necesarios para autenticar a un usuario: email y contraseña.
 */
public class LoginRequest {
    private String email;
    private String password;

    /**
     * Constructor vacío necesario para la deserialización.
     */
    public LoginRequest() {
    }

    /**
     * Obtiene el email del usuario.
     * @return email del usuario
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el email del usuario.
     * @param email email del usuario
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtiene la contraseña del usuario.
     * @return contraseña del usuario
     */
    public String getPassword() {
        return password;
    }

    /**
     * Establece la contraseña del usuario.
     * @param password contraseña del usuario
     */
    public void setPassword(String password) {
        this.password = password;
    }

}