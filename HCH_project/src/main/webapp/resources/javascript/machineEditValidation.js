function checkForm() {
		var machineName = document.getElementById("name");
		var brand = document.getElementById("brand");
		var price = document.getElementById("price");
		var review = document.getElementById("review");
				
		/* 가전명 길이 */
		if(machineName.value.length < 5 || machineName.value.length > 30) {
			alert("[가전명]\n5~30자 이내로 작성해주세요.");
			machineName.select();
			machineName.focus();
			return false;
		}
				
		/* 제조사 길이 */
		if(brand.value.length < 1 || brand.value.length > 20) {
			alert("[브랜드]\n1~20자 이내로 작성해주세요.");
			brand.select();
			brand.focus();
			return false;
		}
		
		/* 가격 입력 확인 */
		if(price.value == "" || price.value == null) {
			alert("[가격]\n가격은 비워둘 수 없습니다.");
			price.select();
			price.focus();
			return false;
		}
		
		/* 가격 형식(슷자) */
		if(isNaN(price.value)) {
			alert("[가격]\n숫자만 입력해야 합니다.");
			price.select();
			price.focus();
			return false;
		}
		
		/* 후기 입력 확인 */
		if(review.value == "" || review.value == null) {
			alert("[후기]\n후기는 비워둘 수 없습니다.");
			review.select();
			review.focus();
			return false;
		}
}