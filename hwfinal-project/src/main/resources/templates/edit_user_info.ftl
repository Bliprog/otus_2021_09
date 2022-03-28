<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Изменение пользователя ${userForm.username}</title>
</head>

<body>

<div>
    <fieldset>
        <form method="POST" name="userForm">
            <h2>Изменение пользователя ${userForm.username}</h2>
            Имя пользователя:<input name="username" type="text"/> <br/>
            Адрес эл.почты: <input name="email"  type="text"/>    <br/>
            Номер телефона: <input name="phone" type="text"/>    <br/>
            <button type="submit">Изменить</button>
            <#if accessError??>
                <p>${accessError}</p>
            </#if>
            <#if usernameError??>
                <p>${usernameError}</p>
            </#if>
        </form>
    </fieldset>
    <a href="/user_table">Таблица пользователей</a>
</div>
</body>
</html>