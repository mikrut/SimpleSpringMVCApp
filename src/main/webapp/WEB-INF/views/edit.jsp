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
      href="/css/material-components-web.min.css">
    <script src="/js/material-components-web.css.js" charset="utf-8"></script>

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

        .mdc-textfield-helptext--validation-msg {
            color: #d50000;
        }
    </style>

    <script src="/js/jquery-3.2.0.min.js"></script>
</head>

<body class="mdc-typography demo-body">
    <header class="mdc-toolbar mdc-toolbar--fixed">
      <section class="mdc-toolbar__section mdc-toolbar__section--align-start">
        <a href="${(not empty category_id) ? '/category/' : '/'}${(not empty category_id) ? category_id : ''}" style="color:#fff;" class="material-icons" id="arrow_back">arrow_back</a>
        <a href="/" style="color:#fff; text-decoration:none" class="mdc-toolbar__title" id="toolbar_title">New News</a>
      </section>
      <section class="mdc-toolbar__section mdc-toolbar__section--align-end" role="toolbar">
        <a class="material-icons" aria-label="Save" alt="Save" id="toolbar_save">save</a>
      </section>
    </header>

    <div class="demo-content mdc-toolbar-fixed-adjust">
        <form id="edit_form" autocomplete="off">
            <div class="mdc-textfield">
                <input type="text" id="textfield_name" class="mdc-textfield__input" name="name" value="<c:out value="${(not empty news) ? news.getName() : ''}"/>" />
                <label class="mdc-textfield__label" for="textfield_name">Input news name</label>
            </div>
            <p class="mdc-textfield-helptext mdc-textfield-helptext--persistent mdc-textfield-helptext--validation-msg"
                     id="helper_name">
            </p>

            <div class="mdc-textfield mdc-textfield--multiline">
              <textarea class="mdc-textfield__input"
                        id="multiline_contents"
                        name="contents"
                        rows="8" cols="40"><c:out value="${(not empty news) ? news.getContents() : ''}"/></textarea>
              <label for="multiline_contents" class="mdc-textfield__label">Input news text</label>
            </div>
            <p class="mdc-textfield-helptext mdc-textfield-helptext--persistent mdc-textfield-helptext--validation-msg"
                                 id="helper_contents">
                        </p>
            <c:if test="${empty news}">
            <select name="category" size="${categories.size() + 1}" class="mdc-list">
                <option class="mdc-list-item" disabled>Pick a category</option>
                <c:forEach items="${categories}" var="category">
                    <option class="mdc-list-item" ${(not empty category_id) && category_id == category.getId() ? 'selected' : ''} value="${category.getId()}"><c:out value="${category.getName()}"/></option>
                </c:forEach>
            </select><br/>
            <p class="mdc-textfield-helptext mdc-textfield-helptext--persistent mdc-textfield-helptext--validation-msg"
                                 id="helper_category">
                        </p>
            </c:if>
            <input type="submit" class="mdc-button mdc-button--compact" id="button_save" value="Save" />
        </form>

    </div>

    <script src="/js/material-components-web.js" charset="utf-8"></script>
    <script type="text/javascript">
        (function() {
            var tfs = document.querySelectorAll(
              '.mdc-textfield:not([data-demo-no-auto-js])'
            );
            for (var i = 0, tf; tf = tfs[i]; i++) {
              mdc.textfield.MDCTextfield.attachTo(tf);
            }
          })();

        document.getElementById("toolbar_title").onclick = function() {
            window.location.href = "/";
        };

        save_buttons = [
            document.getElementById("toolbar_save"),
            document.getElementById("button_save")
        ];

        $("#edit_form").submit(function(event) {
                       console.log($("#edit_form").serialize());
                       <c:choose>
                        <c:when test="${not empty news}">
                        $.ajax({
                            url: "/category/${category_id}/news/${news.getId()}",
                            type: "PUT",
                        </c:when>
                        <c:otherwise>
                        $.ajax({url: "/category/${category_id}/news",
                            type: "POST",
                        </c:otherwise>
                       </c:choose>
                            data: $("#edit_form").serialize(),
                            success: function(data, textStatus, jqXHR) {
                                console.log(data)
                                response = $.parseJSON(data);
                                window.location.replace(response.redirect);
                            }
                       }).fail(function(data) {
                            console.error(data.responseText);
                            response = $.parseJSON(data.responseText);
                            response.errors.forEach(function(error){
                                for (var key in error) {
                                    $("#helper_" + key).text(error[key]);
                                }
                            });
                       });
                       event.preventDefault();
                   });

        $("#toolbar_save").click(function(){
            $("#edit_form").submit();
        });

        mdc.ripple.MDCRipple.attachTo($("#toolbar_save")[0]);

    </script>
</body>
</html>