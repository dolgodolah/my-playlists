const alertError = (error: any) => {
  if (error) {
    alert(error.message)
  } else {
    alert("500 error")
  }
}

export default alertError;