package br.org.miauaumigos.backend.service;

import br.org.miauaumigos.backend.dto.AdotanteRequestDTO;
import br.org.miauaumigos.backend.dto.AdotanteResponseDTO;
import br.org.miauaumigos.backend.dto.EnderecoDTO;
import br.org.miauaumigos.backend.model.entity.Adotante;
import br.org.miauaumigos.backend.model.entity.Endereco;
import br.org.miauaumigos.backend.repository.AdotanteRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdotanteService {

    private final AdotanteRepository adotanteRepository;

    @Transactional
    public AdotanteResponseDTO cadastrar(AdotanteRequestDTO dto) {
        if (adotanteRepository.existsByDocumentoIdentificacao(dto.getDocumentoIdentificacao())) {
            throw new IllegalArgumentException("Já existe um adotante cadastrado com este documento.");
        }

        Adotante adotante = toEntity(dto);
        Adotante adotanteSalvo = adotanteRepository.save(adotante);
        return toResponseDTO(adotanteSalvo);
    }

    @Transactional(readOnly = true)
    public List<AdotanteResponseDTO> listarTodos() {
        return adotanteRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AdotanteResponseDTO buscarPorId(Long id) {
        Adotante adotante = adotanteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Adotante não encontrado com o ID: " + id));
        return toResponseDTO(adotante);
    }

    // --- Mappers ---

    private Adotante toEntity(AdotanteRequestDTO dto) {
        Endereco endereco = Endereco.builder()
                .logradouro(dto.getEndereco().getLogradouro())
                .numero(dto.getEndereco().getNumero())
                .complemento(dto.getEndereco().getComplemento())
                .bairro(dto.getEndereco().getBairro())
                .cidade(dto.getEndereco().getCidade())
                .estado(dto.getEndereco().getEstado())
                .cep(dto.getEndereco().getCep())
                .build();

        return Adotante.builder()
                .nome(dto.getNome())
                .telefone(dto.getTelefone())
                .email(dto.getEmail())
                .documentoIdentificacao(dto.getDocumentoIdentificacao())
                .endereco(endereco) // Associação feita aqui
                .observacoes(dto.getObservacoes())
                .fotoUrl(dto.getFotoUrl())
                .build();
    }

    private AdotanteResponseDTO toResponseDTO(Adotante entity) {
        EnderecoDTO enderecoDTO = null;
        if (entity.getEndereco() != null) {
            enderecoDTO = EnderecoDTO.builder()
                    .logradouro(entity.getEndereco().getLogradouro())
                    .numero(entity.getEndereco().getNumero())
                    .complemento(entity.getEndereco().getComplemento())
                    .bairro(entity.getEndereco().getBairro())
                    .cidade(entity.getEndereco().getCidade())
                    .estado(entity.getEndereco().getEstado())
                    .cep(entity.getEndereco().getCep())
                    .build();
        }

        return AdotanteResponseDTO.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .telefone(entity.getTelefone())
                .email(entity.getEmail())
                .documentoIdentificacao(entity.getDocumentoIdentificacao())
                .endereco(enderecoDTO)
                .observacoes(entity.getObservacoes())
                .fotoUrl(entity.getFotoUrl())
                .dataCriacao(entity.getDataCriacao())
                .build();
    }
}
