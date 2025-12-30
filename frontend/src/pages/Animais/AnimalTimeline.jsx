import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { buscarAnimalPorId } from '../../services/animalService';
import { listarTimeline, deletarEvento } from '../../services/timelineService';
import TimelineItem from './components/Timeline/TimelineItem';
import Button from '../../components/shared/Button/Button';
import Modal from '../../components/shared/Modal/Modal';
import AdocaoForm from './components/AdocaoForm/AdocaoForm';
import EventoForm from './components/EventoForm/EventoForm';
import styles from './Animais.module.css'; // Reutilizando estilos básicos

const AnimalTimeline = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  
  const [animal, setAnimal] = useState(null);
  const [eventos, setEventos] = useState([]);
  const [loading, setLoading] = useState(true);
  
  // Modais
  const [adocaoModalOpen, setAdocaoModalOpen] = useState(false);
  const [eventoModalOpen, setEventoModalOpen] = useState(false);

  useEffect(() => {
    fetchData();
  }, [id]);

  const fetchData = async () => {
    try {
      setLoading(true);
      const [animalData, timelineData] = await Promise.all([
        buscarAnimalPorId(id),
        listarTimeline(id)
      ]);
      setAnimal(animalData);
      setEventos(timelineData);
    } catch (error) {
      console.error("Erro ao carregar timeline", error);
      alert("Erro ao carregar dados do animal.");
      navigate('/animais');
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (eventoId) => {
    if (window.confirm("Tem certeza que deseja excluir este evento do histórico?")) {
      try {
        await deletarEvento(eventoId);
        fetchData(); // Recarrega a lista
      } catch (error) {
        console.error(error);
        alert("Erro ao excluir evento.");
      }
    }
  };

  const handleSuccess = () => {
    setAdocaoModalOpen(false);
    setEventoModalOpen(false);
    fetchData();
  };

  if (loading) return <div className={styles.loading}>Carregando histórico...</div>;
  if (!animal) return null;

  return (
    <div className={styles.container}>
      <div className={styles.header}>
        <div>
          <Button onClick={() => navigate('/animais')} style={{ backgroundColor: '#6c757d', marginRight: '1rem' }}>
            ← Voltar
          </Button>
          <h1 style={{ display: 'inline-block' }}>Histórico: {animal.nome}</h1>
        </div>
        
        <div style={{ display: 'flex', gap: '1rem' }}>
          <Button onClick={() => setEventoModalOpen(true)} style={{ backgroundColor: '#17a2b8' }}>
            + Evento
          </Button>
          {!animal.adotado && (
            <Button onClick={() => setAdocaoModalOpen(true)} style={{ backgroundColor: '#28a745' }}>
              + Adoção
            </Button>
          )}
        </div>
      </div>

      <div style={{ maxWidth: '800px', margin: '0 auto', marginTop: '2rem' }}>
        {eventos.length === 0 ? (
          <p className={styles.empty}>Nenhum evento registrado para este animal.</p>
        ) : (
          eventos.map(evento => (
            <TimelineItem 
              key={evento.id} 
              evento={evento} 
              onDelete={handleDelete} 
            />
          ))
        )}
      </div>

      {/* Modais */}
      <Modal 
        isOpen={adocaoModalOpen} 
        onClose={() => setAdocaoModalOpen(false)}
        title="Registrar Adoção"
      >
        <AdocaoForm 
          animal={animal} 
          onSuccess={handleSuccess} 
          onCancel={() => setAdocaoModalOpen(false)} 
        />
      </Modal>

      <Modal 
        isOpen={eventoModalOpen} 
        onClose={() => setEventoModalOpen(false)}
        title="Registrar Evento"
      >
        <EventoForm 
          animal={animal} 
          onSuccess={handleSuccess} 
          onCancel={() => setEventoModalOpen(false)} 
        />
      </Modal>
    </div>
  );
};

export default AnimalTimeline;
