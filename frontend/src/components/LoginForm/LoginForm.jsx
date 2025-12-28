import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Input from '../shared/Input/Input';
import Button from '../shared/Button/Button';
import { login } from '../../services/authService';
import styles from './LoginForm.module.css';

const LoginForm = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleSubmit = async (event) => {
    event.preventDefault();
    setError('');

    try {
      const data = await login(username, password);
      
      // Salva o token e dados do usuário no localStorage
      localStorage.setItem('token', data.token);
      localStorage.setItem('user', JSON.stringify({ username: data.username, nome: data.nome }));
      
      // Redireciona para a Home
      navigate('/home');
    } catch (err) {
      console.error('Erro no login:', err);
      setError('Falha no login. Verifique suas credenciais.');
    }
  };

  return (
    <form className={styles.form} onSubmit={handleSubmit}>
      {error && <div className={styles.error}>{error}</div>}
      <Input
        type="text"
        placeholder="Usuário"
        value={username}
        onChange={(e) => setUsername(e.target.value)}
      />
      <Input
        type="password"
        placeholder="Senha"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
      />
      <Button type="submit">
        Entrar
      </Button>
    </form>
  );
};

export default LoginForm;
