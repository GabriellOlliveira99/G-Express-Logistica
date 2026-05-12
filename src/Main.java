import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<VeiculoStructure> frota = new ArrayList<>();

        frota.add(new VeiculoCarga("VW Delivery", new BigDecimal("400.00"), 8.0));
        frota.add(new VeiculoCarga("Scania R500", new BigDecimal("800.00"), 15.0));
        frota.add(new VeiculoPasseio("Fiat Uno", new BigDecimal("100.00"), 5));
        frota.add(new VeiculoPasseio("Honda Civic", new BigDecimal("250.00"), 5));

        System.out.println("=== RELATÓRIO DE FROTA G-EXPRESS ===\n");

        for (VeiculoStructure v : frota) {
            v.exibirDados();
            System.out.println("Aluguel calculado: R$ " + v.calcularAluguel());

            if (v instanceof Rastreavel) {
                Rastreavel rastreado = (Rastreavel) v;
                boolean conectado = rastreado.conectarSatelite("GEX-123");
                System.out.println(">> Status do Satélite: " + (conectado ? "CONECTADO" : "FALHA NA CONEXÃO"));
            }
            System.out.println("------------------------------------");
        }
    }
}
