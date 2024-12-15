document.addEventListener("DOMContentLoaded", () => {
    const printerForm = document.getElementById("add-printer-form");
    const printerList = document.getElementById("printer-list");
    const printerTipoImpressora = document.getElementById("printer-tipoImpressora");

    let currentEditPrinter = null; // Variável para armazenar a impressora em edição

    // Função para carregar os tipos de impressora
    async function carregarTiposImpressora() {
        try {
            const response = await fetch('http://localhost:9000/tipos-impressora');
            if (!response.ok) {
                const errorText = await response.text();
                console.error("Erro ao carregar os tipos de impressora:", errorText);
                throw new Error("Erro ao carregar os tipos de impressora.");
            }

            const data = await response.json();
            console.log("Tipos de impressora carregados:", data); // Log para verificar os dados
            if (data.content && Array.isArray(data.content)) {
                preencherTiposImpressora(data.content);
            } else {
                console.error("A resposta do servidor não contém o campo 'content' ou não é um array.");
                alert("Erro ao carregar os tipos de impressora.");
            }
        } catch (error) {
            console.error("Erro:", error.message);
        }
    }

    // Função para preencher a lista suspensa com os tipos de impressora
    function preencherTiposImpressora(tipos) {
        // Limpar as opções antigas
        printerTipoImpressora.innerHTML = `<option value="">Selecione o tipo</option>`;
        
        // Adicionar as opções dinâmicas de tipos de impressora
        tipos.forEach((tipo) => {
            const option = document.createElement("option");
            option.value = tipo.id;
            option.textContent = tipo.tipo;
            printerTipoImpressora.appendChild(option);
        });
    }

    // Função para carregar as impressoras do banco de dados
    async function carregarTabelaImpressora() {
        try {
            const response = await fetch('http://localhost:9000/impressoras');
            if (!response.ok) throw new Error("Erro ao carregar as impressoras.");

            const data = await response.json();
            if (data.content && Array.isArray(data.content)) {
                renderPrinterTable(data.content);
            } else {
                console.error("A resposta do servidor não contém o campo 'content' ou não é um array.");
                alert("Erro ao carregar impressoras.");
            }
        } catch (error) {
            console.error("Erro:", error.message);
        }
    }

    // Função para renderizar a tabela com as impressoras
    function renderPrinterTable(printers) {
        printerList.innerHTML = "";
        if (printers.length === 0) {
            const emptyRow = document.createElement("tr");
            emptyRow.innerHTML = `<td colspan="4" class="text-center">Nenhuma impressora disponível.</td>`;
            printerList.appendChild(emptyRow);
        } else {
            printers.forEach((printer) => {
                const row = document.createElement("tr");
                row.innerHTML = `
                    <td>${printer.modelo}</td>
                    <td>${printer.capacidade}</td>
                    <td>${printer.tipoImpressora.tipo}</td>
                    <td>
                        <button class="btn btn-info btn-sm" onclick="showPrinterDetails(${printer.id})">Editar</button>
                        <button class="btn btn-danger btn-sm" onclick="removePrinter(${printer.id})">Remover</button>
                    </td>
                `;
                printerList.appendChild(row);
            });
        }
    }

    // Função para exibir os detalhes de uma impressora e permitir edição
    window.showPrinterDetails = (printerId) => {
        // Encontrar a impressora pelo ID
        fetch(`http://localhost:9000/impressoras/${printerId}`)
            .then(response => response.json())
            .then(printer => {
                currentEditPrinter = printer; // Armazenar a impressora que está sendo editada

                // Preencher o formulário com os dados da impressora
                document.getElementById("printer-name").value = printer.modelo;
                document.getElementById("printer-model").value = printer.modelo;
                document.getElementById("printer-capacidade").value = printer.capacidade;
                document.getElementById("printer-tipoImpressora").value = printer.tipoImpressora.id;
                
                // Alterar o texto do botão de enviar para "Atualizar Impressora"
                document.getElementById("add-printer-form").querySelector('button').textContent = 'Atualizar Impressora';
            })
            .catch(error => {
                console.error("Erro ao buscar impressora:", error);
                alert("Erro ao carregar os detalhes da impressora.");
            });
    };

    // Função para editar impressora no servidor
    async function editPrinter(updatedPrinter) {
        try {
            const response = await fetch(`http://localhost:9000/impressoras/${updatedPrinter.id}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(updatedPrinter),
            });

            if (response.ok) {
                const result = await response.json();
                alert("Impressora atualizada com sucesso!");
                carregarTabelaImpressora(); // Recarregar a tabela após atualização
                resetForm(); // Resetar o formulário
            } else {
                const error = await response.json();
                alert(`Erro ao atualizar impressora: ${error.message}`);
            }
        } catch (error) {
            console.error("Erro ao editar impressora:", error);
            alert("Erro ao editar impressora.");
        }
    }

    // Função para remover impressora
    window.removePrinter = async (printerId) => {
        if (confirm("Tem certeza que deseja remover esta impressora?")) {
            try {
                const response = await fetch(`http://localhost:9000/impressoras/${printerId}`, {
                    method: 'DELETE',
                });
                if (response.ok) {
                    alert("Impressora removida com sucesso!");
                    carregarTabelaImpressora(); // Recarregar a tabela após remoção
                } else {
                    alert("Erro ao remover impressora.");
                }
            } catch (error) {
                console.error("Erro ao remover impressora:", error);
            }
        }
    };

    // Função para adicionar ou atualizar impressora
    printerForm.addEventListener("submit", (event) => {
        event.preventDefault();

        const printerModelo = document.getElementById("printer-model").value;
        const printerCapacidade = document.getElementById("printer-capacidade").value;
        const printerTipoImpressora = document.getElementById("printer-tipoImpressora").value;

        if (printerModelo && !isNaN(printerCapacidade) && !isNaN(printerTipoImpressora)) {
            const newPrinter = {
                modelo: printerModelo,
                capacidade: parseInt(printerCapacidade, 10),
                tipoImpressora: parseInt(printerTipoImpressora, 10),
            };

            if (currentEditPrinter) {
                // Se houver uma impressora em edição, atualiza ela
                newPrinter.id = currentEditPrinter.id;
                editPrinter(newPrinter);
            } else {
                // Caso contrário, adiciona uma nova impressora
                addPrinter(newPrinter);
            }

            printerForm.reset();
        } else {
            alert("Por favor, preencha todos os campos corretamente.");
        }
    });

    // Função para adicionar impressora no servidor
    async function addPrinter(newPrinter) {
        try {
            const response = await fetch('http://localhost:9000/impressoras', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(newPrinter),
            });
            if (response.ok) {
                const result = await response.json();
                console.log(result);
                alert("Impressora adicionada com sucesso!");
                carregarTabelaImpressora(); // Recarregar a tabela após adicionar
            } else {
                const error = await response.json();
                alert(`Erro ao adicionar impressora: ${error.message}`);
            }
        } catch (error) {
            console.error("Erro ao adicionar impressora:", error);
            alert("Erro ao adicionar impressora.");
        }
    }

    // Função para resetar o formulário
    function resetForm() {
        currentEditPrinter = null; // Limpar a impressora em edição
        document.getElementById("add-printer-form").querySelector('button').textContent = 'Adicionar Impressora'; // Restaurar o texto do botão
        printerForm.reset();
    }

    // Carregar os tipos de impressora ao carregar a página
    carregarTiposImpressora();
    // Carregar a tabela de impressoras ao carregar a página
    carregarTabelaImpressora();
});
