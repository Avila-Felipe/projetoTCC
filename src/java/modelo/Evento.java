package modelo;

import java.util.Date;

public class Evento {
    
    private int idEvento;
    private String descEvento;
    private double unidadeMedida;
    private TipoEvento tipoEvento;
    private Date data;

    public int getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }

    public String getDescEvento() {
        return descEvento;
    }

    public void setDescEvento(String descEvento) {
        this.descEvento = descEvento;
    }

    public double getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(double unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public TipoEvento getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(TipoEvento tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
    
    
}
