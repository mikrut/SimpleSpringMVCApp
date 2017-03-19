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

      .demo-content {
                padding: 10px;
                padding-top: 25px;
                display: flex;
                height: 100%;
                box-sizing: border-box;
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
        <span class="mdc-toolbar__title" id="toolbar_title">New News</span>
      </section>
      <section class="mdc-toolbar__section mdc-toolbar__section--align-end" role="toolbar">
        <a class="material-icons" aria-label="Save" alt="Save" id="toolbar_save">save</a>
      </section>
    </header>

    <div class="demo-content mdc-toolbar-fixed-adjust">
        <form id="edit_form" autocomplete="off">
            <div class="mdc-textfield">
                <input type="text" id="textfield_name" class="mdc-textfield__input" name="name">
                <label class="mdc-textfield__label" for="textfield_name">Input news name</label>
            </div>
            <div class="mdc-textfield mdc-textfield--multiline">
              <textarea class="mdc-textfield__input"
                        id="multiline_contents"
                        name="contents"
                        rows="8" cols="40"></textarea>
              <label for="multiline_contents" class="mdc-textfield__label">Input news text</label>
            </div>
            <select name="category" size="${categories.size() + 1}" class="mdc-list">
                <option class="mdc-list-item" disabled>Pick a category</option>
                <c:forEach items="${categories}" var="category">
                    <c:choose>
                        <c:when test="${(not empty category_id) && category_id == category.getId()}">
                            <option class="mdc-list-item" selected value="${category.getId()}"><c:out value="${category.getName()}"/></option>
                        </c:when>
                        <c:otherwise>
                            <option class="mdc-list-item" alue="${category.getId()}"><c:out value="${category.getName()}"/></option>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </select><br/>
            <input type="submit" class="mdc-button mdc-button--compact" id="button_save" value="Save" />
        </form>

    </div>

    <script type="text/javascript">
        document.getElementById("arrow_back").onclick = function() {
            <c:choose>
                <c:when test="${not empty category_id}">
                    window.location.href = "/category/${category_id}"
                </c:when>
                <c:otherwise>
                    window.location.href = "/"
                </c:otherwise>
            </c:choose>
        }

        document.getElementById("toolbar_title").onclick = function() {
            window.location.href = "/";
        };

        save_buttons = [
            document.getElementById("toolbar_save"),
            document.getElementById("button_save")
        ]

        $("#edit_form").submit(function(event) {
            console.log($("#edit_form").serialize());
            event.preventDefault();
        });

        $("#toolbar_save").click(function(){
            $("#edit_form").submit();
        });

    </script>
</body>
</html>