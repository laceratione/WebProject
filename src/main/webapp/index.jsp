<?xml version="1.0" encoding="UTF-8"?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="style.css" rel="stylesheet">
    </head>
    <body>
        <div class="main">
            <div id="elem">
                Поисковик "Крестьянин"
            </div>

            <br>

            <div align="center">
                <form action="servlet" method="POST">
                    <input name="word" placeholder="Input word" type="search">
                    <button type="submit">Find</button>
                 </form>
            </div>

        </div>
    </body>
</html>