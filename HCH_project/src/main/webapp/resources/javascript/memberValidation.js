function checkForm() {
		var userId = document.getElementById("id");
		var password = document.getElementById("password");
		var confirmPassword = document.getElementById("confirmPassword");
		var email = document.getElementById("email");
		var name = document.getElementById("name");
		var birth = document.getElementById("birth");
		
		/* 아이디 형식 */
		if(!check(/^[a-zA-Z]\w+$/, userId, "[아이디]\n알파벳과 숫자를 조합하여 작성해주세요.\n첫글자는 알파벳이어야 합니다."))
			return false;
		
		/* 아이디 길이 */
		if(userId.value.length < 6 || userId.value.length > 16) {
			alert("[아이디]\n6~16자 이내로 작성해주세요.");
			userId.select();
			userId.focus();
			return false;
		}
				
		/* 비밀번호 형식 */
		if(!check(/\d*[a-zA-Z]+\d+[a-zA-Z]*$/, password, "[비밀번호]\n알파벳과 숫자를 조합하여 작성해주세요."))
			return false;
				
		/* 비밀번호 길이 */
		if(password.value.length < 8 || password.value.length > 16) {
			alert("[비밀번호]\n8~16자 이내로 작성해주세요.");
			password.select();
			password.focus();
			return false;
		}
		
		if(password.value !== confirmPassword.value) {
			alert("[비밀번호 확인]\n비밀번호가 일치하지 않습니다.");
			confirmPassword.select();
			confirmPassword.focus();
			return false;
		}		
				
		/* 이메일 형식 */
		if(!check(/\d*[a-zA-Z]+\d*@[a-zA-Z]+.[a-zA-Z]+$/, email, "[이메일]\n형식을 확인해주세요."))
			return false;
		
		/* 이메일 길이 */
		if(email.value.length < 10 || email.value.length > 35) {
			alert("[이메일]\n10~35자 이내로 작성해주세요.");
			email.select();
			email.focus();
			return false;
		}
		
		/* 이름 길이 */
		if(name.value.length < 1 || name.value.length > 10) {
			alert("[이름]\n1~10자 이내로 작성해주세요.");
			name.select();
			name.focus();
			return false;
		}
		
		/* 생년월일 형식 */
		if(!check(/^\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][1-9]|3[01])$/, birth, "[생년월일]\n형식을 확인해주세요.\n예: 1999-01-01"))
			return false;
		
		function check(regExp, e, msg) {
			if(regExp.test(e.value)) {
				return true;
			}
			alert(msg);
			e.select();
			e.focus();
			return false;
		}
		
		document.registerForm.submit();
	}