import br.com.gexpress.exception.ValorInvalidoException;
import br.com.gexpress.model.*;
import br.com.gexpress.service.FrotaService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class Main {

    private static final FrotaService frotaService = new FrotaService();
    private static final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {
        boolean rodando = true;

        try {
            while (rodando) {
                exibirMenu();
                try {
                    int opcao = lerInteiro("Escolha uma opção: ");

                    switch (opcao) {
                        case 1 -> cadastrarVeiculoCarga();
                        case 2 -> cadastrarVeiculoPasseio();
                        case 3 -> listarRelatorioFrota();
                        case 4 -> alugarVeiculo();
                        case 5 -> devolverVeiculo();
                        case 0 -> {
                            System.out.println("\n💾 Salvando dados da frota...");
                            frotaService.salvarDados();
                            rodando = false;
                            System.out.println("Saindo...");
                        }
                        default -> System.out.println("⚠️ Opção inválida! Escolha um número de 0 a 5.");
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

    private static void exibirMenu() {
        System.out.println("\n=================================");
        System.out.println("       G-EXPRESS LOGÍSTICA       ");
        System.out.println("=================================");
        System.out.println("1 - Cadastrar Veículo de Carga");
        System.out.println("2 - Cadastrar Veículo de Passeio");
        System.out.println("3 - Listar Relatório de Frota");
        System.out.println("4 - Alugar Veículo");
        System.out.println("5 - Devolver Veículo");
        System.out.println("0 - Sair do Sistema");
    }

    private static void cadastrarVeiculoCarga() throws ValorInvalidoException {
        System.out.println("\n--- Cadastro de Veículo de Carga ---");
        String modCarga = lerTexto("Digite o modelo: ");
        BigDecimal diariaCarga = lerBigDecimal("Digite o valor da diária: ");
        int anoCarga = lerInteiro("Digite o ano de fabricação: ");
        double capCarga = lerDouble("Digite a capacidade de carga (toneladas): ");
        TipoCombustivel combustCarga = escolherCombustivel();

        VeiculoCarga caminhao = new VeiculoCarga(modCarga, diariaCarga, anoCarga, capCarga, combustCarga);
        frotaService.cadastrarVeiculo(caminhao);
        System.out.println("✅ Caminhão cadastrado com sucesso!");
    }

    private static void cadastrarVeiculoPasseio() throws ValorInvalidoException {
        System.out.println("\n--- Cadastro de Veículo de Passeio ---");
        String modPasseio = lerTexto("Digite o modelo: ");
        BigDecimal diariaPasseio = lerBigDecimal("Digite o valor da diária: ");
        int anoPasseio = lerInteiro("Digite o ano de fabricação: ");
        int qtdPassageiros = lerInteiro("Digite a quantidade de passageiros: ");
        TipoCombustivel combustPass = escolherCombustivel();

        VeiculoPasseio carro = new VeiculoPasseio(modPasseio, diariaPasseio, anoPasseio, qtdPassageiros, combustPass);
        frotaService.cadastrarVeiculo(carro);
        System.out.println("✅ Carro de passeio cadastrado com sucesso!");
    }

    private static void listarRelatorioFrota() {
        System.out.println("\n=== RELATÓRIO DE FROTA G-EXPRESS ===");
        Map<String, VeiculoStructure> frota = frotaService.obterFrota();

        if (frota.isEmpty()) {
            System.out.println("Nenhum veículo na frota.");
            return;
        }

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

    private static void alugarVeiculo() {
        System.out.println("\n--- Locação de Veículo ---");
        Map<String, VeiculoStructure> frota = frotaService.obterFrota();

        if (frota.isEmpty()) {
            System.out.println("Nenhum veículo disponível no sistema para alugar.");
            return;
        }

        String modeloAluguel = lerTexto("Digite o modelo do veículo que deseja alugar: ");
        VeiculoStructure veiculoAlugar = frotaService.buscarVeiculo(modeloAluguel);

        if (veiculoAlugar == null) {
            System.out.println("❌ Veículo não encontrado na frota!");
            return;
        }

        if (veiculoAlugar.getStatus() == StatusVeiculo.ALOCADO) {
            System.out.println("⚠️ Este veículo já está alugado no momento!");
            return;
        }

        try {
            String dataRetText = lerTexto("Digite a data de RETIRADA (dd/mm/aaaa): ");
            LocalDate dataRetirada = LocalDate.parse(dataRetText, formatador);

            String dataDevText = lerTexto("Digite a data de DEVOLUÇÃO (dd/mm/aaaa): ");
            LocalDate dataDevolucao = LocalDate.parse(dataDevText, formatador);

            BigDecimal valorTotal = veiculoAlugar.calcularAluguel(dataRetirada, dataDevolucao);
            veiculoAlugar.setStatus(StatusVeiculo.ALOCADO);

            System.out.println("\n✅ CONTRATO DE LOCAÇÃO EMITIDO!");
            System.out.println("Veículo: " + veiculoAlugar.getModelo());
            System.out.println("Valor total do período: R$ " + valorTotal);
        } catch (DateTimeParseException e) {
            System.out.println("\n❌ ERRO DE FORMATAÇÃO: Formato de data inválido! Use o padrão dd/mm/aaaa.");
        }
    }

    private static void devolverVeiculo() {
        System.out.println("\n--- Devolução de Veículo ---");
        if (frotaService.obterFrota().isEmpty()) {
            System.out.println("Nenhum veículo cadastrado no sistema.");
            return;
        }

        String modeloDevolucao = lerTexto("Digite o modelo do veículo que está sendo devolvido: ");
        VeiculoStructure veiculoDevolver = frotaService.buscarVeiculo(modeloDevolucao);

        if (veiculoDevolver == null) {
            System.out.println("❌ Veículo não encontrado na frota!");
            return;
        }

        if (veiculoDevolver.getStatus() == StatusVeiculo.DISPONIVEL) {
            System.out.println("⚠️ Este veículo já consta como DISPONÍVEL no pátio.");
            return;
        }

        veiculoDevolver.setStatus(StatusVeiculo.DISPONIVEL);
        System.out.println("✅ Devolução concluída! O veículo " + veiculoDevolver.getModelo() + " agora está DISPONÍVEL.");
    }

    // --- MÉTODOS AUXILIARES DE ENTRADA (CLEAN CODE) ---
    private static String lerTexto(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine();
    }

    private static int lerInteiro(String mensagem) {
        System.out.print(mensagem);
        int valor = scanner.nextInt();
        scanner.nextLine();
        return valor;
    }

    private static double lerDouble(String mensagem) {
        System.out.print(mensagem);
        double valor = scanner.nextDouble();
        scanner.nextLine();
        return valor;
    }

    private static BigDecimal lerBigDecimal(String mensagem) {
        System.out.print(mensagem);
        BigDecimal valor = scanner.nextBigDecimal();
        scanner.nextLine();
        return valor;
    }

    private static TipoCombustivel escolherCombustivel() {
        System.out.println("\nSelecione o Combustível:");
        System.out.println("1 - GASOLINA | 2 - DIESEL | 3 - FLEX | 4 - ELETRICO");
        int op = lerInteiro("Escolha: ");

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


