import java.math.BigDecimal;

public abstract class VeiculoStructure {
    private String modelo;
    private BigDecimal valorDiaria;

    public VeiculoStructure(String modelo, BigDecimal valorDiaria) {
        this.modelo = modelo;
        this.valorDiaria = valorDiaria;
    }

    public abstract BigDecimal calcularAluguel();

    public void exibirDados() {
        System.out.println("Modelo: " + modelo + " | Diária: R$ " + valorDiaria);
    }

    public String getModelo() { return modelo; }
    public BigDecimal getValorDiaria() { return valorDiaria; }
}
