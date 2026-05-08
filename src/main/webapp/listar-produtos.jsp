<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Meus Produtos</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
</head>
<body>
    <nav class="navbar navbar-dark bg-dark">
        <div class="container">
            <span class="navbar-brand">Sistema de Produtos</span>
            <div class="d-flex">
                <span class="navbar-text me-3">Olá, ${usuarioLogado.nome}</span>
                <a href="${pageContext.request.contextPath}/LogoutServlet" class="btn btn-outline-light btn-sm">Sair</a>
            </div>
        </div>
    </nav>
    
    <div class="container mt-4">
        <div class="row mb-3">
            <div class="col">
                <h2>Meus Produtos</h2>
            </div>
            <div class="col text-end">
                <a href="form-produto.jsp" class="btn btn-success">
                    <i class="bi bi-plus-circle"></i> Novo Produto
                </a>
            </div>
        </div>
        
        <div class="table-responsive">
            <table class="table table-striped table-hover">
                <thead class="table-dark">
                    <tr>
                        <th>ID</th>
                        <th>Nome</th>
                        <th>Descrição</th>
                        <th>Preço</th>
                        <th>Estoque</th>
                        <th>Ações</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${produtos}" var="produto">
                        <tr>
                            <td>${produto.id}</td>
                            <td>${produto.nome}</td>
                            <td>${produto.descricao}</td>
                            <td>R$ ${produto.preco}</td>
                            <td>${produto.quantidadeEstoque}</td>
                            <td>
                                <a href="EditarProdutoServlet?id=${produto.id}" class="btn btn-sm btn-warning">
                                    <i class="bi bi-pencil"></i>
                                </a>
                                <a href="ExcluirProdutoServlet?id=${produto.id}" 
                                   class="btn btn-sm btn-danger"
                                   onclick="return confirm('Tem certeza?')">
                                    <i class="bi bi-trash"></i>
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty produtos}">
                        <tr>
                            <td colspan="6" class="text-center">Nenhum produto cadastrado</td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </div>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>