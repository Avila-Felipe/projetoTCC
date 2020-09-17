package controle;

import dao.RacaDAO;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import modelo.Raca;

@ManagedBean
@SessionScoped

public class RacaControle {

    private List<Raca> lista;
    private Raca raca = new Raca();
    private boolean salvar = false;

    public String preparaIncluir() {
        raca = new Raca();
        salvar = true;
        return "cadastroRacas.xhtml?faces-redirect=true";

    }

    public String preparaAlterar() {
        salvar = false;
        return "cadastroRacas.xhtml?faces-redirect=true";

    }

@PostConstruct    
    public void atualizaLista() {
        try {
            lista = RacaDAO.getLista();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public String salvar() {
        try {
            if (salvar) {
                RacaDAO.inserir(raca);
            } else {
                RacaDAO.alterar(raca);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        atualizaLista();
        return "manutencaoRacas.xhtml?faces-redirect=true";

    }

    public void excluir() {
        try {
            RacaDAO.excluir(raca);
            atualizaLista();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<Raca> getLista() {
        return lista;
    }

    public void setLista(List<Raca> lista) {
        this.lista = lista;
    }

    public Raca getRaca() {
        return raca;
    }

    public void setRaca(Raca raca) {
        this.raca = raca;
    }

    public boolean isSalvar() {
        return salvar;
    }

    public void setSalvar(boolean salvar) {
        this.salvar = salvar;
    }

}
