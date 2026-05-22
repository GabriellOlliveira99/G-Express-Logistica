package br.com.gexpress.model;
import br.com.gexpress.exception.ValorInvalidoException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class VeiculoCarga extends VeiculoStructure implements Rastreavel {
    private double capacidadeToneladas;

    public VeiculoCarga(String modelo, BigDecimal valorDiaria, int anoFabricacao, double capacidadeToneladas, TipoCombustivel tipo) throws ValorInvalidoException {
        super(modelo, valorDiaria, anoFabricacao, tipo);
        setCapacidadeToneladas(capacidadeToneladas);
    }

    @Override
    public BigDecimal calcularAluguel(LocalDate dataRetirada, LocalDate dataDevolucao) {
        long dias = ChronoUnit.DAYS.between(dataRetirada, dataDevolucao);

        if (dias <= 0) {
            dias = 1;
        }

        BigDecimal totalDias = new BigDecimal(dias);
        BigDecimal valorTotalBase = getValorDiaria().multiply(totalDias);

        if (this.capacidadeToneladas > 10) {
            return valorTotalBase.multiply(new BigDecimal("1.20")).setScale(2, RoundingMode.HALF_UP);
        }

        return valorTotalBase;
    }

    @Override
    public boolean conectarSatelite(String codigo) {
        return codigo.equals("GEX-123");
    }

    @Override
    public void exibirDados() {
        super.exibirDados();
        System.out.println("Especificações -> Capacidade: " + this.capacidadeToneladas + "t");
    }

    public void setCapacidadeToneladas(double capacidadeToneladas) throws ValorInvalidoException {
        if (capacidadeToneladas <= 0) {
            throw new ValorInvalidoException("A capacidade de carga deve ser positiva!");
        }
        this.capacidadeToneladas = capacidadeToneladas;
    }

    public double getCapacidadeToneladas() {
        return capacidadeToneladas;
    }


}
