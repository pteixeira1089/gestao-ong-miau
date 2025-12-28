import React from 'react';
import styles from './Select.module.css';

const Select = ({ label, value, onChange, options, placeholder }) => {
  return (
    <div className={styles.container}>
      {label && <label className={styles.label}>{label}</label>}
      <select className={styles.select} value={value} onChange={onChange}>
        <option value="" disabled>
          {placeholder || 'Selecione uma opção'}
        </option>
        {options.map((option) => (
          <option key={option.value} value={option.value}>
            {option.label}
          </option>
        ))}
      </select>
    </div>
  );
};

export default Select;
