import api from './api';

export const listarTimeline = async (animalId) => {
  try {
    const response = await api.get(`/timeline/${animalId}`);
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const deletarEvento = async (eventoId) => {
  try {
    await api.delete(`/timeline/${eventoId}`);
  } catch (error) {
    throw error;
  }
};
