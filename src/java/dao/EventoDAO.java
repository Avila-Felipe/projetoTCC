package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Evento;
import modelo.TipoEvento;
import util.Conexao;

public class EventoDAO {
    
    
    public static void inserir(Evento evento) throws SQLException{
        Connection con = Conexao.getConexao();
        String sql = "insert into evento \n"
                +"  (descEvento, unidadeMedida, idTipoEvento, data)\n"
                +"  values\n"
                +"  (?, ?, ?, ?)";
                
        PreparedStatement stat = con.prepareStatement(sql);
        stat.setString(1, evento.getDescEvento());
        stat.setDouble(2, evento.getUnidadeMedida());
        stat.setInt(3, evento.getTipoEvento().getIdTipo());
        stat.setDate(4, new Date(evento.getData().getTime()));
        stat.execute();
        stat.close();
        con.close();
    }
    
     public static void alterar(Evento evento) throws SQLException{
        Connection con = Conexao.getConexao();
        String sql = "update evento set descEvento = ?, unidadeMedida = ?,"
                + "idTipoEvento = ?, data = ? WHERE idEvento = ?";
        
        PreparedStatement stat = con.prepareStatement(sql);
        stat.setString(1, evento.getDescEvento());
        stat.setDouble(2, evento.getUnidadeMedida());
        stat.setInt(3, evento.getTipoEvento().getIdTipo());
        stat.setDate(4, new Date(evento.getData().getTime()));
        stat.setInt(5, evento.getIdEvento());
        stat.execute();
        stat.close();
        con.close();
     }
     
     public static void excluir(Evento evento) throws SQLException{
        Connection con = Conexao.getConexao();
        String sql = "delete from evento WHERE idEvento = ?";
        
        PreparedStatement stat = con.prepareStatement(sql);
        stat.setInt(1, evento.getIdEvento());
        stat.executeUpdate();
        stat.close();
        con.close();
    }
     
     public static List<Evento> getLista() throws SQLException {
        List<Evento> lista = new ArrayList<Evento>();
        Connection con = Conexao.getConexao();
        String sql = "SELECT\n"
                + "	*\n"
                + "FROM\n"
                + "	evento e,\n"
                + "	tipoevento t\n"
                + "WHERE\n"
                + "	e.idTipoEvento = t.idTipoEvento\n"
                + "ORDER BY\n"
                + "	e.descEvento"; 
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            
            TipoEvento t = new TipoEvento();
            t.setIdTipo(rs.getInt("idTipoEvento"));
            t.setDescTipo(rs.getString("descTipo"));
            
            Evento e = new Evento();
            e.setIdEvento(rs.getInt("idEvento"));
            e.setDescEvento(rs.getString("descEvento"));
            e.setUnidadeMedida(rs.getDouble("unidadeMedida"));
            e.setData(rs.getDate("data"));
            e.setTipoEvento(t);
            lista.add(e);
        }
        return lista;
    }
}