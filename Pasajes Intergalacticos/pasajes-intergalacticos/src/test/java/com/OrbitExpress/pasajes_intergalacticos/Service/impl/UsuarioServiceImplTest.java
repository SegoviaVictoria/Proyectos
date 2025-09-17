package com.OrbitExpress.pasajes_intergalacticos.Service.impl;

import com.OrbitExpress.pasajes_intergalacticos.model.Usuario;
import com.OrbitExpress.pasajes_intergalacticos.repository.UsuarioRepository;
import com.OrbitExpress.pasajes_intergalacticos.service.impl.UsuarioServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsuarioServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testObtenerTodasLosUsuarios() {
        List<Usuario> mockUsuarios = Arrays.asList(new Usuario(), new Usuario());
        when(usuarioRepository.findAll()).thenReturn(mockUsuarios);

        List<Usuario> result = usuarioService.obtenerTodasLosUsuarios();

        assertEquals(2, result.size());
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    public void testObtenerUsuariosPorId_Existe() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Optional<Usuario> result = usuarioService.obtenerUsuariosPorId(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(usuarioRepository).findById(1L);
    }

    @Test
    public void testObtenerUsuariosPorId_NoExiste() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Usuario> result = usuarioService.obtenerUsuariosPorId(1L);

        assertFalse(result.isPresent());
        verify(usuarioRepository).findById(1L);
    }

    @Test
    public void testCrearUsuario() {
        Usuario usuario = new Usuario();
        usuario.setId(10L);

        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario result = usuarioService.crearUsuario(usuario);

        assertNotNull(result);
        assertEquals(10L, result.getId());
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    public void testEliminarUsuario() {
        Long id = 3L;

        usuarioService.eliminarUsuario(id);

        verify(usuarioRepository, times(1)).deleteById(id);
    }
}
