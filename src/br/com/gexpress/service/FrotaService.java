package br.com.gexpress.service;
import br.com.gexpress.exception.ValorInvalidoException;
import br.com.gexpress.model.StatusVeiculo;
import br.com.gexpress.model.VeiculoCarga;
import br.com.gexpress.model.VeiculoPasseio;
import br.com.gexpress.model.VeiculoStructure;
import br.com.gexpress.repository.VeiculoRepository;

import java.util.List;

public class FrotaService {

    private final VeiculoRepository veiculoRepository = new VeiculoRepository();

    public FrotaService() {
    }

    public void cadastrarVeiculo(VeiculoStructure veiculo) throws ValorInvalidoException {
        if (veiculo == null) {
            throw new ValorInvalidoException("O veículo não pode ser nulo!");
        }

        String modeloChave = veiculo.getModelo().toUpperCase().trim();


        if (veiculoRepository.existeModeloNoBanco(modeloChave)) {
            throw new ValorInvalidoException("Já existe um veículo cadastrado com este modelo na frota da G-Express!");
        }

        if (veiculo instanceof VeiculoCarga caminhao) {
            veiculoRepository.salvarCaminhao(caminhao);
        } else if (veiculo instanceof VeiculoPasseio carro) {
            veiculoRepository.salvarCarroPasseio(carro);
        }
    }

    public List<VeiculoStructure> obterFrota() {
        return veiculoRepository.buscarTodos();
    }

    public VeiculoStructure buscarVeiculo(String modelo) {
        if (modelo == null) return null;

        return veiculoRepository.buscarTodos().stream()
                .filter(v -> v.getModelo().equalsIgnoreCase(modelo.trim()))
                .findFirst()
                .orElse(null);
    }

    public void alterarStatusVeiculo(String modelo, StatusVeiculo novoStatus) {
        veiculoRepository.atualizarStatus(modelo, novoStatus);
    }

}