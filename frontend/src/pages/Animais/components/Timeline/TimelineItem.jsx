import React from 'react';
import Button from '../../../../components/shared/Button/Button';
import styles from './TimelineItem.module.css';

const TimelineItem = ({ evento, onDelete }) => {
  const getIcon = (tipo) => {
    switch (tipo) {
      case 'ADOCAO': return '🏠';
      case 'DEVOLUCAO': return '↩️';
      case 'FALECIMENTO': return '✝️';
      case 'ACOLHIMENTO': return '📥';
      case 'ALTA': return '🏥';
      default: return '📅';
    }
  };

  const getLabel = (tipo) => {
    switch (tipo) {
      case 'ADOCAO': return 'Adoção';
      case 'DEVOLUCAO': return 'Devolução';
      case 'INTERNACAO': return 'Internação';
      case 'ALTA': return 'Alta Médica';
      default: return tipo.charAt(0) + tipo.slice(1).toLowerCase();
    }
  };

  // Formata data para DD/MM/YYYY
  const formatDate = (dateString) => {
    if (!dateString) return '';
    const [ano, mes, dia] = dateString.split('-');
    return `${dia}/${mes}/${ano}`;
  };

  return (
    <div className={styles.item}>
      <div className={`${styles.icon} ${styles[evento.tipo]}`}>
        {getIcon(evento.tipo)}
      </div>
      <div className={styles.content}>
        <div className={styles.header}>
          <h4 className={styles.title}>{getLabel(evento.tipo)}</h4>
          <span className={styles.date}>{formatDate(evento.dataEvento)}</span>
        </div>
        
        <div className={styles.body}>
          {evento.tipo === 'ADOCAO' && (
            <p><strong>Adotante:</strong> {evento.nomeAdotante}</p>
          )}
          {evento.observacoes && <p>{evento.observacoes}</p>}
        </div>

        <div className={styles.actions}>
          <Button 
            onClick={() => onDelete(evento.id)} 
            style={{ backgroundColor: '#dc3545', padding: '5px 10px', fontSize: '0.8rem' }}
          >
            Excluir
          </Button>
        </div>
      </div>
    </div>
  );
};

export default TimelineItem;
