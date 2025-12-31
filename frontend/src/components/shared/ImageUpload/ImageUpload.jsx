import React, { useState } from 'react';
import { uploadImagem } from '../../../services/uploadService';
import styles from './ImageUpload.module.css';

const ImageUpload = ({ value, onChange, placeholder = "📷" }) => {
  const [loading, setLoading] = useState(false);

  const handleFileChange = async (e) => {
    const file = e.target.files[0];
    if (!file) return;

    setLoading(true);
    try {
      const url = await uploadImagem(file);
      onChange(url); // Passa a URL recebida do backend para o pai
    } catch (error) {
      alert("Erro ao enviar imagem. Tente novamente.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className={styles.container}>
      <div className={styles.previewContainer}>
        {loading && <div className={styles.loading}>Enviando...</div>}
        
        {value ? (
          <img src={value} alt="Preview" className={styles.previewImage} />
        ) : (
          <span className={styles.placeholder}>{placeholder}</span>
        )}
      </div>
      
      <label className={styles.uploadButton}>
        {value ? 'Alterar Foto' : 'Adicionar Foto'}
        <input 
          type="file" 
          accept="image/*" 
          onChange={handleFileChange} 
          className={styles.hiddenInput} 
        />
      </label>
    </div>
  );
};

export default ImageUpload;
