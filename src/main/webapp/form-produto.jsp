<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title><c:if test="${empty produto}">Novo Produto</c:if> <c:if
		test="${not empty produto}">Editar Produto</c:if> - Sistema de
	Produtos</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
<style>
.preview-image {
	max-width: 200px;
	max-height: 200px;
	border: 1px solid #ddd;
	border-radius: 8px;
	padding: 5px;
}

.required:after {
	content: " *";
	color: red;
}
</style>
</head>
<body class="bg-light">
	<nav class="navbar navbar-dark bg-dark">
		<div class="container">
			<span class="navbar-brand"> <i class="bi bi-box-seam"></i>
				Sistema de Produtos
			</span>
			<div class="d-flex">
				<span class="navbar-text me-3"> <i
					class="bi bi-person-circle"></i> ${usuarioLogado.nome}
				</span> <a href="${pageContext.request.contextPath}/LogoutServlet"
					class="btn btn-outline-light btn-sm"> <i
					class="bi bi-box-arrow-right"></i> Sair
				</a>
			</div>
		</div>
	</nav>

	<div class="container mt-4">
		<div class="row justify-content-center">
			<div class="col-md-8 col-lg-6">
				<div class="card shadow">
					<div class="card-header bg-primary text-white">
						<h4 class="mb-0">
							<c:if test="${empty produto}">
								<i class="bi bi-plus-circle"></i> Cadastrar Novo Produto
                            </c:if>
							<c:if test="${not empty produto}">
								<i class="bi bi-pencil-square"></i> Editar Produto #${produto.id}
                            </c:if>
						</h4>
					</div>
					<div class="card-body p-4">

						<!-- Mensagens de erro -->
						<%
						if (request.getAttribute("erro") != null) {
						%>
						<div class="alert alert-danger alert-dismissible fade show"
							role="alert">
							<i class="bi bi-exclamation-triangle-fill"></i>
							<%=request.getAttribute("erro")%>
							<button type="button" class="btn-close" data-bs-dismiss="alert"></button>
						</div>
						<%
						}
						%>

						<form
							action="${pageContext.request.contextPath}/NovoProdutoServlet"
							method="post" id="formProduto"
							onsubmit="return validarFormulario()">

							<!-- Campo oculto para ID (quando for edição) -->
							<c:if test="${not empty produto}">
								<input type="hidden" name="id" value="${produto.id}">
								<input type="hidden" name="acao" value="editar">
							</c:if>

							<!-- Nome do Produto -->
							<div class="mb-3">
								<label for="nome" class="form-label fw-bold required"> <i
									class="bi bi-tag"></i> Nome do Produto
								</label> <input type="text" class="form-control" id="nome" name="nome"
									value="${produto.nome}" required minlength="3" maxlength="150"
									placeholder="Ex: Smartphone XYZ">
								<div class="form-text">Mínimo 3 caracteres, máximo 150</div>
							</div>

							<!-- Descrição -->
							<div class="mb-3">
								<label for="descricao" class="form-label fw-bold"> <i
									class="bi bi-file-text"></i> Descrição
								</label>
								<textarea class="form-control" id="descricao" name="descricao"
									rows="4" placeholder="Descreva o produto...">${produto.descricao}</textarea>
								<div class="form-text">Descrição detalhada do produto
									(opcional)</div>
							</div>

							<div class="row">
								<!-- Preço -->
								<div class="col-md-6 mb-3">
									<label for="preco" class="form-label fw-bold required">
										<i class="bi bi-currency-dollar"></i> Preço (R$)
									</label>
									<div class="input-group">
										<span class="input-group-text">R$</span> <input type="text"
											class="form-control" id="preco" name="preco"
											value="${produto.preco}" required placeholder="0,00">
									</div>
									<div class="form-text">Use ponto como separador decimal
										(ex: 99.90)</div>
								</div>

								<!-- Estoque -->
								<div class="col-md-6 mb-3">
									<label for="quantidadeEstoque"
										class="form-label fw-bold required"> <i
										class="bi bi-boxes"></i> Quantidade em Estoque
									</label> <input type="number" class="form-control"
										id="quantidadeEstoque" name="quantidadeEstoque"
										value="${produto.quantidadeEstoque}" required min="0" step="1"
										placeholder="0">
									<div class="form-text">Apenas números inteiros</div>
								</div>
							</div>

							<!-- Preview (demonstração visual) -->
							<div class="alert alert-info mt-3" id="previewArea"
								style="display: none;">
								<strong><i class="bi bi-eye"></i> Pré-visualização:</strong>
								<div id="previewNome"></div>
								<div id="previewPreco"></div>
							</div>

							<hr class="my-4">

							<div class="d-grid gap-2 d-md-flex justify-content-md-end">
								<a
									href="${pageContext.request.contextPath}/ListarProdutosServlet"
									class="btn btn-secondary"> <i class="bi bi-arrow-left"></i>
									Cancelar
								</a>
								<button type="submit" class="btn btn-primary">
									<i class="bi bi-check-lg"></i>
									<c:if test="${empty produto}">Cadastrar Produto</c:if>
									<c:if test="${not empty produto}">Salvar Alterações</c:if>
								</button>
							</div>
						</form>
					</div>
				</div>

				<!-- Dicas para cadastro -->
				<div class="card mt-3 bg-light">
					<div class="card-body">
						<h6 class="card-title">
							<i class="bi bi-lightbulb"></i> Dicas para um bom cadastro:
						</h6>
						<ul class="mb-0">
							<li>Use um nome claro e descritivo</li>
							<li>Preços competitivos aumentam as vendas</li>
							<li>Mantenha o estoque atualizado</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
	<script>
		// Máscara para preço (formatação em tempo real)
		document.getElementById('preco').addEventListener('input', function(e) {
			let value = e.target.value;
			// Remove tudo que não for número ou ponto
			value = value.replace(/[^\d.]/g, '');

			// Garante apenas um ponto decimal
			let parts = value.split('.');
			if (parts.length > 2) {
				value = parts[0] + '.' + parts.slice(1).join('');
			}

			// Limita a 2 casas decimais
			if (parts.length === 2 && parts[1].length > 2) {
				value = parts[0] + '.' + parts[1].substring(0, 2);
			}

			e.target.value = value;
		});

		// Validação do formulário
		function validarFormulario() {
			let nome = document.getElementById('nome').value.trim();
			let preco = document.getElementById('preco').value;
			let quantidade = document.getElementById('quantidadeEstoque').value;

			if (nome.length < 3) {
				alert('O nome do produto deve ter no mínimo 3 caracteres');
				return false;
			}

			if (nome.length > 150) {
				alert('O nome do produto deve ter no máximo 150 caracteres');
				return false;
			}

			if (!preco || parseFloat(preco) <= 0) {
				alert('Informe um preço válido maior que zero');
				return false;
			}

			if (parseFloat(preco) > 999999.99) {
				alert('Preço muito alto. Valor máximo permitido: R$ 999.999,99');
				return false;
			}

			if (quantidade === '' || parseInt(quantidade) < 0) {
				alert('A quantidade em estoque deve ser um número maior ou igual a zero');
				return false;
			}

			if (!Number.isInteger(parseFloat(quantidade))) {
				alert('A quantidade em estoque deve ser um número inteiro');
				return false;
			}

			return true;
		}

		// Preview ao digitar (profissional)
		let nomeInput = document.getElementById('nome');
		let precoInput = document.getElementById('preco');
		let previewArea = document.getElementById('previewArea');

		function atualizarPreview() {
			let nome = nomeInput.value.trim();
			let preco = precoInput.value;

			if (nome !== '' && preco !== '') {
				document.getElementById('previewNome').innerHTML = '<strong>Nome:</strong> '
						+ nome;
				document.getElementById('previewPreco').innerHTML = '<strong>Preço:</strong> R$ '
						+ (parseFloat(preco) || 0).toFixed(2);
				previewArea.style.display = 'block';
			} else {
				previewArea.style.display = 'none';
			}
		}

		nomeInput.addEventListener('keyup', atualizarPreview);
		precoInput.addEventListener('keyup', atualizarPreview);

		// Evitar envio duplicado (proteção contra clique duplo)
		document
				.getElementById('formProduto')
				.addEventListener(
						'submit',
						function() {
							let btn = this
									.querySelector('button[type="submit"]');
							btn.disabled = true;
							btn.innerHTML = '<span class="spinner-border spinner-border-sm"></span> Processando...';
						});
	</script>
</body>
</html>