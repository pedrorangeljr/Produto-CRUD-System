<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Login - Sistema de Produtos</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/custom.css"
	rel="stylesheet">
</head>
<body class="bg-light">
	<div class="container">
		<div class="row justify-content-center mt-5">
			<div class="col-md-6 col-lg-4">
				<div class="card shadow">
					<div class="card-header bg-primary text-white">
						<h4 class="mb-0">Login</h4>
					</div>
					<div class="card-body">
						<%
						if (request.getAttribute("erro") != null) {
						%>
						<div class="alert alert-danger alert-dismissible fade show"
							role="alert">
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
							<%=request.getAttribute("sucesso")%>
							<button type="button" class="btn-close" data-bs-dismiss="alert"></button>
						</div>
						<%
						}
						%>

						<form action="${pageContext.request.contextPath}/LoginServlet"
							method="post">
							<div class="mb-3">
								<label for="email" class="form-label">Email</label> <input
									type="email" class="form-control" id="email" name="email"
									required>
							</div>
							<div class="mb-3">
								<label for="senha" class="form-label">Senha</label> <input
									type="password" class="form-control" id="senha" name="senha"
									required>
							</div>
							<button type="submit" class="btn btn-primary w-100">Entrar</button>
						</form>
						<hr>
						<p class="text-center mb-0">
							Não tem conta? <a
								href="${pageContext.request.contextPath}/cadastro.jsp">Cadastre-se</a>
						</p>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>