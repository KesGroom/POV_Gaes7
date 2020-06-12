/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Entidades.Rol;
import Entidades.Tipo;
import Entidades.Usuario;
import Facade.RolFacade;
import Facade.TipoFacade;
import Facade.UsuarioFacade;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import static java.lang.Integer.getInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;

/**
 *
 * @author jusag
 */
@Named(value = "usuarioControlador")
@SessionScoped
public class UsuarioControlador implements Serializable {

    /**
     * Creates a new instance of usuarioControlador
     */
    public UsuarioControlador() {
        rol = new Rol();
        tipo = new Tipo();
        usuario = new Usuario();
        usuarioFacade = new UsuarioFacade();
    }

    private Usuario usuario;
    private Rol rol;
    private Tipo tipo;

    @EJB
    UsuarioFacade usuarioFacade;

    @EJB
    RolFacade rolFacade;

    @EJB
    TipoFacade tipoFacade;

    public String registrarUsu() {
        usuario.setIdRoles(rolFacade.find(rol.getIdRoles()));
        usuario.setIdTipo(tipoFacade.find(tipo.getIdTipo()));
        usuarioFacade.create(usuario);
        usuario = new Usuario();

        return "Usuarios";
    }

    public String Remover(Usuario usuarioRemover) {
        usuario = usuarioRemover;
        usuario.setEstado(2);
        usuarioFacade.edit(usuario);
        return "Usuarios";
    }

    public List<Usuario> consultarUsuarios() {
        return usuarioFacade.consultarUsuario(1);
    }

    public List<Usuario> buscarRol(int rol) {
        return usuarioFacade.busquedaRol(rol);
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (filterText == null || filterText.equals("")) {
            return true;
        }
        int filterInt = getInteger(filterText);

        Usuario usu = (Usuario) value;
        return usu.getNumerodeDocumento().toLowerCase().contains(filterText)
                || usu.getIdTipo().getIdTipo() < filterInt
                || usu.getIdRoles().getIdRoles() < filterInt
                || usu.getApellido().toLowerCase().contains(filterText)
                || usu.getNombre().toLowerCase().contains(filterText)
                || usu.getCelular().toLowerCase().contains(filterText)
                || usu.getTelefonoFijo().toLowerCase().contains(filterText)
                || usu.getCorreoElectronico().toLowerCase().contains(filterText)
                || usu.getDireccion().toLowerCase().contains(filterText);

    }

}


