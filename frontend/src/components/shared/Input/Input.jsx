import React from 'react';
import styles from './Input.module.css';

// Adicionamos ...props para pegar todo o resto (max, min, disabled, etc)
const Input = ({ type = 'text', placeholder, value, onChange, ...props }) => {
  return (
    <input
      type={type}
      placeholder={placeholder}
      value={value}
      onChange={onChange}
      className={styles.input}
      {...props} // Espalha as props extras aqui
    />
  );
};

export default Input;
