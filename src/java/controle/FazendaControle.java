
package controle;

import dao.FazendaDAO;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import modelo.Fazenda;
import modelo.Usuario;

@ManagedBean
@SessionScoped

public class FazendaControle {
    private List<Fazenda> lista;
    private Fazenda fazenda = new Fazenda();
    private boolean salvar =  false;
    private int idUsuario;
    
    public String preparaIncluir(){
        fazenda = new Fazenda();
        salvar = true;
        idUsuario = 0;
        return "cadastroFazenda.xhtml?faces-redirect=true";
        
    }
    
    public String preparaAlterar(){
        salvar = false;
        idUsuario = fazenda.getUsuario().getIdUsuario();
        return "cadastroFazenda.xhtml?faces-redirect=true";
        
    }
    
    @PostConstruct
    public void atualizaLista (){
        try {
            lista = FazendaDAO.getLista();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
     public String salvar(){
            Usuario usuario = new Usuario();
            usuario.setIdUsuario(idUsuario);
            fazenda.setUsuario(usuario);
        try {
            if(salvar){
                FazendaDAO.inserir(fazenda);
                
            }else{
                FazendaDAO.alterar(fazenda);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        atualizaLista();
        return "manutencaoFazenda.xhtml?faces-redirect=true";
     }
    
    public void excluir(){
        try {
            FazendaDAO.excluir(fazenda);
            atualizaLista();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<Fazenda> getLista() {
        return lista;
    }

    public void setLista(List<Fazenda> lista) {
        this.lista = lista;
    }

    public Fazenda getFazenda() {
        return fazenda;
    }

    public void setFazenda(Fazenda fazenda) {
        this.fazenda = fazenda;
    }

    public boolean isSalvar() {
        return salvar;
    }

    public void setSalvar(boolean salvar) {
        this.salvar = salvar;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    
}
