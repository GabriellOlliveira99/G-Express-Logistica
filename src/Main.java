import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<VeiculoStructure> frota = new ArrayList<>();

        try {
            frota.add(new VeiculoCarga("VW Delivery", new BigDecimal("400.00"), 8.0, TipoCombustivel.DIESEL));
            frota.add(new VeiculoCarga("Scania R500", new BigDecimal("800.00"), 15.0, TipoCombustivel.DIESEL));
            frota.add(new VeiculoPasseio("Fiat Uno", new BigDecimal("100.00"), 5, TipoCombustivel.GASOLINA));
            frota.add(new VeiculoPasseio("Honda Civic", new BigDecimal("250.00"), 5, TipoCombustivel.ELETRICO));
        } catch (ValorInvalidoException e) {
            System.out.println("❌ Erro crítico ao carregar dados de teste: " + e.getMessage());
        }

        Scanner scanner = new Scanner(System.in);
        boolean rodando = true;

        try {
            while (rodando) {
                System.out.println("\n=================================");
                System.out.println("       G-EXPRESS LOGÍSTICA       ");
                System.out.println("=================================");
                System.out.println("1 - Cadastrar Veículo de Carga");
                System.out.println("2 - Cadastrar Veículo de Passeio");
                System.out.println("3 - Gerar Relatório de Frota");
                System.out.println("0 - Sair do Sistema");
                System.out.print("Escolha uma opção: ");

                try {
                    int opcao = scanner.nextInt();
                    scanner.nextLine();

                    switch (opcao) {
                        case 1:
                            System.out.println("\n--- Cadastro de Veículo de Carga ---");
                            System.out.print("Digite o modelo: ");
                            String modCarga = scanner.nextLine();

                            System.out.print("Digite o valor da diária: ");
                            BigDecimal diariaCarga = scanner.nextBigDecimal();

                            System.out.print("Digite a capacidade de carga (toneladas): ");
                            double capCarga = scanner.nextDouble();
                            scanner.nextLine();

                            TipoCombustivel combustCarga = escolherCombustivel(scanner);
                            frota.add(new VeiculoCarga(modCarga, diariaCarga, capCarga, combustCarga));

                            System.out.println("✅ Caminhão cadastrado com sucesso!");
                            break;

                        case 2:
                            System.out.println("\n--- Cadastro de Veículo de Passeio ---");
                            System.out.print("Digite o modelo: ");
                            String modPasseio = scanner.nextLine();

                            System.out.print("Digite o valor da diária: ");
                            BigDecimal diariaPasseio = scanner.nextBigDecimal();

                            System.out.print("Digite a quantidade de passageiros: ");
                            int qtdPassageiros = scanner.nextInt();
                            scanner.nextLine();

                            TipoCombustivel combustPass = escolherCombustivel(scanner);
                            frota.add(new VeiculoPasseio(modPasseio, diariaPasseio, qtdPassageiros, combustPass));

                            System.out.println("✅ Carro de passeio cadastrado com sucesso!");
                            break;

                        case 3:
                            System.out.println("\n=== RELATÓRIO DE FROTA G-EXPRESS ===\n");
                            if (frota.isEmpty()) {
                                System.out.println("Nenhum veículo na frota.");
                            } else {
                                System.out.println("Total de veículos cadastrados: " + VeiculoStructure.getTotalVeiculos());
                                System.out.println("------------------------------------");

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
                            break;

                        case 0:
                            ArquivoUtil.salvarFrota(frota);
                            rodando = false;
                            System.out.println("Saindo...");
                            break;

                        default:
                            System.out.println("⚠️ Opção inválida! Escolha um número de 0 a 3.");
                    }

                } catch (InputMismatchException e) {
                    System.out.println("\n❌ ERRO DE ENTRADA: Você digitou letras em vez de números.");
                    scanner.nextLine();
                } catch (ValorInvalidoException e) {
                    System.out.println("\n❌ ERRO DE NEGÓCIO: " + e.getMessage());
                }
            }
        } finally {
            System.out.println("\n[SISTEMA] Fechando recursos e encerrando com segurança...");
            scanner.close();
        }
    }

    public static TipoCombustivel escolherCombustivel(Scanner scanner) {
        System.out.println("\nSelecione o Combustível:");
        System.out.println("1 - GASOLINA | 2 - DIESEL | 3 - FLEX | 4 - ELETRICO");
        System.out.print("Escolha: ");
        int op = scanner.nextInt();
        scanner.nextLine();

        return switch (op) {
            case 1 -> TipoCombustivel.GASOLINA;
            case 2 -> TipoCombustivel.DIESEL;
            case 3 -> TipoCombustivel.FLEX;
            case 4 -> TipoCombustivel.ELETRICO;
            default -> {
                System.out.println("⚠️ Opção inválida! Definindo como FLEX por segurança.");
                yield TipoCombustivel.FLEX;
            }
        };
    }
}



