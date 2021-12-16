<!DOCTYPE html>
<html lang="ko" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>로그인페이지</title>
</head>

<body>
    <div style="padding:10px">
		<form th:action="@{/shop/login}" method="post">
			<input type="text" name="id" placeholder="아이디입력" /><br />
			<input type="password" name="password" placeholder="암호입력" /><br />
			<input type="submit" value="로그인" />
		</form>    
    </div>
</body>
</html>
