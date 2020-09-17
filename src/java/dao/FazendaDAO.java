package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Fazenda;
import modelo.Usuario;
import util.Conexao;
import util.SessionContext;

public class FazendaDAO {
    
     public static void inserir(Fazenda fazenda) throws SQLException{
        Connection con = Conexao.getConexao();
        String sql = "insert into fazenda \n"
                +"  (nomeFazenda, referencia, area, idUsuario)\n"
                +"  values (?, ?, ?, ?)";
        
        PreparedStatement stat = con.prepareStatement(sql);
        stat.setString(1, fazenda.getNomeFazenda());
        stat.setString(2, fazenda.getReferencia());
        stat.setDouble(3, fazenda.getArea());
        stat.setInt(4, SessionContext.getInstance().getUsuarioLogado().getIdUsuario());
        stat.execute();
        stat.close();
        con.close();
    }
     
     public static void alterar(Fazenda fazenda) throws SQLException{
        Connection con = Conexao.getConexao();
        String sql = "update fazenda set nomeFazenda = ?, referencia = ?, idUsuario = ?,"
                + "area = ? WHERE idFazenda = ?";
        
        PreparedStatement stat = con.prepareStatement(sql);
        stat.setString(1, fazenda.getNomeFazenda());
        stat.setString(2, fazenda.getReferencia());
        stat.setInt(3, SessionContext.getInstance().getUsuarioLogado().getIdUsuario());
        stat.setDouble(4, fazenda.getArea());
        stat.setInt(5, fazenda.getIdFazenda());
        stat.execute();
        stat.close();
        con.close();
    }
     
     public static void excluir(Fazenda fazenda) throws SQLException{
        Connection con = Conexao.getConexao();
        String sql = "delete from fazenda WHERE idFazenda = ?";
        
        PreparedStatement stat = con.prepareStatement(sql);
        stat.setInt(1, fazenda.getIdFazenda());
        stat.executeUpdate();
        stat.close();
        con.close();
    }
     
     public static List<Fazenda> getLista() throws SQLException {
        List<Fazenda> lista = new ArrayList<Fazenda>();
        Connection con = Conexao.getConexao();
        String sql = "SELECT\n"
                + "	*\n"
                + "FROM\n"
                + "	fazenda f,\n"
                + "	usuario u\n"
                + "WHERE\n"
                + "	f.idUsuario = u.idUsuario AND"
                + "     f.idUsuario = ?\n"
                + "ORDER BY\n"
                + "	f.idFazenda"; 
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, SessionContext.getInstance().getUsuarioLogado().getIdUsuario());
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            
            Usuario u = new Usuario();
            u.setIdUsuario(rs.getInt("idUsuario"));
            u.setCpf(rs.getString("cpf"));
            u.setEmail(rs.getString("email"));
            u.setNome(rs.getString("nome"));
            u.setSobrenome(rs.getString("sobrenome"));
            u.setTelefone(rs.getString("telefone"));
            u.setEndereco(rs.getString("endereco"));
            
            Fazenda f = new Fazenda();
            f.setIdFazenda(rs.getInt("idFazenda"));
            f.setNomeFazenda(rs.getString("nomeFazenda"));
            f.setReferencia(rs.getString("referencia"));
            f.setArea(rs.getDouble("area"));
            f.setUsuario(u);
            lista.add(f);
        }
        return lista;
    }
}
