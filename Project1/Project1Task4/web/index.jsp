<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%= request.getAttribute("doctype") %>

<html>
    <head>
        <title>Child Art</title>
        <link rel="stylesheet" href="CSS/task4.css">
    </head>
    <body class="index">
        <h1 class="title">Search the</h1>
        <a href="http://library.illinoisstate.edu/icca/copyright"><h1 class="title"> International Collection of Child Art </h1></a>
        <h1 class="title">for pictures for any subject.</h1>
        
        <form action="childArt" method="GET">
            <div class="search">   <input class="word" type="text" name="searchWord" value="" placeholder="Please input a subject." /></div>
            <div class="search"><input class="submit" type="submit" value="Search" /></div>
        </form>
    </body>
</html>

