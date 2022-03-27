<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User Table</title>
</head>
<body>
<div>
<table border="1" <#if isGet><#else>align="center"</#if>>
    <tr>
    <th>Имя пользователя</th>
    <th>Адрес эл. почты</th>
    </tr>
<#list users as us>
    <tr>
        <td>
            <a href="/user_details/${us.username}">${us.username}</a>
        </td>
        <td>
            ${us.email}
        </td>
    </tr>
</#list>
</table>
</div>
<br>
<#if isGet>
    <div>
    <form method="get" action="/user_table/getPdf">
    <button type="submit">Получить PDF</button>
</form>
    <p><a href="/">На главную</a></p>
    </div>
</#if>

</body>
</html>