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
    .app-fab--absolute.app-fab--absolute {
      position: absolute;
      bottom: 1rem;
      right: 1rem;
    }

    @media(min-width: 1024px) {
       .app-fab--absolute.app-fab--absolute {
        bottom: 3rem;
        right: 5rem;
      }
    }
    </style>

    <style>
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
        /* Place drawer and main next to each other. */
        .demo-content {
          display: flex;
          height: 100%;
          box-sizing: border-box;
        }
        .demo-main {
          width: 100%;
          padding-left: 16px;
        }
        .material-icons {
                text-decoration: none;
                cursor: pointer;
                padding-right: 8px;
              }
              .mdc-toolbar__title {
                cursor: pointer;
              }
        </style>
</head>


<body class="mdc-typography demo-body">
    <header class="mdc-toolbar mdc-toolbar--fixed">
      <section class="mdc-toolbar__section mdc-toolbar__section--align-start">
        <c:if test="${not empty chosen_category_id}">
        <a class="material-icons" id="arrow_back">arrow_back</a>
        </c:if>
        <span class="mdc-toolbar__title" id="toolbar_title">News</span>
      </section>
      <section class="mdc-toolbar__section mdc-toolbar__section--align-end" role="toolbar">
          <a class="material-icons" aria-label="Create" alt="Create">add</a>
        </section>
    </header>

    <div class="demo-content mdc-toolbar-fixed-adjust">
          <nav class="mdc-permanent-drawer" style="width:350px;">
            <div class="mdc-list-group">
              <nav class="mdc-list">
              <c:forEach items="${categories}" var="category">
                <c:choose>
                    <c:when test="${(not empty chosen_category_id) && (chosen_category_id == category.getId())}">
                        <a class="mdc-list-item mdc-permanent-drawer--selected" href="#">
                          <i class="material-icons mdc-list-item__start-detail" aria-hidden="true">subdirectory_arrow_right</i>
                          <c:out value="${category.getName()}"/>
                        </a>
                    </c:when>
                    <c:otherwise>
                        <a class="mdc-list-item" href="/category/${category.getId()}">
                          <i class="material-icons mdc-list-item__start-detail" aria-hidden="true">subdirectory_arrow_right</i>
                          <c:out value="${category.getName()}"/>
                        </a>
                    </c:otherwise>
                </c:choose>
              </c:forEach>
              </nav>
            </div>
          </nav>
          <main class="demo-main">
            <ul class="mdc-list">
                    <c:forEach items="${news}" var="news_item">
                      <li class="mdc-list-item-text">
                        <h2 class="mdc-typography--title">
                            <a href="/category/${news_item.getCategory().getId()}/news/${news_item.getId()}">
                                #${news_item.getId()} <c:out value="${news_item.getName()}"/>
                            </a>
                        </h2>
                        <p class="mdc-typography--body1"><c:out value="${news_item.getContents()}"/></p>
                        <a href="/category/${news_item.getCategory().getId()}" class="mdc-typography--caption">
                        <i class="material-icons"  style="vertical-align:bottom; padding-right: 0px;">subdirectory_arrow_right</i>
                        <c:out value="${news_item.getCategory().getName()}"/>
                        </a>
                        <i class="material-icons" style="vertical-align:bottom; padding-right: 0px; margin-left: 20px;">access_time</i>
                        ${news_item.getPublicationDate()}
                      </li>
                      <li role="separator" class="mdc-list-divider"></li>
                    </c:forEach>
                </ul>
          </main>
    </div>

    <script type="text/javascript">
        back_to_main = function() {
           window.location.href = "/";
        };
        document.getElementById("toolbar_title").onclick = back_to_main;
        arrow_back = document.getElementById("arrow_back")
        if (arrow_back != null) arrow_back.onclick = back_to_main;
    </script>

</body>
</html>
