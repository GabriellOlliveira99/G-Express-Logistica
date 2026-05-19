import java.io.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ArquivoUtil {

    private static final String NOME_ARQUIVO = "frota.txt";

    public static void salvarFrota(Map<String, VeiculoStructure> frota) {
        try (FileWriter fw = new FileWriter(NOME_ARQUIVO);
             PrintWriter pw = new PrintWriter(fw)) {

            for (VeiculoStructure v : frota.values()) {
                String tipo = (v instanceof VeiculoCarga) ? "CARGA" : "PASSEIO";

                String dadoEspecifico = (v instanceof VeiculoCarga)
                        ? String.valueOf(((VeiculoCarga) v).getCapacidadeToneladas())
                        : String.valueOf(((VeiculoPasseio) v).getCapacidadePassageiros());

                pw.println(tipo + ";" + v.getModelo() + ";" + v.getValorDiaria() + ";" + v.getTipoCombustivel() + ";" + dadoEspecifico);
            }
            System.out.println("💾 Frota salva com sucesso em 'frota.txt'!");

        } catch (IOException e) {
            System.err.println("❌ Erro ao salvar arquivo: " + e.getMessage());
        }
    }

    public static Map<String, VeiculoStructure> carregarFrota() {
        Map<String, VeiculoStructure> mapa = new HashMap<>();
        File arquivo = new File(NOME_ARQUIVO);

        if (!arquivo.exists()) {
            return mapa;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] p = linha.split(";");

                String tipo = p[0];
                String modelo = p[1];
                BigDecimal valor = new BigDecimal(p[2]);
                TipoCombustivel combustivel = TipoCombustivel.valueOf(p[3]);
                String dadoEsp = p[4];

                if (tipo.equals("CARGA")) {
                    mapa.put(modelo.toUpperCase(), new VeiculoCarga(modelo, valor, Double.parseDouble(dadoEsp), combustivel));
                } else {
                    mapa.put(modelo.toUpperCase(), new VeiculoPasseio(modelo, valor, Integer.parseInt(dadoEsp), combustivel));
                }
            }
        } catch (Exception e) {
            System.err.println("❌ Erro ao carregar dados: " + e.getMessage());
        }
        return mapa;
    }
}

