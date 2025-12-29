import api from './api';

export const registrarAdocao = async (adocaoDTO) => {
  try {
    const response = await api.post('/adocoes', adocaoDTO);
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const listarAdocoes = async () => {
  try {
    const response = await api.get('/adocoes');
    return response.data;
  } catch (error) {
    throw error;
  }
};
