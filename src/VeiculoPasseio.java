import java.math.BigDecimal;

public class VeiculoPasseio extends VeiculoStructure {
    private int passageiros;

    public VeiculoPasseio(String modelo, BigDecimal valorDiaria, int passageiros, TipoCombustivel tipo) throws ValorInvalidoException {
        super(modelo, valorDiaria, tipo);
        this.passageiros = passageiros;
    }

    @Override
    public BigDecimal calcularAluguel() {
        if (getTipoCombustivel() == TipoCombustivel.ELETRICO) {
            return getValorDiaria().multiply(new BigDecimal("0.90"));
        }
        return getValorDiaria();
    }

    @Override
    public void exibirDados() {
        System.out.println("Modelo: " + getModelo() + " | Diária: R$ " + getValorDiaria() + " | Passageiros: " + this.passageiros + " | Combustível: " + getTipoCombustivel());
    }
}