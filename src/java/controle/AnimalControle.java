package controle;

import dao.AnimalDAO;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import modelo.Animal;
import modelo.Fazenda;
import modelo.Raca;

@ManagedBean
@SessionScoped

public class AnimalControle {

    private List<Animal> lista;
    private Animal animal = new Animal();
    private boolean salvar = false;
    private int idRaca;
    private int idFazenda;
    private Fazenda fazenda;

    public Fazenda getFazenda() {
        return fazenda;
    }

    public void setFazenda(Fazenda fazenda) {
        this.fazenda = fazenda;
    }

    public String preparaIncluir() {
        animal = new Animal();
        salvar = true;
        idRaca = 0;
        idFazenda = 0;
        return "cadastroAnimal.xhtml?faces-redirect=true";

    }

    public String preparaAlterar() {
        salvar = false;
        idRaca = animal.getRaca().getIdRaca();
        idFazenda = animal.getFazenda().getIdFazenda();
        return "cadastroAnimal.xhtml?faces-redirect=true";

    }

    public String atualizaLista(Fazenda faz) {
        try {
            fazenda = faz;
            lista = AnimalDAO.getLista(fazenda);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return "manutencaoAnimais.xhtml?faces-redirect=true";
    }

    public String salvar() {
        Raca raca = new Raca();
        raca.setIdRaca(idRaca);
        animal.setRaca(raca);

        Fazenda fazenda = new Fazenda();
        fazenda.setIdFazenda(idFazenda);
        animal.setFazenda(fazenda);
        try {
            if (salvar) {
                AnimalDAO.inserir(animal);
            } else {
                AnimalDAO.alterar(animal);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        atualizaLista(fazenda);
        return "manutencaoAnimais.xhtml?faces-redirect=true";
    }
    public String salvarLote() {
        Raca raca = new Raca();
        raca.setIdRaca(idRaca);
        animal.setRaca(raca);

        Fazenda fazenda = new Fazenda();
        fazenda.setIdFazenda(idFazenda);
        animal.setFazenda(fazenda);
        try {

                AnimalDAO.inserirLote(animal);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        atualizaLista(fazenda);
        return "manutencaoAnimais.xhtml?faces-redirect=true";
    }

    public void excluir() {
        try {
            AnimalDAO.excluir(animal);
            atualizaLista(fazenda);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<Animal> getLista() {
        return lista;
    }

    public void setLista(List<Animal> lista) {
        this.lista = lista;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public boolean isSalvar() {
        return salvar;
    }

    public void setSalvar(boolean salvar) {
        this.salvar = salvar;
    }

    public int getIdRaca() {
        return idRaca;
    }

    public void setIdRaca(int idRaca) {
        this.idRaca = idRaca;
    }

    public int getIdFazenda() {
        return idFazenda;
    }

    public void setIdFazenda(int idFazenda) {
        this.idFazenda = idFazenda;
    }

}
