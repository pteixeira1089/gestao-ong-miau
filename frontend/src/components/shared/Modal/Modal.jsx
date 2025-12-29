import React from 'react';
import styles from './Modal.module.css';

const Modal = ({ isOpen, onClose, children, title }) => {
  if (!isOpen) return null;

  return (
    <div className={styles.overlay}>
      <div className={styles.content}>
        <button className={styles.closeButton} onClick={onClose}>&times;</button>
        {title && <h2>{title}</h2>}
        {children}
      </div>
    </div>
  );
};

export default Modal;
