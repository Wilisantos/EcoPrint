document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById('add-printer-type-form');
    const printerTypeInput = document.getElementById('printer-type');
    const printerTypeTableBody = document.getElementById('printer-type-list');
    let currentEditPrinterType = null; // Variável para armazenar o tipo de impressora em edição

    // Verifique se os elementos existem antes de adicionar os eventos
    if (form && printerTypeInput && printerTypeTableBody) {
        // Função para adicionar um tipo de impressora à tabela
        function addPrinterType(printerType, id) {
            const row = document.createElement('tr');
            row.id = `printer-type-${id}`;  // ID único para cada linha da tabela

            // Criação da célula com o tipo de impressora
            const typeCell = document.createElement('td');
            typeCell.textContent = printerType;
            typeCell.classList.add('type-cell');

            // Botões de Editar e Excluir
            const actionCell = document.createElement('td');
            const editButton = document.createElement('button');
            editButton.classList.add('btn', 'btn-primary');
            editButton.textContent = 'Editar';
            editButton.addEventListener('click', function () {
                enableEditPrinterType(id, printerType);
            });

            const deleteButton = document.createElement('button');
            deleteButton.classList.add('btn', 'btn-danger');
            deleteButton.textContent = 'Excluir';
            deleteButton.addEventListener('click', function () {
                deletePrinterType(id, row);
            });

            actionCell.appendChild(editButton);
            actionCell.appendChild(deleteButton);

            // Adicionando as células ao row (linha)
            row.appendChild(typeCell);
            row.appendChild(actionCell);

            // Adicionando a linha na tabela
            printerTypeTableBody.appendChild(row);
        }

        // Função para ativar o modo de edição
        function enableEditPrinterType(id, printerType) {
            currentEditPrinterType = { id, tipo: printerType };  // Armazena o tipo de impressora em edição
            printerTypeInput.value = printerType;  // Preenche o campo de input com o tipo de impressora
            form.querySelector('button').textContent = 'Atualizar Tipo de Impressora';  // Altera o botão para "Atualizar"
        }

        // Função para salvar a edição no backend
        function editPrinterType(id, newType) {
            fetch(`http://localhost:9000/tipos-impressora/${id}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ tipo: newType }),
            })
            .then(response => response.json())
            .then(data => {
                updatePrinterTypeInTable(id, newType);  // Atualiza a tabela com o novo nome
                resetForm();  // Limpa o formulário
            })
            .catch(error => {
                console.error('Erro ao atualizar tipo de impressora:', error);
                alert("Erro ao salvar a edição.");
            });
        }

        // Função para atualizar o tipo de impressora na tabela
        function updatePrinterTypeInTable(id, newType) {
            const row = document.getElementById(`printer-type-${id}`);
            if (row) {
                row.querySelector('.type-cell').textContent = newType;
            }
        }

        // Função para excluir tipo de impressora com verificação se está vinculado a uma impressora
        function deletePrinterType(id, row) {
            // Verificar se o tipo de impressora está vinculado a alguma impressora
            fetch(`http://localhost:9000/impressoras?tipoImpressora=${id}`)
                .then(response => response.json())
                .then(data => {
                    // Se houver impressoras vinculadas, não permite a exclusão
                    if (data && data.length > 0) {
                        // Exibe o alerta informando que o tipo de impressora não pode ser excluído
                        alert("Este tipo de impressora não pode ser excluído porque está vinculado a uma ou mais impressoras.");
                    } else {
                        // Se não houver impressoras vinculadas, confirmar a exclusão
                        const confirmation = confirm("Você tem certeza que deseja excluir este tipo de impressora?");
                        
                        if (confirmation) {
                            row.remove();  // Remove a linha da tabela

                            fetch(`http://localhost:9000/tipos-impressora/${id}`, {
                                method: 'DELETE',
                            })
                            .then(response => {
                                if (response.ok) {
                                    console.log("Tipo de impressora excluído com sucesso");
                                } else {
                                    alert("Este tipo de impressora não pode ser excluído porque está vinculado a uma ou mais impressoras.");
                                    location.reload();  // Recarrega a página após o alerta
                                    
                                }
                            })
                            .catch(error => {
                                console.error('Erro ao chamar a API para excluir tipo de impressora:', error);
                            });
                        } else {
                            console.log("Exclusão cancelada");
                        }
                    }
                })
                .catch(error => {
                    console.error('Erro ao verificar vinculação de impressoras:', error);
                    alert("Erro ao verificar se o tipo de impressora está vinculado a uma impressora.");
                });
        }

        // Função para carregar os tipos de impressora ao carregar a página
        function loadPrinterTypes() {
            fetch('http://localhost:9000/tipos-impressora')
                .then(response => response.json())
                .then(data => {
                    if (data && data.content) {
                        data.content.forEach(type => {
                            addPrinterType(type.tipo, type.id);
                        });
                    }
                })
                .catch(error => {
                    console.error('Erro ao carregar os tipos de impressora:', error);
                });
        }

        // Adicionar ou atualizar tipo de impressora
        form.addEventListener('submit', function (event) {
            event.preventDefault();
            
            const printerType = printerTypeInput.value.trim();

            if (printerType) {
                if (currentEditPrinterType) {
                    // Se estiver editando, atualize
                    editPrinterType(currentEditPrinterType.id, printerType);
                } else {
                    // Caso contrário, adicione um novo tipo
                    fetch('http://localhost:9000/tipos-impressora', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json',
                        },
                        body: JSON.stringify({ tipo: printerType }),
                    })
                    .then(response => response.json())
                    .then(data => {
                        addPrinterType(printerType, data.id);
                        printerTypeInput.value = ''; // Limpa o campo
                    })
                    .catch(error => {
                        console.error('Erro ao adicionar tipo de impressora:', error);
                        alert("Erro ao adicionar tipo de impressora.");
                    });
                }
            }
        });

        // Função para resetar o formulário
        function resetForm() {
            currentEditPrinterType = null; // Limpar a impressora em edição
            form.querySelector('button').textContent = 'Adicionar Tipo de Impressora'; // Restaurar o texto do botão
            printerTypeInput.value = ""; // Limpar o campo do formulário
        }

        // Carrega os tipos de impressora quando a página for carregada
        loadPrinterTypes();
    } else {
        console.error('Um ou mais elementos não foram encontrados no DOM.');
    }
});
