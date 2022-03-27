<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Главная</title>
</head>
<body>
<#if username=="unknown">
    <h3>Добро пожаловать, вы не авторизовались.</h3>
    <h4><a href="/login">Войти</a></h4>
    <h4><a href="/registration">Зарегистрироваться</a></h4>
<#elseif isAdmin>
    <h3>Добро пожаловать, админ ${username}</h3>
    <h4><a href="/user_table">Таблица пользователей</a></h4>
    <h4><a href="/chat">Чат</a></h4>
    <h4><a href="/logout">Выйти</a></h4>
<#else>
    <h3>Добро пожаловать, ${username}</h3>
    <p>Вы обычный пользователь</p>
    <h4><a href="/chat">Чат</a></h4>
    <h4><a href="/logout">Выйти</a></h4>
</#if>
</body>
</html>