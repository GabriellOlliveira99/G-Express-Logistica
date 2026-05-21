package br.com.gexpress.service;

import br.com.gexpress.exception.ValorInvalidoException;
import br.com.gexpress.model.VeiculoStructure;
import br.com.gexpress.repository.ArquivoUtil;

import java.util.HashMap;
import java.util.Map;

public class FrotaService {

    private final Map<String, VeiculoStructure> frota;
    private final String CAMINHO_ARQUIVO = "frota.txt";

    public FrotaService() {
        this.frota = ArquivoUtil.carregarFrota();
    }

    public void cadastrarVeiculo(VeiculoStructure veiculo) throws ValorInvalidoException {
        if (veiculo == null) {
            throw new ValorInvalidoException("O veículo não pode ser nulo!");
        }

        if (frota.containsKey(veiculo.getModelo().toUpperCase())) {
            throw new ValorInvalidoException("Já existe um veículo cadastrado com este modelo!");
        }

        frota.put(veiculo.getModelo().toUpperCase(), veiculo);
    }

    public Map<String, VeiculoStructure> obterFrota() {
        return new HashMap<>(this.frota);
    }

    public VeiculoStructure buscarVeiculo(String modelo) {
        if (modelo == null) return null;
        return frota.get(modelo.toUpperCase());
    }

    public void salvarDados() {
        ArquivoUtil.salvarFrota(frota);
    }
}
