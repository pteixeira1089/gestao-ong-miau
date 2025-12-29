import React, { useState } from 'react';
import Input from '../../../../components/shared/Input/Input';
import Button from '../../../../components/shared/Button/Button';
import SelectAdotante from '../../../../components/shared/SelectAdotante/SelectAdotante';
import { registrarAdocao } from '../../../../services/adocaoService';

const AdocaoForm = ({ animal, onSuccess, onCancel }) => {
  const [adotanteId, setAdotanteId] = useState('');
  
  // Pega a data de hoje no formato YYYY-MM-DD localmente
  const getHojeLocal = () => {
    const hoje = new Date();
    const ano = hoje.getFullYear();
    const mes = String(hoje.getMonth() + 1).padStart(2, '0');
    const dia = String(hoje.getDate()).padStart(2, '0');
    return `${ano}-${mes}-${dia}`;
  };

  const [dataAdocao, setDataAdocao] = useState(getHojeLocal());
  const [observacoes, setObservacoes] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');

    // Validação Frontend: Data Futura
    const hoje = getHojeLocal();
    
    // Comparação de strings YYYY-MM-DD funciona bem
    if (dataAdocao > hoje) {
      setError('A data da adoção não pode ser no futuro (Frontend Check).');
      return;
    }

    if (!adotanteId) {
      setError('Selecione um adotante!');
      return;
    }

    setLoading(true);
    try {
      await registrarAdocao({
        animalId: animal.id,
        adotanteId: parseInt(adotanteId),
        dataAdocao,
        observacoes
      });
      alert('Adoção registrada com sucesso! 🎉');
      onSuccess();
    } catch (err) {
      console.error("Erro capturado:", err);
      
      // Tenta extrair a mensagem de erro mais específica possível
      let msg = 'Erro ao registrar adoção.';

      if (err.response) {
        // Erro 400 com lista de erros de validação (padrão Spring Boot sem handler customizado)
        if (err.response.data && err.response.data.errors) {
           // Às vezes o Spring retorna errors como array de objetos
           if (Array.isArray(err.response.data.errors)) {
             msg = err.response.data.errors.map(e => e.defaultMessage).join(', ');
           } else {
             msg = JSON.stringify(err.response.data.errors);
           }
        } 
        // Erro 500 ou 400 com mensagem simples
        else if (err.response.data && err.response.data.message) {
          msg = err.response.data.message;
        }
        // Fallback para status text
        else if (err.response.statusText) {
          msg = `Erro ${err.response.status}: ${err.response.statusText}`;
        }
      } else if (err.message) {
        msg = err.message;
      }

      setError(msg);
    } finally {
      setLoading(false);
    }
  };

  return (
    <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '1rem' }}>
      <p>Registrando adoção para: <strong>{animal.nome}</strong></p>
      
      {error && (
        <div style={{ color: '#d32f2f', backgroundColor: '#ffebee', padding: '0.5rem', borderRadius: '4px', border: '1px solid #ef9a9a' }}>
          {error}
        </div>
      )}

      <SelectAdotante 
        value={adotanteId} 
        onChange={(e) => setAdotanteId(e.target.value)} 
      />

      <Input 
        type="date" 
        placeholder="Data da Adoção" 
        value={dataAdocao} 
        onChange={(e) => setDataAdocao(e.target.value)} 
        max={getHojeLocal()} // Agora isso vai funcionar graças ao ajuste no Input.jsx
      />

      <Input 
        placeholder="Observações (opcional)" 
        value={observacoes} 
        onChange={(e) => setObservacoes(e.target.value)} 
      />

      <div style={{ display: 'flex', justifyContent: 'flex-end', gap: '1rem', marginTop: '1rem' }}>
        <Button type="button" onClick={onCancel} style={{ backgroundColor: '#ccc' }}>Cancelar</Button>
        <Button type="submit" disabled={loading}>
          {loading ? 'Salvando...' : 'Confirmar Adoção'}
        </Button>
      </div>
    </form>
  );
};

export default AdocaoForm;
