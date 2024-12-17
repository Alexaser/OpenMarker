<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Добро пожаловать в мир приключений</title>
    <link rel="stylesheet" type="text/css" href="styles/main.css">
</head>
<body>
<div class="container">
    <c:if test="${userName == null}">
        <header>
            <h1>Добро пожаловать в волшебный мир приключений!</h1>
            <p>Ты оказался в загадочном месте, полном неизведанных тропинок, древних легенд и великих испытаний. Перед тобой лежит путь, полный опасностей и тайн. Но чтобы начать своё приключение, нам нужно узнать твоё имя. Как к тебе обращаться, путник?</p>
        </header>
        <section>
            <h2>Расскажи о себе!</h2>
            <p>Введи своё имя в поле ниже, чтобы начать свое великое путешествие. Пусть твоё имя станет известным в этом загадочном мире!</p>
            <form action="startGameServlet" method="get">
                <label for="userName">Твоё имя:</label><br>
                <input
                        type="text"
                        id="userName"
                        name="userName"
                        placeholder="Введите своё имя здесь"
                        required
                        oninvalid="this.setCustomValidity('Пожалуйста, напишите ваше имя.')"
                        oninput="this.setCustomValidity('')">
                <br><br>
                <button type="submit" class="button">Начать приключение</button>
            </form>
        </section>
    </c:if>

    <c:if test="${userName != null}">
        <header>
            <h1>Добро пожаловать, ${userName}!</h1>
            <p>Ты сделал первый шаг на пути к великим свершениям. Перед тобой открываются двери в мир загадок, магии и опасностей. Выбери свою первую историю и начни приключение!</p>
        </header>
        <main>
            <div class="game-card">
                <a href="startGameServlet?game=1">
                    <img src="https://oboi-telefon.ru/wallpapers/179154/36763.jpg" alt="Таинственный лес">
                    <p>Таинственный лес</p>
                    <p>Игр сыграно: ${countGamesMysteriousForest}</p>
                </a>
            </div>
            <div class="game-card">
                <a href="startGameServlet?game=2">
                    <img src="images/dragon_hunt.jpg" alt="Охота на драконов">
                    <p>Охота на драконов</p>
                    <p>Игр сыграно: 0</p>
                </a>
            </div>
            <div class="game-card">
                <a href="startGameServlet?game=3">
                    <img src="images/dungeons_castles.jpg" alt="Подземелья и замки">
                    <p>Подземелья и замки</p>
                    <p>Игр сыграно: 0</p>
                </a>
            </div>
            <div class="user-info">
                <p><strong>Имя:</strong> ${userName}</p>
            </div>
        </main>
    </c:if>
</div>
</body>
</html>