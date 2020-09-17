package controle;

import dao.EventoDAO;
import dao.TipoEventoDAO;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import modelo.TipoEvento;

@ManagedBean
@SessionScoped

public class TipoEventoControle {
    
    private List<TipoEvento> lista;
    private TipoEvento tipoEvento = new TipoEvento();
    private boolean salvar = false;

    public String preparaIncluir(){
        tipoEvento = new TipoEvento();
        salvar = true;
        return "cadastroTipoEvento.xhtml?faces-redirect=true";
        
    }
    public String preparaAlterar(){
        salvar = false;
        return "cadastroTipoEvento.xhtml?faces-redirect=true";
        
    }
    
    @PostConstruct
    public void atualizaLista (){
        try {
            lista = TipoEventoDAO.getLista();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public String salvar(){
        try {
            if(salvar){
                TipoEventoDAO.inserir(tipoEvento);
            }else{
               TipoEventoDAO.alterar(tipoEvento);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        atualizaLista();
        return "manutencaoTipoEvento.xhtml?faces-redirect=true";
    }
    
    public void excluir(){
        try {
            TipoEventoDAO.excluir(tipoEvento);
            atualizaLista();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<TipoEvento> getLista() {
        return lista;
    }

    public void setLista(List<TipoEvento> lista) {
        this.lista = lista;
    }

    public TipoEvento getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(TipoEvento tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public boolean isSalvar() {
        return salvar;
    }

    public void setSalvar(boolean salvar) {
        this.salvar = salvar;
    }
}