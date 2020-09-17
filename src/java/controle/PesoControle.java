package controle;

import dao.PesoDAO;
import java.sql.SQLException;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import modelo.Animal;
import modelo.Peso;

@ManagedBean
@SessionScoped

public class PesoControle {
    
    private List<Peso> lista;
    private Peso peso = new Peso();
    private int idAnimal;
    private boolean salvar = false;

    public String preparaIncluir(){
        peso = new Peso();
        salvar = true;
        idAnimal = 0;
        return "cadastroPeso.xhtml?faces-redirect=true";
        
    }
    public String preparaAlterar(){
        salvar = false;
        idAnimal = peso.getIdAnimal().getIdAnimal();
        return "cadastroPeso.xhtml?faces-redirect=true";
        
    }
    
    public void atualizaLista (){
        try {
            lista = PesoDAO.getLista();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public String salvar(){
        Animal animal = new Animal();
        animal.setIdAnimal(idAnimal);
        peso.setIdAnimal(animal);

        try {
            if(salvar){
                PesoDAO.inserir(peso);
            }else{
                PesoDAO.alterar(peso);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        atualizaLista();
        return "manutencaoPeso.xhtml?faces-redirect=true";
    }
    
    public void excluir(){
        try {
            PesoDAO.excluir(peso);
            atualizaLista();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<Peso> getLista() {
        return lista;
    }

    public void setLista(List<Peso> lista) {
        this.lista = lista;
    }

    public Peso getPeso() {
        return peso;
    }

    public void setPeso(Peso peso) {
        this.peso = peso;
    }

    public boolean isSalvar() {
        return salvar;
    }

    public void setSalvar(boolean salvar) {
        this.salvar = salvar;
    }

    public int getIdAnimal() {
        return idAnimal;
    }

    public void setIdAnimal(int idAnimal) {
        this.idAnimal = idAnimal;
    }
    
}