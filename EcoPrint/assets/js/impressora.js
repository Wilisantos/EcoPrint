document.addEventListener("DOMContentLoaded", () => {
    const printerForm = document.getElementById("add-printer-form");
    const printerList = document.getElementById("printer-list");
    let savedPrinters = [];

    // Função para carregar as impressoras
    async function carregarTabelaImpressora() {
        try {
            const response = await fetch('http://localhost:9000/impressoras');
            if (!response.ok) throw new Error("Erro ao carregar as impressoras.");
            savedPrinters = await response.json();
            renderPrinterTable();
        } catch (error) {
            console.error("Erro:", error.message);
        }
    }

    // Função para renderizar a tabela de impressoras
    function renderPrinterTable() {
        printerList.innerHTML = "";
        savedPrinters.forEach((printer, index) => {
            const row = document.createElement("tr");
            row.innerHTML = `
                <td>${printer.name}</td>
                <td>${printer.model}</td>
                <td>
                    <button class="btn btn-info btn-sm" onclick="showPrinterDetails(${index})">Editar</button>
                    <button class="btn btn-danger btn-sm" onclick="removePrinter(${index})">Remover</button>
                </td>
            `;
            printerList.appendChild(row);
        });

        if (savedPrinters.length === 0) {
            const emptyRow = document.createElement("tr");
            emptyRow.innerHTML = `<td colspan="3" class="text-center">Nenhuma impressora disponível.</td>`;
            printerList.appendChild(emptyRow);
        }
    }

    // Função para exibir detalhes e editar impressora
    window.showPrinterDetails = (index) => {
        const printer = savedPrinters[index];
        const newName = prompt("Novo nome da impressora:", printer.name);
        const newModel = prompt("Novo modelo da impressora:", printer.model);
        const newPages = prompt("Novas páginas impressas:", printer.pages || 0);

        if (newName && newModel && !isNaN(newPages)) {
            const updatedPrinter = {
                ...printer,
                name: newName,
                model: newModel,
                pages: parseInt(newPages, 10),
            };
            editPrinter(updatedPrinter);
        }
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
                alert("Impressora editada com sucesso!");
                carregarTabelaImpressora();
            } else {
                alert("Erro ao editar impressora.");
            }
        } catch (error) {
            console.error("Erro ao editar impressora:", error);
        }
    }

    // Função para remover impressora
    window.removePrinter = (index) => {
        const printerId = savedPrinters[index].id;
        if (confirm("Tem certeza que deseja remover esta impressora?")) {
            deletePrinter(printerId);
        }
    };

    // Função para remover impressora no servidor
    async function deletePrinter(printerId) {
        try {
            const response = await fetch(`http://localhost:9000/impressoras/${printerId}`, {
                method: 'DELETE',
            });
            if (response.ok) {
                alert("Impressora removida com sucesso!");
                carregarTabelaImpressora();
            } else {
                alert("Erro ao remover impressora.");
            }
        } catch (error) {
            console.error("Erro ao remover impressora:", error);
        }
    }

    // Função para adicionar impressora
    printerForm.addEventListener("submit", (event) => {
        event.preventDefault();

        const printerName = document.getElementById("printer-name").value;
        const printerModel = document.getElementById("printer-model").value;
        const newPrinter = { name: printerName, model: printerModel, pages: 0, id: Date.now() };

        addPrinter(newPrinter);
        printerForm.reset();
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
                alert("Impressora adicionada com sucesso!");
                carregarTabelaImpressora();
            } else {
                alert("Erro ao adicionar impressora.");
            }
        } catch (error) {
            console.error("Erro ao adicionar impressora:", error);
        }
    }

    // Carregar a tabela de impressoras ao carregar a página
    carregarTabelaImpressora();
});
