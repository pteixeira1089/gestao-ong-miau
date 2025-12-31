import api from './api';

export const uploadImagem = async (arquivo) => {
  try {
    const formData = new FormData();
    // Ajuste 1: O backend espera @RequestParam("arquivo"), não "file"
    formData.append('arquivo', arquivo); 

    const response = await api.post('/fotos', formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });
    
    // Ajuste 2: O backend retorna um Map (JSON) { "nomeArquivo": "...", "url": "..." }
    // Nós queremos retornar apenas a URL para o componente usar
    return response.data.url; 
  } catch (error) {
    console.error("Erro no upload:", error);
    throw error;
  }
};
