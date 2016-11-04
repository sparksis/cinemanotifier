<?xml version="1.0" encoding="UTF-8"?>
<%@ page language="java" contentType="text/xml; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<urlset xmlns="http://www.sitemaps.org/schemas/sitemap/0.9"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.sitemaps.org/schemas/sitemap/0.9
            http://www.sitemaps.org/schemas/sitemap/0.9/sitemap.xsd">
	<url><loc><c:out value="${baseUrl}" />/</loc></url>
	<c:forEach items="${movies}" var="m">
		<url><loc><c:out value="${baseUrl}" />/movies/<c:out value="${m.cineplexKey}" /></loc></url>
	</c:forEach>
</urlset>
