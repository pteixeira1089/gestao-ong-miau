import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { listarAnimais, cadastrarAnimal, atualizarAnimal, deletarAnimal } from '../../services/animalService';
import Card from '../../components/shared/Card/Card';
import Button from '../../components/shared/Button/Button';
import Modal from '../../components/shared/Modal/Modal';
import AnimalForm from './components/AnimalForm/AnimalForm';
import AdocaoForm from './components/AdocaoForm/AdocaoForm';
import styles from './Animais.module.css';

const Animais = () => {
  const navigate = useNavigate();
  const [animais, setAnimais] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showForm, setShowForm] = useState(false);
  const [editingAnimal, setEditingAnimal] = useState(null);
  
  // Estado para Modal de Adoção Rápida (opcional, já que temos a timeline)
  const [adocaoModalOpen, setAdocaoModalOpen] = useState(false);
  const [animalSelecionado, setAnimalSelecionado] = useState(null);

  useEffect(() => {
    fetchAnimais();
  }, []);

  const fetchAnimais = async () => {
    try {
      setLoading(true);
      const data = await listarAnimais();
      setAnimais(data);
    } catch (error) {
      console.error('Erro ao buscar animais:', error);
      alert('Erro ao carregar a lista de animais.');
    } finally {
      setLoading(false);
    }
  };

  const handleNovoAnimal = () => {
    setEditingAnimal(null);
    setShowForm(true);
  };

  const handleEditarAnimal = (animal) => {
    setEditingAnimal(animal);
    setShowForm(true);
  };

  const handleDeletarAnimal = async (id) => {
    if (window.confirm('Tem certeza que deseja excluir este animal?')) {
      try {
        await deletarAnimal(id);
        fetchAnimais();
      } catch (error) {
        console.error('Erro ao deletar:', error);
        alert('Erro ao excluir animal.');
      }
    }
  };

  const handleFormSubmit = async (formData) => {
    try {
      if (editingAnimal) {
        await atualizarAnimal(editingAnimal.id, formData);
      } else {
        await cadastrarAnimal(formData);
      }
      setShowForm(false);
      fetchAnimais();
    } catch (error) {
      console.error('Erro ao salvar:', error);
      alert('Erro ao salvar animal. Verifique os dados.');
    }
  };

  const handleCancel = () => {
    setShowForm(false);
    setEditingAnimal(null);
  };

  // --- Ações ---
  const irParaTimeline = (id) => {
    navigate(`/animais/${id}/timeline`);
  };

  const handleAbrirAdocao = (animal) => {
    setAnimalSelecionado(animal);
    setAdocaoModalOpen(true);
  };

  const handleAdocaoSucesso = () => {
    setAdocaoModalOpen(false);
    setAnimalSelecionado(null);
    fetchAnimais(); 
  };

  if (loading) return <div className={styles.loading}>Carregando...</div>;

  if (showForm) {
    return (
      <div className={styles.container}>
        <AnimalForm 
          initialData={editingAnimal} 
          onSubmit={handleFormSubmit} 
          onCancel={handleCancel} 
        />
      </div>
    );
  }

  return (
    <div className={styles.container}>
      <div className={styles.header}>
        <h1>Nossos Animais</h1>
        <Button onClick={handleNovoAnimal}>+ Novo Animal</Button>
      </div>

      {animais.length === 0 ? (
        <div className={styles.empty}>
          <p>Nenhum animal cadastrado ainda.</p>
        </div>
      ) : (
        <div className={styles.grid}>
          {animais.map((animal) => (
            <Card
              key={animal.id}
              title={animal.nome}
              subtitle={`${animal.especieAnimal} - ${animal.sexo === 'M' ? 'Macho' : 'Fêmea'}`}
              image={animal.urlFoto || 'https://via.placeholder.com/300?text=Sem+Foto'}
              actions={
                <div style={{ display: 'flex', gap: '0.5rem', flexWrap: 'wrap', justifyContent: 'flex-end' }}>
                  <Button onClick={() => irParaTimeline(animal.id)} style={{ backgroundColor: '#17a2b8' }}>Histórico 📅</Button>
                  
                  {animal.adotado ? (
                    <div style={{ 
                      padding: '0.5rem 1rem', 
                      backgroundColor: '#e0f7fa', 
                      color: '#006064', 
                      borderRadius: '4px',
                      fontWeight: 'bold',
                      border: '1px solid #b2ebf2',
                      display: 'flex',
                      alignItems: 'center',
                      fontSize: '0.9rem'
                    }}>
                      Adotado 🏠
                    </div>
                  ) : (
                    <Button onClick={() => handleAbrirAdocao(animal)} style={{ backgroundColor: '#28a745' }}>Adotar 🏠</Button>
                  )}
                  
                  <Button onClick={() => handleEditarAnimal(animal)}>Editar</Button>
                  <Button onClick={() => handleDeletarAnimal(animal.id)} style={{ backgroundColor: '#dc3545' }}>Excluir</Button>
                </div>
              }
            >
              <p><strong>Pelagem:</strong> {animal.pelagem}</p>
              <p>{animal.bio}</p>
            </Card>
          ))}
        </div>
      )}

      {/* Mantive o Modal de Adoção Rápida aqui também, pois é prático */}
      <Modal 
        isOpen={adocaoModalOpen} 
        onClose={() => setAdocaoModalOpen(false)}
        title="Registrar Adoção"
      >
        {animalSelecionado && (
          <AdocaoForm 
            animal={animalSelecionado} 
            onSuccess={handleAdocaoSucesso} 
            onCancel={() => setAdocaoModalOpen(false)} 
          />
        )}
      </Modal>
    </div>
  );
};

export default Animais;
