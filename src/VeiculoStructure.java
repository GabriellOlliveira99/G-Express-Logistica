import java.math.BigDecimal;

public abstract class VeiculoStructure {
    private String modelo;
    private BigDecimal valorDiaria;

    public VeiculoStructure(String modelo, BigDecimal valorDiaria) throws ValorInvalidoException {
        this.modelo = modelo;
        setValorDiaria(valorDiaria);
    }

    public abstract BigDecimal calcularAluguel();

    public void exibirDados() {
        System.out.println("Modelo: " + modelo + " | Diária: R$ " + valorDiaria);
    }

    public String getModelo() { return modelo; }
    public BigDecimal getValorDiaria() { return valorDiaria; }

    public void setValorDiaria (BigDecimal ValorDiaria) throws ValorInvalidoException{
        if (ValorDiaria == null || ValorDiaria.compareTo(BigDecimal.ZERO) <= 0){
            throw new ValorInvalidoException("O valor da diária deve ser positivo e não nulo!");
        }
        this.valorDiaria = ValorDiaria;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
}
