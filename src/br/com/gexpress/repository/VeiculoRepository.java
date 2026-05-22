package br.com.gexpress.repository;

import br.com.gexpress.model.VeiculoCarga;
import br.com.gexpress.model.VeiculoPasseio;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VeiculoRepository {

    private static final String URL = "jdbc:postgresql://localhost:5432/gexpress_db";
    private static final String USUARIO = "postgres";
    private static final String SENHA = "12345678";

    public void salvarCaminhao(VeiculoCarga caminhao) {
        String sql = "INSERT INTO veiculos (tipo_veiculo, modelo, valor_diaria, ano_fabricacao, tipo_combustivel, status, capacidade_carga) " +
                "VALUES ('CARGA', ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USUARIO, SENHA);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, caminhao.getModelo());
            stmt.setBigDecimal(2, caminhao.getValorDiaria());
            stmt.setInt(3, caminhao.getAnoFabricacao());
            stmt.setString(4, caminhao.getTipoCombustivel().name());
            stmt.setString(5, caminhao.getStatus().name());
            stmt.setDouble(6, caminhao.getCapacidadeToneladas());

            stmt.executeUpdate();
            System.out.println("💾 [BANCO DE DADOS] Dados gravados na tabela 'veiculos' com sucesso!");

        } catch (SQLException e) {
            System.out.println("❌ Erro ao salvar o caminhão no banco de dados!");
            e.printStackTrace();
        }
    }

    public void salvarCarroPasseio(VeiculoPasseio carro) {
        String sql = "INSERT INTO veiculos (tipo_veiculo, modelo, valor_diaria, ano_fabricacao, tipo_combustivel, status, qtd_passageiros) " +
                "VALUES ('PASSEIO', ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USUARIO, SENHA);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, carro.getModelo());
            stmt.setBigDecimal(2, carro.getValorDiaria());
            stmt.setInt(3, carro.getAnoFabricacao());
            stmt.setString(4, carro.getTipoCombustivel().name());
            stmt.setString(5, carro.getStatus().name());
            stmt.setInt(6, carro.getCapacidadePassageiros());

            stmt.executeUpdate();
            System.out.println("💾 [BANCO DE DADOS] Carro de passeio gravado no banco com sucesso!");

        } catch (SQLException e) {
            System.out.println("❌ Erro ao salvar o carro de passeio no banco de dados!");
            e.printStackTrace();
        }
    }

    public boolean existeModeloNoBanco(String modelo) {
        String sql = "SELECT COUNT(*) FROM veiculos WHERE UPPER(TRIM(modelo)) = UPPER(TRIM(?))";

        try (Connection conn = DriverManager.getConnection(URL, USUARIO, SENHA);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, modelo);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.out.println("⚠️ Erro ao checar duplicidade no banco: " + e.getMessage());
        }
        return false;
    }
}
