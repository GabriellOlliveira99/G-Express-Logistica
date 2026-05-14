import java.math.BigDecimal;
import java.math.RoundingMode;

public class VeiculoCarga extends VeiculoStructure implements Rastreavel {
    private double capacidadeToneladas;

    public VeiculoCarga(String modelo, BigDecimal valorDiaria, double capacidadeToneladas, TipoCombustivel tipo) throws ValorInvalidoException {
        super(modelo, valorDiaria, tipo);
        setCapacidadeToneladas(capacidadeToneladas);
    }

    @Override
    public BigDecimal calcularAluguel() {
        if (this.capacidadeToneladas > 10) {
            return getValorDiaria().multiply(new BigDecimal("1.20")).setScale(2, RoundingMode.HALF_UP);
        }
        return getValorDiaria();
    }

    @Override
    public boolean conectarSatelite(String codigo) {
        return codigo.equals("GEX-123");
    }

    @Override
    public void exibirDados() {
        System.out.println("Modelo: " + getModelo() + " | Diária Base: R$ " + getValorDiaria() + " | Capacidade: " + this.capacidadeToneladas + "t" + " | Combustível: " + getTipoCombustivel());
    }

    public void setCapacidadeToneladas(double capacidadeToneladas) throws ValorInvalidoException {
        if (capacidadeToneladas <= 0) {
            throw new ValorInvalidoException("A capacidade de carga deve ser positiva!");
        }
        this.capacidadeToneladas = capacidadeToneladas;
    }
}
