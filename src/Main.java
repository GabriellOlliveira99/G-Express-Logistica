import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

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

        Scanner scanner = new Scanner(System.in);
        boolean ok = false;
        while (!ok) {
            try {
                System.out.println("--- Cadastro de Veículo G-Express ---");
                System.out.print("Digite a capacidade de carga (toneladas): ");
                double capacidade = scanner.nextDouble();

                if (capacidade <= 0) {
                    throw new ValorInvalidoException("A capacidade de carga deve ser positiva!");
                }

                System.out.print("Digite o valor da diária: ");
                double diaria = scanner.nextDouble();

                if (diaria <= 0) {
                    throw new ValorInvalidoException("O valor da diária não pode ser zero ou negativo!");
                }

                ok = true;
                System.out.println("✅ Veículo validado com sucesso!");

            } catch (InputMismatchException e) {
                System.out.println("\n❌ ERRO DE ENTRADA: Você digitou letras em vez de números.");
            } catch (ValorInvalidoException e) {
                System.out.println("\n❌ ERRO DE NEGÓCIO: " + e.getMessage());
            }
        }
        scanner.close();
    }
}
