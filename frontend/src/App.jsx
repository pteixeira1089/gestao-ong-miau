import React from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import Login from './pages/Login/Login';
import Home from './pages/Home/Home';
import Animais from './pages/Animais/Animais';
import AnimalTimeline from './pages/Animais/AnimalTimeline';
import Adotantes from './pages/Adotantes/Adotantes';
import './App.css';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/home" element={<Home />} />
        <Route path="/animais" element={<Animais />} />
        <Route path="/animais/:id/timeline" element={<AnimalTimeline />} />
        <Route path="/adotantes" element={<Adotantes />} />
        <Route path="*" element={<Navigate to="/login" replace />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
