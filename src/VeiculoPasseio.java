import java.math.BigDecimal;

public class VeiculoPasseio extends VeiculoStructure {
    private int passageiros;

    public VeiculoPasseio(String modelo, BigDecimal valorDiaria, int passageiros) {
        super(modelo, valorDiaria);
        this.passageiros = passageiros;
    }

    @Override
    public BigDecimal calcularAluguel() {
        return getValorDiaria();
    }

    @Override
    public void exibirDados() {
        System.out.println("Modelo: " + getModelo() + " | Diária: R$ " + getValorDiaria() + " | Passageiros: " + this.passageiros);
    }
}