import React, { useState, useEffect } from 'react';
import { listarAdotantes, cadastrarAdotante, atualizarAdotante, deletarAdotante } from '../../services/adotanteService';
import Card from '../../components/shared/Card/Card';
import Button from '../../components/shared/Button/Button';
import AdotanteForm from './components/AdotanteForm/AdotanteForm';
import styles from './Adotantes.module.css';

const Adotantes = () => {
  const [adotantes, setAdotantes] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showForm, setShowForm] = useState(false);
  const [editingAdotante, setEditingAdotante] = useState(null);

  useEffect(() => {
    fetchAdotantes();
  }, []);

  const fetchAdotantes = async () => {
    try {
      setLoading(true);
      const data = await listarAdotantes();
      setAdotantes(data);
    } catch (error) {
      console.error('Erro ao buscar adotantes:', error);
      alert('Erro ao carregar a lista de adotantes.');
    } finally {
      setLoading(false);
    }
  };

  const handleNovoAdotante = () => {
    setEditingAdotante(null);
    setShowForm(true);
  };

  const handleEditarAdotante = (adotante) => {
    setEditingAdotante(adotante);
    setShowForm(true);
  };

  const handleDeletarAdotante = async (id) => {
    if (window.confirm('Tem certeza que deseja excluir este adotante?')) {
      try {
        await deletarAdotante(id);
        fetchAdotantes(); // Recarrega a lista
      } catch (error) {
        console.error('Erro ao deletar:', error);
        alert('Erro ao excluir adotante.');
      }
    }
  };

  const handleFormSubmit = async (formData) => {
    try {
      if (editingAdotante) {
        await atualizarAdotante(editingAdotante.id, formData);
      } else {
        await cadastrarAdotante(formData);
      }
      setShowForm(false);
      fetchAdotantes();
    } catch (error) {
      console.error('Erro ao salvar:', error);
      alert('Erro ao salvar adotante. Verifique os dados (CPF, Email, Endereço).');
    }
  };

  const handleCancel = () => {
    setShowForm(false);
    setEditingAdotante(null);
  };

  if (loading) return <div className={styles.loading}>Carregando...</div>;

  if (showForm) {
    return (
      <div className={styles.container}>
        <AdotanteForm 
          initialData={editingAdotante} 
          onSubmit={handleFormSubmit} 
          onCancel={handleCancel} 
        />
      </div>
    );
  }

  return (
    <div className={styles.container}>
      <div className={styles.header}>
        <h1>Adotantes Cadastrados</h1>
        <Button onClick={handleNovoAdotante}>+ Novo Adotante</Button>
      </div>

      {adotantes.length === 0 ? (
        <div className={styles.empty}>
          <p>Nenhum adotante cadastrado ainda.</p>
        </div>
      ) : (
        <div className={styles.grid}>
          {adotantes.map((adotante) => (
            <Card
              key={adotante.id}
              title={adotante.nome}
              subtitle={adotante.email}
              image={adotante.fotoUrl || 'https://via.placeholder.com/300?text=Sem+Foto'}
              actions={
                <>
                  <Button onClick={() => handleEditarAdotante(adotante)}>Editar</Button>
                  <Button onClick={() => handleDeletarAdotante(adotante.id)} style={{ backgroundColor: '#dc3545' }}>Excluir</Button>
                </>
              }
            >
              <p><strong>Tel:</strong> {adotante.telefone}</p>
              <p><strong>Cidade:</strong> {adotante.endereco?.cidade} - {adotante.endereco?.estado}</p>
            </Card>
          ))}
        </div>
      )}
    </div>
  );
};

export default Adotantes;
