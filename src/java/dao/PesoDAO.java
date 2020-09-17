package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Animal;
import modelo.Peso;
import util.Conexao;

public class PesoDAO {
    
     public static void inserir(Peso peso) throws SQLException{
        Connection con = Conexao.getConexao();
        String sql = "insert into peso \n"
                +"  (peso, idAnimal, data)\n"
                +"  values\n"
                +"(?, ?)";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setDouble(1, peso.getPeso());
        stmt.setInt(2, peso.getIdAnimal().getIdAnimal());
        stmt.setDate(3, new Date(peso.getData().getTime()));
        stmt.execute();
        stmt.close();
        con.close();
    }
    public static void alterar(Peso peso) throws SQLException{
        Connection con = Conexao.getConexao();
        String sql = "update peso set peso = ?, idAnimal = ?, data = ? where idPeso = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setDouble(1, peso.getPeso());
        stmt.setInt(2, peso.getIdAnimal().getIdAnimal());
        stmt.setDate(3, new Date(peso.getData().getTime()));
        stmt.setInt(4, peso.getIdPeso());
        stmt.executeUpdate();
        stmt.close();
        con.close();
    }
    public static void excluir(Peso peso) throws SQLException{
        Connection con = Conexao.getConexao();
        String sql = "delete from peso where idPeso = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, peso.getIdPeso());
        stmt.executeUpdate();
        stmt.close();
        con.close();
    }
    
      public static List<Peso> getLista() throws SQLException{
            List<Peso> lista = new ArrayList<Peso>();
            Connection con = Conexao.getConexao();
            String sql = "select *\n"
                +"from peso p, animal a\n"
                +"where p.idAnimal = a.idAnimal\n   "
                +"order by p.data";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Animal a = new Animal();
                a.setIdAnimal(rs.getInt("idAnimal"));
                a.setIdentificacao(rs.getString("identificacao"));
                a.setDataNasc(rs.getDate("dataNasc"));
                a.setSexo(rs.getString("sexo"));
                a.setCor(rs.getString("cor"));
            
                Peso p = new Peso();
                p.setIdPeso(rs.getInt("idPeso"));
                p.setPeso(rs.getDouble("peso"));
                p.setData(rs.getDate("data"));
                p.setIdAnimal(a);
                lista.add(p);
            }
            return lista;
    }

}
