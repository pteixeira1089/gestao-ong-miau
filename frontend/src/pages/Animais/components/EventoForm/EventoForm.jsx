import React, { useState } from 'react';
import Input from '../../../../components/shared/Input/Input';
import Select from '../../../../components/shared/Select/Select';
import Button from '../../../../components/shared/Button/Button';
import { registrarEvento } from '../../../../services/eventoService';
import { TIPO_EVENTO_OPTIONS } from '../../../../constants/eventoModel';

const EventoForm = ({ animal, onSuccess, onCancel }) => {
  const getHojeLocal = () => {
    const hoje = new Date();
    const ano = hoje.getFullYear();
    const mes = String(hoje.getMonth() + 1).padStart(2, '0');
    const dia = String(hoje.getDate()).padStart(2, '0');
    return `${ano}-${mes}-${dia}`;
  };

  const [tipoEvento, setTipoEvento] = useState('');
  const [dataEvento, setDataEvento] = useState(getHojeLocal());
  const [observacoes, setObservacoes] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');

    if (!tipoEvento) {
      setError('Selecione o tipo do evento.');
      return;
    }

    const hoje = getHojeLocal();
    if (dataEvento > hoje) {
      setError('A data do evento não pode ser no futuro.');
      return;
    }

    setLoading(true);
    try {
      await registrarEvento({
        animalId: animal.id,
        tipoEvento,
        dataEvento,
        observacoes
      });
      alert('Evento registrado com sucesso!');
      onSuccess();
    } catch (err) {
      console.error(err);
      let msg = 'Erro ao registrar evento.';
      if (err.response && err.response.data) {
         if (err.response.data.errors) {
             if (Array.isArray(err.response.data.errors)) {
                 msg = err.response.data.errors.map(e => e.defaultMessage).join(', ');
             } else {
                 msg = JSON.stringify(err.response.data.errors);
             }
         } else if (err.response.data.message) {
             msg = err.response.data.message;
         }
      }
      setError(msg);
    } finally {
      setLoading(false);
    }
  };

  return (
    <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '1rem' }}>
      <p>Registrando evento para: <strong>{animal.nome}</strong></p>
      
      {error && (
        <div style={{ color: '#d32f2f', backgroundColor: '#ffebee', padding: '0.5rem', borderRadius: '4px', border: '1px solid #ef9a9a' }}>
          {error}
        </div>
      )}

      <Select
        label="Tipo de Evento"
        value={tipoEvento}
        onChange={(e) => setTipoEvento(e.target.value)}
        options={TIPO_EVENTO_OPTIONS}
        placeholder="Selecione o tipo..."
      />

      <Input 
        type="date" 
        placeholder="Data do Evento" 
        value={dataEvento} 
        onChange={(e) => setDataEvento(e.target.value)} 
        max={getHojeLocal()}
      />

      <Input 
        placeholder="Observações (Ex: Vacina V10, Vermífugo...)" 
        value={observacoes} 
        onChange={(e) => setObservacoes(e.target.value)} 
      />

      <div style={{ display: 'flex', justifyContent: 'flex-end', gap: '1rem', marginTop: '1rem' }}>
        <Button type="button" onClick={onCancel} style={{ backgroundColor: '#ccc' }}>Cancelar</Button>
        <Button type="submit" disabled={loading}>
          {loading ? 'Salvando...' : 'Confirmar Evento'}
        </Button>
      </div>
    </form>
  );
};

export default EventoForm;
