import React from 'react';
import LoginForm from '../../components/LoginForm/LoginForm';
import styles from './Login.module.css';

const Login = () => {
  return (
    <div className={styles.container}>
      <h1 className={styles.title}>Miau Aumigos</h1>
      <LoginForm />
    </div>
  );
};

export default Login;
