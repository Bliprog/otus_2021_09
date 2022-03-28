<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${user.username}</title>
</head>
<body>
<p> Имя пользователя:${user.username}</p>
<p> Адрес эл. почты:${user.email}</p>
<p> Номер телефона:${user.phone}</p>
<#if (isAdmin &&!isUserAdmin)||isSuperAdmin>
    <form method="post" action="/admin/delete/${user.username}">
        <button type="submit">Удалить</button>
    </form>
    <#if accessError??>
        <p>${accessError}</p>
    </#if>
    <#if superAdminError??>
        <p>${superAdminError}</p>
    </#if>
    <form method="get" action="/edit/${user.username}/change_information">
        <button type="submit">Изменить информацию</button>
    </form>
    <form method="get" action="/edit/${user.username}/change_password">
        <button type="submit">Изменить пароль</button>
    </form>
<#elseif isUserProfile>
    <form method="get" action="/edit/${user.username}/change_information">
        <button type="submit">Изменить информацию</button>
    </form>
    <form method="get" action="/edit/${user.username}/change_password">
        <button type="submit">Изменить пароль</button>
    </form>
</#if>
<a href="/user_table">Таблица пользователей</a>

</body>
</html>