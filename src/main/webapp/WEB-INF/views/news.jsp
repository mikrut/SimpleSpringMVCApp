<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <title>News</title>
    <link
      rel="stylesheet"
      href="https://unpkg.com/material-components-web@latest/dist/material-components-web.min.css">
    <script src="http://material-components-web.appspot.com/assets/material-components-web.css.js" charset="utf-8"></script>

    <style>
      .material-icons {
        text-decoration: none;
        cursor: pointer;
        padding-right: 8px;
      }

      /* Ensure layout covers the entire screen. */
      html {
        height: 100%;
      }
      /* Stack toolbar and content on top of each other. */
      .demo-body {
        display: flex;
        flex-direction: column;
        padding: 0;
        margin: 0;
        box-sizing: border-box;
        height: 100%;
      }

      .mdc-toolbar__title {
          cursor: pointer;
        }
    </style>

    <script src="https://code.jquery.com/jquery-3.2.0.min.js"></script>
</head>

<body class="mdc-typography demo-body">
    <header class="mdc-toolbar mdc-toolbar--fixed">
      <section class="mdc-toolbar__section mdc-toolbar__section--align-start">
        <a class="material-icons" id="arrow_back">arrow_back</a>
        <span class="mdc-toolbar__title" id="toolbar_title">News</span>
      </section>
      <section class="mdc-toolbar__section mdc-toolbar__section--align-end" role="toolbar">
        <a class="material-icons" aria-label="Delete" alt="Delete" id="toolbar_delete">delete</a>
        <a class="material-icons" aria-label="Edit" alt="Edit" id="toolbar_edit">edit</a>
      </section>
    </header>

    <div class="demo-content mdc-toolbar-fixed-adjust">
        <div class="mdc-card">
          <section class="mdc-card__primary">
            <h1 class="mdc-card__title mdc-card__title--large"><c:out value="${news.getName()}"/></h1>
          </section>
          <section class="mdc-card__supporting-text">
            <c:out value="${news.getContents()}"/>
          </section>
          <section class="mdc-card__actions">
            <button class="mdc-button mdc-button--compact mdc-card__action" id="button_edit">Edit</button>
            <button class="mdc-button mdc-button--compact mdc-card__action" id="button_delete">Delete</button>
          </section>
        </div>
    </div>

    <script type="text/javascript">
        document.getElementById("arrow_back").onclick = function() {
            window.location.href = "/category/${news.getCategory().getId()}/news"
        }

        document.getElementById("toolbar_title").onclick = function() {
            window.location.href = "/";
        };

        edit_buttons = [
            document.getElementById("toolbar_edit"),
            document.getElementById("button_edit")
        ];

        edit_buttons.forEach(function(element){
            element.onclick = function() {

            };
        });

        delete_buttons = [
            document.getElementById("toolbar_delete"),
            document.getElementById("button_delete")
        ];

        delete_buttons.forEach(function(element){
            element.onclick = function() {
                $.ajax({
                    url: '/category/${news.getCategory().getId()}/news/${news.getId()}',
                    type: 'DELETE',
                    success: function(result) {
                        window.location.href = "/category/${news.getCategory().getId()}/news"
                    },
                    error(jqXHR, textStatus, errorThrown) {
                        console.error(jqXHR);
                        console.error(textStatus);
                        console.error(errorThrown);
                    }
                });
            };
        });
    </script>
</body>
</html>