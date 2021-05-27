function checkDeletion(){
	const deleteForm = document.forms['deleteForm'];
	if (confirm("정말 삭제하시겠습니까?")==true){
		deleteForm.submit();
	}else{
		return;
	}
}