import React, { useState, useEffect } from 'react';
import Input from '../../../../components/shared/Input/Input';
import Button from '../../../../components/shared/Button/Button';
import { INITIAL_ADOTANTE_STATE } from '../../../../constants/adotanteModel';
import styles from './AdotanteForm.module.css';

const AdotanteForm = ({ initialData, onSubmit, onCancel }) => {
  const [formData, setFormData] = useState(INITIAL_ADOTANTE_STATE);

  useEffect(() => {
    if (initialData) {
      setFormData(initialData);
    } else {
      setFormData(INITIAL_ADOTANTE_STATE);
    }
  }, [initialData]);

  const handleChange = (field, value) => {
    setFormData((prev) => ({
      ...prev,
      [field]: value
    }));
  };

  const handleEnderecoChange = (field, value) => {
    setFormData((prev) => ({
      ...prev,
      endereco: {
        ...prev.endereco,
        [field]: value
      }
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit(formData);
  };

  return (
    <form className={styles.form} onSubmit={handleSubmit}>
      <h2>{initialData ? 'Editar Adotante' : 'Novo Adotante'}</h2>
      
      <h3 className={styles.sectionTitle}>Dados Pessoais</h3>
      <Input
        placeholder="Nome Completo"
        value={formData.nome}
        onChange={(e) => handleChange('nome', e.target.value)}
      />

      <div className={styles.row}>
        <Input
          placeholder="CPF / RG"
          value={formData.documentoIdentificacao}
          onChange={(e) => handleChange('documentoIdentificacao', e.target.value)}
        />
        <Input
          placeholder="Telefone"
          value={formData.telefone}
          onChange={(e) => handleChange('telefone', e.target.value)}
        />
      </div>

      <Input
        type="email"
        placeholder="E-mail"
        value={formData.email}
        onChange={(e) => handleChange('email', e.target.value)}
      />

      <h3 className={styles.sectionTitle}>Endereço</h3>
      <div className={styles.row}>
        <Input
          placeholder="CEP (00000-000)"
          value={formData.endereco.cep}
          onChange={(e) => handleEnderecoChange('cep', e.target.value)}
        />
        <div style={{ flex: 3 }}>
          <Input
            placeholder="Logradouro (Rua, Av...)"
            value={formData.endereco.logradouro}
            onChange={(e) => handleEnderecoChange('logradouro', e.target.value)}
          />
        </div>
      </div>

      <div className={styles.row}>
        <div className={styles.smallInput}>
          <Input
            placeholder="Número"
            value={formData.endereco.numero}
            onChange={(e) => handleEnderecoChange('numero', e.target.value)}
          />
        </div>
        <Input
          placeholder="Complemento"
          value={formData.endereco.complemento}
          onChange={(e) => handleEnderecoChange('complemento', e.target.value)}
        />
      </div>

      <div className={styles.row}>
        <Input
          placeholder="Bairro"
          value={formData.endereco.bairro}
          onChange={(e) => handleEnderecoChange('bairro', e.target.value)}
        />
        <Input
          placeholder="Cidade"
          value={formData.endereco.cidade}
          onChange={(e) => handleEnderecoChange('cidade', e.target.value)}
        />
        <div className={styles.smallInput}>
          <Input
            placeholder="UF"
            value={formData.endereco.estado}
            onChange={(e) => handleEnderecoChange('estado', e.target.value)}
          />
        </div>
      </div>

      <h3 className={styles.sectionTitle}>Outros</h3>
      <Input
        placeholder="URL da Foto (opcional)"
        value={formData.fotoUrl}
        onChange={(e) => handleChange('fotoUrl', e.target.value)}
      />
      
      <Input
        placeholder="Observações"
        value={formData.observacoes}
        onChange={(e) => handleChange('observacoes', e.target.value)}
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

export default AdotanteForm;
