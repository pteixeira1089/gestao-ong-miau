import React, { useState } from 'react';
import Input from '../shared/Input/Input';
import Button from '../shared/Button/Button';
import styles from './LoginForm.module.css';

const LoginForm = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const handleSubmit = (event) => {
    event.preventDefault(); // Impede o recarregamento da página
    // Aqui, futuramente, faremos a chamada para a API de login
    console.log('Email:', email, 'Password:', password);
    alert(`Login com Email: ${email}`);
  };

  return (
    <form className={styles.form} onSubmit={handleSubmit}>
      <Input
        type="email"
        placeholder="Digite seu e-mail"
        value={email}
        onChange={(e) => setEmail(e.target.value)}
      />
      <Input
        type="password"
        placeholder="Digite sua senha"
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
