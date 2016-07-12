<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%= request.getAttribute("doctype")%>

<html>
    <head>
        <title>Child Art</title>
        <link rel="stylesheet" href="CSS/task4.css">
    </head>
    <body class="result">
        <!- If the picture URL is null, display no result -->
        <%if (request.getAttribute("pictureURL") == null) {%>
        <h1 class="title">The <a href="http://library.illinoisstate.edu/icca/copyright">International Collection of Child Art</a></h1>
        <h1 class="title">does not have a picture of <%= request.getAttribute("pictureTag")%>.</h1>
        <% } else {%>
        <h1 class="title">Here is a child's picture of a <%= request.getAttribute("pictureTag")%></h1>
        <h1 class="title">from the <a href="http://library.illinoisstate.edu/icca/copyright">International Collection of Child Art</a></h1>
        <div class="img"><img src="<%= request.getAttribute("pictureURL")%>"></div>
        <%}%>
        <!- Else display the picture -->
        <form action="childArt" method="GET">
            <h2 class="title">Try searching for another subject?</h2>
            <div class="search">
                <input class="word" type="text" name="searchWord" value="" placeholder="Please input a subject." />
            </div>
            <div class="search">
                <input class="submit" type="submit" value="Search" />
            </div>
        </form>
    </body>
</html>

