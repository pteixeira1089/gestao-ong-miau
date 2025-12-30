import api from './api';

export const registrarEvento = async (eventoDTO) => {
  try {
    const response = await api.post('/eventos', eventoDTO);
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const listarEventosPorAnimal = async (animalId) => {
  try {
    const response = await api.get(`/eventos/animal/${animalId}`);
    return response.data;
  } catch (error) {
    throw error;
  }
};
