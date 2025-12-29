import React from 'react';
import { useNavigate } from 'react-router-dom';
import Button from '../../components/shared/Button/Button';
import styles from './Home.module.css';

const Home = () => {
  const navigate = useNavigate();
  const user = JSON.parse(localStorage.getItem('user') || '{}');

  return (
    <div className={styles.container}>
      <h1 className={styles.welcome}>Bem-vindo, {user.nome || 'Colaborador'}!</h1>
      <p>O que você deseja fazer hoje?</p>
      
      <div style={{ marginTop: '2rem', display: 'flex', gap: '1rem', justifyContent: 'center' }}>
        <Button onClick={() => navigate('/animais')}>
          Gerenciar Animais
        </Button>
        <Button onClick={() => navigate('/adotantes')}>
          Gerenciar Adotantes
        </Button>
      </div>
    </div>
  );
};

export default Home;
