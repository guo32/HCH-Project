function checkForm() {
		var coffeeName = document.getElementById("name");
		var manufacturer = document.getElementById("manufacturer");
		var price = document.getElementById("price");
		var taste = document.getElementById("taste");
		var volume = document.getElementById("volume");
		var review = document.getElementById("review");
		var filename = document.getElementById("filename");
				
		/* 원두명 길이 */
		if(coffeeName.value.length < 5 || coffeeName.value.length > 30) {
			alert("[원두명]\n5~30자 이내로 작성해주세요.");
			coffeeName.select();
			coffeeName.focus();
			return false;
		}
				
		/* 제조사 길이 */
		if(manufacturer.value.length < 1 || manufacturer.value.length > 20) {
			alert("[제조사]\n1~20자 이내로 작성해주세요.");
			manufacturer.select();
			manufacturer.focus();
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
		
		/* 맛(노트) 선택 여부 */
		if(taste.value == "-1") {
			alert("[맛(노트)]\n선택이 필요합니다.");
			//taste.select();
			//taste.focus();
			return false;
		}
		
		/* 용량 입력 확인 */
		if(volume.value == "" || volume.value == null) {
			alert("[용량]\n용량은 비워둘 수 없습니다.");
			volume.select();
			volume.focus();
			return false;
		}
		
		/* 용량 형식(슷자) */
		if(isNaN(volume.value)) {
			alert("[용량]\n숫자만 입력해야 합니다.");
			volume.select();
			volume.focus();
			return false;
		}
		
		/* 후기 입력 확인 */
		if(review.value == "" || review.value == null) {
			alert("[후기]\n후기는 비워둘 수 없습니다.");
			review.select();
			review.focus();
			return false;
		}
		
		/* 파일 입력 확인 */
		if(filename.value == "" || filename.value == null) {
			alert("[이미지]\n이미지는 비워둘 수 없습니다.");
			filename.select();
			filename.focus();
			return false;
		}
}