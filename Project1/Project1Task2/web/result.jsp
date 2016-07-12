<%-- 
    Document   : result
    Created on : Sep 22, 2015, 2:42:19 PM
    Author     : Yilong Chang
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Big Integer Calculator</title>
        <link rel="stylesheet" href="CSS/task2.css">
    </head>
    <body class="index">
        <h1 class="title">Welcome to the Big Integer Calculator!</h1>

        <h1 class="title"><%= request.getAttribute("result")%></h1>
        <div class="calc">
            <form action="BigCalc" method="GET">
                <div class="numbers">
                    <input class="number1" type="text" name ="firstNum" placeholder="Please input a number" value=<%= request.getAttribute("firstNum")%> required autofocus/>
                    <input class="number2" type="text" name ="secondNum" placeholder="Please input a number" value=<%= request.getAttribute("secondNum")%> required autofocus/></div>
                <select name="operations" class="operations">
                    <option name="add" value ="1" style="font-family: sans-serif">Add</option>
                    <option name="multiply" value ="2">Multiply</option>
                    <option name="relativelyPrime" value="3">Relatively Prime</option>
                    <option name="mod" value="4">Mod</option>
                    <option name="modInverse" value ="5">Mod Inverse</option>
                    <option name="power" value ="6">Power</option>            
                </select>
            <input class="submit" type="submit" value="Compute"/>
    </form></div>
</body>
</html>
