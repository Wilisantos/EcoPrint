document.addEventListener("DOMContentLoaded", () => {
    const insumoForm = document.getElementById("add-insumo-form");
    const insumoList = document.getElementById("insumo-list");
    let savedInsumos = [];

    // Função para carregar os insumos
    async function carregarTabelaInsumo() {
        try {
            const response = await fetch('http://localhost:9000/insumos');
            if (!response.ok) throw new Error("Erro ao carregar os insumos.");
            const data = await response.json();
            savedInsumos = data.content || []; // Ajustado para lidar com paginação
            renderInsumoTable();
        } catch (error) {
            console.error("Erro:", error.message);
        }
    }

    // Função para renderizar a tabela de insumos
    function renderInsumoTable() {
        insumoList.innerHTML = "";
        savedInsumos.forEach((insumo, index) => {
            const row = document.createElement("tr");
            row.innerHTML = `
                <td>${insumo.tipoInsumo}</td>
                <td>${insumo.descricao}</td>
                <td>${insumo.quantidade}</td>
                <td>
                    <button class="btn btn-info btn-sm" onclick="showInsumoDetails(${index})">Editar</button>
                    <button class="btn btn-danger btn-sm" onclick="removeInsumo(${index})">Remover</button>
                </td>
            `;
            insumoList.appendChild(row);
        });

        if (savedInsumos.length === 0) {
            const emptyRow = document.createElement("tr");
            emptyRow.innerHTML = `<td colspan="4" class="text-center">Nenhum insumo disponível.</td>`;
            insumoList.appendChild(emptyRow);
        }
    }

    // Função para exibir detalhes e editar insumo
    window.showInsumoDetails = (index) => {
        const insumo = savedInsumos[index];
        const newTipo = prompt("Novo tipo de insumo:", insumo.tipoInsumo);
        const newDescricao = prompt("Nova descrição:", insumo.descricao);
        const newQuantidade = prompt("Nova quantidade:", insumo.quantidade);

        if (newTipo && newDescricao && !isNaN(newQuantidade)) {
            const updatedInsumo = {
                ...insumo,
                tipoInsumo: newTipo,
                descricao: newDescricao,
                quantidade: parseInt(newQuantidade, 10),
            };
            editInsumo(updatedInsumo);
        }
    };

    // Função para editar insumo no servidor
    async function editInsumo(updatedInsumo) {
        try {
            const response = await fetch(`http://localhost:9000/insumos/${updatedInsumo.id}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(updatedInsumo),
            });
            if (response.ok) {
                alert("Insumo editado com sucesso!");
                carregarTabelaInsumo();
            } else {
                alert("Erro ao editar insumo.");
            }
        } catch (error) {
            console.error("Erro ao editar insumo:", error);
        }
    }

    // Função para remover insumo
    window.removeInsumo = (index) => {
        const insumoId = savedInsumos[index].id;
        if (confirm("Tem certeza que deseja remover este insumo?")) {
            deleteInsumo(insumoId);
        }
    };

    // Função para remover insumo no servidor
    async function deleteInsumo(insumoId) {
        try {
            const response = await fetch(`http://localhost:9000/insumos/${insumoId}`, {
                method: 'DELETE',
            });
            if (response.ok) {
                alert("Insumo removido com sucesso!");
                carregarTabelaInsumo();
            } else {
                alert("Erro ao remover insumo.");
            }
        } catch (error) {
            console.error("Erro ao remover insumo:", error);
        }
    }

    // Função para adicionar insumo
    insumoForm.addEventListener("submit", (event) => {
        event.preventDefault();

        const insumoTipo = document.getElementById("insumo-tipo").value;
        const insumoDescricao = document.getElementById("insumo-descricao").value;
        const insumoQuantidade = parseInt(document.getElementById("insumo-quantidade").value, 10);
        const newInsumo = { tipoInsumo: insumoTipo, descricao: insumoDescricao, quantidade: insumoQuantidade };

        addInsumo(newInsumo);
        insumoForm.reset();
    });

    // Função para adicionar insumo no servidor
    async function addInsumo(newInsumo) {
        try {
            const response = await fetch('http://localhost:9000/insumos', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(newInsumo),
            });
            if (response.ok) {
                alert("Insumo adicionado com sucesso!");
                carregarTabelaInsumo();
            } else {
                alert("Erro ao adicionar insumo.");
            }
        } catch (error) {
            console.error("Erro ao adicionar insumo:", error);
        }
    }

    // Carregar a tabela de insumos ao carregar a página
    carregarTabelaInsumo();
});
