package br.org.miauaumigos.backend.service.storage.cloudinary;

import br.org.miauaumigos.backend.service.storage.FotoStorageService;
import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Profile("prod") // Ativa apenas quando o perfil 'prod' estiver ativo
@RequiredArgsConstructor
public class CloudinaryFotoStorageService implements FotoStorageService {

    private final Cloudinary cloudinary;
    
    // Lista de tipos MIME permitidos
    private static final List<String> TIPOS_PERMITIDOS = Arrays.asList(
            "image/jpeg", 
            "image/jpg", 
            "image/png", 
            "image/webp"
    );

    @Override
    public String armazenar(MultipartFile arquivo) {
        validarArquivo(arquivo);

        try {
            // Gera um nome único para evitar sobrescrita
            String nomeArquivo = UUID.randomUUID().toString();

            // Configura upload
            Map params = ObjectUtils.asMap(
                    "public_id", nomeArquivo,
                    "folder", "miau-aumigos",
                    "resource_type", "image"
            );

            Map uploadResult = cloudinary.uploader().upload(arquivo.getBytes(), params);
            
            // Retorna o public_id (ex: miau-aumigos/uuid-xyz)
            return (String) uploadResult.get("public_id");
        } catch (IOException e) {
            throw new RuntimeException("Erro ao enviar foto para o Cloudinary", e);
        }
    }

    @Override
    public String recuperarUrl(String nomeArquivo) {
        // Gera a URL com transformações dinâmicas
        return cloudinary.url()
                .transformation(new Transformation()
                        .width(500)
                        .height(500)
                        .crop("thumb") // Corte quadrado
                        .gravity("face") // Foca no rosto (humano ou animal, se detectado)
                        .fetchFormat("auto") // WebP/AVIF automático
                        .quality("auto") // Otimização de tamanho
                )
                .generate(nomeArquivo);
    }

    private void validarArquivo(MultipartFile arquivo) {
        String tipo = arquivo.getContentType();
        if (tipo == null || !TIPOS_PERMITIDOS.contains(tipo)) {
            throw new RuntimeException("Tipo de arquivo não permitido. Envie apenas imagens (JPG, PNG, WebP).");
        }
    }
}
