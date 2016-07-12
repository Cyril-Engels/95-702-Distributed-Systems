<%-- 
    Document   : result
    Created on : Sep 23, 2015, 4:44:08 PM
    Author     : changyilong
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%= request.getAttribute("doctype") %>

<html>
    <head>
        <title>Palindrome</title>
        <link rel="stylesheet" href="CSS/task3.css">
    </head>
   <body class="index">
        <h1 class="title"><%= request.getAttribute("result")%></h1>
        <form action="judgePalindrome" method="POST">
            <div><h1 class="title"><label for="letter">Please input something to see if it is palindrome.</label></h1></div>
            <div class="search"><input class="word" type="text" name="word" value="" /></div>
            <div class="search"><input class="submit" type="submit" value="submit"/></div>
        </form>
    </body>
</html>
