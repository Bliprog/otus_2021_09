<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Изменение пароля ${userForm.username}</title>
</head>

<body>
<#import "/spring.ftl" as spring/>
<div>
    <fieldset>
        <form method="POST" name="userForm" action="/edit/${userForm.username}/change_password">
            Пароль:<@spring.formInput "userForm.password" "" "password"/> <br/>
            <@spring.showErrors "<br>" /><br>
            Подтверждение пароля: <@spring.formInput "userForm.passwordConfirm" "" "password"/>    <br/>
            <@spring.showErrors "<br>" /><br>
            <button type="submit">Изменить</button>
            <#if accessError??>
            <p>${accessError}</p>
            </#if>
            <#if passwordError??>
                <p>${passwordError}</p>
        </#if>
        </form>
    </fieldset>
    <a href="/user_table">Таблица пользователей</a>
</div>
</body>
</html>