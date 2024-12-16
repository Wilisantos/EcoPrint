const express = require('express');
const bodyParser = require('body-parser');
const axios = require('axios');
const cors = require('cors');
const app = express();
const PORT = 8000;

app.use(cors());
app.use(bodyParser.json());

// URL do backend Spring Boot
const SPRING_BOOT_API_URL = 'http://localhost:8978/impressoras';  // Ajuste conforme a URL do seu Spring Boot

// Rota para obter as impressoras
app.get('/impressoras', async (req, res) => {
    try {
        const response = await axios.get(SPRING_BOOT_API_URL);
        res.json(response.data);
    } catch (err) {
        res.status(500).send('Erro ao carregar as impressoras.');
    }
});

// Rota para adicionar uma nova impressora
app.post('/impressoras', async (req, res) => {
    const newPrinter = req.body;

    try {
        // Envia os dados para o backend Spring Boot
        const response = await axios.post(SPRING_BOOT_API_URL, {
            modelo: newPrinter.modelo,
            capacidade: newPrinter.capacidade,
            tipoImpressora: newPrinter.tipoImpressora,
        });
        res.status(200).send('Impressora adicionada com sucesso.');
    } catch (err) {
        res.status(500).send('Erro ao adicionar impressora.');
    }
});

app.post('/impressoras/:id/imprimir', async (req, res) => {

});

// Rota para remover uma impressora
app.delete('/impressoras/:id', async (req, res) => {
    const printerId = req.params.id;

    try {
        const response = await axios.delete(`${SPRING_BOOT_API_URL}/${printerId}`);
        res.status(200).send('Impressora removida com sucesso.');
    } catch (err) {
        res.status(500).send('Erro ao remover impressora.');
    }
});

// Inicia o servidor
app.listen(PORT, () => {
    console.log(`Servidor rodando em http://localhost:${PORT}`);
});
