import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Map<String, VeiculoStructure> frota = ArquivoUtil.carregarFrota();

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
                    scanner.nextLine(); // Limpa o buffer do teclado

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

                            frota.put(modCarga.toUpperCase(), new VeiculoCarga(modCarga, diariaCarga, capCarga, combustCarga));

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

                            frota.put(modPasseio.toUpperCase(), new VeiculoPasseio(modPasseio, diariaPasseio, qtdPassageiros, combustPass));

                            System.out.println("✅ Carro de passeio cadastrado com sucesso!");
                            break;

                        case 3:
                            System.out.println("\n=== RELATÓRIO E SIMULAÇÃO DE FROTA G-EXPRESS ===\n");
                            if (frota.isEmpty()) {
                                System.out.println("Nenhum veículo na frota.");
                            } else {

                                DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                                try {
                                    System.out.println("--- Período da Simulação de Aluguel ---");
                                    System.out.print("Digite a data de RETIRADA (dd/mm/aaaa): ");
                                    String dataRetText = scanner.nextLine();
                                    LocalDate dataRetirada = LocalDate.parse(dataRetText, formatador);

                                    System.out.print("Digite a data de DEVOLUÇÃO (dd/mm/aaaa): ");
                                    String dataDevText = scanner.nextLine();
                                    LocalDate dataDevolucao = LocalDate.parse(dataDevText, formatador);

                                    System.out.println("\nTotal de veículos cadastrados: " + frota.size());
                                    System.out.println("------------------------------------");

                                    for (VeiculoStructure v : frota.values()) {
                                        v.exibirDados();

                                        System.out.println("Aluguel calculado para o período: R$ " + v.calcularAluguel(dataRetirada, dataDevolucao));

                                        if (v instanceof Rastreavel) {
                                            Rastreavel rastreado = (Rastreavel) v;
                                            boolean conectado = rastreado.conectarSatelite("GEX-123");
                                            System.out.println(">> Status do Satélite: " + (conectado ? "CONECTADO" : "FALHA NA CONEXÃO"));
                                        }
                                        System.out.println("------------------------------------");
                                    }
                                } catch (DateTimeParseException e) {
                                    System.out.println("\n❌ ERRO DE FORMATAÇÃO: Você digitou a data em um formato inválido! Use o padrão dd/mm/aaaa.");
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



