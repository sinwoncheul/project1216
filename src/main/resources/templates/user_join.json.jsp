<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>회원가입</title>
</head>

<body>
    <div style="padding:10px">
		<form th:action="@{/user/join.json}" method="post">
			<input type="text" name="id" placeholder="아이디입력" /><br />
			<input type="password" name="password" placeholder="암호입력" /><br />
			<input type="text" name="name" placeholder="이름"/><br />
            <input type="text" name="address" placeholder="주소" /><br />
			<input type="text" name="phone" placeholder="연락처" /><br />
			<select name="userrole">
				<option value="CUSTOMER">고객</option>
				<option value="SELLER">판매자</option>
				<option value="ADMIN">관리자</option>
			</select><br />
			<input type="submit" value="회원가입" />
		</form>    
    </div>
</body>
</html>
