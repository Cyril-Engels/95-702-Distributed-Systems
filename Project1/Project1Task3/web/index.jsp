<%-- 
    Document   : index
    Created on : Sep 22, 2015, 7:18:13 PM
    Author     : changyilong
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- Get the doc type -->
<%= request.getAttribute("doctype") %>
<html>
    <head>
        <title>Palindrome</title>
        <link rel="stylesheet" href="CSS/task3.css">
    </head>
    <body class="index">
        <form action="judgePalindrome" method="POST">
            <div><h1 class="title"><label for="letter">Please input something to see if it is palindrome.</label></h1></div>
            <div class="search"><input class="word" type="text" name="word" value=""/></div>
            <div class="search"><input class="submit" type="submit" value="submit"/></div>
        </form>
    </body>
</html>


