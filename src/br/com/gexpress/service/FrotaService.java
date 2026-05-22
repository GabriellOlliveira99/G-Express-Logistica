package br.com.gexpress.service;

import br.com.gexpress.exception.ValorInvalidoException;
import br.com.gexpress.model.VeiculoCarga;
import br.com.gexpress.model.VeiculoPasseio;
import br.com.gexpress.model.VeiculoStructure;
import br.com.gexpress.repository.ArquivoUtil;
import br.com.gexpress.repository.VeiculoRepository;

import java.util.HashMap;
import java.util.Map;

public class FrotaService {

    private final Map<String, VeiculoStructure> frota;
    private final VeiculoRepository veiculoRepository = new VeiculoRepository();

    public FrotaService() {
        this.frota = ArquivoUtil.carregarFrota();
    }

    public void cadastrarVeiculo(VeiculoStructure veiculo) throws ValorInvalidoException {
        if (veiculo == null) {
            throw new ValorInvalidoException("O veículo não pode ser nulo!");
        }

        String modeloChave = veiculo.getModelo().toUpperCase().trim();

        if (frota.containsKey(modeloChave) || veiculoRepository.existeModeloNoBanco(modeloChave)) {
            throw new ValorInvalidoException("Já existe um veículo cadastrado com este modelo na frota da G-Express!");
        }

        frota.put(modeloChave, veiculo);

        if (veiculo instanceof VeiculoCarga caminhao) {
            veiculoRepository.salvarCaminhao(caminhao);
        } else if (veiculo instanceof VeiculoPasseio carro) {
            veiculoRepository.salvarCarroPasseio(carro);
        }
    }

    public Map<String, VeiculoStructure> obterFrota() {
        return new HashMap<>(this.frota);
    }

    public VeiculoStructure buscarVeiculo(String modelo) {
        if (modelo == null) return null;
        return frota.get(modelo.toUpperCase().trim());
    }

    public void salvarDados() {
        ArquivoUtil.salvarFrota(frota);
    }
}
