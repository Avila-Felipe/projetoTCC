package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Raca;
import util.Conexao;

public class RacaDAO {
    
     public static void inserir(Raca raca) throws SQLException{
        Connection con = Conexao.getConexao();
        String sql = "insert into raca (raca) values (?)";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, raca.getRaca());
        stmt.execute();
        stmt.close();
        con.close();
    }
    public static void alterar(Raca raca) throws SQLException{
        Connection con = Conexao.getConexao();
        String sql = "update raca set Raca = ? where idRaca = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, raca.getRaca());
        stmt.setInt(2, raca.getIdRaca());
        stmt.executeUpdate();
        stmt.close();
        con.close();
    }
    public static void excluir(Raca raca) throws SQLException{
        Connection con = Conexao.getConexao();
        String sql = "delete from raca where idRaca = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, raca.getIdRaca());
        stmt.executeUpdate();
        stmt.close();
        con.close();
    }
    
      public static List<Raca> getLista() throws SQLException{
            List<Raca> lista = new ArrayList<Raca>();
            Connection con = Conexao.getConexao();
            String sql = "select * from raca ORDER BY raca asc ";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Raca r = new Raca();
                r.setIdRaca(rs.getInt("idRaca"));
                r.setRaca(rs.getString("raca"));
                lista.add(r);           
            }
            return lista;
    }

}
