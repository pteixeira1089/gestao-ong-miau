// Define a estrutura padrão de um animal para formulários
export const INITIAL_ANIMAL_STATE = {
  nome: '',
  especieAnimal: '', // GATO, CACHORRO, OUTRO
  sexo: '', // M, F
  pelagem: '',
  bio: '',
  urlFoto: ''
};

// Opções para os selects
export const ESPECIE_OPTIONS = [
  { value: 'GATO', label: 'Gato' },
  { value: 'CACHORRO', label: 'Cachorro' },
  { value: 'OUTRO', label: 'Outro' }
];

export const SEXO_OPTIONS = [
  { value: 'M', label: 'Macho' },
  { value: 'F', label: 'Fêmea' }
];
