import React, { useState, useEffect } from 'react';
import Input from '../../../../components/shared/Input/Input';
import Select from '../../../../components/shared/Select/Select';
import Button from '../../../../components/shared/Button/Button';
import { INITIAL_ANIMAL_STATE, ESPECIE_OPTIONS, SEXO_OPTIONS } from '../../../../constants/animalModel';
import styles from './AnimalForm.module.css';

const AnimalForm = ({ initialData, onSubmit, onCancel }) => {
  const [formData, setFormData] = useState(INITIAL_ANIMAL_STATE);

  // Se receber dados iniciais (edição), preenche o formulário
  useEffect(() => {
    if (initialData) {
      setFormData(initialData);
    } else {
      setFormData(INITIAL_ANIMAL_STATE);
    }
  }, [initialData]);

  const handleChange = (field, value) => {
    setFormData((prev) => ({
      ...prev,
      [field]: value
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit(formData);
  };

  return (
    <form className={styles.form} onSubmit={handleSubmit}>
      <h2>{initialData ? 'Editar Animal' : 'Novo Animal'}</h2>
      
      <Input
        placeholder="Nome do Animal"
        value={formData.nome}
        onChange={(e) => handleChange('nome', e.target.value)}
      />

      <div className={styles.row}>
        <Select
          placeholder="Espécie"
          options={ESPECIE_OPTIONS}
          value={formData.especieAnimal}
          onChange={(e) => handleChange('especieAnimal', e.target.value)}
        />
        <Select
          placeholder="Sexo"
          options={SEXO_OPTIONS}
          value={formData.sexo}
          onChange={(e) => handleChange('sexo', e.target.value)}
        />
      </div>

      <Input
        placeholder="Pelagem / Cor"
        value={formData.pelagem}
        onChange={(e) => handleChange('pelagem', e.target.value)}
      />

      <Input
        placeholder="URL da Foto (opcional)"
        value={formData.urlFoto}
        onChange={(e) => handleChange('urlFoto', e.target.value)}
      />

      <Input
        placeholder="Bio / Descrição"
        value={formData.bio}
        onChange={(e) => handleChange('bio', e.target.value)}
      />

      <div className={styles.actions}>
        <Button type="button" onClick={onCancel} style={{ backgroundColor: '#ccc' }}>
          Cancelar
        </Button>
        <Button type="submit">
          Salvar
        </Button>
      </div>
    </form>
  );
};

export default AnimalForm;
