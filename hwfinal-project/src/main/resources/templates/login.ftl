
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Вход</title>
</head>

<body>
<div>
    <form method="POST" action="http://localhost:8080/login">
        <h2>Вход в систему</h2>
        <div>
            <input name="username" type="text" placeholder="Логин"
                   autofocus="true"/>
            <input name="password" type="password" placeholder="Пароль"/>
            <button type="submit">Войти</button>
        </div>
    </form>
    <h4><a href="/registration">Зарегистрироваться</a></h4>
</div>

</body>
</html>