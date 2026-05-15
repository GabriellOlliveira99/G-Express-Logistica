import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ArquivoUtil {
    public static void salvarFrota(List<VeiculoStructure> frota) {
        try (FileWriter fw = new FileWriter("frota.txt");
             PrintWriter pw = new PrintWriter(fw)) {

            for (VeiculoStructure v : frota) {
                pw.println(v.getModelo() + ";" + v.getValorDiaria() + ";" + v.getTipoCombustivel());
            }
            System.out.println("💾 Frota salva com sucesso em 'frota.txt'!");

        } catch (IOException e) {
            System.err.println("❌ Erro ao salvar arquivo: " + e.getMessage());
        }
    }
}

