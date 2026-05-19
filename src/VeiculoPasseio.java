import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class VeiculoPasseio extends VeiculoStructure {
    private int passageiros;

    public VeiculoPasseio(String modelo, BigDecimal valorDiaria, int passageiros, TipoCombustivel tipo) throws ValorInvalidoException {
        super(modelo, valorDiaria, tipo);
        this.passageiros = passageiros;
    }

    @Override
    public BigDecimal calcularAluguel(LocalDate dataRetirada, LocalDate dataDevolucao) {
        long dias = ChronoUnit.DAYS.between(dataRetirada, dataDevolucao);

        if (dias <= 0) {
            dias = 1;
        }

        BigDecimal totalDias = new BigDecimal(dias);
        BigDecimal valorTotalBase = getValorDiaria().multiply(totalDias);

        if (getTipoCombustivel() == TipoCombustivel.ELETRICO) {
            return valorTotalBase.multiply(new BigDecimal("0.90"));
        }

        return valorTotalBase;
    }

    @Override
    public void exibirDados() {
        System.out.println("Modelo: " + getModelo() + " | Diária: R$ " + getValorDiaria() + " | Passageiros: " + this.passageiros + " | Combustível: " + getTipoCombustivel());
    }

    public int getCapacidadePassageiros() {
        return passageiros;
    }
}