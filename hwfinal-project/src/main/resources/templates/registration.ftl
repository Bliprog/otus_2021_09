<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Регистрация</title>
</head>

<body>
<#import "/spring.ftl" as spring/>
<div>
        <fieldset>
            <form method="POST" name="userForm">
                <h2>Регистрация</h2>
                Username: <@spring.formInput "userForm.username" "" "text"/>    <br/>
                <@spring.showErrors "<br>" /><br>
                <#if usernameError??>
                    ${saveError}
                </#if>
                Email: <@spring.formInput "userForm.email" "" "text"/>    <br/>
                <@spring.showErrors "<br>" /><br>
                Phone: <@spring.formInput "userForm.phone" "" "text"/>    <br/>
                <@spring.showErrors "<br>" /><br>
                Password: <@spring.formInput "userForm.password" "" "password"/>    <br/>
                <@spring.showErrors "<br>" /><br>
                Password Confirm: <@spring.formInput "userForm.passwordConfirm" "" "password"/>    <br/>
                <@spring.showErrors "<br>" /><br>
                <#if passwordError??>
                    ${passwordError}
                </#if>
                <button type="submit">Зарегистрироваться</button>
            </form>
        </fieldset>
        <div>
            <@spring.showErrors "<br>"/>
        </div>
</div>
    <a href="/">Главная</a>
</body>
</html>

