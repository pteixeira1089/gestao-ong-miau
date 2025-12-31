package br.org.miauaumigos.backend.service.storage.local;

import br.org.miauaumigos.backend.service.storage.FotoStorageService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

@Service
@Profile("local")
public class LocalFotoStorageService implements FotoStorageService {

    private final Path diretorioFotos = Paths.get("uploads");

    // Lista Branca (Allowlist): Apenas estes tipos entram
    private static final Map<String, String> EXTENSOES_ACEITAS = Map.of(
            "image/jpeg", ".jpg",
            "image/jpg", ".jpg",
            "image/png", ".png"
    );

    public LocalFotoStorageService() {
        try {
            Files.createDirectories(diretorioFotos);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao criar diretório", e);
        }
    }

    @Override
    public String armazenar(MultipartFile arquivo) {
        // 1. Validação de Segurança
        String tipoArquivo = arquivo.getContentType();

        // Se o tipo não estiver na nossa lista, abortamos imediatamente.
        if (tipoArquivo == null || !EXTENSOES_ACEITAS.containsKey(tipoArquivo)) {
            throw new RuntimeException("Tipo de arquivo não permitido. Envie apenas JPG ou PNG.");
        }

        // 2. Definir a extensão correta baseada no tipo real, e não no nome
        // Isso resolve o problema do arquivo "gato.fofo" ou "gato" (sem extensão)
        String extensaoCorreta = EXTENSOES_ACEITAS.get(tipoArquivo);

        // 3. Gerar novo nome
        String novoNome = UUID.randomUUID().toString() + extensaoCorreta;

        try {
            Path caminhoArquivo = diretorioFotos.resolve(novoNome);
            Files.copy(arquivo.getInputStream(), caminhoArquivo);
            return novoNome;
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar foto", e);
        }
    }

    @Override
    public String recuperarUrl(String nomeArquivo) {
        return "http://localhost:8080/fotos/" + nomeArquivo;
    }
}