import React, { useState, useEffect } from 'react';
import { listarAnimais, cadastrarAnimal, atualizarAnimal, deletarAnimal } from '../../services/animalService';
import Card from '../../components/shared/Card/Card';
import Button from '../../components/shared/Button/Button';
import AnimalForm from './components/AnimalForm/AnimalForm';
import styles from './Animais.module.css';

const Animais = () => {
  const [animais, setAnimais] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showForm, setShowForm] = useState(false);
  const [editingAnimal, setEditingAnimal] = useState(null);

  // Carrega os animais ao abrir a página
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
        fetchAnimais(); // Recarrega a lista
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
      fetchAnimais(); // Recarrega a lista
    } catch (error) {
      console.error('Erro ao salvar:', error);
      alert('Erro ao salvar animal. Verifique os dados.');
    }
  };

  const handleCancel = () => {
    setShowForm(false);
    setEditingAnimal(null);
  };

  if (loading) return <div className={styles.loading}>Carregando...</div>;

  // Se estiver no modo formulário, mostra o form
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

  // Modo listagem
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
                <>
                  <Button onClick={() => handleEditarAnimal(animal)}>Editar</Button>
                  <Button onClick={() => handleDeletarAnimal(animal.id)} style={{ backgroundColor: '#dc3545' }}>Excluir</Button>
                </>
              }
            >
              <p><strong>Pelagem:</strong> {animal.pelagem}</p>
              <p>{animal.bio}</p>
            </Card>
          ))}
        </div>
      )}
    </div>
  );
};

export default Animais;
