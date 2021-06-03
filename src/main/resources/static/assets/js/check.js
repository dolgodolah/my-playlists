function checkPlaylistDeletion(){
	const deleteForm = document.forms['deletePlaylistForm'];
	if (confirm("정말 삭제하시겠습니까?")==true){
		deleteForm.submit();
	}else{
		return;
	}
}

function checkSongDeletion(){
	const deleteForm = document.forms['deleteSongForm'];
	if (confirm("정말 삭제하시겠습니까?")==true){
		deleteForm.submit();
	}else{
		return;
	}
}