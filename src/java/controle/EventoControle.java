package controle;

import dao.EventoDAO;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import modelo.Evento;
import modelo.TipoEvento;

@ManagedBean
@SessionScoped

public class EventoControle {
    
    private List<Evento> lista;
    private Evento evento = new Evento();
    private boolean salvar = false;
    private int idTipo;

    public String preparaIncluir(){
        evento = new Evento();
        salvar = true;
        idTipo = 0;
        return "cadastroEvento.xhtml?faces-redirect=true";
        
    }
    public String preparaAlterar(){
        salvar = false;
        idTipo = evento.getTipoEvento().getIdTipo();
        return "cadastroEvento.xhtml?faces-redirect=true";
        
    }
    
    public void atualizaLista (){
        try {
            lista = EventoDAO.getLista();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public String salvar(){
        TipoEvento tipo = new TipoEvento();
        tipo.setIdTipo(idTipo);
        evento.setTipoEvento(tipo);
        try {
            if(salvar){
                EventoDAO.inserir(evento);
            }else{
               EventoDAO.alterar(evento);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        atualizaLista();
        return "manutencaoEvento.xhtml?faces-redirect=true";
    }
    
    public void excluir(){
        try {
            EventoDAO.excluir(evento);
            atualizaLista();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<Evento> getLista() {
        return lista;
    }

    public void setLista(List<Evento> lista) {
        this.lista = lista;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public boolean isSalvar() {
        return salvar;
    }

    public void setSalvar(boolean salvar) {
        this.salvar = salvar;
    }

    public int getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }
    
    
}