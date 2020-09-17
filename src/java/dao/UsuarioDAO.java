package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Usuario;
import util.Conexao;

public class UsuarioDAO {
    
    
    public static void inserir(Usuario usuario) throws SQLException{
        Connection con = Conexao.getConexao();
            String sql = "insert into usuario\n"
            +"      (nome, sobrenome, cpf, endereco, email, telefone, senha, status, tipoUsuario)\n"
            +"      values\n"
            +"      (?, ?, ?, ?, ?, ?, md5(?), 'ATIVO', 'comum')";
        
        PreparedStatement stat = con.prepareStatement(sql);
        stat.setString(1, usuario.getNome());
        stat.setString(2, usuario.getSobrenome());
        stat.setString(3, usuario.getCpf());
        stat.setString(4, usuario.getEndereco());
        stat.setString(5, usuario.getEmail());
        stat.setString(6, usuario.getTelefone());
        stat.setString(7, usuario.getSenha());
        stat.execute();
        stat.close();
        con.close();
    }
    
     public static void alterar(Usuario usuario) throws SQLException{
        Connection con = Conexao.getConexao();
        String sql = "update usuario set nome = ?, sobrenome = ?, cpf= ?,"
                + "endereco = ? , email = ?, telefone = ?, senha = md5(?) WHERE idUsuario = ?";
        
        PreparedStatement stat = con.prepareStatement(sql);
        stat.setString(1, usuario.getNome());
        stat.setString(2, usuario.getSobrenome());
        stat.setString(3, usuario.getCpf());
        stat.setString(4,usuario.getEndereco());
        stat.setString(5, usuario.getEmail());
        stat.setString(6, usuario.getTelefone());
        stat.setString(7, usuario.getSenha());
        stat.setInt(8, usuario.getIdUsuario());
        stat.execute();
        stat.close();
        con.close();
     }
     
     public static void excluir(Usuario usuario) throws SQLException{
        Connection con = Conexao.getConexao();
        String sql = "delete from usuario WHERE idUsuario = ?";
        
        PreparedStatement stat = con.prepareStatement(sql);
        stat.setInt(1, usuario.getIdUsuario());
        stat.executeUpdate();
        stat.close();
        con.close();
    }
     
     public static List<Usuario> getLista() throws SQLException {
        List<Usuario> lista = new ArrayList<Usuario>();
        Connection con = Conexao.getConexao();
        String sql = "SELECT\n"
                + "	*\n"
                + "FROM\n"
                + "	usuario u\n"
                + "ORDER BY\n"
                + "	u.nome"; 
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            
            
            Usuario u = new Usuario();
            u.setIdUsuario(rs.getInt("idUsuario"));
            u.setNome(rs.getString("nome"));
            u.setSobrenome(rs.getString("sobrenome"));
            u.setCpf(rs.getString("cpf"));
            u.setEndereco(rs.getString("endereco"));
            u.setEmail(rs.getString("email"));
            u.setTelefone(rs.getString("telefone"));
            lista.add(u);
        }
        return lista;
    }
     
      public static Usuario getLogin(Usuario eP) throws SQLException {
        Usuario usuario = null;
        Connection con = Conexao.getConexao();
        String sql = "SELECT\n"
                + "	*\n"
                + "FROM\n"
                + "	usuario u\n"
                + "WHERE\n"
                + "	u.email = ? AND\n"
                + "	u.senha = md5(?) AND\n"
                + "	u.status = 'ATIVO'\n"
                + "ORDER BY\n"
                + "	u.nome";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, eP.getEmail());
        stmt.setString(2, eP.getSenha());
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {

            usuario = new Usuario();
            usuario.setIdUsuario(rs.getInt("idUsuario"));
            usuario.setNome(rs.getString("nome"));
            usuario.setSobrenome(rs.getString("sobrenome"));
            usuario.setEmail(rs.getString("email"));
            usuario.setTelefone(rs.getString("telefone"));
            usuario.setCpf(rs.getString("cpf"));
            usuario.setEndereco(rs.getString("endereco"));
            usuario.setTipoUsuario(rs.getString("tipoUsuario"));
            
        }
        return usuario;
    }
}