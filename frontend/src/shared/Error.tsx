const alertError = (message: any) => {
  if (message) {
    alert(message)
  } else {
    alert("500 error")
  }
}

export default alertError;