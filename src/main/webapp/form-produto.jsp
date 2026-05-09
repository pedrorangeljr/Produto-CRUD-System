<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>
        <c:if test="${empty produto}">Novo Produto</c:if>
        <c:if test="${not empty produto}">Editar Produto</c:if>
    </title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
</head>
<body class="bg-light">
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card shadow">
                    <div class="card-header bg-primary text-white">
                        <h4>
                            <c:if test="${empty produto}">
                                <i class="bi bi-plus-circle"></i> Novo Produto
                            </c:if>
                            <c:if test="${not empty produto}">
                                <i class="bi bi-pencil-square"></i> Editar Produto #${produto.id}
                            </c:if>
                        </h4>
                    </div>
                    <div class="card-body">
                        
                        <!-- 🔑 CRÍTICO: Action correto baseado se tem ID ou não -->
                        <c:choose>
                            <c:when test="${empty produto}">
                                <!-- NOVO: enviar para NovoProdutoServlet -->
                                <form action="${pageContext.request.contextPath}/NovoProdutoServlet" 
                                      method="post" 
                                      id="formProduto">
                            </c:when>
                            <c:otherwise>
                                <!-- EDIÇÃO: enviar para EditarProdutoServlet -->
                                <form action="${pageContext.request.contextPath}/EditarProdutoServlet" 
                                      method="post" 
                                      id="formProduto">
                                <input type="hidden" name="id" value="${produto.id}">
                            </c:otherwise>
                        </c:choose>
                        
                        <div class="mb-3">
                            <label for="nome" class="form-label">Nome do Produto</label>
                            <input type="text" class="form-control" id="nome" name="nome" 
                                   value="${produto.nome}" required>
                        </div>
                        
                        <div class="mb-3">
                            <label for="descricao" class="form-label">Descrição</label>
                            <textarea class="form-control" id="descricao" name="descricao" rows="3">${produto.descricao}</textarea>
                        </div>
                        
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label for="preco" class="form-label">Preço (R$)</label>
                                <input type="number" step="0.01" class="form-control" id="preco" 
                                       name="preco" value="${produto.preco}" required>
                            </div>
                            
                            <div class="col-md-6 mb-3">
                                <label for="quantidadeEstoque" class="form-label">Quantidade em Estoque</label>
                                <input type="number" class="form-control" id="quantidadeEstoque" 
                                       name="quantidadeEstoque" value="${produto.quantidadeEstoque}" required>
                            </div>
                        </div>
                        
                        <div class="d-flex justify-content-end gap-2">
                            <a href="${pageContext.request.contextPath}/ListarProdutosServlet" 
                               class="btn btn-secondary">
                                <i class="bi bi-arrow-left"></i> Cancelar
                            </a>
                            <button type="submit" class="btn btn-primary">
                                <i class="bi bi-check-lg"></i> Salvar
                            </button>
                        </div>
                        
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>