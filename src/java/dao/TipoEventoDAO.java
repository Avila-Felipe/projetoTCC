package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.TipoEvento;
import util.Conexao;

public class TipoEventoDAO {
    
    
    public static void inserir(TipoEvento tipoEvento) throws SQLException{
        Connection con = Conexao.getConexao();
        String sql = "insert into tipoevento \n"
                +"  (descTipo)\n"
                +"  values\n"
                +"(?)";
        
        PreparedStatement stat = con.prepareStatement(sql);
        stat.setString(1, tipoEvento.getDescTipo());
        stat.execute();
        stat.close();
        con.close();
    }
    
     public static void alterar(TipoEvento tipoEvento) throws SQLException{
        Connection con = Conexao.getConexao();
        String sql = "update tipoEvento set descTipo = ?"
                + "WHERE idTipoEvento = ?";
        
        PreparedStatement stat = con.prepareStatement(sql);
        stat.setString(1, tipoEvento.getDescTipo());
        stat.setInt(2, tipoEvento.getIdTipo());
        stat.execute();
        stat.close();
        con.close();
     }

     public static void excluir(TipoEvento tipoEvento) throws SQLException{
        Connection con = Conexao.getConexao();
        String sql = "delete from tipoevento WHERE idTipoEvento = ?";
        
        PreparedStatement stat = con.prepareStatement(sql);
        stat.setInt(1, tipoEvento.getIdTipo());
        stat.executeUpdate();
        stat.close();
        con.close();
    }
     
     public static List<TipoEvento> getLista() throws SQLException{
            List<TipoEvento> lista = new ArrayList<TipoEvento>();
            Connection con = Conexao.getConexao();
            String sql = "select * from tipoEvento ORDER BY idTipoEvento ";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                TipoEvento t = new TipoEvento();
                t.setIdTipo(rs.getInt("idTipoEvento"));
                t.setDescTipo(rs.getString("descTipo"));
                lista.add(t);           
            }
            return lista;
    }
}