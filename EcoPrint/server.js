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
const IMPRESSORAS_COUNT_URL = 'http://localhost:9000/impressoras/count';  // A URL que retorna a contagem de impressoras

// Rota para obter a contagem de impressoras
app.get('/impressoras/count', async (req, res) => {
    try {
        // Fazendo a requisição GET para obter a contagem
        const response = await axios.get(IMPRESSORAS_COUNT_URL);
        res.json({ count: response.data });  // Retorna a contagem como resposta
    } catch (err) {
        res.status(500).send('Erro ao carregar a contagem de impressoras.');
    }
});

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


// Rota para imprimir com a impressora
app.post('/impressoras/imprimir', async (req, res) => {
    const { produtoId, descricao, dadosImagem } = req.body;

    console.log(`Produto ID: ${produtoId}`);
    console.log(`Descrição: ${descricao}`);
    console.log(`Dados da Imagem: ${dadosImagem}`);

    // Verificando se todos os parâmetros estão presentes
    if (!produtoId || !descricao || !dadosImagem) {
        return res.status(400).send("Todos os campos são obrigatórios.");
    }

    // Verificando se dadosImagem é uma string
    if (typeof dadosImagem !== 'string') {
        return res.status(400).send("Os dados da imagem devem ser uma string.");
    }

    console.log(`Produto ID: ${produtoId}`);
    console.log(`Descrição: ${descricao}`);
    console.log(`Dados da Imagem: ${dadosImagem}`);


    console.log("ENVIANDO DTO")
    const reqx = {
        produtoId: produtoId,
        cmykPredictor: await postImageBase64(dadosImagem),
        dadosImagem: dadosImagem,
        descricao: descricao,
    };
    postDTO(reqx)

});

function postDTO(req) {
    const url = "http://localhost:9000/impressoes"; // URL do endpoint

    fetch(url, {
      method: "POST", // Método HTTP POST
      headers: {
        "Content-Type": "application/json", // Define o tipo de conteúdo como JSON
      },
      body: JSON.stringify(req), // Converte o objeto req em JSON
    })
    .then((response) => response.json())
    .then((data) => {
        console.log("Resposta:", data);
    })
    .catch((error) => {
        console.error("Erro:", error);
    });
  }

function postImageBase64(imageBase64) {
    const url =
      "http://127.0.0.1:7475/CmykPredictor?image_base64=" +
      encodeURIComponent(imageBase64);

      fetch(url, {
        method: "POST",
        headers: {
            Accept: "application/json",
            "Content-Type": "application/json",  // Define o tipo de conteúdo para JSON
        },
        body: JSON.stringify({ image_base64: imageBase64 }) // Envia a imagem base64 no corpo da requisição
    })
    .then((response) => response.json())
    .then((data) => {
        console.log("Resposta:", data);
    })
    .catch((error) => {
        console.error("Erro:", error);
    });
  }