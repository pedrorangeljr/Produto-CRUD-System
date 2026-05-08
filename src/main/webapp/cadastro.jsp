<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Cadastro de Usuário - Sistema de Produtos</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
<style>
.password-strength {
	height: 5px;
	transition: all 0.3s ease;
}

.strength-weak {
	width: 33%;
	background-color: #dc3545;
}

.strength-medium {
	width: 66%;
	background-color: #ffc107;
}

.strength-strong {
	width: 100%;
	background-color: #198754;
}
</style>
</head>
<body class="bg-light">
	<div class="container">
		<div class="row justify-content-center mt-5">
			<div class="col-md-6 col-lg-5">
				<div class="card shadow-lg border-0">
					<div class="card-header bg-primary text-white text-center py-3">
						<h4 class="mb-0">
							<i class="bi bi-person-plus-fill"></i> Criar Nova Conta
						</h4>
					</div>
					<div class="card-body p-4">

						<!-- Exibição de mensagens de erro/sucesso -->
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

						<%
						if (request.getAttribute("sucesso") != null) {
						%>
						<div class="alert alert-success alert-dismissible fade show"
							role="alert">
							<i class="bi bi-check-circle-fill"></i>
							<%=request.getAttribute("sucesso")%>
							<button type="button" class="btn-close" data-bs-dismiss="alert"></button>
						</div>
						<%
						}
						%>

						<form
							action="${pageContext.request.contextPath}/CadastroUsuarioServlet"
							method="post" id="formCadastro" onsubmit="return validarSenha()">

							<div class="mb-3">
								<label for="nome" class="form-label fw-bold"> <i
									class="bi bi-person-badge"></i> Nome Completo
								</label> <input type="text" class="form-control" id="nome" name="nome"
									required minlength="3" maxlength="100"
									placeholder="Digite seu nome completo">
								<div class="form-text">Mínimo 3 caracteres</div>
							</div>

							<div class="mb-3">
								<label for="email" class="form-label fw-bold"> <i
									class="bi bi-envelope"></i> E-mail
								</label> <input type="email" class="form-control" id="email"
									name="email" required placeholder="seu@email.com">
								<div class="form-text">Usaremos este e-mail para login</div>
							</div>

							<div class="mb-3">
								<label for="senha" class="form-label fw-bold"> <i
									class="bi bi-key"></i> Senha
								</label>
								<div class="input-group">
									<input type="password" class="form-control" id="senha"
										name="senha" required minlength="6"
										placeholder="Mínimo 6 caracteres"
										onkeyup="avaliarForcaSenha()">
									<button class="btn btn-outline-secondary" type="button"
										onclick="toggleSenha('senha')">
										<i class="bi bi-eye-slash" id="toggleIconSenha"></i>
									</button>
								</div>
								<div class="mt-2">
									<div class="password-strength rounded" id="forcaSenhaBar"></div>
									<small class="text-muted" id="forcaSenhaTexto"></small>
								</div>
							</div>

							<div class="mb-4">
								<label for="confirmacaoSenha" class="form-label fw-bold">
									<i class="bi bi-shield-lock"></i> Confirmar Senha
								</label>
								<div class="input-group">
									<input type="password" class="form-control"
										id="confirmacaoSenha" name="confirmacaoSenha" required>
									<button class="btn btn-outline-secondary" type="button"
										onclick="toggleSenha('confirmacaoSenha')">
										<i class="bi bi-eye-slash" id="toggleIconConfirm"></i>
									</button>
								</div>
								<div class="form-text" id="matchSenhaMsg"></div>
							</div>

							<div class="d-grid gap-2">
								<button type="submit" class="btn bg-primary btn-lg">
									<i class="bi bi-check-circle"></i> Cadastrar
								</button>
								<a href="${pageContext.request.contextPath}/index.jsp"
									class="btn btn-outline-secondary"> <i
									class="bi bi-arrow-left"></i> Voltar para Login
								</a>
							</div>
						</form>

						<hr class="my-4">

						<div class="text-center">
							<p class="text-muted">
								Ao se cadastrar, você concorda com nossos <a href="#"
									data-bs-toggle="modal" data-bs-target="#termosModal">Termos
									de Uso</a>
							</p>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- Modal de Termos de Uso -->
	<div class="modal fade" id="termosModal" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header bg-success text-white">
					<h5 class="modal-title">Termos de Uso</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
				</div>
				<div class="modal-body">
					<p>Este sistema é para fins de avaliação técnica. Sua senha é
						criptografada e nunca compartilhada.</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-bs-dismiss="modal">Fechar</button>
				</div>
			</div>
		</div>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
	<script>
		// Avaliar força da senha
		function avaliarForcaSenha() {
			let senha = document.getElementById('senha').value;
			let barra = document.getElementById('forcaSenhaBar');
			let texto = document.getElementById('forcaSenhaTexto');

			if (senha.length === 0) {
				barra.className = 'password-strength';
				texto.innerHTML = '';
				return;
			}

			let forca = 0;
			if (senha.length >= 8)
				forca++;
			if (senha.match(/[a-z]+/))
				forca++;
			if (senha.match(/[A-Z]+/))
				forca++;
			if (senha.match(/[0-9]+/))
				forca++;
			if (senha.match(/[$@#&!]+/))
				forca++;

			if (forca <= 2) {
				barra.className = 'password-strength strength-weak';
				texto.innerHTML = '⚠️ Senha fraca';
				texto.style.color = '#dc3545';
			} else if (forca <= 4) {
				barra.className = 'password-strength strength-medium';
				texto.innerHTML = '⚠️ Senha média';
				texto.style.color = '#ffc107';
			} else {
				barra.className = 'password-strength strength-strong';
				texto.innerHTML = '✓ Senha forte';
				texto.style.color = '#198754';
			}
		}

		// Validar confirmação de senha
		function validarSenha() {
			let senha = document.getElementById('senha').value;
			let confirmacao = document.getElementById('confirmacaoSenha').value;

			if (senha !== confirmacao) {
				document.getElementById('matchSenhaMsg').innerHTML = '<span style="color: red;">✗ As senhas não coincidem</span>';
				return false;
			}

			if (senha.length < 6) {
				alert('A senha deve ter no mínimo 6 caracteres');
				return false;
			}

			return true;
		}

		// Verificar match em tempo real
		document
				.getElementById('confirmacaoSenha')
				.addEventListener(
						'keyup',
						function() {
							let senha = document.getElementById('senha').value;
							let confirmacao = this.value;
							let msg = document.getElementById('matchSenhaMsg');

							if (senha === confirmacao && confirmacao.length > 0) {
								msg.innerHTML = '<span style="color: green;">✓ Senhas coincidem</span>';
							} else if (confirmacao.length > 0) {
								msg.innerHTML = '<span style="color: red;">✗ As senhas não coincidem</span>';
							} else {
								msg.innerHTML = '';
							}
						});

		// Alternar visibilidade da senha
		function toggleSenha(campoId) {
			let campo = document.getElementById(campoId);
			let iconId = campoId === 'senha' ? 'toggleIconSenha'
					: 'toggleIconConfirm';
			let icon = document.getElementById(iconId);

			if (campo.type === 'password') {
				campo.type = 'text';
				icon.classList.remove('bi-eye-slash');
				icon.classList.add('bi-eye');
			} else {
				campo.type = 'password';
				icon.classList.remove('bi-eye');
				icon.classList.add('bi-eye-slash');
			}
		}
	</script>
</body>
</html>