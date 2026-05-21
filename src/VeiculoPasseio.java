import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class VeiculoPasseio extends VeiculoStructure {
    private int passageiros;

    public VeiculoPasseio(String modelo, BigDecimal valorDiaria, int anoFabricacao, int passageiros, TipoCombustivel tipo) throws ValorInvalidoException {
        super(modelo, valorDiaria, anoFabricacao, tipo);
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
        super.exibirDados();
        System.out.println("Especificações -> Passageiros: " + this.passageiros);
    }

    public int getCapacidadePassageiros() {
        return passageiros;
    }
}