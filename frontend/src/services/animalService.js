import api from './api';

export const listarAnimais = async () => {
  try {
    const response = await api.get('/animais');
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const cadastrarAnimal = async (animal) => {
  try {
    const response = await api.post('/animais', animal);
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const buscarAnimalPorId = async (id) => {
  try {
    const response = await api.get(`/animais/${id}`);
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const atualizarAnimal = async (id, animal) => {
  try {
    const response = await api.put(`/animais/${id}`, animal);
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const deletarAnimal = async (id) => {
  try {
    await api.delete(`/animais/${id}`);
  } catch (error) {
    throw error;
  }
};
