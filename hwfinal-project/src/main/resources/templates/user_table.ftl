<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User Table</title>
    <!-- CSS only -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
</head>
<body>
<div>
<table class="table" <#if isGet><#else>align="center"</#if>>
    <tr>
        <th scope="col">#</th>
        <th scope="col">Имя пользователя</th>
        <th scope="col">Адрес эл. почты</th>
    </tr>
<#list users as us>
    <tr>
        <th scope="row">${us_index+1}</th>
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
    <p><a href="/">На главную</a></p>
</body>
</html>