const alertError = (response: any) => {
  if (response.message) {
    alert(response.message)
  } else {
    alert("500 error")
  }
}

export default alertError;