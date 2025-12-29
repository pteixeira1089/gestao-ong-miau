import React, { useState, useEffect } from 'react';
import Select from '../Select/Select';
import { listarAdotantes } from '../../../services/adotanteService';

const SelectAdotante = ({ value, onChange, label }) => {
  const [options, setOptions] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchAdotantes = async () => {
      try {
        const data = await listarAdotantes();
        const formattedOptions = data.map(adotante => ({
          value: adotante.id,
          label: `${adotante.nome} (CPF: ${adotante.documentoIdentificacao})`
        }));
        setOptions(formattedOptions);
      } catch (error) {
        console.error("Erro ao carregar adotantes", error);
      } finally {
        setLoading(false);
      }
    };

    fetchAdotantes();
  }, []);

  if (loading) return <div>Carregando adotantes...</div>;

  return (
    <Select
      label={label || "Selecione o Adotante"}
      value={value}
      onChange={onChange}
      options={options}
      placeholder="Escolha um adotante..."
    />
  );
};

export default SelectAdotante;
