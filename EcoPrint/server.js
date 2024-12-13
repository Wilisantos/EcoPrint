const express = require('express');
const bodyParser = require('body-parser');
const fs = require('fs');
const cors = require('cors');
const app = express();
const PORT = 9000;

// Habilitar CORS para todas as origens (para desenvolvimento)
app.use(cors({
   origin: '*'  // Permite qualquer origem, ajuste para produção conforme necessário
}));

app.use(bodyParser.json());

// Caminho para o arquivo JSON das impressoras
const printersFilePath = './assets/js/printers.json';

// Rota para obter as impressoras
app.get('/impressoras', (req, res) => {
    fs.readFile(printersFilePath, (err, data) => {
        if (err) {
            return res.status(500).send('Erro ao carregar as impressoras.');
        }
        res.json(JSON.parse(data));
    });
});

// Rota para adicionar uma nova impressora
app.post('/impressoras', (req, res) => {
    const newPrinter = req.body;

    fs.readFile(printersFilePath, (err, data) => {
        if (err) {
            return res.status(500).send('Erro ao carregar as impressoras.');
        }
        const printers = JSON.parse(data);
        printers.push(newPrinter);

        fs.writeFile(printersFilePath, JSON.stringify(printers, null, 2), (err) => {
            if (err) {
                return res.status(500).send('Erro ao salvar a impressora.');
            }
            res.status(200).send('Impressora adicionada com sucesso.');
        });
    });
});

// Rota para editar uma impressora
app.put('/impressoras/:id', (req, res) => {
    const updatedPrinter = req.body;
    const printerId = parseInt(req.params.id, 10);

    fs.readFile(printersFilePath, (err, data) => {
        if (err) {
            return res.status(500).send('Erro ao carregar as impressoras.');
        }

        let printers = JSON.parse(data);
        const printerIndex = printers.findIndex(printer => printer.id === printerId);

        if (printerIndex !== -1) {
            printers[printerIndex] = updatedPrinter;

            fs.writeFile(printersFilePath, JSON.stringify(printers, null, 2), (err) => {
                if (err) {
                    return res.status(500).send('Erro ao salvar a impressora.');
                }
                res.status(200).send('Impressora editada com sucesso.');
            });
        } else {
            res.status(404).send('Impressora não encontrada.');
        }
    });
});

// Rota para remover uma impressora
app.delete('/impressoras/:id', (req, res) => {
    const printerId = parseInt(req.params.id, 10);

    fs.readFile(printersFilePath, (err, data) => {
        if (err) {
            return res.status(500).send('Erro ao carregar as impressoras.');
        }

        let printers = JSON.parse(data);
        printers = printers.filter(printer => printer.id !== printerId);

        fs.writeFile(printersFilePath, JSON.stringify(printers, null, 2), (err) => {
            if (err) {
                return res.status(500).send('Erro ao salvar a lista de impressoras.');
            }
            res.status(200).send('Impressora removida com sucesso.');
        });
    });
});

// Inicia o servidor
app.listen(PORT, () => {
    console.log(`Servidor rodando em http://localhost:${PORT}`);
});
