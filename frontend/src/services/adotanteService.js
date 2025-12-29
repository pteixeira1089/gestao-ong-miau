import api from './api';

export const listarAdotantes = async () => {
  try {
    const response = await api.get('/adotantes');
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const cadastrarAdotante = async (adotante) => {
  try {
    const response = await api.post('/adotantes', adotante);
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const buscarAdotantePorId = async (id) => {
  try {
    const response = await api.get(`/adotantes/${id}`);
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const atualizarAdotante = async (id, adotante) => {
  try {
    const response = await api.put(`/adotantes/${id}`, adotante);
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const deletarAdotante = async (id) => {
  try {
    await api.delete(`/adotantes/${id}`);
  } catch (error) {
    throw error;
  }
};
