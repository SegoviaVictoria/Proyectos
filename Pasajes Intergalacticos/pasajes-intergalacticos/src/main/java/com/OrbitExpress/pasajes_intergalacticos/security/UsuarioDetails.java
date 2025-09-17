// Ubicación sugerida: src/main/java/com/OrbitExpress/pasajes_intergalacticos/security/UsuarioDetails.java

package com.OrbitExpress.pasajes_intergalacticos.security;

import com.OrbitExpress.pasajes_intergalacticos.model.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UsuarioDetails implements UserDetails {

    private final Usuario usuario;

    public UsuarioDetails(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Convertimos el rol del usuario a formato Spring Security (con prefijo "ROLE_")
        return List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getRol().name()));
    }

    @Override
    public String getPassword() {
        return usuario.getPassword();
    }

    @Override
    public String getUsername() {
        return usuario.getEmail(); // usamos el email como nombre de usuario
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // puedes cambiar esto si gestionas expiraciones
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // idem para bloqueo de cuenta
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true; // o algo como usuario.getActivo() si lo tienes
    }

    public Usuario getUsuario() {
        return usuario;
    }
}
