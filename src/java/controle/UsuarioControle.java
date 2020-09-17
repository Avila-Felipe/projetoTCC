package controle;

import dao.UsuarioDAO;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import modelo.Usuario;
import util.SessionContext;

@ManagedBean
@SessionScoped

public class UsuarioControle {

    private List<Usuario> lista;
    private Usuario usuario = new Usuario();
    private Usuario usuarioLogado = new Usuario();
    private boolean salvar = false;

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }
    
    public String login() {
        try {
            usuarioLogado = UsuarioDAO.getLogin(usuario);
            if (usuarioLogado == null) {
                System.out.println("USUÁRIO NÃO ENCONTRADO");
                return "";
            } else {

                System.out.println("USUÁRIO ENCONTRADO");
                SessionContext.getInstance().setAttribute("usuario", usuarioLogado);
                if (usuarioLogado.getTipoUsuario().equals("admin")) {
                    return "paginasAdmin/p1Admin.xhtml?faces-redirect=true";
                } else {
                    return "paginasComum/manutencaoFazenda.xhtml?faces-redirect=true";
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }
    
    public String preparaIncluir(){
        usuario = new Usuario();
        salvar = true;
        return "cadastroUsuario.xhtml?faces-redirect=true";
    
    }
    public String preparaAlterar(){
        salvar = false;
        return "cadastroUsuario.xhtml?faces-redirect=true";
        
    }
    
    @PostConstruct
    public void atualizaLista (){
        try {
            lista = UsuarioDAO.getLista();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public String salvar(){
        try {
            if(salvar){
                UsuarioDAO.inserir(usuario);
            }else{
                UsuarioDAO.alterar(usuario);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        atualizaLista();
        return "login.xhtml?faces-redirect=true";
   
}
    
    public void excluir(){
        try {
            UsuarioDAO.excluir(usuario);
            atualizaLista();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<Usuario> getLista() {
        return lista;
    }

    public void setLista(List<Usuario> lista) {
        this.lista = lista;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public boolean isSalvar() {
        return salvar;
    }

    public void setSalvar(boolean salvar) {
        this.salvar = salvar;
    }
   

}
