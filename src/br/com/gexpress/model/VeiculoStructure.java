package br.com.gexpress.model;
import br.com.gexpress.exception.ValorInvalidoException;
import java.math.BigDecimal;
import java.time.LocalDate;

public abstract class VeiculoStructure {
    private String modelo;
    private BigDecimal valorDiaria;
    private TipoCombustivel tipoCombustivel;
    private int anoFabricacao;
    private StatusVeiculo status;
    private static int totalVeiculos = 0;

    public VeiculoStructure(String modelo, BigDecimal valorDiaria, int anoFabricacao, TipoCombustivel tipoCombustivel) throws ValorInvalidoException {
        this.modelo = modelo;
        this.tipoCombustivel = tipoCombustivel;
        setValorDiaria(valorDiaria);
        setAnoFabricacao(anoFabricacao);
        this.status = StatusVeiculo.DISPONIVEL;

        totalVeiculos++;
    }

    public abstract BigDecimal calcularAluguel(LocalDate dataRetirada, LocalDate dataDevolucao);

    public void exibirDados() {
        System.out.println("Modelo: " + modelo + " | Diária: R$ " + valorDiaria + " | Ano: " + anoFabricacao + " | Status: " + status + " | Combustível: " + tipoCombustivel);
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public BigDecimal getValorDiaria() {
        return valorDiaria;
    }

    public void setValorDiaria(BigDecimal valorDiaria) throws ValorInvalidoException {
        if (valorDiaria == null || valorDiaria.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValorInvalidoException("O valor da diária deve ser positivo e não nulo!");
        }
        this.valorDiaria = valorDiaria;
    }

    public TipoCombustivel getTipoCombustivel() {
        return tipoCombustivel;
    }

    public int getAnoFabricacao() {
        return anoFabricacao;
    }

    private void setAnoFabricacao(int anoFabricacao) throws ValorInvalidoException {
        int anoAtual = LocalDate.now().getYear();
        if (anoFabricacao < 2000 || anoFabricacao > anoAtual) {
            throw new ValorInvalidoException("Ano de fabricação inválido! Permitido apenas veículos de 2000 até " + anoAtual);
        }
        this.anoFabricacao = anoFabricacao;
    }

    public StatusVeiculo getStatus() {
        return status;
    }

    public void setStatus(StatusVeiculo status) {
        this.status = status;
    }

    public static int getTotalVeiculos() {
        return totalVeiculos;
    }
}
