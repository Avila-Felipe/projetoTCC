package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Animal;
import modelo.Fazenda;
import modelo.Raca;
import util.Conexao;
import util.SessionContext;

public class AnimalDAO {

    public static void inserirLote(Animal animal) throws SQLException {
        Connection con = Conexao.getConexao();
        String sql = "insert into animal\n"
                + "(identificacao, dataNasc, racaFK, sexo, cor, fazendaFK, dataEntrada, dataSaida, status, descricao)\n"
                + "values\n"
                + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement stat = con.prepareStatement(sql);
        int auxId = animal.getIdentificacaoInicial();
        for (int i = 0; i < animal.getQuantidade(); i++) {
            
            stat.setString(1, auxId+"");
            stat.setDate(2, new Date(animal.getDataNasc().getTime()));
            stat.setInt(3, animal.getRaca().getIdRaca());
            stat.setString(4, animal.getSexo());
            stat.setString(5, animal.getCor());
            stat.setInt(6, animal.getFazenda().getIdFazenda());
            stat.setDate(7, new Date(animal.getDataEntrada().getTime()));
            stat.setDate(8, new Date(animal.getDataSaida().getTime()));
            stat.setString(9, animal.getStatus());
            stat.setString(10, animal.getDescricao());
            stat.execute();
            auxId++;
        }

        stat.close();
        con.close();
    }

    public static void inserir(Animal animal) throws SQLException {
        Connection con = Conexao.getConexao();
        String sql = "insert into animal\n"
                + "(identificacao, dataNasc, racaFK, sexo, cor, fazendaFK, dataEntrada, dataSaida, status, descricao)\n"
                + "values\n"
                + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement stat = con.prepareStatement(sql);
        stat.setString(1, animal.getIdentificacao());
        stat.setDate(2, new Date(animal.getDataNasc().getTime()));
        stat.setInt(3, animal.getRaca().getIdRaca());
        stat.setString(4, animal.getSexo());
        stat.setString(5, animal.getCor());
        stat.setInt(6, animal.getFazenda().getIdFazenda());
        stat.setDate(7, new Date(animal.getDataEntrada().getTime()));
        stat.setDate(8, new Date(animal.getDataSaida().getTime()));
        stat.setString(9, animal.getStatus());
        stat.setString(10, animal.getDescricao());
        stat.execute();
        stat.close();
        con.close();
    }

    public static void alterar(Animal animal) throws SQLException {
        Connection con = Conexao.getConexao();
        String sql = "update animal set identificacao = ?, racaFK = ?, dataNasc = ?,"
                + "sexo = ?, cor = ?, fazendaFK = ?, dataEntrada = ?, dataSaida = ?,"
                + "status = ?, descricao = ? WHERE idAnimal = ?";

        PreparedStatement stat = con.prepareStatement(sql);
        stat.setString(1, animal.getIdentificacao());
        stat.setInt(2, animal.getRaca().getIdRaca());
        stat.setDate(3, new Date(animal.getDataNasc().getTime()));
        stat.setString(4, animal.getSexo());
        stat.setString(5, animal.getCor());
        stat.setInt(6, animal.getFazenda().getIdFazenda());
        stat.setDate(7, new Date(animal.getDataEntrada().getTime()));
        stat.setDate(8, new Date(animal.getDataSaida().getTime()));
        stat.setString(9, animal.getStatus());
        stat.setString(10, animal.getDescricao());
        stat.setInt(11, animal.getIdAnimal());
        stat.execute();
        stat.close();
        con.close();
    }

    public static void excluir(Animal animal) throws SQLException {
        Connection con = Conexao.getConexao();
        String sql = "delete from animal WHERE idAnimal = ?";

        PreparedStatement stat = con.prepareStatement(sql);
        stat.setInt(1, animal.getIdAnimal());
        stat.executeUpdate();
        stat.close();
        con.close();
    }

    public static List<Animal> getLista(Fazenda fazenda) throws SQLException {
        List<Animal> lista = new ArrayList<Animal>();
        Connection con = Conexao.getConexao();
        String sql = "SELECT\n"
                + "	*\n"
                + "FROM\n"
                + "	animal a,\n"
                + "	raca r,\n"
                + "	fazenda f\n"
                + "WHERE\n"
                + "	a.racaFK = r.idRaca AND\n"
                + "     a.fazendaFK = f.idFazenda AND\n"
                + "     f.idFazenda = ?\n"
                + "ORDER BY\n"
                + "	a.Identificacao";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, fazenda.getIdFazenda());
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {

            Raca r = new Raca();
            r.setIdRaca(rs.getInt("idRaca"));
            r.setRaca(rs.getString("raca"));

            Fazenda f = new Fazenda();
            f.setIdFazenda(rs.getInt("idFazenda"));
            f.setNomeFazenda(rs.getString("nomeFazenda"));
            f.setReferencia(rs.getString("referencia"));
            f.setArea(rs.getDouble("area"));            
            
            Animal a = new Animal();
            a.setIdAnimal(rs.getInt("idAnimal"));
            a.setIdentificacao(rs.getString("identificacao"));
            a.setDataNasc(rs.getDate("dataNasc"));
            a.setSexo(rs.getString("sexo"));
            a.setCor(rs.getString("cor"));
            a.setDataEntrada(rs.getDate("dataEntrada"));
            a.setDataSaida(rs.getDate("dataSaida"));
            a.setStatus(rs.getString("status"));
            a.setDescricao(rs.getString("descricao"));
            a.setRaca(r);
            a.setFazenda(f);
            lista.add(a);
        }
        return lista;
    }
}
