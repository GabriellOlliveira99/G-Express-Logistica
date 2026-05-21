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
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        boolean rodando = true;

        try {
            while (rodando) {
                System.out.println("\n=================================");
                System.out.println("       G-EXPRESS LOGÍSTICA       ");
                System.out.println("=================================");
                System.out.println("1 - Cadastrar Veículo de Carga");
                System.out.println("2 - Cadastrar Veículo de Passeio");
                System.out.println("3 - Listar Relatório de Frota");
                System.out.println("4 - Alugar Veículo");
                System.out.println("5 - Devolver Veículo");
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

                            System.out.print("Digite o ano de fabricação: ");
                            int anoCarga = scanner.nextInt();

                            System.out.print("Digite a capacidade de carga (toneladas): ");
                            double capCarga = scanner.nextDouble();
                            scanner.nextLine();

                            TipoCombustivel combustCarga = escolherCombustivel(scanner);

                            frota.put(modCarga.toUpperCase(), new VeiculoCarga(modCarga, diariaCarga, anoCarga, capCarga, combustCarga));
                            System.out.println("✅ Caminhão cadastrado com sucesso!");
                            break;

                        case 2:
                            System.out.println("\n--- Cadastro de Veículo de Passeio ---");
                            System.out.print("Digite o modelo: ");
                            String modPasseio = scanner.nextLine();

                            System.out.print("Digite o valor da diária: ");
                            BigDecimal diariaPasseio = scanner.nextBigDecimal();

                            System.out.print("Digite o ano de fabricação: ");
                            int anoPasseio = scanner.nextInt();

                            System.out.print("Digite a quantidade de passageiros: ");
                            int qtdPassageiros = scanner.nextInt();
                            scanner.nextLine();

                            TipoCombustivel combustPass = escolherCombustivel(scanner);

                            frota.put(modPasseio.toUpperCase(), new VeiculoPasseio(modPasseio, diariaPasseio, anoPasseio, qtdPassageiros, combustPass));
                            System.out.println("✅ Carro de passeio cadastrado com sucesso!");
                            break;

                        case 3:
                            System.out.println("\n=== RELATÓRIO DE FROTA G-EXPRESS ===");
                            if (frota.isEmpty()) {
                                System.out.println("Nenhum veículo na frota.");
                            } else {
                                System.out.println("Total de veículos cadastrados: " + frota.size());
                                System.out.println("------------------------------------");
                                for (VeiculoStructure v : frota.values()) {
                                    v.exibirDados();
                                    if (v instanceof Rastreavel rastreado) {
                                        boolean conectado = rastreado.conectarSatelite("GEX-123");
                                        System.out.println(">> Status do Satélite: " + (conectado ? "CONECTADO" : "FALHA NA CONEXÃO"));
                                    }
                                    System.out.println("------------------------------------");
                                }
                            }
                            break;

                        case 4:
                            System.out.println("\n--- Locação de Veículo ---");
                            if (frota.isEmpty()) {
                                System.out.println("Nenhum veículo disponível no sistema para alugar.");
                                break;
                            }
                            System.out.print("Digite o modelo do veículo que deseja alugar: ");
                            String modeloAluguel = scanner.nextLine().toUpperCase();

                            if (!frota.containsKey(modeloAluguel)) {
                                System.out.println("❌ Veículo não encontrado na frota!");
                                break;
                            }

                            VeiculoStructure veiculoAlugar = frota.get(modeloAluguel);

                            if (veiculoAlugar.getStatus() == StatusVeiculo.ALOCADO) {
                                System.out.println("⚠️ Este veículo já está alugado no momento!");
                                break;
                            }

                            try {
                                System.out.print("Digite a data de RETIRADA (dd/mm/aaaa): ");
                                String dataRetText = scanner.nextLine();
                                LocalDate dataRetirada = LocalDate.parse(dataRetText, formatador);

                                System.out.print("Digite a data de DEVOLUÇÃO (dd/mm/aaaa): ");
                                String dataDevText = scanner.nextLine();
                                LocalDate dataDevolucao = LocalDate.parse(dataDevText, formatador);

                                BigDecimal valorTotal = veiculoAlugar.calcularAluguel(dataRetirada, dataDevolucao);
                                veiculoAlugar.setStatus(StatusVeiculo.ALOCADO);

                                System.out.println("\n✅ CONTRATO DE LOCAÇÃO EMITIDO!");
                                System.out.println("Veículo: " + veiculoAlugar.getModelo());
                                System.out.println("Valor total do período: R$ " + valorTotal);
                            } catch (DateTimeParseException e) {
                                System.out.println("\n❌ ERRO DE FORMATAÇÃO: Formato de data inválido! Use o padrão dd/mm/aaaa.");
                            }
                            break;

                        case 5:
                            System.out.println("\n--- Devolução de Veículo ---");
                            if (frota.isEmpty()) {
                                System.out.println("Nenhum veículo cadastrado no sistema.");
                                break;
                            }
                            System.out.print("Digite o modelo do veículo que está sendo devolvido: ");
                            String modeloDevolucao = scanner.nextLine().toUpperCase();

                            if (!frota.containsKey(modeloDevolucao)) {
                                System.out.println("❌ Veículo não encontrado na frota!");
                                break;
                            }

                            VeiculoStructure veiculoDevolver = frota.get(modeloDevolucao);

                            if (veiculoDevolver.getStatus() == StatusVeiculo.DISPONIVEL) {
                                System.out.println("⚠️ Este veículo já consta como DISPONÍVEL no pátio.");
                                break;
                            }

                            veiculoDevolver.setStatus(StatusVeiculo.DISPONIVEL);
                            System.out.println("✅ Devolução concluída! O veículo " + veiculoDevolver.getModelo() + " agora está DISPONÍVEL.");
                            break;

                        case 0:
                            ArquivoUtil.salvarFrota(frota);
                            rodando = false;
                            System.out.println("Saindo...");
                            break;

                        default:
                            System.out.println("⚠️ Opção inválida! Escolha um número de 0 a 5.");
                    }

                } catch (InputMismatchException e) {
                    System.out.println("\n❌ ERRO DE ENTRADA: Você digitou dados em um formato inválido.");
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


