<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Items - Inventory Management</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand" href="#">Inventory Management</a>
            <div class="navbar-nav ms-auto">
                <a class="nav-link" href="logout">Logout</a>
            </div>
        </div>
    </nav>

    <div class="container mt-4">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2>Items</h2>
            <a href="items/new" class="btn btn-primary">Add New Item</a>
        </div>
        
        <!-- Search Form -->
        <div class="card mb-4">
            <div class="card-header bg-light">
                <h5 class="mb-0">Search Items</h5>
            </div>
            <div class="card-body">
                <form action="items" method="get" class="row g-3">
                    <div class="col-md-6">
                        <label for="description" class="form-label">Description</label>
                        <input type="text" class="form-control" id="description" name="description" value="${searchDescription}">
                    </div>
                    
                    <div class="col-md-3">
                        <label for="minPrice" class="form-label">Min Price</label>
                        <input type="number" step="0.01" class="form-control" id="minPrice" name="minPrice" value="${searchMinPrice}">
                    </div>
                    
                    <div class="col-md-3">
                        <label for="maxPrice" class="form-label">Max Price</label>
                        <input type="number" step="0.01" class="form-control" id="maxPrice" name="maxPrice" value="${searchMaxPrice}">
                    </div>
                    
                    <div class="col-md-3">
                        <label for="fromDate" class="form-label">From Date</label>
                        <input type="date" class="form-control" id="fromDate" name="fromDate" value="${searchFromDate}">
                    </div>
                    
                    <div class="col-md-3">
                        <label for="toDate" class="form-label">To Date</label>
                        <input type="date" class="form-control" id="toDate" name="toDate" value="${searchToDate}">
                    </div>
                    
                    <div class="col-md-3">
                        <label for="minQuantity" class="form-label">Min Quantity</label>
                        <input type="number" class="form-control" id="minQuantity" name="minQuantity" value="${searchMinQuantity}">
                    </div>
                    
                    <div class="col-md-3">
                        <label for="maxQuantity" class="form-label">Max Quantity</label>
                        <input type="number" class="form-control" id="maxQuantity" name="maxQuantity" value="${searchMaxQuantity}">
                    </div>
                    
                    <div class="col-12">
                        <button type="submit" class="btn btn-primary">Search</button>
                        <a href="items" class="btn btn-secondary">Reset</a>
                    </div>
                </form>
            </div>
        </div>
        
        <!-- Results Table -->
        <div class="table-responsive">
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Description</th>
                        <th>Price</th>
                        <th>Expiration Date</th>
                        <th>Quantity</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${items}" var="item">
                        <tr>
                            <td>${item.id}</td>
                            <td>${item.description}</td>
                            <td>${item.price}</td>
                            <td>${item.expirationDate}</td>
                            <td>${item.quantity}</td>
                            <td>
                                <a href="items/edit?id=${item.id}" class="btn btn-sm btn-warning">Edit</a>
                                <a href="items/delete?id=${item.id}" class="btn btn-sm btn-danger" 
                                   onclick="return confirm('Are you sure you want to delete this item?')">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty items}">
                        <tr>
                            <td colspan="6" class="text-center">No items found</td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>