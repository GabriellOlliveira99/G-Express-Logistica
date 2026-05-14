import java.math.BigDecimal;

public abstract class VeiculoStructure {
    private String modelo;
    private BigDecimal valorDiaria;
    private TipoCombustivel tipoCombustivel;

    public VeiculoStructure(String modelo, BigDecimal valorDiaria, TipoCombustivel tipoCombustivel) throws ValorInvalidoException {
        this.modelo = modelo;
        this.tipoCombustivel = tipoCombustivel;
        setValorDiaria(valorDiaria);
    }

    public abstract BigDecimal calcularAluguel();

    public void exibirDados() {
        System.out.println("Modelo: " + modelo + " | Diária: R$ " + valorDiaria + " | Combustível: R$ " + tipoCombustivel);
    }

    public String getModelo() {
        return modelo;
    }

    public BigDecimal getValorDiaria() {
        return valorDiaria;
    }

    public TipoCombustivel getTipoCombustivel() {
        return tipoCombustivel;
    }

    public void setValorDiaria(BigDecimal ValorDiaria) throws ValorInvalidoException {
        if (ValorDiaria == null || ValorDiaria.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValorInvalidoException("O valor da diária deve ser positivo e não nulo!");
        }
        this.valorDiaria = ValorDiaria;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
}
